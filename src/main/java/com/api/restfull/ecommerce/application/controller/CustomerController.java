package com.api.restfull.ecommerce.application.controller;

import com.api.restfull.ecommerce.application.request.customer.CustomerRequest;
import com.api.restfull.ecommerce.application.request.customer.CustomerUpRequest;
import com.api.restfull.ecommerce.application.response.customer.CustomerListResponse;
import com.api.restfull.ecommerce.application.response.customer.CustomerResponse;
import com.api.restfull.ecommerce.application.service.CustomerService;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundExceptionLogger;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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
    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);


    @PostMapping
    public ResponseEntity<CustomerListResponse> create(@RequestBody @Valid CustomerRequest request) {

        logger.info("Recebendo requisição: [method=POST, endpoint=/customers, body={}]", request);
        try {

            CustomerListResponse response = service.createCustomer(request);
            URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.id()).toUri();

            log.info("Resposta enviada: [status=201, body={}, executionTime={}ms]", response);
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

    @GetMapping("/searchs")
    public ResponseEntity<List<CustomerResponse>> searchByName(@RequestParam(name = "name") String name) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/customers/searchs, body={}]");

        try {
            logger.info("Requisição concluída com sucesso: Cliente encontrado ID={}", service.searchByNameCustomers(name));

            return ResponseEntity.status(HttpStatus.OK).body(service.searchByNameCustomers(name));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Cliente não encontrado: [status=404, message={}]", ex.getMessage(), ex);
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
    public ResponseEntity<Page<CustomerResponse>> getAllPaged(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "size", defaultValue = "10") int size) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/customers/pages, page={}, size={}]", page, size);

        try {
            // Obtém os clientes paginados
            Page<CustomerResponse> pagedCustomers = service.getAllPagedCustomers(page, size);

            logger.info("Requisição concluída com sucesso: [status=200, totalElements={}]", pagedCustomers.getTotalElements());

            return ResponseEntity.status(HttpStatus.OK).body(pagedCustomers);

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Cliente não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/cpf")
    public ResponseEntity<CustomerListResponse> getByCpf(@RequestParam String cpf) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/customers/cpf", cpf);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, cpf={}]");

            return ResponseEntity.status(HttpStatus.OK).body(service.getByCpfCustomer(cpf));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Cliente não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<CustomerListResponse> getByEmail(@RequestParam String email) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/customers/email", email);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, cpf={}]");

            return ResponseEntity.status(HttpStatus.OK).body(service.getByEmailCustomer(email));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Cliente não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping("/dateOfBirths")
    public ResponseEntity<List<CustomerResponse>> findByDateOfBirthLike(@RequestParam String dateOfBirth) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/customers/dateOfBirth", dateOfBirth);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, dateOfBirth={}]");

            return ResponseEntity.status(HttpStatus.OK).body(service.findByDateOfBirthLikeCustomer(dateOfBirth));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Cliente não encontrado: [status=404, message={}]", ex.getMessage(), ex);
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
    public ResponseEntity<List<CustomerResponse>> getByStatus(@RequestParam(name = "active") Boolean active) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/customers/active", active);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, active={}]");

            return ResponseEntity.status(HttpStatus.OK).body(service.getByStatusCustomers(active));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Cliente não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerListResponse> getById(@PathVariable Long id) {

        logger.info("Recebendo requisição: [method=GET, endpoint=/customers/id", id);

        try {
            logger.info("Requisição concluída com sucesso: [status=200, active={}]");

            return ResponseEntity.status(HttpStatus.OK).body(service.getByIdCustomer(id));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Cliente não encontrado: [status=404, message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado: [message={}]", ex.getMessage(), ex);
            throw ex;

        } catch (Exception ex) {
            logger.error("Erro genérico inesperado: [message={}]", ex.getMessage(), ex);
            throw new RuntimeException("Erro ao processar a solicitação.", ex);
        }
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<CustomerListResponse> update(@Valid @RequestBody CustomerUpRequest request) {

        logger.info("Recebendo requisição para atualizar cliente: [method=PUT, endpoint=/customers/id, body={}], ID={}, Dados={}", request);
        try {
            logger.info("Cliente atualizado com sucesso: [status=200, body={}, ID={}");

            return ResponseEntity.status(HttpStatus.OK).body(service.updateCustomer(request));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Cliente não encontrado: [status=404, message={}]", ex.getMessage(), ex);
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
    public ResponseEntity<CustomerResponse> desable(@PathVariable Long id) {

        logger.info("Recebendo requisição para desabilitar cliente: [method=Patch, endpoint=/customers/id, body={}], ID={}, Dados={}", id);
        try {
            logger.info("Cliente desabilitado com sucesso: [status=200, body={}, ID={}");

            return ResponseEntity.status(HttpStatus.OK).body(service.desableCustomer(id));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.warn("Cliente não encontrado: [status=404, message={}]", ex.getMessage(), ex);
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
    public ResponseEntity<Void> deletegetByIdCustomer(@PathVariable Long id) {

        logger.info("Recebendo requisição para excluir um cliente desabilitada:  [method=DELETE, endpoint=/customers/id ] ID={}", id);

        try {
            service.deleteCustomer(id);

            logger.info("Cliente excluído com sucesso: [status=204, ID={}", id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao excluir cliente: {}", ex.getMessage(), ex);
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (BusinessRuleExceptionLogger ex) {
            logger.warn("Violação de regra de negócio do cliente pois ele esta ativo: {}", ex.getMessage());
            throw ex; // A exceção será tratada pelo ExceptionHandler

        } catch (ExceptionLogger ex) {
            logger.error("Erro inesperado ao excluir cliente: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
