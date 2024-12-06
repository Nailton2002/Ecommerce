package com.api.restfull.ecommerce.application.service_impl;

import com.api.restfull.ecommerce.application.request.CartItemRequest;
import com.api.restfull.ecommerce.application.request.CartRequest;
import com.api.restfull.ecommerce.application.response.CartResponse;
import com.api.restfull.ecommerce.application.response.OrderItemResponse;
import com.api.restfull.ecommerce.application.service.CartService;
import com.api.restfull.ecommerce.domain.entity.Cart;
import com.api.restfull.ecommerce.domain.entity.CartItem;
import com.api.restfull.ecommerce.domain.entity.Customer;
import com.api.restfull.ecommerce.domain.entity.Product;
import com.api.restfull.ecommerce.domain.exception.ExceptionLogger;
import com.api.restfull.ecommerce.domain.exception.ResourceNotFoundException;
import com.api.restfull.ecommerce.domain.repository.CartItemRepository;
import com.api.restfull.ecommerce.domain.repository.CartRepository;
import com.api.restfull.ecommerce.domain.repository.CustomerRepository;
import com.api.restfull.ecommerce.domain.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final CustomerRepository customerRepository;

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);


    @Override
    public CartResponse createCart(CartRequest request) {

        logger.info("Iniciando salvamento de Item do Pedido para o carrinho ID: {}, Cart ID: {}", request);

        try {
            // Verifica se o cliente existe
            Customer user = customerRepository.findById(request.customerId()).orElseThrow(() ->
                    new ResourceNotFoundException("User not found with ID: " + request.customerId()));

            // Cria uma nova instância de Cart
            Cart cart = new Cart();
            cart.setCustomer(user);
            cart.setCreationDate(LocalDateTime.now());

            // Inicializa a lista de itens
            List<CartItem> items = request.items().stream().map(itemRequest -> {
                Product product = productRepository.findById(itemRequest.productId()).orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with ID: " + itemRequest.productId()));

                CartItem item = new CartItem();
                item.setProduct(product);
                item.setQuantity(itemRequest.quantity());
                item.setCart(cart);

                // Calcula o total_price do item
                BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity()));
                item.setTotalPrice(totalPrice);
                return item;

            }).collect(Collectors.toList());
            cart.setItems(items);

            // Calcula o total do carrinho
            BigDecimal total = items.stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add);
            cart.setTotal(total);

            // Salva o carrinho no banco
            Cart savedCart = cartRepository.save(cart);

            // Salva os itens associados ao carrinho
            cartItemRepository.saveAll(items);

            logger.info("Item do Pedido salvo com sucesso no carrinho: ID={}, Pedido={}, Produto={}", savedCart.getId());

            // Retorna a resposta do carrinho criado
            return CartResponse.fromCartToResponse(savedCart);

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao salvar Item do Pedido no carrinho: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


    @Override
    public CartResponse addItemToCart(Long cartId, CartItemRequest request) {

        logger.info("Adicionando Item do Pedido ao carrinho ID: {}", request.productId(), request.quantity());
        try {

            // Verifica se o carrinho existe
            Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                    new ResourceNotFoundException("Carrinho não encontrado com ID: " + cartId));

            // Verifica se o produto existe
            Product product = productRepository.findById(request.productId()).orElseThrow(() ->
                    new ResourceNotFoundException("Produto não encontrado com ID: " + request.productId()));

            // Cria o CartItem e associa ao Cart
            CartItem cartItem = new CartItem(cart, product, request.quantity());
            cartItemRepository.save(cartItem);

            // Atualiza e adiciona os itens no carrinho
            cart.getItems().add(cartItem);
            cart.setTotal(cart.getItems().stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

            cartRepository.save(cart); // Salva o carrinho atualizado

            // Retorna a resposta do carrinho criado
            return CartResponse.fromCartToResponse(cart);

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao Adicionando Item do Pedido ao carrinho: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


    @Override
    public CartResponse removeItemToCart(Long cartId, CartItemRequest request) {

        logger.info("Removendo Item do Pedido no carrinho ID: {}", request.productId(), request.quantity());
        try {

            // Verifica se o carrinho existe
            Cart cart = cartRepository.findById(cartId).orElseThrow(() ->
                    new ResourceNotFoundException("Carrinho não encontrado com ID: " + cartId));

            // Verifica se o produto existe
            Product product = productRepository.findById(request.productId()).orElseThrow(() ->
                    new ResourceNotFoundException("Produto não encontrado com ID: " + request.productId()));

            // Busca o item no carrinho
            CartItem cartItem = cart.getItems()
                    .stream()
                    .filter(item -> item.getProduct().getId().equals(request.productId()))
                    .findFirst()
                    .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado no carrinho com ID: " + request.productId()));

            // Remove o item do carrinho
            cart.getItems().remove(cartItem);

            // Remove o item da base de dados
            cartItemRepository.delete(cartItem);

            // Atualiza o total do carrinho
            cart.setTotal(cart.getItems().stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add));

            // Salva o carrinho atualizado
            cartRepository.save(cart);

            // Retorna a resposta do carrinho atualizado
            return CartResponse.fromCartToResponse(cart);

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao remover Item do Pedido ao carrinho: {}", ex.getMessage(), ex);
            throw ex;
        }
    }

    public CartResponse clearCart(Long cartId) {

        logger.info("Limpando Item do Pedido no carrinho ID: {}", cartId);
        try {

            // Recupera o carrinho pelo ID
            Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("Carrinho não encontrado com ID: " + cartId));

            // Limpa os itens do carrinho
            cart.getItems().clear();

            // Atualiza o total do carrinho para zero
            cart.setTotal(BigDecimal.ZERO);

            // Salva o estado atualizado do carrinho
            cartRepository.save(cart);

            // Retorna a resposta do carrinho atualizado
            return CartResponse.fromCartToResponse(cart);

        } catch (ExceptionLogger ex) {
            logger.error("Erro ao limpar Item do Pedido ao carrinho: {}", ex.getMessage(), ex);
            throw ex;
        }
    }


}
