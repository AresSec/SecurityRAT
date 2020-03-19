package org.appsec.securityrat.api;

import java.util.List;
import org.appsec.securityrat.api.dto.Account;
import org.appsec.securityrat.api.dto.PersistentToken;
import org.appsec.securityrat.api.exception.ApiException;
import org.appsec.securityrat.api.exception.EmailAlreadyInUseException;
import org.appsec.securityrat.api.exception.NotActivatedException;
import org.appsec.securityrat.api.exception.UnauthorizedContextException;
import org.appsec.securityrat.api.exception.UnknownEmailException;
import org.appsec.securityrat.api.exception.UsernameTakenException;

/**
 * This interface provides access to the requester's {@link Account}.
 * 
 * All declared methods of this interface can only be used to access information
 * of the requester's {@link Account}. For the modification of other users, use
 * the {@link UserProvider} interface. The reason for the separation of these
 * interfaces is an easier and cleaner access management.
 */
public interface AccountProvider {
    /**
     * Updates the specified <code>account</code>.
     * 
     * If {@link Account#password} is <code>null</code>, the password will not
     * be updated.
     * 
     * This method will also ignore any modifications of the
     * {@link Account#roles} set as users are not allowed to change their own
     * roles via this API.
     * 
     * @param account The {@link Account} that contains the updated information.
     * 
     * @return The same {@link Account} instance with the information that have
     *         actually been stored. The {@link Account#password} field will
     *         always be <code>null</code>.
     *         If the account does not exist, calling this method will have no
     *         effect and it will return with <code>null</code>.
     * 
     * @throws EmailAlreadyInUseException If the {@link Account#email} changed
     *                                    and another {@link Account} object
     *                                    uses the same one.
     * 
     * @throws UnauthorizedContextException If the current context/requester is
     *                                      anonymous or attempts to modify the
     *                                      account details of another user.
     */
    Account save(Account account)
            throws ApiException,
                EmailAlreadyInUseException,
                UnauthorizedContextException;
    
    /**
     * Creates the specified <code>account</code>.
     * 
     * Filling the {@link Account#roles} set will have no effect as that
     * property is completely ignored.
     * 
     * @param account The {@link Account} that contains the updated information.
     * 
     * @return The same {@link Account} instance as passed to the function, but
     *         only with the information that have actually been stored. The
     *         {@link Account#password} field will always be <code>null</code>.
     * 
     * @throws UsernameTakenException If the chosen username is already assigned
     *                                to another existing {@link Account}.
     * 
     * @throws EmailAlreadyInUseException If the specified email address was
     *                                    already specified for another
     *                                    {@link Account}.
     */
    Account create(Account account)
            throws ApiException,
                UsernameTakenException,
                EmailAlreadyInUseException;
    
    /**
     * Activates the {@link Account} that is associated to the specified
     * <code>activationKey</code>.
     * 
     * @param activationKey The activation key that is associated with the
     *                      {@link Account} that will be activated.
     * 
     * @return Either <code>true</code>, if the specified
     *         <code>activationKey</code> existed and the associated user got
     *         activated, otherwise <code>false</code>.
     */
    boolean activate(String activationKey);
    
    /**
     * Returns the {@link Account} for the current requester.
     * 
     * @return The {@link Account} object of the current requester.
     * 
     * @throws UnauthorizedContextException If the current requester is
     *                                      anonymous and was not authenticated.
     */
    Account getCurrent()
            throws UnauthorizedContextException;
    
    /**
     * Returns whether the specified <code>password</code> matches the
     * requester's password or not.
     * 
     * @param password The password that will be validated.
     * 
     * @return Either <code>true</code>, if the passed <code>password</code>
     *         matches the requester's password, otherwise <code>false</code>.
     * 
     * @throws UnauthorizedContextException If the current requester is
     *                                      anonymous and was not authenticated.
     */
    boolean confirmPassword(String password)
            throws UnauthorizedContextException;
    
    /**
     * Returns all valid {@link PersistentToken} for the current requester.
     * 
     * @return The valid tokens.
     * 
     * @throws UnauthorizedContextException If the current requester is
     *                                      anonymous and was not authenticated.
     */
    List<PersistentToken> getCurrentTokens()
            throws UnauthorizedContextException;
    
    /**
     * Invalidates the specified {@link PersistentToken}.
     * 
     * @param series The series of the token.
     * 
     * @return Either <code>true</code>, if the specified token was assigned to
     *         the current requester and got deleted successfully, otherwise
     *         <code>false</code>.
     * 
     * @throws UnauthorizedContextException If the current requester is
     *                                      anonymous and was not authenticated.
     */
    boolean invalidateToken(String series)
            throws UnauthorizedContextException;
    
    /**
     * Requests resetting the password of the {@link Account} that is associated
     * with the specified <code>email</code>.
     * 
     * @param email The email address that is assigned to the {@link Account}
     *              whose password shall be reset.
     * 
     * @throws UnknownEmailException If the specified <code>email</code> is not
     *                               associated with any existing account.
     * 
     * @throws NotActivatedException If the {@link Account} that is associated
     *                               with the specified <code>email</code> was
     *                               not activated yet.
     */
    void requestPasswordReset(String email)
            throws UnknownEmailException, NotActivatedException;
    
    /**
     * Finishes the reset of an {@link Account}'s password.
     * 
     * @param resetKey The key that has been sent to the user by invoking
     *                 {@link #requestPasswordReset(java.lang.String)}.
     * 
     * @param password The new password that has been specified by the user.
     * 
     * @return Either <code>true</code>, if resetting the password of the
     *         associated {@link Account} succeeded, otherwise
     *         <code>false</code>.
     */
    boolean finishPasswordReset(String resetKey, String password);
}
