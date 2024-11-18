package com.api.restfull.ecommerce.application.service_imple;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import com.api.restfull.ecommerce.application.service.CategoryService;
import com.api.restfull.ecommerce.domain.entity.Category;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;

    @Override
    public CategoryResponse save(CategoryRequest request) {
        Category category = new Category(request);
        Category obj = repository.save(category);
        return new CategoryResponse(obj);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isPresent()) {
            Category obj = optionalCategory.get();
            obj.updateCategory(request);
            Category categoryUpdate = repository.save(obj);
            return new CategoryResponse(categoryUpdate);
        } else {
            throw new ResourceNotFoundException("Categoria não encontrado com o ID: " + id);
        }
    }

    @Override
    public CategoryResponse desableCategory(Long id) {
        Category categoria = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
        categoria.desableCategory();
        repository.save(categoria);
        return new CategoryResponse(categoria);
    }

    @Override
    public void deleteCategoryDesable(Long id) {
        Category categoria = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
        if (categoria.getAtivo()) {
            throw new ResourceNotFoundException("Não é possível excluir uma categoria ativa.");
        }
        repository.delete(categoria);
    }

    @Override
    public CategoryResponse findByNameCategory(String nome) {
        Category category = repository.findByNome(nome).orElseThrow(() -> new ResourceNotFoundException("Categoria com nome '" + nome + "' não encontrada."));
        return new CategoryResponse(category);
    }

    @Override
    public CategoryResponse findById(Long id) {
        Optional<Category> optionalCategory = repository.findById(id);
        return new CategoryResponse(optionalCategory.orElseThrow(() ->
                new ResourceNotFoundException("Objeto não encontrado! Id: " + id + ", tipo: " + CategoryResponse.class.getName())));
    }

    @Override
    public List<CategoryResponse> findAll() {
        List<Category> categoryList = repository.findAll();
        List<CategoryResponse> responseList = categoryList.stream().map(c -> new CategoryResponse(c)).collect(Collectors.toList());
        return responseList;
    }



}
