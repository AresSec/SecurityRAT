package org.appsec.securityrat.api.endpoint.rest;

import java.util.List;
import java.util.Set;
import org.appsec.securityrat.api.dto.rest.OptColumnDto;
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
public class OptColumnResource extends SimpleResource<Long, OptColumnDto> {
    public OptColumnResource() {
        super(OptColumnDto.class);
    }
    
    @Override
    @PostMapping("/optColumns")
    protected ResponseEntity<OptColumnDto> create(
            @RequestBody OptColumnDto dto) {
        return super.create(dto);
    }

    @Override
    @DeleteMapping("/optColumns/{id}")
    protected ResponseEntity<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    @GetMapping("/optColumns/{id}")
    protected ResponseEntity<OptColumnDto> get(@PathVariable Long id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/optColumns")
    protected ResponseEntity<Set<OptColumnDto>> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/_search/optColumns/{query}")
    protected ResponseEntity<List<OptColumnDto>> search(
            @PathVariable String query) {
        return super.search(query);
    }

    @Override
    @PutMapping("/optColumns")
    protected ResponseEntity<OptColumnDto> update(
            @RequestBody OptColumnDto dto) {
        return super.update(dto);
    }
}
