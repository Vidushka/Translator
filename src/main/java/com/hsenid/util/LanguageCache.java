package com.hsenid.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.hsenid.services.TranslateServiceRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vidushka on 4/6/2017.
 */
@Component
public class LanguageCache {
    @Autowired
    TranslateServiceRest translate;

    private LoadingCache<Long, HashMap> userLoadingCache;

    /**
     * Create a cache of languages if not exist.(size of cache = 10, validity = 1 day)
     */
    @PostConstruct
    public void initCache() {
        userLoadingCache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .expireAfterAccess(1, TimeUnit.DAYS)
                .build(new CacheLoader<Long, HashMap>() {
                    @Override
                    public HashMap load(Long langId) throws Exception {
                        return translate.getLanguages();
                    }
                });
    }

    /**
     * Load language list from cache.
     *
     * @param langMapId - Id of language list stored in cache.
     * @return HashMap of languages got from Yandex Api.
     */
    public HashMap getLangFromCache(Long langMapId) {
        try {
            return userLoadingCache.get(langMapId);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

}
