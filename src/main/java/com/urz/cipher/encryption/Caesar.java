package com.urz.cipher.encryption;

import com.urz.cipher.encryption.engine.Cipher;

public class Caesar extends Cipher {

    public Caesar() {
        super();
    }

    public String encrypt(String key, String text) {
        StringBuilder output = new StringBuilder();
        int keyInteger = Integer.parseInt(key);
        for (Character c : text.toCharArray()) {
            if (alphabetLoverCase.contains(c)) {
                int index = alphabetLoverCase.indexOf(c);
                int newIndex = (index + keyInteger) % 26;
                output.append(alphabetLoverCase.get(newIndex));
            } else if (alphabetUpperCase.contains(c)) {
                int index = alphabetUpperCase.indexOf(c);
                int newIndex = (index + keyInteger) % 26;
                output.append(alphabetUpperCase.get(newIndex));
            } else output.append(c);
        }
        return output.toString();
    }

    public String decrypt(String key, String text) {
        StringBuilder output = new StringBuilder();
        int keyInteger = Integer.parseInt(key);
        for (Character c : text.toCharArray()) {
            if (alphabetLoverCase.contains(c)) {
                int index = alphabetLoverCase.indexOf(c);
                int newIndex = (26 + index - keyInteger) % 26;
                output.append(alphabetLoverCase.get(newIndex));
            } else if (alphabetUpperCase.contains(c)) {
                int index = alphabetUpperCase.indexOf(c);
                int newIndex = (26 + index - keyInteger) % 26;
                output.append(alphabetUpperCase.get(newIndex));
            } else output.append(c);
        }
        return output.toString();
    }

    public boolean isKeyValid(String key) {
        return isStringNumber(key);
    }

    @Override
    public boolean isTextValid(String text) {
        return true;
    }

    @Override
    public String prepareKey(String key) {
        return key;
    }

    @Override
    public String toString() {
        return "Szyfr cezara";
    }
}
