package com.borrowing.zwh.controller;

import com.borrowing.zwh.dao.*;
import com.borrowing.zwh.entity.Admin;
import com.borrowing.zwh.entity.Base;
import com.borrowing.zwh.entity.Book;
import com.borrowing.zwh.entity.Stu;
import com.borrowing.zwh.model.Meg;
import com.borrowing.zwh.model.Search;
import com.borrowing.zwh.model.wechat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class StuController {
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
    @RequestMapping(value = "/stu/change",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg change(Stu b) {
        //数据更新处理
        m = new Meg();
        Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(sm.updateByPrimaryKey(b)==0){
            m.setStatus(0);
            m.setObj("更新失败");
        }else{
            m.setStatus(1);
            m.setObj("更新成功");
        }
        return m;
    }
    @RequestMapping(value = "/stu/wechatchange",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg wechatchange(wechat b) {
        //数据更新处理
        m = new Meg();
        Date day=new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(sm.update(b)==0){
            m.setStatus(0);
            m.setObj("更新失败");
        }else{
            m.setStatus(1);
            m.setObj(sm.selectByPrimaryKey(b.getId()));
        }
        return m;
    }
    //新增
    @RequestMapping(value = "/stu/add",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg add(Stu b) {
        //数据更新处理
        m = new Meg();
        Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        b.setUt(df.format(day));
        b.setPay(0);
        //短信通知借阅处理
        if(sm.insert(b)==0){
            m.setStatus(0);
            m.setObj("更新失败");
        }else{
            m.setStatus(1);
            m.setObj("更新成功");
        }
        return m;
    }
    //获取单个
    @RequestMapping(value = "/stu/get",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Meg getone(int id) {
        m = new Meg();
        m.setStatus(1);
        m.setObj(sm.selectByPrimaryKey(id));
        return m;
    }
    //获取所有
    @RequestMapping(value = "/stu/all",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg all() {
        m = new Meg();
        m.setStatus(1);
        m.setObj(sm.selectAll());
        return m;
    }
    //删除
    @RequestMapping(value = "/stu/del",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg del(int id) {
        m = new Meg();
        m.setStatus(sm.deleteByPrimaryKey(id));
        if(m.getStatus()==0){
            m.setObj("删除失败");
        }else{
            m.setObj("删除成功");
        }
        return m;
    }
    //查找
    @RequestMapping(value = "/stu/search",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg search(Search s) {
        m = new Meg();
        m.setStatus(1);
        m.setObj(sm.search(s));
        return m;
    }
}
