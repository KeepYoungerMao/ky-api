package com.mao.ky.service.sys.impl;

import com.mao.ky.entity.Query;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.entity.sys.role.*;
import com.mao.ky.mapper.sys.RolePermissionMapper;
import com.mao.ky.service.BaseService;
import com.mao.ky.service.sys.RoleService;
import com.mao.ky.util.SU;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色权限业务处理
 * @author : create by zongx at 2020/11/10 11:37
 */
@Service
public class RoleServiceImpl extends BaseService implements RoleService {

    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    public void setRolePermissionMapper(RolePermissionMapper rolePermissionMapper) {
        this.rolePermissionMapper = rolePermissionMapper;
    }

    @Override
    public ResponseData<?> roleSrc(String id) {
        Long tid = SU.parseId(id);
        if (!SU.isZS(tid))
            return paramErr("Param Error: invalid role id");
        RoleDo roleDo = rolePermissionMapper.getRoleById(tid);
        if (null == roleDo || null == roleDo.getId())
            return paramErr("Param Error: invalid role id");
        List<Long> ids = rolePermissionMapper.getPermissionIdByRoleId(roleDo.getId());
        return ok(roleDo.toVo(ids));
    }

    @Override
    public ResponseData<?> roleList(Query query) {
        if (SU.isEmpty(query.getType()))
            return paramErr("Param error: wrong type");
        switch (query.getType()) {
            case "keyword": return roleListByKeyword(query);
            case "page": return roleListByPage(query);
            default: return paramErr("Param error: wrong type");
        }
    }

    private ResponseData<?> roleListByKeyword(Query query) {
        if (SU.isEmpty(query.getKeyword()))
            return paramErr("Param error: keyword id empty");
        List<RoleDo> roles = rolePermissionMapper.getRolesByNameLike(query.getKeyword());
        return ok(toVos(roles));
    }

    private ResponseData<?> roleListByPage(Query query) {
        transQuery(query);
        List<RoleDo> roles = rolePermissionMapper.getRoles(query);
        if (roles.size() <= 0)
            return ok(0, 0, 0, roles);
        int total = rolePermissionMapper.getRolesTotal(query);
        return ok(query.getPage(), query.getSize(), total, toVos(roles));
    }

    private List<RoleVo> toVos(List<RoleDo> roles) {
        List<RoleVo> vos = new ArrayList<>();
        if (roles.size() <= 0)
            return vos;
        roles.forEach(roleDo -> vos.add(roleDo.toVo()));
        return vos;
    }

    @Transactional
    @Override
    public ResponseData<?> roleAdd(Role role) {
        RoleDo roleDo = role.toDo();
        int count = rolePermissionMapper.checkExistRole(roleDo.getName());
        if (count > 0)
            return paramErr("Param Error: role name used");
        Long tid = maoFlake.nextId();
        roleDo.setId(tid);
        rolePermissionMapper.saveRole(roleDo);
        dealRolePermission(tid, role);
        return ok("success to save");
    }

    @Transactional
    @Override
    public ResponseData<?> roleEdit(Role role) {
        Long tid = SU.parseId(role.getId());
        if (null == tid || tid <= 0)
            return paramErr("Param Error: invalid id");
        if (GLOBAL_ROLE.equals(role.getName()))
            return paramErr("Param Error: edit 'Administrator' is prohibited");
        RoleDo roleDo = role.toDo();
        roleDo.setId(tid);
        int count = rolePermissionMapper.updateRole(roleDo);
        if (count <= 0)
            return dataAccessErr("failed to update: invalid id");
        dealRolePermission(tid, role);
        return ok("success to update");
    }

    /**
     * role permissions ref
     */
    private void dealRolePermission(Long id, Role role) {
        if (null != role.getInc_permissions() && role.getInc_permissions().size() > 0) {
            List<Long> inc = new ArrayList<>();
            role.getInc_permissions().forEach(s -> {
                Long tid = SU.parseId(s);
                if (SU.isZS(tid))
                    inc.add(tid);
            });
            rolePermissionMapper.increasePermissions(id, inc);
        }
        if (null != role.getDec_permissions() && role.getDec_permissions().size() > 0) {
            List<Long> dec = new ArrayList<>();
            role.getDec_permissions().forEach(s -> {
                Long tid = SU.parseId(s);
                if (SU.isZS(tid))
                    dec.add(tid);
            });
            rolePermissionMapper.decreasePermissions(id, dec);
        }
    }

    @Transactional
    @Override
    public ResponseData<?> roleDel(String id) {
        Long tid = SU.parseId(id);
        if (!SU.isZS(tid))
            return paramErr("Param Error: invalid role id");
        RoleDo role = rolePermissionMapper.getRoleById(tid);
        if (null == role || null == role.getId())
            return paramErr("Param Error: invalid role id");
        if (GLOBAL_ROLE.equals(role.getName()))
            return dataAccessErr("Param Error: remove 'Administrator' is prohibited");
        int count = rolePermissionMapper.deleteRole(tid);
        if (count <= 0)
            return dataAccessErr("failed to update: invalid role id");
        //delete role-permission ref
        rolePermissionMapper.deleteRolePermissions(tid);
        return ok("success to delete");
    }

    @Override
    public ResponseData<?> permissionList() {
        List<PermissionDo> permissions = rolePermissionMapper.getPermissions();
        List<PermissionVo> result = new ArrayList<>();
        permissions.forEach(permission -> result.add(permission.toVo()));
        return ok(result);
    }

}
