package com.mao.ky.mapper.sys;

import com.mao.ky.entity.Query;
import com.mao.ky.entity.sys.role.PermissionDo;
import com.mao.ky.entity.sys.role.RoleDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : create by zongx at 2020/11/10 10:46
 */
@Repository
@Mapper
public interface RolePermissionMapper {

    List<String> getPermissionByRoleId(@Param("id") long id);

    List<Long> getPermissionIdByRoleId(@Param("id") long id);

    RoleDo getRoleById(@Param("id") long id);

    List<RoleDo> getRolesByNameLike(@Param("keyword") String keyword);

    List<RoleDo> getRoles(Query query);

    int getRolesTotal(Query query);

    int checkExistRole(@Param("name") String name);

    void saveRole(RoleDo roleDo);

    int updateRole(RoleDo roleDo);

    void increasePermissions(@Param("role") Long role, @Param("list") List<Long> list);

    void decreasePermissions(@Param("role") Long role, @Param("list") List<Long> list);

    int deleteRole(@Param("id") long id);

    void deleteRolePermissions(@Param("id") long id);

    List<PermissionDo> getPermissions();

}
