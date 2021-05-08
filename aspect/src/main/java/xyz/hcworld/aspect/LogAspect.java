package xyz.hcworld.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import xyz.hcworld.common.annotation.Log;
import xyz.hcworld.common.lang.Result;
import xyz.hcworld.gotool.date.DateTimeUtils;
import xyz.hcworld.util.IpUtil;


import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志AOP处理
 *
 * @ClassName: LogAspect
 * @Author: 张红尘
 * @Date: 2021-05-06
 * @Version： 1.0
 */
@Slf4j
@Aspect
@Component
@Order(1)
public class LogAspect {

    /**
     * 拦截xyz.hcworld.common.annotation.Log注解的方法
     */
    @Pointcut("@annotation(xyz.hcworld.common.annotation.Log)")
    public void log() {
        System.out.println("经过AOP的log方法");
    }

    /**
     * 前置增强
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        System.out.println("经过AOP的前置增强方法");
        //获取客户端的请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();
        Map<String, String> map = new HashMap<>(10);
        //获取时分秒存进time中
        map.put("time", DateTimeUtils.getCurrentDateTimeStr());
        // 获得注解
        Log controllerLog = getAnnotationLog(joinPoint);
        if (controllerLog != null) {
            map.put("apiTitle",controllerLog.title());
        }

        //Url可能有中文所以进行URLDecoder解码
        try {
            map.put("url", URLDecoder.decode(request.getRequestURI(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            //异常时打印日志且不转码
            log.error("Error:{}", e.getMessage());
            map.put("url", request.getRequestURI());
        }
        //获取用户ip
        map.put("ip", IpUtil.getIp(request));
        //获取前端传来的变量
        if (joinPoint.getArgs().length > 0) {
            StringBuilder sb = new StringBuilder(32).append("[");
            for (Object str : joinPoint.getArgs()) {
                sb.append(str).append(",");
            }
            sb.deleteCharAt(sb.length() - 1).append("]");
            map.put("value", sb.toString());
        }
        //包路径
        map.put("classMethod", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("LogAspect前置增强:{}", map.toString());
    }

    /**
     * 后置增强
     */
    @After("log()")
    public void doAfter() {
        log.info("LogAspect后置增强");
    }

    /**
     * 拦截最后返回
     *
     * @param result 返回结果
     */
    @AfterReturning(returning = "result", pointcut = "log()")
    public void doAfterReturn(Result result) {
        log.info("LogAspect拦截返回:{}", result.toString());
    }

    /**
     *如果注解存在就获取注解属性
     * @param joinPoint
     * @return
     */
    private Log getAnnotationLog(JoinPoint joinPoint)
    {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        if (method != null)
        {
            return method.getAnnotation(Log.class);
        }
        return null;
    }



}
