package com.sononio.bostongene.translate.translator;

import java.util.List;

/**
 * Container for translator api response.
 */
public class TranslatorResponse {
    private Integer code;
    private String lang;
    private List<String> text;

    public TranslatorResponse() {
    }

    public TranslatorResponse(Integer code, String lang, List<String> text) {
        this.code = code;
        this.lang = lang;
        this.text = text;
    }


    public Integer getCode() {
        return code;
    }

    public String getLang() {
        return lang;
    }

    public List<String> getText() {
        return text;
    }
}
