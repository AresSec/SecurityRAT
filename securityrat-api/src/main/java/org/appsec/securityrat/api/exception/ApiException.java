package org.appsec.securityrat.api.exception;

/**
 * Represents an exception that may occur in a component of the SecurityRAT API.
 */
public class ApiException extends Exception {
    public ApiException() {
    }
    
    public ApiException(String message) {
        super(message);
    }
    
    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }
}
