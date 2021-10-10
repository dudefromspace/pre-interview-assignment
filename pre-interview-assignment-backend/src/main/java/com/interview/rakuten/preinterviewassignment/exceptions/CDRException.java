package com.interview.rakuten.preinterviewassignment.exceptions;

public class CDRException extends Exception{

    private final String errorCode;

    public CDRException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public CDRException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CDRException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public CDRException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
