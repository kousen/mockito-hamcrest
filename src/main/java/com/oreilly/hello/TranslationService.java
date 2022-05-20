package com.oreilly.hello;

public interface TranslationService {

    default String translate(String text,
                             String sourceLanguage,
                             String targetLanguage) {
        return text;
    }
}
