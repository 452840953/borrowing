package com.borrowing.zwh.dao;

import com.borrowing.zwh.entity.Email;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface EmailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Email record);

    Email selectByPrimaryKey(Integer id);

    List<Email> selectAll();

    int updateByPrimaryKey(Email record);

    Email selectByStatusandBid(Email record);
}
