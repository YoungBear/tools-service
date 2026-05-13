package com.tools.web.controller;

import com.tools.web.service.I18nService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/i18n")
public class I18nController {

    private final I18nService i18nService;

    public I18nController(I18nService i18nService) {
        this.i18nService = i18nService;
    }

    @GetMapping
    public Map<String, String> getMessages(@RequestParam(defaultValue = "zh-CN") String lang) {
        return i18nService.getMessages(lang);
    }
}
