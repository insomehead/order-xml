package com.inobitec.orderxml.filter;


import com.inobitec.orderxml.model.Session;
import com.inobitec.orderxml.service.SessionServiceImpl;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebFilter("/SimpleServletFilter")
@RequiredArgsConstructor
public class SessionFilter implements Filter {

    private final SessionServiceImpl sessionService;

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        String sessionId = servletRequest.getParameter("sessionId");
        Session session = sessionService.getSessionBySessionId(sessionId);
        PrintWriter out = httpServletResponse.getWriter();

        if (sessionId == null || sessionId.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print("Invalid session");
            return;
        }

        if (session == null){
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print("Unknown session");
            return;
        }

        if (session.getTimeoutMinutes() >= 60){
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
