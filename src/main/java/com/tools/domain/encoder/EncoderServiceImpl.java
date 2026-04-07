package com.tools.domain.encoder;

import com.tools.domain.common.result.ToolResult;
import com.tools.domain.common.result.ToolResultFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * 编码解码服务实现 - 充血模型
 */
public class EncoderServiceImpl implements EncoderService {

    @Override
    public ToolResult base64Encode(String input) {
        if (input == null) {
            return ToolResultFactory.fail("Input cannot be null");
        }
        try {
            String encoded = java.util.Base64.getEncoder().encodeToString(input.getBytes(StandardCharsets.UTF_8));
            return ToolResultFactory.ok(encoded);
        } catch (Exception e) {
            return ToolResultFactory.fail("Base64 encoding failed: " + e.getMessage());
        }
    }

    @Override
    public ToolResult base64Decode(String input) {
        if (input == null) {
            return ToolResultFactory.fail("Input cannot be null");
        }
        try {
            byte[] decoded = java.util.Base64.getDecoder().decode(input);
            String result = new String(decoded, StandardCharsets.UTF_8);
            return ToolResultFactory.ok(result);
        } catch (Exception e) {
            return ToolResultFactory.fail("Base64 decoding failed: Invalid input");
        }
    }

    @Override
    public ToolResult urlEncode(String input) {
        if (input == null) {
            return ToolResultFactory.fail("Input cannot be null");
        }
        try {
            String encoded = URLEncoder.encode(input, StandardCharsets.UTF_8.toString());
            return ToolResultFactory.ok(encoded);
        } catch (UnsupportedEncodingException e) {
            return ToolResultFactory.fail("URL encoding failed: " + e.getMessage());
        }
    }

    @Override
    public ToolResult urlDecode(String input) {
        if (input == null) {
            return ToolResultFactory.fail("Input cannot be null");
        }
        try {
            String decoded = URLDecoder.decode(input, StandardCharsets.UTF_8.toString());
            return ToolResultFactory.ok(decoded);
        } catch (Exception e) {
            return ToolResultFactory.fail("URL decoding failed: " + e.getMessage());
        }
    }

    @Override
    public ToolResult htmlEscape(String input) {
        if (input == null) {
            return ToolResultFactory.fail("Input cannot be null");
        }
        String escaped = input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
        return ToolResultFactory.ok(escaped);
    }

    @Override
    public ToolResult htmlUnescape(String input) {
        if (input == null) {
            return ToolResultFactory.fail("Input cannot be null");
        }
        String unescaped = input
                .replace("&amp;", "&")
                .replace("&lt;", "<")
                .replace("&gt;", ">")
                .replace("&quot;", "\"")
                .replace("&#39;", "'");
        return ToolResultFactory.ok(unescaped);
    }

    @Override
    public ToolResult unicodeEncode(String input) {
        if (input == null) {
            return ToolResultFactory.fail("Input cannot be null");
        }
        StringBuilder sb = new StringBuilder();
        for (char c : input.toCharArray()) {
            sb.append(String.format("\\u%04x", (int) c));
        }
        return ToolResultFactory.ok(sb.toString());
    }

    @Override
    public ToolResult unicodeDecode(String input) {
        if (input == null) {
            return ToolResultFactory.fail("Input cannot be null");
        }
        try {
            String decoded = input.replace("\\u", "");
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < decoded.length(); i += 4) {
                String hex = decoded.substring(i, i + 4);
                sb.append((char) Integer.parseInt(hex, 16));
            }
            return ToolResultFactory.ok(sb.toString());
        } catch (Exception e) {
            return ToolResultFactory.fail("Unicode decoding failed: Invalid format");
        }
    }
}
