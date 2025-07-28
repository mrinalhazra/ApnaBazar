package com.app.apnabazar.controller;

import com.app.apnabazar.model.OrderItem;
import com.app.apnabazar.service.OrderService;
import com.app.apnabazar.dto.CartDto;
import com.app.apnabazar.dto.OrderDto;
import com.app.apnabazar.dto.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage createOrder(@PathVariable Long userId,@RequestBody CartDto cartDto){
        orderService.createOrder(userId,cartDto);
        return new ResponseMessage("Order created successfully.");

    }

    @GetMapping("/summary/{userId}")
    public ResponseEntity<List<OrderItem>> getOrderSummaryForUser(@PathVariable Long userId){
        List<OrderItem> orderItems = orderService.generateSummary(userId);
        return (ResponseEntity<List<OrderItem>>) orderItems;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMessage placeOrder(@RequestBody OrderDto orderDto){
        orderService.placeOrder(orderDto);
        return new ResponseMessage("Order Placed successfully.");
    }
}
