package org.appsec.securityrat.mapper;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractMapperBase<TEntity, TDto> {
    public abstract TDto toDto(TEntity entity);
    public abstract TEntity toEntity(TDto dto);
    
    public List<TDto> toDtoList(List<TEntity> entities) {
        return entities.stream()
                .filter(Objects::nonNull)
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    public Set<TDto> toDtoSet(Set<TEntity> entities) {
        return entities.stream()
                .filter(Objects::nonNull)
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
    
    public List<TEntity> toEntityList(List<TDto> dtos) {
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
    
    public Set<TEntity> toEntitySet(Set<TDto> dtos) {
        return dtos.stream()
                .filter(Objects::nonNull)
                .map(this::toEntity)
                .collect(Collectors.toSet());
    }
}
