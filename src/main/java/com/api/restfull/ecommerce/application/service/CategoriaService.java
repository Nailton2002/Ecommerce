package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoriaService {

    @Transactional
    CategoryResponse save(CategoryRequest request);

    @Transactional
    CategoryResponse update(CategoryRequest request);

    @Transactional
    CategoryResponse findByNameCategory(String nomeCategoria);

    CategoryResponse findById(Long id);

    List<CategoryResponse> findAll();

    void deleteById(Long id);
}
