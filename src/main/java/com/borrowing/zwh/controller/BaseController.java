package com.borrowing.zwh.controller;

import com.borrowing.zwh.dao.*;
import com.borrowing.zwh.entity.*;
import com.borrowing.zwh.model.Meg;
import com.borrowing.zwh.util.ExcelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BaseController {
    @Autowired
    AdminMapper am;
    @Autowired
    BaseMapper bm;
    @Autowired
    BookMapper bom;
    @Autowired
    NoticeMapper nm;
    @Autowired
    StuMapper sm;
    @Autowired
    BorrowingMapper borrow;
    @Autowired
    HttpSession session;
    Meg m;
    //excel
    @RequestMapping(value = "/base/excel",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public void ex(String db, HttpServletResponse response) throws IOException {
        List<List<String>> excelData = new ArrayList<>();
        List<String> head = new ArrayList<>();
        String fileName="";
        String sheetName="";
        if(db.equals("stu")){
            head.add("编号");
            head.add("学号");
            head.add("用户名");
            head.add("欠款");
            head.add("邮箱");
            List<Stu> stu = sm.selectAll();
            excelData.add(head);
            for (int i=0;i<stu.size();i++){
                List<String> data1 = new ArrayList<>();
                Stu s = stu.get(i);
                data1.add(s.getId()+"");
                data1.add(s.getName()+"");
                data1.add(s.getSchool()+"");
                data1.add(s.getPay()+"");
                data1.add(s.getCt()+"");
                excelData.add(data1);
            }
            sheetName = "学生表";
            fileName = "StuExcel.xls";
        }else if(db.equals("borrowing")){
            head.add("借阅号");
            head.add("借阅人id");
            head.add("借阅人姓名");
            head.add("书目id");
            head.add("书目名");
            head.add("状态");
            head.add("归还日期");
            head.add("创建日期");
            head.add("更新日期");
            List<Borrowing> stu = borrow.selectAll();
            excelData.add(head);
            for (int i=0;i<stu.size();i++){
                List<String> data1 = new ArrayList<>();
                Borrowing s = stu.get(i);
                data1.add(bom.selectByPrimaryKey(s.getBookid()).getImg()+""+s.getId()+"");
                data1.add(s.getStuid()+"");
                data1.add(sm.selectByPrimaryKey(s.getStuid()).getName()+"");
                data1.add(s.getBookid()+"");
                data1.add(bom.selectByPrimaryKey(s.getBookid()).getName()+"");
                data1.add(s.getStatus()+"");
                data1.add(s.getRd()+"");
                data1.add(s.getCt()+"");
                data1.add(s.getUt()+"");
                excelData.add(data1);
            }
            sheetName = "借阅表";
            fileName = "BorrowingExcel.xls";
        }else if(db.equals("book")){
            head.add("书号");
            head.add("书名");
            head.add("介绍");
            head.add("状态");
            head.add("作者");
            head.add("更新日期");
            List<Book> stu = bom.selectAll();
            excelData.add(head);
            for (int i=0;i<stu.size();i++){
                List<String> data1 = new ArrayList<>();
                Book s = stu.get(i);
                data1.add(s.getId()+"");
                data1.add(s.getName()+"");
                data1.add(s.getIntro()+"");
                data1.add(s.getStatus()+"");
                data1.add(s.getCt()+"");
                data1.add(s.getUt()+"");
                excelData.add(data1);
            }
            sheetName = "图书表";
            fileName = "BookExcel.xls";
        }
        ExcelController.exportExcel(response, excelData, sheetName, fileName, 15);
    }
    //登录
    @RequestMapping(value = "/admin/login",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg loginadmin(String username, String password){
        m = new Meg();
        Admin a = am.selectByPrimaryKey(1);
        if(a.getAname().equals(username)){
            if(a.getPassword().equals(password)){
                m.setStatus(1);
                m.setObj("登录成功！");
                session.setAttribute("id",a.getAid());
            }else{
                m.setStatus(0);
                m.setObj("密码错误！");
            }
        }else{
            m.setStatus(0);
            m.setObj("用户名错误！");
        }
        return m;
    }
    //登录
    @RequestMapping(value = "/stu/login",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg loginstu(Stu u){
        m = new Meg();
        Stu a = sm.selectByStu(u);
        if(a.getName().equals(u.getName())){
            if(a.getUt().equals(u.getUt())){
                m.setStatus(1);
                m.setObj(a);
                session.setAttribute("id",a.getId());
            }else{
                m.setStatus(0);
                m.setObj("密码错误！");
            }
        }else{
            m.setStatus(0);
            m.setObj("用户名错误！");
        }
        return m;
    }
    //获取当前用户
    @RequestMapping(value = "/admin/get",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg get() {
        m = new Meg();
        if(session.getAttribute("id")==null){
            m.setObj("用户未登录！");
            m.setStatus(0);
        }else{
            m.setStatus(1);
            m.setObj(am.selectByPrimaryKey((int)session.getAttribute("id")));
        }
        return m;
    }
    //基础参数修改
    @RequestMapping(value = "/base/change",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg change(Base b) {
        m = new Meg();
        if(bm.updateByPrimaryKey(b)==0){
            m.setStatus(0);
            m.setObj("更新失败");
        }else{
            m.setStatus(1);
            m.setObj("更新成功");
        }
        return m;
    }
    //获取基础参数
    @RequestMapping(value = "/base/get",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg getbase() {
        m = new Meg();
        m.setStatus(1);
        m.setObj(bm.selectAll());
        return m;
    }
    //5大线程
}
