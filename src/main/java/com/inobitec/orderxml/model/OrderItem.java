package com.inobitec.orderxml.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlText;
import lombok.*;


@Data
public class OrderItem {

    @JsonIgnore
    private Integer id;

    @JsonIgnore
    private Integer orderId;

    @JacksonXmlText
    private String itemName;
}