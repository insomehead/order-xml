package com.inobitec.orderxml.service;

import com.inobitec.orderxml.mapper.OrderItemMapper;
import com.inobitec.orderxml.mapper.OrderMapper;
import com.inobitec.orderxml.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    public Order getOrderById(Integer id){
        return orderMapper.findOrderById(id);
    }
    public void deleteOrderById(Integer id){
        orderItemMapper.deleteOrderItemById(id);
        orderMapper.deleteOrderById(id);
    }

    public void saveOrder(Order order){
        orderMapper.saveOrder(order);
        if (order.getOrderItemList() == null || order.getOrderItemList().isEmpty()) {
            return;
        }
        for (int i = 0; i < order.getOrderItemList().size(); i++){
            order.getOrderItemList().get(i).setOrderId(order.getId());
            orderItemMapper.saveOrderItem(order.getOrderItemList().get(i));
        }
    }
}
