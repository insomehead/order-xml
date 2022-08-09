package com.inobitec.orderxml.servlet;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.inobitec.orderxml.model.Message;
import com.inobitec.orderxml.model.Order;
import com.inobitec.orderxml.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;


@WebServlet("/order-servlet")
@RequiredArgsConstructor
public class OrderServlet extends HttpServlet {

    private final OrderServiceImpl orderServiceImpl;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/xml");
        response.setCharacterEncoding("UTF-8");
        Integer id = Integer.valueOf(request.getParameter("id"));
        Order order = orderServiceImpl.getOrderById(id);
        XmlMapper xmlMapper = new XmlMapper();
        PrintWriter out = response.getWriter();
        String xmlOrder = xmlMapper.writeValueAsString(order);
        out.print(xmlOrder);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        InputStream in = request.getInputStream();
        Message message = xmlMapper.readValue(in, Message.class);
        if ("create".equals(message.getCommand())) {
            orderServiceImpl.saveOrder(message.getBody().getOrder());
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        InputStream in = request.getInputStream();
        Message message = xmlMapper.readValue(in, Message.class);
        if ("update".equals(message.getCommand())) {
            Integer orderId = Integer.valueOf(request.getParameter("id"));
            orderServiceImpl.updateOrder(orderId, message.getBody().getOrder());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response){
        Integer id = Integer.valueOf(request.getParameter("id"));
        orderServiceImpl.deleteOrderById(id);
    }
}