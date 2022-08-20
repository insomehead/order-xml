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


@WebServlet(name = "OrderServlet", urlPatterns = "/order-servlet")
@RequiredArgsConstructor
public class OrderServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final OrderServiceImpl orderServiceImpl;

    private static final XmlMapper xmlMapper = new XmlMapper();

    private static final String TEXT_XML_TYPE = "text/xml";
    private static final String UTF_8 = "UTF-8";

    private static final String ID_PROPERTY = "id";

    private static final String CREATE_COMMAND = "create";
    private static final String READ_COMMAND = "read";
    private static final String DELETE_COMMAND = "delete";
    private static final String UPDATE_COMMAND = "update";

    @Override
    @Transactional
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        response.setContentType(TEXT_XML_TYPE);
        response.setCharacterEncoding(UTF_8);
        InputStream in = request.getInputStream();
        PrintWriter out = response.getWriter();
        Message message = xmlMapper.readValue(in, Message.class);
        Order orderFromRequest = message.getBody().getOrder();
        Order order;
        int id;

        switch (message.getCommand()) {

            case CREATE_COMMAND:
                orderServiceImpl.addOrder(orderFromRequest);
                response.setStatus(HttpServletResponse.SC_CREATED);
                break;

            case READ_COMMAND:
                id = Integer.parseInt(request.getParameter(ID_PROPERTY));
                order = orderServiceImpl.getOrderById(id);
                if (order == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(response.getStatus());
                    return;
                }
                String xmlOrder = xmlMapper.writeValueAsString(order);
                out.print(xmlOrder);
                response.setStatus(HttpServletResponse.SC_OK);
                break;

            case DELETE_COMMAND:
                id = Integer.parseInt(request.getParameter(ID_PROPERTY));
                order = orderServiceImpl.getOrderById(id);
                if (order == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(response.getStatus());
                    return;
                }
                orderServiceImpl.deleteOrderById(id);
                response.setStatus(HttpServletResponse.SC_OK);
                break;

            case UPDATE_COMMAND:
                id = Integer.parseInt(request.getParameter(ID_PROPERTY));
                order = orderServiceImpl.getOrderById(id);
                if (order == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(response.getStatus());
                    return;
                }
                orderServiceImpl.updateOrder(id, orderFromRequest);
                response.setStatus(HttpServletResponse.SC_ACCEPTED);
                break;

            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(response.getStatus());
        }
    }
}