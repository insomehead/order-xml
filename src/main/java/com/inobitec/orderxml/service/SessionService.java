package com.inobitec.orderxml.service;

import com.inobitec.orderxml.model.Session;

public interface SessionService {

    Session getSessionBySessionId(String sessionId);
}
