package com.rainbow.queue.task;

import cn.hutool.core.util.ObjectUtil;
import com.rainbow.queue.entity.Slot;
import com.rainbow.queue.queue.WheelQueue;
import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 类似钟表的秒针，队列是表盘，这里有个类似秒针的循环器，每秒循环一次；就类似秒针再走。
 *
 * @author lxs
 */
@Slf4j
public class QueueScanTimer implements Runnable {
    
    private WheelQueue wheelQueue;
    
    private ReentrantLock lock;
    
    public QueueScanTimer(WheelQueue wheelQueue, ReentrantLock lock) {
        super();
        this.wheelQueue = wheelQueue;
        this.lock = lock;
    }
    
    /**
     * 时间轮任务每一秒执行一次
     */
    @Override
    public void run() {
        try {
            if (ObjectUtil.isNull(wheelQueue)) {
                return;
            }
            if (lock.tryLock()) {
                Calendar calendar = Calendar.getInstance();
                int currentSecond = calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
                Slot slot = wheelQueue.peek(currentSecond);
                log.debug("current slot:" + currentSecond);
                ThreadPool.slotPool().execute(new SlotTask(slot.getTasks(), currentSecond));
            }
        } catch (Exception e) {
            log.error("槽位任务执行出错，错误信息：{}", e.getMessage(), e);
        } finally {
            if (ObjectUtil.isNotNull(lock) && lock.isLocked()) {
                lock.unlock();
            }
        }
    }
}
