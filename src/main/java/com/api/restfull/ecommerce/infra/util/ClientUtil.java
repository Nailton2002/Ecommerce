package com.api.restfull.ecommerce.infra.util;

import com.api.restfull.ecommerce.application.request.ClientRequest;
import com.api.restfull.ecommerce.application.service_impl.ClientServiceImpl;
import com.api.restfull.ecommerce.domain.entity.Client;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleException;
import com.api.restfull.ecommerce.domain.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientUtil {

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

    public void validateClientUniquenessByUpdate(ClientRequest request, Long currentClientId) {
        // Verifica se o CPF já existe em outro cliente
        Optional<Client> clientWithSameCpf = repository.findByCpf(request.cpf());
        if (clientWithSameCpf.isPresent() && !clientWithSameCpf.get().getId().equals(currentClientId)) {
            log.error("Tentativa de atualização com CPF já existente: {}", request.cpf());
            throw new BusinessRuleException("Já existe um cliente com este CPF: " + request.cpf());
        }

        // Verifica se o email já existe em outro cliente
        Optional<Client> clientWithSameEmail = repository.findByEmail(request.email());
        if (clientWithSameEmail.isPresent() && !clientWithSameEmail.get().getId().equals(currentClientId)) {
            log.error("Tentativa de atualização com e-mail já existente: {}", request.email());
            throw new BusinessRuleException("Já existe um cliente com este e-mail: " + request.email());
        }

        log.info("Validação de exclusividade concluída com sucesso para cliente ID: {}", currentClientId);
    }
}
