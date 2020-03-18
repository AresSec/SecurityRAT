package org.appsec.securityrat.provider;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.FrontendCategoryProvider;
import org.appsec.securityrat.api.dto.frontend.Category;
import org.appsec.securityrat.api.dto.frontend.OptionColumnContent;
import org.appsec.securityrat.api.dto.frontend.Requirement;
import org.appsec.securityrat.domain.ReqCategory;
import org.appsec.securityrat.repository.ReqCategoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FrontendCategoryProviderImpl
        extends AbstractFrontendProviderImplementation<
            org.appsec.securityrat.api.dto.frontend.Category,
            org.appsec.securityrat.domain.ReqCategory>
        implements FrontendCategoryProvider {

    @Getter
    @Inject
    private ReqCategoryRepository repository;

    @Override
    @Transactional
    public List<Category> findEagerlyCategoriesWithRequirements(
            Long[] collectionIds,
            Long[] projectTypeIds) {
        // TODO [luis.felger@bosch.com]: Needs implementation
        
        return Collections.EMPTY_LIST;
    }

    @Override
    protected Category createDto(ReqCategory entity) {
        Category dto = new Category();
        
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setShortcut(entity.getShortcut());
        dto.setShowOrder(entity.getShowOrder());
        
        // NOTE: There are no dedicated providers for Requirement and
        //       OptionColumnContent.
        
        dto.setRequirements(entity.getRequirementSkeletons()
                .stream()
                .map(e -> {
                    Requirement rs = new Requirement();
                    
                    rs.setId(e.getId());
                    rs.setShortName(e.getShortName());
                    rs.setUniversalId(e.getUniversalId());
                    rs.setDescription(e.getDescription());
                    rs.setShowOrder(e.getShowOrder());
                    
                    rs.setOptionColumnContents(e.getOptColumnContents()
                            .stream()
                            .map(f -> {
                                OptionColumnContent cnt =
                                        new OptionColumnContent();
                                
                                cnt.setId(f.getId());
                                cnt.setOptionColumnId(f.getOptColumn().getId());
                                cnt.setContent(f.getContent());
                                cnt.setOptionColumnName(
                                        f.getOptColumn().getName());
                                
                                return cnt;
                            })
                            .collect(Collectors.toSet()));
                    
                    rs.setTagInstanceIds(e.getTagInstances()
                            .stream()
                            .map(f -> f.getId())
                            .collect(Collectors.toSet()));
                    
                    return rs;
                })
                .collect(Collectors.toSet()));
        
        return dto;
    }
}
