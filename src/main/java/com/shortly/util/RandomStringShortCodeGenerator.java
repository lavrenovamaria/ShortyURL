package com.shortly.util;

import java.security.SecureRandom;
import java.util.Random;

public class RandomStringShortCodeGenerator implements ShortCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 8;

    private final Random random = new SecureRandom();

    @Override
    public String generateShortCode() {
        StringBuilder shortCodeBuilder = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            shortCodeBuilder.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return shortCodeBuilder.toString();
    }
}
