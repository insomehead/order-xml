package com.inobitec.orderxml.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
public class OrderItemDto {

    @JacksonXmlProperty(localName = "item")
    private String item;
}
