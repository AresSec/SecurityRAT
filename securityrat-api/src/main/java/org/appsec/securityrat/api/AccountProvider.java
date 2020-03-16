package org.appsec.securityrat.api;

import java.util.Optional;
import org.appsec.securityrat.api.dto.Account;

public interface AccountProvider {
    /**
     * Looks for an {@link Account} that is associated with the specified
     * <code>login</code>.
     * 
     * @param login The login (aka username) of the searched {@link Account}.
     * @return An {@link Optional} container that either contains the searched
     *         {@link Account} or <code>null</code>, if there is no such
     *         {@link Account}.
     */
    Optional<Account> findOneByLogin(String login);
    
    /**
     * Looks for an {@link Account} that is associated with the specified
     * <code>email</code>.
     * 
     * @param email The email of the searched {@link Account}.
     * @return An {@link Optional} container that either contains the searched
     *         {@link Account} or <code>null</code>, if there is no such
     *         {@link Account}.
     */
    Optional<Account> findOneByEmail(String email);
    
    /**
     * Creates or updates the specified <code>account</code>.
     * 
     * If {@link Account#password} is <code>null</code>, it will not be updated.
     * 
     * @param account The {@link Account} that contains the updated information.
     * @return The same {@link Account} instance with the information that have
     *         actually been stored. The {@link Account#password} field will
     *         always be <code>null</code>.
     */
    Account save(Account account);
    
    /**
     * Activates the {@link Account} that is associated to the specified
     * <code>activationKey</code>.
     * 
     * @param activationKey The activation key that is associated with the
     *                      {@link Account} that will be activated.
     * @return Either <code>true</code>, if the activation succeeded, otherwise
     *         <code>false</code>.
     */
    boolean activate(String activationKey);
    
    /**
     * Returns the {@link Account} for the current requester.
     * 
     * @return An {@link Optional} container that either contains the
     *         {@link Account} object of the current requester or
     *         <code>null</code>, if he/she is anonymous.
     */
    Optional<Account> getCurrent();
    
    /**
     * Returns whether the specified <code>password</code> matches the
     * requester's password or not.
     * 
     * @param password The password that will be validated.
     * @return Either <code>true</code>, if the passed <code>password</code>
     *         matches the requester's password, otherwise <code>false</code>.
     */
    boolean confirmPassword(String password);
}
