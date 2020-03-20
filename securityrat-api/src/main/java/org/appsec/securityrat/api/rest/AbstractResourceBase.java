package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.appsec.securityrat.api.IdentifiableDtoProvider;
import org.appsec.securityrat.api.dto.IdentifiableDto;
import org.appsec.securityrat.api.exception.ApiException;
import org.appsec.securityrat.api.util.HeaderUtil;
import org.springframework.http.HttpStatus;

@Slf4j
public abstract class AbstractResourceBase<
        TId,
        TDto extends IdentifiableDto<TId>> {
    
    private final String entityName;
    
    @Inject
    @Getter(AccessLevel.PROTECTED)
    private IdentifiableDtoProvider<TId, TDto> dtoProvider;
    
    protected AbstractResourceBase(String entityName) {
        this.entityName = entityName;
    }
    
    protected abstract URI getLocation(TDto dto) throws URISyntaxException;
    
    protected ResponseEntity<?> doCreate(TDto dto)
            throws URISyntaxException {
        log.debug("REST request to save {} : {}", this.entityName, dto);
        
        if (dto.getId().isPresent()) {
            return ResponseEntity.badRequest().header("Failure",
                    String.format(
                            "A new %s cannot already have an ID",
                            this.entityName)).body(null);
        }
        
        try {
            dto = this.getDtoProvider().save(dto);
        } catch (ApiException ex) {
            return this.handleException(ex);
        }
        
        return ResponseEntity.created(this.getLocation(dto)).headers(
                HeaderUtil.createEntityCreationAlert(
                        this.entityName,
                        dto.getId().get().toString())).body(dto);
    }
    
    protected ResponseEntity<?> doUpdate(TDto dto)
            throws URISyntaxException {
        log.debug("REST request to update {} : {}", this.entityName, dto);
        
        if (dto.getId().isEmpty()) {
            return this.doCreate(dto);
        }
        
        try {
            dto = this.getDtoProvider().save(dto);
        } catch (ApiException ex) {
            return this.handleException(ex);
        }
        
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(
                this.entityName, dto.getId().get().toString())).body(dto);
    }
    
    protected ResponseEntity<?> doGetAll() {
        log.debug("REST request to get all {}", this.entityName);
        return ResponseEntity.ok(this.getDtoProvider().findAll());
    }
    
    protected ResponseEntity<?> doGet(TId id) {
        log.debug("REST request to get {} : {}", id);
        
        return this.getDtoProvider()
                .findById(id)
                .map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    protected ResponseEntity<?> doDelete(TId id) {
        log.debug("REST request to delete {} : {}", this.entityName, id);
        
        boolean deleted;
        
        try {
            deleted = this.getDtoProvider().delete(id);
        } catch (ApiException ex) {
            return this.handleException(ex);
        }
        
        if (!deleted) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityDeletionAlert(
                        this.entityName, id.toString())).build();
    }
    
    protected ResponseEntity<?> doSearch(String query) {
        return ResponseEntity.ok(this.getDtoProvider().search(query));
    }
    
    protected ResponseEntity<?> handleException(ApiException ex) {
        // NOTE: A general exception handler should not respond with a 4xx error
        //       since we do not know whether the exception was caused by an
        //       invalid client request or by a fault on the server side.
        
        log.warn("Exception occurred while handling request", ex);
        
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
