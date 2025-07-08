package com.produictapp.orderservice.controller;

import com.produictapp.orderservice.dto.OrderRequest;
import com.produictapp.orderservice.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.produictapp.orderservice.service.OrderService;

@ComponentScan(basePackages = "com.produictapp.orderservice")
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return ResponseEntity.ok("Order placed successfully");
    }


    @GetMapping("/test")
    public String test() {
        return "API is working!";
    }
}
