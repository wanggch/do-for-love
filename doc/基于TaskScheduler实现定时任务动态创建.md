
> 本人的记性不是很好，比较容易忘记、遗漏一些待办的事项。因为经常遗漏一些事情，时常惹老婆大人生气。这是我想要做一个待办事项提醒的初衷。

我想要创建的待办事项大体分为如下几种：

- 未来某一天要做的且只做一次的事情；
- 每天都要做的事情；
- 每周周X要做的事情；
- 每月X号要做的事情；
- 每间隔X天要做的事情；

对应这5种待办事项我预定义了5种Cron表达式，详情如下：

```java
@Getter
public enum TaskType implements BaseEnum<String> {

    // 每天要做的事情
    DAILY("daily", "0 0 0 * * ?"),
    // 每周周X要做的事情
    WEEKLY("weekly", "0 0 0 ? * {}"),
    // 每月X号要做的事情
    MONTHLY("monthly", "0 0 0 {} * ?"),
    // 每隔X天要做的事情
    INTERVAL("interval", "0 0 0 1/{} * ? "),
    // 只做一次的事情
    ONCE("once", "");

    /**
     * 类型编码
     */
    private String code;
    /**
     * 表达式模板
     */
    private String cronPattern;

    TaskType(String code, String cronPattern) {
        this.code = code;
        this.cronPattern = cronPattern;
    }

    /**
     * 根据类型编码获取任务类型
     * @param code 类型编码
     * @return 任务类型
     */
    public static TaskType getTaskType(String code) {
        for (TaskType taskType : TaskType.values()) {
            if (Objects.equals(taskType.getCode(), code)) {
                return taskType;
            }
        }
        return null;
    }
}
```

未来某一天要做的且只做一次的事情不走定时任务逻辑，不是本篇文章讲述的重点。意思就是未来某一天要做的且只做一次的事情的逻辑实现，我们这里不讲了。

我画了一个创建定时任务的流程图，方便大家理解。

创建定时任务具体实现类

```java
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
}
```

`addTask()`方法包含三个参数：

- `jobId`：定时任务的惟一标识，可以通过此标识从Job Map找到该任务；
- `job`：我们定义的任务，里面包含我们想要定时执行的具体业务代码；
- `cronExpression`：cron表达式，满足cron表达式时会执行`job`的`run()`方法。

我们添加一个定时任务的流程大体是：

1. 定义一个任务类，实现`Runnable`接口；
2. 明确步骤1中定义的任务类想要触发的cron表达式；
3. 调用`JobService`的`addTask()`方法。

经过上面三步操作，当时间点满足cron表达式指定的时间点时就会执行自定义任务类的`run()`方法。

任务示例

```java
@Slf4j
@Service
public class DayEventJob implements Runnable {

    private DayEvent dayEvent;
    @Resource
    private DayEventDao dayEventDao;

    public void setDayEvent(DayEvent dayEvent) {
        this.dayEvent = dayEvent;
    }

    @Override
    public void run() {
        log.info("day event job run...");
        if (Objects.nonNull(dayEvent)) {
            dayEvent.setId(Id.next());
            dayEvent.setCreateTime(new Date());
            dayEvent.setEventDate(new Date());
            dayEventDao.insert(dayEvent);
        } else {
            log.info("day event object is null.");
        }
    }
}
```

DayEventJob实现了`Runnable`，作用是保存一条事项数据到数据库。







