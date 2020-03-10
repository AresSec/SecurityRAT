package org.appsec.securityrat.api;

import java.util.List;
import org.appsec.securityrat.api.dto.frontend.Category;

public interface FrontendCategoryProvider
        extends FrontendDtoProvider<Category> {
    List<Category> findEagerlyCategoriesWithRequirements(
            Long[] collectionIds, Long[] projectTypeIds);
}
