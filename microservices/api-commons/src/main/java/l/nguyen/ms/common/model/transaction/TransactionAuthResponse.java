package l.nguyen.ms.common.model.transaction;

import java.util.Date;

public class TransactionAuthResponse {
    public enum AuthorizationStatus {
        APPROVED, DENIED
    }

    private Date timestamp;

    private AuthorizationStatus status;

    // expected null when status is DENIED
    private String authCode;

    public TransactionAuthResponse(AuthorizationStatus status, String authCode, Date timestamp) {
        this.status = status;
        this.authCode = authCode;
        this.timestamp = timestamp;
    }

    public AuthorizationStatus getStatus() {
        return status;
    }

    public void setStatus(AuthorizationStatus status) {
        this.status = status;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
