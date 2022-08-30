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

import static com.inobitec.orderxml.controller.OrderController.ORDER_PATH;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = ORDER_PATH)
public class OrderController {

    private final OrderServiceImpl orderServiceImpl;

    private final OrderPatientService orderPatientService;

    static final String ORDER_PATH = "/order";
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
        OrderPatientDto orderPatientDtoAfterPost = orderPatientService.addPatientAndOrder(orderPatientDto);
        if (orderPatientDtoAfterPost == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderPatientDtoAfterPost, HttpStatus.CREATED);
    }

    @PutMapping(PATIENT_PATH + ID_PARAMETER)
    public ResponseEntity<OrderPatientDto> updateOrderAndPatient(@PathVariable Integer id,
                                                      @RequestBody OrderPatientDto orderPatientDto)
            throws URISyntaxException, JsonProcessingException {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        System.out.println(id);
        if (orderPatientDto == null || orderPatientDto.getPatient() == null
                | orderPatientDto.getOrder() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OrderPatientDto orderPatientDtoAfterPost = orderPatientService.updatePatientAndOrder(id, orderPatientDto);
        if (orderPatientDtoAfterPost == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderPatientDtoAfterPost, HttpStatus.ACCEPTED);
    }

    @DeleteMapping(ID_PARAMETER)
    public void deleteOrder(@PathVariable Integer id) {
        orderServiceImpl.deleteOrderById(id);
    }
}
