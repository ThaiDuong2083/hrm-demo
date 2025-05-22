package com.example.apus_hrm_demo.util.i18n;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class GenerateMessage {
    private final MessageSource messageSource;

    public String getMessage(String code, Locale locale) {
        return messageSource.getMessage(code,null, locale);
    }
}
