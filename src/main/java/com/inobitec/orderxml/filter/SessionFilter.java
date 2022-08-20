package com.inobitec.orderxml.filter;


import com.inobitec.orderxml.model.Session;
import com.inobitec.orderxml.service.SessionServiceImpl;
import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@WebFilter(servletNames = "OrderServlet", urlPatterns = "/order-servlet")
public class SessionFilter implements Filter {

    private final SessionServiceImpl sessionService;

//    @Value("${value.timeout.minutes}") чет не вышло. потом ещё попробую
    private static final Integer TIMEOUT_MINUTES = 60;

    private static final String SESSION_ID_PROPERTY = "sessionId";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String sessionId = httpServletRequest.getHeader(SESSION_ID_PROPERTY);
        Session session = sessionService.getSessionBySessionId(sessionId);
        PrintWriter out = httpServletResponse.getWriter();

        if (sessionId == null || sessionId.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("Invalid session");
            return;
        } else if (session == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("Unknown session");
            return;
        } else if (session.getTimeoutMinutes() >= TIMEOUT_MINUTES) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("Session expired");
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
