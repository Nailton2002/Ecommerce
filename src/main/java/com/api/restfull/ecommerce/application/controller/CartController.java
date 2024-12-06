package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.CartItemRequest;
import com.api.restfull.ecommerce.application.request.CartRequest;
import com.api.restfull.ecommerce.application.response.CartResponse;
import com.api.restfull.ecommerce.application.service.CartService;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundExceptionLogger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {

    private final CartService service;
    private static final Logger logger = LoggerFactory.getLogger(CartController.class);

    @PostMapping
    public ResponseEntity<CartResponse> createCart(@RequestBody @Valid CartRequest request) {

        long startTime = System.currentTimeMillis();
        logger.info("Recebendo requisição: [method=POST, endpoint=/carts, body={}]", request);

        try {
            CartResponse response = service.createCart(request);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();

            long executionTime = System.currentTimeMillis() - startTime;
            logger.info("Resposta enviada: [status=201, body={}, executionTime={}ms]", response, executionTime);

            return ResponseEntity.created(uri).body(response);

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao criar carrinho: {}", ex.getMessage(), ex);
            throw ex;

        } catch (
                ExceptionLogger ex) {
            logger.error("Erro inesperado ao carrinho: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


    @PutMapping("/{cartId}/items/add")
    public ResponseEntity<CartResponse> addItemToCart(@PathVariable Long cartId, @RequestBody CartItemRequest request) {

        logger.info("Recebendo requisição: [method=PUT, endpoint=/carts/{cartId}/items/add, body={}]", request);

        try {
            CartResponse response = service.addItemToCart(cartId, request);

            logger.info("Resposta enviada: [status=200, body={}, executionTime={}ms]", response);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao adicionar Item do Pedido ao carrinho: {}", ex.getMessage(), ex);
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao adicionar Item do Pedido ao carrinho: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


    @PutMapping("/{cartId}/items/remove")
    public ResponseEntity<CartResponse> removeItemToCart(@PathVariable Long cartId, @RequestBody CartItemRequest request) {

        logger.info("Recebendo requisição: [method=PUT, endpoint=/carts/{cartId}/items/remove, body={}]", request);

        try {
            CartResponse response = service.removeItemToCart(cartId, request);

            logger.info("Resposta enviada: [status=200, body={}, executionTime={}ms]", response);

            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao remover Item do Pedido ao carrinho: {}", ex.getMessage(), ex);
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao remover Item do Pedido ao carrinho: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @PatchMapping("/{cartId}")
    public ResponseEntity<CartResponse> clearItemToCart(@PathVariable Long cartId) {

        logger.info("Recebendo requisição: [method=PATCH, endpoint=/carts/{cartId}, body={}]", cartId);

        try {
            CartResponse response = service.clearCart(cartId);

            logger.info("Resposta enviada: [status=200, body={}, executionTime={}ms]", response);

            return ResponseEntity.status(HttpStatus.OK).body(response);


        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao limpar carrinho: {}", ex.getMessage(), ex);
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (BusinessRuleExceptionLogger ex) {
            logger.warn("Violação de regra de negócio do carrinho: {}", ex.getMessage());
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao limpar carrinho: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


}
