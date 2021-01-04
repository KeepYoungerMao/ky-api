package com.mao.ky.web;

import com.mao.ky.entity.Query;
import com.mao.ky.entity.log.req.Log;
import com.mao.ky.entity.log.req.ReqData;
import com.mao.ky.entity.response.ResponseData;
import com.mao.ky.entity.sys.role.Role;
import com.mao.ky.entity.sys.user.User;
import com.mao.ky.entity.sys.user.UserPass;
import com.mao.ky.service.sys.RoleService;
import com.mao.ky.service.sys.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 * 系统类请求
 * 用户、角色权限、公司部门
 * @author : create by zongx at 2020/11/10 11:26
 */
@RestController
@RequestMapping("api/sys")
public class SystemWeb {

    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("clients")
    public ResponseData<?> clientList() {
        return userService.clientList();
    }

    @GetMapping("user")
    public ResponseData<?> currentUser(Principal principal) {
        return userService.userSrc(principal.getName());
    }

    @Log(ReqData.USER)
    @GetMapping("user/{username}")
    public ResponseData<?> userSrc(@PathVariable("username") String username) {
        return userService.userSrc(username);
    }

    @GetMapping("user/{username}/check")
    public ResponseData<?> userCheck(@PathVariable("username") String username) {
        return userService.userCheck(username);
    }

    @Log(ReqData.USER)
    @GetMapping("users")
    public ResponseData<?> userList(Query query) {
        return userService.userList(query);
    }

    @Log(ReqData.USER)
    @PutMapping("user")
    public ResponseData<?> userAdd(@Validated @RequestBody User user) {
        return userService.userAdd(user);
    }

    @Log(ReqData.USER)
    @PostMapping("user")
    public ResponseData<?> userEdit(@Validated @RequestBody User user) {
        return userService.userEdit(user);
    }

    @Log(ReqData.USER)
    @PostMapping("user/password")
    public ResponseData<?> userPassEdit(@Validated @RequestBody UserPass userPass) {
        return userService.userPassEdit(userPass);
    }

    @Log(ReqData.USER)
    @DeleteMapping("user/{username}")
    public ResponseData<?> userDel(@PathVariable("username") String username) {
        return userService.userDel(username);
    }

    @Log(ReqData.ROLE)
    @GetMapping("role/{id}")
    public ResponseData<?> roleSrc(@PathVariable("id") String id) {
        return roleService.roleSrc(id);
    }

    @Log(ReqData.ROLE)
    @GetMapping("roles")
    public ResponseData<?> roleList(Query query) {
        return roleService.roleList(query);
    }

    @Log(ReqData.ROLE)
    @PutMapping("role")
    public ResponseData<?> roleAdd(@Validated @RequestBody Role role) {
        return roleService.roleAdd(role);
    }

    @Log(ReqData.ROLE)
    @PostMapping("role")
    public ResponseData<?> roleEdit(@Validated @RequestBody Role role) {
        return roleService.roleEdit(role);
    }

    @Log(ReqData.ROLE)
    @DeleteMapping("role/{id}")
    public ResponseData<?> roleDel(@PathVariable("id") String id) {
        return roleService.roleDel(id);
    }

    @Log(ReqData.PERMISSION)
    @GetMapping("permissions")
    public ResponseData<?> permissionList() {
        return roleService.permissionList();
    }

}
