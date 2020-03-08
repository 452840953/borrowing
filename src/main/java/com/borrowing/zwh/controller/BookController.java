package com.borrowing.zwh.controller;

import com.borrowing.zwh.dao.*;
import com.borrowing.zwh.entity.*;
import com.borrowing.zwh.model.*;
import com.borrowing.zwh.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
public class BookController {
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
    CollectMapper cm;
    @Autowired
    ReportMapper rm;
    @Autowired
    HttpSession session;
    Meg m;
    //修改
    @RequestMapping(value = "/book/change",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg change(Book b,@RequestParam(name = "file",required = false) MultipartFile file) {
        if (file==null) {
            //数据更新处理
            m = new Meg();
            Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            b.setUt(df.format(day));
            if(bom.updateByPrimaryKey(b)==0){
                m.setStatus(0);
                m.setObj("更新失败");
            }else{
                m.setStatus(1);
                m.setObj("更新成功");
            }
        }else{
            String filePath = "D:/work/img/";
            String fileName = file.getOriginalFilename();
            String name1 = getRandomString2(10)+".jpg";
            String name =filePath + name1;
            File dest = new File(name);
            try {
                System.out.println(b.getUt());
                System.out.println(b.getImg());
                if(b.getUt().equals("change")){
                    file.transferTo(dest);
                    b.setImg(name1);
                }
            } catch (IOException e) {
                m.setStatus(0);
                m.setObj("更新失败");
                return m;
            }
            //数据更新处理
            m = new Meg();
            Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            b.setUt(df.format(day));
            if(bom.updateByPrimaryKey(b)==0){
                m.setStatus(0);
                m.setObj("更新失败");
            }else{
                m.setStatus(1);
                m.setObj("更新成功");
            }
        }
        return m;
    }
    //新增
    public static String getRandomString2(int length){
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(3);
            long result=0;
            switch(number){
                case 0:
                    result=Math.round(Math.random()*25+65);
                    sb.append(String.valueOf((char)result));
                    break;
                case 1:
                    result=Math.round(Math.random()*25+97);
                    sb.append(String.valueOf((char)result));
                    break;
                case 2:
                    sb.append(String.valueOf(new Random().nextInt(10)));
                    break;
            }


        }
        return sb.toString();
    }
    @RequestMapping(value = "/book/add",method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg add(Book b,@RequestParam("file") MultipartFile file) {
        //图片上传处理
        if (file.isEmpty()) {
            m.setStatus(0);
            m.setObj("更新失败");
        }else{
            String filePath = "D:/work/img/";
            String fileName = file.getOriginalFilename();
            String name1 = getRandomString2(10)+".jpg";
            String name =filePath + name1;
            File dest = new File(name);
            try {
                file.transferTo(dest);
            } catch (IOException e) {
                m.setStatus(0);
                m.setObj("更新失败");
                return m;
            }
            //数据更新处理
            m = new Meg();
            Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            b.setStatus("空闲中");
            b.setUt(df.format(day));
            b.setImg(name1);
            if(bom.insert(b)==0){
                m.setStatus(0);
                m.setObj("更新失败");
            }else{
                m.setStatus(1);
                m.setObj("更新成功");
            }
        }
        return m;
    }
    //获取单个(仅借阅)
    @RequestMapping(value = "/book/get2",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Meg getone2(int id,int sid) {
        m = new Meg();
        m.setStatus(1);
        Book book = bom.selectByPrimaryKey(id);
        lent l = new lent();
        Borrowing borrowing = new Borrowing();
        borrowing.setBookid(id);
        borrowing.setStuid(sid);
        borrowing.setStatus("借阅中");
        l.setBorrowing(borrow.selectByBorrowingStatus(borrowing));
        l.setBook(book);
        m.setObj(l);
        return m;
    }
    //获取单个(仅预约)
    @RequestMapping(value = "/book/get3",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Meg getone3(int id,int sid) {
        m = new Meg();
        m.setStatus(1);
        Book book = bom.selectByPrimaryKey(id);
        lent l = new lent();
        Borrowing borrowing = new Borrowing();
        borrowing.setBookid(id);
        borrowing.setStuid(sid);
        borrowing.setStatus("预约中");
        l.setBorrowing(borrow.selectByBorrowingStatus(borrowing));
        l.setBook(book);
        m.setObj(l);
        return m;
    }
    //获取单个
    @RequestMapping(value = "/book/get",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Meg getone(int id) {
        m = new Meg();
        m.setStatus(1);
        Book book = bom.selectByPrimaryKey(id);
        book.setGc(bom.selectByBorrowing2(book).size()+"");
        book.setKj(bom.selectByBorrowing2(book).size());
        System.out.println(book.getGc());
        //可借判定
        //查出所有未归还书本
        List<Borrowing> ntb = borrow.selectNotReturn();
        for (int i=0;i<ntb.size();i++) {
            Borrowing b = ntb.get(i);
            System.out.println(book.getGc());
            if(bom.selectByPrimaryKey(b.getBookid()).getName().equals(book.getName())){
                System.out.println(book.getGc());
                if(bom.selectByPrimaryKey(b.getBookid()).getCt().equals(book.getCt())){
                        int gc = Integer.parseInt(book.getGc());
                        System.out.println(gc);
                        book.setKj(book.getKj()-1);
                    }
                }
        }
        m.setObj(book);
        return m;
    }
    //获取所有
    @RequestMapping(value = "/book/all",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg all() {
        m = new Meg();
        m.setStatus(1);
        m.setObj(bom.selectAll());
        return m;
    }
    @RequestMapping(value = "/book/collect",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg collect(Collect c) {
        m = new Meg();
        if(cm.select(c)==null){
            m.setStatus(1);
            cm.insert(c);
        }else{
            m.setStatus(0);
            cm.delete(c);
        }
        return m;
    }
    @RequestMapping(value = "/book/ifcollect",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg ifcollect(Collect c) {
        m = new Meg();
        if(cm.select(c)==null){
            m.setStatus(0);
        }else{
            m.setStatus(1);
        }
        return m;
    }

    @RequestMapping(value = "/report/insert",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg insertreport(Report c) {
        m = new Meg();
        Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(c.getSid());
        c.setCreatetime(df.format(day));
        if(rm.insert(c)==0){
            m.setStatus(0);
            m.setObj("发布失败");
        }else{
            m.setStatus(1);
            m.setObj("发布成功");
        }
        return m;
    }

    @RequestMapping(value = "/report/all",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg allreport(Report c) {
        m = new Meg();
        m.setStatus(1);
        m.setObj(rm.selectAll());
        return m;
    }

    //获取所有
    @RequestMapping(value = "/book/indexall",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg al2l() {
        m = new Meg();
        m.setStatus(1);
        m.setObj(bom.selectIndexAll());
        return m;
    }
    @RequestMapping(value = "/book/wechatall",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg wechatall() {
        m = new Meg();
        m.setStatus(1);
        List<Book> bl = bom.selectAll();
        List<WechatBook> book = new ArrayList<WechatBook>();
        for (int i=0;i<bl.size();i++){
            boolean ift=true;
            for (int j=0;j<book.size();j++){
                if(bl.get(i).getName().equals(book.get(j).getName())){
                    ift=false;
                    book.get(j).setGc(book.get(j).getGc()+1);
                    book.get(j).setKj(book.get(j).getKj()+1);
                }
            }
            if(ift){
                WechatBook wb = new WechatBook();
                wb.setBook(bl.get(i));
                wb.setCt(bl.get(i).getCt());
                wb.setName(bl.get(i).getName());
                wb.setGc(1);
                wb.setKj(1);
                book.add(wb);
            }
        }
        //可借判定
        //查出所有未归还书本
        List<Borrowing> ntb = borrow.selectNotReturn();
        for (int i=0;i<ntb.size();i++) {
            Borrowing b = ntb.get(i);
            for (int j=0;j<book.size();j++){
                if(bom.selectByPrimaryKey(b.getBookid()).getName().equals(book.get(j).getName())){
                    if(bom.selectByPrimaryKey(b.getBookid()).getCt().equals(book.get(j).getCt())){
                        book.get(j).setKj(book.get(j).getKj()-1);
                    }
                }
            }
        }
        m.setObj(book);
        return m;
    }
    //获取所有（仅借阅）
    @RequestMapping(value = "/book/myborrowing",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg myborrowing(int id) {
        m = new Meg();
        m.setStatus(1);
        //获得该学生所有借阅
        Borrowing bie = new Borrowing();
        bie.setStatus("借阅中");
        bie.setStuid(id);
        List<Borrowing> bl = borrow.selectByStuid(bie);
        List<Book> bl2 = new ArrayList<Book>();
        for(int i=0;i<bl.size();i++){
            bl2.add(bom.selectByPrimaryKey(bl.get(i).getBookid()));
        }
        m.setObj(bl2);
        return m;
    }
    //获取所有（仅收藏）
    @RequestMapping(value = "/book/mycollect",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg mycollect(int id) {
        m = new Meg();
        m.setStatus(1);
        //获得该学生所有借阅
        List<Collect> cl = cm.selectByStuid(id);
        List<Book> bl2 = new ArrayList<Book>();
        for(int i=0;i<cl.size();i++){
            bl2.add(bom.selectByPrimaryKey(cl.get(i).getBookid()));
        }
        m.setObj(bl2);
        return m;
    }
    //获取所有（仅预约）
    @RequestMapping(value = "/book/myborrowing2",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg myborrowing2(int id) {
        m = new Meg();
        m.setStatus(1);
        //获得该学生所有借阅
        Borrowing bie = new Borrowing();
        bie.setStatus("预约中");
        bie.setStuid(id);
        List<Borrowing> bl = borrow.selectByStuid(bie);
        List<Book> bl2 = new ArrayList<Book>();
        for(int i=0;i<bl.size();i++){
            bl2.add(bom.selectByPrimaryKey(bl.get(i).getBookid()));
        }
        m.setObj(bl2);
        return m;
    }
    //删除
    @RequestMapping(value = "/book/del",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg del(int id) {
        m = new Meg();
        m.setStatus(bom.deleteByPrimaryKey(id));
        if(m.getStatus()==0){
            m.setObj("删除失败");
        }else{
            m.setObj("删除成功");
        }
        return m;
    }
    //查找
    @RequestMapping(value = "/book/search",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg search(Search s) {
        m = new Meg();
        m.setStatus(1);
        m.setObj(bom.search(s));
        return m;
    }
    //查找
    @RequestMapping(value = "/book/searchbywechat",method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Meg searchbywechat(Search s) {
        m = new Meg();
        m.setStatus(1);
        List<WechatSearch> bl2 = new ArrayList<WechatSearch>();
        if(s.getSearch()!=""){
            List<Book> bl = bom.search(s);
            for (int i=0;i<bl.size();i++){
                WechatSearch w = new WechatSearch();
                w.setText(bl.get(i).getName());
                w.setValue(bl.get(i).getId());
                bl2.add(w);
            }
        }
        m.setObj(bl2);
        return m;
    }
}
