package org.appsec.securityrat.provider.mapper.training;

import org.appsec.securityrat.api.dto.training.TrainingTreeNodeDto;
import org.appsec.securityrat.domain.TrainingTreeNode;
import org.appsec.securityrat.provider.mapper.IdentifiableMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingTreeNodeMapper
        extends IdentifiableMapper<Long, TrainingTreeNode, TrainingTreeNodeDto> {
}
