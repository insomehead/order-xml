package com.inobitec.orderxml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.inobitec.orderxml.dto.OrderDto;
import com.inobitec.orderxml.dto.OrderItemDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@JacksonXmlRootElement(localName = "order")
public class Order {

    @JacksonXmlProperty(localName = "id")
    private Integer id;

    @JacksonXmlProperty(localName = "orderStatusId")
    private Integer orderStatusId;

    @JacksonXmlProperty(localName = "customerName")
    private String customerName;

    @JacksonXmlProperty(localName = "customerPhone")
    private String customerPhone;

    @JacksonXmlProperty(localName = "customerComment")
    private String customerComment;

    @JacksonXmlProperty(localName = "patientId")
    private Integer patientId;

    @JacksonXmlElementWrapper(localName = "items")
    @JacksonXmlProperty(localName = "item")
    private List<OrderItem> orderItemList;

    public OrderDto mapToDto() {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(id);
        orderDto.setOrderStatusId(orderStatusId);
        orderDto.setCustomerName(customerName);
        orderDto.setCustomerPhone(customerPhone);
        orderDto.setCustomerComment(customerComment);
        orderDto.setPatientId(patientId);
        if (orderItemList == null || orderItemList.isEmpty()){
            return orderDto;
        }
        List<OrderItemDto> orderItemDtoList = new ArrayList<>(orderItemList.size());
        for (OrderItem orderItem : orderItemList) {
            orderItemDtoList.add(orderItem.mapToDto(orderItem));
        }
        orderDto.setItems(orderItemDtoList);
        return orderDto;
    }

    public static Order mapToEntity(OrderDto orderDto) {
        if (orderDto == null){
            return null;
        }
        Order order = new Order();
        order.setId(orderDto.getId());
        order.setOrderStatusId(orderDto.getOrderStatusId());
        order.setCustomerName(orderDto.getCustomerName());
        order.setCustomerPhone(orderDto.getCustomerPhone());
        order.setCustomerComment(orderDto.getCustomerComment());
        order.setPatientId(orderDto.getPatientId());

        List<OrderItemDto> orderItemDtoList = orderDto.getItems();
        if (orderItemDtoList == null || orderItemDtoList.isEmpty()){
            return order;
        }
        List<OrderItem> orderItems = new ArrayList<>(orderItemDtoList.size());

        for (OrderItemDto orderItemDto : orderItemDtoList) {
            OrderItem orderItem = new OrderItem();
            OrderItem orderItemEntity = orderItem.mapToEntity(orderItemDto);
            orderItems.add(orderItemEntity);
        }
        order.setOrderItemList(orderItems);
        return order;
    }
}