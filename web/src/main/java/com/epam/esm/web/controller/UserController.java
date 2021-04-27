package com.epam.esm.web.controller;

import com.epam.esm.service.dto.order.OrderCertificatesDto;
import com.epam.esm.service.dto.order.OrderDetailsDto;
import com.epam.esm.service.dto.user.UserInfoDto;
import com.epam.esm.service.dto.user.UserOrderDto;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;
    private final OrderService orderService;

    @Autowired
    public UserController(UserService service, OrderService orderService) {
        this.service = service;
        this.orderService = orderService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOrderDto> findById(@PathVariable Long id) {
        UserOrderDto user = service.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping()
    public ResponseEntity<Set<UserInfoDto>> getAll() {
        Set<UserInfoDto> users = service.getAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<OrderCertificatesDto> getUserOrderDetails(@PathVariable Long orderId, @PathVariable Long userId) {
        OrderCertificatesDto found = orderService.getUserOrderById(userId, orderId);
        return ResponseEntity.ok(found);
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderCertificatesDto> saveUserOrder(@PathVariable Long userId, @RequestBody OrderCertificatesDto orderCertificatesDto) throws ValidationException {
        orderCertificatesDto.setUserId(userId);
        OrderCertificatesDto saved = orderService.save(orderCertificatesDto);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{user_id}/orders")
    public ResponseEntity<Set<OrderDetailsDto>> getUserAllOrders(@PathVariable("user_id") Long userId) {
        Set<OrderDetailsDto> orders = orderService.getAllUserOrders(userId);
        return ResponseEntity.ok(orders);
    }

}
///{user_id}/orders/{order_id}
