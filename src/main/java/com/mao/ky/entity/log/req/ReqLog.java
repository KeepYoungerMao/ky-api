package com.mao.ky.entity.log.req;

import com.mao.ky.entity.sys.Browser;
import com.mao.ky.util.OAuthUtil;
import com.mao.ky.util.SU;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author : create by zongx at 2020/12/14 16:28
 */
@Getter
@Setter
@NoArgsConstructor
public class ReqLog {
    private Long id;                    //主键，自增
    private Integer request_type;       //请求类型
    private Integer data_type;          //数据类型
    private String client;              //客户端
    private String user;                //用户登录名
    private Long ip;                    //用户ip，使用移位方法转换处long型数字
    private String uri;                 //请求地址
    private Integer method;             //请求方法
    private String params;              //表单参数
    private String body;                //请求body参数
    private Integer status;             //返回状态
    private String result;              //只记录错误时的信息,请求成功的数据不做记录
    private Long date;                  //请求时间

    public ReqLog(Log log, HttpServletRequest request, Integer status, String result) {
        this.id = 1L;
        this.request_type = log.value().getReqType().getType();
        this.data_type = log.value().getType();
        Browser browser = OAuthUtil.getBrowser();
        if (null != browser) {
            this.client = browser.getClient();
            this.user = browser.getUser();
        }
        this.ip = SU.getIp(request);
        this.uri = request.getRequestURI();
        this.method = SU.getRequestMethod(request.getMethod());
        this.params = SU.paramToLog(request.getParameterMap());
        ServletInputStream inputStream;
        try {
            inputStream = request.getInputStream();
        } catch (IOException e) {
            inputStream = null;
        }
        this.body = null != inputStream ? SU.getBodyData(inputStream) : null;
        this.status = status;
        this.result = result;
        this.date = System.currentTimeMillis();
    }

    public ReqLogVo toVo() {
        ReqLogVo vo = new ReqLogVo();
        vo.setId(String.valueOf(this.id));
        ReqType type = SU.requestType(this.request_type);
        vo.setType(null == type ? "" : type.getName());
        ReqData data = SU.requestData(this.data_type);
        vo.setData(null == data ? "" : data.getName());
        vo.setClient(this.client);
        vo.setUser(this.user);
        vo.setIp(SU.isZS(this.ip) ? SU.longToIp(this.ip) : "");
        vo.setUri(this.uri);
        vo.setMethod(SU.getRequestMethod(this.method));
        //参数后面再说
        vo.setParams(SU.parseLogParamData(this.params));
        vo.setBody(this.body);
        vo.setStatus(this.status);
        vo.setResult(this.result);
        vo.setDate(SU.timestampToStr(this.date));
        return vo;
    }

}
