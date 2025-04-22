package com.hosManSys.login.exception;

public class UserNotLoggedInException extends RuntimeException {
    
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

        public UserNotLoggedInException(String errorCode, String errorMessage) {
            super();
            this.errorCode = errorCode;
            this.errorMessage = errorMessage;
        }

        public UserNotLoggedInException() {
            super();
            // TODO Auto-generated constructor stub
        }

        public UserNotLoggedInException(String message, Throwable cause, boolean enableSuppression,
                boolean writableStackTrace) {
            super(message, cause, enableSuppression, writableStackTrace);
            // TODO Auto-generated constructor stub
        }

        public UserNotLoggedInException(String message, Throwable cause) {
            super(message, cause);
            // TODO Auto-generated constructor stub
        }

        public UserNotLoggedInException(String message) {
            super(message);
            // TODO Auto-generated constructor stub
        }

        public UserNotLoggedInException(Throwable cause) {
            super(cause);
            // TODO Auto-generated constructor stub
        }

        
}