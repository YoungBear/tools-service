package com.tools.web.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/i18n")
public class I18nController {

    private static final Map<String, Map<String, String>> TRANSLATIONS = new HashMap<>();

    static {
        // Chinese translations
        Map<String, String> zh = new HashMap<>();
        zh.put("app.title", "在线工具箱");
        zh.put("nav.encoder", "编码解码");
        zh.put("nav.datetime", "日期时间");
        zh.put("nav.formatter", "格式化");
        zh.put("nav.crypto", "加解密");
        zh.put("nav.common", "其他工具");
        zh.put("action.execute", "执行");
        zh.put("action.clear", "清空");
        zh.put("action.copy", "复制");
        zh.put("placeholder.input", "请输入内容");
        zh.put("placeholder.output", "输出结果");
        zh.put("placeholder.none", "点击执行按钮即可");
        zh.put("theme.light", "浅色");
        zh.put("theme.dark", "深色");
        zh.put("tool.base64Encode", "Base64编码");
        zh.put("tool.base64Decode", "Base64解码");
        zh.put("tool.urlEncode", "URL编码");
        zh.put("tool.urlDecode", "URL解码");
        zh.put("tool.htmlEscape", "HTML转义");
        zh.put("tool.htmlUnescape", "HTML反转义");
        zh.put("tool.unicodeEncode", "Unicode编码");
        zh.put("tool.unicodeDecode", "Unicode解码");
        zh.put("tool.currentTimestamp", "当前时间戳");
        zh.put("tool.timestampToDate", "时间戳转日期");
        zh.put("tool.dateToTimestamp", "日期转时间戳");
        zh.put("tool.formatJson", "JSON格式化");
        zh.put("tool.compressJson", "JSON压缩");
        zh.put("tool.formatXml", "XML格式化");
        zh.put("tool.formatSql", "SQL美化");
        zh.put("tool.md5", "MD5哈希");
        zh.put("tool.sha1", "SHA-1哈希");
        zh.put("tool.sha256", "SHA-256哈希");
        zh.put("tool.aesEncrypt", "AES加密");
        zh.put("tool.aesDecrypt", "AES解密");
        zh.put("tool.desEncrypt", "DES加密");
        zh.put("tool.desDecrypt", "DES解密");
        zh.put("tool.rsaGenerateKey", "生成RSA密钥对");
        zh.put("tool.hexToRgb", "HEX转RGB");
        zh.put("tool.rgbToHex", "RGB转HEX");
        zh.put("tool.generateUuid", "生成UUID");
        zh.put("tool.parseJwt", "JWT解析");
        zh.put("tool.parseCron", "Cron解析");
        zh.put("tool.testRegex", "正则测试");
        zh.put("placeholder.base64Encode", "请输入要编码的文本");
        zh.put("placeholder.base64Decode", "请输入要解码的Base64");
        zh.put("placeholder.urlEncode", "请输入要编码的URL");
        zh.put("placeholder.urlDecode", "请输入要解码的URL");
        zh.put("placeholder.htmlEscape", "请输入要转义的HTML");
        zh.put("placeholder.htmlUnescape", "请输入要反转义的内容");
        zh.put("placeholder.unicodeEncode", "请输入要编码的中文");
        zh.put("placeholder.unicodeDecode", "请输入要解码的Unicode");
        zh.put("placeholder.timestamp", "请输入时间戳");
        zh.put("placeholder.date", "请输入日期");
        zh.put("placeholder.dateFormat", "yyyy-MM-dd HH:mm:ss");
        zh.put("placeholder.json", "请输入JSON");
        zh.put("placeholder.xml", "请输入XML");
        zh.put("placeholder.sql", "请输入SQL");
        zh.put("placeholder.crypto", "请输入要哈希的文本");
        zh.put("placeholder.ciphertext", "请输入要解密的密文");
        zh.put("placeholder.key", "请输入密钥");
        zh.put("placeholder.hexColor", "请输入HEX颜色值，如#FF0000");
        zh.put("placeholder.rgb", "请输入RGB值，如255,0,0");
        zh.put("placeholder.r", "R");
        zh.put("placeholder.g", "G");
        zh.put("placeholder.b", "B");
        zh.put("placeholder.jwt", "请输入JWT Token");
        zh.put("placeholder.cron", "请输入Cron表达式，如0 0 * * * ?");
        zh.put("placeholder.pattern", "请输入正则表达式");
        zh.put("placeholder.regex", "请输入要匹配的文本");
        zh.put("label.format", "日期格式");
        zh.put("label.key", "密钥");
        zh.put("label.r", "R");
        zh.put("label.g", "G");
        zh.put("label.b", "B");
        zh.put("label.pattern", "正则表达式");
        TRANSLATIONS.put("zh-CN", zh);

        // English translations
        Map<String, String> en = new HashMap<>();
        en.put("app.title", "Online Tools");
        en.put("nav.encoder", "Encoder/Decoder");
        en.put("nav.datetime", "Date/Time");
        en.put("nav.formatter", "Formatter");
        en.put("nav.crypto", "Cryptography");
        en.put("nav.common", "Common Tools");
        en.put("action.execute", "Execute");
        en.put("action.clear", "Clear");
        en.put("action.copy", "Copy");
        en.put("placeholder.input", "Enter input");
        en.put("placeholder.output", "Output");
        en.put("placeholder.none", "Click Execute to continue");
        en.put("theme.light", "Light");
        en.put("theme.dark", "Dark");
        en.put("tool.base64Encode", "Base64 Encode");
        en.put("tool.base64Decode", "Base64 Decode");
        en.put("tool.urlEncode", "URL Encode");
        en.put("tool.urlDecode", "URL Decode");
        en.put("tool.htmlEscape", "HTML Escape");
        en.put("tool.htmlUnescape", "HTML Unescape");
        en.put("tool.unicodeEncode", "Unicode Encode");
        en.put("tool.unicodeDecode", "Unicode Decode");
        en.put("tool.currentTimestamp", "Current Timestamp");
        en.put("tool.timestampToDate", "Timestamp to Date");
        en.put("tool.dateToTimestamp", "Date to Timestamp");
        en.put("tool.formatJson", "Format JSON");
        en.put("tool.compressJson", "Compress JSON");
        en.put("tool.formatXml", "Format XML");
        en.put("tool.formatSql", "Format SQL");
        en.put("tool.md5", "MD5 Hash");
        en.put("tool.sha1", "SHA-1 Hash");
        en.put("tool.sha256", "SHA-256 Hash");
        en.put("tool.aesEncrypt", "AES Encrypt");
        en.put("tool.aesDecrypt", "AES Decrypt");
        en.put("tool.desEncrypt", "DES Encrypt");
        en.put("tool.desDecrypt", "DES Decrypt");
        en.put("tool.rsaGenerateKey", "Generate RSA Key Pair");
        en.put("tool.hexToRgb", "HEX to RGB");
        en.put("tool.rgbToHex", "RGB to HEX");
        en.put("tool.generateUuid", "Generate UUID");
        en.put("tool.parseJwt", "Parse JWT");
        en.put("tool.parseCron", "Parse Cron");
        en.put("tool.testRegex", "Regex Test");
        en.put("placeholder.base64Encode", "Enter text to encode");
        en.put("placeholder.base64Decode", "Enter Base64 to decode");
        en.put("placeholder.urlEncode", "Enter URL to encode");
        en.put("placeholder.urlDecode", "Enter URL to decode");
        en.put("placeholder.htmlEscape", "Enter HTML to escape");
        en.put("placeholder.htmlUnescape", "Enter content to unescape");
        en.put("placeholder.unicodeEncode", "Enter Chinese text to encode");
        en.put("placeholder.unicodeDecode", "Enter Unicode to decode");
        en.put("placeholder.timestamp", "Enter timestamp");
        en.put("placeholder.date", "Enter date");
        en.put("placeholder.dateFormat", "yyyy-MM-dd HH:mm:ss");
        en.put("placeholder.json", "Enter JSON");
        en.put("placeholder.xml", "Enter XML");
        en.put("placeholder.sql", "Enter SQL");
        en.put("placeholder.crypto", "Enter text to hash");
        en.put("placeholder.ciphertext", "Enter ciphertext to decrypt");
        en.put("placeholder.key", "Enter key");
        en.put("placeholder.hexColor", "Enter HEX color, e.g. #FF0000");
        en.put("placeholder.rgb", "Enter RGB values, e.g. 255,0,0");
        en.put("placeholder.r", "R");
        en.put("placeholder.g", "G");
        en.put("placeholder.b", "B");
        en.put("placeholder.jwt", "Enter JWT Token");
        en.put("placeholder.cron", "Enter Cron expression, e.g. 0 0 * * * ?");
        en.put("placeholder.pattern", "Enter regex pattern");
        en.put("placeholder.regex", "Enter text to match");
        en.put("label.format", "Date Format");
        en.put("label.key", "Key");
        en.put("label.r", "R");
        en.put("label.g", "G");
        en.put("label.b", "B");
        en.put("label.pattern", "Regex Pattern");
        TRANSLATIONS.put("en-US", en);
    }

    @GetMapping
    public Map<String, String> getMessages(@RequestParam(defaultValue = "zh-CN") String lang) {
        return TRANSLATIONS.getOrDefault(lang, TRANSLATIONS.get("zh-CN"));
    }
}
