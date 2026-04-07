package com.tools.domain.common.result;

/**
 * 工具结果工厂
 */
public final class ToolResultFactory {
    private ToolResultFactory() {
    }

    public static ToolResult ok(String data) {
        return ToolResult.success(data);
    }

    public static ToolResult fail(String message) {
        return ToolResult.error(message);
    }
}
