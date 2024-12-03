package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.customer.CustomerRequest;
import com.api.restfull.ecommerce.application.request.customer.CustomerUpRequest;
import com.api.restfull.ecommerce.application.response.customer.CustomerListResponse;
import com.api.restfull.ecommerce.application.response.customer.CustomerResponse;
import com.api.restfull.ecommerce.application.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerListResponse> create(@RequestBody @Valid CustomerRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("Recebendo requisição: [method=POST, endpoint=/clients, body={}]", request);

        CustomerListResponse response = service.createCustomer(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();

        long executionTime = System.currentTimeMillis() - startTime;
        log.info("Resposta enviada: [status=201, body={}, executionTime={}ms]", response, executionTime);

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/searchs")
    public ResponseEntity<List<CustomerResponse>> searchByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(service.searchByNameCustomers(name));
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<CustomerResponse>> getAllPaged(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllPagedCustomers(page, size));
    }

    @GetMapping("/cpf")
    public ResponseEntity<CustomerListResponse> getByCpf(@RequestParam String cpf) {
        return ResponseEntity.ok().body(service.getByCpfCustomer(cpf));
    }

    @GetMapping("/email")
    public ResponseEntity<CustomerListResponse> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok().body(service.getByEmailCustomer(email));
    }

    @GetMapping("/dateOfBirths")
    public ResponseEntity<List<CustomerResponse>> findByDateOfBirthLike(@RequestParam String dateOfBirth) {
        return ResponseEntity.ok().body(service.findByDateOfBirthLikeCustomer(dateOfBirth));
    }

    @GetMapping("/actives")
    public ResponseEntity<List<CustomerResponse>> getByStatus(@RequestParam(name = "active") Boolean active) {
        return ResponseEntity.ok(service.getByStatusCustomers(active));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerListResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.getByIdCustomer(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerListResponse> update(@Valid @RequestBody CustomerUpRequest request) {
        return ResponseEntity.ok().body(service.updateCustomer(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomerResponse> desable(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.desableCustomer(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletegetByIdCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
