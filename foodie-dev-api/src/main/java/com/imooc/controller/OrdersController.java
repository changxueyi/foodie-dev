package com.imooc.controller;

import com.imooc.enums.PayMethod;
import com.imooc.pojo.Bo.SubmitOrderBo;
import com.imooc.service.OrderService;
import com.imooc.utils.CookieUtils;
import com.imooc.utils.IMOOCJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @ClassName OrdersController
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/4/8 16:56
 */
@Api(value = "订单相关", tags = {"订单相关的api接口"})
@RequestMapping("orders")
@RestController
public class OrdersController extends BaseController {
    final static Logger logger = LoggerFactory.getLogger(OrdersController.class);
    @Autowired
    private OrderService orderService;
    @ApiOperation(value = "用户下单", notes = "用户下单", httpMethod = "POST")
    @PostMapping("/create")
    public IMOOCJSONResult create(
            @RequestBody SubmitOrderBo submitOrderBo,
            HttpServletRequest request,
            HttpServletResponse response) {

        if (submitOrderBo.getPayMethod() != PayMethod.WEIXIN.type
                && submitOrderBo.getPayMethod() != PayMethod.ALIPAY.type) {
            return IMOOCJSONResult.errorMsg("支付方式不支持！");
        }
        System.out.println(submitOrderBo.toString());
        System.out.println("*********************");
        String orderId = orderService.createOrder(submitOrderBo);

        //2.创建订单以后，移除购物车中已结算的商品
        // TODO 整合redis之后，完善购物车中的已结算商品清除，并且同步到前端的cookie
     //  CookieUtils.setCookie(request, response, FOODIE_SHOPCART, "", true);
        return IMOOCJSONResult.ok(orderId);
    }
    //

}