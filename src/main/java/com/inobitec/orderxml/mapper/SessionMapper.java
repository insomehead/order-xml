package com.inobitec.orderxml.mapper;

import com.inobitec.orderxml.model.Session;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SessionMapper {

    List<Session> findAllSession();
}
