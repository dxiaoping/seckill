package com.ccsu.seckill.dao;

import com.ccsu.seckill.domain.Goods;
import com.ccsu.seckill.domain.SeckillGoods;
import com.ccsu.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @Description
 * @auther DuanXiaoping
 * @create 2019-10-12 13:28
 */
@Mapper
public interface GoodsDao {
    @Select("select g.*,sg.stock_count, sg.start_date, sg.end_date,sg.seckill_price" +
            " from seckill_goods sg left join goods g on sg.goods_id=g.id")
    List<GoodsVo> getGoodsVoList();

    @Select("select g.*,sg.stock_count, sg.start_date, sg.end_date,sg.seckill_price " +
            "from seckill_goods sg left join goods g on sg.goods_id = g.id " +
            "where g.id = #{goodsId}")
    GoodsVo getGoodsVoById(@Param("goodsId") long goodsId);

@Update("update seckill_goods set stock_count = stock_count -1 where goods_id = #{goodsId}")
    int reduceStock(SeckillGoods seckillGoods);
}
