package com.api.restfull.ecommerce.application.service_impl;

import com.api.restfull.ecommerce.application.request.customer.CustomerRequest;
import com.api.restfull.ecommerce.application.request.customer.CustomerUpRequest;
import com.api.restfull.ecommerce.application.response.customer.CustomerListResponse;
import com.api.restfull.ecommerce.application.response.customer.CustomerResponse;
import com.api.restfull.ecommerce.application.service.CustomerService;
import com.api.restfull.ecommerce.domain.entity.Customer;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleException;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.repository.CustomerRepository;
import com.api.restfull.ecommerce.infra.util.CustomerUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;
    private final CustomerUtil customerUtil;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public CustomerListResponse createCustomer(CustomerRequest request) {

        // validar a exclusividade do cliente por cpf e email.
        customerUtil.validateCustomerUniqueness(request);

        // Cria uma nova instância de Customer usando o DTO
        Customer customer = new Customer(request);
        return new CustomerListResponse(repository.save(customer));
    }

    @Override
    public CustomerListResponse getByIdCustomer(Long id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado! ID: " + id + ", "));
        return new CustomerListResponse(customer);
    }

    @Override
    public Page<CustomerResponse> getAllPagedCustomers(int page, int size) {
        // Cria uma Pageable para paginar os dados
        Pageable pageable = PageRequest.of(page, size);

        // Chama o repositório para obter a página de clientes
        Page<Customer> clientsPage = repository.findAll(pageable);

        // Converte os clientes em CustomerResponse
        return clientsPage.map(CustomerResponse::new);
    }

    @Override
    public CustomerListResponse updateCustomer(CustomerUpRequest request) {
        // Busca o cliente pelo ID no banco de dados
        Customer customer = repository.findById(request.id()).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + request.id()));

        // validar a exclusividade do cliente
        customerUtil.validateCustomerUniquenessByUpdate(request);

        // Verificação dos dados
        customer.updateCustomer(request);

        // Salva e atualizar os dados necessario do cliente
        Customer obj = repository.save(customer);

        // Retorna o cliente dto
        return new CustomerListResponse(obj);
    }

    @Override
    public List<CustomerResponse> searchByNameCustomers(String name) {
        List<Customer> customers = repository.findByNameContainingIgnoreCase(name);
        return customers.stream().map(CustomerResponse::new).toList();
    }

    @Override
    public List<CustomerResponse> findByDateOfBirthLikeCustomer(String dateOfBirth) {
//      String datePartial = "1985-02"; // Procurando por fevereiro de 1985
        List<Customer> customers = repository.findByDateOfBirthLike(dateOfBirth);
        return customers.stream().map(CustomerResponse::new).toList();
    }

    @Override
    public CustomerListResponse getByCpfCustomer(String cpf) {
        Optional<Customer> byCpf = repository.findByCpf(cpf);
        if (byCpf.isEmpty()) {
            logger.error("Buscar por inessitente por cpf" + cpf);
            throw new BusinessRuleException("Cliente não encontrado com CPF começando por: " + cpf);
        }
        logger.info("");
        return new CustomerListResponse(byCpf.get());
    }

    @Override
    public CustomerListResponse getByEmailCustomer(String email) {
        Optional<Customer> byEmail = repository.findByEmail(email);
        if (byEmail.isPresent()) {
            return new CustomerListResponse(byEmail.get());
        }
        throw new BusinessRuleException("Cliente não encontrado com E-MAIL: " + email);
    }

    @Override
    public CustomerResponse desableCustomer(Long id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        customer.customerDesactive();
        return new CustomerResponse(repository.save(customer));
    }

    @Override
    public List<CustomerResponse> getByStatusCustomers(Boolean active) {
        List<Customer> byActives = repository.findByActives(active);
        return byActives.stream().map(CustomerResponse::new).toList();
    }

    @Override
    public void deleteCustomer(Long id) {
        Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
        if (customer.getActive()) {
            throw new ResourceNotFoundException("Cliente ativo, não pode ser excluido!");
        }
        repository.delete(customer);
    }
}
