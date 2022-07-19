package com.inobitec.orderxml.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
public class Order {

    private Integer id;

    private Integer orderStatusId;

    private String customerName;

    private String customerPhone;

    private String customerComment;

    private List<OrderItem> orderItemList;
}