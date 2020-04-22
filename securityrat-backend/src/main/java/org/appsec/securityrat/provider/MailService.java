package org.appsec.securityrat.provider;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.appsec.securityrat.domain.User;
import org.springframework.context.MessageSource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Service
@Slf4j
public class MailService {
    @Inject
    private JavaMailSenderImpl javaMailSender;
    
    @Inject
    private MessageSource messageSource;

    @Inject
    private SpringTemplateEngine templateEngine;
    
    public void sendActivationEmail(User user) {
        // TODO [luis.felger@bosch.com]: Implementation
    }
    
    public void sendActivationPassword(User user, String password) {
        // TODO [luis.felger@bosch.com]: Implementation
    }
    
    public void sendPasswordResetMail(User user) {
        // TODO [luis.felger@bosch.com]: Implementation
    }
}
