package com.wen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * (User)表数据库访问层
 *
 * @author makejava
 * @since 2022-08-30 17:12:47
 */

@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}

