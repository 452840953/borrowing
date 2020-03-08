package com.borrowing.zwh.dao;

import com.borrowing.zwh.entity.Admin;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface AdminMapper {
    int deleteByPrimaryKey(Integer aid);

    int insert(Admin record);

    Admin selectByPrimaryKey(Integer aid);

    List<Admin> selectAll();

    int updateByPrimaryKey(Admin record);
}
