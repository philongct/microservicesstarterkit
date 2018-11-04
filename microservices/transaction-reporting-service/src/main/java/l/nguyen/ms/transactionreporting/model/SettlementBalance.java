package l.nguyen.ms.transactionreporting.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

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
