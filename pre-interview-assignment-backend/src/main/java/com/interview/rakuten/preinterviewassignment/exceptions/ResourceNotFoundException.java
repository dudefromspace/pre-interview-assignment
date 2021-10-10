package com.interview.rakuten.preinterviewassignment.exceptions;

public class ResourceNotFoundException extends Exception{

    private final String errorCode;

    public ResourceNotFoundException(String errorCode) {
        super(errorCode);
        this.errorCode = errorCode;
    }

    public ResourceNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public ResourceNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public ResourceNotFoundException(Throwable cause, String errorCode) {
        super(cause);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
