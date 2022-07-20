package com.inobitec.orderxml.mapper;

import com.inobitec.orderxml.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

    Order findOrderById(@Param("id") Integer id);

    void deleteOrderById(@Param("id") Integer id);

    void saveOrder(Order order);

    void updateOrder(@Param("order") Order order, @Param("id") Integer id);
}
