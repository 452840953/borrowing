package com.borrowing.zwh;

import com.borrowing.zwh.dao.AsyncTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@SpringBootApplication
public class ZwhApplication {
    public static void main(String[] args) {
        SpringApplication.run(ZwhApplication.class, args);
    }
}
