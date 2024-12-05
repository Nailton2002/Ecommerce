package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.product.ProductRequest;
import com.api.restfull.ecommerce.application.request.product.ProductUpRequest;
import com.api.restfull.ecommerce.application.response.product.ProductListResponse;
import com.api.restfull.ecommerce.application.response.product.ProductResponse;
import com.api.restfull.ecommerce.application.service.ProductService;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundExceptionLogger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
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
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    public ResponseEntity<ProductListResponse> createProduct(@Valid @RequestBody ProductRequest request) {

        long startTime = System.currentTimeMillis(); // Início do cálculo do tempo de execução
        logger.info("Recebendo requisição: [method=POST, endpoint=/products, body={}]", request);

        try {
            // Criação do produto
            ProductListResponse response = service.createProduct(request);
            // Construção da URI de retorno
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();

            long executionTime = System.currentTimeMillis() - startTime; // Cálculo do tempo de execução
            logger.info("Resposta enviada: [status=201, body={}, executionTime={}ms]", response, executionTime);

            return ResponseEntity.created(uri).body(response);

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Recurso não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao criar produto: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductListResponse> updateProduct(@Valid @RequestBody ProductUpRequest request) {

        long startTime = System.currentTimeMillis();
        logger.info("Recebendo requisição: [method=PUT, endpoint=/products/id, body={}]", request);

        try {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Resposta enviada: [status=200, body={}, executionTime={}ms]", request, executionTime);

            return ResponseEntity.status(HttpStatus.OK).body(service.updateProduct(request));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Recurso não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao criar produto: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductListResponse> getByIdProduct(@PathVariable Long id) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/products/searchs, body={}]");

        try {
            logger.info("Requisição concluída com sucesso: Cliente encontrado ID={}", service.getByIdProduct(id));

            return ResponseEntity.status(HttpStatus.OK).body(service.getByIdProduct(id));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/names")
    public ResponseEntity<ProductListResponse> getByNameProduct(@RequestParam(name = "name") String name) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/products/names, body={}]");

        try {
            logger.info("Requisição concluída com sucesso: Produto encontrado Name={}", service.getByNameProduct(name));

            return ResponseEntity.status(HttpStatus.OK).body(service.getByNameProduct(name));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<ProductResponse>> getAllPagedProducts(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/products/pages, page={}, size={}]", page, size);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, totalElements={}]", service.getAllPagedProducts(page, size));

            return ResponseEntity.status(HttpStatus.OK).body(service.getAllPagedProducts(page, size));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/searchs")
    public ResponseEntity<List<ProductResponse>> searchByName(@RequestParam(name = "name") String name) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/products/searchs, body={}]");

        try {
            logger.info("Requisição concluída com sucesso: Produto encontrado Name={}", service.searchByName(name));

            return ResponseEntity.status(HttpStatus.OK).body(service.searchByName(name));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/descriptions")
    public ResponseEntity<List<ProductResponse>> getByDescriptionProduct(@RequestParam(name = "description") String description) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/products/descriptions, body={}]");

        try {
            logger.info("Requisição concluída com sucesso: Produto encontrado description={}", service.getByDescriptionProduct(description));

            return ResponseEntity.status(HttpStatus.OK).body(service.getByDescriptionProduct(description));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/prices")
    public ResponseEntity<List<ProductResponse>> getSortedByPriceProducts(@RequestParam(defaultValue = "asc") String price) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/products/prices, body={}]");

        try {
            logger.info("Requisição concluída com sucesso: Produto encontrado Price={}", service.getSortedByPriceProducts(price));

            return ResponseEntity.status(HttpStatus.OK).body(service.getSortedByPriceProducts(price));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/quantitys")
    public ResponseEntity<List<ProductResponse>> getByQuantityProduct(@RequestParam(name = "quantityStock") Integer quantityStock) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/products/quantitys, body={}]");

        try {
            logger.info("Requisição concluída com sucesso: Produto encontrado Quantitys={}", service.getByQuantityProduct(quantityStock));

            return ResponseEntity.status(HttpStatus.OK).body(service.getByQuantityProduct((quantityStock)));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/actives")
    public ResponseEntity<List<ProductResponse>> getByStatusProducts(@RequestParam(name = "active") Boolean active) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/products/actives, body={}]");

        try {
            logger.info("Requisição concluída com sucesso: Produto encontrado Actives={}", service.getByStatusProducts(active));

            return ResponseEntity.status(HttpStatus.OK).body(service.getByStatusProducts(active));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductResponse> desableCategory(@PathVariable Long id) {

        logger.info("Recebendo requisição: [method=Patch, endpoint=/products/ad, body={}]");

        try {
            logger.info("Requisição concluída com sucesso: Produto encontrado ID={}", service.desableProduct(id));

            return ResponseEntity.status(HttpStatus.OK).body(service.desableProduct(id));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {

        logger.info("Recebendo requisição para excluir um produto desabilitada:  [method=DELETE, endpoint=/customers/id ] ID={}", id);

        try {
            service.deleteProduct(id);
            logger.info("Produto excluído com sucesso: [status=204, ID={}", id);
            return ResponseEntity.noContent().build();

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao excluir produto: {}", ex.getMessage(), ex);
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (BusinessRuleExceptionLogger ex) {
            logger.warn("Violação de regra de negócio do produto pois ele esta ativo: {}", ex.getMessage());
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao excluir produto: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    //BUSCA POR productS RELACIONADOS A category.
    @GetMapping("/categories/{categoryId}")
    public ResponseEntity<List<ProductListResponse>> getAllByIdCategory(@PathVariable Long categoryId) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/prducts/categories/{categoryId}", categoryId);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, categoryId={}]");

            return ResponseEntity.status(HttpStatus.OK).body(service.getAllByParamProducts(categoryId));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/categories")
    public ResponseEntity<List<ProductListResponse>> getAllByParamCategory(@RequestParam(value = "category", defaultValue = "0") Long id_cat) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/prducts/categories", id_cat);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, id_cat={}]");

            return ResponseEntity.status(HttpStatus.OK).body(service.getAllByParamProducts(id_cat));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/categories/names")
    public ResponseEntity<List<ProductListResponse>> getAllByParamNameCategory(@RequestParam(value = "category", defaultValue = "0") String nameCategory) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/prducts/categories/names", nameCategory);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, namecategory={}]");

            return ResponseEntity.status(HttpStatus.OK).body(service.getAllByParamNameProducts(nameCategory));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Produto não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }


}

