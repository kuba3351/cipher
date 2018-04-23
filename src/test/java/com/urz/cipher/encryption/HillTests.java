package com.urz.cipher.encryption;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class HillTests {

    @Test
    public void testCalculatingMatrixDet() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<List<Integer>> matrix = Arrays.asList(Arrays.asList(1, 24, 0, 3),
                Arrays.asList(1, 0, 22, 5),
                Arrays.asList(5, 2, 0, 2),
                Arrays.asList(1, 21, 24, 1));
        Method method = Hill.class.getDeclaredMethod("calculateDetFromMatrix", List.class);
        method.setAccessible(true);
        Method method2 = Hill.class.getDeclaredMethod("modulo", int.class, int.class);
        method2.setAccessible(true);
        method.setAccessible(true);
        assertEquals(method2.invoke(new Hill(), -352, 26), method.invoke(new Hill(), matrix));
    }

    @Test
    public void moduloMinusTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Hill.class.getDeclaredMethod("modulo", int.class, int.class);
        method.setAccessible(true);
        assertEquals(25, method.invoke(new Hill(), -1, 26));
    }

    @Test
    public void moduloPlusTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Hill.class.getDeclaredMethod("modulo", int.class, int.class);
        method.setAccessible(true);
        assertEquals(2, method.invoke(new Hill(), 7, 5));
    }

    @Test
    public void moduloZeroTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Hill.class.getDeclaredMethod("modulo", int.class, int.class);
        method.setAccessible(true);
        assertEquals(0, method.invoke(new Hill(), 0, 5));
    }

    @Test
    public void inverseMatrixTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<List<Integer>> matrix = Arrays.asList(Arrays.asList(1, 2, 3, 4),
                Arrays.asList(2, 3, 1, 2),
                Arrays.asList(1, 1, 1, 25),
                Arrays.asList(1, 0, 24, 20));
        List<List<Integer>> resultMatrix = Arrays.asList(Arrays.asList(22, 20, 0, 17),
                Arrays.asList(9, 5, 20, 13),
                Arrays.asList(25, 0, 2, 25),
                Arrays.asList(4, 25, 21, 3));
        Method method = Hill.class.getDeclaredMethod("calculateInverseMatrix", List.class);
        method.setAccessible(true);
        assertEquals(resultMatrix, method.invoke(new Hill(), matrix));
        assertEquals(matrix, method.invoke(new Hill(), resultMatrix));
    }

    @Test
    public void inverseMatrixTest2() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        List<List<Integer>> matrix = Arrays.asList(Arrays.asList(3, 3),
                Arrays.asList(2, 5));
        List<List<Integer>> resultMatrix = Arrays.asList(Arrays.asList(15, 17),
                Arrays.asList(20, 9));
        Method method = Hill.class.getDeclaredMethod("calculateInverseMatrix", List.class);
        method.setAccessible(true);
        assertEquals(resultMatrix, method.invoke(new Hill(), matrix));
        assertEquals(matrix, method.invoke(new Hill(), resultMatrix));
    }

    @Test
    public void findInverseNumberTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = Hill.class.getDeclaredMethod("getInverseNumber", int.class);
        method.setAccessible(true);
        assertEquals(Optional.of(9), method.invoke(new Hill(), 3));
    }
}
