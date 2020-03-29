package com.imooc.pojo.Bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @ClassName UserBo
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/3/26 10:18
 */
@ApiModel(value = "用户对象BO ， ",description = "从客户端传来的数据，封装在此Entity中")
public class UserBo {
    //required 此项是否必填？  true 必填    可以来判断
    @ApiModelProperty(value = "用户名",name = "username",example = "changxueyi",required =true)
    private String username;
    @ApiModelProperty(value = "密码",name = "password",example = "123456",required =true)
    private String password;
    @ApiModelProperty(value = "确认密码",name = "confirmPassword",example = "123456",required =false)
    private String confirmPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}