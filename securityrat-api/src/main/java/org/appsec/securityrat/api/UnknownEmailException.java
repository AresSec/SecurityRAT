package org.appsec.securityrat.api;

/**
 * An exception that indicates that a specified email address is unknown to the
 * application.
 * 
 * @see AccountProvider#requestPasswordReset(java.lang.String)
 */
public class UnknownEmailException extends Exception {
    public UnknownEmailException() {
    }
    
    public UnknownEmailException(String message) {
        super(message);
    }
    
    public UnknownEmailException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnknownEmailException(Throwable cause) {
        super(cause);
    }
}
