package com.ccsu.seckill.controller;

/**
 * @Description
 * @auther DuanXiaoping
 * @create 2019-10-11 21:52
 */

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ccsu.seckill.redis.RedisService;
import com.ccsu.seckill.result.Result;
import com.ccsu.seckill.service.UserService;
import com.ccsu.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;


@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_login")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        //登录
        userService.login(response, loginVo);
        return Result.success(true);
    }
}