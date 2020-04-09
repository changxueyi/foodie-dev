package com.imooc.pojo.Vo;

/**
 * @ClassName MerchantOrdersVO
 * @Description TODO 订单详情
 * @Author changxueyi
 * @Date 2020/4/8 17:57
 */
public class MerchantOrdersVO {
    private String merchantOrderId;//商户订单号
    private String merchantUserid;//商户方的发起用户主键id
    private Integer amount;  //顶顶那的总金额 （包含商户所支付的订单费邮费总额）
    private Integer payMethod; //支付方式： 1. 微信  2. 支付宝
    private String returnUrl;  //支付成功的回调地址

    public String getMerchantOrderId() {
        return merchantOrderId;
    }

    public void setMerchantOrderId(String merchantOrderId) {
        this.merchantOrderId = merchantOrderId;
    }

    public String getMerchantUserid() {
        return merchantUserid;
    }

    public void setMerchantUserid(String merchantUserid) {
        this.merchantUserid = merchantUserid;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(Integer payMethod) {
        this.payMethod = payMethod;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
}