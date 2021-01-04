package com.mao.ky.service.rcd;

import com.mao.ky.entity.rcd.dept.Dept;
import com.mao.ky.entity.response.ResponseData;

/**
 * @author : create by zongx at 2020/12/3 17:44
 */
public interface DeptService {

    void deptCache() throws Exception;

    ResponseData<?> deptSrc(String id);

    ResponseData<?> deptList();

    ResponseData<?> deptSave(Dept dept);

    ResponseData<?> deptEdit(Dept dept);

    ResponseData<?> deptDel(String id);

}
