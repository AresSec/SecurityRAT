package org.appsec.securityrat.api.endpoint.rest;

import java.util.List;
import java.util.Set;
import org.appsec.securityrat.api.dto.rest.ReqCategory;
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
public class ReqCategoryResource extends SimpleResource<Long, ReqCategory> {
    public ReqCategoryResource() {
        super(ReqCategory.class);
    }

    @Override
    @PostMapping("/reqCategorys")
    protected ResponseEntity<ReqCategory> create(@RequestBody ReqCategory dto) {
        return super.create(dto);
    }

    @Override
    @DeleteMapping("/reqCategorys/{id}")
    protected ResponseEntity<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    @GetMapping("/reqCategorys/{id}")
    protected ResponseEntity<ReqCategory> get(@PathVariable Long id) {
        return super.get(id);
    }

    @Override
    @GetMapping("/reqCategorys")
    protected ResponseEntity<Set<ReqCategory>> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/_search/reqCategorys/{query}")
    protected ResponseEntity<List<ReqCategory>> search(
            @PathVariable String query) {
        return super.search(query);
    }

    @Override
    @PutMapping("/reqCategorys")
    protected ResponseEntity<ReqCategory> update(@RequestBody ReqCategory dto) {
        return super.update(dto);
    }
}
