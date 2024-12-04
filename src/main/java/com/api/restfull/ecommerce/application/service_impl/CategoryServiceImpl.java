package com.api.restfull.ecommerce.application.service_impl;

import com.api.restfull.ecommerce.application.request.category.CategoryRequest;
import com.api.restfull.ecommerce.application.request.category.CategoryUpRequest;
import com.api.restfull.ecommerce.application.response.category.CategoryListResponse;
import com.api.restfull.ecommerce.application.response.category.CategoryResponse;
import com.api.restfull.ecommerce.application.service.CategoryService;
import com.api.restfull.ecommerce.domain.entity.Category;
import com.api.restfull.ecommerce.domain.exception.DataIntegrityValidationException;
import com.api.restfull.ecommerce.domain.exception.ExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundExceptionLogger;
import com.api.restfull.ecommerce.domain.repository.CategoryRepository;
import com.api.restfull.ecommerce.infra.util.CategoryUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryUtil categoryUtil;
    private final CategoryRepository repository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Override
    public CategoryResponse saveCategory(CategoryRequest request) {

        logger.info("Iniciando salvamento da categoria {}: request");

        try {
            // Verifica se já existe uma categoria ativa com o mesmo nome e descrição
            categoryUtil.validateOnSave(request);

            Category category = new Category(request);
            Category obj = repository.save(category);

            logger.info("Categoria salvo com sucesso: ID={}, Categoria={}");
            return new CategoryResponse(obj);

        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityValidationException("O nome da categoria já existe.");

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao salvar Categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


    @Override
    public CategoryListResponse findByIdCategory(Long id) {

        logger.info("Buscando Item do Pedido pelo ID: {}", id);

        try {
            Optional<Category> optionalCategory = repository.findById(id);
            logger.info("Categoria encontrado: ID={}, Categoria={}");

            return CategoryListResponse.fromEntityToResponse((optionalCategory.orElseThrow(() ->
                    new ResourceNotFoundException("Objeto não encontrado! Id: " + id + ", tipo: " + CategoryListResponse.class.getName()))));

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public List<CategoryResponse> findAllCategory() {
        List<Category> categoryList = repository.findAll();
        List<CategoryResponse> responseList = categoryList.stream().map(c -> new CategoryResponse(c)).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public CategoryResponse updateCategory(CategoryUpRequest request) {

        logger.info("Iniciando atualização da categoria ID: {}", request.id());
        try {
            // Busca a categoria pelo ID
            Category category = repository.findById(request.id()).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + request));

            logger.debug("Categoria encontrado para atualização: {}", request.name());

            // Verifica se já existe uma categoria ativa com o mesmo nome e descrição
            categoryUtil.validateOnUpdate(request);

            logger.debug("Categoria validada para atualização: {}", request.name());

            // Atualiza os dados da categoria
            category.updateCategory(request);

            logger.info("Dados da categoria atualizados", category.getId());

            // Salva a categoria atualizada
            Category updatedCategory = repository.save(category);

            logger.info("Categoria atualizada com sucesso: ID={}", updatedCategory.getId());

            // Retorna a resposta com os dados atualizados
            return new CategoryResponse(updatedCategory);

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao atualizar categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public CategoryResponse desableCategory(Long id) {

        logger.info("Iniciando a desabilitação da categoria ID: {}", id);
        try {

            Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category não encontrada com ID: " + id));
            logger.debug("Categoria encontrado para desabilitar: {}");

            category.desableCategory();
            repository.save(category);

            logger.info("Categoria desabilitada com sucesso: ID={}", id);
            return new CategoryResponse(category);

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao desabilitar categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public void deleteDesableCategory(Long id) {

        logger.info("Iniciando exclusão da categoria ID: {}", id);

        try {

            Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category não encontrada com ID: " + id));
            logger.debug("Categoria encontrado para deletar: {}");

            if (category.getActive()) {
                logger.warn("Tentativa de excluir categoria ativa: Status={}", category.getActive());
                throw new ResourceNotFoundException("Não é possível excluir uma category ativa.");
            }
            repository.delete(category);
            logger.info("Categoria excluído com sucesso: ID={}, Categoria={}", LocalDateTime.now());

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao excluir categoria: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


    @Override
    public CategoryResponse findByNameCategory(String name) {

        logger.info("Buscando categoria pelo nome: {}", name);
        try {
            Category category = repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("category com nome '" + name + "' não encontrada."));
            logger.info("Categoria encontrado: NAME={}, ");
            return new CategoryResponse(category);

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar categoria por nome: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public List<CategoryResponse> findByDescriptionCategory(String description) {

        logger.info("Buscando categoria por descrição: {}", description);
        try {
            List<Category> categoryList = repository.findByDescription(description);
            logger.info("Categoria encontrado: description={}, ");
            return categoryList.stream().map(CategoryResponse::new).toList();

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar categoria por descrição: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    @Override
    public List<CategoryResponse> findByActivesCategory(Boolean active) {

        logger.info("Buscando por categoria ativas: {}", active);
        try {

            List<Category> responseList = repository.findByActives(active);
            logger.info("Categoria ativas encontrado: active={}, ");
            return responseList.stream().map(CategoryResponse::new).toList();

        } catch (ResourceNotFoundExceptionLogger ex) {
            logger.error("Erro ao buscar categoria ativas: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


}
