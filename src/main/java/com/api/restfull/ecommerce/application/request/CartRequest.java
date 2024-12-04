package com.api.restfull.ecommerce.application.request;

import java.util.List;

public record CartRequest(

        Long customerId, // ID do usuário dono do carrinho
        List<CartItemRequest> items // Lista de itens no carrinho
) {
}
