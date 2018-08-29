package com.jeefw.dao.sys.impl;

import com.jeefw.dao.sys.ProductSalesVolumeDao;
import com.jeefw.model.sys.ProductSalesVolume;
import core.dao.BaseDao;
import org.springframework.stereotype.Repository;

/**
 * @author Hystar
 * @date 2018/8/29
 */
@Repository
public class ProductSalesVolumeDaoImpl extends BaseDao<ProductSalesVolume> implements ProductSalesVolumeDao {

    public ProductSalesVolumeDaoImpl() {
        super(ProductSalesVolume.class);
    }
}
