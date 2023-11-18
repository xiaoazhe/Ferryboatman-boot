package com.ferry.web.aspect;

import com.alibaba.fastjson.JSONObject;
import com.ferry.core.http.Result;
import com.ferry.server.admin.entity.SysLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: 摆渡人
 * @Date: 2022/9/24
 */
@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("execution(public * com.ferry..*.*.*(..))")
    private void commonPointcut() {
    }

    /**
     * 全局的异常打印切面
     *
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("commonPointcut()")
    public Object cutAllAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = null;

        try {
            result = joinPoint.proceed();
        } catch (Exception ex) {
            result = handleException(ex);
            logger.error("服务调用全局异常", ex);
        }
        return result;
    }


    public <T> Result handleException(Exception ex){
        if (ex instanceof RuntimeException){
            return Result.error();
        }
        return Result.error();
    }
}
