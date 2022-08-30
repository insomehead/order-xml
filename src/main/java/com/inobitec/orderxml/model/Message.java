package com.inobitec.orderxml.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "message")
public class Message {

    private String command;

    private Body body;
}
