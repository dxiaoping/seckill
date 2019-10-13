package com.ccsu.seckill.controller;

import com.ccsu.seckill.domain.SeckillUser;
import com.ccsu.seckill.service.GoodsService;
import com.ccsu.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.List;

/**
 * @Description
 * @auther DuanXiaoping
 * @create 2019-10-12 13:27
 */
@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    GoodsService goodsService;


    @Autowired
    ThymeleafViewResolver thymeleaf;

    /**
     * 压测结果 3000线程 110 QPS
     *
     * @param model
     * @return
     */
    @RequestMapping("/to_list")
    public String list(Model model) {
        List<GoodsVo> goodsList = goodsService.goodsVoList();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, SeckillUser user,
                         @PathVariable("goodsId") long goodsId) {
        GoodsVo goods = goodsService.getGoodsDetail(goodsId);
        long startTime = goods.getStartDate().getTime();
        long endTime = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();
        /**
         * 0 活动未开始
         * 1 活动正在进行
         * 2 活动结束
         */
        int seckillState = 0;
        int remainSeconds = 0;
        if (now < startTime) {
            remainSeconds = (int) (startTime - now) / 1000;
        } else if (now > startTime && now < endTime) {
            seckillState = 1;
        } else if (now > endTime) {
            seckillState = 2;
            remainSeconds = -1;
        }
        model.addAttribute("remainSeconds", remainSeconds);
        model.addAttribute("seckillState", seckillState);
        model.addAttribute("goods", goods);
        model.addAttribute("user", user);
        return "goods_detail";
    }
}
