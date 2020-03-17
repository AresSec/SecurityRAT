package org.appsec.securityrat.service;

import org.appsec.securityrat.domain.User;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    public void sendActivationMail(User user) {
        throw new UnsupportedOperationException();
    }
    
    public void sendPasswordResetMail(User user) {
        throw new UnsupportedOperationException();
    }
}
