package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.OrderRequest;
import com.api.restfull.ecommerce.application.response.OrderResponse;
import com.api.restfull.ecommerce.application.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    @PostMapping
    public ResponseEntity<OrderResponse> saveOrder(@Valid @RequestBody OrderRequest request) {
        OrderResponse response = service.saveOrder(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }
    @GetMapping
    public ResponseEntity<Page<OrderResponse>> getAllPagedOrders(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.getAllPagedOrders(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> findByIdOrder(@PathVariable Long id){
        return ResponseEntity.ok().body(service.findByIdOrder(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderResponse> updateOrder(@RequestBody OrderRequest request) {
        // Chama o serviço para atualizar o pedido
        OrderResponse response = service.updateOrder(request);
        // Retorna a resposta com status HTTP 200 OK
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        service.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }

}
