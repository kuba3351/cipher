package com.urz.cipher.encryption;

import com.urz.cipher.encryption.engine.Cipher;

import java.util.*;
import java.util.stream.Collectors;

public class Caesar implements Cipher {

    private List<Character> alphabet;
    private List<Character> alphabetUpperCase;
    private Map<Character, Character> polishCharactersMap;

    public Caesar() {
        alphabet = Arrays.asList('a', 'b','c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' );
        alphabetUpperCase = alphabet.stream().map(Character::toUpperCase).collect(Collectors.toList());
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

    public String encrypt(String key, String text) {
        StringBuilder output = new StringBuilder();
        int keyInteger = Integer.parseInt(key);
        for(Character c : text.toCharArray()) {
            if(alphabet.contains(c)) {
                int index = alphabet.indexOf(c);
                int newIndex = (index + keyInteger) % 26;
                output.append(alphabet.get(newIndex));
            }
            else if(alphabetUpperCase.contains(c)) {
                int index = alphabetUpperCase.indexOf(c);
                int newIndex = (index + keyInteger) % 26;
                output.append(alphabetUpperCase.get(newIndex));
            }
            else output.append(c);
        }
        return output.toString();
    }

    public String decrypt(String key, String text) {
        StringBuilder output = new StringBuilder();
        int keyInteger = Integer.parseInt(key);
        for(Character c : text.toCharArray()) {
            if(alphabet.contains(c)) {
                int index = alphabet.indexOf(c);
                int newIndex = (26 + index - keyInteger) % 26;
                output.append(alphabet.get(newIndex));
            }
            else if(alphabetUpperCase.contains(c)) {
                int index = alphabetUpperCase.indexOf(c);
                int newIndex = (26 + index - keyInteger) % 26;
                output.append(alphabetUpperCase.get(newIndex));
            }
            else output.append(c);
        }
        return output.toString();
    }

    public String prepareText(String text) {
        StringBuilder output = new StringBuilder();
        for(Character c : text.toCharArray()){
            output.append(polishCharactersMap.getOrDefault(c, c));
        }
        return output.toString();
    }

    public boolean isKeyValid(String key) {
        for(Character c : key.toCharArray()){
            if(!Character.isDigit(c))
                return false;
        }
        return !key.isEmpty();
    }

    @Override
    public String toString() {
        return "Szyfr cezara";
    }
}
