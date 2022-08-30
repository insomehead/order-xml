package com.inobitec.orderxml.service;

import com.inobitec.orderxml.mapper.SessionMapper;
import com.inobitec.orderxml.model.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionMapper sessionMapper;

    @Override
    public List<Session> getAllSession() {
        return sessionMapper.findAllSession();
    }

}
