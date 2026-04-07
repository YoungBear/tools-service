package com.tools.domain.encoder;

import com.tools.domain.common.result.ToolResult;

/**
 * 编码解码服务接口 - 领域服务
 */
public interface EncoderService {

    /**
     * Base64编码
     */
    ToolResult base64Encode(String input);

    /**
     * Base64解码
     */
    ToolResult base64Decode(String input);

    /**
     * URL编码
     */
    ToolResult urlEncode(String input);

    /**
     * URL解码
     */
    ToolResult urlDecode(String input);

    /**
     * HTML转义
     */
    ToolResult htmlEscape(String input);

    /**
     * HTML反转义
     */
    ToolResult htmlUnescape(String input);

    /**
     * Unicode编码
     */
    ToolResult unicodeEncode(String input);

    /**
     * Unicode解码
     */
    ToolResult unicodeDecode(String input);
}
