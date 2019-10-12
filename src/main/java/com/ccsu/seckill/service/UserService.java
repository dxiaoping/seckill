package com.ccsu.seckill.service;

import com.ccsu.seckill.dao.UserDao;
import com.ccsu.seckill.domain.SeckillUser;
import com.ccsu.seckill.exception.GlobalException;
import com.ccsu.seckill.redis.RedisService;
import com.ccsu.seckill.redis.UserKey;
import com.ccsu.seckill.result.CodeMsg;
import com.ccsu.seckill.util.MD5Util;
import com.ccsu.seckill.util.UUIDUtil;
import com.ccsu.seckill.vo.LoginVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description
 * @auther DuanXiaoping
 * @create 2019-10-11 10:16
 */
@Service
public class UserService {
    public static final String COOKI_NAME_TOKEN = "token";
    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;

    public SeckillUser getById(long id) {
        return userDao.getById(id);
    }

    public SeckillUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)) {
            return null;
        }
        SeckillUser user = redisService.get(UserKey.token, token, SeckillUser.class);
        //延长有效期
        if(user != null) {
            addCookie(response, token, user);
        }
        return user;
    }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null){
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        String mobile = loginVo.getMobile();
        String formPass = loginVo.getPassword();
//        验证用户是否存在
        SeckillUser user = userDao.getById(Long.parseLong(mobile));
        if (user == null){
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
//        验证密码
        String dbPass = user.getPassword();
        String dbSalt = user.getSalt();
        String calcPass = MD5Util.formPass2dbPass(formPass,dbSalt);
        if (!calcPass.equals(dbPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
//        生成cookie信息
        String token	 = UUIDUtil.uuid();
        addCookie(response, token, user);
        return true;
    }

    private void addCookie(HttpServletResponse response, String token, SeckillUser user) {
        redisService.set(UserKey.token, token, user);
        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(UserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}
