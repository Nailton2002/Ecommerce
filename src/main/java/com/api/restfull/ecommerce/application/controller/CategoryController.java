package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import com.api.restfull.ecommerce.application.response.ProductResponse;
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
    public ResponseEntity<List<CategoryResponse>> findAllCategory(@RequestParam(required = false) String nome) {
        if (nome != null) {
            return ResponseEntity.ok(List.of(service.findByNameCategory(nome)));
        }
        return ResponseEntity.ok(service.findAllCategory());
    }

    @GetMapping("/descriptions")
    public ResponseEntity<List<CategoryResponse>> findByDescriptionCategory(@RequestParam(name = "descricao") String descricao) {
        return ResponseEntity.ok(service.findByDescriptionCategory(descricao));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> findByIdCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.findByIdCategory(id));
    }

    @GetMapping("/actives")
    public ResponseEntity<List<CategoryResponse>> finByActivesCategory(@RequestParam(name = "ativo") Boolean ativo) {
        return ResponseEntity.ok(service.finByActivesCategory(ativo));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @PathVariable Long id, @RequestBody CategoryRequest request) {
        return ResponseEntity.ok().body(service.updateCategory(id, request));
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
