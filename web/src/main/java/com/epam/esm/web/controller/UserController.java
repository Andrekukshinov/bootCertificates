package com.epam.esm.web.controller;

import com.epam.esm.service.dto.order.OrderCertificatesDto;
import com.epam.esm.service.dto.order.OrderDetailsDto;
import com.epam.esm.service.dto.user.UserInfoDto;
import com.epam.esm.service.dto.user.UserOrderDto;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;
    private final OrderService orderService;
    private final PagedResourcesAssembler<UserInfoDto> userInfoAssembler;
    private final PagedResourcesAssembler<OrderDetailsDto> orderAssembler;

    @Autowired
    public UserController(UserService service, OrderService orderService, PagedResourcesAssembler<UserInfoDto> userInfoAssembler, PagedResourcesAssembler<OrderDetailsDto> orderAssembler) {
        this.service = service;
        this.orderService = orderService;
        this.userInfoAssembler = userInfoAssembler;

        this.orderAssembler = orderAssembler;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOrderDto> findById(@PathVariable Long id) {
        UserOrderDto user = service.getById(id);
        user.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).getAllUsers(null)).withRel("all_users"));
        user.add(linkTo(methodOn(UserController.class).getUserAllOrders(user.getId(), null)).withRel("all_orders"));
        return ResponseEntity.ok(user);
    }

    @GetMapping()
    public ResponseEntity<PagedModel<EntityModel<UserInfoDto>>> getAllUsers(Pageable pageable) {
        Page<UserInfoDto> users = service.getAll(pageable);
        PagedModel<EntityModel<UserInfoDto>> entityModels = userInfoAssembler.toModel(users);
        return ResponseEntity.ok(entityModels);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<OrderCertificatesDto> getUserOrderDetails(@PathVariable Long userId, @PathVariable Long orderId) {
        OrderCertificatesDto found = orderService.getUserOrderById(userId, orderId);
        found.add(linkTo(methodOn(UserController.class).getUserOrderDetails(found.getUserId(), found.getId())).withSelfRel());
        found.add(linkTo(methodOn(UserController.class).getUserAllOrders(found.getUserId(), null)).withRel("all_orders"));
        found.add(linkTo(methodOn(UserController.class).findById(found.getUserId())).withRel("this_user"));
        return ResponseEntity.ok(found);
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderCertificatesDto> saveUserOrder(@PathVariable Long userId, @RequestBody OrderCertificatesDto orderCertificatesDto) throws ValidationException {
        orderCertificatesDto.setUserId(userId);
        OrderCertificatesDto saved = orderService.save(orderCertificatesDto);
        saved.add(linkTo(methodOn(UserController.class).findById(userId)).withRel("this_user"));
        saved.add(linkTo(methodOn(UserController.class).getUserAllOrders(saved.getUserId(), null)).withRel("all_orders"));
        saved.add(linkTo(methodOn(UserController.class).getUserOrderDetails(saved.getUserId(), saved.getId())).withRel("this"));
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<PagedModel<EntityModel<OrderDetailsDto>>> getUserAllOrders(@PathVariable Long userId, Pageable pageable) {
        Page<OrderDetailsDto> orders = orderService.getAllUserOrders(userId, pageable);
        PagedModel<EntityModel<OrderDetailsDto>> entityModels = orderAssembler.toModel(orders);
        entityModels.add(linkTo(methodOn(UserController.class).findById(userId)).withRel("this_user"));
        return ResponseEntity.ok(entityModels);
    }

}
///{user_id}/orders/{order_id}
