package com.imooc.service;

import com.imooc.pojo.Bo.AddressBo;
import com.imooc.pojo.UserAddress;

import java.util.List;

/**
 * @ClassName AddressService
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/4/7 15:44
 */
public interface AddressService {
    /**
     * 根据用户id查询用户的收货地址列表
     * @param userId
     * @return
     */
    public List<UserAddress> queryAll(String userId);
    //增加收获地址
    public void addNewUserAddress(AddressBo addressBo);

    //更改收获地址
    public void updateUserAddress(AddressBo addressBo);

    /**
     * 根据用户id和地址id，删除对应的用户地址信息
     * @param userId
     * @param addressId
     * @return void
    */
    public void deleteUserAddress(String userId, String addressId);
    /**
     * 修改默认地址
     * @param userId
     * @param addressId
     */
    public void updateUserAddressToBeDefault(String userId, String addressId);

    /**
     * 根据用户id和地址id，查询具体的用户地址对象信息
     * @param userId
     * @param addressId
     * @return
     */
    public UserAddress queryUserAddres(String userId, String addressId);


}