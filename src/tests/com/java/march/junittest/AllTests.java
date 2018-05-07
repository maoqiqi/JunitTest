package com.java.march.junittest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({JunitTest.class, CalculatorTest.class})
public class AllTests {

}