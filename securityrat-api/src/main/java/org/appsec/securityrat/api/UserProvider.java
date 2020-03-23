package org.appsec.securityrat.api;

import java.util.Optional;
import org.appsec.securityrat.api.dto.User;
import org.appsec.securityrat.api.exception.ApiException;
import org.appsec.securityrat.api.exception.EmailAlreadyInUseException;
import org.appsec.securityrat.api.exception.NotActivatedException;
import org.appsec.securityrat.api.exception.UnknownEmailException;
import org.appsec.securityrat.api.exception.UnknownIdentifierException;
import org.appsec.securityrat.api.exception.UsernameTakenException;

/**
 * Provides access to the persistent {@link User} instances.
 * <p>
 * Please note that no method of this provider will include the value of
 * {@link User#password}. This attribute is one-way only.
 */
public interface UserProvider
        extends IdentifiableDtoProvider<Long, User> {
    
    Optional<User> findByLogin(String login);
    
    /**
     * Creates or updates the persistent representation of the passed
     * <code>dto</code>.
     * <p>
     * Changing a user's name is not supported. The following properties of the
     * passed <code>dto</code> will be ignored and not copied to the persistent
     * representation:
     * <ul>
     *   <li>{@link User#activated}</li>
     *   <li>{@link User#resetKey}</li>
     *   <li>{@link User#resetDate}</li>
     *   <li>{@link User#persistentTokens}</li>
     * </ul>
     * 
     * @param dto The data transfer object with the details of the new or
     *            updated user
     * 
     * @return The data transfer object that has been derived from the
     *         persistent representation, which has been created or updated.
     * 
     * @throws ApiException If the operation fails (e.g. due to a database
     *                      error).
     * 
     * @throws UsernameTakenException If the specified {@link User#login} is
     *                                already assigned to another user.
     * 
     * @throws EmailAlreadyInUseException If the specified {@link User#email} is
     *                                    already registered for another user.
     */
    @Override
    User save(User dto) throws ApiException,
            UsernameTakenException,
            EmailAlreadyInUseException;
    
    /**
     * Activates the {@link User} that is associated with the specified
     * <code>activationKey</code>.
     * 
     * @param activationKey The activation key that is associated with the
     *                      {@link User} that will be activated.
     * 
     * @return Either <code>true</code>, if the specified
     *         <code>activationKey</code> existed and the associated user got
     *         activated, otherwise <code>false</code>.
     * 
     * @throws ApiException If the operation fails.
     */
    boolean activate(String activationKey) throws ApiException;
    
    /**
     * Returns whether the specified <code>password</code> matches the
     * password of the {@link User} that is associated with the specified
     * <code>userId</code> or not.
     * 
     * @param userId The identifier of the user whose password is validated.
     * @param password The password that will be validated.
     * 
     * @return Either <code>true</code>, if the passed <code>password</code>
     *         matches the password that is associated with <code>userId</code>,
     *         otherwise <code>false</code>.
     * 
     * @throws UnknownIdentifierException If the passed <code>userId</code> is
     *                                    unknown.
     * 
     * @throws ApiException If the operation fails.
     */
    boolean confirmPassword(Long userId, String password) throws ApiException,
            UnknownIdentifierException;
    
    /**
     * Invalidates the specified
     * {@link org.appsec.securityrat.api.dto.rest.PersistentToken} that is
     * associated with <code>userId</code>.
     * 
     * @param userId The identifier of the user whose token is removed.
     * @param series The series of the token.
     * 
     * @return Either <code>true</code>, if the specified token was assigned to
     *         the current user and got deleted successfully, otherwise
     *         <code>false</code>.
     * 
     * @throws UnknownIdentifierException If the passed <code>userId</code> is
     *                                    unknown.
     * 
     * @throws ApiException If the operation fails.
     */
    boolean invalidateToken(Long userId, String series) throws ApiException,
            UnknownIdentifierException;
    
    /**
     * Requests resetting the password of the {@link User} that is associated
     * with the specified <code>email</code>.
     * 
     * @param email The email address that is assigned to the {@link User} whose
     *              password shall be reset.
     * 
     * @throws ApiException If the operation fails for another reason than an
     *                      invalid email or not activated account.
     * 
     * @throws UnknownEmailException If the specified <code>email</code> is not
     *                               associated with any existing account.
     * 
     * @throws NotActivatedException If the {@link Account} that is associated
     *                               with the specified <code>email</code> was
     *                               not activated yet.
     */
    void requestPasswordReset(String email) throws ApiException,
            UnknownEmailException,
            NotActivatedException;
    
    /**
     * Finishes the reset of an {@link User}'s password.
     * 
     * @param resetKey The key that has been sent to the user by invoking
     *                 {@link #requestPasswordReset(java.lang.String)}.
     * 
     * @param password The new password that has been specified by the user.
     * 
     * @return Either <code>true</code>, if resetting the password of the
     *         associated {@link Account} succeeded, otherwise
     *         <code>false</code>.
     * 
     * @throws ApiException If the operation fails.
     */
    boolean finishPasswordReset(String resetKey, String password)
            throws ApiException;
}
