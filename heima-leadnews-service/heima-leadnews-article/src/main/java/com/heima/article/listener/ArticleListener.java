package com.heima.article.listener;

import com.alibaba.fastjson.JSON;
import com.heima.article.service.ApArticleConfigService;
import com.heima.article.service.ApArticleService;
import com.heima.common.constants.WmNewsMessageConstants;
import com.heima.model.article.pojos.ApArticle;
import com.heima.model.wemedia.dtos.WmNewsDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ArticleListener {

    @Autowired
    private ApArticleConfigService apArticleConfigService;
    @KafkaListener(topics = {WmNewsMessageConstants.WM_NEWS_UP_OR_DOWN_TOPIC})
    public void onMessage(String message){
        log.info("从 Kafka 取出消息");
        if(!StringUtils.isEmpty(message)){
            Map<String, Object>  map = JSON.parseObject((String) message, Map.class);
            log.info(map.toString());
            apArticleConfigService.updateByMap(map);
        }
    }
}
