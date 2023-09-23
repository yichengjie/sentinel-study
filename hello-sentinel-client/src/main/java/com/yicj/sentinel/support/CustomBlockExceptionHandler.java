package com.yicj.sentinel.support;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: yicj
 * @date: 2023/9/23 11:17
 */
@Component
public class CustomBlockExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, BlockException e) throws Exception {
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        String message = "{\"code\": \"999\", \"message\": \"访问人数过多\"}" ;
        response.getWriter().write(message);
    }
}
