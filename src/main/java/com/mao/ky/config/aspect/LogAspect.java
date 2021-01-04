package com.mao.ky.config.aspect;

import com.mao.ky.entity.log.req.Log;
import com.mao.ky.entity.log.req.ReqLog;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.service.log.ReqLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * @author : create by zongx at 2020/12/14 16:30
 */
@Aspect
@Component
public class LogAspect {

    private ReqLogService reqLogService;

    @Autowired
    public void setReqLogService(ReqLogService reqLogService) {
        this.reqLogService = reqLogService;
    }

    /**
     * 环绕通知
     * 以注解Log为切面
     */
    @SuppressWarnings("rawtypes")
    @Around(value = "@annotation(com.mao.ky.entity.log.req.Log)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        //获取切点的方法，方法上的Log注解
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();
        Log annotation = method.getAnnotation(Log.class);
        //获取当前HttpServletRequest
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (!(requestAttributes instanceof ServletRequestAttributes))
            return null;
        ServletRequestAttributes sra = (ServletRequestAttributes) requestAttributes;
        HttpServletRequest request = sra.getRequest();
        //获取返回值
        Object response = pjp.proceed();
        Integer status = null;
        String result = null;
        if (response instanceof ResponseData) {
            //泛型强转 SuppressWarnings
            ResponseData responseData = (ResponseData) response;
            status = responseData.getCode();
            //如非成功返回，则记录返回结果
            if (status != 200) {
                result = responseData.getData().toString();
            }
        }
        //构造日志数据
        ReqLog log = new ReqLog(annotation,request,status,result);
        //保存日志
        reqLogService.save(log);
        return response;
    }

}
