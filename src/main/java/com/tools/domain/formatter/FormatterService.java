package com.tools.domain.formatter;

import com.tools.domain.common.result.ToolResult;

/**
 * 格式化服务接口 - 领域服务
 */
public interface FormatterService {

    /**
     * JSON格式化
     */
    ToolResult formatJson(String input, int indent);

    /**
     * JSON压缩
     */
    ToolResult compressJson(String input);

    /**
     * XML格式化
     */
    ToolResult formatXml(String input, int indent);

    /**
     * SQL美化
     */
    ToolResult formatSql(String input);
}
