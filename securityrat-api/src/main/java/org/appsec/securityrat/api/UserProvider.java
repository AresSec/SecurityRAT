package org.appsec.securityrat.api;

import java.util.Optional;
import org.appsec.securityrat.api.dto.User;
import org.appsec.securityrat.api.exception.ApiException;
import org.appsec.securityrat.api.exception.EmailAlreadyInUseException;
import org.appsec.securityrat.api.exception.UsernameTakenException;

public interface UserProvider extends IdentifiableDtoProvider<Long, User> {
    Optional<User> findByLogin(String login);
    
    @Override
    User save(User dto)
            throws ApiException,
                UsernameTakenException,
                EmailAlreadyInUseException;
}
