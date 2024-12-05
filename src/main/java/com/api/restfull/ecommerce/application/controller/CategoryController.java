package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.category.CategoryRequest;
import com.api.restfull.ecommerce.application.request.category.CategoryUpRequest;
import com.api.restfull.ecommerce.application.response.category.CategoryListResponse;
import com.api.restfull.ecommerce.application.response.category.CategoryResponse;
import com.api.restfull.ecommerce.application.service.CategoryService;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundExceptionLogger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService service;
    private static final Logger logger = LoggerFactory.getLogger(CategoryController.class);

    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@Valid @RequestBody CategoryRequest request) {

        logger.info("Recebendo requisição: [method=POST, endpoint=/categories, body={}]", request);

        try {
            CategoryResponse response = service.saveCategory(request);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();

            logger.info("Resposta enviada: [status=201, body={}, executionTime={}ms]", response);
            return ResponseEntity.created(uri).body(response);

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Erro ao criar uma categoria: {}", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao criar uma categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAllCategory(@RequestParam(required = false) String name) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/categories, body={}]");

        try {
            logger.info("Requisição concluída com sucesso: Categoria encontrado ID={}", service.findByNameCategory(name));
            if (name != null) {
                return ResponseEntity.status(HttpStatus.OK).body(List.of(service.findByNameCategory(name)));
            }
            return ResponseEntity.status(HttpStatus.OK).body(service.findAllCategory());

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Erro ao buscar categoria:  [status=404, message={}", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao buscar categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


    @GetMapping("/actives")
    public ResponseEntity<List<CategoryResponse>> findByActivesCategory(@RequestParam(name = "active") Boolean active) {

        logger.info("Recebendo requisição para buscar categorias ativas: [method=GET, endpoint=/categories/actives, body={}] {}", id);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, body={}, Total de categorias retornados={}");
            return ResponseEntity.status(HttpStatus.OK).body(service.findByActivesCategory(active));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Erro ao buscar categoria:  [status=404, message={}", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao buscar categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping("/descriptions")
    public ResponseEntity<List<CategoryResponse>> findByDescriptionCategory(@RequestParam(name = "description") String description) {

        logger.info("Recebendo requisição para buscar categorias pela descrição: [method=GET, endpoint=/cagetories/descriptions, body={}] {}", description);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, body={}, Total de categoria retornados={}");

            return ResponseEntity.status(HttpStatus.OK).body(service.findByDescriptionCategory(description));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Erro ao buscar categoria:  [status=404, message={}", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao buscar categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryListResponse> findByIdCategory(@PathVariable Long id) {
        logger.info("Recebendo requisição para buscar categorias pelo ID: [method=GET, endpoint=/categories/id, body={}] {}", id);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, body={}, Total de categoria retornados={}");

            return ResponseEntity.status(HttpStatus.OK).body(service.findByIdCategory(id));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar categoria:  [status=404, body={}", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao buscar categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CategoryResponse> updateCategory(@Valid @RequestBody CategoryUpRequest request) {

        logger.info("Recebendo requisição para atualizar categoria: [method=PUT, endpoint=/categories/id, body={}], ID={}, Dados={}", request);
        try {
            logger.info("Categoria atualizado com sucesso: [status=200, body={}, ID={}");

            return ResponseEntity.status(HttpStatus.OK).body(service.updateCategory(request));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao atualizar categroia: {}", ex.getMessage(), ex);
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao atualizar categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CategoryResponse> desableCategory(@PathVariable Long id) {

        logger.info("Recebendo requisição para desabilitar a categoria: [method=PATCH, endpoint=/categories/id, body={}], ID={}, Dados={}");
        try {
            logger.info("Categoria desabilitada com sucesso: [status=200, body={}, ID={}");
            return ResponseEntity.status(HttpStatus.OK).body(service.desableCategory(id));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao desabilitar a catgeroria: {}", ex.getMessage(), ex);
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao desabilitar a categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteDesableCategory(@PathVariable Long id) {

        logger.info("Recebendo requisição para excluir uma categoria desabilitada:  [method=DELETE, endpoint=/categories/id ] ID={}", id);

        try {
            service.deleteDesableCategory(id);
            logger.info("Categoria excluído com sucesso: [status=204, ID={}", id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao excluir categoria: {}", ex.getMessage(), ex);
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (BusinessRuleExceptionLogger ex) {
            logger.warn("Violação de regra de negócio da categoria pois ela esta ativa: {}", ex.getMessage());
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao excluir categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
