package com.api.restfull.ecommerce.application.service;

import com.api.restfull.ecommerce.application.request.ProductRequest;
import com.api.restfull.ecommerce.application.response.ProductResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {

    @Transactional
    ProductResponse createProduct(ProductRequest request);

    @Transactional
    ProductResponse getByIdProduct(Long id);

    @Transactional
    Page<ProductResponse> getAllPagedProducts(int page, int size);

    @Transactional
    ProductResponse updateProduct(ProductRequest request);

    @Transactional
    List<ProductResponse> getAllByParamProducts(Long id_cat);

    @Transactional
    List<ProductResponse> getAllByParamNameProducts(String namecategory);

    @Transactional
    List<ProductResponse> searchByName(String name);

    @Transactional
    ProductResponse getByNameProduct(String name);

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
