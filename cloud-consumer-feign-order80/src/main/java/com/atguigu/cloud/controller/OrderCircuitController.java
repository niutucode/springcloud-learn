package com.atguigu.cloud.controller;

import com.atguigu.cloud.apis.PayFeignApi;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class OrderCircuitController {

    @Resource
    private PayFeignApi payFeignApi;

    @GetMapping(value = "/feign/pay/circuit/{id}")
    @CircuitBreaker(name = "cloud-payment-service", fallbackMethod = "circuitBreakerFallBack")
    public String circuitBreaker(@PathVariable("id") Integer id) {
        return payFeignApi.payCircuit(id);
    }

    public String circuitBreakerFallBack(Integer id, Throwable throwable) {
        return "circuitBreakerFallBack, 系统繁忙，请稍后再试！";
    }

    @GetMapping(value = "/feign/pay/bulkhead/semaphore/{id}")
    @Bulkhead(name = "cloud-payment-service", fallbackMethod = "bulkheadSemaphoreFallBack", type = Bulkhead.Type.SEMAPHORE)
    public String bulkHeadSemaphore(@PathVariable("id") Integer id) {
        System.out.println("===========================");
        return payFeignApi.payBulkhead(id);
    }

    public String bulkheadSemaphoreFallBack(Integer id, Throwable throwable) {
        return "bulkHeadFallBack, 系统繁忙，请稍后再试！";
    }

    @GetMapping(value = "/feign/pay/bulkhead/threadpool/{id}")
    @Bulkhead(name = "cloud-payment-service", fallbackMethod = "bulkheadThreadPoolFallBack", type = Bulkhead.Type.THREADPOOL)
    public CompletableFuture<String> bulkheadThreadPool(@PathVariable("id") Integer id) {
        log.info("{}开始进入", Thread.currentThread().getName() + "\t");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
           e.printStackTrace();
        }
        log.info("{}准备离开", Thread.currentThread().getName() + "\t");
        return CompletableFuture.supplyAsync(() -> payFeignApi.payBulkhead(id) + "\t" + "Bulkhead.Type.THREADPOOL");
    }

    public CompletableFuture<String> bulkheadThreadPoolFallBack(Integer id, Throwable throwable) {
        return CompletableFuture.supplyAsync(() -> "Bulkhead.Type.THREADPOOL,系统繁忙，请稍后再试");
    }

    @GetMapping(value = "/feign/pay/rate_limit/{id}")
    @RateLimiter(name = "cloud-payment-service", fallbackMethod = "rateLimitFallback")
    public String myBulkhead(@PathVariable("id") Integer id) {
        return payFeignApi.rateLimit(id);
    }

    public String rateLimitFallback(Integer id, Throwable t) {
        return "你被限流了，禁止访问/(ㄒoㄒ)/~~";
    }


}
