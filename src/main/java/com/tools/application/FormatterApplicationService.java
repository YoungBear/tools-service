package com.tools.application;

import com.tools.domain.common.result.ToolResult;
import com.tools.domain.formatter.FormatterService;
import com.tools.domain.formatter.FormatterServiceImpl;

/**
 * 格式化应用服务 - 用例编排
 */
public class FormatterApplicationService {

    private final FormatterService formatterService;

    public FormatterApplicationService() {
        this.formatterService = new FormatterServiceImpl();
    }

    public ToolResult formatJson(String input, int indent) {
        return formatterService.formatJson(input, indent);
    }

    public ToolResult compressJson(String input) {
        return formatterService.compressJson(input);
    }

    public ToolResult formatXml(String input, int indent) {
        return formatterService.formatXml(input, indent);
    }

    public ToolResult formatSql(String input) {
        return formatterService.formatSql(input);
    }
}
