package com.inobitec.orderxml.mapper;

import com.inobitec.orderxml.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderItemMapper {

    void deleteOrderItemByOrderId(@Param("id") Integer id);

    void deleteOrderItemById(@Param("id") Integer id);

    void saveOrderItem(@Param("orderItem") OrderItem orderItem);

    void updateOrderItem(@Param("id") Integer id,
                         @Param("order_id") Integer order_id,
                         @Param("itemName") String itemName);
}
