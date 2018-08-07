package core.dto;

import java.math.BigDecimal;

/**
 * @author Hystar
 * @date 2018/7/20
 */
public class FundBill {

    private BigDecimal amount;

    private String fund_channel;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getFund_channel() {
        return fund_channel;
    }

    public void setFund_channel(String fund_channel) {
        this.fund_channel = fund_channel;
    }
}
