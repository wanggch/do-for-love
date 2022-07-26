package com.jasmine.doforlove.aspect;

import cn.hutool.core.util.StrUtil;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@Slf4j
@Aspect
@Component
public class WebLogAspect {

    // 切入点
    @Pointcut("execution(public * com.jasmine.*.controller..*(..)))")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 开始打印请求日志
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 构建成一条长 日志，避免并发下日志错乱
        StringBuilder beforeReqLog = new StringBuilder(300);
        // 日志参数
        List<Object> beforeReqArgs = new ArrayList<>();
        beforeReqLog.append("\n================ Start  ================\n");

        // 打印路由
        beforeReqLog.append("===> {}: {}\n");
        String requestMethod = request.getMethod();
        beforeReqArgs.add(requestMethod);
        beforeReqArgs.add(request.getRequestURL().toString());

        // IP
        beforeReqLog.append("===> IP: {}\n");
        beforeReqArgs.add(request.getRemoteAddr());

        // 打印调用 controller 的全路径以及执行方法
        beforeReqLog.append("===> Class Method: {}.{}\n");
        beforeReqArgs.add(joinPoint.getSignature().getDeclaringTypeName());
        beforeReqArgs.add(joinPoint.getSignature().getName());

        // 打印请求头
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            beforeReqLog.append("=== Headers ===  {}: {}\n");
            String headerName = headerNames.nextElement();
            beforeReqArgs.add(headerName);
            beforeReqArgs.add(StrUtil.join("", request.getHeader(headerName)));
        }

        // 打印请求入参
        beforeReqLog.append("===> request params: {}\n");
        beforeReqArgs.add(new Gson().toJson(joinPoint.getArgs()));

//        List<Object> args = Arrays.asList(joinPoint.getArgs());
//        log.info("Request Args   : {}", new Gson().toJson(args));
        Object result = joinPoint.proceed();
//        // 打印出参
//        log.info("Response Args  : {}", new Gson().toJson(result));
//        // 执行耗时
        beforeReqLog.append(StrUtil.format("Time-Consuming : {} ms \n", System.currentTimeMillis() - startTime));


        beforeReqLog.append("================ End  =================\n");

        // 打印
        log.info(beforeReqLog.toString(), beforeReqArgs.toArray());
        return result;
    }
}
