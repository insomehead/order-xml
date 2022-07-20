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
    public void saveOrder(Order order) {
        orderMapper.saveOrder(order);
        if (order.getOrderItemList() == null || order.getOrderItemList().isEmpty()) {
            return;
        }
        for (int i = 0; i < order.getOrderItemList().size(); i++) {
            order.getOrderItemList().get(i).setOrderId(order.getId());
            orderItemMapper.saveOrderItem(order.getOrderItemList().get(i));
        }
    }

    @Override
    public void updateOrder(Integer id, Order order) {
        List<OrderItem> oldOrderItemList = orderMapper.findOrderById(id).getOrderItemList();
        List<OrderItem> orderItemList = order.getOrderItemList();
        orderMapper.updateOrder(order, id);
        if (orderItemList == null || orderItemList.isEmpty()) {
            orderItemMapper.deleteOrderItemByOrderId(id);
            return;
        }
        if (oldOrderItemList.size() == orderItemList.size()) {
            for (int i = 0; i < orderItemList.size(); i++) {
                orderItemMapper.updateOrderItem(
                        oldOrderItemList.get(i).getId(),
                        id,
                        orderItemList.get(i).getItemName()
                );
            }
        }
        if (orderItemList.size() < oldOrderItemList.size()) {
            for (int i = 0; i < orderItemList.size(); i++) {
                orderItemMapper.updateOrderItem(
                        oldOrderItemList.get(i).getId(),
                        id,
                        orderItemList.get(i).getItemName()
                );
            }
            for (int i = orderItemList.size(); i < oldOrderItemList.size(); i++) {
                orderItemMapper.deleteOrderItemById(oldOrderItemList.get(i).getId());
            }
        }
        if (orderItemList.size() > oldOrderItemList.size()) {
            for (int i = 0; i < oldOrderItemList.size(); i++) {
                orderItemMapper.updateOrderItem(
                        oldOrderItemList.get(i).getId(),
                        id,
                        orderItemList.get(i).getItemName()
                );
            }
            for (int i = oldOrderItemList.size(); i < orderItemList.size(); i++) {
                order.getOrderItemList().get(i).setOrderId(id);
                orderItemMapper.saveOrderItem(orderItemList.get(i));
            }
        }
    }
}
