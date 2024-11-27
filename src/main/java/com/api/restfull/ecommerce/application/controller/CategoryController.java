package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.category.CategoryRequest;
import com.api.restfull.ecommerce.application.request.category.CategoryUpdateRequest;
import com.api.restfull.ecommerce.application.response.category.CategoryListResponse;
import com.api.restfull.ecommerce.application.response.category.CategoryResponse;
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
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse response = service.saveCategory(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAllCategory(@RequestParam(required = false) String name) {
        if (name != null) {
            return ResponseEntity.ok(List.of(service.findByNameCategory(name)));
        }
        return ResponseEntity.ok(service.findAllCategory());
    }

    @GetMapping("/descriptions")
    public ResponseEntity<List<CategoryResponse>> findByDescriptionCategory(@RequestParam(name = "description") String description) {
        return ResponseEntity.ok(service.findByDescriptionCategory(description));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryListResponse> findByIdCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findByIdCategory(id));
    }

    @GetMapping("/actives")
    public ResponseEntity<List<CategoryResponse>> finByActivesCategory(@RequestParam(name = "active") Boolean active) {
        return ResponseEntity.ok(service.finByActivesCategory(active));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody CategoryUpdateRequest request) {
        return ResponseEntity.ok().body(service.updateCategory(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> desableCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.desableCategory(id));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteDesableCategory(@PathVariable Long id) {
        service.deleteDesableCategory(id);
        return ResponseEntity.noContent().build();
    }
}
