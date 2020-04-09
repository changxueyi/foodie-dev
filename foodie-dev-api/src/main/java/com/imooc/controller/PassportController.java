package com.imooc.controller;

import com.imooc.pojo.Bo.UserBo;
import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.JsonUtils;
import com.imooc.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName PassportController
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/3/25 18:49
 */
@Api(value = "注册登录", tags = {"用于注册登录的相关接口"})
@RestController
@RequestMapping("passport")
public class PassportController {
    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户名是否存在", notes = "用户名是否存在", httpMethod = "GET")
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

    @ApiOperation(value = "用户注册", notes = "用户注册", httpMethod = "POST")
    //注册
    @PostMapping("/regist")
    public IMOOCJSONResult regist(@RequestBody UserBo userBo,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
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
        Users userResult = userService.createUser(userBo);
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user",
                JsonUtils.objectToJson(userResult), true);//true 设置为true 开启cookie加密
        // TODO 生成 用户token，存入redis会话
        // TODO 同步购物车数据
        return IMOOCJSONResult.ok();
    }


    @ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
    //用户登录
    @PostMapping("/login")
    public IMOOCJSONResult login(@RequestBody UserBo userBo,
                                 HttpServletRequest request,
                                 HttpServletResponse response
    ) throws Exception {
        //前端传来的是BO
        String username = userBo.getUsername();
        String password = userBo.getPassword();
        //总体的步骤  0 . 判断用户名和密码 必须不为空    1. 查询用户名是否存在
        //  2. 设计密码的长度 ，密码的长度不能少于 6 位  // 3 判断两次密码是否一致  // 4 实现注册
        // 0 . 判断用户名和密码 必须不为空
        if (StringUtils.isBlank(username) ||
                StringUtils.isBlank(password)) {
            return IMOOCJSONResult.errorMsg("用户名或者密码不能为空");
        }

        // 1 实现登录
        Users userResult = userService.queryUserForLogin(username,
                MD5Utils.getMD5Str(password));
        if (userResult == null) {
            return IMOOCJSONResult.errorMsg("用户名或密码不正确");
        }
        userResult = setNullProperty(userResult);
        CookieUtils.setCookie(request, response, "user",
               JsonUtils.objectToJson(userResult), true);//true 设置为true 开启cookie加密

        return IMOOCJSONResult.ok(userResult);
    }

    //封装的方法，登录后，一些基本的信息不爆露出来，设置默认为null
    private Users setNullProperty(Users userResult) {
        userResult.setPassword(null);
        userResult.setMobile(null);
        userResult.setEmail(null);
        userResult.setCreatedTime(null);
        userResult.setUpdatedTime(null);
        userResult.setBirthday(null);
        return userResult;
    }

    //登录退出
    @ApiOperation(value = "用户退出登录", notes = "用户退出登录", httpMethod = "POST")
    @PostMapping("/logout")
    public IMOOCJSONResult logout(@RequestParam String userId,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
        //清除用户的相关信息cookie'
        CookieUtils.deleteCookie(request,response,"user");
        return IMOOCJSONResult.ok();
    }

}