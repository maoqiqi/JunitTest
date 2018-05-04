package com.java.march.junittest;

import org.junit.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculatorTest {

    private static int num1;
    private static int num2;

    private Calculator calculator;

    @BeforeClass
    public static void beforeClass() {
        num1 = 1;
        System.out.println("beforeClass()");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("afterClass()");
    }

    @Before
    public void before() {
        num2 = 1;
        calculator = new Calculator();
        System.out.println("before()");
    }

    @After
    public void after() {
        System.out.println("after()");
        System.out.println("------------------------------");
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

    @Test
    public void testBeforeClassAndBefore1() {
        num1++;
        num2++;
        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
    }

    @Test
    public void testBeforeClassAndBefore2() {
        num1++;
        num2++;
        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
    }
}