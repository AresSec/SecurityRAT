package org.appsec.securityrat.api.endpoint.rest;

import java.util.List;
import java.util.Set;
import org.appsec.securityrat.api.dto.rest.SlideTemplateDto;
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
public class SlideTemplateResource
        extends SimpleResource<Long, SlideTemplateDto> {
    
    public SlideTemplateResource() {
        super(SlideTemplateDto.class);
    }

    @Override
    @PostMapping("/slideTemplates")
    protected ResponseEntity<SlideTemplateDto> create(
            @RequestBody SlideTemplateDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping("/slideTemplates")
    protected ResponseEntity<SlideTemplateDto> update(
            @RequestBody SlideTemplateDto dto) {
        return super.update(dto);
    }

    @Override
    @GetMapping("/slideTemplates")
    protected ResponseEntity<Set<SlideTemplateDto>> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/slideTemplates/{id}")
    protected ResponseEntity<SlideTemplateDto> get(@PathVariable Long id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/slideTemplates/{id}")
    protected ResponseEntity<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    @GetMapping("/_search/slideTemplates/{query}")
    protected ResponseEntity<List<SlideTemplateDto>> search(
            @PathVariable String query) {
        return super.search(query);
    }
}
