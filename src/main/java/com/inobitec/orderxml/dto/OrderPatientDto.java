package com.inobitec.orderxml.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "orderItemDto")
public class OrderPatientDto {

    @JacksonXmlProperty(localName = "order")
    private OrderDto order;

    @JacksonXmlProperty(localName = "patient")
    private PatientDto patient;
}
