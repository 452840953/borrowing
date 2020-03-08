package com.borrowing.zwh.dao;

import com.borrowing.zwh.entity.Report;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface ReportMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Report record);

    Report selectByPrimaryKey(Integer id);

    List<Report> selectAll();

    int updateByPrimaryKey(Report record);
}
