package com.rainbow.queue.task;

import com.rainbow.queue.pool.QueueDefaultThreadFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 常用的队列任务执行线程池定义
 *
 * @author lxs
 */
public class ThreadPool {
    
    
    private static ThreadFactory slotThreadFactory = new QueueDefaultThreadFactory("slotThreadGroup");
    
    private static ThreadFactory taskThreadFactory = new QueueDefaultThreadFactory("taskThreadGroup");
    
    /**
     * 处理每个槽位的线程，循环到这个槽位，立即丢到一个线程去处理，然后继续循环队列。
     */
    private static ThreadPoolExecutor slotPool = new ThreadPoolExecutor(5, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), slotThreadFactory);
    
    /**
     * 处理每一个槽位中task集合的线程， 集合中的每个任务一个线程
     */
    private static ThreadPoolExecutor taskPool = new ThreadPoolExecutor(10, 10,
            0L, TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(), taskThreadFactory);
    
    
    public static ThreadPoolExecutor slotPool() {
        return slotPool;
    }
    
    public static ThreadPoolExecutor taskPool() {
        return taskPool;
    }
}
