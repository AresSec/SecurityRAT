package org.appsec.securityrat.api.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.appsec.securityrat.api.IdentifiableDtoProvider;
import org.appsec.securityrat.api.dto.IdentifiableDto;
import org.appsec.securityrat.api.util.HeaderUtil;
import org.springframework.http.HttpStatus;

@Slf4j
public abstract class AbstractResourceBase<
        TIdType, TDto extends IdentifiableDto<TIdType>> {
    private final String entityName;
    
    protected AbstractResourceBase(String entityName) {
        this.entityName = entityName;
    }
    
    protected abstract IdentifiableDtoProvider<TIdType, TDto> getDtoProvider();
    
    protected abstract URI getLocation(TDto dto) throws URISyntaxException;
    
    protected ResponseEntity<TDto> doCreate(TDto dto)
            throws URISyntaxException {
        log.debug("REST request to save {} : {}", this.entityName, dto);
        
        if (dto.getId().isPresent()) {
            return ResponseEntity.badRequest().header("Failure",
                    String.format(
                            "A new %s cannot already have an ID",
                            this.entityName)).body(null);
        }
        
        dto = this.getDtoProvider().save(dto);
        
        return ResponseEntity.created(this.getLocation(dto)).headers(
                HeaderUtil.createEntityCreationAlert(
                        this.entityName,
                        dto.getId().get().toString())).body(dto);
    }
    
    protected ResponseEntity<TDto> doUpdate(TDto dto)
            throws URISyntaxException {
        log.debug("REST request to update {} : {}", this.entityName, dto);
        
        if (dto.getId().isEmpty()) {
            return this.doCreate(dto);
        }
        
        dto = this.getDtoProvider().save(dto);
        
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(
                this.entityName, dto.getId().get().toString())).body(dto);
    }
    
    protected List<TDto> doGetAll() {
        log.debug("REST request to get all {}", this.entityName);
        return this.getDtoProvider().findAll();
    }
    
    protected ResponseEntity<TDto> doGet(TIdType id) {
        log.debug("REST request to get {} : {}", id);
        
        return this.getDtoProvider()
                .findById(id)
                .map(entity -> new ResponseEntity<>(entity, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    protected ResponseEntity<Void> doDelete(TIdType id) {
        log.debug("REST request to delete {} : {}", this.entityName, id);
        
        if (!this.getDtoProvider().delete(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return ResponseEntity.ok().headers(
                HeaderUtil.createEntityDeletionAlert(
                        this.entityName, id.toString())).build();
    }
    
    protected List<TDto> doSearch(String query) {
        return this.getDtoProvider().search(query);
    }
}
