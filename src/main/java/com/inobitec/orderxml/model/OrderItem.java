package com.inobitec.orderxml.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import com.inobitec.orderxml.dto.OrderItemDto;
import lombok.*;


@Data
public class OrderItem {

    @JsonIgnore
    private Integer id;

    @JsonIgnore
    private Integer orderId;

    @JacksonXmlText
    private String itemName;

    public OrderItemDto mapToDto(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setItem(itemName);
        return orderItemDto;
    }

    public OrderItem mapToEntity(OrderItemDto orderItemDto) {
        if (orderItemDto == null) {
            return null;
        }
        OrderItem orderItem = new OrderItem();
        orderItem.setItemName(orderItemDto.getItem());
        return orderItem;
    }
}