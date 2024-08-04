package com.url_shortener.app.util;

import com.url_shortener.app.constants.Constants;

public class UrlUtils {


    public static String generateShortUrl() {
        StringBuilder sb = new StringBuilder(Constants.LENGTH);

        for (int i = 0; i < Constants.LENGTH; i++) {
            sb.append(Constants.BASE64.charAt
            (Constants.SECURE_RANDOM.nextInt(Constants.BASE64.length())));
        }
        return sb.toString();
    
}
}