package com.jeefw.controller.sys;

import com.jeefw.core.Constant;
import com.jeefw.core.JavaEEFrameworkBaseController;
import com.jeefw.model.sys.ProductInfo;
import com.jeefw.model.sys.ProductShelf;
import com.jeefw.model.sys.SmartCollocation;
import com.jeefw.service.sys.ProductInfoService;
import com.jeefw.service.sys.ProductShelfService;
import com.jeefw.service.sys.SmartCollocationService;
import core.dto.SmartCollocationDTO;
import core.util.GSON;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 智能搭配 Controller
 *
 * @author Hystar
 * @date 2018/9/1
 */
@Controller
@RequestMapping(value = "/smart/collocation")
public class SmartCollocationController extends JavaEEFrameworkBaseController implements Constant {

    @Resource
    private SmartCollocationService smartCollocationService;

    @Resource
    private ProductShelfService productShelfService;

    /**
     * 前台 - 获取智能搭配列表
     *
     * @return
     */
    @RequestMapping(value = "/get_smart_collocation_list_fe", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getSmartCollocationListFe() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("success", false);
        Integer allianceId = getCurrentAllianceId();

        List<SmartCollocation> smartCollocationList = smartCollocationService.queryByProerties("allianceId", allianceId);
        List<SmartCollocationDTO> smartCollocationDTOList = assemblySmartCollocation(smartCollocationList);
        resultMap.put("list", smartCollocationDTOList);
        resultMap.put("success", true);

        return resultMap;
    }

    private List<SmartCollocationDTO> assemblySmartCollocation(List<SmartCollocation> smartCollocationList) {
        List<SmartCollocationDTO> smartCollocationDTOList = new ArrayList<>();

        if (smartCollocationList.size() > 0) {
            for (SmartCollocation smartCollocation : smartCollocationList) {
                SmartCollocationDTO smartCollocationDTO = new SmartCollocationDTO();
                smartCollocationDTO.setId(smartCollocation.getId());
                smartCollocationDTO.setDiscountPrice(smartCollocation.getDiscountPrice());
                smartCollocationDTO.setOriginalPrice(smartCollocation.getOriginalPrice());
                List<Long> productShelfIdList = new ArrayList<>();
                if (!StringUtils.isEmpty(smartCollocation.getShelfIds())) {
                    productShelfIdList = GSON.toList(smartCollocation.getShelfIds(), Long.class);
                }
                boolean ok = true;
                List<ProductShelf> productShelfs = productShelfService.queryProductShelfListByIdIn(productShelfIdList);
                for (ProductShelf productShelf : productShelfs) {
                    // 如果货架商品数量为0，则不进行展示
                    if (productShelf.getNum() <= 0) {
                        ok = false;
                    }
                }
                if (ok) {
                    smartCollocationDTO.setProductShelfList(productShelfs);
                    smartCollocationDTOList.add(smartCollocationDTO);
                }
            }
        }

        return smartCollocationDTOList;
    }
}
