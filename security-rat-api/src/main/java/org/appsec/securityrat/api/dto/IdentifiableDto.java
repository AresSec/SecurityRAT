package org.appsec.securityrat.api.dto;

import java.util.Optional;

/**
 * A data transfer object that may provide a unique id.
 * 
 * @param <TIdType> The type of the unique id.
 */
public interface IdentifiableDto<TIdType> {
    /**
     * Returns the unique id.
     * 
     * The unique identity of the DTO may be <code>null</code>, if the DTO
     * itself is a draft.
     * 
     * @return The unique id.
     */
    Optional<TIdType> getId();
}
