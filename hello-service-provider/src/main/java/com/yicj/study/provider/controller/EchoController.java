package com.yicj.study.provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yicj
 * @date 2023/11/11 9:03
 */
@RestController
@RequestMapping("/echo")
public class EchoController {

    @GetMapping("/dynamic/{name}")
    public String dynamicEcho(@PathVariable("name") String name) throws Exception {
        Thread.sleep(500);
        return "hello, " + name ;
    }


    @GetMapping("/fixed/hello")
    public String fixedEcho() throws Exception {
        Thread.sleep(500);
        return "hello, sentinel !" ;
    }

}
