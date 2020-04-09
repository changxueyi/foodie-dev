package com.imooc.service;

import com.imooc.pojo.Bo.SubmitOrderBo;
import com.imooc.pojo.Vo.OrderVO;

public interface OrderService {
    /**
     * 用于创建订单相关信息
     * @param submitOrderBo
     */
    public String createOrder(SubmitOrderBo submitOrderBo);
}
