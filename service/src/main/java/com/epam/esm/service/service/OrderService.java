package com.epam.esm.service.service;

import com.epam.esm.service.dto.order.OrderCertificatesDto;
import com.epam.esm.service.dto.order.OrderDetailsDto;

import java.util.Set;

public interface OrderService {
    OrderCertificatesDto save(OrderCertificatesDto orderCertificatesDto);

    OrderCertificatesDto getById(Long orderId);

    OrderCertificatesDto getUserOrderById(Long userId, Long orderId);

    Set<OrderDetailsDto> getAllUserOrders(Long userId);
}
