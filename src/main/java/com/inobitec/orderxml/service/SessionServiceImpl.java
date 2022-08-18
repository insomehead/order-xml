package com.inobitec.orderxml.service;

import com.inobitec.orderxml.mapper.SessionMapper;
import com.inobitec.orderxml.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionMapper sessionMapper;

    @Override
    public Session getSessionBySessionId(String sessionId) {
        return sessionMapper.findSessionBySessionId(sessionId);
    }
}
