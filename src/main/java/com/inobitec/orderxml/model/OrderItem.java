package com.inobitec.orderxml.model;

import lombok.*;

@Getter
@Setter
public class OrderItem {

    private Integer id;

    private Integer orderId;

    private String itemName;
}