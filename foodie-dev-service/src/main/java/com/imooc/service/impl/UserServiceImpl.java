package com.imooc.service.impl;

import com.imooc.enums.Sex;
import com.imooc.mapper.UsersMapper;
import com.imooc.pojo.UserBo;
import com.imooc.pojo.Users;
import com.imooc.service.UserService;
import com.imooc.utils.DateUtil;
import com.imooc.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

/**
 * @ClassName UserServiceImpl
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/3/25 18:17
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    public UsersMapper usersMapper;
    private static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";
    @Autowired
    private Sid sid;


    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Example example = new Example(Users.class);
        Example.Criteria userCriteria = example.createCriteria();

        userCriteria.andEqualTo("username", username);
        Users result = usersMapper.selectOneByExample(example);
        return result==null ? false : true;
    }

    //注册用户
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users createUser(UserBo userBo) {
        String userId =  sid.nextShort();

        Users user = new Users();
        user.setId(userId);
        user.setUsername(userBo.getUsername());
        try {
            user.setPassword(MD5Utils.getMD5Str(userBo.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //昵称同用户名
        user.setNickname(userBo.getUsername());
        //默认头像
        user.setFace(USER_FACE);
        //默认生日
        user.setBirthday(DateUtil.stringToDate("2000-01-01"));
        //设置性别,使用枚举
        user.setSex(Sex.secret.type);
        user.setCreatedTime(new Date());
        user.setUpdatedTime(new Date());

        usersMapper.insert(user);
        return user;
    }

    //用户登录
    @Override
    public Users queryUserForLogin(String username, String password) {
        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        //criteria.andXxxEqualTo(value)    添加xxx字段等于value条件
        userCriteria.andEqualTo("username", username);
        userCriteria.andEqualTo("password", password);

        Users result = usersMapper.selectOneByExample(userExample);
        return result;
    }
}