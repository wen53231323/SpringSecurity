package com.wen.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wen.pojo.Permissions;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * (Permissions)表数据库访问层
 *
 * @author makejava
 * @since 2022-09-04 16:42:40
 */

@Mapper
@Repository
public interface PermissionsMapper extends BaseMapper<Permissions> {
    @Select("select " +
            "perms " +
            "from user_role " +
            "left join role on user_role.role_id = role.id " +
            "left join role_permissions on role.id = role_permissions.role_id " +
            "left join permissions on role_permissions.permissions_id = permissions.id " +
            "where " +
            "user_id = #{id} " +
            "and role.status = 0 " +
            "and permissions.status = 0;"
    )
        // 查询用户的权限信息
    List<String> selectPermsByUserId(Long id);
}

