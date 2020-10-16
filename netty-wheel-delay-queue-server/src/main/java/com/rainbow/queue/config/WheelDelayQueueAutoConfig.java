package com.rainbow.queue.config;

import com.rainbow.queue.pool.QueueDefaultThreadFactory;
import com.rainbow.queue.queue.QueueBootstrap;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 时间轮延迟队列自动配置
 *
 * @author lxs
 */
@Configuration
@ConditionalOnMissingBean(value = QueueBootstrap.class)
public class WheelDelayQueueAutoConfig {
    
    @Bean(name = "queueBootstrap", initMethod = "start", destroyMethod = "shutdown")
    public QueueBootstrap queueBootstrap() {
        QueueBootstrap queueBootstrap = new QueueBootstrap();
        QueueDefaultThreadFactory threadFactory = new QueueDefaultThreadFactory("queueBootstrapGroup");
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1, threadFactory);
        queueBootstrap.setNewScheduledThreadPool(executorService);
        return queueBootstrap;
    }
    
}
