package com.rainbow.queue.utils;

import com.rainbow.queue.entity.SlotTaskThread;
import com.rainbow.queue.entity.TaskAttribute;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

/**
 * 任务的一些属性设置
 *
 * @author lxs
 */
public class TaskAttributeUtil {
    
    /**
     * setAttribute。
     * <ul>
     * <li>计算任务所在槽位</li>
     * <li>记录任务的加入时间，应该几点执行</li>
     * <li>任务Id和槽位的映射记录到taskSlotMapping中</li>
     * </ul>
     *
     * @param secondsLater    以当前时间点为基准，多少秒以后执行
     * @param task
     * @param taskSlotMapping
     * @return 返回所在槽位索引
     */
    public static int setAttribute(int secondsLater, SlotTaskThread task, Map<String, TaskAttribute> taskSlotMapping) {
        TaskAttribute taskAttribute = new TaskAttribute();
        Calendar calendar = Calendar.getInstance();
        //把当前时间的分钟和秒加起来
        int currentSecond = calendar.get(Calendar.MINUTE) * 60 + calendar.get(Calendar.SECOND);
        int slotIndex = (currentSecond + secondsLater) % 3600;
        
        task.setCycleNum(secondsLater / 3600);
        
        calendar.add(Calendar.SECOND, 1);
        taskAttribute.setExecuteTime(calendar.getTime());
        taskAttribute.setSlotIndex(slotIndex);
        taskAttribute.setJoinTime(new Date());
        taskSlotMapping.put(task.getId(), taskAttribute);
        
        return slotIndex;
    }
    
}
