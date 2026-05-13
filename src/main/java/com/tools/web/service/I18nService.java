package com.tools.web.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class I18nService {

    private final MessageSource messageSource;

    @Value("${spring.messages.basename:classpath:messages}")
    private String messageBasename;

    private Set<String> allKeys = new LinkedHashSet<>();

    public I18nService(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @PostConstruct
    public void init() {
        scanAndLoadKeys();
    }

    private void scanAndLoadKeys() {
        try {
            ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            Resource[] resources = resolver.getResources("classpath:messages*.properties");

            for (Resource resource : resources) {
                String filename = resource.getFilename();
                if (filename == null) continue;

                // 从文件名提取locale (e.g., messages_en.properties -> en)
                String locale = "";
                if (filename.contains("_")) {
                    locale = filename.substring(filename.indexOf("_") + 1, filename.lastIndexOf("."));
                }

                Properties props = new Properties();
                try (InputStream is = resource.getInputStream()) {
                    props.load(is);
                }

                allKeys.addAll(props.stringPropertyNames());
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load i18n keys", e);
        }
    }

    public Map<String, String> getMessages(String lang) {
        Locale locale = Locale.forLanguageTag(lang);
        Map<String, String> messages = new LinkedHashMap<>();

        for (String key : allKeys) {
            try {
                String value = messageSource.getMessage(key, null, locale);
                messages.put(key, value);
            } catch (Exception e) {
                // 如果找不到翻译，使用key作为默认值
                messages.put(key, key);
            }
        }

        return messages;
    }

    public Set<String> getAllKeys() {
        return Collections.unmodifiableSet(allKeys);
    }
}
