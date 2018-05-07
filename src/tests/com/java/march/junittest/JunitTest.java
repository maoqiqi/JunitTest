package com.java.march.junittest;

import org.junit.*;
import org.junit.Test;

public class JunitTest {

    private static int num1;
    private static int num2;

    // @BeforeClass 表示该方法只执行一次，并且在所有方法之前执行。
    // 一般可以使用该方法进行数据库连接操作，注意该注解运用在静态方法。
    @BeforeClass
    public static void beforeClass() {
        num1 = 1;
        System.out.println("beforeClass()");
        System.out.println("------------------------------");
    }

    // @AfterClass 表示该方法只执行一次，并且在所有方法之后执行。
    // 一般可以使用该方法进行数据库连接关闭操作，注意该注解运用在静态方法。
    @AfterClass
    public static void afterClass() {
        System.out.println("------------------------------");
        System.out.println("afterClass()");
    }

    // @Before 表示该方法在每一个测试方法之前运行，可以使用该方法进行初始化之类的操作
    @Before
    public void before() {
        num2 = 1;
        System.out.println("before()");
    }

    // @After 表示该方法在每一个测试方法之后运行，可以使用该方法进行释放资源，回收内存之类的操作
    @After
    public void after() {
        System.out.println("after()");
    }

    // 一个 JUnit 测试是一个在专用于测试的类中的一个方法, 并且这个方法被 @org.junit.Test 注解标注。
    // @Test (expected = Exception.class) 表示预期会抛出Exception.class 的异常
    @Test(expected = NullPointerException.class)
    public void testNullPointerException() {
        String str = null;
        System.out.println(str.toUpperCase());
    }

    // @Ignore 含义是“某些方法尚未完成，暂不参与此次测试”。
    // 这样的话测试结果就会提示你有几个测试被忽略，而不是失败。
    // 一旦你完成了相应函数，只需要把@Ignore注解删去，就可以进行正常的测试。
    @Ignore
    public void testIgnore() {
        System.out.println("方法尚未完成，暂不参与此次测试");
    }

    // @Test(timeout=100) 表示预期方法执行不会超过 100 毫秒，控制死循环
    @Test(timeout = 100)
    public void testTimeOut() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("i = " + i);
        }
    }

    @Test
    public void testBeforeClassAndBefore1() {
        num1++;
        num2++;
        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
        System.out.println("------------------------------");
    }

    @Test
    public void testBeforeClassAndBefore2() {
        num1++;
        num2++;
        System.out.println("num1 = " + num1);
        System.out.println("num2 = " + num2);
    }
}