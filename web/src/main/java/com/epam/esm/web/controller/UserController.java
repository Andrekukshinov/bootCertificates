package com.epam.esm.web.controller;

import com.epam.esm.persistence.model.page.Page;
import com.epam.esm.persistence.model.page.Pageable;
import com.epam.esm.service.dto.TagDto;
import com.epam.esm.service.dto.order.OrderCertificatesDto;
import com.epam.esm.service.dto.order.OrderDetailsDto;
import com.epam.esm.service.dto.user.UserInfoDto;
import com.epam.esm.service.dto.user.UserOrderDto;
import com.epam.esm.service.exception.ValidationException;
import com.epam.esm.service.service.OrderService;
import com.epam.esm.service.service.TagService;
import com.epam.esm.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService service;
    private final OrderService orderService;
    private final TagService tagService;


    @Autowired
    public UserController(UserService service, OrderService orderService, TagService tagService) {
        this.service = service;
        this.orderService = orderService;
        this.tagService = tagService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserOrderDto> findById(@PathVariable Long id) {
        UserOrderDto user = service.getById(id);
        user.add(linkTo(methodOn(UserController.class).findById(id)).withSelfRel());
        user.add(linkTo(methodOn(UserController.class).getAllUsers(new HashMap<>())).withRel("all_users"));
        user.add(linkTo(methodOn(UserController.class).getUserAllOrders(user.getId(), new HashMap<>())).withRel("all_orders"));
        return ResponseEntity.ok(user);
    }

    @GetMapping()
    public ResponseEntity<CollectionModel<UserInfoDto>> getAllUsers(@RequestParam(required = false) Map<String, String> requestParams) {
        Pageable pageable = getPageable(requestParams);
        Page<UserInfoDto> page = service.getAll(pageable);
        CollectionModel<UserInfoDto> of = getUserBuiltLinks(requestParams, page);
        return ResponseEntity.ok(of);
    }

    private CollectionModel<UserInfoDto> getUserBuiltLinks(Map<String, String> requestParams, Page<UserInfoDto> page) {
        CollectionModel<UserInfoDto> of = CollectionModel.of(page.getContent());
        of.add(linkTo(
                methodOn(UserController.class)
                        .getAllUsers( getPageParamMap(requestParams, page.getFirstPage())))
                .withRel("first")
        );
        if (page.hasPrevious()) {
            of.add(linkTo(
                    methodOn(UserController.class)
                            .getAllUsers(getPageParamMap(requestParams, page.getPreviousPage())))
                    .withRel("previous")
            );
        }
        of.add(linkTo(
                methodOn(UserController.class)
                        .getAllUsers(getPageParamMap(requestParams, page.getPage())))
                .withRel("this")
        );
        if (page.hasNext()) {
            of.add(linkTo(
                    methodOn(UserController.class)
                            .getAllUsers(getPageParamMap(requestParams, page.getNextPage())))
                    .withRel("next")
            );
        }
        of.add(linkTo(
                methodOn(UserController.class)
                        .getAllUsers(getPageParamMap(requestParams, page.getLastPage())))
                .withRel("last")
        );
        return of;
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity<OrderCertificatesDto> getUserOrderDetails(@PathVariable Long userId, @PathVariable Long orderId) {
        OrderCertificatesDto found = orderService.getUserOrderById(userId, orderId);
        found.add(linkTo(methodOn(UserController.class).getUserOrderDetails(found.getUserId(), found.getId())).withSelfRel());
        found.add(linkTo(methodOn(UserController.class).getUserAllOrders(found.getUserId(), new HashMap<>())).withRel("all_orders"));
        found.add(linkTo(methodOn(UserController.class).findById(found.getUserId())).withRel("this_user"));
        return ResponseEntity.ok(found);
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity<OrderCertificatesDto> saveUserOrder(@PathVariable Long userId, @Valid @RequestBody OrderCertificatesDto orderCertificatesDto) throws ValidationException {
        orderCertificatesDto.setUserId(userId);
        OrderCertificatesDto saved = orderService.save(orderCertificatesDto);
        saved.add(linkTo(methodOn(UserController.class).findById(userId)).withRel("this_user"));
        saved.add(linkTo(methodOn(UserController.class).getUserAllOrders(saved.getUserId(), new HashMap<>())).withRel("all_orders"));
        saved.add(linkTo(methodOn(UserController.class).getUserOrderDetails(saved.getUserId(), saved.getId())).withRel("this"));
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/{userId}/orders")
    public ResponseEntity<CollectionModel<OrderDetailsDto>> getUserAllOrders(@PathVariable Long userId, @RequestParam Map<String, String> requestParams) {
        Pageable pageable = getPageable(requestParams);
        Page<OrderDetailsDto> page = orderService.getAllUserOrders(userId, pageable);
        CollectionModel<OrderDetailsDto> of = getOrdersBuiltLinks(userId, requestParams, page);
        return ResponseEntity.ok(of);
    }

    private CollectionModel<OrderDetailsDto> getOrdersBuiltLinks(Long userId, Map<String, String> requestParams, Page<OrderDetailsDto> page) {
        CollectionModel<OrderDetailsDto> of = CollectionModel.of(page.getContent());
        of.add(linkTo(
                methodOn(UserController.class)
                        .getUserAllOrders(userId, getPageParamMap(requestParams, page.getFirstPage())))
                .withRel("first")
        );
        if (page.hasPrevious()) {
            of.add(linkTo(
                    methodOn(UserController.class)
                            .getUserAllOrders(userId, getPageParamMap(requestParams, page.getPreviousPage())))
                    .withRel("previous")
            );
        }
        of.add(linkTo(
                methodOn(UserController.class)
                        .getUserAllOrders(userId, getPageParamMap(requestParams, page.getPage())))
                .withRel("this")
        );
        if (page.hasNext()) {
            of.add(linkTo(
                    methodOn(UserController.class)
                            .getUserAllOrders(userId, getPageParamMap(requestParams, page.getNextPage())))
                    .withRel("next")
            );
        }
        of.add(linkTo(
                methodOn(UserController.class)
                        .getUserAllOrders(userId, getPageParamMap(requestParams, page.getLastPage())))
                .withRel("last")
        );
        return of;
    }

    @GetMapping("/search/tag")
    public ResponseEntity<TagDto> getTopUserMostPopularTag() {
        TagDto tag = tagService.getTopUserMostPopularTag();
        tag.add(linkTo(methodOn(TagController.class).getTagById(tag.getId())).withRel("this_tag"));
        tag.add(linkTo(methodOn(UserController.class).getTopUserMostPopularTag()).withSelfRel());
        tag.add(linkTo(methodOn(TagController.class).getAll(new HashMap<>())).withRel("all"));
        return ResponseEntity.ok(tag);
    }

    private Map<String, String> getPageParamMap(Map<String, String> requestParams, Integer page) {
        HashMap<String, String> result = new HashMap<>(requestParams);
        result.put("page", page.toString());
        return result;
    }

    //todo validate input
    private Pageable getPageable(Map<String, String> requestParams) {
        String pages = requestParams.get("page");
        Integer page = Integer.valueOf(pages);
        Integer size = Integer.valueOf(requestParams.get("size"));
        String sort = requestParams.get("sort");
        String sortDir = requestParams.get("sortDir");
        return new Pageable(page, size, sort, sortDir);
    }
}
