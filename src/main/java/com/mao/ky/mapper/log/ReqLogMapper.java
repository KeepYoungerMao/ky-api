package com.mao.ky.mapper.log;

import com.mao.ky.entity.log.req.LogParam;
import com.mao.ky.entity.log.req.ReqLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : create by zongx at 2020/12/14 16:53
 */
@Repository
@Mapper
public interface ReqLogMapper {

    void saveRequestLog(ReqLog reqLog);

    List<ReqLog> getRequestLogs(LogParam logParam);

    Long getRequestLogsCount(LogParam logParam);

    ReqLog getRequestLogById(@Param("id") Long id);

}
