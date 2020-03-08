package com.borrowing.zwh.controller;

import com.borrowing.zwh.dao.*;
import com.borrowing.zwh.entity.*;
import com.borrowing.zwh.model.Meg;
import com.borrowing.zwh.model.Search;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class NoticeController {
    @Autowired
    AdminMapper am;
    @Autowired
    BaseMapper bm;
    @Autowired
    BookMapper bom;
    @Autowired
    BorrowingMapper Borrow;
    @Autowired
    NoticeMapper nm;
    @Autowired
    StuMapper sm;
    @Autowired
    HttpSession session;
    Meg m;
    //修改
    @RequestMapping(value = "/notice/change",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg change(Notice b) {
        //数据更新处理
        m = new Meg();
        Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        b.setUt(df.format(day));
        if(nm.updateByPrimaryKey(b)==0){
            m.setStatus(0);
            m.setObj("更新失败");
        }else{
            m.setStatus(1);
            m.setObj("更新成功");
        }
        return m;
    }
    //新增
    @RequestMapping(value = "/notice/add",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg add(Notice b) {
        //数据更新处理
        m = new Meg();
        Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        b.setUt(df.format(day));
        if(nm.insert(b)==0){
            m.setStatus(0);
            m.setObj("更新失败");
        }else{
            m.setStatus(1);
            m.setObj("更新成功");
        }
        return m;
    }
    //获取单个
    @RequestMapping(value = "/notice/get",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Meg getone(int id) {
        m = new Meg();
        m.setStatus(1);
        m.setObj(nm.selectByPrimaryKey(id));
        return m;
    }
    //获取所有
    @RequestMapping(value = "/notice/all",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg all() {
        m = new Meg();
        m.setStatus(1);
        m.setObj(nm.selectAll());
        return m;
    }
    //删除
    @RequestMapping(value = "/notice/del",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg del(int id) {
        m = new Meg();
        m.setStatus(nm.deleteByPrimaryKey(id));
        if(m.getStatus()==0){
            m.setObj("删除失败");
        }else{
            m.setObj("删除成功");
        }
        return m;
    }
    //查找
    @RequestMapping(value = "/notice/search",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg search(Search s) {
        m = new Meg();
        m.setStatus(1);
        m.setObj(nm.search(s));
        return m;
    }
}
