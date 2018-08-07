package core.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信刷卡支付返回信息
 *
 * @author Hystar
 * @date 2018/7/18
 */
@XmlRootElement(name = "xml")
public class WechatSwipePayDTO {

    private String return_code;

    private String return_msg;

    private String appid;

    private String mch_id;

    private String device_info;

    private String nonce_str;

    private String sign;

    private String result_code;

    private String err_code;

    private String err_code_des;

    private String openid;

    private String is_subscribe;

    private String trade_type;

    private String bank_type;

    private String fee_type;

    private Integer total_fee;

    private Integer settlement_total_fee;

    private Integer coupon_fee;

    private String cash_fee_type;

    private Integer cash_fee;

    private String transaction_id;

    private String out_trade_no;

    private String attach;

    private String time_end;

    public WechatSwipePayDTO() {
    }

    public WechatSwipePayDTO(String return_code, String return_msg, String appid, String mch_id, String device_info, String nonce_str, String sign, String result_code, String err_code, String err_code_des, String openid, String is_subscribe, String trade_type, String bank_type, String fee_type, Integer total_fee, Integer settlement_total_fee, Integer coupon_fee, String cash_fee_type, Integer cash_fee, String transaction_id, String out_trade_no, String attach, String time_end) {
        this.return_code = return_code;
        this.return_msg = return_msg;
        this.appid = appid;
        this.mch_id = mch_id;
        this.device_info = device_info;
        this.nonce_str = nonce_str;
        this.sign = sign;
        this.result_code = result_code;
        this.err_code = err_code;
        this.err_code_des = err_code_des;
        this.openid = openid;
        this.is_subscribe = is_subscribe;
        this.trade_type = trade_type;
        this.bank_type = bank_type;
        this.fee_type = fee_type;
        this.total_fee = total_fee;
        this.settlement_total_fee = settlement_total_fee;
        this.coupon_fee = coupon_fee;
        this.cash_fee_type = cash_fee_type;
        this.cash_fee = cash_fee;
        this.transaction_id = transaction_id;
        this.out_trade_no = out_trade_no;
        this.attach = attach;
        this.time_end = time_end;
    }

    public String getReturn_code() {
        return return_code;
    }

    @XmlElement
    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    @XmlElement
    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getAppid() {
        return appid;
    }

    @XmlElement
    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    @XmlElement
    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getDevice_info() {
        return device_info;
    }

    @XmlElement
    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    @XmlElement
    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    @XmlElement
    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    @XmlElement
    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    @XmlElement
    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    @XmlElement
    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getOpenid() {
        return openid;
    }

    @XmlElement
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    @XmlElement
    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getTrade_type() {
        return trade_type;
    }

    @XmlElement
    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getBank_type() {
        return bank_type;
    }

    @XmlElement
    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public String getFee_type() {
        return fee_type;
    }

    @XmlElement
    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    @XmlElement
    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public Integer getSettlement_total_fee() {
        return settlement_total_fee;
    }

    @XmlElement
    public void setSettlement_total_fee(Integer settlement_total_fee) {
        this.settlement_total_fee = settlement_total_fee;
    }

    public Integer getCoupon_fee() {
        return coupon_fee;
    }

    @XmlElement
    public void setCoupon_fee(Integer coupon_fee) {
        this.coupon_fee = coupon_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    @XmlElement
    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public Integer getCash_fee() {
        return cash_fee;
    }

    @XmlElement
    public void setCash_fee(Integer cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    @XmlElement
    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    @XmlElement
    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getAttach() {
        return attach;
    }

    @XmlElement
    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getTime_end() {
        return time_end;
    }

    @XmlElement
    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }
}
