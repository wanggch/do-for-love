package com.jasmine.doforlove.controller;

import cn.hutool.core.lang.Dict;
import com.jasmine.doforlove.controller.param.TaskAddParam;
import com.jasmine.doforlove.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
@RequestMapping("/task")
public class TaskController {

    @Resource
    private TaskService taskService;

    @PostMapping("/add")
    public Object add(@RequestBody TaskAddParam param) {
        return taskService.addTask(param);
    }

    @PostMapping("/remove/{jobId}")
    public Object remove(@PathVariable("jobId") String jobId) {
        taskService.removeTask(jobId);
        return Dict.create();
    }
}
