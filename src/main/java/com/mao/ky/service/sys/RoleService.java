package com.mao.ky.service.sys;

import com.mao.ky.entity.Query;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.entity.sys.role.Role;

/**
 * 角色权限业务处理
 * @author : create by zongx at 2020/11/10 11:36
 */
public interface RoleService {

    ResponseData<?> roleSrc(String id);

    ResponseData<?> roleList(Query query);

    ResponseData<?> roleAdd(Role role);

    ResponseData<?> roleEdit(Role role);

    ResponseData<?> roleDel(String id);

    ResponseData<?> permissionList();

}
