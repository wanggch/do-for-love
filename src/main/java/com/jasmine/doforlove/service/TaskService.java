package com.jasmine.doforlove.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.jasmine.doforlove.constant.DayEventStatus;
import com.jasmine.doforlove.constant.Status;
import com.jasmine.doforlove.constant.TaskType;
import com.jasmine.doforlove.controller.param.TaskAddParam;
import com.jasmine.doforlove.dao.DayEventDao;
import com.jasmine.doforlove.dao.TaskDao;
import com.jasmine.doforlove.entity.DayEvent;
import com.jasmine.doforlove.entity.Task;
import com.jasmine.doforlove.job.DayEventJob;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

@Service
public class TaskService {

    @Resource
    private TaskDao taskDao;
    @Resource
    private DayEventDao dayEventDao;
    @Resource
    private JobService jobService;
    @Resource
    private DayEventJob dayEventJob;

    @Transactional(rollbackFor = Exception.class)
    public Task addTask(TaskAddParam param) {
        TaskType taskType = TaskType.getTaskType(param.getType());
        if (Objects.isNull(taskType)) {
            throw new RuntimeException("任务类型错误！");
        }
        if (Objects.equals(taskType, TaskType.ONCE)) {
            DayEvent dayEvent = new DayEvent();
            dayEvent.setName(param.getName());
            dayEvent.setRemark(param.getRemark());
            dayEvent.setStatus(DayEventStatus.ENABLE.getCode());
            dayEvent.setCreateTime(new Date());
            dayEvent.setEventDate(DateUtil.parseDate(param.getContent()));
            dayEventDao.insert(dayEvent);
            return null;
        } else {
            Task task = new Task();
            task.setName(param.getName());
            task.setRemark(param.getRemark());
            task.setCron(generateCronExpression(param.getType(), param.getContent()));
            task.setStatus(Status.ENABLE.getCode());
            int rows = taskDao.insert(task);
            if (rows > 0) {
                DayEvent dayEvent = new DayEvent();
                dayEvent.setTaskId(task.getId());
                dayEvent.setName(param.getName());
                dayEvent.setRemark(param.getRemark());
                dayEvent.setStatus(DayEventStatus.ENABLE.getCode());
                dayEventJob.setDayEvent(dayEvent);
                jobService.addTask(task.getId(), dayEventJob, task.getCron());
            }
            return task;
        }
    }
    
    public void removeTask(String jobId) {
        jobService.removeTask(jobId);
    }

    private String generateCronExpression(String type, String content) {
        TaskType taskType = TaskType.getTaskType(type);
        return StrUtil.format(taskType.getCronPattern(), content);
    }
}
