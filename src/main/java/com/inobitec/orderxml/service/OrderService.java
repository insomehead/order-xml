package com.inobitec.orderxml.service;

import com.inobitec.orderxml.model.Order;

public interface OrderService {

    Order getOrderById(Integer id);

    void deleteOrderById(Integer id);

    void saveOrder(Order order);

    void updateOrder(Integer id, Order order);
}
