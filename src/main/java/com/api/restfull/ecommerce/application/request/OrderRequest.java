package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record OrderRequest(
        Long id,
        // client associado a order por id
        @NotNull
        Long clientId,
        // Conjunto de produtos do order por ids
        @NotNull
        List<Long> productIds,
        // Endereço de entrega
        @Valid @NotNull(message = "Dados do endereço são obrigatórios")
        AddressDto addressDelivery,
        @Column(name = "Pendente") @Enumerated(EnumType.STRING)
        StatusOrder statusOrder,
        // sumOfItemsOfOrders calculado do order (cálculo dinâmico com base nos itens)
        Double sumOfItemsOfOrders,
        // Datas de criação e última atualização do order
        LocalDateTime creationDate,
        // Data prevista para entrega
        LocalDateTime expectedDeliveryDate,
        // Data de atualização para entrega
        LocalDateTime lastUpdateDate
) {


}
