package com.yicj.resttemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yicj.common.model.vo.RestResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;


/**
 * @author: yicj
 * @date: 2023/9/17 13:35
 */
@Slf4j
public class TypeReferenceTest{

    @Test
    public void hello(){
        String jsonContent = "{\"code\":\"200\",\"message\":\"error message\",\"data\":\"hello world\"}" ;
        TypeReference<RestResponse<String>> typeReference = new TypeReference<>(){} ;
        Type type = typeReference.getType();
        log.info("====> type : {}", type);
        RestResponse<String> response = JSON.parseObject(jsonContent, typeReference);
        log.info("====> response : {}", response);
    }


    @Test
    public void hello2(){
        TypeReference<RestResponse<String>> typeReference = new TypeReference<>(){} ;
        //
        //com.alibaba.fastjson.TypeReference<com.yicj.common.model.vo.RestResponse<java.lang.String>>
        Type superClass = typeReference.getClass().getGenericSuperclass();
        log.info("====> super class: {}", superClass);
        //--------------------//
        //com.yicj.common.model.vo.RestResponse<java.lang.String>
        Type type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        log.info("====> type: {}", type);
    }

}
