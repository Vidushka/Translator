package com.hsenid.services;

import com.google.gson.Gson;
import com.hsenid.interfaces.ITranslater;
import com.hsenid.util.Words;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by Vidushka on 03/05/17.
 */
@Service
public class TranslateServiceHttp implements ITranslater {

    @Autowired
    Gson gson;

    @Autowired
    Properties properties;

    JSONObject obj;
    Words convertedWord;
    BufferedReader br;
    HashMap<String, Object> result;
    Boolean langLoded = false;
    String yandexUrl;

    @Override
    public HashMap getLanguages() {
        try {
            URL url = new URL(generateUrl("", "", ""));
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

    @Override
    public Words translate(String inputLang, String outputLang, String wordToConvert) {
        try {
            URL url = new URL(generateUrl(wordToConvert, inputLang, outputLang));
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
