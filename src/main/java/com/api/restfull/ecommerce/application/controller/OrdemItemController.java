package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.OrderItemRequest;
import com.api.restfull.ecommerce.application.response.OrderItemResponse;
import com.api.restfull.ecommerce.application.service.OrderItemService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping
    public ResponseEntity<OrderItemResponse> saveOrderItem(@RequestBody OrderItemRequest request) {
        OrderItemResponse response = service.saveOrderItem(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<OrderItemResponse>> getAllPagedOrderItems(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(service.getAllPagedOrderItems(page, size));
    }
}
