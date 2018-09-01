package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.OrderDetailDao;
import com.jeefw.dao.sys.OrderMasterDao;
import com.jeefw.dao.sys.ProductInfoDao;
import com.jeefw.model.sys.OrderDetail;
import com.jeefw.model.sys.OrderMaster;
import com.jeefw.model.sys.ProductInfo;
import com.jeefw.model.sys.SmartCollocation;
import com.jeefw.service.sys.OrderMasterService;
import com.jeefw.service.sys.SmartCollocationService;
import core.dto.OrderMasterDTO;
import core.enums.OrderStatusEnum;
import core.enums.PayStatusEnum;
import core.enums.TradeTypeEnum;
import core.service.BaseService;
import core.util.GSON;
import core.util.KeyUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

/**
 * @author Hystar
 * @date 2018/7/18
 */
@Service
public class OrderMasterServiceImpl extends BaseService<OrderMaster> implements OrderMasterService {

    private OrderMasterDao orderMasterDao;

    @Resource
    private OrderDetailDao orderDetailDao;

    @Resource
    private ProductInfoDao productInfoDao;

    @Resource
    private SmartCollocationService smartCollocationService;

    @Resource
    public void setOrderMasterDao(OrderMasterDao orderMasterDao) {
        this.orderMasterDao = orderMasterDao;
        this.dao = orderMasterDao;
    }

    /**
     * 创建订单
     *
     * @param orderMasterDTO
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderMasterDTO create(OrderMasterDTO orderMasterDTO) throws Exception {
        // 生成订单编号
        String orderId = KeyUtil.generatorUniqueKey();
        // 初始化订单总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        // 1、查询商品（数量、价格）
        for (OrderDetail orderDetail : orderMasterDTO.getOrderDetailList()) {
            ProductInfo productInfo = productInfoDao.getByProerties("no", orderDetail.getProductNo());
            if (productInfo == null) {
                throw new Exception("商品不存在");
            }

            // 2、计算订单总价
            orderAmount = productInfo.getAdvicePrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            // 3、订单详情入库（前端返回的为商品id和商品数量，其他的订单详情字段都得自己设置）
            orderDetail.setDetailId(KeyUtil.generatorUniqueKey());
            orderDetail.setOrderId(orderId);
            orderDetail.setProductId(productInfo.getId());
            orderDetail.setProductName(productInfo.getName());
            orderDetail.setProductIcon(productInfo.getImage());
            orderDetail.setTotalPrice(productInfo.getAdvicePrice().multiply(new BigDecimal(orderDetail.getProductQuantity())));
            orderDetail.setProductQuantity(orderDetail.getProductQuantity());
            orderDetail.setCreateTime(new Date());
            orderDetail.setUpdateTime(new Date());
            orderDetail.setProductPrice(productInfo.getAdvicePrice());
            orderDetail.setAllianceId(orderMasterDTO.getAllianceId());
            orderDetailDao.persist(orderDetail);
        }

        // 4、写入订单数据库
        orderMasterDTO.setOrderId(orderId);
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId(orderId);
        orderMaster.setAmount(orderAmount);
        orderMaster.setCreateTime(new Date());
        orderMaster.setUpdateTime(new Date());
        orderMaster.setPayStatus(PayStatusEnum.NOT_PAY.getCode());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setAllianceId(orderMasterDTO.getAllianceId());
        orderMaster.setTradeType(TradeTypeEnum.WECHAT.getCode());
        orderMasterDao.persist(orderMaster);

        // 5、减少商品库存

        return orderMasterDTO;
    }

    /**
     * 创建智能搭配订单
     *
     * @param allianceId
     * @param smartCollocationId
     * @return
     * @throws Exception
     */
    @Override
    public String createBySmart(Integer allianceId, String smartCollocationId) throws Exception {
        if (StringUtils.isBlank(smartCollocationId) || allianceId == 0) {
            throw new Exception("参数错误");
        }

        // 生成订单编号
        String orderId = KeyUtil.generatorUniqueKey();

        SmartCollocation smartCollocation = smartCollocationService.getByProerties("id", smartCollocationId);
        if (smartCollocation != null && StringUtils.isNotBlank(smartCollocation.getProductIds())) {
            List<Long> productIdList = GSON.toList(smartCollocation.getProductIds(), Long.class);
            List<ProductInfo> productInfoList = productInfoDao.queryProductListByIdIn(productIdList);
            for (ProductInfo productInfo : productInfoList) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderId(orderId);
                orderDetail.setDetailId(KeyUtil.generatorUniqueKey());
                orderDetail.setProductId(productInfo.getId());
                orderDetail.setProductName(productInfo.getName());
                orderDetail.setProductIcon(productInfo.getImage());
                orderDetail.setTotalPrice(productInfo.getAdvicePrice().multiply(new BigDecimal(1)));
                orderDetail.setProductQuantity(1);
                orderDetail.setCreateTime(new Date());
                orderDetail.setUpdateTime(new Date());
                orderDetail.setProductPrice(productInfo.getAdvicePrice());
                orderDetail.setAllianceId(allianceId);
                orderDetailDao.persist(orderDetail);
            }

            OrderMaster orderMaster = new OrderMaster();
            orderMaster.setOrderId(orderId);
            orderMaster.setAmount(smartCollocation.getDiscountPrice());
            orderMaster.setCreateTime(new Date());
            orderMaster.setUpdateTime(new Date());
            orderMaster.setPayStatus(PayStatusEnum.NOT_PAY.getCode());
            orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
            orderMaster.setAllianceId(allianceId);
            orderMaster.setTradeType(TradeTypeEnum.WECHAT.getCode());
            orderMasterDao.persist(orderMaster);

            // todo 减少商品库存
        } else {
            throw new Exception("智能搭配数据不存在");
        }

        return orderId;
    }

    /**
     * 通过订单编号获取订单详情
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    @Override
    public OrderMasterDTO getOrderById(String orderId) throws Exception {
        // 非法参数判断
        if (StringUtils.isBlank(orderId)) {
            throw new Exception("参数错误");
        }

        // 通过订单编号获取订单信息
        OrderMaster orderMaster = orderMasterDao.getByProerties("orderId", orderId);
        if (orderMaster == null) {
            throw new Exception("订单不存在");
        }

        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);
        return orderMasterDTO;
    }

    /**
     * 获取订单和订单详情信息
     *
     * @param orderId
     * @return
     * @throws Exception
     */
    @Override
    public OrderMasterDTO getOrderDetail(String orderId) throws Exception {
        // 非法参数判断
        if (StringUtils.isBlank(orderId)) {
            throw new Exception("参数错误");
        }

        OrderMaster orderMaster = orderMasterDao.getByProerties("orderId", orderId);
        if (orderMaster == null) {
            throw new Exception("订单不存在");
        }

        List<OrderDetail> orderDetailList = orderDetailDao.queryByProerties("orderId", orderId);
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();
        BeanUtils.copyProperties(orderMaster, orderMasterDTO);
        orderMasterDTO.setOrderDetailList(orderDetailList);
        return orderMasterDTO;
    }
}
