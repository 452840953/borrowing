package com.borrowing.zwh.dao;

import com.borrowing.zwh.entity.Base;
import com.borrowing.zwh.entity.Borrowing;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface BaseMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Base record);

    Base selectByPrimaryKey(Integer id);

    List<Base> selectAll();

    int updateByPrimaryKey(Base record);
}
