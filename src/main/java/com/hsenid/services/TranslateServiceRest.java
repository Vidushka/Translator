package com.hsenid.services;

import com.google.gson.Gson;
import com.hsenid.interfaces.ITranslater;
import com.hsenid.util.Words;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Vidushka on 09/05/17.
 */
@Service
public class TranslateServiceRest implements ITranslater {

    @Autowired
    Gson gson;

    @Autowired
    Properties properties;

    String languages;
    RestTemplate rest;
    JSONObject json;
    HashMap<String, Object> languageMap;
    Words convertedWord;
    String translateJson;
    Boolean langLoded = false;
    String yandexUrl;

    @Override
    public HashMap getLanguages() {
        try {
            rest = new RestTemplate();
            languages = rest.getForObject(generateUrl("", "", ""), String.class);
            json = new JSONObject(languages).getJSONObject("langs");
            languageMap = new ObjectMapper().readValue(json.toString(), HashMap.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return languageMap;
    }

    @Override
    public Words translate(String inputLang, String outputLang, String wordToConvert) {
        translateJson = rest.getForObject(generateUrl(wordToConvert, inputLang, outputLang), String.class);
        convertedWord = gson.fromJson(translateJson, Words.class);
        return convertedWord;
    }

    @Override
    public String generateUrl(String param1, String param2, String param3) {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            if (!langLoded) {
                yandexUrl = String.format(properties.getProperty("yandexUrl"), "getLangs", "ui=en", "", "", "", "", "");
                langLoded = true;
            } else {
                yandexUrl = String.format(properties.getProperty("yandexUrl"), "translate", "text=", param1, "&lang=", param2, "-", param3);
                langLoded = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return yandexUrl;
    }
}
