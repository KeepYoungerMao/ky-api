package com.mao.ky.service.sys.impl;

import com.mao.ky.entity.IdName;
import com.mao.ky.entity.Query;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.entity.sys.ClientDo;
import com.mao.ky.entity.sys.user.User;
import com.mao.ky.entity.sys.user.UserDo;
import com.mao.ky.entity.sys.user.UserPass;
import com.mao.ky.entity.sys.user.UserVo;
import com.mao.ky.mapper.sys.ClientMapper;
import com.mao.ky.mapper.sys.UserMapper;
import com.mao.ky.service.BaseService;
import com.mao.ky.service.sys.UserService;
import com.mao.ky.util.SU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户业务处理
 * @author : create by zongx at 2020/11/10 11:29
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

    private ClientMapper clientMapper;
    private UserMapper userMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setClientMapper(ClientMapper clientMapper) {
        this.clientMapper = clientMapper;
    }
    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ResponseData<?> clientList() {
        List<ClientDo> clients = clientMapper.getClients();
        List<IdName> result = new ArrayList<>();
        clients.forEach(c -> result.add(new IdName(c.getClientId(), c.getName())));
        return ok(result);
    }

    @Override
    public ResponseData<?> userSrc(String username) {
        UserDo user = userMapper.getUserByUsername(username);
        if (null == user || null == user.getId())
            return paramErr("Param Error：wrong username");
        return ok(user.toVo());
    }

    @Override
    public ResponseData<?> userCheck(String username) {
        Integer count = userMapper.checkExistUsername(username);
        return ok(null != count && count > 0);
    }

    @Override
    public ResponseData<?> userList(Query query) {
        if (SU.isEmpty(query.getType()))
            return paramErr("Param error: wrong type");
        switch (query.getType()) {
            case "keyword": return userListByKeyword(query);
            case "page": return userListByPage(query);
            default: return paramErr("Param error: wrong type");
        }
    }

    private ResponseData<?> userListByKeyword(Query query) {
        if (SU.isEmpty(query.getKeyword()))
            return paramErr("Param error: keyword id empty");
        List<UserDo> list = userMapper.getUserByUsernameLike(query.getKeyword());
        return ok(toVos(list));
    }

    private ResponseData<?> userListByPage(Query query) {
        transQuery(query);
        List<UserDo> list = userMapper.getUsers(query);
        if (list.size() <= 0)
            return ok(0, 0, 0, list);
        Integer total = userMapper.getUsersTotal(query);
        return ok(query.getPage(), query.getSize(), total, toVos(list));
    }

    private List<UserVo> toVos(List<UserDo> list) {
        List<UserVo> result = new ArrayList<>();
        if (list.size() <= 0)
            return result;
        list.forEach(userDo -> result.add(userDo.toVo()));
        return result;
    }

    @Transactional
    @Override
    public ResponseData<?> userAdd(User user) {
        UserDo userDo = user.toDo();
        Integer count = userMapper.checkExistUsername(userDo.getUsername());
        if (null != count && count > 0)
            return paramErr("Param Error: username used");
        userDo.setId(maoFlake.nextId());
        userDo.setPassword(passwordEncoder.encode(userDo.getPassword()));
        userMapper.saveUser(userDo);
        return ok("success to save");
    }

    @Transactional
    @Override
    public ResponseData<?> userEdit(User user) {
        Long tid = SU.parseId(user.getId());
        if (null == tid || tid <= 0)
            return paramErr("Param Error: invalid id");
        if (GLOBAL_USER.equals(user.getUsername()))
            return paramErr("Param Error: edit 'admin' is prohibited");
        UserDo userDo = user.toDo();
        userDo.setId(tid);
        Integer count = userMapper.updateUser(userDo);
        if (null == count || count <= 0)
            return dataAccessErr("failed to update: invalid id");
        return ok("success to update");
    }

    @Transactional
    @Override
    public ResponseData<?> userPassEdit(UserPass userPass) {
        UserDo user = userMapper.getUserByUsername(userPass.getUsername());
        if (null == user || null == user.getId())
            return paramErr("Param Error: invalid username");
        String encodeOldPass = passwordEncoder.encode(userPass.getOld_pass());
        if (!encodeOldPass.equals(user.getPassword()))
            return paramErr("Param Error: password is not true");
        userPass.setNew_pass(passwordEncoder.encode(userPass.getNew_pass()));
        Integer count = userMapper.updateUserPass(userPass);
        if (null == count || count <= 0)
            return dataAccessErr("Failed to update: no user");
        return ok("success to update");
    }

    @Transactional
    @Override
    public ResponseData<?> userDel(String username) {
        if (GLOBAL_USER.equals(username))
            return paramErr("Param Error: edit 'admin' is prohibited");
        Integer count = userMapper.deleteUser(username);
        if (null == count || count <= 0)
            return dataAccessErr("failed to update: invalid username");
        return ok("success to delete");
    }

}
