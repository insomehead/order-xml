package com.inobitec.orderxml.cache;

import com.inobitec.orderxml.model.Session;

import java.util.Map;

public interface Cache {

    Map<String, Session> addCache();

}
