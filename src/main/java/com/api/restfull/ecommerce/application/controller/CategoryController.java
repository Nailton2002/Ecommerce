package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import com.api.restfull.ecommerce.application.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/categorys")
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll(@RequestParam(required = false) String nome) {
        if (nome != null) {
            return ResponseEntity.ok(List.of(service.findByNameCategory(nome)));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> update(@Valid @PathVariable Long id, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok().body(service.update(id, request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> desableCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.desableCategory(id));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findById(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.deleteCategoryDesable(id);
        return ResponseEntity.noContent().build();
    }
}
