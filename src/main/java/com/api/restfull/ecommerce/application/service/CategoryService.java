package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.category.CategoryRequest;
import com.api.restfull.ecommerce.application.request.category.CategoryUpdateRequest;
import com.api.restfull.ecommerce.application.response.category.CategoryListResponse;
import com.api.restfull.ecommerce.application.response.category.CategoryResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    @Transactional
    CategoryResponse saveCategory(CategoryRequest request);

    @Transactional
    CategoryListResponse findByIdCategory(Long id);

    @Transactional
    List<CategoryResponse> findAllCategory();

    @Transactional
    CategoryResponse updateCategory(CategoryUpdateRequest request);

    @Transactional
    CategoryResponse findByNameCategory(String name);

    @Transactional
    List<CategoryResponse> findByDescriptionCategory(String description);

    @Transactional
    List<CategoryResponse> finByActivesCategory(Boolean active);

    @Transactional
    CategoryResponse desableCategory(Long id);

    void deleteDesableCategory(Long id);

}
