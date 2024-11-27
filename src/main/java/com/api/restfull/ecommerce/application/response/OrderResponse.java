package com.api.restfull.ecommerce.application.response;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.api.restfull.ecommerce.domain.entity.Order;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;
import com.api.restfull.ecommerce.domain.model.Address;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public record OrderResponse(
        Long id,
        String statusOrder,
        LocalDateTime creationDate,
        LocalDateTime expectedDeliveryDate,
        ClientResponse client,
        AddressDto  addressDelivery
) {

    // Construtor para mapear uma entidade Order para o DTO
    public OrderResponse(Order order) {
        this(
                order.getId(),
                order.getStatusOrder() != null ? String.valueOf(order.getStatusOrder()) : "Status não definido",
                // Usa o valor da data de criação se disponível, senão o horário atual
                order.getCreationDate() != null ? order.getCreationDate() : LocalDateTime.now(),
                // Usa a data de entrega prevista se disponível, senão o horário atual
                order.getExpectedDeliveryDate() != null ? order.getExpectedDeliveryDate() : LocalDateTime.now(),
                order.getClient() != null ? new ClientResponse(order.getClient()) : null,
                // Lógica para exibir endereço de entrega ou mensagem Lógica de validação encapsulada
                validateAddress(order)
        );
    }

    // Método para validar entre o endereço de cliente e o endereço de entrega preenchendo os campos faltantes
    public static AddressDto validateAddress(Order order) {

        Address clientAddress = order.getClient() != null ? order.getClient().getAddress() : null;
        Address deliveryAddress = order.getAddressDelivery();

        if (deliveryAddress == null && clientAddress == null) {
            return null;
        }
        return new AddressDto(
                deliveryAddress != null && deliveryAddress.getNeighborhood() != null
                        ? deliveryAddress.getNeighborhood()
                        : (clientAddress != null ? clientAddress.getNeighborhood() : null),
                deliveryAddress != null && deliveryAddress.getComplement() != null
                        ? deliveryAddress.getComplement()
                        : (clientAddress != null ? clientAddress.getComplement() : null),
                deliveryAddress != null && deliveryAddress.getCountry() != null
                        ? deliveryAddress.getCountry()
                        : (clientAddress != null ? clientAddress.getCountry() : null),
                deliveryAddress != null && deliveryAddress.getZipCode() != null
                        ? deliveryAddress.getZipCode()
                        : (clientAddress != null ? clientAddress.getZipCode() : null),
                deliveryAddress != null && deliveryAddress.getStreet() != null
                        ? deliveryAddress.getStreet()
                        : (clientAddress != null ? clientAddress.getStreet() : null),
                deliveryAddress != null && deliveryAddress.getNumber() != null
                        ? deliveryAddress.getNumber()
                        : (clientAddress != null ? clientAddress.getNumber() : null),
                deliveryAddress != null && deliveryAddress.getState() != null
                        ? deliveryAddress.getState()
                        : (clientAddress != null ? clientAddress.getState() : null),
                deliveryAddress != null && deliveryAddress.getCity() != null
                        ? deliveryAddress.getCity()
                        : (clientAddress != null ? clientAddress.getCity() : null),
                deliveryAddress != null && deliveryAddress.getUf() != null
                        ? deliveryAddress.getUf()
                        : (clientAddress != null ? clientAddress.getUf() : null)
        );
    }
}
