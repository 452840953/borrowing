package com.borrowing.zwh.controller;

import com.borrowing.zwh.dao.*;
import com.borrowing.zwh.entity.*;
import com.borrowing.zwh.model.Meg;
import com.borrowing.zwh.model.Search;
import com.borrowing.zwh.model.lent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class BorrowingController {
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
    @Autowired
    EmailMapper em;
    @Autowired
    private IMailService mailService;
    Meg m;
    //修改
    @RequestMapping(value = "/borrowing/change",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg change(Borrowing b) {
        //数据更新处理
        m = new Meg();
        Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        b.setUt(df.format(day));
        b.setCt(Borrow.selectByPrimaryKey(b.getId()).getCt());
        if(Borrow.updateByPrimaryKey(b)==0){
            m.setStatus(0);
            m.setObj("更新失败");
        }else{
            m.setStatus(1);
            m.setObj("更新成功");
        }
        return m;
    }
    @RequestMapping(value = "/borrowing/changedate",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg changedate(Borrowing b) {
        //数据更新处理
        m = new Meg();
        Borrowing bb = Borrow.selectByPrimaryKey(b.getId());
        Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bb.setUt(df.format(day));
        bb.setRd(b.getRd());
        if(Borrow.updateByPrimaryKey(bb)==0){
            m.setStatus(0);
            m.setObj("更新失败");
        }else{
            m.setStatus(1);
            m.setObj("更新成功");
        }
        return m;
    }
    //新增
    @RequestMapping(value = "/borrowing/add",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg add(Borrowing b) {
        //数据更新处理
        m = new Meg();
        //获取未出借的库存
        Book bol = bom.selectByPrimaryKey(b.getBookid());
        List<Book> bols = bom.selectByBorrowing2(bol);
        for (int i =0;i<bols.size();i++){
            Book bok = bols.get(i);
            Borrowing bbbb = new Borrowing();
            bbbb.setBookid(bok.getId());
            List<Borrowing> bbb = Borrow.selectByBorrowing2(bbbb);
            if(bbb.size()==0){
                b.setBookid(bok.getId());
                break;
            }else{
                continue;
            }
        }
        //判断是否已经预约
        b.setStatus("预约中");
        Borrowing booo = Borrow.selectByBorrowingStatus(b);
        if(booo==null){
            b.setStatus("借阅中");
        }else{
            b=booo;
            b.setStatus("借阅中");
        }
        Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        b.setCt(df.format(day));
        b.setUt(df.format(day));
        //判断是否可以借阅
        Borrowing bow = Borrow.selectByBorrowing(b);
        if(bow==null){
            //书本状态修改
            Book book = bom.selectByPrimaryKey(b.getBookid());
            book.setStatus("借阅中");
            bom.updateByPrimaryKey(book);
            if(Borrow.insert(b)==0){
                m.setStatus(0);
                m.setObj("借阅失败");
            }else{
                //通知借阅处理
                Stu s = sm.selectByPrimaryKey(b.getStuid());
                System.out.println(s.getCt());
                mailService.sendHtmlMail(s.getCt(),"主题：借阅成功确认","内容：<br>欢迎使用本系统<br>您已经成功借阅"+book.getName()+"<br>" +
                        "归还日期："+b.getRd()+"<br>" +
                        "借阅号："+book.getImg().substring(0,10)+""+Borrow.selectByBorrowingStatus(b).getId()+
                        "<br>请您按时归还，以免产生逾期费用");
                Email e = new Email();
                e.setBid(Borrow.selectByBorrowingStatus(b).getId());
                e.setStatus("借阅成功通知");
                em.insert(e);
                m.setStatus(book.getId());
                m.setObj(book.getImg()+""+b.getId());
            }
        }else{
            m.setStatus(0);
            m.setObj("本书已经被借阅或者预约");
        }
        return m;
    }
    @RequestMapping(value = "/borrowing/add2",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg add2(Borrowing b) {
        //数据更新处理
        m = new Meg();
        //获取未出借的库存
        Book bol = bom.selectByPrimaryKey(b.getBookid());
        List<Book> bols = bom.selectByBorrowing2(bol);
        for (int i =0;i<bols.size();i++){
            Book bok = bols.get(i);
            Borrowing bbbb = new Borrowing();
            bbbb.setBookid(bok.getId());
            List<Borrowing> bbb = Borrow.selectByBorrowing2(bbbb);
            if(bbb.size()==0){
                b.setBookid(bok.getId());
                break;
            }else{
                continue;
            }
        }
        Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        b.setCt(df.format(day));
        b.setUt(df.format(day));
        b.setRd("预约中");
        //判断是否可以借阅
        Borrowing bow = Borrow.selectByBorrowing(b);
        if(bow==null){
            //书本状态修改
            Book book = bom.selectByPrimaryKey(b.getBookid());
            book.setStatus("预约中");
            bom.updateByPrimaryKey(book);
            if(Borrow.insert(b)==0){
                m.setStatus(0);
                m.setObj("预约失败");
            }else{
                //通知借阅处理
                Stu s = sm.selectByPrimaryKey(b.getStuid());
                System.out.println(s.getCt());
                mailService.sendHtmlMail(s.getCt(),"主题：预约成功确认","内容：<br>欢迎使用本系统<br>您已经成功预约"+book.getName()+"<br>" +
                        "借阅号："+book.getImg().substring(0,10)+""+Borrow.selectByBorrowingStatus(b).getId()+
                        "<br>请你在3天后借出，否则将取消您的预约");
                Email e = new Email();
                e.setBid(Borrow.selectByBorrowingStatus(b).getId());
                e.setStatus("预约成功通知");
                em.insert(e);
                m.setStatus(book.getId());
                m.setObj(book.getImg()+""+b.getId());
            }
        }else{
            m.setStatus(0);
            m.setObj("本书已经被借阅或者预约");
        }
        return m;
    }
    //续借
    @RequestMapping(value = "/borrowing/continue",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg continues(int id,String rd) {
        m = new Meg();
        Borrowing b = Borrow.selectByPrimaryKey(id);
        b.setRd(rd);
        if(Borrow.updateByPrimaryKey(b)!=0){
            m.setStatus(1);
            m.setObj("续借成功");
            mailService.sendHtmlMail(sm.selectByPrimaryKey(b.getStuid()).getCt(),"主题：续借成功确认","内容：<br>欢迎使用本系统<br>您已经成功续借"+bom.selectByPrimaryKey(b.getBookid()).getName()+"<br>" +
                    "归还日期："+b.getRd()+"<br>" +
                    "借阅号："+bom.selectByPrimaryKey(b.getBookid()).getImg().substring(0,10)+""+b.getId()+
                    "<br>请您按时归还，以免产生逾期费用");
            Email e = new Email();
            e.setBid(Borrow.selectByBorrowingStatus(b).getId());
            e.setStatus("续借成功通知");
            em.insert(e);
        }else{
            m.setStatus(0);
            m.setObj("续借失败");
        }
        return m;
    }
    @RequestMapping(value = "/borrowing/continue2",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg continues2(int id,String rd) {
        m = new Meg();
        Borrowing b = Borrow.selectByPrimaryKey(id);
        b.setRd(rd);
        b.setStatus("借阅中");
        if(Borrow.updateByPrimaryKey(b)!=0){
            m.setStatus(1);
            m.setObj("借阅成功");
            mailService.sendHtmlMail(sm.selectByPrimaryKey(b.getStuid()).getCt(),"主题：借阅成功确认","内容：<br>欢迎使用本系统<br>您已经成功借阅"+bom.selectByPrimaryKey(b.getBookid()).getName()+"<br>" +
                    "归还日期："+b.getRd()+"<br>" +
                    "借阅号："+bom.selectByPrimaryKey(b.getBookid()).getImg().substring(0,10)+""+b.getId()+
                    "<br>请您按时归还，以免产生逾期费用");
            Email e = new Email();
            e.setBid(Borrow.selectByBorrowingStatus(b).getId());
            e.setStatus("借阅成功通知");
            em.insert(e);
        }else{
            m.setStatus(0);
            m.setObj("借阅失败");
        }
        return m;
    }
    //归还
    @RequestMapping(value = "/borrowing/return",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg returns(Borrowing b) {
        m = new Meg();
        Borrowing bow = Borrow.selectByBorrowingStatus(b);
        if(bow!=null){
            Book book = bom.selectByPrimaryKey(bow.getBookid());
            if(book!=null){
                book.setStatus("空闲中");
                bom.updateByPrimaryKey(book);

                Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                bow.setUt(df.format(day));
                bow.setStatus("已经归还");
                if(Borrow.updateByPrimaryKey(bow)!=0){
                    m.setStatus(1);
                    m.setObj("归还成功");
                }
            }else{
                m.setStatus(0);
                m.setObj("本书从数据库中被删除");
            }
        }else{
            m.setStatus(0);
            m.setObj("本借阅记录被删除");
        }
        return m;
    }
    //获取单个
    @RequestMapping(value = "/borrowing/get",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Meg getone(int id) {
        m = new Meg();
        m.setStatus(1);
        lent l = new lent();
        l.setBook(bom.selectByPrimaryKey(Borrow.selectByPrimaryKey(id).getBookid()));
        l.setStu(sm.selectByPrimaryKey(Borrow.selectByPrimaryKey(id).getStuid()));
        l.setBorrowing(Borrow.selectByPrimaryKey(id));
        m.setObj(l);
        return m;
    }
    //获取所有
    @RequestMapping(value = "/borrowing/all",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg all() {
        m = new Meg();
        m.setStatus(1);
        List<Borrowing> lb = Borrow.selectAll();
        List<lent> lb2 = new ArrayList<lent>();
        for (int i=0;i<lb.size();i++){
            lent l = new lent();
            l.setBook(bom.selectByPrimaryKey(lb.get(i).getBookid()));
            l.setStu(sm.selectByPrimaryKey(lb.get(i).getStuid()));
            l.setBorrowing(lb.get(i));
            lb2.add(l);
        }
        m.setObj(lb2);
        return m;
    }
    //删除
    @RequestMapping(value = "/borrowing/del",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg del(int id) {
        m = new Meg();
        m.setStatus(Borrow.deleteByPrimaryKey(id));
        if(m.getStatus()==0){
            m.setObj("删除失败");
        }else{
            m.setObj("删除成功");
        }
        return m;
    }
    //查找
    @RequestMapping(value = "/borrowing/search",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg search(Search s) {
        m = new Meg();
        m.setStatus(1);
        List<lent> lb2 = new ArrayList<lent>();
        if(s.getItem().equals("id")){
            if(s.getSearch().length()>=11){
                String img = s.getSearch().substring(0,10);
                s.setSearch(s.getSearch().substring(10,s.getSearch().length()));
                System.out.println(s.getItem());
                System.out.println(s.getSearch());
                List<Borrowing> lb = Borrow.search(s);
                Book book = bom.selectByPrimaryKey(lb.get(0).getBookid());
                if(book.getImg().substring(0,10).equals(img)){
                    lent l = new lent();
                    l.setBook(book);
                    l.setStu(sm.selectByPrimaryKey(lb.get(0).getStuid()));
                    l.setBorrowing(lb.get(0));
                    lb2.add(l);
                }
            }else{
                String img = s.getSearch().substring(0,s.getSearch().length());
                s.setSearch(img);
                s.setItem("img");
                List<Book> lb = bom.search(s);
                List<Borrowing> lb3 = new ArrayList<Borrowing>();
                for (int i=0;i<lb.size();i++){
                    List<Borrowing> Bll = Borrow.selectByBookid(lb.get(i).getId());
                    if(Bll.size()==0){
                        continue;
                    }else{
                        lb3.addAll(Bll);
                    }
                }
                for (int i=0;i<lb3.size();i++){
                    lent l = new lent();
                    l.setBook(bom.selectByPrimaryKey(lb3.get(i).getBookid()));
                    l.setStu(sm.selectByPrimaryKey(lb3.get(i).getStuid()));
                    l.setBorrowing(lb3.get(i));
                    lb2.add(l);
                }
            }
        }else{
            List<Borrowing> lb = Borrow.search(s);
            for (int i=0;i<lb.size();i++){
                lent l = new lent();
                l.setBook(bom.selectByPrimaryKey(lb.get(i).getBookid()));
                l.setStu(sm.selectByPrimaryKey(lb.get(i).getStuid()));
                l.setBorrowing(lb.get(i));
                lb2.add(l);
            }
        }

        m.setObj(lb2);
        return m;
    }
}
