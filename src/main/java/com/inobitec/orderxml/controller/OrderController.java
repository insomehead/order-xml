package com.inobitec.orderxml.controller;

import com.inobitec.orderxml.model.Order;
import com.inobitec.orderxml.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Integer id){
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id){
        orderService.deleteOrderById(id);
    }

    @PutMapping("")
    public void saveOrder(@RequestBody Order order){
        orderService.saveOrder(order);
    }
}
