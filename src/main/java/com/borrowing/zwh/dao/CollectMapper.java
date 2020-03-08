package com.borrowing.zwh.dao;

import com.borrowing.zwh.entity.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface CollectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Collect record);

    Collect selectByPrimaryKey(Integer id);

    Collect select(Collect id);

    int delete(Collect id);

    List<Collect> selectAll();

    List<Collect> selectByStuid(Integer id);

    int updateByPrimaryKey(Collect record);
}
