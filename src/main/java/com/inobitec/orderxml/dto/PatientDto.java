package com.inobitec.orderxml.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@JacksonXmlRootElement(localName = "patient")
public class PatientDto {

    @JacksonXmlProperty(localName = "id")
    private Integer id;

    @JacksonXmlProperty(localName = "firstName")
    private String firstName;

    @JacksonXmlProperty(localName = "midName")
    private String midName;

    @JacksonXmlProperty(localName = "lastName")
    private String lastName;

    @JacksonXmlProperty(localName = "genderId")
    private Integer genderId;

    @JacksonXmlProperty(localName = "birthday")
    private LocalDate birthday;

    @JacksonXmlProperty(localName = "phone")
    private String phone;

    @JacksonXmlProperty(localName = "email")
    private String email;

    @JacksonXmlProperty(localName = "address")
    private String address;
}
