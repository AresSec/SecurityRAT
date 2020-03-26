package org.appsec.securityrat.api.test;

import org.appsec.securityrat.api.endpoint.rest.SimpleResource;
import org.appsec.securityrat.api.test.mock.PersistentStorageMock;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackageClasses = SimpleResource.class)
public class GeneralTestConfiguration {
    @Bean
    public PersistentStorageMock persistentStorageMock() {
        return new PersistentStorageMock();
    }
}
