package com.borrowing.zwh.dao;

import com.borrowing.zwh.entity.Book;
import com.borrowing.zwh.entity.Borrowing;
import com.borrowing.zwh.model.Search;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
@Mapper
@Component
public interface BorrowingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Borrowing record);

    Borrowing selectByPrimaryKey(Integer id);

    Borrowing selectByBorrowing(Borrowing re);

    Borrowing selectByBorrowingStatus(Borrowing re);

    List<Borrowing> selectAll();

    List<Borrowing> selectByStuid(Borrowing re);

    int updateByPrimaryKey(Borrowing record);

    List<Borrowing> search(Search s);

    List<Borrowing> selectByStatys(String status);

    List<Borrowing> selectByBorrowing2(Borrowing borrowing);

    List<Borrowing> selectNotReturn();

    List<Borrowing> selectByBookid(Integer id);
}
