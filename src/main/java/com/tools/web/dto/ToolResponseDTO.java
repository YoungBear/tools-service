package com.tools.web.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ToolResponseDTO {
    private boolean success;
    private Object data;
    private String message;
    private ErrorInfo error;
    private long timestamp;

    public ToolResponseDTO() {
        this.timestamp = System.currentTimeMillis();
    }

    public static ToolResponseDTO success(Object data) {
        ToolResponseDTO response = new ToolResponseDTO();
        response.setSuccess(true);
        response.setData(data);
        return response;
    }

    public static ToolResponseDTO error(String code, String message) {
        ToolResponseDTO response = new ToolResponseDTO();
        response.setSuccess(false);
        response.setError(new ErrorInfo(code, message));
        return response;
    }

    public boolean isSuccess() { return success; }
    public void setSuccess(boolean success) { this.success = success; }
    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public ErrorInfo getError() { return error; }
    public void setError(ErrorInfo error) { this.error = error; }
    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }

    public static class ErrorInfo {
        private String code;
        private String message;
        public ErrorInfo(String code, String message) {
            this.code = code;
            this.message = message;
        }
        public String getCode() { return code; }
        public void setCode(String code) { this.code = code; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}
