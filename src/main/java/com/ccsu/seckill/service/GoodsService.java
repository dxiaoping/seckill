package com.ccsu.seckill.service;

import com.ccsu.seckill.dao.GoodsDao;
import com.ccsu.seckill.domain.Goods;
import com.ccsu.seckill.domain.SeckillGoods;
import com.ccsu.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @auther DuanXiaoping
 * @create 2019-10-12 13:32
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> goodsVoList(){
        return goodsDao.getGoodsVoList();
    }

    public GoodsVo getGoodsDetail(long goodsId){
        return goodsDao.getGoodsVoById(goodsId);
    }

    public void reduceStock(GoodsVo goods){
        SeckillGoods sg = new SeckillGoods();
        sg.setGoodsId(goods.getId());
        goodsDao.reduceStock(sg);
    }
}
