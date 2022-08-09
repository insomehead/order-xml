package com.inobitec.orderxml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.inobitec.orderxml.servlet")
public class OrderXmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderXmlApplication.class, args);
    }

}
