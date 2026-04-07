package com.tools.application;

import com.tools.domain.common.result.ToolResult;
import com.tools.domain.common.CommonService;
import com.tools.domain.common.CommonServiceImpl;

/**
 * 通用工具应用服务 - 用例编排
 */
public class CommonApplicationService {

    private final CommonService commonService;

    public CommonApplicationService() {
        this.commonService = new CommonServiceImpl();
    }

    public ToolResult hexToRgb(String hex) {
        return commonService.hexToRgb(hex);
    }

    public ToolResult rgbToHex(int r, int g, int b) {
        return commonService.rgbToHex(r, g, b);
    }

    public ToolResult rgbToHsl(int r, int g, int b) {
        return commonService.rgbToHsl(r, g, b);
    }

    public ToolResult hslToRgb(int h, int s, int l) {
        return commonService.hslToRgb(h, s, l);
    }

    public ToolResult binaryToDecimal(String binary) {
        return commonService.binaryToDecimal(binary);
    }

    public ToolResult decimalToBinary(String decimal) {
        return commonService.decimalToBinary(decimal);
    }

    public ToolResult hexToDecimal(String hex) {
        return commonService.hexToDecimal(hex);
    }

    public ToolResult decimalToHex(String decimal) {
        return commonService.decimalToHex(decimal);
    }

    public ToolResult generateUuid() {
        return commonService.generateUuid();
    }

    public ToolResult parseJwt(String token) {
        return commonService.parseJwt(token);
    }

    public ToolResult parseCron(String cronExpression) {
        return commonService.parseCron(cronExpression);
    }

    public ToolResult testRegex(String pattern, String input) {
        return commonService.testRegex(pattern, input);
    }
}
