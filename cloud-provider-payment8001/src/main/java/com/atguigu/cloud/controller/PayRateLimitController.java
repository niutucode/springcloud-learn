package com.atguigu.cloud.controller;

import cn.hutool.core.util.IdUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayRateLimitController {

    @GetMapping(value = "/pay/rate_limit/{id}")
    public String rateLimit(@PathVariable("id") Integer id) {
        return "hello rateLimit input:" + id + "\t" + IdUtil.simpleUUID();
    }
}
