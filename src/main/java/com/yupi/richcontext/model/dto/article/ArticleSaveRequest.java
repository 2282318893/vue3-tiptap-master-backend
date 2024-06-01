package com.yupi.richcontext.model.dto.article;

import lombok.Data;

import java.io.Serializable;

@Data
public class ArticleSaveRequest implements Serializable {


    /**
     * 文章所有者Id
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
     * 提交者
     */
    private Long submitterId;

}
