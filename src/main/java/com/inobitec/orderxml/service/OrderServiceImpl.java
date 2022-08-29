package com.inobitec.orderxml.service;

import com.inobitec.orderxml.mapper.OrderItemMapper;
import com.inobitec.orderxml.mapper.OrderMapper;
import com.inobitec.orderxml.model.Order;
import com.inobitec.orderxml.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderMapper orderMapper;

    private final OrderItemMapper orderItemMapper;

    @Override
    public Order getOrderById(Integer id) {
        return orderMapper.findOrderById(id);
    }

    @Override
    public void deleteOrderById(Integer id) {
        orderItemMapper.deleteOrderItemByOrderId(id);
        orderMapper.deleteOrderById(id);
    }

    @Override
    public void addOrder(Order order) {
        orderMapper.addOrder(order);
        List<OrderItem> orderItemList = order.getOrderItemList();
        if (order.getOrderItemList() == null || order.getOrderItemList().isEmpty()) {
            return;
        }
        for (OrderItem orderItem : orderItemList) {
            Integer orderId = order.getId();
            orderItem.setOrderId(orderId);
            orderItemMapper.addOrderItem(orderItem);
        }
    }

    @Override
    public void updateOrder(Integer id, Order order) {
        List<OrderItem> orderItemList = order.getOrderItemList();
        orderMapper.updateOrder(order, id);
        if (orderItemList == null || orderItemList.isEmpty()) {
            orderItemMapper.deleteOrderItemByOrderId(id);
            return;
        }
        orderItemMapper.deleteOrderItemByOrderId(id);
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(id);
            orderItemMapper.addOrderItem(orderItem);
        }
    }
}
