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
        Category categoria = categoryRepository.findById(request.categoriaId()).orElseThrow(() -> new ResourceNotFoundException("ID não encontrado"));
        product.setCategoria(categoria);
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
        // Chama o repositório para obter a página de produtos
        Page<Product> productPage = repository.findAll(pageable);
        // Converte os produtos em ProductResponse
        return productPage.map(ProductResponse::new);
    }

    @Override
    public ProductResponse updateProduct(ProductRequest request) {
        // Verifica se o produto existe
        Optional<Product> productOptional = repository.findById(request.id());
        if (productOptional.isEmpty()) {
            throw new ResourceNotFoundException("Produto não encontrado com o ID: " + request.id());
        }
        // Verifica se a categoria existe
        if (request.categoriaId() != null) { // Apenas verifica se o ID da categoria foi fornecido
            boolean categoryExists = categoryRepository.existsById(request.categoriaId());
            if (!categoryExists) {
                throw new ResourceNotFoundException("Categoria não encontrada com o ID: " + request.categoriaId());
            }
        }
        // Atualiza o produto
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
    public List<ProductResponse> getAllByParamNameProducts(String nomeCategoria) {
        // Buscar categoria pelo nome
        var category = categoryRepository.findByNome(nomeCategoria);
        // Se não encontrar, retorna uma lista vazia
        if (category == null) {
            return List.of();
        }
        // Buscar os produtos da categoria encontrada
        var products = repository.findByCategory(category.get().getId());
        // Converter para DTO e retornar
        return products.stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @Override
    public ProductResponse getByNameProduct(String nome) {
        Product product = repository.findByName(nome).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com o nome: " + nome));
        return new ProductResponse(product);
    }

    @Override
    public List<ProductResponse> searchByName(String nome) {
        List<Product> products = repository.findByNameContainingIgnoreCase(nome);
        return products.stream().map(ProductResponse::new).collect(Collectors.toList());
    }


    @Override
    public List<ProductResponse> getByDescriptionProduct(String descricao) {
        List<Product> list = repository.findByDescription(descricao);
        List<ProductResponse> responseList = list.stream().map(ProductResponse::new).toList();
        return responseList;
    }

    @Override
    public List<ProductResponse> getSortedByPriceProducts(String preco) {
        Sort sortOrder = preco.equalsIgnoreCase("desc") ? Sort.by("preco").descending() : Sort.by("preco").ascending();
        List<Product> products = repository.findAll(sortOrder);
        return products.stream().map(ProductResponse::new).collect(Collectors.toList());
    }

    @Override
    public List<ProductResponse> getByQuantityProduct(Integer quantidadeEstoque) {
        List<Product> list = repository.findByQuantity(quantidadeEstoque);
        List<ProductResponse> responseList = list.stream().map(ProductResponse::new).toList();
        return responseList;
    }

    @Override
    public List<ProductResponse> getByStatusProducts(Boolean ativo) {
        List<Product> products = repository.findByAtivo(ativo);
        return products.stream().map(ProductResponse::new).toList();
    }

    @Override
    public ProductResponse desableProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Categoria não encontrada com ID: " + id));
        product.desableProduct();
        repository.save(product);
        return new ProductResponse(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Produto não encontrada com ID: " + id));
        if (product.getAtivo()) {
            throw new ResourceNotFoundException("Não é possível excluir um produto ativo.");
        }
       repository.delete(product);
    }
}
