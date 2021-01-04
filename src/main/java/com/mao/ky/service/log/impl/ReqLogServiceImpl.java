package com.mao.ky.service.log.impl;

import com.mao.ky.config.cache.Cache;
import com.mao.ky.entity.log.req.*;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.mapper.log.ReqLogMapper;
import com.mao.ky.service.BaseService;
import com.mao.ky.service.log.ReqLogService;
import com.mao.ky.util.SU;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author : create by zongx at 2020/12/14 16:49
 */
@Service
public class ReqLogServiceImpl extends BaseService implements ReqLogService {

    public static final Logger LOG = LoggerFactory.getLogger(ReqLogServiceImpl.class);

    private ReqLogMapper reqLogMapper;

    @Autowired
    public void setReqLogMapper(ReqLogMapper reqLogMapper) {
        this.reqLogMapper = reqLogMapper;
    }

    @Override
    public void save(ReqLog log) {
        //异步执行保存方法
        CompletableFuture.supplyAsync(() -> {
            //保存请求日志
            reqLogMapper.saveRequestLog(log);
            return null != log.getId() && log.getId() > 0 ? 1 : 0;
        }, Cache.SERVICE).thenAccept(status -> {
            if (status <= 0) {
                LOG.warn("保存请求日志出错...");
            }
        });
    }

    @Override
    public ResponseData<?> logTree() {
        return ok(makeLogTree());
    }

    public List<LogTree> makeLogTree() {
        List<LogTree> logTrees = new ArrayList<>();
        //遍历父类
        for (ReqType rt : ReqType.values()) {
            logTrees.add(new LogTree(rt.getType(), rt.getName(), new ArrayList<>()));
        }
        //遍历子类
        for (ReqData rdt : ReqData.values()) {
            for (LogTree tree : logTrees) {
                if (rdt.getReqType().getType() == tree.getId()) {
                    tree.getChild().add(new LogTree(rdt.getType(), rdt.getName(), null));
                    break;
                }
            }
        }
        return logTrees;
    }

    @Override
    public ResponseData<?> logList(LogParam param) {
        //设置参数
        ReqType type = SU.requestType(param.getType());
        param.setType(null == type ? null : type.getType());
        ReqData data = SU.requestData(param.getData());
        param.setData(null == data ? null : data.getType());
        param.setPage(null == param.getPage() || param.getPage() < 1 ? 1 : param.getPage());
        param.setSize(null == param.getSize() || param.getSize() <= 1 || param.getSize() > 50 ? 20 : param.getSize());
        param.setPre(param.getPage() == 1 ? 0 : (param.getPage() - 1) * param.getSize());
        //查询数据
        List<ReqLog> logs = reqLogMapper.getRequestLogs(param);
        if (logs.size() <= 0)
            return ok(0, 0, 0L, new ArrayList<>());
        Long count = reqLogMapper.getRequestLogsCount(param);
        //数据转换
        List<ReqLogVo> result = new ArrayList<>();
        logs.forEach(requestLog -> result.add(requestLog.toVo()));
        //返回分页数据
        return ok(param.getPage(), param.getSize(), count, result);
    }

    @Override
    public ResponseData<?> logSrc(String id) {
        Long tid = SU.parseId(id);
        if (!SU.isZS(tid))
            return paramErr("参数错误：日志id错误");
        ReqLog log = reqLogMapper.getRequestLogById(tid);
        if (null == log || null == log.getId())
            return paramErr("参数错误：日志id错误，匹配不到数据");
        return ok(log.toVo());
    }

}
