package com.url_shortener.app.constants;

import java.security.SecureRandom;

public class Constants {
    //Characters set to generate short urls
    public static final String BASE64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
   
    //instance of SecureRandom for generating random values
    public static final SecureRandom SECURE_RANDOM = new SecureRandom();
    // length of short url
    public static final int LENGTH = 6;
}
