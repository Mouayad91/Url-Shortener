package com.url_shortener.app.util;

import com.url_shortener.app.constants.Constants;

public class UrlUtils {

/**
 * Generates short URL using base64 characters.
 *
 * @return short URL with length of 6 
 */
    public static String generateShortUrl() {
        
        // construct a StringBuilder to hold the result
        StringBuilder sb = new StringBuilder(Constants.LENGTH);

        for (int i = 0; i < Constants.LENGTH; i++) {
            sb.append(Constants.BASE64.charAt
            (Constants.SECURE_RANDOM.nextInt(Constants.BASE64.length())));
        }
        return sb.toString();
    
}



}