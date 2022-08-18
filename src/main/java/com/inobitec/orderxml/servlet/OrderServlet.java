package com.inobitec.orderxml.servlet;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.inobitec.orderxml.model.Message;
import com.inobitec.orderxml.model.Order;
import com.inobitec.orderxml.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

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

    private static final long serialVersionUID = 1L;

    private final OrderServiceImpl orderServiceImpl;

    private static final XmlMapper xmlMapper = new XmlMapper();

    private static final String XML_TEXT_TYPE = "text/xml";

    private static final String UTF_8 = "UTF-8";

    private static final String ID_XML_PROPERTY = "id";

    @Override
    @Transactional
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType(XML_TEXT_TYPE);
        response.setCharacterEncoding(UTF_8);
        InputStream in = request.getInputStream();
        PrintWriter out = response.getWriter();
        Message message = xmlMapper.readValue(in, Message.class);
        Order orderFromRequest = message.getBody().getOrder();
        if ("create".equals(message.getCommand())) {
            orderServiceImpl.addOrder(orderFromRequest);
            response.setStatus(HttpServletResponse.SC_CREATED);
        }

        if ("read".equals(message.getCommand())) {
            Integer id = Integer.valueOf(request.getParameter(ID_XML_PROPERTY));
            Order order = orderServiceImpl.getOrderById(id);
            if (order == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(response.getStatus());
                return;
            }
            String xmlOrder = xmlMapper.writeValueAsString(order);
            out.print(xmlOrder);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if ("delete".equals(message.getCommand())) {
            Integer id = Integer.valueOf(request.getParameter(ID_XML_PROPERTY));
            Order order = orderServiceImpl.getOrderById(id);
            if (order == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(response.getStatus());
                return;
            }
            orderServiceImpl.deleteOrderById(id);
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        if ("update".equals(message.getCommand())) {
            Integer id = Integer.valueOf(request.getParameter(ID_XML_PROPERTY));
            Order order = orderServiceImpl.getOrderById(id);
            if (order == null) {
                response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.print(response.getStatus());
                return;
            }
            orderServiceImpl.updateOrder(id, orderFromRequest);
            response.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(response.getStatus());
        }
    }
}