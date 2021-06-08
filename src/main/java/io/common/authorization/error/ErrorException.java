package io.common.authorization.error;

import lombok.Getter;

@Getter
public class ErrorException extends RuntimeException {

    //http status
    private int status;

    //custom code
    private String code;

    public ErrorException(ErrorCode error) {
        super(error.getMessage());
        this.status = error.getStatus();
        this.code = error.getCode();
    }

    public ErrorException(int status, String code, String message) {
        super(message);
        this.status = status;
        this.code = code;
    }
}
