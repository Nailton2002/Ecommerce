package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.ClientRequest;
import com.api.restfull.ecommerce.application.response.ClientResponse;
import com.api.restfull.ecommerce.application.service.ClientService;
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
@RequestMapping("/clients")
public class ClientController {

    private final ClientService service;

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Valid @RequestBody ClientRequest request) {
        long startTime = System.currentTimeMillis();
        log.info("Recebendo requisição: [method=POST, endpoint=/clients, body={}]", request);

        ClientResponse response = service.createClient(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();

        long executionTime = System.currentTimeMillis() - startTime;
        log.info("Resposta enviada: [status=201, body={}, executionTime={}ms]", response, executionTime);

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping("/searchs")
    public ResponseEntity<List<ClientResponse>> searchByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(service.searchByNameClients(name));
    }

    @GetMapping("/pages")
    public ResponseEntity<Page<ClientResponse>> getAllPaged(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok(service.getAllPagedClients(page, size));
    }

    @GetMapping("/cpf")
    public ResponseEntity<ClientResponse> getByCpf(@RequestParam String cpf) {
        return ResponseEntity.ok().body(service.getByCpfClient(cpf));
    }

    @GetMapping("/email")
    public ResponseEntity<ClientResponse> getByEmail(@RequestParam String email) {
        return ResponseEntity.ok().body(service.getByEmailClient(email));
    }

    @GetMapping("/dateOfBirths")
    public ResponseEntity<List<ClientResponse>> findByDateOfBirthLike(@RequestParam String dateOfBirth) {
        return ResponseEntity.ok().body(service.findByDateOfBirthLikeClient(dateOfBirth));
    }

    @GetMapping("/actives")
    public ResponseEntity<List<ClientResponse>> getByStatus(@RequestParam(name = "active") Boolean active) {
        return ResponseEntity.ok(service.getByStatusClients(active));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.getByIdClient(id));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ClientResponse> update(@Valid @RequestBody ClientRequest request) {
        return ResponseEntity.ok().body(service.updateClient(request));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientResponse> desable(@PathVariable Long id) {
        return ResponseEntity.ok().body(service.desableClient(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        service.deleteClient(id);
        return ResponseEntity.noContent().build();
    }
}
