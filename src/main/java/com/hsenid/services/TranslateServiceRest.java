package com.hsenid.services;

import com.google.gson.Gson;
import com.hsenid.interfaces.ITranslater;
import com.hsenid.util.Words;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by Vidushka on 09/05/17.
 */
@Service
public class TranslateServiceRest implements ITranslater {
    String languages;
    RestTemplate rest;
    JSONObject json;
    HashMap<String, Object> languageMap;
    Words convertedWord;
    String translateJson;
    Gson gson = new Gson();

    @Override
    public HashMap getLanguages() {
        try {
            String getLanguageUrl = "https://translate.yandex.net/api/v1.5/tr.json/getLangs?key=trnsl.1.1.20170503T091519Z.9f30c24402100dfb.91f7ddaca07e07cddb27fd1cd769dd2b43d5c765&ui=en";
            rest = new RestTemplate();
            languages = rest.getForObject(getLanguageUrl, String.class);
            json = new JSONObject(languages).getJSONObject("langs");
            languageMap = new ObjectMapper().readValue(json.toString(), HashMap.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return languageMap;
    }

    @Override
    public Words translate(String inputLang, String outputLang, String wordToConvert) {
        String translateUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20170503T091519Z.9f30c24402100dfb.91f7ddaca07e07cddb27fd1cd769dd2b43d5c765&text="
                + wordToConvert + "&lang=" + inputLang + "-" + outputLang;
        translateJson = rest.getForObject(translateUrl, String.class);
        convertedWord = gson.fromJson(translateJson, Words.class);
        return convertedWord;
    }
}
