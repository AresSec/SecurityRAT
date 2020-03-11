package org.appsec.securityrat.config;

import org.appsec.securityrat.security.*;

import io.github.jhipster.config.JHipsterProperties;
import io.github.jhipster.security.*;
import javax.inject.Inject;
import org.appsec.securityrat.api.AuthoritiesConstants;
import org.appsec.securityrat.config.filter.CsrfCookieGeneratorFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer.AuthorizedUrl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.filter.CorsFilter;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@Import(SecurityProblemSupport.class)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Inject
    private JHipsterProperties jHipsterProperties;
    
    @Inject
    private RememberMeServices rememberMeServices;
    
    @Inject
    private CorsFilter corsFilter;
    
    @Inject
    private SecurityProblemSupport problemSupport;
    
    @Inject
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;
    
    @Inject
    private ApplicationProperties appConfig;

    @Bean
    public AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler() {
        return new AjaxAuthenticationSuccessHandler();
    }

    @Bean
    public AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler() {
        return new AjaxAuthenticationFailureHandler();
    }

    @Bean
    public AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler() {
        return new AjaxLogoutSuccessHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        
        // TODO: At the moment only FORM authentication is implemented;
        //       reimplement CAS, too.
        
        // TODO: Probably, it would be better, if we would outsource the
        //       authentication endpoints to the API project.
        
        http
                .headers().frameOptions().sameOrigin()
            .and()
                .csrf()
                .ignoringAntMatchers("/websocket/**")
            .and()
                .addFilterAfter(
                        new CsrfCookieGeneratorFilter(),
                        CsrfFilter.class)
                    .exceptionHandling()
                    .authenticationEntryPoint(this.authenticationEntryPoint)
            .and()
                .rememberMe()
                .rememberMeServices(this.rememberMeServices)
                .rememberMeParameter("remember-me")
                .key(this.jHipsterProperties.getSecurity()
                        .getRememberMe()
                        .getKey())
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/api/authentication")
                .successHandler(this.ajaxAuthenticationSuccessHandler())
                .failureHandler(this.ajaxAuthenticationFailureHandler())
                .usernameParameter("j_username")
                .passwordParameter("j_password")
                .permitAll()
            .and()
                .logout()
                .logoutUrl("/api/logout")
                .logoutSuccessHandler(this.ajaxLogoutSuccessHandler())
                .deleteCookies("JSESSIONID")
                .permitAll();
        
        AuthorizedUrl registerUrl = http.authorizeRequests()
                .antMatchers("/api/register");
        
        if (this.appConfig.getAuthentication().isRegistration()) {
            registerUrl.permitAll();
        } else {
            registerUrl.denyAll();
        }
        
        http
            .authorizeRequests()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/authentication_config").permitAll()
            .antMatchers("/api/account/reset_password/init").permitAll()
            .antMatchers("/api/account/reset_password/finish").permitAll()
            .antMatchers("/api/account").authenticated()
            .antMatchers("/api/account/**").authenticated()
            .antMatchers(HttpMethod.GET, "/frontend-api/**").hasAnyAuthority(AuthoritiesConstants.FRONTEND_USER, AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER)
            .antMatchers("/api/training/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/trainings/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/trainingBranchNodes/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/trainingCategoryNodes/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/trainingCustomSlideNodes/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/trainingGeneratedSlideNodes/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/trainingRequirementNodes/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/trainingTreeNodes/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/trainingTreeNodeUpdate/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/trainingTreeNodesWithPreparedContent/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/trainingTreeNode/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/slideTemplates/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.TRAINER)
            .antMatchers("/api/**").hasAnyAuthority(AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER)
            .antMatchers(HttpMethod.GET, "/admin-api/configConstants").permitAll()
            .antMatchers("/admin-api/**").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/configuration/security").permitAll()
            .antMatchers("/configuration/ui").permitAll()
            .antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
            .antMatchers("/protected/**").authenticated();
        
        /*
        http
            .csrf()
            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
        .and()
            .addFilterBefore(corsFilter, CsrfFilter.class)
            .exceptionHandling()
                .authenticationEntryPoint(problemSupport)
                .accessDeniedHandler(problemSupport)
        .and()
            .rememberMe()
            .rememberMeServices(rememberMeServices)
            .rememberMeParameter("remember-me")
            .key(jHipsterProperties.getSecurity().getRememberMe().getKey())
        .and()
            .formLogin()
            .loginProcessingUrl("/api/authentication")
            .successHandler(ajaxAuthenticationSuccessHandler())
            .failureHandler(ajaxAuthenticationFailureHandler())
            .permitAll()
        .and()
            .logout()
            .logoutUrl("/api/logout")
            .logoutSuccessHandler(ajaxLogoutSuccessHandler())
            .permitAll()
        .and()
            .headers()
            .contentSecurityPolicy("default-src 'self'; frame-src 'self' data:; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://storage.googleapis.com; style-src 'self' 'unsafe-inline'; img-src 'self' data:; font-src 'self' data:")
        .and()
            .referrerPolicy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN)
        .and()
            .featurePolicy("geolocation 'none'; midi 'none'; sync-xhr 'none'; microphone 'none'; camera 'none'; magnetometer 'none'; gyroscope 'none'; speaker 'none'; fullscreen 'self'; payment 'none'")
        .and()
            .frameOptions()
            .deny()
        .and()
            .authorizeRequests()
            .antMatchers("/api/authenticate").permitAll()
            .antMatchers("/api/authentication_config").permitAll()
            .antMatchers("/api/register").permitAll()
            .antMatchers("/api/activate").permitAll()
            .antMatchers("/api/account/reset-password/init").permitAll()
            .antMatchers("/api/account/reset-password/finish").permitAll()
            .antMatchers("/api/**").authenticated()
            .antMatchers("/management/health").permitAll()
            .antMatchers("/management/info").permitAll()
            .antMatchers("/management/prometheus").permitAll()
            .antMatchers("/management/**").hasAuthority(AuthoritiesConstants.ADMIN);
        */
        // @formatter:on
    }
}
