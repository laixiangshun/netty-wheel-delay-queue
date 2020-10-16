package com.rainbow.queue.entity;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 时间轮队列的槽位
 *
 * @author lxs
 */
@Slf4j
public class Slot {
    
    /**
     * 用来存放任务。也可更加时间情况使用其他容器存放，如：ConcurrentSkipListSet,Set, Map
     */
    private ConcurrentLinkedQueue<SlotTaskThread> tasks = new ConcurrentLinkedQueue<>();
    
    public ConcurrentLinkedQueue<SlotTaskThread> getTasks() {
        return tasks;
    }
    
    /**
     * 加入一个任务
     *
     * @param task
     */
    public void add(SlotTaskThread task) {
        tasks.add(task);
    }
    
    /**
     * 删除一个任务
     *
     * @param taskId
     */
    public void remove(String taskId) {
        tasks.removeIf(abstractTask -> taskId.equals(abstractTask.getId()));
    }
}
