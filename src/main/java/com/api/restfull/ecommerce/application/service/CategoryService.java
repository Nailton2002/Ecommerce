package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    @Transactional
    CategoryResponse save(CategoryRequest request);

    @Transactional
    CategoryResponse update(Long id, CategoryRequest request);

    @Transactional
    CategoryResponse findByNameCategory(String nome);

    @Transactional
    CategoryResponse desableCategory(Long id);

    CategoryResponse findById(Long id);

    List<CategoryResponse> findAll();

    void deleteCategoryDesable(Long id);



}
