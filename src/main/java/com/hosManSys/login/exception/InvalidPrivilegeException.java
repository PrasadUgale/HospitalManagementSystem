package com.hosManSys.login.exception;

public class InvalidPrivilegeException extends RuntimeException {
    private String errorCode;
    private String errorMessage;
    public String getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public InvalidPrivilegeException(String errorCode, String errorMessage) {
        super();
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public InvalidPrivilegeException() {
        super();
        // TODO Auto-generated constructor stub
    }

    public InvalidPrivilegeException(String message, Throwable cause, boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        // TODO Auto-generated constructor stub
    }

    public InvalidPrivilegeException(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public InvalidPrivilegeException(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }

    public InvalidPrivilegeException(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }
    
        
}