package com.borrowing.zwh.dao;

import com.borrowing.zwh.entity.Book;
import com.borrowing.zwh.model.Search;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface BookMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Book record);

    Book selectByPrimaryKey(Integer id);

    List<Book> selectAll();

    int updateByPrimaryKey(Book record);

    List<Book> search(Search s);

    List<Book> selectByBorrowing2(Book s);

    List<Book> selectIndexAll();
}
