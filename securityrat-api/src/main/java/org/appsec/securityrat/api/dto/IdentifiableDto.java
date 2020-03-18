package org.appsec.securityrat.api.dto;

import java.util.Optional;

/**
 * A data transfer object that may provide a unique id.
 * 
 * @param <TId> The type of the unique id.
 */
public interface IdentifiableDto<TId> {
    /**
     * Returns the unique id.
     * 
     * The unique identity of the DTO may be <code>null</code>, if the DTO
     * itself is a draft.
     * 
     * @return The unique id.
     */
    Optional<TId> getId();
}
