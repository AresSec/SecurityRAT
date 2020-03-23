package org.appsec.securityrat.api.exception;

public class UnknownIdentifierException extends ApiException {
    public UnknownIdentifierException() {
    }
    
    public UnknownIdentifierException(String message) {
        super(message);
    }
    
    public UnknownIdentifierException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownIdentifierException(Throwable cause) {
        super(cause);
    }
}
