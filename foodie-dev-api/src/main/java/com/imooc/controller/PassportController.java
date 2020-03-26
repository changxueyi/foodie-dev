package com.imooc.controller;

import com.imooc.pojo.UserBo;
import com.imooc.service.UserService;
import com.imooc.utils.IMOOCJSONResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.jni.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName PassportController
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/3/25 18:49
 */
@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @GetMapping("/usernameIsExist")
    public IMOOCJSONResult usernameIsExist(@RequestParam String username) {
        //判断用户名不能为空
        if (StringUtils.isBlank(username)) {
            return IMOOCJSONResult.errorMsg("用户名不能为空");
        }
        //查找注册的用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        //3.请求成功，用户名没有重复
        return IMOOCJSONResult.ok();
    }

    //注册
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBo userBo) {
        //前端传来的是BO
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        String confirmpwd = userBo.getConfirmPassword();
        //总体的步骤  0 . 判断用户名和密码 必须不为空    1. 查询用户名是否存在
        //  2. 设计密码的长度 ，密码的长度不能少于 6 位  // 3 判断两次密码是否一致  // 4 实现注册
        // 0 . 判断用户名和密码 必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password) ||
                StringUtils.isBlank(confirmpwd)) {
            return IMOOCJSONResult.errorMsg("用户名或者密码不能为空");
        }
        // 1. 查询用户名是否存在
        boolean isExist = userService.queryUsernameIsExist(username);
        if (isExist) {
            return IMOOCJSONResult.errorMsg("用户名已存在");
        }
        // 2. 设计密码的长度 ，密码的长度不能少于 6 位
        if (password.length() < 6) {
            return IMOOCJSONResult.errorMsg("密码长度不能少于 6 位 ");
        }
        // 3 判断两次密码是否一致
        if (!password.equals(confirmpwd)) {
            return IMOOCJSONResult.errorMsg("两次密码输入的不一致");
        }
        // 4 实现注册
        userService.createUser(userBo);
        return IMOOCJSONResult.ok();
    }

}