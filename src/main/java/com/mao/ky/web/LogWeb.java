package com.mao.ky.web;

import com.mao.ky.entity.log.req.LogParam;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.service.log.ReqLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : create by zongx at 2020/12/14 16:48
 */
@RestController
@RequestMapping("api/log")
public class LogWeb {

    private ReqLogService reqLogService;

    @Autowired
    public void setReqLogService(ReqLogService reqLogService) {
        this.reqLogService = reqLogService;
    }

    /**
     * 获取请求日志分类树数据
     * @return 日志树
     */
    @GetMapping("tree/request")
    public ResponseData<?> logTree() {
        return reqLogService.logTree();
    }

    /**
     * 请求日志数据列表
     * @param param 请求参数
     * @return 日志列表
     */
    @GetMapping("requests")
    public ResponseData<?> logList(LogParam param) {
        return reqLogService.logList(param);
    }

    /**
     * 查询日志详情
     * @param id 日志id
     * @return 日志详情
     */
    @GetMapping("request/{id}")
    public ResponseData<?> logSrc(@PathVariable("id") String id) {
        return reqLogService.logSrc(id);
    }

}
