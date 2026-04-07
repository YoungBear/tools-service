package com.tools.infrastructure.ratelimit;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLongArray;

/**
 * 滑动窗口限流器实现
 * 使用固定大小的数组记录每个子窗口的请求数
 */
public class SlidingWindowRateLimiter implements RateLimiter {

    private final int windowSizeInSeconds;
    private final int maxRequests;
    private final ConcurrentHashMap<String, SlidingWindowCounter> counters;

    public SlidingWindowRateLimiter(int windowSizeInSeconds, int maxRequests) {
        this.windowSizeInSeconds = windowSizeInSeconds;
        this.maxRequests = maxRequests;
        this.counters = new ConcurrentHashMap<>();
    }

    @Override
    public boolean isAllowed(String clientId) {
        SlidingWindowCounter counter = counters.computeIfAbsent(clientId,
                k -> new SlidingWindowCounter(windowSizeInSeconds));
        return counter.tryAcquire(maxRequests);
    }

    /**
     * 滑动窗口计数器
     */
    private static class SlidingWindowCounter {
        private final AtomicLongArray slots;
        private final int slotCount;
        private volatile long lastSlotUpdate;

        SlidingWindowCounter(int slotCount) {
            this.slotCount = slotCount;
            this.slots = new AtomicLongArray(slotCount);
            this.lastSlotUpdate = System.currentTimeMillis() / 1000;
        }

        boolean tryAcquire(int maxRequests) {
            long currentSecond = System.currentTimeMillis() / 1000;
            int currentSlot = (int) (currentSecond % slotCount);

            // 如果时间前进，需要滑动窗口
            if (currentSecond != lastSlotUpdate) {
                slideWindow(currentSecond);
                lastSlotUpdate = currentSecond;
            }

            // 计算当前窗口内的总请求数
            long totalRequests = 0;
            for (int i = 0; i < slotCount; i++) {
                totalRequests += slots.get(i);
            }

            if (totalRequests >= maxRequests) {
                return false;
            }

            slots.incrementAndGet(currentSlot);
            return true;
        }

        private void slideWindow(long currentSecond) {
            int oldSlot = (int) (lastSlotUpdate % slotCount);
            int newSlot = (int) (currentSecond % slotCount);

            // 只有在槽位变化时才清除旧槽位
            if (oldSlot != newSlot) {
                slots.set(oldSlot, 0);
            }
        }
    }
}
