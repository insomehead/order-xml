package com.inobitec.orderxml.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.time.LocalDate;

@Data
@JacksonXmlRootElement(localName = "patient")
public class Patient {

    private Integer id;

    private String firstName;

    private String midName;

    private String lastName;

    private Integer genderId;

    private LocalDate birthday;

    private String phone;

    private String email;

    private String address;
}
