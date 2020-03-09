package org.appsec.securityrat.api;

import java.util.List;
import java.util.Optional;
import org.appsec.securityrat.api.dto.IdentifiableDto;

public interface IdentifiableDtoProvider<
        TIdType, TDto extends IdentifiableDto<TIdType>>
        extends DtoProvider<TDto> {
    List<TDto> findAll();
    Optional<TDto> findById(TIdType id);
    TDto save(TDto dto);
    boolean delete(TIdType id);
    List<TDto> search(String query);
}
