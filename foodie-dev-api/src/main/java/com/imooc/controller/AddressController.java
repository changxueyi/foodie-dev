package com.imooc.controller;

import com.imooc.pojo.Bo.AddressBo;
import com.imooc.pojo.UserAddress;
import com.imooc.service.AddressService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.MobileEmailUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ClassName AddressController
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/4/7 15:41
 */
@Api(value = "地址相关", tags = {"地址相关的api接口"})
@RequestMapping("address")
@RestController
public class AddressController {
    /**
     * 用户在确认订单页面，可以针对收货地址做如下操作：
     * 1. 查询用户的所有收货地址列表
     * 2. 新增收货地址
     * 3. 删除收货地址
     * 4. 修改收货地址
     * 5. 设置默认地址
     */
    @Autowired
    private AddressService addressService;

    @ApiOperation(value = "根据用户id查询收货地址列表", notes = "根据用户id查询收货地址列表", httpMethod = "POST")
    @PostMapping("/list")
    public IMOOCJSONResult list(@RequestParam String userId) {
        if (StringUtils.isBlank(userId)) {
            return IMOOCJSONResult.errorMsg("");
        }
        List<UserAddress> list = addressService.queryAll(userId);
        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "用户新增地址", notes = "用户新增地址", httpMethod = "POST")
    @PostMapping("/add")
    public IMOOCJSONResult add(@RequestBody AddressBo addressBo) {
        IMOOCJSONResult checkRes = checkAddress(addressBo);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        addressService.addNewUserAddress(addressBo);
        return IMOOCJSONResult.ok();
    }

    //传来的Bo 主要包括了 1. 地址id 2. 用户 id 3. 收件人 4. 手机号 5. 省份 6.城市 7.区 8.具体描述
    private IMOOCJSONResult checkAddress(AddressBo addressBo) {
        String receiver = addressBo.getReceiver();
        if (StringUtils.isBlank(receiver)) {
            return IMOOCJSONResult.errorMsg("收货人不能为空");
        }
        String mobile = addressBo.getMobile();
        if (StringUtils.isBlank(mobile)) {
            return IMOOCJSONResult.errorMsg("收货人手机号不能为空");
        }
        if (mobile.length() != 11) {
            return IMOOCJSONResult.errorMsg("收货人手机号长度不正确");
        }
        boolean isMobileOk = MobileEmailUtils.checkMobileIsOk(mobile);
        if (!isMobileOk) {
            return IMOOCJSONResult.errorMsg("收货人手机号格式不正确");
        }
        String province = addressBo.getProvince();
        String city = addressBo.getCity();
        String district = addressBo.getDistrict();
        String detail = addressBo.getDetail();
        if (StringUtils.isBlank(province) ||
                StringUtils.isBlank(city) ||
                StringUtils.isBlank(district) ||
                StringUtils.isBlank(detail)) {
            return IMOOCJSONResult.errorMsg("收获地址信息不能为空");
        }
        return IMOOCJSONResult.ok();
    }

    //修改收获地址
    @ApiOperation(value = "修改收获地址", notes = "修改收获地址", httpMethod = "POST")
    @PostMapping("/update")
    public IMOOCJSONResult update(@RequestBody AddressBo addressBo) {
        if (StringUtils.isBlank(addressBo.getAddressId())) {
            return IMOOCJSONResult.errorMsg("修改地址错误，addressId不能为空");
        }
        IMOOCJSONResult checkRes = checkAddress(addressBo);
        if (checkRes.getStatus() != 200) {
            return checkRes;
        }
        addressService.updateUserAddress(addressBo);
        return IMOOCJSONResult.ok();

    }

    //删除用户地址
    @ApiOperation(value = "用户删除地址", notes = "用户删除地址", httpMethod = "POST")
    @PostMapping("/delete")
    public IMOOCJSONResult delete(@RequestParam String userId, @RequestParam String addressId) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return IMOOCJSONResult.errorMsg("");
        }
        //删除，传入一个用户id，传入一个地址的id
        addressService.deleteUserAddress(userId,addressId);
        return IMOOCJSONResult.ok();
    }
    @ApiOperation(value = "用户设置默认地址", notes = "用户设置默认地址", httpMethod = "POST")
    @PostMapping("/setDefalut")
    public IMOOCJSONResult setDefalut(
            @RequestParam String userId,
            @RequestParam String addressId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(addressId)) {
            return IMOOCJSONResult.errorMsg("");
        }

        addressService.updateUserAddressToBeDefault(userId, addressId);
        return IMOOCJSONResult.ok();
    }
}