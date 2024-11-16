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
        Category category = Category.fromRequestToEntity(request);
        Category obj = repository.save(category);
        return CategoryResponse.fromEntityToResponse(obj);
    }

    @Override
    public CategoryResponse update(Long id, CategoryRequest request) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isPresent()) {
            Category obj = optionalCategory.get();
            obj.updateCategory(request);
            Category categoryUpdate = repository.save(obj);
            return CategoryResponse.fromEntityToResponse(categoryUpdate);
        } else {
            throw new ResourceNotFoundException("Categoria não encontrado com o ID: " + id);
        }
    }

    @Override
    public CategoryResponse findByNameCategory(String nome) {
        Category category = repository.findByNome(nome);
        return CategoryResponse.fromEntityToResponse(category);
    }

    @Override
    public CategoryResponse findById(Long id) {
        Optional<Category> optionalCategory = repository.findById(id);
        return CategoryResponse.fromEntityToResponse(optionalCategory.orElseThrow(() ->
        new ResourceNotFoundException("Objeto não encontrado! Id: " + id + ", tipo: " + CategoryResponse.class.getName())));
    }

    @Override
    public List<CategoryResponse> findAll() {
        List<Category> categoryList = repository.findAll();
        List<CategoryResponse> responseList = categoryList.stream().map(c -> CategoryResponse.fromEntityToResponse(c)).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public void deleteById(Long id) {
        Category categoria = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
//        if (!categoria.getProducts().isEmpty()) {
//            throw new ResourceNotFoundException("Não é possível excluir uma categoria com produtos associados.");
//        }
        repository.delete(categoria);
    }
}
