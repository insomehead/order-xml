package com.inobitec.orderxml;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan(basePackages = {
        "com.inobitec.orderxml.servlet",
        "com.inobitec.orderxml.filter"})
@EnableScheduling
public class OrderXmlApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrderXmlApplication.class, args);
    }

}
