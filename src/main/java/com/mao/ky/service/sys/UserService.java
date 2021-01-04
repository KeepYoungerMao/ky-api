package com.mao.ky.service.sys;

import com.mao.ky.entity.Query;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.entity.sys.user.User;
import com.mao.ky.entity.sys.user.UserPass;

/**
 * 用户业务处理
 * @author : create by zongx at 2020/11/10 11:28
 */
public interface UserService {

    ResponseData<?> clientList();

    ResponseData<?> userSrc(String username);

    ResponseData<?> userCheck(String username);

    ResponseData<?> userList(Query query);

    ResponseData<?> userAdd(User user);

    ResponseData<?> userEdit(User user);

    ResponseData<?> userPassEdit(UserPass userPass);

    ResponseData<?> userDel(String username);

}
