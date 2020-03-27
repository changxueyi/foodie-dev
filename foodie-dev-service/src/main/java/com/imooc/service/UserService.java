package com.imooc.service;

import com.imooc.pojo.UserBo;
import com.imooc.pojo.Users;

public interface UserService {
    //判断用户名是否存在
    public boolean queryUsernameIsExist(String username);

    //Bo是用来接收前端数据封装起来的，创建用户
    public Users createUser(UserBo userBo);

    //检索用户名和密码是否匹配 ，用于登录
    public Users queryUserForLogin(String username,String password);


}
