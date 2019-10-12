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
 * @create 2019-10-12 16:29
 */
@Service
public class SeckillService {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;


    @Transactional
    public OrderInfo seckill(SeckillUser user, GoodsVo goods) {


        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);
        //order_info maiosha_order
        return orderService.createOrder(user, goods);
    }
}
