package org.appsec.securityrat.api.exception;

/**
 * A checked exception that indicates that an operation could not take place
 * because the requester does not have the required rights.
 */
public class UnauthorizedContextException extends Exception {
    public UnauthorizedContextException() {
    }
    
    public UnauthorizedContextException(String message) {
        super(message);
    }
    
    public UnauthorizedContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedContextException(Throwable cause) {
        super(cause);
    }
}
