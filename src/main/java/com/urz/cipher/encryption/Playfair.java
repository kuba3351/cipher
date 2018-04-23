package com.urz.cipher.encryption;

import com.urz.cipher.encryption.engine.Cipher;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Playfair extends Cipher {

    @Override
    public String encrypt(String key, String text) {
        return performOperation(key, text, 1);
    }

    private String performOperation(String key, String text, int i3) {
        List<Integer> table = new ArrayList<>();
        for (char c : key.toCharArray()) {
            table.add(getCharacterNumber(c));
        }
        table.addAll(alphabetLoverCase.stream().filter(character
                -> !key.contains(String.valueOf(character))).map(this::getCharacterNumber).distinct().collect(Collectors.toList()));
        Iterator<Integer> iterator = table.iterator();
        List<List<Integer>> preparedTable = new ArrayList<>();
        int x = 0;
        while (iterator.hasNext()) {
            int i = iterator.next();
            if (x++ % 5 == 0)
                preparedTable.add(new ArrayList<>());
            preparedTable.get(preparedTable.size() - 1).add(i);
        }
        List<Integer> encrypted = new ArrayList<>();
        for (int i = 0; i < text.length() - 1; i = i + 2) {
            int i1 = getCharacterNumber(text.charAt(i));
            int i2 = getCharacterNumber(text.charAt(i + 1));
            Map<String, Integer> first = findInTable(i1, preparedTable);
            Map<String, Integer> second = findInTable(i2, preparedTable);
            encrypted.addAll(findOpposite(first, second, preparedTable, i3));
        }
        return encrypted.stream().map(this::getCharacterFromNumber).map(character
                -> Character.toString(character)).reduce((s1, s2) -> s1 + s2).get();
    }

    private List<Integer> findOpposite(Map<String, Integer> first, Map<String, Integer> second, List<List<Integer>> table, int i) {
        List<Integer> result = new ArrayList<>();
        if (first.get("row").equals(second.get("row"))) {
            result.add((table.get(first.get("row")).get((first.get("column") + i) % 5)));
            result.add((table.get(first.get("row")).get((second.get("column") + i) % 5)));
        } else if (first.get("column").equals(second.get("column"))) {
            result.add((table.get((first.get("row") + i) % 5).get(first.get("column"))));
            result.add(table.get((second.get("row") + i) % 5).get(first.get("column")));
        } else {
            result.add((table.get(first.get("row")).get(second.get("column"))));
            result.add(table.get(second.get("row")).get(first.get("column")));
        }
        return result;
    }

    private Map<String, Integer> findInTable(int number, List<List<Integer>> table) {
        Map<String, Integer> result = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (table.get(i).get(j).equals(number)) {
                    result.put("row", i);
                    result.put("column", j);
                    return result;
                }
            }
        }
        return result;
    }

    @Override
    public String decrypt(String key, String text) throws IOException {
        return performOperation(key, text, -1);
    }

    @Override
    public boolean isKeyValid(String key) {
        return isStringInAlphabet(key) && key.length() <= 26;
    }

    @Override
    public boolean isTextValid(String text) {
        return isStringInAlphabet(text);
    }

    @Override
    public String prepareKey(String key) {
        List<Character> chars = new ArrayList<>();
        for (char character : key.toCharArray())
            chars.add(character);
        return chars.stream().distinct().map(character
                -> Character.toString(character)).reduce((str1, str2) -> str1 + str2).get().toLowerCase();
    }

    private int getCharacterNumber(char c) {
        return alphabetLoverCase.indexOf(c) > 8 ? alphabetLoverCase.indexOf(c) - 1 : alphabetLoverCase.indexOf(c);
    }

    private char getCharacterFromNumber(int index) {
        return index > 8 ? alphabetLoverCase.get(index + 1) : alphabetLoverCase.get(index);
    }

    @Override
    public String prepareText(String key, String text) {
        String preparedText = super.prepareText(key, text).toLowerCase();
        for (int i = 0; i < text.length(); i = i + 2) {
            char c1 = text.charAt(i);
            char c2 = text.charAt(i + 1);
            if (c1 == c2)
                preparedText = preparedText.substring(0, i) + preparedText.substring(i + 1, preparedText.length());
        }
        return preparedText;
    }

    @Override
    public String toString() {
        return "szyfr Playfaira";
    }
}
