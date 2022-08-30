package com.inobitec.orderxml.dto;

import com.inobitec.orderxml.model.Order;
import com.inobitec.orderxml.model.Patient;
import lombok.Data;

@Data
public class OrderPatientDto {

    private Order order;

    private Patient patient;
}
