package l.nguyen.ms.common.model.transaction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.util.Date;

import lnguyen.jpa.AbstractEntity;

@Entity
// When received transaction summary from requested bank, generatedDt and authCode must match
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"requestBank", "authCode", "generatedDt"})})
public class GeneratedAuthCode extends AbstractEntity {

    @Column(length = 50)
    private String requestBank;

    @Column
    private Integer authCode;

    @Column
    private Date generatedDt;

    public GeneratedAuthCode() {
    }

    public GeneratedAuthCode(String requestBank, int authCode) {
        this.requestBank = requestBank;
        this.authCode = authCode;
        this.generatedDt = new Date();
    }

    public String getRequestBank() {
        return requestBank;
    }

    public void setRequestBank(String requestBank) {
        this.requestBank = requestBank;
    }

    public Integer getAuthCode() {
        return authCode;
    }

    public void setAuthCode(Integer authCode) {
        this.authCode = authCode;
    }

    public Date getGeneratedDt() {
        return generatedDt;
    }

    public void setGeneratedDt(Date generatedDt) {
        this.generatedDt = generatedDt;
    }
}
