package com.springboot.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser implements Serializable {
    /**
     * 用户id
     */
    @TableId("id")
    private String id;

    /**
     * 手机号（账号）
     */
    private String phone;

    /**
     * 用户密码
     */
    private String password;


    /**
     * 游戏昵称
     */
    private String name;

    /**
     * 用户头像
     */
    private String headUrl;

    /**
     * 用户ip地址
     */
    private String ipAddress;

    /**
     * 身份证号码
     */
    private Integer number;

    /**
     * token令牌，用户的唯一标识
     */
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String token;

    /**
     * 状态（1：正常 0：停用）
     */
    private String status;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT) //插入时填充字段
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE) //插入和更新时填充字段
    private LocalDateTime updateTime;

    /**
     * 删除标记（0:可用 1:已删除）
     */
    private String isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}