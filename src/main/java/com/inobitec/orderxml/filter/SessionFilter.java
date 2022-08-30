package com.inobitec.orderxml.filter;


import com.inobitec.orderxml.cache.SessionCache;
import com.inobitec.orderxml.model.Session;
import lombok.RequiredArgsConstructor;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@RequiredArgsConstructor
@WebFilter(servletNames = "OrderServlet", urlPatterns = "/order-servlet")
public class SessionFilter implements Filter {

    private final SessionCache sessionCache;

    private static final String SESSION_EXPIRED ="Session expired";
    private static final String UNKNOWN_SESSION = "Unknown session";
    private static final String INVALID_SESSION = "Invalid session";
    private static final String SESSION_ID_PROPERTY = "sessionId";
    private static final Integer TIMEOUT_MINUTES = 60;

    @Override
    public void init(FilterConfig filterConfig) {
        sessionCache.addCache();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {



        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String sessionId = httpServletRequest.getHeader(SESSION_ID_PROPERTY);
        PrintWriter out = httpServletResponse.getWriter();
        if (sessionId == null || sessionId.isEmpty()) {
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.print(INVALID_SESSION);
            return;
        }
        Session session = sessionCache.getSessionBySessionId(sessionId);
        if (session == null) {
            httpServletResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            out.print(UNKNOWN_SESSION);
            return;
        } else if (session.getTimeoutMinutes() >= TIMEOUT_MINUTES) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print(SESSION_EXPIRED);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
    }
}
