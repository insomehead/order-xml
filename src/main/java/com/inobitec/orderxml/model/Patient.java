package com.inobitec.orderxml.model;


import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.inobitec.orderxml.dto.PatientDto;
import lombok.Data;

import java.time.LocalDate;

@Data
@JacksonXmlRootElement(localName = "patient")
public class Patient {

    private static final String SPACE = " ";

    private Integer id;

    private String firstName;

    private String midName;

    private String lastName;

    private Integer genderId;

    private LocalDate birthday;

    private String phone;

    private String email;

    private String address;

    public String getFullName() {
        return firstName + SPACE + midName + SPACE + lastName;
    }

    public PatientDto mapToDto() {
        PatientDto patientDto = new PatientDto();
        patientDto.setId(id);
        patientDto.setFirstName(firstName);
        patientDto.setMidName(midName);
        patientDto.setLastName(lastName);
        patientDto.setGenderId(genderId);
        patientDto.setBirthday(birthday);
        patientDto.setPhone(phone);
        patientDto.setEmail(email);
        patientDto.setAddress(address);
        return patientDto;
    }

    public static Patient mapToEntity(PatientDto patientDto) {
        if (patientDto == null){
            return null;
        }
        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setFirstName(patientDto.getFirstName());
        patient.setMidName(patientDto.getMidName());
        patient.setLastName(patientDto.getLastName());
        patient.setGenderId(patientDto.getGenderId());
        patient.setBirthday(patientDto.getBirthday());
        patient.setPhone(patientDto.getPhone());
        patient.setEmail(patientDto.getEmail());
        patient.setAddress(patientDto.getAddress());
        return patient;
    }
}
