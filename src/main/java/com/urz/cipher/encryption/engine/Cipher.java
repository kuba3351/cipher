package com.urz.cipher.encryption.engine;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public abstract class Cipher {

    protected List<Character> alphabetLoverCase;
    protected List<Character> alphabetUpperCase;
    protected List<Character> alphabet;
    private Map<Character, Character> polishCharactersMap;

    public Cipher() {
        alphabetLoverCase = Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z');
        alphabetUpperCase = alphabetLoverCase.stream().map(Character::toUpperCase).collect(Collectors.toList());
        alphabet = new ArrayList<>();
        alphabet.addAll(alphabetLoverCase);
        alphabet.addAll(alphabetUpperCase);
        polishCharactersMap = new HashMap<>();
        polishCharactersMap.put('ą', 'a');
        polishCharactersMap.put('ć', 'c');
        polishCharactersMap.put('ę', 'e');
        polishCharactersMap.put('ł', 'l');
        polishCharactersMap.put('ń', 'n');
        polishCharactersMap.put('ó', 'o');
        polishCharactersMap.put('ś', 's');
        polishCharactersMap.put('ź', 'z');
        polishCharactersMap.put('ż', 'z');
    }

    public abstract String encrypt(String key, String text);

    public abstract String decrypt(String key, String text) throws IOException;

    public String prepareText(String key, String text) {
        StringBuilder output = new StringBuilder();
        for (Character c : text.toCharArray()) {
            output.append(polishCharactersMap.getOrDefault(c, c));
        }
        return output.toString();
    }

    public abstract boolean isKeyValid(String key);

    public abstract boolean isTextValid(String text);

    protected boolean isStringNumber(String string) {
        for (Character c : string.toCharArray()) {
            if (!Character.isDigit(c))
                return false;
        }
        return !string.isEmpty();
    }

    protected boolean isStringInAlphabet(String string) {
        for (char c : string.toCharArray()) {
            if (!alphabet.contains(c))
                return false;
        }
        return true;
    }

    public abstract String prepareKey(String key);
}
