package com.yicj.nacos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.endpoint.event.RefreshEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: yicj
 * @date: 2023/9/22 16:04
 */
@RestController
@RequestMapping("/config")
public class ConfigurationController {

    @Autowired
    private ApplicationContext context ;

    @GetMapping("/config")
    public String config(){
        Environment env = context.getEnvironment();
        StringBuilder builder = new StringBuilder() ;
        builder.append("env.get('book.category')=" + env.getProperty("book.category", "unknown"))
                .append("<br/>env.get('book.author')=" + env.getProperty("book.author", "unknown")) ;
        return builder.toString() ;
    }


    @GetMapping("/event")
    public String event(){
        context.publishEvent(new RefreshEvent(this, null, "just for test"));
        return "send RefreshEvent" ;
    }

}
