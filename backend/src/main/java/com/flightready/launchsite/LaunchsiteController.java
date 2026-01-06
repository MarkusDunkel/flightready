package com.flightready.launchsite;

import com.flightready.launchsite.dto.LaunchsiteRequest;
import com.flightready.launchsite.dto.LaunchsiteResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/launchsites")
public class LaunchsiteController {
    private final LaunchsiteService service;

    public LaunchsiteController(LaunchsiteService service) {
        this.service = service;
    }

    @GetMapping
    public List<LaunchsiteResponse> list() {
        return service.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LaunchsiteResponse create(@Valid @RequestBody LaunchsiteRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public LaunchsiteResponse update(@PathVariable("id") UUID id, @Valid @RequestBody LaunchsiteRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") UUID id) {
        service.delete(id);
    }
}
