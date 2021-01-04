package com.mao.ky.service.log;

import com.mao.ky.entity.log.req.LogParam;
import com.mao.ky.entity.log.req.ReqLog;
import com.mao.ky.entity.response.ResponseData;

/**
 * @author : create by zongx at 2020/12/14 16:49
 */
public interface ReqLogService {

    void save(ReqLog log);

    ResponseData<?> logTree();

    ResponseData<?> logList(LogParam param);

    ResponseData<?> logSrc(String id);

}
