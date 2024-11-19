package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import com.api.restfull.ecommerce.application.response.ProductResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    @Transactional
    CategoryResponse saveCategory(CategoryRequest request);

    @Transactional
    CategoryResponse findByIdCategory(Long id);

    @Transactional
    List<CategoryResponse> findAllCategory();

    @Transactional
    CategoryResponse updateCategory(Long id, CategoryRequest request);

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
