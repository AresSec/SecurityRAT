package org.appsec.securityrat.api;

import java.util.List;
import org.appsec.securityrat.api.dto.frontend.AlternativeInstance;

public interface FrontendAlternativeInstanceProvider
        extends FrontendDtoProvider<AlternativeInstance> {
    List<AlternativeInstance> getActiveAlternativeInstancesForAlternativeSet(
            Long alternativeSetId);
}
