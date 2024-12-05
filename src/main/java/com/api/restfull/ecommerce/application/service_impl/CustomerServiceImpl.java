package com.api.restfull.ecommerce.application.service_impl;

import com.api.restfull.ecommerce.application.request.customer.CustomerRequest;
import com.api.restfull.ecommerce.application.request.customer.CustomerUpRequest;
import com.api.restfull.ecommerce.application.response.customer.CustomerListResponse;
import com.api.restfull.ecommerce.application.response.customer.CustomerResponse;
import com.api.restfull.ecommerce.application.service.CustomerService;
import com.api.restfull.ecommerce.domain.entity.Customer;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleException;
import com.api.restfull.ecommerce.domain.exception.ExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundExceptionLogger;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerUtil customerUtil;
    private final CustomerRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public CustomerListResponse createCustomer(CustomerRequest request) {

        logger.info("Iniciando salvamento do cliente ID: {}, Cliente ID: {}", request);
        try {

            // validar a exclusividade do cliente por cpf e email.
            customerUtil.validateCustomerUniqueness(request);

            // Cria uma nova instância de Customer usando o DTO
            Customer customer = new Customer(request);

            logger.info("Cliente salvo com sucesso: ID={}, Cliente={}");

            return new CustomerListResponse(repository.save(customer));

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao salvar cliente: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public CustomerListResponse getByIdCustomer(Long id) {

        logger.info("Buscando cliente pelo ID: {}", id);

        try {
            Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado! ID: " + id + ", "));
            logger.info("Cliente encontrado: ID={}, Quantidade={}", customer.getId());

            return new CustomerListResponse(customer);

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar cliente: {}", ex.getMessage(), ex);
            throw ex;
        }
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

        logger.info("Iniciando atualização do cliente ID: {}", request.id());
        try {
            // Busca o cliente pelo ID no banco de dados
            Customer customer = repository.findById(request.id()).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o ID: " + request.id()));

            logger.debug("Cliente encontrado para atualização: {}", request);

            // validar a exclusividade do cliente
            customerUtil.validateCustomerUniquenessByUpdate(request);

            // Verificação dos dados
            customer.updateCustomer(request);

            // Salva e atualizar os dados necessario do cliente
            Customer obj = repository.save(customer);

            logger.info("Cliente atualizado com sucesso: ID={}", request.id());

            // Retorna o cliente dto
            return new CustomerListResponse(obj);

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao atualizar cliente: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public List<CustomerResponse> searchByNameCustomers(String name) {

        logger.info("Buscando cliente pelo nome: {}", name);

        try {
            List<Customer> customers = repository.findByNameContainingIgnoreCase(name);
            logger.info("Cliente encontrado: NAME={},", name);
            return customers.stream().map(CustomerResponse::new).toList();

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar cliente por nome: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public List<CustomerResponse> findByDateOfBirthLikeCustomer(String dateOfBirth) {

        logger.info("Buscando cliente pela data de nascimento: {}", dateOfBirth);
        try {

            // String datePartial = "1985-02"; // Procurando por fevereiro de 1985
            List<Customer> customers = repository.findByDateOfBirthLike(dateOfBirth);
            logger.info("Cliente encontrado: dateOfBirth={}, ");
            return customers.stream().map(CustomerResponse::new).toList();

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar cliente por data de nascimento: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public CustomerListResponse getByCpfCustomer(String cpf) {

        logger.info("Buscando cliente por cpf: {}", cpf);
        try {

            Optional<Customer> byCpf = repository.findByCpf(cpf);
            if (byCpf.isEmpty()) {
                logger.error("Buscar inessitente por cpf" + cpf);
                throw new BusinessRuleException("Cliente não encontrado com CPF começando por: " + cpf);
            }
            logger.info("Cliente encontrado: cpf={}, ");
            return new CustomerListResponse(byCpf.get());

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar cliente por cpf: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public CustomerListResponse getByEmailCustomer(String email) {

        logger.info("Buscando cliente por cpf: {}", email);
        try {

            Optional<Customer> byEmail = repository.findByEmail(email);
            if (byEmail.isPresent()) {
                return new CustomerListResponse(byEmail.get());
            }
            logger.info("Cliente encontrado: cpf={}, ");

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar cliente por email: {}", ex.getMessage(), ex);
            throw ex;
        }
        throw new ResourceNotFoundExceptionLogger("Cliente não encontrado com E-MAIL: " + email);
    }

    @Override
    public List<CustomerResponse> getByStatusCustomers(Boolean active) {

        logger.info("Buscando cliente ativos na base de dados: {}", active);
        try {

            List<Customer> byActives = repository.findByActives(active);
            logger.info("Cliente encontrado: active={}, ");
            return byActives.stream().map(CustomerResponse::new).toList();

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar cliente por active: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public CustomerResponse desableCustomer(Long id) {

        logger.info("Iniciando a desabilitação do cliente ID: {}", id);
        try {

            Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
            logger.debug("Cliente encontrado para desabilitar: {}");

            customer.customerDesactive();
            logger.info("Cliente desabilitado com sucesso: ID={}", id);
            return new CustomerResponse(repository.save(customer));

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao desabilitar cliente: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public void deleteCustomer(Long id) {

        logger.info("Iniciando exclusão do cliente ID: {}", id);

        try {

            Customer customer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com ID: " + id));
            logger.debug("Cliente encontrado para deletar: {}");

            if (customer.getActive()) {

                logger.warn("Tentativa de excluir cliente ativo: Status={}", customer.getActive());

                throw new ResourceNotFoundException("Cliente ativo, não pode ser excluido!");
            }
            repository.delete(customer);
            logger.info("Cliente excluído com sucesso: ID={}, Cliente={}", LocalDateTime.now());

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao excluir cliente: {}", ex.getMessage(), ex);
            throw ex;
        }
    }
}
