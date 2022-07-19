package com.inobitec.orderxml.mapper;

import com.inobitec.orderxml.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderMapper {

    Order findOrderById(@Param("id") Integer id);
}
