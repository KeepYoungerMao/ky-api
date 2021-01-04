package com.mao.ky.service.auth;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AccessDeniedHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 鉴权失败处理器
 * @author : create by zongx at 2020/10/15 17:28
 */
@Service
public class DefaultAccessDeniedHandler extends OAuth2AccessDeniedHandler
        implements AuthenticationEntryPoint, AuthenticationFailureHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        doHandler(response,e);
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException e) throws IOException {
        doHandler(response,e);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException {
        doHandler(response,e);
    }

    /**
     * 鉴权失败统一返回
     */
    private void doHandler(HttpServletResponse response, RuntimeException e) throws IOException {
        String remark = "no permission";
        if (null != e.getMessage())
            remark = e.getMessage();
        response.setStatus(200);
        response.setContentType("application/json; charset=utf-8");
        String result = "{\"code\":401,\"msg\":\"NO_PERMISSION\",\"data\":\""+ remark +"\"}";
        response.getWriter().write(result);
        response.flushBuffer();
    }

}
