package com.inobitec.orderxml.mapper;

import com.inobitec.orderxml.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderItemMapper {

    void deleteOrderItemByOrderId(@Param("id") Integer id);

    void addOrderItem(@Param("orderItem") OrderItem orderItem);

}
