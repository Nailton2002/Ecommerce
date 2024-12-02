package com.api.restfull.ecommerce.infra.util;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.service_impl.ClientServiceImpl;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleException;
import com.api.restfull.ecommerce.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryUtil {

    private final CategoryRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(ClientServiceImpl.class);

    /**
     * Validação ao salvar uma nova categoria.
     *
     * @param request a requisição contendo os dados da categoria
     */
    public void validateOnSave(CategoryRequest request) {
        validateNameAndDescription(request.name(), request.description(), "Categoria ativa com o mesmo nome e descrição já existe."
        );
    }

    /**
     * Validação ao atualizar uma categoria existente.
     *
     * @param request a requisição contendo os dados da categoria
     */
    public void validateOnUpdate(CategoryRequest request) {
        validateNameAndDescription(request.name(), request.description(), "Não pode ser atualizada, a categoria ativa com o mesmo nome e descrição já existe."
        );
    }

    /**
     * Método genérico para validar nome, descrição e status ativo de uma categoria.
     *
     * @param name        o nome da categoria
     * @param description a descrição da categoria
     * @param errorMessage a mensagem de erro a ser lançada
     */
    private void validateNameAndDescription(String name, String description, String errorMessage) {
        // Verifica se já existe uma categoria ativa com o mesmo nome e descrição
        if (repository.existsByNameAndDescriptionAndActive(name, description, true)) {
            throw new BusinessRuleException(errorMessage);
        }
    }

}
