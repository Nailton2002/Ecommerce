package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.CategoryRequest;
import com.api.restfull.ecommerce.application.request.ProductRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import com.api.restfull.ecommerce.application.response.ProductResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    @Transactional
    ProductResponse save(ProductRequest request);

    @Transactional
    ProductResponse update(Long id, ProductRequest request);

    @Transactional
    ProductResponse findByNameProduct(String nome);

    @Transactional
    ProductResponse desableProduct(Long id);

    ProductResponse findById(Long id);

    List<ProductResponse> findAll();

    void deleteCategoryDesable(Long id);
}
