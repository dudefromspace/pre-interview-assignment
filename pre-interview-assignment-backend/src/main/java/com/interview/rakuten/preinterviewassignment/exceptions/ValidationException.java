package com.interview.rakuten.preinterviewassignment.exceptions;

public class ValidationException extends Exception {

    private final String errorCode;

    public ValidationException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ValidationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ValidationException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ValidationException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
