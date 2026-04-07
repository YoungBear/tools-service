package com.tools.domain.common;

import com.tools.domain.common.result.ToolResult;

/**
 * 通用工具服务接口 - 领域服务
 */
public interface CommonService {

    /**
     * 颜色转换 - HEX转RGB
     */
    ToolResult hexToRgb(String hex);

    /**
     * 颜色转换 - RGB转HEX
     */
    ToolResult rgbToHex(int r, int g, int b);

    /**
     * 颜色转换 - RGB转HSL
     */
    ToolResult rgbToHsl(int r, int g, int b);

    /**
     * 颜色转换 - HSL转RGB
     */
    ToolResult hslToRgb(int h, int s, int l);

    /**
     * 进制转换 - 二进制转十进制
     */
    ToolResult binaryToDecimal(String binary);

    /**
     * 进制转换 - 十进制转二进制
     */
    ToolResult decimalToBinary(String decimal);

    /**
     * 进制转换 - 十六进制转十进制
     */
    ToolResult hexToDecimal(String hex);

    /**
     * 进制转换 - 十进制转十六进制
     */
    ToolResult decimalToHex(String decimal);

    /**
     * 生成UUID
     */
    ToolResult generateUuid();

    /**
     * JWT解析
     */
    ToolResult parseJwt(String token);

    /**
     * Cron表达式解析
     */
    ToolResult parseCron(String cronExpression);

    /**
     * 正则表达式测试
     */
    ToolResult testRegex(String pattern, String input);
}
