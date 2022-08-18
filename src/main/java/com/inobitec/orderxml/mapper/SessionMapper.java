package com.inobitec.orderxml.mapper;

import com.inobitec.orderxml.model.Session;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SessionMapper {

    Session findSessionBySessionId(@Param("sessionId") String sessionId);
}
