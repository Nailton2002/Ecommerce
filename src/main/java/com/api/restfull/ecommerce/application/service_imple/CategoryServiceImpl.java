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
    public CategoryResponse saveCategory(CategoryRequest request) {
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
    public CategoryResponse updateCategory(Long id, CategoryRequest request) {
        Optional<Category> optionalCategory = repository.findById(id);
        if (optionalCategory.isPresent()) {
            Category obj = optionalCategory.get();
            obj.updateCategory(request);
            Category categoryUpdate = repository.save(obj);
            return new CategoryResponse(categoryUpdate);
        } else {
            throw new ResourceNotFoundException("category não encontrado com o ID: " + id);
        }
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
        if (category.getactive()) {
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
