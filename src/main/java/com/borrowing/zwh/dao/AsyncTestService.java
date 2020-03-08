package com.borrowing.zwh.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

public interface AsyncTestService {
    void test(int i);
}
