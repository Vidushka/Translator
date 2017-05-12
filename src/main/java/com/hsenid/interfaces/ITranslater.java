package com.hsenid.interfaces;

import com.hsenid.util.Words;

import java.util.HashMap;

/**
 * Created by Vidushka on 09/05/17.
 */
public interface ITranslater {

    public HashMap getLanguages();

    public Words translate(String inputLang, String outLang, String input);

    public String generateUrl(String param1, String param2, String param3);

}
