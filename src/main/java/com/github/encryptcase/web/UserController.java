package com.github.encryptcase.web;

import com.github.encryptcase.domain.User;
import com.github.encryptcase.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(tags = "用户模块")
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "增加用户")
    @PostMapping(value = "/addUser")
    public String addUser(User user) {
        try {
            userService.addUser(user);
            return "添加用户成功";
        } catch (Exception e) {
            return "添加用户失败:" + e.getMessage();
        }
    }

    @ApiOperation(value = "删除用户")
    @PostMapping(value = "/delUser")
    public String delUser(int id) {
        try {
            userService.delUser(id);
            return "删除用户成功";
        } catch (Exception e) {
            return "删除用户失败:" + e.getMessage();
        }
    }

    @ApiOperation(value = "更新用户")
    @PostMapping(value = "/updateUser")
    public String updateUser(User user) {
        try {
            userService.updateUser(user);
            return "更新用户成功";
        } catch (Exception e) {
            return "更新用户失败:" + e.getMessage();
        }
    }


    @ApiOperation(value = "查询用户列表")
    @GetMapping(value = "/findAll")
    public List<User> findAll() {
        return userService.findAll();
    }

    @ApiOperation(value = "查询用户")
    @GetMapping(value = "/findById")
    public User findById(Integer id) {
        return userService.findById(id);
    }
}
