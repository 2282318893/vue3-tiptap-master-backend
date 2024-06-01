package com.yupi.richcontext.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.richcontext.model.dto.article.ArticleSaveRequest;
import com.yupi.richcontext.model.entity.Article;

import java.util.List;

public interface ArticleService extends IService<Article> {

    /**
     * 提交文章
     *
     * @param articleSaveRequest 文章请求保存信息
     * @return 保存文章id
     */
    Long articleSave(ArticleSaveRequest articleSaveRequest);

}
