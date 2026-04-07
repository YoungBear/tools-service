package com.tools.infrastructure.ratelimit;

/**
 * 限流器接口
 */
public interface RateLimiter {

    /**
     * 判断是否允许请求
     * @param clientId 客户端标识（通常是IP地址）
     * @return true表示允许，false表示被限流
     */
    boolean isAllowed(String clientId);
}
