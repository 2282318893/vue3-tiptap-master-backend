package com.yupi.richcontext.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.richcontext.mapper.ArticleMapper;
import com.yupi.richcontext.mapper.UserMapper;
import com.yupi.richcontext.model.dto.article.ArticleSaveRequest;
import com.yupi.richcontext.model.entity.Article;
import com.yupi.richcontext.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Override
    public Long articleSave(ArticleSaveRequest articleSaveRequest) {
        Article article = new Article();
        Long ownerId = articleSaveRequest.getOwnerId();
        String content = articleSaveRequest.getContent();
        Integer safetyLevel = articleSaveRequest.getSafetyLevel();
        Integer privacy = articleSaveRequest.getPrivacy();
        Integer onlyRead = articleSaveRequest.getOnlyRead();
        Long submitterId = articleSaveRequest.getSubmitterId();
        article.setOwnerId(ownerId);
        // @watch 实际文件存储到OSS中 返回url
        article.setContent(content);
        article.setSafetyLevel(safetyLevel);
        article.setPrivacy(privacy);
        article.setOnlyRead(onlyRead);
        article.setLastOperatorId(submitterId);
        this.save(article);
        // 获取article id
        return article.getId();
    }

}
