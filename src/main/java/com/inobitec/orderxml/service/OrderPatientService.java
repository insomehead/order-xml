package com.inobitec.orderxml.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.inobitec.orderxml.dto.OrderPatientDto;
import com.inobitec.orderxml.model.Order;
import com.inobitec.orderxml.model.Patient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class OrderPatientService {

    private final OrderService orderService;

    private final PatientControllerService patientControllerService;

    private final HttpHeaders httpHeaders = new HttpHeaders();
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String BASE_URL = "http://localhost:8081/patient";

    {
        objectMapper.registerModule(new JavaTimeModule());
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    }

    public OrderPatientDto getOrderByIdAndPatientByOrderId(Integer id) throws URISyntaxException {
        Order order = orderService.getOrderById(id);
        if (order == null) {
            return null;
        }
        Integer orderPatientId = order.getPatientId();
        Patient patient = patientControllerService.getPatientById(orderPatientId);
        if (patient == null) {
            return null;
        }
        OrderPatientDto orderPatientDto = new OrderPatientDto();
        orderPatientDto.setOrder(order);
        orderPatientDto.setPatient(patient);
        return orderPatientDto;
    }

    @Transactional
    public ResponseEntity<OrderPatientDto> addPatientAndOrder(OrderPatientDto orderPatientDto)
            throws JsonProcessingException {
        Order order = orderPatientDto.getOrder();
        Patient patient = orderPatientDto.getPatient();
        String firstName = patient.getFirstName();
        String midName = patient.getMidName();
        String lastName = patient.getLastName();
//        objectMapper.registerModule(new JavaTimeModule())
        LocalDate birthday = patient.getBirthday();
        Patient patientFromDatabase = patientControllerService
                .getPatientByBirthdayAndFullName(birthday, firstName, midName, lastName);
        String patientFullName = firstName + " " + midName + " " + lastName;
        if (patientFromDatabase == null) {
//            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
            String stringPatient = objectMapper.writeValueAsString(patient);
            HttpEntity<String> httpEntity = new HttpEntity<>(stringPatient, httpHeaders);
            restTemplate.postForObject(BASE_URL, httpEntity, String.class);
            Patient patientFromDatabaseAfterPost = patientControllerService
                    .getPatientByBirthdayAndFullName(birthday, firstName, midName, lastName);
            order.setPatientId(patientFromDatabaseAfterPost.getId());
            order.setCustomerName(patientFullName);
            System.out.println(order);
            orderService.addOrder(order);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        String patientFullNameFromDatabase = patientFromDatabase.getFirstName() + " " +
                patientFromDatabase.getMidName() + " " +
                patientFromDatabase.getLastName();
        if (patientFullName.equals(patientFullNameFromDatabase)
                & patient.getBirthday().equals(patientFromDatabase.getBirthday())) {
            order.setPatientId(patientFromDatabase.getId());
            order.setCustomerName(patientFullNameFromDatabase);
            System.out.println(order);
            orderService.addOrder(order);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Void> updatePatientAndOrder(Integer id, OrderPatientDto orderPatientDto)
            throws URISyntaxException, JsonProcessingException {
        Order order = orderPatientDto.getOrder();
        Patient patient = orderPatientDto.getPatient();
        String firstName = patient.getFirstName();
        String midName = patient.getMidName();
        String lastName = patient.getLastName();
        String patientFullName = firstName + " " + midName + " " + lastName;
        order.setCustomerName(patientFullName);
        Order orderFromDatabase = orderService.getOrderById(id);
        if (orderFromDatabase == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Integer patientId = orderFromDatabase.getPatientId();
        Patient patientFromDatabase = patientControllerService.getPatientById(patientId);
        if (patientFromDatabase.equals(patient)) {
            orderService.updateOrder(id, order);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            String stringPatient = objectMapper.writeValueAsString(patient);
            HttpEntity<String> entity = new HttpEntity<>(stringPatient, httpHeaders);
            restTemplate.put(BASE_URL + "/" + orderFromDatabase.getPatientId(), entity);
            orderService.updateOrder(id, order);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
}
