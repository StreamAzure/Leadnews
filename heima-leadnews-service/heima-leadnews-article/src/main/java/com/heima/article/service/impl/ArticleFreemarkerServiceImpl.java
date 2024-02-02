package com.heima.article.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.heima.article.service.ApArticleService;
import com.heima.article.service.ArticleFreemarkerService;
import com.heima.file.service.FileStorageService;
import com.heima.model.article.pojos.ApArticle;
import edu.umd.cs.findbugs.annotations.Confidence;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class ArticleFreemarkerServiceImpl implements ArticleFreemarkerService {
    @Autowired
    private Configuration configuration;
    @Autowired
    private FileStorageService fileStorageService;
    @Autowired
    private ApArticleService apArticleService;

    /**
     * 生成静态文件上传到 minIO 中
     * @param apArticle
     * @param content
     */
    @Override
    @Async
    public void buildArticleToMinIO(ApArticle apArticle, String content) {
        if(StringUtils.isNotBlank(content)){
            Template template = null;
            try {
                template = configuration.getTemplate("article.ftl");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Map<String, Object> contentDataModel = new HashMap<>();
            contentDataModel.put("content", JSONArray.parseArray(content));
            StringWriter out = new StringWriter();

            // 合成
            try {
                template.process(contentDataModel, out);
            } catch (TemplateException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            // 把HTML文件上传到minIO
            InputStream in  = new ByteArrayInputStream(out.toString().getBytes());
            String path = fileStorageService.uploadHtmlFile("", apArticle.getId() + ".html", in);

            // 修改 ap_article 表，保存minIO的URL
            apArticleService.update(Wrappers.<ApArticle>lambdaUpdate().eq(ApArticle::getId, apArticle.getId())
                    .set(ApArticle::getStaticUrl, path));

        }
    }
}
