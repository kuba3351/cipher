package com.urz.cipher.encryption;

import com.urz.cipher.encryption.engine.Cipher;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Hill extends Cipher {
    @Override
    public String encrypt(String key, String text) {
        Integer columns = findIntegerSqrt(key.length()).get();
        List<List<Integer>> keyMatrix = generateMatrixFromString(key, columns);
        List<List<Integer>> textMatrix = generateMatrixFromString(text, columns);
        List<List<Integer>> resultMatrix = multiplyMatrixWithVectors(keyMatrix, textMatrix);
        StringBuilder builder = new StringBuilder();
        resultMatrix.forEach(resultRow -> resultRow.forEach(integer -> builder.append(alphabetLoverCase.get(integer))));
        return builder.toString();
    }

    @Override
    public String decrypt(String key, String text) {
        Integer columns = findIntegerSqrt(key.length()).get();
        List<List<Integer>> keyMatrix = calculateInverseMatrix(generateMatrixFromString(key, columns));
        List<List<Integer>> textMatrix = generateMatrixFromString(text, columns);
        List<List<Integer>> resultMatrix = multiplyMatrixWithVectors(keyMatrix, textMatrix);
        StringBuilder builder = new StringBuilder();
        resultMatrix.forEach(resultRow -> resultRow.forEach(integer -> builder.append(alphabetLoverCase.get(integer))));
        return builder.toString();
    }

    @Override
    public String prepareText(String key, String text) {
        StringBuilder s = new StringBuilder(super.prepareText(key, text));
        int sqrt = findIntegerSqrt(key.length()).orElseThrow(()
                -> new IllegalArgumentException("Key does not have proper length"));
        int spaces = sqrt;
        while (spaces < text.length())
            spaces += sqrt;
        for (int i = 0; i < spaces - text.length(); i++)
            s.append("x");
        return s.toString().toLowerCase();
    }

    @Override
    public boolean isKeyValid(String key) {
        if (key.isEmpty() || !findIntegerSqrt(key.length()).isPresent() || !isStringInAlphabet(key))
            return false;
        List<List<Integer>> matrix = generateMatrixFromString(key.toLowerCase(), findIntegerSqrt(key.length()).get());
        int det = calculateDetFromMatrix(matrix);
        return det != 0 && getInverseNumber(det).isPresent();
    }

    @Override
    public boolean isTextValid(String text) {
        return !text.isEmpty() && isStringInAlphabet(text);
    }

    @Override
    public String prepareKey(String key) {
        return key.toLowerCase();
    }

    private List<List<Integer>> multiplyMatrixWithVectors(List<List<Integer>> keyMatrix, List<List<Integer>> textMatrix) {
        List<List<Integer>> resultMatrix = new ArrayList<>();
        for (List<Integer> textRow : textMatrix) {
            resultMatrix.add(new ArrayList<>());
            for (List<Integer> keyRow : keyMatrix) {
                int sum = 0;
                for (int i = 0; i < keyRow.size(); i++)
                    sum += keyRow.get(i) * textRow.get(i);
                resultMatrix.get(textMatrix.indexOf(textRow)).add(modulo(sum, 26));
            }
        }
        return resultMatrix;
    }

    private Optional<Integer> findIntegerSqrt(int number) {
        for (int i = 1; i < number; i++)
            if (i * i == number)
                return Optional.of(i);
        return Optional.empty();
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private List<List<Integer>> generateMatrixFromString(String s, int columns) {
        List<List<Integer>> matrix = new ArrayList<>();
        char[] chars = s.toCharArray();
        int x = 0;
        for (int i = 0; i < s.length() / columns; i++) {
            matrix.add(new ArrayList<>());
            for (int j = 0; j < columns; j++)
                matrix.get(i).add(alphabetLoverCase.indexOf(chars[x++]));
        }
        return matrix;
    }

    private boolean isMatrixSquare(List<List<Integer>> matrix) {
        return matrix.size() == matrix.get(0).size();
    }

    private List<List<Integer>> removeColumnAndRowFromMatrix(List<List<Integer>> matrix, int column, int row) {
        List<List<Integer>> newMatrix = matrix.stream().map(ArrayList::new).collect(Collectors.toList());
        newMatrix.remove(row - 1);
        newMatrix.forEach(matrixRow -> matrixRow.remove(column - 1));
        return newMatrix;
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private int calculateDetFromMatrix(List<List<Integer>> matrix) {
        if (!isMatrixSquare(matrix)) {
            throw new IllegalArgumentException("Matrix is not square!");
        }
        if (matrix.size() == 1 && matrix.get(0).size() == 1)
            return matrix.get(0).get(0);
        int sum = 0;
        for (int i = 0; i < matrix.get(0).size(); i++) {
            sum += modulo(((i + 1) % 2 == 1 ? 1 : 25) * matrix.get(0).get(i) * calculateDetFromMatrix(removeColumnAndRowFromMatrix(matrix, i + 1, 1)), 26);
        }
        return modulo(sum, 26);
    }

    private List<List<Integer>> transposeMatrix(List<List<Integer>> matrix) {
        List<List<Integer>> newMatrix = new ArrayList<>();
        for (int i = 0; i < matrix.get(0).size(); i++)
            newMatrix.add(new ArrayList<>());
        for (int i = 0; i < matrix.size(); i++)
            for (int j = 0; j < matrix.get(0).size(); j++)
                newMatrix.get(i).add(matrix.get(j).get(i));
        return newMatrix;
    }

    private List<List<Integer>> calculateComplementMatrix(List<List<Integer>> matrix) {
        List<List<Integer>> newMatrix = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            newMatrix.add(new ArrayList<>());
            for (int j = 0; j < matrix.get(0).size(); j++) {
                newMatrix.get(i).add(modulo(((i + j) % 2 == 1 ? 1 : 25) * calculateDetFromMatrix(removeColumnAndRowFromMatrix(matrix, j + 1, i + 1)), 26));
            }
        }
        return newMatrix;
    }

    private List<List<Integer>> calculateInverseMatrix(List<List<Integer>> matrix) {
        Integer inverse = getInverseNumber(calculateDetFromMatrix(matrix)).get();
        return transposeMatrix(calculateComplementMatrix(matrix)).stream().map(row
                -> row.stream().map(number
                -> modulo((26 - (number * inverse)), 26))
                .collect(Collectors.toList())).collect(Collectors.toList());
    }

    private Optional<Integer> getInverseNumber(int number) {
        if (number < 0) throw new IllegalArgumentException("negative numbers does not have inverse");
        for (int i = 2; i < 26; i++)
            if ((i * number) % 26 == 1)
                return Optional.of(i);
        return Optional.empty();
    }

    private int modulo(int i, int j) {
        if (i > 0) return i % j;
        if (i < 0) return j - Math.abs(i % j);
        return 0;
    }

    @Override
    public String toString() {
        return "szyfr Hilla";
    }
}