package com.hsenid.util;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.Properties;

/**
 * Created by Vidushka on 16/05/17.
 */
public class Urls {

    @Autowired
    Properties properties;

    Boolean langLoded = false;
    String yandexUrl;

    /**
     * Generate translate and getLang Url. Loads base Url from properties file and change it using String formatter.
     *
     * @param param1 Optional. Use to generate translate Url. Provide input text to translate.
     * @param param2 Optional. Use to generate translate Url. Provide from language to translate.
     * @param param3 Optional. Use to generate translate Url. Provide to language to translate.
     * @return String Url
     */
    public String generateUrl(String param1, String param2, String param3) {
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("application.properties"));
            if (!langLoded) {
                yandexUrl = String.format(properties.getProperty("yandexUrl"), "getLangs", "ui=en", "", "", "", "", "");
                langLoded = true;
            } else {
                yandexUrl = String.format(properties.getProperty("yandexUrl"), "translate", "text=", param1, "&lang=", param2, "-", param3);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return yandexUrl;
    }
}
