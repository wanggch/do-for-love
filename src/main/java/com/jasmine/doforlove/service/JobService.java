package com.jasmine.doforlove.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

@Slf4j
@Service
public class JobService {

    @Resource
    private TaskScheduler taskScheduler;
    private Map<String, ScheduledFuture<?>> jobMap = new HashMap<>();

    /**
     * 添加定时任务
     * @param jobId 任务ID
     * @param job 任务
     * @param cronExpression cron表达式
     */
    public void addTask(String jobId, Runnable job, String cronExpression) {
        log.info("任务ID：[{}]，任务表达式：[{}]", jobId, cronExpression);

        ScheduledFuture<?> scheduledFuture = taskScheduler.schedule(job,
                new CronTrigger(cronExpression, TimeZone.getTimeZone(TimeZone.getDefault().getID()))
        );
        jobMap.put(jobId, scheduledFuture);
        log.info("内存中总的任务数：{}", jobMap.keySet().size());
    }

    /**
     * 移除定时任务
     * @param jobId 任务ID
     */
    public void removeTask(String jobId) {
        ScheduledFuture<?> scheduledFuture = jobMap.get(jobId);
        if (Objects.isNull(scheduledFuture)) {
            scheduledFuture.cancel(true);
            jobMap.remove(jobId);
        }
    }
}
