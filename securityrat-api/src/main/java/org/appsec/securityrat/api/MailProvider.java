package org.appsec.securityrat.api;

import org.appsec.securityrat.api.dto.Account;

public interface MailProvider {
    void sendActivationEmail(Account account);
}
