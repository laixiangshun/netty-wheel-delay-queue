package com.rainbow.queue.task;

import cn.hutool.core.collection.CollUtil;
import com.rainbow.queue.entity.SlotTaskThread;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 每个槽位对应执行的任务
 *
 * @author lxs
 */
@Slf4j
public class SlotTask implements Runnable {
    
    /**
     * 当前槽位对应的任务集合
     */
    private ConcurrentLinkedQueue<SlotTaskThread> tasks;
    
    /**
     * 当前时间，单位秒
     */
    private int currentSecond;
    
    public SlotTask(ConcurrentLinkedQueue<SlotTaskThread> tasks, int currentSecond) {
        this.tasks = tasks;
        this.currentSecond = currentSecond;
    }
    
    @Override
    public void run() {
        if (CollUtil.isEmpty(tasks)) {
            return;
        }
        String taskId;
        for (SlotTaskThread task : tasks) {
            taskId = task.getId();
            if (log.isDebugEnabled()) {
                log.debug("running_current_slot:currentSecond={}, taskId={}, taskQueueSize={}", currentSecond, taskId, tasks.size());
            }
            if (task.getCycleNum() <= 0) {
                ThreadPool.taskPool().execute(task);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("countDown#running_current_slot:currentSecond={}, taskId={}", currentSecond, taskId);
                }
                task.countDown();
            }
        }
    }
}
