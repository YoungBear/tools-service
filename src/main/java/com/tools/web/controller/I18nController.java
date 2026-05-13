package com.tools.web.controller;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/i18n")
public class I18nController {

    private final MessageSource messageSource;

    public I18nController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @GetMapping
    public Map<String, String> getMessages(@RequestParam(defaultValue = "zh-CN") String lang) {
        Locale locale = Locale.forLanguageTag(lang);
        Map<String, String> messages = new HashMap<>();

        String[] keys = {
            "app.title",
            "sidebar.tools",
            "content.title", "content.subtitle",
            "footer.poweredBy", "footer.year",
            "nav.encoder", "nav.datetime", "nav.formatter", "nav.crypto", "nav.common",
            "action.execute", "action.clear", "action.copy",
            "placeholder.input", "placeholder.output", "placeholder.none",
            "theme.light", "theme.dark", "theme.switchToLight", "theme.switchToDark",
            "message.inputRequired", "message.success", "message.error", "message.rateLimit", "message.copied", "message.copyFailed",
            "tool.base64Encode", "tool.base64Decode", "tool.urlEncode", "tool.urlDecode",
            "tool.htmlEscape", "tool.htmlUnescape", "tool.unicodeEncode", "tool.unicodeDecode",
            "tool.currentTimestamp", "tool.timestampToDate", "tool.dateToTimestamp",
            "tool.formatJson", "tool.compressJson", "tool.formatXml", "tool.formatSql",
            "tool.md5", "tool.sha1", "tool.sha256", "tool.aesEncrypt", "tool.aesDecrypt",
            "tool.desEncrypt", "tool.desDecrypt", "tool.rsaGenerateKey",
            "tool.hexToRgb", "tool.rgbToHex", "tool.generateUuid", "tool.parseJwt", "tool.parseCron", "tool.testRegex",
            "placeholder.base64Encode", "placeholder.base64Decode", "placeholder.urlEncode", "placeholder.urlDecode",
            "placeholder.htmlEscape", "placeholder.htmlUnescape", "placeholder.unicodeEncode", "placeholder.unicodeDecode",
            "placeholder.timestamp", "placeholder.date", "placeholder.dateFormat",
            "placeholder.json", "placeholder.xml", "placeholder.sql",
            "placeholder.crypto", "placeholder.ciphertext", "placeholder.key",
            "placeholder.hexColor", "placeholder.rgb", "placeholder.r", "placeholder.g", "placeholder.b",
            "placeholder.jwt", "placeholder.cron", "placeholder.pattern", "placeholder.regex",
            "label.format", "label.key", "label.r", "label.g", "label.b", "label.pattern"
        };

        for (String key : keys) {
            messages.put(key, messageSource.getMessage(key, null, locale));
        }

        return messages;
    }
}
