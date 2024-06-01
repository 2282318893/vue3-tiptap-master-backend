package com.yupi.richcontext.controller;

import com.yupi.richcontext.common.BaseResponse;
import com.yupi.richcontext.common.DeleteRequest;
import com.yupi.richcontext.common.ErrorCode;
import com.yupi.richcontext.common.ResultUtils;
import com.yupi.richcontext.exception.BusinessException;
import com.yupi.richcontext.exception.ThrowUtils;
import com.yupi.richcontext.model.dto.article.ArticleSaveRequest;
import com.yupi.richcontext.model.dto.article.ArticleUpdateRequest;
import com.yupi.richcontext.model.entity.Article;
import com.yupi.richcontext.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Resource
    private ArticleService articleService;

    /**
     * 保存提交文章
     * @param articleSaveRequest 提交文章基本数据
     * @return 返回文章id
     *
     * 错误警告！！：方法记得是public修饰
     */
    @PostMapping("/save")
    public BaseResponse<Long> articleSave(@RequestBody ArticleSaveRequest articleSaveRequest){
        if(articleSaveRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long articleId = articleService.articleSave(articleSaveRequest);

        return ResultUtils.success(articleId);
    }

    /**
     * 删除文章
     * @param deleteRequest 删除文章id
     * @return 返回boolean值
     */
    @PostMapping("/delete")
    public BaseResponse<Boolean> articleDelete(@RequestBody DeleteRequest deleteRequest){
        if(deleteRequest == null || deleteRequest.getId() <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Boolean b = articleService.removeById(deleteRequest.getId());
        return ResultUtils.success(b);
    }

    /**
     * 根据id查询文章
     * @param id 文章id
     * @return 对应文章
     */
    @GetMapping("/getByArticleId")
    public BaseResponse<Article> getArticleById(Long id){
        if(id == null || id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Article article = articleService.getById(id);
        ThrowUtils.throwIf(article == null,ErrorCode.NOT_FOUND_ERROR);
        return ResultUtils.success(article);
    }

    /**
     * 根据用户id查询用户所有文章
     * @param id 用户id
     * @return 返回用户所有文章
     */
    @GetMapping("/getByUserId")
    public BaseResponse<List<Article>> getArticleListByUserId(Long id){
        if(id == null || id <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        HashMap<String, Object> params = new HashMap<>();
        params.put("ownerId",id);
        List<Article> articles = articleService.listByMap(params);
        if(articles.isEmpty()){
            return ResultUtils.success(new ArrayList<Article>());
        }
        return ResultUtils.success(articles);
    }

    /**
     * 更新文章
     * @param articleUpdateRequest 新的文章信息
     * @return 布尔值
     */
    @PostMapping("/update")
    public BaseResponse<Boolean> articleUpdate(@RequestBody ArticleUpdateRequest articleUpdateRequest){
        if(articleUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Long id = articleUpdateRequest.getId();
        Long ownerId = articleUpdateRequest.getOwnerId();
        String content = articleUpdateRequest.getContent();
        Integer safetyLevel = articleUpdateRequest.getSafetyLevel();
        Integer privacy = articleUpdateRequest.getPrivacy();
        Integer onlyRead = articleUpdateRequest.getOnlyRead();
        Long submitterId = articleUpdateRequest.getSubmitterId();

        Article article = new Article();
        article.setId(id);
        article.setOwnerId(ownerId);
        article.setContent(content);
        article.setSafetyLevel(safetyLevel);
        article.setPrivacy(privacy);
        article.setOnlyRead(onlyRead);
        article.setLastOperatorId(submitterId);

        boolean b = articleService.updateById(article);

        return ResultUtils.success(b);
    }
}
