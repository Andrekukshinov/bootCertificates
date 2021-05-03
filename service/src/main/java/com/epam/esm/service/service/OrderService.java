package com.epam.esm.service.service;

import com.epam.esm.persistence.model.page.Page;
import com.epam.esm.persistence.model.page.Pageable;
import com.epam.esm.service.dto.order.OrderCertificatesDto;
import com.epam.esm.service.dto.order.OrderDetailsDto;


public interface OrderService {
    OrderCertificatesDto save(OrderCertificatesDto orderCertificatesDto);

    OrderCertificatesDto getById(Long orderId);

    OrderCertificatesDto getUserOrderById(Long userId, Long orderId);

    Page<OrderDetailsDto> getAllUserOrders(Long userId, Pageable pageable);
}
