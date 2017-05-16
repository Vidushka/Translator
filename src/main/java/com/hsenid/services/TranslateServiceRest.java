package com.hsenid.services;

import com.google.gson.Gson;
import com.hsenid.interfaces.ITranslater;
import com.hsenid.util.Urls;
import com.hsenid.util.Words;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

/**
 * Created by Vidushka on 09/05/17.
 */
@Service
public class TranslateServiceRest implements ITranslater {

    @Autowired
    Gson gson;

    @Autowired
    Urls yandexUrl;

    String languages;
    RestTemplate rest;
    JSONObject json;
    HashMap<String, Object> languageMap;
    Words convertedWord;
    String translateJson;


    /**
     * Loading languages from Yandex API using Rest. To get Yandex getLanguage URL this
     * method use ""generateUrl method.
     * Yandex returns Json type response and here it mapped in to "result" HashMap.
     *
     * @return HashMap of languages
     */
    @Override
    public HashMap getLanguages() {
        try {
            rest = new RestTemplate();
            languages = rest.getForObject(yandexUrl.generateUrl("", "", ""), String.class);
            json = new JSONObject(languages).getJSONObject("langs");
            languageMap = new ObjectMapper().readValue(json.toString(), HashMap.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return languageMap;
    }


    /**
     * Translate the given text using Rest. Get user inputs as parameters to translate the input text.
     * Use "generateUrl()" method to get translate url of Yandex Api.
     *
     * @param inputLang     - String language code as defined in Yandex Api.(Eg. Japan - ja)
     * @param outputLang    - String language code as defined in Yandex Api.(Eg. Japan - ja)
     * @param wordToConvert - String word to convert .
     * @return Word. Gives output text value.
     */
    @Override
    public Words translate(String inputLang, String outputLang, String wordToConvert) {
        translateJson = rest.getForObject(yandexUrl.generateUrl(wordToConvert, inputLang, outputLang), String.class);
        convertedWord = gson.fromJson(translateJson, Words.class);
        return convertedWord;
    }
}
