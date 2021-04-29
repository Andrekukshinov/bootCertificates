package com.epam.esm.service.service;

import com.epam.esm.service.dto.order.OrderCertificatesDto;
import com.epam.esm.service.dto.order.OrderDetailsDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderCertificatesDto save(OrderCertificatesDto orderCertificatesDto);

    OrderCertificatesDto getById(Long orderId);

    OrderCertificatesDto getUserOrderById(Long userId, Long orderId);

    Page<OrderDetailsDto> getAllUserOrders(Long userId, Pageable pageable);
}
