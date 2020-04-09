package com.imooc.service.impl;

import com.imooc.enums.OrderStatusEnum;
import com.imooc.enums.YesOrNo;
import com.imooc.mapper.OrderItemsMapper;
import com.imooc.mapper.OrderStatusMapper;
import com.imooc.mapper.OrdersMapper;
import com.imooc.pojo.*;
import com.imooc.pojo.Bo.SubmitOrderBo;
import com.imooc.pojo.Vo.OrderVO;
import com.imooc.service.AddressService;
import com.imooc.service.ItemService;
import com.imooc.service.OrderService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @ClassName OrderServiceImpl
 * @Description TODO
 * @Author changxueyi
 * @Date 2020/4/8 18:01
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private Sid sid;
    @Autowired
    private AddressService addressService;
    @Autowired
    private ItemService itemService;
    @Autowired
    private OrderItemsMapper orderItemsMapper;
    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private OrderStatusMapper orderStatusMapper;

    /**
     * 用于创建订单相关信息
     *
     * @param submitOrderBo
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String createOrder(SubmitOrderBo submitOrderBo) {
        //*****************模块一*********************************
        //传来要给订单的bo ，返回 OrderVO
        //submitOrderBo包括：1.用户iduserId 2.商品属性itemSpecIds 3.地址id，addressId
        //                  4.支付方式payMethod 5.订单备注leftMsg
        //返回的数据：orderVO：1.orderId订单id  2.订单详情：merchantOrdersVO
        // 订单详情主要包括：1.merchantOrderId商户订单号 2.merchantUserid;商户方的发起用户主键id
        //                  3.amount总结额，包括了邮费 4.payMethod 支付方式5.支付回调地址
        String userId = submitOrderBo.getUserId();
        String addressId = submitOrderBo.getAddressId();
        String itemSpecIds = submitOrderBo.getItemSpecIds();
        Integer payMethod = submitOrderBo.getPayMethod();
        String leftMsg = submitOrderBo.getLeftMsg();
        //设置包邮的费用
        Integer postAmount = 0;
        //随机生成一个订单的id
        String orderId = sid.nextShort();
        UserAddress address = addressService.queryUserAddres(userId, addressId);
        //1.新订单的数据保存
        Orders newOrder = new Orders();
        newOrder.setId(orderId);
        newOrder.setUserId(userId);

        newOrder.setReceiverName(address.getReceiver());
        newOrder.setReceiverMobile(address.getMobile());
        newOrder.setReceiverAddress(address.getProvince() + " "
                + address.getCity() + " "
                + address.getDistrict() + " "
                + address.getDetail()
        );
        //       newOrder.setTotalAmount();
        //优惠后实际支付价格
//        newOrder.setRealPayAmount();
        newOrder.setPostAmount(postAmount);
        newOrder.setPayMethod(payMethod);
        newOrder.setLeftMsg(leftMsg);

        newOrder.setIsComment(YesOrNo.NO.type);
        newOrder.setIsDelete(YesOrNo.NO.type);
        newOrder.setCreatedTime(new Date());
        newOrder.setUpdatedTime(new Date());
        //********模块二************2.循环根据itemSpecIds保存订单商品信息表
        //订单规格属性表相当于就是一个子订单表
        //因为传来的规格属性商品不止一个，用split 把每个商品分成一个一个，形成一个数组
        String itemSpecIdArr[] = itemSpecIds.split(",");
        Integer totalAmount = 0;//商品原价累计
        Integer realPayAmount = 0;//优惠后的实际支付价格
        //从数组中一个一个的遍历每个商品规格，计算出总价格
        for (String itemSpecId : itemSpecIdArr) {
            //1.根据规格id ，查询出规格的具体信息，主要获取这个商品规格的价格
            ItemsSpec itemsSpec = itemService.queryItemSpecById(itemSpecIds);
            //商品的数量要从后端 购物车获取，redis 所以先默认都为 1
            //TODO  redis 从购物车中获取商品数量
            int buyCounts = 1;//此处暂时写死，购买数量就是1；

            totalAmount += itemsSpec.getPriceNormal() * buyCounts;
            realPayAmount += itemsSpec.getPriceDiscount() * buyCounts;
            //根据规格id，获取商品信息以及商品图片
            String itemId = itemsSpec.getItemId();
            //根据商品id，获取商品的规格
            Items item = itemService.queryItemById(itemId);
            String imgUrl = itemService.queryItemMainImgById(itemId);
            //2.3循环保存子订单数据到数据库
            String subOrderId = sid.nextShort();
            //创建要给订单规格商品关联表
            OrderItems subOrderItem = new OrderItems();
            subOrderItem.setId(subOrderId);
            subOrderItem.setOrderId(orderId);
            //出错点一
            subOrderItem.setItemId(itemId);
            subOrderItem.setItemName(item.getItemName());
            //出错点二
            subOrderItem.setItemImg(imgUrl);
            subOrderItem.setBuyCounts(buyCounts);
            subOrderItem.setItemSpecId(itemSpecId);
            subOrderItem.setItemSpecName(itemsSpec.getName());
            subOrderItem.setPrice(itemsSpec.getPriceDiscount());
            orderItemsMapper.insert(subOrderItem);
            //在用户提交订单以后，规格表中扣除库存
            itemService.decreaseItemSpecStock(itemSpecId,buyCounts);
        }
        newOrder.setTotalAmount(totalAmount);
        //优惠后实际支付价格
        newOrder.setRealPayAmount(realPayAmount);
        ordersMapper.insert(newOrder);
        //************模块三***************3.保存订单状态表

        OrderStatus waitPayOrderStatus = new OrderStatus();
        //订单状态的id 和订单id一样
        waitPayOrderStatus.setOrderId(orderId);
        waitPayOrderStatus.setOrderStatus(OrderStatusEnum.WAIT_PAY.type);
        waitPayOrderStatus.setCreatedTime(new Date());
        orderStatusMapper.insert(waitPayOrderStatus);



        return orderId;
    }
}