package com.ash.creatorstore.Controllers;

import com.ash.creatorstore.Services.OrderService;
import com.ash.creatorstore.dto.OrderRequest;
import com.ash.creatorstore.entities.Order;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    public Order createOrder(@Valid @RequestBody OrderRequest orderRequest) {
       return orderService.createOrder(orderRequest);
    }
}

