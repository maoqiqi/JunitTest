package com.java.march.junittest;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CalculatorTest {

    private Calculator calculator;

    @Before
    public void before() {
        calculator = new Calculator();
    }

    /**
     * Method: addition(int x, int y)
     */
    @Test
    public void testAddition() {
        assertEquals("result:", 7, calculator.addition(2, 5));
    }

    /**
     * Method: subtraction(int x, int y)
     */
    @Test
    public void testSubtraction() {
        assertEquals("result:", -3, calculator.subtraction(2, 5));
    }

    /**
     * Method: multiplication(int x, int y)
     */
    @Test
    public void testMultiplication() {
        assertEquals("result:", 10, calculator.multiplication(2, 5));
    }

    /**
     * Method: division(int x, int y)
     */
    @Test
    public void testDivision() {
        assertEquals("result:", 4, calculator.division(20, 5));
    }
}