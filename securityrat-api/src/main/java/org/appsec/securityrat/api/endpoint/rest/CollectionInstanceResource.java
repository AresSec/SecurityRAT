package org.appsec.securityrat.api.endpoint.rest;

import java.util.List;
import java.util.Set;
import org.appsec.securityrat.api.dto.rest.CollectionInstanceDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CollectionInstanceResource
        extends SimpleResource<Long, CollectionInstanceDto> {
    
    public CollectionInstanceResource() {
        super(CollectionInstanceDto.class);
    }

    @Override
    @PostMapping("/collectionInstances")
    protected ResponseEntity<CollectionInstanceDto> create(
            @RequestBody CollectionInstanceDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping("/collectionInstances")
    protected ResponseEntity<CollectionInstanceDto> update(
            @RequestBody CollectionInstanceDto dto) {
        return super.update(dto);
    }

    @Override
    @GetMapping("/collectionInstances")
    protected ResponseEntity<Set<CollectionInstanceDto>> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/collectionInstances/{id}")
    protected ResponseEntity<CollectionInstanceDto> get(@PathVariable Long id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/collectionInstances/{id}")
    protected ResponseEntity<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    @GetMapping("/_search/collectionInstances/{query}")
    protected ResponseEntity<List<CollectionInstanceDto>> search(
            @PathVariable String query) {
        return super.search(query);
    }
}
