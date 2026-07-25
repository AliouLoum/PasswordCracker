package com.passwordcracker.strategy;

import com.passwordcracker.core.HashCracker;
import com.passwordcracker.core.MD5Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class DictionaryHashCracker implements HashCracker {

    private static final String DICTIONARY_PATH = "dictionary.txt";

    @Override
    public String crack(String hash) {
        if (hash == null) {
            return null;
        }

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(DICTIONARY_PATH);
        if (inputStream == null) {
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
            String mot;
            while ((mot = reader.readLine()) != null) {
                if (mot.isEmpty()) {
                    continue;
                }
                String hashMot = MD5Utils.calculerMd5(mot);
                if (hashMot.equals(hash)) {
                    return mot;
                }
            }
        } catch (IOException e) {
            return null;
        }

        return null;
    }
}