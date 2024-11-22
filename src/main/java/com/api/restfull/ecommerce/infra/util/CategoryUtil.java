package com.api.restfull.ecommerce.infra.util;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.service_imple.ClientServiceImpl;
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

    public void validationNameAndDescriptionAndActive(CategoryRequest request) {

        // Verifica se já existe uma categoria ativa com o mesmo nome e descrição
        if (repository.existsByNameAndDescriptionAndActive(request.name(), request.description(), true)) {
            throw new BusinessRuleException("Categoria ativa com o mesmo nome e descrição já existe.");
        }
    }
}
