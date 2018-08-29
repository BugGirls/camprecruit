package com.jeefw.controller.sys;

import com.google.gson.reflect.TypeToken;
import com.jeefw.model.sys.ProductInfo;
import com.jeefw.service.sys.ProductInfoService;
import core.util.GSON;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车
 *
 * @author Hystar
 * @date 2018/8/24
 */
@Controller
@RequestMapping(value = "/cart/")
public class CartController {

    @Resource
    private ProductInfoService productInfoService;

    /**
     * 结算
     *
     * @param productIdList
     * @return
     */
    @RequestMapping(value = "/settle_account", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> settleAccount(@RequestParam(value = "productIdList") String productIdList) {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);

        if (StringUtils.isEmpty(productIdList)) {
            resultMap.put("msg", "购物车为空");
            return resultMap;
        }

        List<Long> idList = GSON.toObject(productIdList, new TypeToken<List<Long>>() {
        }.getType());

        BigDecimal amount = new BigDecimal(BigInteger.ZERO);
        for (Long id : idList) {
            ProductInfo productInfo = productInfoService.getByProerties("id", id);
            if (productInfo == null) {
                resultMap.put("msg", "商品不存在");
                return resultMap;
            }
            amount = productInfo.getAdvicePrice().multiply(new BigDecimal(1)).add(amount);
        }

        resultMap.put("amount", amount.toString());
        resultMap.put("success", true);

        return resultMap;
    }
}
