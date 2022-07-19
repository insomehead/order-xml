package com.inobitec.orderxml.service;

import com.inobitec.orderxml.mapper.OrderMapper;
import com.inobitec.orderxml.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;

    public Order getOrderById(Integer id){
        return orderMapper.findOrderById(id);
    }
}
