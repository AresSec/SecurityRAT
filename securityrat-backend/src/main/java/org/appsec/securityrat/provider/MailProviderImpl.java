package org.appsec.securityrat.provider;

import org.appsec.securityrat.api.MailProvider;
import org.appsec.securityrat.api.dto.Account;
import org.springframework.stereotype.Service;

@Service
public class MailProviderImpl implements MailProvider {
    @Override
    public void sendActivationEmail(Account account) {
        throw new UnsupportedOperationException();
    }
}
