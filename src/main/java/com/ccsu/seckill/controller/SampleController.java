package com.ccsu.seckill.controller;

import com.ccsu.seckill.domain.SeckillUser;
import com.ccsu.seckill.redis.RedisService;
import com.ccsu.seckill.redis.UserKey;
import com.ccsu.seckill.result.Result;
import com.ccsu.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description
 * @auther DuanXiaoping
 * @create 2019-10-11 9:52
 */
@Controller
@RequestMapping("/demo")
public class SampleController {

    @Autowired
    UserService userService;
//
    @Autowired
    RedisService redisService;
//
//    @Autowired
//    MQSender sender;

//	@RequestMapping("/mq/header")
//    @ResponseBody
//    public Result<String> header() {
//		sender.sendHeader("hello,imooc");
//        return Result.success("Hello，world");
//    }
//
//	@RequestMapping("/mq/fanout")
//    @ResponseBody
//    public Result<String> fanout() {
//		sender.sendFanout("hello,imooc");
//        return Result.success("Hello，world");
//    }
//
//	@RequestMapping("/mq/topic")
//    @ResponseBody
//    public Result<String> topic() {
//		sender.sendTopic("hello,imooc");
//        return Result.success("Hello，world");
//    }
//
//	@RequestMapping("/mq")
//    @ResponseBody
//    public Result<String> mq() {
//		sender.send("hello,imooc");
//        return Result.success("Hello，world");
//    }
//===========================================================
//    @RequestMapping("/hello")
//    @ResponseBody
//    public Result<String> home() {
//        return Result.success("Hello，world");
//    }
//
//    @RequestMapping("/error")
//    @ResponseBody
//    public Result<String> error() {
//        return Result.error(CodeMsg.SESSION_ERROR);
//    }

    @RequestMapping("/hello/themaleaf")
    public String themaleaf(Model model) {
        model.addAttribute("name", "Joshua");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<SeckillUser> dbGet() {
        SeckillUser user = userService.getById(Long.parseLong("15574902291"));
        return Result.success(user);
    }
//
//
//    @RequestMapping("/db/tx")
//    @ResponseBody
//    public Result<Boolean> dbTx() {
//        userService.tx();
//        return Result.success(true);
//    }
//
    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<SeckillUser> redisGet() {
        SeckillUser user  = redisService.get(UserKey.getById, ""+1, SeckillUser.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet() {
        SeckillUser user  = new SeckillUser();
        user.setId(15574902291l);
        user.setNickname("1111");
        redisService.set(UserKey.getById, ""+1, user);//UserKey:id1
        return Result.success(true);
    }

}
