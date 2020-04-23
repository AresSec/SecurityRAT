package org.appsec.securityrat.api.endpoint.rest;

import java.util.List;
import java.util.Set;
import org.appsec.securityrat.api.dto.rest.ProjectTypeDto;
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
public class ProjectTypeResource extends SimpleResource<Long, ProjectTypeDto> {
    public ProjectTypeResource() {
        super(ProjectTypeDto.class);
    }

    @Override
    @PostMapping("/projectTypes")
    protected ResponseEntity<ProjectTypeDto> create(
            @RequestBody ProjectTypeDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping("/projectTypes")
    protected ResponseEntity<ProjectTypeDto> update(
            @RequestBody ProjectTypeDto dto) {
        return super.update(dto);
    }

    @Override
    @GetMapping("/projectTypes")
    protected ResponseEntity<Set<ProjectTypeDto>> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/projectTypes/{id}")
    protected ResponseEntity<ProjectTypeDto> get(@PathVariable Long id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/projectTypes/{id}")
    protected ResponseEntity<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    @GetMapping("/_search/projectTypes/{query}")
    protected ResponseEntity<List<ProjectTypeDto>> search(
            @PathVariable String query) {
        return super.search(query);
    }
}
