package com.mao.ky.mapper.sys;

import com.mao.ky.entity.Query;
import com.mao.ky.entity.sys.user.UserDo;
import com.mao.ky.entity.sys.user.UserPass;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * sys_user
 * 用户数据处理
 * @author : create by zongx at 2020/11/10 10:44
 */
@Repository
@Mapper
public interface UserMapper {

    UserDo getUserByUsername(@Param("username") String username);

    List<UserDo> getUserByUsernameLike(@Param("username") String username);

    List<UserDo> getUsers(Query query);

    Integer getUsersTotal(Query query);

    Integer checkExistUsername(@Param("username") String username);

    void saveUser(UserDo userDo);

    Integer updateUser(UserDo userDo);

    Integer updateUserPass(UserPass userPass);

    Integer deleteUser(@Param("username") String username);

}
