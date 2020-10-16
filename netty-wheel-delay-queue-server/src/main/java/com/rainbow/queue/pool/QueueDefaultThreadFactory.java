package com.rainbow.queue.pool;

import cn.hutool.core.util.ObjectUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 自定义默认的时间轮延迟队列线程池工厂
 *
 * @author lxs
 * @see java.util.concurrent.ThreadFactory
 */
@Slf4j
public class QueueDefaultThreadFactory implements ThreadFactory {
    
    /**
     * 自定义线程池编号
     */
    private static final AtomicInteger POOL_NUMBER = new AtomicInteger(1);
    
    /**
     * 线程组
     */
    private final ThreadGroup group;
    
    /**
     * 自定义线程编号
     */
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    
    /**
     * 线程前缀
     */
    private final String namePrefix;
    
    public QueueDefaultThreadFactory(String groupName) {
        SecurityManager securityManager = System.getSecurityManager();
        group = (ObjectUtil.isNotNull(securityManager)) ? securityManager.getThreadGroup() : Thread.currentThread().getThreadGroup();
        namePrefix = groupName + "-pool-" +
                POOL_NUMBER.getAndIncrement() +
                "-thread-";
    }
    
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
