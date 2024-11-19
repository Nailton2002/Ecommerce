package com.api.restfull.ecommerce.application.service_imple;

import com.api.restfull.ecommerce.application.request.ProductRequest;
import com.api.restfull.ecommerce.application.response.CategoryResponse;
import com.api.restfull.ecommerce.application.response.ProductResponse;
import com.api.restfull.ecommerce.application.service.CategoryService;
import com.api.restfull.ecommerce.application.service.ProductService;
import com.api.restfull.ecommerce.domain.entity.Category;
import com.api.restfull.ecommerce.domain.entity.Product;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.repository.CategoryRepository;
import com.api.restfull.ecommerce.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final CategoryRepository categoryRepository;
    private final CategoryService categoryService;
    private final ProductRepository repository;

    @Override
    public ProductResponse createProduct(ProductRequest request) {
        Product product = new Product(request);
        Category category = categoryRepository.findById(request.categoryId()).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
        product.setcategory(category);
        repository.save(product);
        return new ProductResponse(product);
    }

    @Override
    public ProductResponse getByIdProduct(Long id) {
        Product obj = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + ProductResponse.class.getName()));
        return new ProductResponse(obj);
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
    public ProductResponse updateProduct(ProductRequest request) {
        // Verifica se o product existe
        Optional<Product> productOptional = repository.findById(request.id());
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("product não encontrado com o ID: " + request.id());
        }
        // Verifica se a category existe
        if (request.categoryId() != null) { // Apenas verifica se o ID da category foi fornecido
            boolean categoryExists = categoryRepository.existsById(request.categoryId());
            if (!categoryExists) {
                throw new ResourceNotFoundException("category não encontrada com o ID: " + request.categoryId());
            }
        }
        // Atualiza o product
        Product obj = productOptional.get();
        obj.updateProduct(request);
        Product productUpdate = repository.save(obj);
        return new ProductResponse(productUpdate);
    }

    @Override
    public List<ProductResponse> getAllByParamProducts(Long id_cat) {
        CategoryResponse response = categoryService.findByIdCategory(id_cat);
        List<Product> productdList = repository.findByCategory(response.id());
        List<ProductResponse> responseList = productdList.stream().map(ProductResponse::new).collect(Collectors.toList());
        return responseList;
    }

    @Override
    public List<ProductResponse> getAllByParamNameProducts(String namecategory) {
        // Buscar category pelo name
        var category = categoryRepository.findByName(namecategory);
        // Se não encontrar, retorna uma lista vazia
        if (category == null) {
            return List.of();
        }
        // Buscar os products da category encontrada
        var products = repository.findByCategory(category.get().getId());
        // Converter para DTO e retornar
        return products.stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getByNameProduct(String name) {
        Product product = repository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("product não encontrado com o name: " + name));
        return new ProductResponse(product);
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
        if (product.getactive()) {
            throw new ResourceNotFoundException("Não é possível excluir um product active.");
        }
       repository.delete(product);
    }
}
