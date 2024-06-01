package com.yupi.richcontext.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 文章
 */
@TableName(value = "article")
@Data
public class Article implements Serializable {

    /**
     * 文章id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 文章所有者
     */
    private Long ownerId;

    /**
     * 文章内容 目前考虑的是OSS_url
     */
    private String content;

    /**
     * 安全级别 考虑模仿美团的 p0~p4 p4安全级别最高
     */
    private Integer safetyLevel;

    /**
     * 是否公开 public private
     */
    private Integer privacy;

    /**
     * 1只读 0可修改
     */
    private Integer onlyRead;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 最后操作者
     */
    private Long lastOperatorId;

    /**
     * 文章是否被删除
     */
    @TableLogic
    private Integer isDelete;
}
