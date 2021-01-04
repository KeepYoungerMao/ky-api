package com.mao.ky.service;

import com.mao.ky.entity.Query;
import com.mao.ky.entity.response.PageData;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.entity.response.ResponseEnum;
import com.mao.ky.util.MaoFlake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 基础服务
 * 所有服务service继承此
 * @author : create by zongx at 2020/10/15 17:41
 */
@Service
public class BaseService {

    //超级管理员角色，该角色保证为所有权限，且该角色不允许修改和删除
    public static final String GLOBAL_ROLE = "administrator";
    public static final String GLOBAL_USER = "admin";

    public MaoFlake maoFlake;

    @Autowired
    public void setMaoFlake(MaoFlake maoFlake) {
        this.maoFlake = maoFlake;
    }

    /**
     * 数据成功返回
     */
    protected <T> ResponseData<T> ok(T data) {
        return ope(ResponseEnum.OK,data);
    }

    /**
     * 分页数据成功返回
     */
    protected <T> ResponseData<PageData<T>> ok(int page, int size, int total, List<T> data) {
        return ope(ResponseEnum.OK, new PageData<>(page, size, (long)total, data));
    }
    protected <T> ResponseData<PageData<T>> ok(int page, int size, long total, List<T> data) {
        return ope(ResponseEnum.OK, new PageData<>(page, size, total, data));
    }

    protected ResponseData<String> refuse(String msg) {
        return ope(ResponseEnum.REFUSE,msg);
    }

    //参数错误
    protected ResponseData<String> paramErr(String msg) {
        return ope(ResponseEnum.PARAM_ERR, msg);
    }
    //数据库数据错误
    protected ResponseData<String> databaseErr(String msg) {
        return ope(ResponseEnum.DATABASE_ERR, msg);
    }
    //数据保存、更新、删除时错误
    protected ResponseData<String> dataAccessErr(String msg) {
        return ope(ResponseEnum.DATA_ACCESS_ERR, msg);
    }

    private <T> ResponseData<T> ope(ResponseEnum type, T data) {
        return new ResponseData<>(type.getCode(),type.getMsg(),data);
    }

    //======================= 工具类 ==================================

    public void transQuery(Query query) {
        Integer page = query.getPage();
        if (null == page || page < 1)
            page = 1;
        query.setPage(page);
        Integer size = query.getSize();
        if (null == size || size <= 1 || size > 50)
            size = 20;
        query.setSize(size);
        query.setStart(page == 1 ? 0 : (page - 1) * size);
    }

}
