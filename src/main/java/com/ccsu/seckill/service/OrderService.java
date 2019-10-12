package com.ccsu.seckill.service;

import com.ccsu.seckill.dao.OrderDao;
import com.ccsu.seckill.domain.OrderInfo;
import com.ccsu.seckill.domain.SeckillOrder;
import com.ccsu.seckill.domain.SeckillUser;
import com.ccsu.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Description
 * @auther DuanXiaoping
 * @create 2019-10-12 15:58
 */
@Service
public class OrderService {

    @Autowired
    OrderDao orderDao;

    public SeckillOrder getOrderByUserAndGoods(long userId, long goodsId){
        return orderDao.getOrder(userId,goodsId);
    }

    @Transactional
    public OrderInfo createOrder(SeckillUser user, GoodsVo goods){
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        long orderId = orderDao.insertOrder(orderInfo);
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setOrderId(orderId);
        seckillOrder.setUserId(user.getId());
        orderDao.insertSeckillOrder(seckillOrder);
        return orderInfo;
    }

}
