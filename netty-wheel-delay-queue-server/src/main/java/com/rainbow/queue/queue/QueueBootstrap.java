package com.rainbow.queue.queue;

import cn.hutool.core.util.ObjectUtil;
import com.rainbow.queue.pool.QueueDefaultThreadFactory;
import com.rainbow.queue.task.QueueScanTimer;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 启动队列
 *
 * @author lxs
 */
@Slf4j
public class QueueBootstrap {
    
    private ScheduledExecutorService newScheduledThreadPool;
    
    public void setNewScheduledThreadPool(ScheduledExecutorService newScheduledThreadPool) {
        this.newScheduledThreadPool = newScheduledThreadPool;
    }
    
    /**
     * 创建一个环形队列；并开启定时扫描队列
     *
     * @return
     */
    public WheelQueue start() {
        if (ObjectUtil.isNull(newScheduledThreadPool)) {
            QueueDefaultThreadFactory threadFactory = new QueueDefaultThreadFactory("queueBootstrapGroup");
            newScheduledThreadPool = Executors.newScheduledThreadPool(1, threadFactory);
        }
        log.info("time wheel scanner starting...");
        WheelQueue wheelQueue = new WheelQueue();
        // 定义时间轮任务
        ReentrantLock lock = new ReentrantLock();
        QueueScanTimer timerTask = new QueueScanTimer(wheelQueue, lock);
        // 设置任务的执行，1秒后开始，每1秒执行一次
        newScheduledThreadPool.scheduleWithFixedDelay(timerTask, 1, 1, TimeUnit.SECONDS);
        log.info("time wheel scanner start up.");
        
        return wheelQueue;
    }
    
    /**
     * 停止此队列运行。
     */
    public void shutdown() {
        // 只停止扫描队列。已运行的任务暂不停止。
        if (newScheduledThreadPool != null) {
            newScheduledThreadPool.shutdown();
        }
    }
    
}
