package org.appsec.securityrat.provider;

import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import lombok.Getter;
import org.appsec.securityrat.api.FrontendCategoryProvider;
import org.appsec.securityrat.api.dto.frontend.Category;
import org.appsec.securityrat.mapper.FrontendCategoryMapper;
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
    private ReqCategoryRepository repo;

    @Getter
    @Inject
    private FrontendCategoryMapper mapper;

    @Override
    @Transactional
    public List<Category> findEagerlyCategoriesWithRequirements(
            Long[] collectionIds,
            Long[] projectTypeIds) {
        // TODO: Needs implementation
        
        return this.mapper.toDtoList(Collections.EMPTY_LIST);
    }
}
