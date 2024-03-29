package com.heima.wemedia.controller.v1;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.wemedia.dtos.WmNewsDto;
import com.heima.model.wemedia.dtos.WmNewsPageReqDto;
import com.heima.wemedia.service.WmNewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/news")
@Slf4j
public class WmNewsController {
    @Autowired
    private WmNewsService wmNewsService;

    @PostMapping("/list")
    public ResponseResult findAll(@RequestBody WmNewsPageReqDto dto){
        return  wmNewsService.findAll(dto);
    }

    @PostMapping("/submit")
    public ResponseResult submitNews(@RequestBody WmNewsDto dto){
        return  wmNewsService.submitNews(dto);
    }

    @GetMapping("/one/{id}")
    public ResponseResult detailNews(@PathVariable Integer id){
        log.info("查看文章详情： {}",id);
        return wmNewsService.detailNews(id);
    }

    @GetMapping("/del_news/{id}")
    public ResponseResult deleteNews(@PathVariable Integer id){
        log.info("删除文章：{}", id);
        return wmNewsService.deleteNews(id);
    }

    @PostMapping("/down_or_up")
    public ResponseResult downOrUpNews(@RequestBody WmNewsDto wmNewsDto){
        log.info("文章上下架: {}", wmNewsDto);
        return wmNewsService.downOrUpNews(wmNewsDto);
    }

}