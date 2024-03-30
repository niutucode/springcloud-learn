package com.atguigu.cloud.apis;

import com.atguigu.cloud.entities.PayDTO;
import com.atguigu.cloud.resp.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "cloud-payment-service")
public interface PayFeignApi {

    @PostMapping(value = "/pay/add")
    ResultData<String> addPay(@RequestBody PayDTO payDTO);

    @GetMapping(value = "/pay/get/{id}")
    ResultData<PayDTO> getPayInfo(@PathVariable("id") Integer id);

    @GetMapping(value = "/pay/get/info")
    String mylb();

    @GetMapping(value = "/pay/circuit/{id}")
    String payCircuit(@PathVariable("id") Integer id);

    @GetMapping(value = "/pay/bulkhead/{id}")
    String payBulkhead(@PathVariable("id") Integer id);

    @GetMapping(value = "/pay/rate_limit/{id}")
    String rateLimit(@PathVariable("id") Integer id);

    /**
     * Micrometer(Sleuth)进行链路监控的例子
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/pay/micrometer/{id}")
    String myMicrometer(@PathVariable("id") Integer id);
}
