package org.appsec.securityrat.api;

/**
 * An exception that indicates that the chosen email is already in use by
 * another user.
 * 
 * @see AccountProvider
 * @see UserProvider
 */
public class EmailAlreadyInUseException extends Exception {
    public EmailAlreadyInUseException() {
    }
    
    public EmailAlreadyInUseException(String message) {
        super(message);
    }
    
    public EmailAlreadyInUseException(String message, Throwable cause) {
        super(message, cause);
    }

    public EmailAlreadyInUseException(Throwable cause) {
        super(cause);
    }
}
