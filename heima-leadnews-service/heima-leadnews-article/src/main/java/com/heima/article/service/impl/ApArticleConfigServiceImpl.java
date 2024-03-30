package com.heima.article.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.heima.article.mapper.ApArticleConfigMapper;
import com.heima.article.service.ApArticleConfigService;
import com.heima.model.article.pojos.ApArticleConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.Map;

@Service
public class ApArticleConfigServiceImpl extends ServiceImpl<ApArticleConfigMapper, ApArticleConfig> implements ApArticleConfigService{

    @Autowired
    private ApArticleConfigMapper apArticleConfigMapper;

    @Override
    public void updateByMap(Map map) {
        Boolean isDown = !map.get("enable").equals(1); // 不为1则下架
        update(Wrappers.<ApArticleConfig>lambdaUpdate()
                .eq(ApArticleConfig::getArticleId, map.get("articleId"))
                .set(ApArticleConfig::getIsDown, isDown));
    }
}
