package org.appsec.securityrat.api.dto.rest;

import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.appsec.securityrat.api.dto.IdentifiableDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Training implements IdentifiableDto<Long> {
    @Getter(AccessLevel.NONE)
    private Long id;
    private String name;
    private String description;
    private Boolean allRequirementsSelected;
    private Set<OptColumn> optColumns;
    private Set<CollectionInstance> collections;
    private Set<ProjectType> projectTypes;

    @Override
    public Optional<Long> getId() {
        return Optional.ofNullable(this.id);
    }
}
