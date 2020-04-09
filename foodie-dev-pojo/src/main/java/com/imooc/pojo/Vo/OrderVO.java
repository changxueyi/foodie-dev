package com.imooc.pojo.Vo;

/**
 * @ClassName OrderVO
 * @Description TODO 返回订单的数据给前端
 * @Author changxueyi
 * @Date 2020/4/8 17:38
 */
public class OrderVO {
   //订单的id
   private String orderId;

   private MerchantOrdersVO merchantOrdersVO;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }
}