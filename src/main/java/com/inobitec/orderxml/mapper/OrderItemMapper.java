package com.inobitec.orderxml.mapper;

import com.inobitec.orderxml.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderItemMapper {

    void deleteOrderItemById(@Param("id") Integer id);

    void saveOrderItem(@Param("orderItem") OrderItem orderItem);

    void updateOrderItem(@Param("id") Integer id);
}
