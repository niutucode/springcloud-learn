package com.atguigu.cloud.gateway;

import cn.hutool.core.util.StrUtil;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

@Component
public class MyRoutePredicateFactory extends AbstractRoutePredicateFactory<MyRoutePredicateFactory.Config> {

    public MyRoutePredicateFactory() {
        super(MyRoutePredicateFactory.Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Collections.singletonList("userType");
    }

    @Override
    public Predicate<ServerWebExchange> apply(MyRoutePredicateFactory.Config config) {
        return serverWebExchange -> {
            String userType = serverWebExchange.getRequest().getQueryParams().getFirst("userType");
            return StrUtil.equalsIgnoreCase(userType, config.getUserType());
        };
    }

    @Data
    @Validated
    public static class Config {
        @NotEmpty
        private String userType;
    }
}
