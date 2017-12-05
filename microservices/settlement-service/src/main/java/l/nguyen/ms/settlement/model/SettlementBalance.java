package l.nguyen.ms.settlement.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

import lnguyen.jpa.AbstractEntity;

@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"bankId", "date"})})
public class SettlementBalance extends AbstractEntity {

    @Column
    private String bankId;

    @Column
    private Double balanceAmount = 0.0;

    @Column
    private Integer numberOfTransactions = 0;

    @Column
    @Temporal(TemporalType.DATE)
    private Date date;

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public Double getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(Double balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getNumberOfTransactions() {
        return numberOfTransactions;
    }

    public void setNumberOfTransactions(Integer numberOfTransactions) {
        this.numberOfTransactions = numberOfTransactions;
    }

    public String toString() {
        return "Balance: " + bankId + ", " + date + ", " + numberOfTransactions + ", " + balanceAmount;
    }
}
