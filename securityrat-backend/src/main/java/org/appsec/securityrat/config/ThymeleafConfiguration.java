package org.appsec.securityrat.config;

import org.apache.commons.codec.CharEncoding;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfiguration {
    @Bean
    public ClassLoaderTemplateResolver emailTemplateResolver() {
        ClassLoaderTemplateResolver emailTemplateResolver =
                new ClassLoaderTemplateResolver();
        
        emailTemplateResolver.setPrefix("mails/");
        emailTemplateResolver.setSuffix(".html");
        emailTemplateResolver.setTemplateMode(TemplateMode.HTML);
        emailTemplateResolver.setCharacterEncoding(CharEncoding.UTF_8);
        emailTemplateResolver.setOrder(1);
        
        return emailTemplateResolver;
    }
    
    @Bean
    public MessageSource emailMessageSource() {
        ReloadableResourceBundleMessageSource messageSource =
                new ReloadableResourceBundleMessageSource();
        
        messageSource.setBasename("classpath:/mails/messages/messages");
        messageSource.setDefaultEncoding(CharEncoding.UTF_8);
        
        return messageSource;
    }
}
