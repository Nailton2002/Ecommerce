package com.api.restfull.ecommerce.infra.util;

import com.api.restfull.ecommerce.application.request.ClientRequest;
import com.api.restfull.ecommerce.application.service_imple.ClientServiceImpl;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleException;
import com.api.restfull.ecommerce.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ServiceClientUtil {

    private final ClientRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    public void validateClientUniqueness(ClientRequest request){

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
}
