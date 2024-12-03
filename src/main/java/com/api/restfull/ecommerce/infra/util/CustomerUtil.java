package com.api.restfull.ecommerce.infra.util;

import com.api.restfull.ecommerce.application.request.customer.CustomerRequest;
import com.api.restfull.ecommerce.application.request.customer.CustomerUpRequest;
import com.api.restfull.ecommerce.application.service_impl.CustomerServiceImpl;
import com.api.restfull.ecommerce.domain.entity.Customer;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleException;
import com.api.restfull.ecommerce.domain.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerUtil {

    private final CustomerRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    public void validateCustomerUniqueness(CustomerRequest request){

        // Verifica se o CPF já existe no banco de dados
        if (repository.existsByCpf(request.cpf())) {
            logger.error("Tentativa de cadastro com CPF já existente: {}", request.cpf());
            throw new BusinessRuleException("Já existe um usuário com este CPF." + request.cpf());
        }
        logger.info("");

        //Verificando se o emai já existe no banco de dados
        if (repository.existsByEmail(request.email())) {
            logger.error("Tentativa de cadastro com e-mail já existente: {}", request.email());
            throw new BusinessRuleException("Já existe um usuário com este e-mail." + request.email());
        }
        logger.info("");
    }

    public void validateCustomerUniquenessByUpdate(CustomerUpRequest request) {

        // Verifica se o CPF já existe em outro cliente
        Optional<Customer> clientWithSameCpf = repository.findByCpf(request.cpf());

        if (clientWithSameCpf.isPresent() && !clientWithSameCpf.get().getId().equals(request.id())) {

            log.error("Tentativa de atualização com CPF já existente: {}", request.cpf());
            throw new BusinessRuleException("Já existe um cliente com este CPF: " + request.cpf());
        }

        // Verifica se o email já existe em outro cliente
        Optional<Customer> clientWithSameEmail = repository.findByEmail(request.email());

        if (clientWithSameEmail.isPresent() && !clientWithSameEmail.get().getId().equals(request.id())) {

            log.error("Tentativa de atualização com e-mail já existente: {}", request.email());
            throw new BusinessRuleException("Já existe um cliente com este e-mail: " + request.email());
        }

        log.info("Validação de exclusividade concluída com sucesso para cliente ID: {}", request.id());
    }
}
