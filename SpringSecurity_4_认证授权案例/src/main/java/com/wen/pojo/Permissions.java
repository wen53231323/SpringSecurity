package com.wen.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * (Permissions)表实体类
 *
 * @author makejava
 * @since 2022-09-04 16:42:42
 */
// @SuppressWarnings注解是jse提供的注解，屏蔽无关紧要的警告。
@SuppressWarnings("serial")
// 代表get、set、toString、equals、hashCode等操作
@Data
// 代表无参构造
@NoArgsConstructor
// 代表全参构造
@AllArgsConstructor
public class Permissions {
    //权限id    
    private Long id;
    //权限名称    
    private String name;
    //权限路由地址    
    private String path;
    //权限组件路径    
    private String component;
    //权限标识    
    private String perms;
    //权限图标    
    private String icon;
    //权限创建者    
    private Long createBy;
    //权限创建时间    
    private Date createTime;
    //权限更新者    
    private Long updateBy;
    //权限更新时间    
    private Date updateTime;
    //权限备注    
    private String remark;
    //权限状态（0显示 1隐藏）    
    private String visible;
    //权限状态（0正常 1停用）    
    private String status;
    //权限删除标志（0未删除、1已删除）    
    private Integer delFlag;
}

