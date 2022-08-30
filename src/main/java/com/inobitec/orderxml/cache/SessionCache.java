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
@RequiredArgsConstructor
@EnableAsync
public class SessionCache{


    private final SessionService sessionService;

    Map<String, Session> map = new ConcurrentHashMap<>();

    @Async
    @Scheduled(fixedDelay =  60000)
    public void addCache() {
        List<Session> sessionList = sessionService.getAllSession();
        for (Session session : sessionList) {
            map.put(session.getSessionId(), session);
        }
    }

    public Map<String, Session> getMap() {
        return map;
    }
}
