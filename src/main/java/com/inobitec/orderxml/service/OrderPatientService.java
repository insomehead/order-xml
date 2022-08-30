package com.inobitec.orderxml.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inobitec.orderxml.dto.OrderPatientDto;
import com.inobitec.orderxml.model.Order;
import com.inobitec.orderxml.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URISyntaxException;

@Service
@RequiredArgsConstructor
public class OrderPatientService {

    private final OrderService orderService;

    private final PatientService patientService;


    public OrderPatientDto getOrderByIdAndPatientByOrderId(Integer id) throws URISyntaxException {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return null;
        }
        Integer orderPatientId = order.getPatientId();
        Patient patient = patientService.getPatientById(orderPatientId);
        if (patient == null) {
            return null;
        }
        OrderPatientDto orderPatientDto = new OrderPatientDto();
        orderPatientDto.setOrder(order.mapToDto());
        orderPatientDto.setPatient(patient.mapToDto());
        return orderPatientDto;
    }

    @Transactional
    public OrderPatientDto addPatientAndOrder(OrderPatientDto orderPatientDto)
            throws JsonProcessingException {
        if (orderPatientDto == null || orderPatientDto.getPatient() == null | orderPatientDto.getOrder() == null) {
            return null;
        }
        Order order = Order.mapToEntity(orderPatientDto.getOrder());
        Patient patient = Patient.mapToEntity(orderPatientDto.getPatient());
        Patient patientFromDatabase = patientService
                .getPatientByBirthdayAndFullName(patient.getBirthday(),
                        patient.getFirstName(), patient.getMidName(), patient.getLastName());
        String patientFullName = patient.getFullName();
        if (patientFromDatabase == null) {
            patientService.addPatient(patient);
            Patient patientFromDatabaseAfterPost = patientService
                    .getPatientByBirthdayAndFullName(patient.getBirthday(),
                            patient.getFirstName(), patient.getMidName(), patient.getLastName());
            order.setPatientId(patientFromDatabaseAfterPost.getId());
            order.setCustomerName(patientFullName);
            orderService.addOrder(order);
            orderPatientDto.setOrder(order.mapToDto());
            orderPatientDto.setPatient(patient.mapToDto());
            return orderPatientDto;
        }
        String patientFullNameFromDatabase = patientFromDatabase.getFullName();
        if (patientFullName.equals(patientFullNameFromDatabase)
                & patient.getBirthday().equals(patientFromDatabase.getBirthday())) {
            order.setPatientId(patientFromDatabase.getId());
            order.setCustomerName(patientFullNameFromDatabase);
            orderService.addOrder(order);
            orderPatientDto.setOrder(order.mapToDto());
            orderPatientDto.setPatient(patientFromDatabase.mapToDto());
            return orderPatientDto;
        } else {
            return null;
        }
    }

    public OrderPatientDto updatePatientAndOrder(Integer id, OrderPatientDto orderPatientDto)
            throws URISyntaxException, JsonProcessingException {
        if (orderPatientDto == null || orderPatientDto.getPatient() == null | orderPatientDto.getOrder() == null) {
            return null;
        }
        Order order = Order.mapToEntity(orderPatientDto.getOrder());
        Patient patient = Patient.mapToEntity(orderPatientDto.getPatient());
        String patientFullName = patient.getFullName();
        order.setCustomerName(patientFullName);
        Order orderFromDatabase = orderService.getOrderById(id);
        if (orderFromDatabase == null) {
            return null;
        }
        Integer patientId = orderFromDatabase.getPatientId();
        Patient patientFromDatabase = patientService.getPatientById(patientId);
        if (!patientFromDatabase.equals(patient)) {
            patientService.updatePatient(patient, id);
        }
        orderService.updateOrder(id, order);
        orderPatientDto.setPatient(patient.mapToDto());
        orderPatientDto.setOrder(order.mapToDto());
        return orderPatientDto;
    }
}
