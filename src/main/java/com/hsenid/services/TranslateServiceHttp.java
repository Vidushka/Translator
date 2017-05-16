package com.hsenid.services;

import com.google.gson.Gson;
import com.hsenid.interfaces.ITranslater;
import com.hsenid.util.Urls;
import com.hsenid.util.Words;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Vidushka on 03/05/17.
 */
@Service
public class TranslateServiceHttp implements ITranslater {

    @Autowired
    Gson gson;

    @Autowired
    Urls yandexUrl;

    JSONObject obj;
    Words convertedWord;
    BufferedReader br;
    HashMap<String, Object> result;

    /**
     * Loading languages from Yandex API using Http client. To get Yandex getLanguage URL this
     * method use ""generateUrl method.
     * Yandex returns Json type response and here it mapped in to "result" HashMap.
     *
     * @return HashMap
     */
    @Override
    public HashMap getLanguages() {
        try {
            URL url = new URL(yandexUrl.generateUrl("", "", ""));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            obj = new JSONObject(br.readLine()).getJSONObject("langs");
            result = new ObjectMapper().readValue(obj.toString(), HashMap.class);

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Translate the given text using Http client. Get user inputs as parameters to translate the input text.
     * Use "generateUrl()" method to get translate url of Yandex Api.
     *
     * @param inputLang     - String language code as defined in Yandex Api.(Eg. Japan - ja)
     * @param outputLang    - String language code as defined in Yandex Api.(Eg. Japan - ja)
     * @param wordToConvert - String word to convert .
     * @return Word. Gives output text value.
     */
    @Override
    public Words translate(String inputLang, String outputLang, String wordToConvert) {
        try {
            URL url = new URL(yandexUrl.generateUrl(wordToConvert, inputLang, outputLang));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));
            convertedWord = gson.fromJson(br.readLine(), Words.class);
            conn.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return convertedWord;
    }

}
