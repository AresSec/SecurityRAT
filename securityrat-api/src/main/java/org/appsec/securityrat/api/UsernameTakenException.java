package org.appsec.securityrat.api;

/**
 * An exception that indicates that a username was already chosen by another
 * user.
 * 
 * @see AccountProvider
 * @see UserProvider
 */
public class UsernameTakenException extends Exception {
    public UsernameTakenException() {
    }
    
    public UsernameTakenException(String message) {
        super(message);
    }
    
    public UsernameTakenException(String message, Throwable cause) {
        super(message, cause);
    }

    public UsernameTakenException(Throwable cause) {
        super(cause);
    }
}
