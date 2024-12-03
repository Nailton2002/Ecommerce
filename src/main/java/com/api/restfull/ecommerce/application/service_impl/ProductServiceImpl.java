package com.api.restfull.ecommerce.application.service_impl;

import com.api.restfull.ecommerce.application.request.product.ProductRequest;
import com.api.restfull.ecommerce.application.request.product.ProductUpRequest;
import com.api.restfull.ecommerce.application.response.category.CategoryListResponse;
import com.api.restfull.ecommerce.application.response.product.ProductListResponse;
import com.api.restfull.ecommerce.application.response.product.ProductResponse;
import com.api.restfull.ecommerce.application.service.CategoryService;
import com.api.restfull.ecommerce.application.service.ProductService;
import com.api.restfull.ecommerce.domain.entity.Category;
import com.api.restfull.ecommerce.domain.entity.Product;
import com.api.restfull.ecommerce.domain.exception.BusinessRuleException;
import com.api.restfull.ecommerce.domain.exception.DataIntegrityValidationException;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.repository.CategoryRepository;
import com.api.restfull.ecommerce.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ProductRepository repository;

    @Override
    public ProductListResponse createProduct(ProductRequest request) {

        // Busca produtos ativos com o mesmo nome e descrição já existe
        List<Product> productsWithSameName = repository.findByNameActive(request.name());

        // Valida se existe um produto ativo com o mesmo nome e descrição
        boolean existsActiveProductWithSameDescription = productsWithSameName.stream().anyMatch(product ->
                product.getActive() && product.getDescription().equalsIgnoreCase(request.description()));

        if (existsActiveProductWithSameDescription) {
            throw new BusinessRuleException("Produto ativo com o mesmo nome e descrição já existe.");
        }

        try {

            // Busca a categoria e cria o produto
            Category category = categoryRepository.findById(request.categoryId()).orElseThrow(
                    () -> new ResourceNotFoundException("Categoria não encontrada para o ID: " + request.categoryId()));

            Product product = new Product(request);
            product.setCategory(category);
            Product savedProduct = repository.save(product);
            return new ProductListResponse(savedProduct);

        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityValidationException("O nome do produto já existe.");
        }
    }

    @Override
    public ProductListResponse updateProduct(ProductUpRequest request) {

        // Verifica se o produto existe
        Product product = repository.findById(request.id())
                .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o ID: " + request.id()));

        // Valida se outro produto com o mesmo nome e descrição já existe e está ativo
        List<Product> productsWithSameName = repository.findByNameActive(request.name());
        boolean existsActiveProductWithSameDescription = productsWithSameName.stream().anyMatch(existingProduct ->
                existingProduct.getId() != request.id() && existingProduct.getDescription().equalsIgnoreCase(request.description()));

        if (existsActiveProductWithSameDescription) {
            throw new BusinessRuleException("Outro produto ativo com o mesmo nome e descrição já existe.");
        }

        // Verifica se a categoria fornecida existe, se foi alterada
        if (request.categoryId() != null && !product.getCategory().getId().equals(request.categoryId())) {
            Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new ResourceNotFoundException(
                    "Categoria não encontrada com o ID: " + request.categoryId()));
            product.setCategory(category);
        }

        try {

            // Atualiza os dados do produto
            product.updateProduct(request);

            // Salva as alterações no banco
            Product updatedProduct = repository.save(product);

            // Retorna o produto atualizado
            return new ProductListResponse(updatedProduct);

        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityValidationException("O nome do produto já existe.");
        }
    }

    @Override
    public ProductListResponse getByIdProduct(Long id) {
        Product obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + ProductResponse.class.getName()));
        return new ProductListResponse(obj);
    }

    @Override
    public Page<ProductResponse> getAllPagedProducts(int page, int size) {
        // Cria uma Pageable para paginar os dados
        Pageable pageable = PageRequest.of(page, size);

        // Chama o repositório para obter a página de products
        Page<Product> productPage = repository.findAll(pageable);

        // Converte os products em ProductResponse
        return productPage.map(ProductResponse::new);
    }

    @Override
    public List<ProductListResponse> getAllByParamProducts(Long id_cat) {
        CategoryListResponse response = categoryService.findByIdCategory(id_cat);
        List<Product> productdList = repository.findByCategory(response.id());
        List<ProductListResponse> responseList = productdList.stream().map(ProductListResponse::new).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public List<ProductListResponse> getAllByParamNameProducts(String namecategory) {
        // Buscar category pelo name
        var category = categoryRepository.findByName(namecategory);

        // Se não encontrar, retorna uma lista vazia
        if (category == null) {
            return List.of();
        }
        // Buscar os products da category encontrada
        var products = repository.findByCategory(category.get().getId());

        // Converter para DTO e retornar
        return products.stream().map(ProductListResponse::new).collect(Collectors.toList());
    }

    @Override
    public ProductListResponse getByNameProduct(String name) {
        Product product = repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("product não encontrado com o name: " + name));
        return new ProductListResponse(product);
    }

    @Override
    public List<ProductResponse> searchByName(String name) {
        List<Product> products = repository.findByNameContainingIgnoreCase(name);
        return products.stream().map(ProductResponse::new).collect(Collectors.toList());
    }


    @Override
    public List<ProductResponse> getByDescriptionProduct(String description) {
        List<Product> list = repository.findByDescription(description);
        List<ProductResponse> responseList = list.stream().map(ProductResponse::new).toList();
        return responseList;
    }

    @Override
    public List<ProductResponse> getSortedByPriceProducts(String price) {
        Sort sortOrder = price.equalsIgnoreCase("desc") ? Sort.by("price").descending() : Sort.by("price").ascending();
        List<Product> products = repository.findAll(sortOrder);
        return products.stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getByQuantityProduct(Integer quantityStock) {
        List<Product> list = repository.findByQuantity(quantityStock);
        List<ProductResponse> responseList = list.stream().map(ProductResponse::new).toList();
        return responseList;
    }

    @Override
    public List<ProductResponse> getByStatusProducts(Boolean active) {
        List<Product> products = repository.findByActives(active);
        return products.stream().map(ProductResponse::new).toList();
    }

    @Override
    public ProductResponse desableProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("category não encontrada com ID: " + id));
        product.desableProduct();
        repository.save(product);
        return new ProductResponse(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("product não encontrada com ID: " + id));
        if (product.getActive()) {
            throw new ResourceNotFoundException("Não é possível excluir um product active.");
        }
        repository.delete(product);
    }
}
