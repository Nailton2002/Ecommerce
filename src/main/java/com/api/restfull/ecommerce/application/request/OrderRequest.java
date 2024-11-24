package com.api.restfull.ecommerce.application.request;

import com.api.restfull.ecommerce.application.dto.AddressDto;
import com.api.restfull.ecommerce.application.response.OrderItemResponse;
import com.api.restfull.ecommerce.application.response.PaymentResponse;
import com.api.restfull.ecommerce.application.response.ProductResponse;
import com.api.restfull.ecommerce.domain.entity.Order;
import com.api.restfull.ecommerce.domain.entity.OrderItem;
import com.api.restfull.ecommerce.domain.entity.Payment;
import com.api.restfull.ecommerce.domain.entity.Product;
import com.api.restfull.ecommerce.domain.enums.StatusOrder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record OrderRequest(
        Long id,
        Long clientId,
        List<Long> productIds,
        List<Long> orderItenIds,
        List<Long> paymentIds,
        StatusOrder statusOrder,
        Double sumOfItemsOfOrders,
        LocalDateTime creationDate,
        LocalDateTime expectedDeliveryDate,
        AddressDto addressDelivery

) {

}
