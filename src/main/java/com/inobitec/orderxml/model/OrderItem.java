package com.inobitec.orderxml.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

@Data
@JacksonXmlRootElement(localName = "items")
public class OrderItem {

    public OrderItem(String itemName) {
        this.itemName = itemName;
    }

    @JsonIgnore
    private Integer id;

    @JsonIgnore
    private Integer orderId;

    @JacksonXmlProperty(localName = "item")
    private String itemName;
}