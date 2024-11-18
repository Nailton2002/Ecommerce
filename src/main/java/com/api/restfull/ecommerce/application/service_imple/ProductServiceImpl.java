package com.api.restfull.ecommerce.application.service_imple;

import com.api.restfull.ecommerce.application.request.ProductRequest;
import com.api.restfull.ecommerce.application.response.ProductResponse;
import com.api.restfull.ecommerce.application.service.ProductService;
import com.api.restfull.ecommerce.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    @Override
    public ProductResponse save(ProductRequest request) {
        return null;
    }

    @Override
    public ProductResponse update(Long id, ProductRequest request) {
        return null;
    }

    @Override
    public ProductResponse findByNameProduct(String nome) {
        return null;
    }

    @Override
    public ProductResponse desableProduct(Long id) {
        return null;
    }

    @Override
    public ProductResponse findById(Long id) {
        return null;
    }

    @Override
    public List<ProductResponse> findAll() {
        return List.of();
    }

    @Override
    public void deleteCategoryDesable(Long id) {

    }
}
