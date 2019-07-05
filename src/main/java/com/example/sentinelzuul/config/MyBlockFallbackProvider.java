package com.example.sentinelzuul.config;

import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.BlockResponse;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.DefaultBlockFallbackProvider;
import com.alibaba.csp.sentinel.adapter.gateway.zuul.fallback.ZuulBlockFallbackProvider;
import com.alibaba.csp.sentinel.log.RecordLog;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author wuweifeng wrote on 2019/7/3.
 */
public class MyBlockFallbackProvider implements ZuulBlockFallbackProvider {

    private Logger logger = LoggerFactory.getLogger(DefaultBlockFallbackProvider.class);

    // you can define route as service level
    @Override
    public String getRoute() {
        return "baobao_api";
    }

    @Override
    public BlockResponse fallbackResponse(String route, Throwable cause) {
        RecordLog.info(String.format("[Sentinel DefaultBlockFallbackProvider] Run fallback route: %s", route));
        if (cause instanceof BlockException) {
            return new BlockResponse(429, "the route is blocked", route);
        } else {
            return new BlockResponse(500, "System Error", route);
        }
    }
}

