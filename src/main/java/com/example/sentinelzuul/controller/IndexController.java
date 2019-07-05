package com.example.sentinelzuul.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wuweifeng wrote on 2019/7/5.
 */
@RestController
public class IndexController {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        logger.info("invoked name = " + name);
        return "hello " + name;
    }
}
