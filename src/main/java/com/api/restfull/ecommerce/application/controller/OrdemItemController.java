package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.OrderItemRequest;
import com.api.restfull.ecommerce.application.response.OrderItemResponse;
import com.api.restfull.ecommerce.application.service.OrderItemService;
import com.api.restfull.ecommerce.domain.exception.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/order-items")
public class OrdemItemController {

    private final OrderItemService service;
    private static final Logger logger = LoggerFactory.getLogger(OrdemItemController.class);

    @PostMapping
    public ResponseEntity<OrderItemResponse> saveOrderItem(@RequestBody OrderItemRequest request) {

//        long startTime = System.currentTimeMillis();
//        logger.info("Recebendo requisição: [method=POST, endpoint=/order-items, body={}]", request);
//
//        try {
//            OrderItemResponse response = service.saveOrderItem(request);
//            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
//
//            long executionTime = System.currentTimeMillis() - startTime;
//            logger.info("Resposta enviada: [status=201, body={}, executionTime={}ms]", response, executionTime);
//
//            return ResponseEntity.created(uri).body(response);
//
//        } catch (ResourceNotFoundExceptionLogger ex) {
//            logger.error("Erro ao criar Item do Pedido: {}", ex.getMessage(), ex);
//            throw ex;
//        } catch (ExceptionLogger ex) {
//            logger.error("Erro inesperado ao criar Item do Pedido: {}", ex.getMessage(), ex);
//            throw ex;
//        }
        return null;
    }

    @GetMapping
    public ResponseEntity<Page<OrderItemResponse>> getAllPagedOrderItems(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/order-items, body={}]", page, size);

        try {
            Page<OrderItemResponse> response = service.getAllPagedOrderItems(page, size);
            logger.info("Requisição concluída com sucesso: [status=200, body={}, Total de itens retornados={}", response.getTotalElements());
            return ResponseEntity.ok(response);
        } catch (ExceptionLogger ex) {
            logger.error("Erro ao listar Itens de Pedido: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemResponse> findByIdOrderItem(@PathVariable Long id) {
//        logger.info("Recebendo requisição para buscar Item do Pedido pelo ID: [method=GET, endpoint=/order-items, body={}] {}", id);
//        try {
//            logger.info("Requisição concluída com sucesso: Item encontrado ID={}", service.findByIdOrderItem(id).id());
//            return ResponseEntity.ok(service.findByIdOrderItem(id));
//        } catch (ResourceNotFoundExceptionLogger ex) {
//            logger.error("Erro ao buscar Item do Pedido:  [status=200, body={}", ex.getMessage(), ex);
//            throw ex;
//        } catch (ExceptionLogger ex) {
//            logger.error("Erro inesperado ao buscar Item do Pedido: {}", ex.getMessage(), ex);
//            throw ex;
//        }
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItemResponse> updateOrderItem(@RequestBody OrderItemRequest request) {
//
//        logger.info("Recebendo requisição para atualizar Item do Pedido: [method=PUT, endpoint=/order-items, body={}], ID={}, Dados={}", request);
//        try {
//            OrderItemResponse response = service.updateOrderItem(request);
//            logger.info("Item do Pedido atualizado com sucesso: [status=200, body={}, ID={}", response.id());
//            return ResponseEntity.ok().body(response);
//        } catch (ResourceNotFoundExceptionLogger ex) {
//            logger.error("Erro ao atualizar Item do Pedido: {}", ex.getMessage(), ex);
//            throw ex; // A exceção será tratada pelo ExceptionHandler
//        } catch (ExceptionLogger ex) {
//            logger.error("Erro inesperado ao atualizar Item do Pedido: {}", ex.getMessage(), ex);
//            throw ex;
//        }
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        logger.info("Recebendo requisição para excluir Item do Pedido:  [method=DELETE, endpoint=/order-items ] ID={}", id);
        try {
            service.deleteOrderItem(id);
            logger.info("Item do Pedido excluído com sucesso: [status=204, ID={}", id);
            return ResponseEntity.noContent().build();
        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao excluir Item do Pedido: {}", ex.getMessage(), ex);
            throw ex; // A exceção será tratada pelo ExceptionHandler
        } catch (BusinessRuleExceptionLogger ex) {
            logger.warn("Violação de regra de negócio ao excluir Item do Pedido: {}", ex.getMessage());
            throw ex; // A exceção será tratada pelo ExceptionHandler
        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao excluir Item do Pedido: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

}
