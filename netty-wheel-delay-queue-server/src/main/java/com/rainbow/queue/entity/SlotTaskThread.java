package com.rainbow.queue.entity;

import lombok.Data;

/**
 * 每个槽点上要运行的任务
 *
 * @author lxs
 */
@Data
public class SlotTaskThread implements Runnable {
    
    /**
     * 任务id. 如果是分布式部署多台应用，那次id要是全局唯一的
     */
    private String id;
    
    /**
     * 第几圈
     */
    private Integer cycleNum;
    
    /**
     * @param id 任务id
     */
    public SlotTaskThread(String id) {
        super();
        this.id = id;
    }
    
    /**
     * 减圈数，为0时即可执行此任务
     */
    public void countDown() {
        this.cycleNum--;
    }
    
    @Override
    public void run() {
        //todo 通过netty将任务信息发送到客户端
        
        //根据任务回调结果，删除已经执行的任务
    }
}
