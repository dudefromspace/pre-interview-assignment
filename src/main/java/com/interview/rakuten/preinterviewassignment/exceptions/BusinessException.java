package com.interview.rakuten.preinterviewassignment.exceptions;

public class BusinessException extends Exception {

    private final String errorCode;

    public BusinessException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
