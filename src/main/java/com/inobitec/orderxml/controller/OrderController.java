package com.inobitec.orderxml.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.inobitec.orderxml.dto.OrderPatientDto;
import com.inobitec.orderxml.service.OrderPatientService;
import com.inobitec.orderxml.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/order")
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    private final OrderPatientService orderPatientService;

    private static final String ID_PARAMETER = "/{id}";
    private static final String PATIENT_PATH = "/patient";

    @GetMapping(PATIENT_PATH + ID_PARAMETER)
    public ResponseEntity<OrderPatientDto> getOrderAndPatientByOrderId(@PathVariable Integer id)
            throws URISyntaxException {
        OrderPatientDto orderByIdAndPatientByOrderId = orderPatientService.getOrderByIdAndPatientByOrderId(id);
        if (orderByIdAndPatientByOrderId == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(orderByIdAndPatientByOrderId, HttpStatus.OK);
    }

    @PostMapping(PATIENT_PATH)
    public ResponseEntity<OrderPatientDto> addOrderAndPatient(@RequestBody OrderPatientDto orderPatientDto)
            throws JsonProcessingException {
        if (orderPatientDto == null || orderPatientDto.getPatient() == null | orderPatientDto.getOrder() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return orderPatientService.addPatientAndOrder(orderPatientDto);
    }

    @PutMapping(PATIENT_PATH + ID_PARAMETER)
    public ResponseEntity<Void> updateOrderAndPatient(@PathVariable Integer id,
                                                      @RequestBody OrderPatientDto orderPatientDto)
            throws URISyntaxException, JsonProcessingException {
        if (id == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(id);
        if (orderPatientDto == null || orderPatientDto.getPatient() == null
                | orderPatientDto.getOrder() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return orderPatientService.updatePatientAndOrder(id, orderPatientDto);
    }

    @DeleteMapping(ID_PARAMETER)
    public void deleteOrder(@PathVariable Integer id) {
        orderServiceImpl.deleteOrderById(id);
    }

    //    Запрос с практики
//    @GetMapping(ID_PARAMETER)
//    public Order getOrderId(@PathVariable Integer id) {
//        return orderServiceImpl.getOrderById(id);
//    }

    //    Запрос с практики
//    @PostMapping
//    public Order addOrder(@RequestBody Order order) {
//        orderServiceImpl.addOrder(order);
//        return orderServiceImpl.getOrderById(order.getId());
//    }

    //    Запрос с практики
//    @PutMapping(ID_PARAMETER)
//    public Order updateOrder(@PathVariable Integer id, @RequestBody Order order) {
//        orderServiceImpl.updateOrder(id, order);
//        return orderServiceImpl.getOrderById(id);
//    }
}
