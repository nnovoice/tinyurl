package com.firstpenguin.tinyurl.restservice.util;

import org.springframework.stereotype.Component;

import java.util.Iterator;

@Component
public class ShortUrlCodeGenerator<T> implements Iterator<T> {
    private final String alphabets = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+@";

    //TODO: Fixing the length to 8 for now. Make it configurable
    private final int length = 8;

    private char[] generatedUrl;
    private int[] indices;
    boolean isDone = false;

    public ShortUrlCodeGenerator() {
        generatedUrl = new char[length];
        indices = new int[length];
    }

    private String urlGeneratorIterative() {
        if (isDone) return null;

        generateShortUrl();

        setupIndicesToGenerateNextShortUrl();

        return new String(generatedUrl);
    }

    /*
    1 - To generate a short code of 2 chars, we start with indicies[] = {0,0};
    2 - After this, to generate the next code, we increment to {0,1} and so on until {0,63}
    3 - after which, we move to {1,0}. This is very similar to a base-64 number.
    4 - Once we reach {63,63}, we cannot generate more.
     */
    private void setupIndicesToGenerateNextShortUrl() {
        int maxIndexVal = alphabets.length() - 1;
        int j = indices.length - 1;

        // start from the last index and find a index which can be
        // incremented
        while (j >= 0) {
           if (indices[j] == maxIndexVal) {
               j -= 1;
           } else {
               indices[j] += 1;
               break;
           }
        }

        // are we at 3 or 4 above?
        if (j >= 0) {
           for (int k = j + 1; k < indices.length; ++k) {
               // we found a good index j, so, set j+1 until the last index to 0
               // this is condition 3 above
               indices[k] = 0;
           }
        } else {
            // condition 4: we could not find a good index because we have
            // generated all combinations
            isDone = true;
        }
    }

    private void generateShortUrl() {
        for (int i = 0; i < indices.length; ++i) {
            generatedUrl[i] = alphabets.charAt(indices[i]);
        }
    }

    @Override
    public boolean hasNext() {
        return !isDone;
    }

    @Override
    public T next() {
        return (T) urlGeneratorIterative();
    }

}
