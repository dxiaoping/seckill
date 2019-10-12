package com.ccsu.seckill.dao;

import com.ccsu.seckill.domain.OrderInfo;
import com.ccsu.seckill.domain.SeckillOrder;
import org.apache.ibatis.annotations.*;

/**
 * @Description
 * @auther DuanXiaoping
 * @create 2019-10-12 16:00
 */
@Mapper
public interface OrderDao {
    @Select("select * from seckill_order where user_id=#{userId} and goods_id=#{goodsId}")
    SeckillOrder getOrder(@Param("userId") long userId,@Param("goodsId") long goodsId);

    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    long insertOrder(OrderInfo order);


    @Insert("insert into seckill_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    long insertSeckillOrder(SeckillOrder seckillOrder);
}
