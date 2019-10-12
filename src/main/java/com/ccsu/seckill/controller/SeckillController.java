package com.ccsu.seckill.controller;

import com.ccsu.seckill.domain.OrderInfo;
import com.ccsu.seckill.domain.SeckillOrder;
import com.ccsu.seckill.domain.SeckillUser;
import com.ccsu.seckill.result.CodeMsg;
import com.ccsu.seckill.service.GoodsService;
import com.ccsu.seckill.service.OrderService;
import com.ccsu.seckill.service.SeckillService;
import com.ccsu.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Description
 * @auther DuanXiaoping
 * @create 2019-10-12 15:27
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    @Autowired
    GoodsService goodsService;
    @Autowired
    OrderService orderService;

    @Autowired
    SeckillService seckillService;
    @RequestMapping("/do_seckill")
    public String order(Model model, SeckillUser user,
                            @RequestParam("goodsId") long goodsId){
        if (user == null){
            return "to_login";
        }
        model.addAttribute("user",user);
//        判断库存
        GoodsVo goods = goodsService.getGoodsDetail(goodsId);
        int stock = goods.getGoodsStock();
        if (stock <= 0){
            model.addAttribute("errmsg", CodeMsg.SECKILL_OVER.getMsg());
            return "seckill_fail";
        }

        //判断是否已经进行了秒杀
        System.out.println(user);
        SeckillOrder order =
                orderService.getOrderByUserAndGoods(
                user.getId(),
                goodsId);
        if(order != null) {
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA.getMsg());
            return "seckill_fail";
        }
//减库存 下订单 写入秒杀订单
        OrderInfo orderInfo = seckillService.seckill(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";


    }
}
