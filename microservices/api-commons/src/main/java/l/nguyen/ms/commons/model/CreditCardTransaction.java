package l.nguyen.ms.commons.model;

import java.util.Date;

public class CreditCardTransaction {
    private String merchantId;

    private String cardNumber;

    private String paymentReason;

    private float money;

    private Date transactionDt;

    public CreditCardTransaction() {
    }

    public CreditCardTransaction(String merchantId, String cardNumber, String paymentReason, float money, Date transactionDt) {
        this.merchantId = merchantId;
        this.cardNumber = cardNumber;
        this.paymentReason = paymentReason;
        this.money = money;
        this.transactionDt = transactionDt;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getPaymentReason() {
        return paymentReason;
    }

    public void setPaymentReason(String paymentReason) {
        this.paymentReason = paymentReason;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public Date getTransactionDt() {
        return transactionDt;
    }

    public void setTransactionDt(Date transactionDt) {
        this.transactionDt = transactionDt;
    }
}
