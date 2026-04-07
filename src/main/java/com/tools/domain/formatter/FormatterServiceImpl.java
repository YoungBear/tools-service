package com.tools.domain.formatter;

import com.tools.domain.common.result.ToolResult;
import com.tools.domain.common.result.ToolResultFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * 格式化服务实现 - 充血模型
 */
public class FormatterServiceImpl implements FormatterService {

    private final ObjectMapper objectMapper;
    private final XmlMapper xmlMapper;

    public FormatterServiceImpl() {
        this.objectMapper = new ObjectMapper();
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public ToolResult formatJson(String input, int indent) {
        if (input == null || input.isEmpty()) {
            return ToolResultFactory.fail("Input cannot be null or empty");
        }
        try {
            JsonNode node = objectMapper.readTree(input);
            String formatted;
            if (indent <= 0) {
                formatted = objectMapper.writeValueAsString(node);
            } else {
                formatted = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(node);
            }
            return ToolResultFactory.ok(formatted);
        } catch (Exception e) {
            return ToolResultFactory.fail("JSON formatting failed: Invalid JSON");
        }
    }

    @Override
    public ToolResult compressJson(String input) {
        if (input == null || input.isEmpty()) {
            return ToolResultFactory.fail("Input cannot be null or empty");
        }
        try {
            JsonNode node = objectMapper.readTree(input);
            String compressed = objectMapper.writeValueAsString(node);
            return ToolResultFactory.ok(compressed);
        } catch (Exception e) {
            return ToolResultFactory.fail("JSON compression failed: Invalid JSON");
        }
    }

    @Override
    public ToolResult formatXml(String input, int indent) {
        if (input == null || input.isEmpty()) {
            return ToolResultFactory.fail("Input cannot be null or empty");
        }
        try {
            StringBuilder sb = new StringBuilder();
            formatXmlNode(sb, input.trim(), 0, indent);
            return ToolResultFactory.ok(sb.toString());
        } catch (Exception e) {
            return ToolResultFactory.fail("XML formatting failed: " + e.getMessage());
        }
    }

    @Override
    public ToolResult formatSql(String input) {
        if (input == null || input.isEmpty()) {
            return ToolResultFactory.fail("Input cannot be null or empty");
        }
        try {
            String formatted = input
                    .replaceAll("\\s+", " ")
                    .replaceAll(",\\s*", ",\n  ")
                    .replaceAll("\\bSELECT\\b", "\nSELECT")
                    .replaceAll("\\bFROM\\b", "\nFROM")
                    .replaceAll("\\bWHERE\\b", "\nWHERE")
                    .replaceAll("\\bAND\\b", "\n  AND")
                    .replaceAll("\\bOR\\b", "\n  OR")
                    .replaceAll("\\bJOIN\\b", "\nJOIN")
                    .replaceAll("\\bLEFT JOIN\\b", "\nLEFT JOIN")
                    .replaceAll("\\bRIGHT JOIN\\b", "\nRIGHT JOIN")
                    .replaceAll("\\bINNER JOIN\\b", "\nINNER JOIN")
                    .replaceAll("\\bON\\b", "\n  ON")
                    .replaceAll("\\bGROUP BY\\b", "\nGROUP BY")
                    .replaceAll("\\bORDER BY\\b", "\nORDER BY")
                    .replaceAll("\\bHAVING\\b", "\nHAVING")
                    .replaceAll("\\bLIMIT\\b", "\nLIMIT")
                    .trim();
            return ToolResultFactory.ok(formatted);
        } catch (Exception e) {
            return ToolResultFactory.fail("SQL formatting failed: " + e.getMessage());
        }
    }

    private void formatXmlNode(StringBuilder sb, String xml, int depth, int indent) {
        String indentStr = " ".repeat(Math.max(indent, 2));
        String currentIndent = indentStr.repeat(depth);

        // 简单的XML格式化
        StringBuilder content = new StringBuilder();
        int i = 0;
        while (i < xml.length()) {
            if (xml.charAt(i) == '<') {
                int closeIndex = xml.indexOf('>', i);
                if (closeIndex == -1) break;
                String tag = xml.substring(i, closeIndex + 1);

                if (tag.startsWith("</")) {
                    sb.append(currentIndent).append(tag).append("\n");
                } else if (tag.endsWith("/>")) {
                    sb.append(currentIndent).append(tag).append("\n");
                } else {
                    sb.append(currentIndent).append(tag).append("\n");
                }
                i = closeIndex + 1;
            } else {
                int j = i;
                while (j < xml.length() && xml.charAt(j) != '<') j++;
                String text = xml.substring(i, j).trim();
                if (!text.isEmpty()) {
                    sb.append(currentIndent).append(text).append("\n");
                }
                i = j;
            }
        }
    }
}
