package org.appsec.securityrat.api;

import java.util.List;

public interface FrontendDtoProvider<TDto> {
    List<TDto> findAll();
}
