package com.urz.cipher.encryption;

import com.urz.cipher.encryption.engine.Cipher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Vigenere extends Cipher {
    @Override
    public String encrypt(String key, String text) {
        List<List<Character>> table = new ArrayList<>();
        for(int i = 0;i<26;i++) {
            table.add(new ArrayList<>());
            for(int j = 0;j<26;j++) {
                table.get(table.size() - 1).add(alphabetLoverCase.get((i+j) % 26));
            }
        }
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<text.length();i++) {
            char keyChar = key.charAt(i % key.length());
            char textChar = text.charAt(i);
            int column = table.get(0).indexOf(keyChar);
            int row = table.stream().filter(elem -> elem.get(0).equals(textChar)).map(table::indexOf).findFirst().get();
            char newCharacter = table.get(column).get(row);
            builder.append(newCharacter);
        }
        return builder.toString();
    }

    @Override
    public String decrypt(String key, String text) throws IOException {
        List<List<Character>> table = new ArrayList<>();
        for(int i = 0;i<26;i++) {
            table.add(new ArrayList<>());
            for(int j = 0;j<26;j++) {
                table.get(table.size() - 1).add(alphabetLoverCase.get((i+j) % 26));
            }
        }
        StringBuilder newKey = new StringBuilder();
        for(char c : key.toCharArray())
            newKey.append(alphabetLoverCase.get((26 - alphabetLoverCase.indexOf(c)) % 26));
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<text.length();i++) {
            char keyChar = newKey.toString().charAt(i % newKey.toString().length());
            char textChar = text.charAt(i);
            int column = table.get(0).indexOf(keyChar);
            int row = table.stream().filter(elem -> elem.get(0).equals(textChar)).map(table::indexOf).findFirst().get();
            char newCharacter = table.get(column).get(row);
            builder.append(newCharacter);
        }
        return builder.toString();
    }

    @Override
    public boolean isKeyValid(String key) {
        return isStringInAlphabet(key);
    }

    @Override
    public boolean isTextValid(String text) {
        return isStringInAlphabet(text);
    }

    @Override
    public String prepareKey(String key, String text) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0;i<text.length();i++) {
            builder.append(key.charAt(i % key.length()));
        }
        return builder.toString().toLowerCase();
    }

    @Override
    public String prepareText(String key, String text) {
        return super.prepareText(key, text).toLowerCase();
    }

    @Override
    public String toString() {
        return "szyfr Vigenere'a";
    }
}
