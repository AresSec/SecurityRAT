package org.appsec.securityrat.api.endpoint.rest;

import java.util.List;
import java.util.Set;
import javax.annotation.security.RolesAllowed;
import org.appsec.securityrat.api.dto.rest.ConfigConstantDto;
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
@RequestMapping("/admin-api")
public class ConfigConstantResource
        extends SimpleResource<Long, ConfigConstantDto> {

    public ConfigConstantResource() {
        super(ConfigConstantDto.class);
    }

    @Override
    @PostMapping("/configConstants")
    protected ResponseEntity<ConfigConstantDto> create(
            @RequestBody ConfigConstantDto dto) {
        return super.create(dto);
    }

    @Override
    @DeleteMapping("/configConstants/{id}")
    protected ResponseEntity<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    @GetMapping("/configConstants/{id}")
    protected ResponseEntity<ConfigConstantDto> get(@PathVariable Long id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/configConstants")
    protected ResponseEntity<Set<ConfigConstantDto>> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/_search/configConstants/{query}")
    protected ResponseEntity<List<ConfigConstantDto>> search(
            @PathVariable String query) {
        return super.search(query);
    }

    @Override
    @PutMapping("/configConstants")
    protected ResponseEntity<ConfigConstantDto> update(
            @RequestBody ConfigConstantDto dto) {
        return super.update(dto);
    }
}
