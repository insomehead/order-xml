package com.inobitec.orderxml.cache;

import com.inobitec.orderxml.model.Session;
import com.inobitec.orderxml.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@RequiredArgsConstructor
public class SessionCache implements Cache{


    private final SessionService sessionService;

    @Override
    public Map<String, Session> addCache() {
        Map<String, Session> map = new ConcurrentHashMap<>();
        List<Session> sessionList = sessionService.getAllSession();
        for (Session session : sessionList) {
            map.put(session.getSessionId(), session);
        }
        return map;
    }
}
