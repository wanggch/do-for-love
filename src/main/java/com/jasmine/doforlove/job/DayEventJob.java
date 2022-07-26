package com.jasmine.doforlove.job;

import com.jasmine.doforlove.dao.DayEventDao;
import com.jasmine.doforlove.entity.DayEvent;
import com.jasmine.doforlove.util.Id;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @description 保存DayEvent实体
 * @author: wanggc
 * @date:  2022/7/26 11:21
 */
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
