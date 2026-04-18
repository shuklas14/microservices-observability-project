package com.order.inventory_service.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkerPoolConfig {
    @Bean
    public ExecutorService workerPool(){
        return new 
        ThreadPoolExecutor(2, 5, 60,
             TimeUnit.SECONDS,new LinkedBlockingQueue<>(100),
              new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
