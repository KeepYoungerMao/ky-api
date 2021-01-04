package com.mao.ky.web;

import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.entity.response.ResponseEnum;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * 统一异常处理类
 *
 * 注：
 * 统一异常处理类中的统一结构体返回方法（ auth, no, refuse, err ）与BaseService中的返回方法一样，
 * 但这里需要单独调用，不能继承 BaseService。
 * 由于BaseService最后会执行到的 ope 方法中执行了鉴权方法，鉴权失败会抛出异常，
 * 如果鉴权失败，异常会被本类拦截，返回错误信息时会再次调用 ope 方法，造成死循环。
 * @author : create by zongx at 2020/10/16 14:34
 */
@RestControllerAdvice
public class ExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(ExceptionAdvice.class);

    private static final String FAVICON = "/favicon.ico";
    private static final String SERVER_ERROR = "Request Error，The Server is wrong";
    private static final String SQL_ERROR = "Data processing error";
    private static final String FAVICON_ERROR = "Requests for Favicon type are not supported";
    private static final String PERMISSION_ERROR = "No permission to request the current data。";
    private static final String VALIDATE_ERROR_PRE = "Valid Failed:";
    private static final String VALIDATE_ERROR = "The parameter check error";

    /**
     * 统一异常拦截
     * 拦截 Exception，在根据异常类型进行不同处理
     * 1. NoHandlerFoundException
     *      404错误，url路径错误，请求不到资源
     * 2. SQLException
     * 3. PersistenceException
     * 4. DataAccessException
     *      为数据库、SQL初始化、SQL执行等发生的错误
     * 5. OAuth2Exception
     * 6. AccessDeniedException
     *      OAuth2验证框架抛出的异常，自定义返回错误信息。不使用OAuth2的返回信息
     * 7. SystemErrorException
     *      自定义异常，系统启动验证错误抛出
     * 8. MethodArgumentNotValidException
     * 9. BindException
     *      参数验证框架抛出的异常，前者为body参数，后者为表单参数
     */
    @ExceptionHandler(Exception.class)
    public ResponseData<String> globalHandler(Exception e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        //e.printStackTrace();
        log.error("error", e);
        if (e instanceof NoHandlerFoundException) {
            //404错误，favicon图标不支持，错误路径不支持
            if (FAVICON.equals(uri))
                return no(FAVICON_ERROR);
            else
                return no("The incorrect path:" + uri);
        } else if (e instanceof MethodArgumentNotValidException) {
            //Spring validation错误，字段校验错误处理
            return validateErr(((MethodArgumentNotValidException) e).getBindingResult());
        } else if (e instanceof BindException) {
            //Spring validation错误，字段校验错误处理
            return validateErr(((BindException) e).getBindingResult());
        } else if (e instanceof SQLException || e instanceof PersistenceException || e instanceof DataAccessException) {
            //数据库操作错误
            return dataAccessErr();
        } else if (e instanceof OAuth2Exception || e instanceof AccessDeniedException) {
            //OAuth2认证、授权错误
            return auth(null == e.getMessage() ? PERMISSION_ERROR : e.getMessage());
        } else {
            //其他错误
            return err(null == e.getMessage() ? SERVER_ERROR : e.getMessage());
        }
    }

    /**
     * 参数校验错误的错误信息返回
     * 参数校验配置为快速校验模式：只要遇到错误就返回
     * 此处则获取错误第一条
     * 没有则返回默认错误信息
     */
    private ResponseData<String> validateErr(BindingResult result) {
        if (result.hasErrors()) {
            ObjectError firstError = result.getAllErrors().get(0);
            String objName = firstError.getObjectName();
            String filedName = "param";
            if (firstError instanceof FieldError) {
                FieldError fieldError = (FieldError) firstError;
                filedName = fieldError.getField();
            }
            String msg = VALIDATE_ERROR;
            if (null != firstError.getDefaultMessage()) {
                msg = firstError.getDefaultMessage();
            }
            return paramErr(VALIDATE_ERROR_PRE + "["+ objName +"]-[" + filedName + "]:" + msg);
        } else {
            return paramErr(VALIDATE_ERROR);
        }
    }

    private ResponseData<String> auth(String msg) {
        return ope(ResponseEnum.AUTH,msg);
    }

    private ResponseData<String> no(String msg) {
        return ope(ResponseEnum.NO, msg);
    }

    private ResponseData<String> refuse(String msg) {
        return ope(ResponseEnum.REFUSE,msg);
    }

    private ResponseData<String> err(String msg) {
        return ope(ResponseEnum.ERR,msg);
    }

    private ResponseData<String> paramErr(String msg) {
        return ope(ResponseEnum.PARAM_ERR, msg);
    }

    private ResponseData<String> dataAccessErr() {
        return ope(ResponseEnum.DATA_ACCESS_ERR, SQL_ERROR);
    }

    private ResponseData<String> ope(ResponseEnum type, String msg) {
        return new ResponseData<>(type.getCode(),type.getMsg(),msg);
    }

}
