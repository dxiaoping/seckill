package com.ccsu.seckill.dao;

import com.ccsu.seckill.domain.SeckillUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @Description
 * @auther DuanXiaoping
 * @create 2019-10-11 10:17
 */
@Mapper
public interface UserDao {

    @Select("select * from seckill_user where id = #{id}")
    public SeckillUser getById(@Param("id") long id);
}
