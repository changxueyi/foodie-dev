package com.imooc.service.impl;

import com.imooc.enums.YesOrNo;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.Bo.AddressBo;
import com.imooc.pojo.UserAddress;
import com.imooc.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @ClassName AddressServiceImpl
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/4/7 15:45
 */
@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private Sid sid;

    /**
     * 根据用户id查询用户的收货地址列表
     *
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress ua = new UserAddress();
        ua.setUserId(userId);
        return userAddressMapper.select(ua);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public void addNewUserAddress(AddressBo addressBo) {
        //1.判断用户是否有收货地址，如果没有，曾新增为默认地址
        Integer isDefault = 0;
        List<UserAddress> addressList = this.queryAll(addressBo.getUserId());
        if (addressList == null || addressList.isEmpty() || addressList.size() == 0) {
            isDefault = 1;
            //设置为默认地址
        }
        String addressId = sid.nextShort();
        //2.保存地址到数据库
        UserAddress newAddress = new UserAddress();
        //用户地址表包括了：1.主键id   2.关联用户id 3.收件人姓名 4.手机号 5.省份6.城市7.区8.详细地址 9.扩展字段
        //newAddress      10.是否是默认地址 11.创建时间 12.更新时间
        // addressBo      1. 地址id  2. 用户 id 3. 收件人 4. 手机号 5. 省份 6.城市 7.区 8.具体描述
        //将addressBO中的元素保存在newAddress中
        BeanUtils.copyProperties(addressBo, newAddress);

        //设置随机的一串数字，作为地址id
        newAddress.setId(addressId);
        newAddress.setIsDefault(isDefault);
        newAddress.setCreatedTime(new Date());
        newAddress.setUpdatedTime(new Date());

        userAddressMapper.insert(newAddress);
    }

    //收货地址的更新
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updateUserAddress(AddressBo addressBo) {
        //获取到这个地址的id
        String addressId = addressBo.getAddressId();
        //创建一个用户地址entity
        UserAddress pendingAddress = new UserAddress();
        BeanUtils.copyProperties(addressBo, pendingAddress);
        //下面这个是不一样的,因为是这样的，：Useraddress 与 AddressBo 中的 id 是不一样的
        pendingAddress.setId(addressId);
        pendingAddress.setUpdatedTime(new Date());

        userAddressMapper.updateByPrimaryKeySelective(pendingAddress);
    }

    /**
     * 根据用户id和地址id，删除对应的用户地址信息
     *
     * @param userId
     * @param addressId
     * @return void
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void deleteUserAddress(String userId, String addressId) {
        UserAddress address = new UserAddress();
        //直接给定一个userAddress 直接就给你删除了，设置好用户id和地址id
        address.setId(addressId);
        address.setUserId(userId);
        userAddressMapper.delete(address);
    }

    /**
     * 修改默认地址 ***********************************这个接口不好实现****************************
      *
     * @param userId
     * @param addressId
     */
    @Override
    public void updateUserAddressToBeDefault(String userId, String addressId) {
        UserAddress queryAddress = new UserAddress();
        queryAddress.setUserId(userId);
        queryAddress.setIsDefault(YesOrNo.YES.type);
        List<UserAddress> list = userAddressMapper.select(queryAddress);
        for (UserAddress ua : list) {
            ua.setIsDefault(YesOrNo.NO.type);
            userAddressMapper.updateByPrimaryKeySelective(ua);
        }
        // 2. 根据地址id修改为默认的地址
        UserAddress defaultAddress = new UserAddress();
        defaultAddress.setId(addressId);
        defaultAddress.setUserId(userId);
        defaultAddress.setIsDefault(YesOrNo.YES.type);
        userAddressMapper.updateByPrimaryKeySelective(defaultAddress);
    }

    /**
     * 根据用户id和地址id，查询具体的用户地址对象信息
     *
     * @param userId
     * @param addressId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public UserAddress queryUserAddres(String userId, String addressId) {

        UserAddress singleAddress = new UserAddress();
        singleAddress.setId(addressId);
        singleAddress.setUserId(userId);

        return userAddressMapper.selectOne(singleAddress);
    }
}