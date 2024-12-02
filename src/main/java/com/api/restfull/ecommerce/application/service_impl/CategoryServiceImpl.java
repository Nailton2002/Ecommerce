package com.api.restfull.ecommerce.application.service_impl;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import com.api.restfull.ecommerce.application.service.CategoryService;
import com.api.restfull.ecommerce.domain.entity.Category;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.repository.CategoryRepository;
import com.api.restfull.ecommerce.infra.util.CategoryUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    private final CategoryUtil categoryUtil;

    @Override
    public CategoryResponse saveCategory(CategoryRequest request) {
        // Verifica se já existe uma categoria ativa com o mesmo nome e descrição
        categoryUtil.validateOnSave(request);

        Category category = new Category(request);
        Category obj = repository.save(category);
        return new CategoryResponse(obj);
    }


    @Override
    public CategoryResponse findByIdCategory(Long id) {
        Optional<Category> optionalCategory = repository.findById(id);
        return new CategoryResponse(optionalCategory.orElseThrow(() ->
        new ResourceNotFoundException("Objeto não encontrado! Id: " + id + ", tipo: " + CategoryResponse.class.getName())));
    }

    @Override
    public List<CategoryResponse> findAllCategory() {
        List<Category> categoryList = repository.findAll();
        List<CategoryResponse> responseList = categoryList.stream().map(c -> new CategoryResponse(c)).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public CategoryResponse updateCategory(CategoryRequest request) {
        // Busca a categoria pelo ID
        Category category = repository.findById(request.id()).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com o ID: " + request));

        // Verifica se já existe uma categoria ativa com o mesmo nome e descrição
        categoryUtil.validateOnUpdate(request);

        // Atualiza os dados da categoria
        category.updateCategory(request);

        // Salva a categoria atualizada
        Category updatedCategory = repository.save(category);

        // Retorna a resposta com os dados atualizados
        return new CategoryResponse(updatedCategory);
    }

    @Override
    public CategoryResponse desableCategory(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category não encontrada com ID: " + id));
        category.desableCategory();
        repository.save(category);
        return new CategoryResponse(category);
    }

    @Override
    public void deleteDesableCategory(Long id) {
        Category category = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category não encontrada com ID: " + id));
        if (category.getActive()) {
            throw new ResourceNotFoundException("Não é possível excluir uma category ativa.");
        }
        repository.delete(category);
    }

    @Override
    public CategoryResponse findByNameCategory(String name) {
        Category category = repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("category com name '" + name + "' não encontrada."));
        return new CategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> findByDescriptionCategory(String description) {
        List<Category> categoryList = repository.findByDescription(description);
        return categoryList.stream().map(CategoryResponse::new).toList();
    }

    @Override
    public List<CategoryResponse> finByActivesCategory(Boolean active) {
        List<Category> responseList = repository.findByActives(active);
        return responseList.stream().map(CategoryResponse::new).toList();
    }


}
