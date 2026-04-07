package com.tools.domain.common.result;

/**
 * 工具操作结果 - 领域层共享值对象
 */
public class ToolResult {
    private final boolean success;
    private final String data;
    private final String errorMessage;

    private ToolResult(boolean success, String data, String errorMessage) {
        this.success = success;
        this.data = data;
        this.errorMessage = errorMessage;
    }

    public static ToolResult success(String data) {
        return new ToolResult(true, data, null);
    }

    public static ToolResult error(String errorMessage) {
        return new ToolResult(false, null, errorMessage);
    }

    public boolean isSuccess() {
        return success;
    }

    public String getData() {
        return data;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
