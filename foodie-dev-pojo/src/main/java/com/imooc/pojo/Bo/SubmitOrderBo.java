package com.imooc.pojo.Bo;

/**
 * @ClassName SubmitOrderBo
 * @Description TODO 前端传来的参数，用于创建一个订单
 * @Author changxueyi
 * @Date 2020/4/8 17:03
 */
public class SubmitOrderBo {
    //用户id
    private String userId;
    //商品的规格，因为这个是很重要的，需要规格才能去判断商品，每一件商品，规格不同，就不同商品
    private String itemSpecIds;
    //商品的地址
    private String addressId;
    //商品支付的方式
    private Integer payMethod;
    //商品备注的信息
    private String leftMsg;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemSpecIds() {
        return itemSpecIds;
    }

    public void setItemSpecIds(String itemSpecIds) {
        this.itemSpecIds = itemSpecIds;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getLeftMsg() {
        return leftMsg;
    }

    public void setLeftMsg(String leftMsg) {
        this.leftMsg = leftMsg;
    }

    @Override
    public String toString() {
        return "SubmitOrderBo{" +
                "userId='" + userId + '\'' +
                ", itemSpecIds='" + itemSpecIds + '\'' +
                ", addressId='" + addressId + '\'' +
                ", payMethod=" + payMethod +
                ", leftMsg='" + leftMsg + '\'' +
                '}';
    }
}