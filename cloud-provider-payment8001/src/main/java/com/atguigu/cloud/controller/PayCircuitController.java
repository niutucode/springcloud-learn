package com.atguigu.cloud.controller;

import cn.hutool.core.util.IdUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class PayCircuitController {
    // 断路器测试
    @GetMapping(value = "/pay/circuit/{id}")
    public String payCircuit(@PathVariable("id") Integer id) {
        if (id == -4) {
            throw new RuntimeException("------ circuit id 不能为负数");
        }
        if (id == 9999) {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "hello circuit inputId: " + id + IdUtil.simpleUUID();
    }
}
