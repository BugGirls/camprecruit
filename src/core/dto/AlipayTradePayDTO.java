package core.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 支付宝刷卡支付或声波支付返回信息
 *
 * 收银员使用扫码设备读取用户手机支付宝“付款码”、声波获取设备（如麦克风）读取用户手机支付宝的声波信息后返回的数据
 *
 * @author Hystar
 * @date 2018/7/20
 */
public class AlipayTradePayDTO {

    private String alipayTradePayResponse;

    private String code;

    private String msg;

    private String trade_no;

    private String out_trade_no;

    private String buyer_logon_id;

    private BigDecimal total_amount;

    private String receipt_amount;

    private Date gmt_payment;

    private List<FundBill> fundBillList;

    private String buyer_user_id;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getBuyer_logon_id() {
        return buyer_logon_id;
    }

    public void setBuyer_logon_id(String buyer_logon_id) {
        this.buyer_logon_id = buyer_logon_id;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public String getReceipt_amount() {
        return receipt_amount;
    }

    public void setReceipt_amount(String receipt_amount) {
        this.receipt_amount = receipt_amount;
    }

    public Date getGmt_payment() {
        return gmt_payment;
    }

    public void setGmt_payment(Date gmt_payment) {
        this.gmt_payment = gmt_payment;
    }

    public List<FundBill> getFundBillList() {
        return fundBillList;
    }

    public void setFundBillList(List<FundBill> fundBillList) {
        this.fundBillList = fundBillList;
    }

    public String getBuyer_user_id() {
        return buyer_user_id;
    }

    public void setBuyer_user_id(String buyer_user_id) {
        this.buyer_user_id = buyer_user_id;
    }

    public String getAlipayTradePayResponse() {
        return alipayTradePayResponse;
    }

    public void setAlipayTradePayResponse(String alipayTradePayResponse) {
        this.alipayTradePayResponse = alipayTradePayResponse;
    }
}
