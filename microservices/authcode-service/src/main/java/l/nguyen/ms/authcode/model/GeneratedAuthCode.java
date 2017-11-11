package l.nguyen.ms.authcode.model;

import java.util.Date;

import org.apache.commons.lang.math.RandomUtils;
import org.joda.time.DateTime;

public class GeneratedAuthCode {

    private String merchantId;

    private int authCode;

    private Date generatedDt;

    private String creditCardNumber;

    public GeneratedAuthCode() {
    }

    public GeneratedAuthCode(String merchantId, int authCode, String creditCardNumber) {
        this.merchantId = merchantId;
        this.authCode = authCode;
        this.generatedDt = new DateTime().withHourOfDay(RandomUtils.nextInt(16)).withMinuteOfHour(RandomUtils.nextInt(59)).toDate();
        this.creditCardNumber = creditCardNumber;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public int getAuthCode() {
        return authCode;
    }

    public void setAuthCode(int authCode) {
        this.authCode = authCode;
    }

    public Date getGeneratedDt() {
        return generatedDt;
    }

    public void setGeneratedDt(Date generatedDt) {
        this.generatedDt = generatedDt;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
