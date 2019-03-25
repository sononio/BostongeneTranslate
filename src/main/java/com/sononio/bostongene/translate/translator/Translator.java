package com.sononio.bostongene.translate.translator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sononio.bostongene.translate.util.Http;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Translates from any language to any.
 * Uses <a href="https://translate.yandex.ru">Yandex Translator</a>.
 * Docs are available <a href="https://tech.yandex.ru/translate/doc/dg/concepts/about-docpage/">here</a>.
 */
public class Translator {
    // Now all constant are here, but its not very good
    // The best variant is move it to config file
    private static final String DEFAULT_LANG = "ru";
    private static final String API_URL = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    private static final String API_KEY = "trnsl.1.1.20190325T171724Z.da274aae7efd334e.c7df90562836487522087d0ca3212c814e4f79d3";

    /**
     * Translates text from one language to another.
     * @param text text to translate.
     * @param fromLang language abbreviation. For example "English" -> "en"
     * @param toLang language abbreviation. For example "English" -> "en"
     * @return text translation
     * @throws TranslatorException if error in yandex api has occurred
     */
    public String translate(String text, String fromLang, String toLang) throws TranslatorException {
        // Have to build http request to yandex API
        // Docs are available here: https://tech.yandex.ru/translate/doc/dg/concepts/about-docpage

        // Static part of request
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        headers.put("Content-Type", "application/x-www-form-urlencoded");
        params.put("lang", String.format("%s-%s", fromLang, toLang));
        params.put("key", API_KEY);

        // Dynamic part of request
        String body = String.format("text=%s", text);

        //Trying to send request to yandex API
        try {
            String res = Http.post(API_URL, body, headers, params);

            // If success then parse result and return translated text
            ObjectMapper objectMapper = new ObjectMapper();
            TranslatorResponse translatorResponse = objectMapper.readValue(res, TranslatorResponse.class);
            return translatorResponse.getText().get(0);

        } catch (NoSuchAlgorithmException | KeyManagementException | KeyStoreException | IOException e) {
            throw new TranslatorException(e.getMessage());
        }
    }

    /**
     * Translates text from one language to default language (russian).
     * @param text text to translate.
     * @param fromLang language abbreviation. For example "English" -> "en"
     * @return text translation
     * @throws TranslatorException if error in yandex api has occurred
     */
    public String translate(String text, String fromLang) throws TranslatorException {
        return translate(text, fromLang, DEFAULT_LANG);
    }
}
