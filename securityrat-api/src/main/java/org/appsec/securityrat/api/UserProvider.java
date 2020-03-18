package org.appsec.securityrat.api;

import java.util.Optional;
import org.appsec.securityrat.api.dto.User;

public interface UserProvider extends IdentifiableDtoProvider<Long, User> {
    Optional<User> findByLogin(String login);
}
