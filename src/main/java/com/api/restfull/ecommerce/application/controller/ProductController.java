package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.product.ProductRequest;
import com.api.restfull.ecommerce.application.request.product.ProductUpRequest;
import com.api.restfull.ecommerce.application.response.product.ProductListResponse;
import com.api.restfull.ecommerce.application.response.product.ProductResponse;
import com.api.restfull.ecommerce.application.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductListResponse> createProduct(@Valid @RequestBody ProductRequest request) {
        ProductListResponse response = service.createProduct(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductListResponse> updateProduct(@Valid @RequestBody ProductUpRequest request) {
        return ResponseEntity.ok(service.updateProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductListResponse> getByIdProduct(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByIdProduct(id));
    }

    @GetMapping("/names")
    public ResponseEntity<ProductListResponse> getByNameProduct(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(service.getByNameProduct(name));
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<ProductResponse>> getAllPagedProducts(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllPagedProducts(page, size));
    }

    @GetMapping("/searchs")
    public ResponseEntity<List<ProductResponse>> searchByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(service.searchByName(name));
    }

    @GetMapping("/descriptions")
    public ResponseEntity<List<ProductResponse>> getByDescriptionProduct(@RequestParam(name = "description") String description) {
        return ResponseEntity.ok(service.getByDescriptionProduct(description));
    }

    @GetMapping("/prices")
    public ResponseEntity<List<ProductResponse>> getSortedByPriceProducts(@RequestParam(defaultValue = "asc") String price) {
        return ResponseEntity.ok(service.getSortedByPriceProducts(price));
    }

    @GetMapping("/quantitys")
    public ResponseEntity<List<ProductResponse>> getByQuantityProduct(@RequestParam(name = "quantityStock") Integer quantityStock) {
        return ResponseEntity.ok(service.getByQuantityProduct((quantityStock)));
    }

    @GetMapping("/actives")
    public ResponseEntity<List<ProductResponse>> getByStatusProducts(@RequestParam(name = "active") Boolean active) {
        return ResponseEntity.ok(service.getByStatusProducts(active));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> desableCategory(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.desableProduct(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    //BUSCA POR productS RELACIONADOS A category.
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<ProductListResponse>> getAllByIdCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(service.getAllByParamProducts(categoryId));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ProductListResponse>> getAllByParamCategory(@RequestParam(value = "category", defaultValue = "0") Long id_cat) {
        return ResponseEntity.ok().body(service.getAllByParamProducts(id_cat));
    }

    @GetMapping("/categories/names")
    public ResponseEntity<List<ProductListResponse>> getAllByParamNameCategory(@RequestParam(value = "category", defaultValue = "0") String namecategory) {
        return ResponseEntity.ok().body(service.getAllByParamNameProducts(namecategory));
    }


}

