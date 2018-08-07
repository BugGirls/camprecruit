package core.converter;

import com.google.gson.reflect.TypeToken;
import com.jeefw.model.sys.OrderDetail;
import core.dto.OrderMasterDTO;
import core.form.OrderForm;
import core.util.GSON;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Hystar
 * @date 2018/3/14
 */
public class OrderFormToOrderMasterDTO {

    /**
     * OrderForm 对象转换成OrderMasterDTO 对象
     *
     * @param orderForm
     * @return
     */
    public static OrderMasterDTO convert(OrderForm orderForm) {
        OrderMasterDTO orderMasterDTO = new OrderMasterDTO();

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = GSON.toObject(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        orderMasterDTO.setOrderDetailList(orderDetailList);

        return orderMasterDTO;
    }
}
