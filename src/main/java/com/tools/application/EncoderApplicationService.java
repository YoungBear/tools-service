package com.tools.application;

import com.tools.domain.common.result.ToolResult;
import com.tools.domain.encoder.EncoderService;
import com.tools.domain.encoder.EncoderServiceImpl;

/**
 * 编码解码应用服务 - 用例编排
 */
public class EncoderApplicationService {

    private final EncoderService encoderService;

    public EncoderApplicationService() {
        this.encoderService = new EncoderServiceImpl();
    }

    public ToolResult base64Encode(String input) {
        return encoderService.base64Encode(input);
    }

    public ToolResult base64Decode(String input) {
        return encoderService.base64Decode(input);
    }

    public ToolResult urlEncode(String input) {
        return encoderService.urlEncode(input);
    }

    public ToolResult urlDecode(String input) {
        return encoderService.urlDecode(input);
    }

    public ToolResult htmlEscape(String input) {
        return encoderService.htmlEscape(input);
    }

    public ToolResult htmlUnescape(String input) {
        return encoderService.htmlUnescape(input);
    }

    public ToolResult unicodeEncode(String input) {
        return encoderService.unicodeEncode(input);
    }

    public ToolResult unicodeDecode(String input) {
        return encoderService.unicodeDecode(input);
    }
}
