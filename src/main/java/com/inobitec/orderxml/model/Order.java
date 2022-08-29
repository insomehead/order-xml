package com.inobitec.orderxml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;

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
}