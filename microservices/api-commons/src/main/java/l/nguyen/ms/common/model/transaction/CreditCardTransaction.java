package l.nguyen.ms.common.model.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lnguyen.jpa.AbstractEntity;
import lnguyen.utils.datetime.BasicDatetimeDeserializer;
import lnguyen.utils.datetime.BasicDatetimeSerializer;

@Entity
public class CreditCardTransaction extends AbstractEntity {

    @Column(length = 20)
    private String cardNumber;

    @Column
    private String paymentReason;

    @Column
    private Double moneyAmount;

    @Column
    @JsonSerialize(using = BasicDatetimeSerializer.class)
    @JsonDeserialize(using = BasicDatetimeDeserializer.class)
    private Date transactionDt;

    @OneToOne(fetch = FetchType.LAZY)
    private GeneratedAuthCode authCode;

    public CreditCardTransaction() {
    }

    public CreditCardTransaction(String cardNumber, String paymentReason, Double money, Date transactionDt) {
        this.cardNumber = cardNumber;
        this.paymentReason = paymentReason;
        this.moneyAmount = money;
        this.transactionDt = transactionDt;
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

    public Double getMoneyAmount() {
        return moneyAmount;
    }

    public void setMoneyAmount(Double money) {
        this.moneyAmount = money;
    }

    public Date getTransactionDt() {
        return transactionDt;
    }

    public void setTransactionDt(Date transactionDt) {
        this.transactionDt = transactionDt;
    }

    public GeneratedAuthCode getAuthCode() {
        return authCode;
    }

    public void setAuthCode(GeneratedAuthCode authCode) {
        this.authCode = authCode;
    }
}
