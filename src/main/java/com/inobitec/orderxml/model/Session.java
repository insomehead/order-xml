package com.inobitec.orderxml.model;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Session {

    private Integer id;

    private String sessionId;

    private LocalDate startTime;

    private Integer timeoutMinutes;
}
