package org.appsec.securityrat.api.endpoint.rest;

import java.util.List;
import java.util.Set;
import org.appsec.securityrat.api.dto.rest.TrainingDto;
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
public class TrainingResource extends SimpleResource<Long, TrainingDto> {
    public TrainingResource() {
        super(TrainingDto.class);
    }

    @Override
    @PostMapping("/trainings")
    protected ResponseEntity<TrainingDto> create(@RequestBody TrainingDto dto) {
        return super.create(dto);
    }

    @Override
    @PutMapping("/trainings")
    protected ResponseEntity<TrainingDto> update(@RequestBody TrainingDto dto) {
        return super.update(dto);
    }

    @Override
    @GetMapping("/trainings")
    protected ResponseEntity<Set<TrainingDto>> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/trainings/{id}")
    protected ResponseEntity<TrainingDto> get(@PathVariable Long id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/trainings/{id}")
    protected ResponseEntity<Void> delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    @GetMapping("/_search/trainings/{query}")
    protected ResponseEntity<List<TrainingDto>> search(
            @PathVariable String query) {
        return super.search(query);
    }
}
