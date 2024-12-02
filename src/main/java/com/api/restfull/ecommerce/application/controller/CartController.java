package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.CartRequest;
import com.api.restfull.ecommerce.application.request.ClientRequest;
import com.api.restfull.ecommerce.application.response.CartResponse;
import com.api.restfull.ecommerce.application.response.ClientResponse;
import com.api.restfull.ecommerce.application.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @PostMapping
    public ResponseEntity<CartResponse> create(@RequestBody @Valid CartRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("Recebendo requisição: [method=POST, endpoint=/carts, body={}]", request);

        CartResponse response = service.createCart(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();

        long executionTime = System.currentTimeMillis() - startTime;
        log.info("Resposta enviada: [status=201, body={}, executionTime={}ms]", response, executionTime);

        return ResponseEntity.created(uri).body(response);
    }
}
