package com.heima.search.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.search.service.ArticleSearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/article/search")
@Slf4j
public class ArticleSearchController {

    @Autowired
    private ArticleSearchService articleSearchService;
    @PostMapping("/search")
    public ResponseResult search(@RequestBody UserSearchDto userSearchDto){
        log.info("/api/v1/article/search/search");
        try {
            return articleSearchService.search(userSearchDto);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
