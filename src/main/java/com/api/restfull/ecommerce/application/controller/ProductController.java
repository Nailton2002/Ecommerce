package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.ProductRequest;
import com.api.restfull.ecommerce.application.response.ProductResponse;
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
    public ResponseEntity<ProductResponse> createProduct( @Valid @RequestBody ProductRequest request) {
        ProductResponse response = service.createProduct(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.updateProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getByIdProduct(@PathVariable Long id) {
        return ResponseEntity.ok(service.getByIdProduct(id));
    }

    @GetMapping("/names")
    public ResponseEntity<ProductResponse> getByNameProduct(@RequestParam(name = "nome") String nome) {
        return ResponseEntity.ok(service.getByNameProduct(nome));
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<ProductResponse>> getAllPagedProducts(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllPagedProducts(page, size));
    }

    @GetMapping("/searchs")
    public ResponseEntity<List<ProductResponse>> searchByName(@RequestParam(name = "nome") String nome) {
        return ResponseEntity.ok(service.searchByName(nome));
    }

    @GetMapping("/descriptions")
    public ResponseEntity<List<ProductResponse>> getByDescriptionProduct(@RequestParam(name = "descricao") String descricao) {
        return ResponseEntity.ok(service.getByDescriptionProduct(descricao));
    }

    @GetMapping("/prices")
    public ResponseEntity<List<ProductResponse>> getSortedByPriceProducts(@RequestParam(defaultValue = "asc") String preco) {
        return ResponseEntity.ok(service.getSortedByPriceProducts(preco));
    }

    @GetMapping("/quantitys")
    public ResponseEntity<List<ProductResponse>> getByQuantityProduct(@RequestParam(name = "quantidadeEstoque") Integer quantidadeEstoque) {
        return ResponseEntity.ok(service.getByQuantityProduct((quantidadeEstoque)));
    }

    @GetMapping("/actives")
    public ResponseEntity<List<ProductResponse>> getByStatusProducts(@RequestParam(name = "ativo") Boolean ativo) {
        return ResponseEntity.ok(service.getByStatusProducts(ativo));
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

    //BUSCA POR PRODUTOS RELACIONADOS A CATEGORIA.
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<ProductResponse>> getAllByIdCategory(@PathVariable Long categoryId) {
        return ResponseEntity.ok().body(service.getAllByParamProducts(categoryId));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ProductResponse>> getAllByParamCategory(@RequestParam(value = "categoria", defaultValue = "0") Long id_cat) {
        return ResponseEntity.ok().body(service.getAllByParamProducts(id_cat));
    }

    @GetMapping("/categories/names")
    public ResponseEntity<List<ProductResponse>> getAllByParamNameCategory(@RequestParam(value = "categoria", defaultValue = "0") String nomeCategoria) {
        return ResponseEntity.ok().body(service.getAllByParamNameProducts(nomeCategoria));
    }


}

