package org.appsec.securityrat.api;

import java.util.List;
import java.util.Optional;
import org.appsec.securityrat.api.dto.IdentifiableDto;
import org.appsec.securityrat.api.exception.ApiException;

/**
 * A provider for a specific type of {@link IdentifiableDto}.
 * 
 * @param <TId> The type of the identifier.
 * @param <TDto> The type of the identifiable data transfer object.
 */
public interface IdentifiableDtoProvider<
        TId,
        TDto extends IdentifiableDto<TId>> {
    
    /**
     * Returns all different instances of the data transfer object type that
     * exist in the persistent storage.
     * 
     * @return All existing instances of the DTO type.
     */
    List<TDto> findAll();
    
    /**
     * Attempts to resolve an identifiable data transfer object by its
     * identifier.
     * 
     * @param id The identifier of the searched data transfer object.
     * 
     * @return A container object that either contains the searched DTO or
     *         <code>null</code>, if there is no DTO with the specified
     *         identifier in the persistent storage.
     */
    Optional<TDto> findById(TId id);
    
    /**
     * Creates or updates the persistent representation of the passed
     * <code>dto</code>.
     * 
     * @param dto The new or modified data transfer object.
     * 
     * @return The data transfer object that has been derived from the
     *         persistent representation after the creation or update.
     * 
     * @throws ApiException If the persistent representation of the passed
     *                      <code>dto</code> cannot be updated with the provided
     *                      information due to limitations of the storage (e.g.
     *                      if a unique value would appear twice).
     */
    TDto save(TDto dto) throws ApiException;
    
    /**
     * Attempts to delete the persistent representation of the identifiable
     * data transfer object that is associated with the passed <code>id</code>
     * from the persistent storage.
     * 
     * @param id The identifier that is associated with the data transfer object
     *           whose persistent representation shall be removed from the
     *           persistent storage.
     * 
     * @return Either <code>true</code>, if the persistent representation had
     *         existed and was removed, otherwise <code>false</code>.
     * 
     * @throws ApiException If removing the persistent representation of the DTO
     *                      would make the persistent storage inconsistent
     *                      (e.g. foreign keys).
     */
    boolean delete(TId id) throws ApiException;
    
    /**
     * Searches for data transfer objects that are associated with the specified
     * specified <code>query</code> by using Elasticsearch.
     * 
     * @param query The search query.
     * @return The result set.
     */
    List<TDto> search(String query);
}
