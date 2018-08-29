package com.jeefw.service.sys.impl;

import com.jeefw.dao.sys.ProductSalesVolumeDao;
import com.jeefw.model.sys.ProductSalesVolume;
import com.jeefw.service.sys.ProductSalesVolumeService;
import core.service.BaseService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Hystar
 * @date 2018/8/29
 */
@Service
public class ProductSalesVolumeServiceImpl extends BaseService<ProductSalesVolume> implements ProductSalesVolumeService {

    private ProductSalesVolumeDao productSalesVolumeDao;

    @Resource
    public void setProductSalesVolumeDao(ProductSalesVolumeDao productSalesVolumeDao) {
        this.productSalesVolumeDao = productSalesVolumeDao;
        this.dao = productSalesVolumeDao;
    }
}
