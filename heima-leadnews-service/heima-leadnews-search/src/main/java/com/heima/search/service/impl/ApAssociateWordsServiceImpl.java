package com.heima.search.service.impl;

import com.heima.model.common.dtos.ResponseResult;
import com.heima.model.common.enums.AppHttpCodeEnum;
import com.heima.model.search.dtos.UserSearchDto;
import com.heima.search.pojos.ApAssociateWords;
import com.heima.search.service.ApAssociateWordsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApAssociateWordsServiceImpl implements ApAssociateWordsService {
    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public ResponseResult search(UserSearchDto searchDto) {
        //1. 检查参数
        if(StringUtils.isNotBlank(searchDto.getSearchWords())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        //2. 分页检查
        if(searchDto.getPageSize() > 20){
            searchDto.setPageSize(20);
        }
        //3. 模糊查询
        Query query = Query.query(Criteria.where("associateWords").regex(".*?\\" + searchDto.getSearchWords() + ".*"));
        query.limit(searchDto.getPageSize());
        List<ApAssociateWords> apAssociateWordsList = mongoTemplate.find(query, ApAssociateWords.class);
        return ResponseResult.okResult(apAssociateWordsList);
    }
}
