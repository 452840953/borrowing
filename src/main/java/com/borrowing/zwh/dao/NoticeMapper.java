package com.borrowing.zwh.dao;

import com.borrowing.zwh.entity.Notice;
import com.borrowing.zwh.entity.Stu;
import com.borrowing.zwh.model.Search;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface NoticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Notice record);

    Notice selectByPrimaryKey(Integer id);

    List<Notice> selectAll();

    int updateByPrimaryKey(Notice record);

    List<Notice> search(Search s);
}
