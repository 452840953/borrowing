package com.borrowing.zwh.dao;

import com.borrowing.zwh.entity.Book;
import com.borrowing.zwh.entity.Stu;
import com.borrowing.zwh.model.Search;
import com.borrowing.zwh.model.wechat;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface StuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Stu record);

    Stu selectByStu(Stu record);

    Stu selectByPrimaryKey(Integer id);

    List<Stu> selectAll();

    int updateByPrimaryKey(Stu record);

    int update(wechat record);

    List<Stu> search(Search s);
}
