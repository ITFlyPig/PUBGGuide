package com.statstracker.forpubggame.util;

import android.content.Context;
import android.text.TextUtils;

import java.util.Locale;

public class LocalUtil {
    /**
     * 是否是指定的语言
     * @param language
     * @param country
     * @param context
     * @return
     */
    public static boolean match(String language, String country, Context context) {
        if (TextUtils.isEmpty(language) || TextUtils.isEmpty(country) || context == null) {
            return false;
        }
        String l = Locale.getDefault().getLanguage();
        String c = context.getResources().getConfiguration().locale.getCountry();
        if (language.equals(l)) {
            if (country.equals(c)) {
               return true;
            }
        }
        return false;
    }
}
