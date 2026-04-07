package com.tools.infrastructure.ratelimit;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 限流配置
 */
@Configuration
public class RateLimiterConfig {

    @Bean
    @ConfigurationProperties(prefix = "tools.ratelimit")
    public RateLimiterProperties rateLimiterProperties() {
        return new RateLimiterProperties();
    }

    @Bean
    public RateLimiter rateLimiter(RateLimiterProperties properties) {
        return new SlidingWindowRateLimiter(
                properties.getWindowSize(),
                properties.getMaxRequests()
        );
    }

    public static class RateLimiterProperties {
        private boolean enabled = true;
        private int windowSize = 60;
        private int maxRequests = 60;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getWindowSize() {
            return windowSize;
        }

        public void setWindowSize(int windowSize) {
            this.windowSize = windowSize;
        }

        public int getMaxRequests() {
            return maxRequests;
        }

        public void setMaxRequests(int maxRequests) {
            this.maxRequests = maxRequests;
        }
    }
}
