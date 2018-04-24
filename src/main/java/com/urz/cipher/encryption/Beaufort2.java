package com.urz.cipher.encryption;

import com.urz.cipher.encryption.engine.Cipher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Beaufort2 extends Cipher {
    @Override
    public String encrypt(String key, String text) {
        return runCipher(key, text);
    }

    @Override
    public String decrypt(String key, String text) throws IOException {
        return runCipher(key, text);
    }

    private List<Character> reverseAndShift(List<Character> collection, int shift) {
        List<Character> reversed = new ArrayList<>(collection);
        char beginChar = reversed.get(0);
        reversed.remove(0);
        Collections.reverse(reversed);
        reversed.add(0, beginChar);
        List<Character> shifted = new ArrayList<>();
        for(int i = 0;i<26;i++) {
            shifted.add(reversed.get((shift + i) % 26));
        }
        return shifted;
    }

    private String runCipher(String key, String text) {
        int keyInteger = Integer.parseInt(key);
        List<Character> alphabetLoverShifted = reverseAndShift(alphabetLoverCase, keyInteger);
        List<Character> alphabetUpperShifted = reverseAndShift(alphabetUpperCase, keyInteger);
        StringBuilder output = new StringBuilder();
        for (Character c : text.toCharArray()) {
            if (alphabetLoverCase.contains(c)) {
                output.append(alphabetLoverShifted.get(alphabetLoverCase.indexOf(c)));
            } else if (alphabetUpperCase.contains(c)) {
                output.append(alphabetUpperShifted.get(alphabetUpperCase.indexOf(c)));
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
    public String prepareKey(String key, String text) {
        return key;
    }

    @Override
    public String toString() {
        return "Szyfr Beauforte'a 2";
    }
}
