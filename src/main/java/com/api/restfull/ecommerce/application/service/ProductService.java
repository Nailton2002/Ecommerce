package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.product.ProductRequest;
import com.api.restfull.ecommerce.application.request.product.ProductUpRequest;
import com.api.restfull.ecommerce.application.response.product.ProductListResponse;
import com.api.restfull.ecommerce.application.response.product.ProductResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    @Transactional
    ProductListResponse createProduct(ProductRequest request);

    @Transactional
    ProductListResponse getByIdProduct(Long id);

    @Transactional
    Page<ProductResponse> getAllPagedProducts(int page, int size);

    @Transactional
    ProductListResponse updateProduct(ProductUpRequest request);

    @Transactional
    List<ProductListResponse> getAllByParamProducts(Long id_cat);

    @Transactional
    List<ProductListResponse> getAllByParamNameProducts(String namecategory);

    @Transactional
    List<ProductResponse> searchByName(String name);

    @Transactional
    ProductListResponse getByNameProduct(String name);

    @Transactional
    List<ProductResponse> getByDescriptionProduct(String description);

    @Transactional
    List<ProductResponse> getSortedByPriceProducts(String order);

    @Transactional
    List<ProductResponse> getByQuantityProduct(Integer quantityStock);

    @Transactional
    ProductResponse desableProduct(Long id);

    @Transactional
    List<ProductResponse> getByStatusProducts(Boolean active);

    @Transactional
    void deleteProduct(Long id);
}
