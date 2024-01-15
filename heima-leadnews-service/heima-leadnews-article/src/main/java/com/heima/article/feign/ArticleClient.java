package com.heima.article.feign;

import com.heima.apis.article.IArticleClient;
import com.heima.article.service.ApArticleService;
import com.heima.model.article.dtos.ArticleDto;
import com.heima.model.common.dtos.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ArticleClient implements IArticleClient {

    @Autowired
    private ApArticleService apArticleService;
    @PostMapping("/api/v1/article/save")
    @Override
    public ResponseResult saveArticle(@RequestBody ArticleDto dto){
        log.info("Article 微服务被远程调用：Feign saveArticle: {}",dto);
        return apArticleService.saveArticle(dto);
    }
}
