package com.inobitec.orderxml.cache;

import com.inobitec.orderxml.model.Session;
import com.inobitec.orderxml.service.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@EnableAsync
@RequiredArgsConstructor
public class SessionCache{

    private final SessionService sessionService;

    private final Map<String, Session> map = new ConcurrentHashMap<>();

    @Async
    @Scheduled(fixedRate =  60000)
    public void addCache() {
        map.clear();
        List<Session> sessionList = sessionService.getAllSession();
        for (Session session : sessionList) {
            map.put(session.getSessionId(), session);
        }
    }

    public Session getSessionBySessionId(String sessionId) {
        return map.get(sessionId);
    }
}
