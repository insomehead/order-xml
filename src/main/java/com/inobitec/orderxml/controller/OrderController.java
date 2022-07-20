package com.inobitec.orderxml.controller;

import com.inobitec.orderxml.model.Order;
import com.inobitec.orderxml.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable Integer id) {
        return orderServiceImpl.getOrderById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Integer id) {
        orderServiceImpl.deleteOrderById(id);
    }

    @PostMapping("")
    public Order saveOrder(@RequestBody Order order) {
        orderServiceImpl.saveOrder(order);
        return orderServiceImpl.getOrderById(order.getId());
    }

    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        orderServiceImpl.updateOrder(id, order);
        return orderServiceImpl.getOrderById(id);
    }
}
