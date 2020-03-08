package com.borrowing.zwh.util;
import com.borrowing.zwh.dao.*;
import com.borrowing.zwh.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @describe:
 * @author: caichangmeng <modules@163.com>
 * @since: 2018/10/22
 */
@RestController
@RequestMapping("thread")
public class ThreadController {

    @Qualifier("taskExecutor")
    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    BaseMapper base;
    @Autowired
    BorrowingMapper borrowing;
    @Autowired
    BookMapper book;
    @Autowired
    StuMapper stu;
    @Autowired
    EmailMapper em;
    @Autowired
    private IMailService mailService;
    /**
     * @describe: 无返回值
     * @return : null
     * @throws:
     */
    @GetMapping("test")
    public String threadTest() {
        taskExecutor.execute(new Runnable() {
            public void run() {
                try {
                    while (true){
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                        System.out.println("检测时间:"+sdf.format(date));
                        //逾期参数查询
                        List<Base> baseList = base.selectAll();
                        Base od1 = baseList.get(0);
                        Base od2 = baseList.get(1);
                        //借阅中查询
                        List<Borrowing> bl = borrowing.selectByStatys("借阅中");
                        Date day=new Date(); SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        //借阅逾期前判断
                        for (int i=0;i<bl.size();i++){
                            Borrowing bow = bl.get(i);
                            String outday = bow.getRd();
                            String nowday = df.format(day);
                            String outtime = dateToStamp(outday);
                            String nowtime = dateToStamp(nowday);
                            System.out.println(outday+"/"+outtime);
                            System.out.println(nowday+"/"+nowtime);
                            Long second = Long.parseLong(od1.getValue())*24*60*60;
                            Long second2 = Long.parseLong(od2.getValue())*24*60*60;
                            Long gas = Long.parseLong(outtime)-Long.parseLong(nowtime);
                            Stu stt = stu.selectByPrimaryKey(bow.getStuid());
                            Book bo = book.selectByPrimaryKey(bow.getBookid());
                            System.out.println(gas+"/"+second+"/"+second2);
                            //逾期否
                            if(gas>0){
                                //未逾期
                                //逾期前一次通知
                                if(gas<second){
                                    //判断是否已经一次通知
                                    Email e = new Email();
                                    e.setStatus("一次提醒通知");
                                    e.setBid(bow.getId());
                                    Email e2 = em.selectByStatusandBid(e);
                                    if(e2==null){
                                        System.out.println("用户："+stt.getName()+"借阅的《"+bo.getName()+"》到达一次通知日，发送一次通知信息");
                                        mailService.sendHtmlMail(stt.getCt(),"主题：归还日期提醒","内容：<br>您好！<br>您成功借阅的"+bo.getName()+"还剩7日归还！" +
                                                "<br>" +
                                                "归还日期："+bow.getRd()+"<br>" +
                                                "请您按时归还，以免产生逾期费用<br>" +
                                                "如果逾期，逾期费用为"+baseList.get(3).getValue()+"元/日");
                                        //增加记录
                                        Email e3 = new Email();
                                        e3.setBid(bow.getId());
                                        e3.setStatus("一次提醒通知");
                                        em.insert(e3);
                                    }
                                    //逾期前二次通知
                                    if(gas<second2){
                                        //判断是否已经二次通知
                                        Email e4 = new Email();
                                        e4.setStatus("二次提醒通知");
                                        e4.setBid(bow.getId());
                                        Email e5 = em.selectByStatusandBid(e4);
                                        if(e5==null){
                                            System.out.println("用户："+stt.getName()+" 借阅的《"+bo.getName()+"》到达二次通知日，发送二次通知信息");
                                            mailService.sendHtmlMail(stt.getCt(),"主题：归还日期提醒","内容：<br>您好！<br>您成功借阅的"+bo.getName()+"还剩7日归还！" +
                                                    "<br>" +
                                                    "归还日期："+bow.getRd()+"<br>" +
                                                    "请您按时归还，以免产生逾期费用<br>" +
                                                    "如果逾期，逾期费用为"+baseList.get(3).getValue()+"元/日");
                                            //增加记录
                                            Email e6 = new Email();
                                            e6.setBid(bow.getId());
                                            e6.setStatus("二次提醒通知");
                                            em.insert(e6);
                                        }
                                    }
                                }
                            }else{
                                Email e = new Email();
                                e.setBid(bow.getId());
                                e.setStatus("借阅逾期通知");
                                Email eee = em.selectByStatusandBid(e);
                                if(eee==null){
                                    System.out.println("用户："+stt.getName()+"借阅的"+bo.getName()+"出现逾期，发送逾期通知信息");
                                    //逾期
                                    mailService.sendHtmlMail(stt.getCt(),"主题：借书逾期提醒！","内容：<br>您好！<br>您成功借阅的《"+bo.getName()+"》已经逾期！" +
                                            "<br>" +
                                            "归还日期："+bow.getRd()+"<br>" +
                                            "请您按时归还，以免产生逾期费用<br>" +
                                            "如果逾期，逾期费用为"+baseList.get(3).getValue()+"元/日");
                                    //增加记录
                                    Email e2 = new Email();
                                    e2.setBid(bow.getId());
                                    e2.setStatus("借阅逾期通知");
                                    em.insert(e2);
                                    //判断逾期天数
                                    Long gas2 = 0-gas;
                                    Long days = gas2/(3600*24);
                                    int pay = Integer.parseInt((days*(Integer.parseInt(baseList.get(3).getValue())))+"");
                                    stt.setPay(pay);
                                    stu.updateByPrimaryKey(stt);
                                }
                            }
                        }
                        //借阅中查询
                        List<Borrowing> bl2 = borrowing.selectByStatys("预约中");
                        //借阅逾期前判断
                        for (int i=0;i<bl.size();i++){
                            Borrowing bow = bl.get(i);
                            String outday = bow.getCt();
                            String nowday = df.format(day);
                            String outtime = dateToStamp(outday);
                            String nowtime = dateToStamp(nowday);
                            System.out.println(outday+"/"+outtime);
                            System.out.println(nowday+"/"+nowtime);
                            Long second = Long.parseLong(od2.getValue())*24*60*60;
                            Long gas = Long.parseLong(nowtime)-Long.parseLong(outtime);
                            Stu stt = stu.selectByPrimaryKey(bow.getStuid());
                            Book bo = book.selectByPrimaryKey(bow.getBookid());
                            System.out.println(gas+"/"+second);
                            //预约逾期否
                            if(gas>second){
                                Email e = new Email();
                                e.setBid(bow.getId());
                                e.setStatus("预约取消通知");
                                Email eee = em.selectByStatusandBid(e);
                                if(eee==null){
                                    System.out.println("用户："+stt.getName()+"预约的"+bo.getName()+"超时未借阅，发送预约取消通知信息");
                                    //逾期
                                    mailService.sendHtmlMail(stt.getCt(),"主题：预约取消提醒！","内容：<br>您好！<br>您成功预约的《"+bo.getName()+"》超时未取！" +
                                            "<br>" +
                                            "预约日期："+bow.getCt()+"<br>" +
                                            "三天后您并未借阅，已经取消");
                                    //增加记录
                                    Email e2 = new Email();
                                    e2.setBid(bow.getId());
                                    e2.setStatus("预约取消通知");
                                    em.insert(e2);
                                }
                            }
                        }
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException | ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("***************************");
                System.out.println(Thread.currentThread().getName());
                System.out.println("***************************");
            }
        });
        System.out.println("***********threadTest***********");
        return "开始检测<br>1.所有借阅是否逾期<br>2.所有借阅是否到达逾期前<br>3.所有预约是否到达借阅";
    }

    /**
     * @Date:     2018/10/22
     * @describe: 有参返回方法
     * @return : null
     * @throws:
     */
    @GetMapping("hasReturn")
    public String hasReturn() throws ExecutionException, InterruptedException {
        Future<String> future = taskExecutor.submit(new Callable<String>() {
            public String call() throws Exception {
                Thread.sleep(3000);
                System.out.println("***************************");
                System.out.println(Thread.currentThread().getName());
                System.out.println("***************************");
                return "执行成功";
            }
        });
        System.out.println("***********hasReturn***********");
        String returnStr = future.get();
        System.out.println("***********end***********");
        return "success";
    }

    /*
     * 将时间转换为时间戳
     */
    public static String dateToStamp(String s) throws ParseException {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = simpleDateFormat.parse(s);
        long ts = date.getTime();
        res = String.valueOf(ts/1000);
        return res;
    }

}
