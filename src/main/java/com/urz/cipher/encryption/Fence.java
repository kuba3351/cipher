package com.urz.cipher.encryption;

import com.urz.cipher.encryption.engine.Cipher;

import java.util.ArrayList;
import java.util.List;

public class Fence extends Cipher {

    @Override
    public String encrypt(String key, String text) {
        int keyInt = Integer.parseInt(key);
        List<List<Character>> fence = new ArrayList<>();
        for (int i = 0; i < keyInt; i++)
            fence.add(new ArrayList<>());
        int row = 0;
        int x = -1;
        for (Character c : text.toCharArray()) {
            if (row == 0 || row == keyInt - 1)
                x *= -1;
            fence.get(row).add(c);
            row += x;
        }
        StringBuilder builder = new StringBuilder();
        fence.forEach(line -> line.forEach(builder::append));
        return builder.toString();
    }

    @Override
    public String decrypt(String key, String text) {
        int keyInt = Integer.parseInt(key);
        int[] tab = new int[keyInt];
        int row = 0;
        int x = -1;
        for (Character c : text.toCharArray()) {
            if (row == 0 || row == keyInt - 1)
                x *= -1;
            tab[row]++;
            row += x;
        }
        StringBuilder builder = new StringBuilder();
        List<String> fence = new ArrayList<>();
        int sum = 0;
        for (int i = 0; i < keyInt; i++) {
            fence.add(text.substring(sum, sum + tab[i]));
            sum += tab[i];
        }
        builder.append(fence.get(0).charAt(0));
        row = 1;
        x = 1;
        int character = 0;
        for (int i = 0; i < text.length() - 1; i++) {
            if (row == 0 || row == keyInt - 1) {
                x *= -1;
                builder.append(fence.get(row).charAt((character / 2) + character % 2));
                character++;
                row += x;
            } else {
                builder.append(fence.get(row).charAt(character));
                row += x;
            }
        }
        return builder.toString();
    }

    @Override
    public String prepareText(String key, String text) {
        return text;
    }

    @Override
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
        return "Szyfr płotkowy";
    }
}
