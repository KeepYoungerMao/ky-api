package com.mao.ky.config;

import com.mao.ky.config.cache.Cache;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.util.JsonUtil;
import com.mao.ky.util.SU;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : create by zongx at 2020/10/16 10:54
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class CORSFilter implements Filter {

    private static final String WAIT = "请等待系统启动完成再请求。";
    private static final String BAD_GATEWAY = "网关错误";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin","*");
        response.setHeader("Access-Control-Allow-Credentials","true");
        response.setHeader("Access-Control-Allow-Methods","*");
        response.setHeader("Access-Control-Allow-Headers","*");
        response.setHeader("Access-Control-Allow-Max-Age","3600");
        if ("OPTIONS".equals(request.getMethod().toUpperCase())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            //获取ip地址
            Long ip = SU.getIp(request);
            if (Cache.BLACK_IP.contains(ip)) {
                response(response, new ResponseData<>(502,"BAD_GATEWAY",BAD_GATEWAY));
            } else {
                //需等待初始化加载完成
                if (Cache.canRequest()) {
                    chain.doFilter(req, res);
                } else {
                    response(response, new ResponseData<>(405,"NOT_ALLOWED",WAIT));
                }
            }
        }
    }

    /**
     * 返回json数据
     */
    private void response(HttpServletResponse response, ResponseData<String> data) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        response.addHeader("Content-Type","application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        String result = JsonUtil.obj2json(data);
        response.getWriter().write(result == null ? "" : result);
        response.flushBuffer();
    }

}
