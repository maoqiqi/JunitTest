package com.java.march.junittest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

// 通过静态导入的方式,可以让代码看起来很干净
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTest {

    // 1. 验证
    @Test
    public void testVerify() throws Exception {

        /**
         * 1.1 验证行为
         */
        // 创建 mock 对象
        List mockList1 = mock(List.class);
        // 使用 mock 对象
        mockList1.add("once");
        mockList1.clear();
        // 验证是否调用过一次add("once")方法
        verify(mockList1).add("once");
        // 验证是否调用过一次clear()方法
        verify(mockList1).clear();

        /**
         * 1.2 验证函数的调用次数,最多、最少、从不等
         */
        List mockList2 = mock(List.class);
        mockList2.add("once");
        mockList2.add("twice");
        mockList2.add("twice");
        mockList2.add("three times");
        mockList2.add("three times");
        mockList2.add("three times");
        // 下面的两个验证函数的验证效果一样。因为verify默认验证的就是times(1)
        verify(mockList2).add("once");
        verify(mockList2, times(1)).add("once");
        // 验证具体的执行次数
        verify(mockList2, times(2)).add("twice");
        verify(mockList2, times(3)).add("three times");
        // 使用never()进行验证,never()相当于times(0)
        verify(mockList2, never()).add("never happened");
        // 使用atLeast()/atMost()来进行验证
        verify(mockList2, atLeastOnce()).add("three times");
        verify(mockList2, atLeast(2)).add("three times");
        verify(mockList2, atMost(5)).add("three times");

        /**
         * 1.3 确保交互操作没有执行在Mock对象上
         */
        List mockList5 = mock(List.class);
        // 删除注释,测试不通过
        // mockList5.add("");
        // 下面的两个验证函数的验证效果一样。
        verify(mockList5, never()).add("");
        // 没有发生交互
        verifyZeroInteractions(mockList5);

        /**
         * 1.4 查找是否有未验证的交互
         */
        List mockList6 = mock(List.class);
        mockList6.add("one");
        mockList6.add("two");
        verify(mockList6).add("one");
        verify(mockList6).add("two");
        // 注释上面这句代码,验证失败,因为mockList6.add("two")尚未验证
        verifyNoMoreInteractions(mockList6);

        /**
         * 1.5 按顺序来验证
         */
        // A. 单个mock对象它的方法必须按照顺序来调用。
        List singleMock = mock(List.class);
        // 使用单个mock对象
        singleMock.add("was added first");
        singleMock.add("was added second");
        // 为单个Mock创建一个InOrder的顺序验证
        InOrder inOrder = inOrder(singleMock);
        // 验证调用次数,若是调换两句,将会出错,因为singleMock.add("was added first")是先调用的
        inOrder.verify(singleMock).add("was added first");
        inOrder.verify(singleMock).add("was added second");

        // B. 多个mock也必须按照顺序来使用
        List firstMock = mock(List.class);
        List secondMock = mock(List.class);
        firstMock.add("was called first");
        secondMock.add("was called second");
        // 创建一个inOrder对象，把需要按照顺序验证的mock传递进去。
        inOrder = inOrder(firstMock, secondMock);
        // 保证firstMock在secondMock之前调用
        inOrder.verify(firstMock).add("was called first");
        inOrder.verify(secondMock).add("was called second");

        /**
         * 1.6 超时验证
         */
        Supplier mockList3 = mock(Supplier.class);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 当事件修改为150的时候,下面的测试不通过
                    Thread.sleep(50);
                    mockList3.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        // 测试程序将会在下面这句阻塞100毫秒,timeout的时候再进行验证是否执行过get()
        verify(mockList3, timeout(100)).get();
    }

    // 2. Stubbing
    @Test
    public void testStubbing() throws Exception {
        /**
         * 2.1 Subbing测试桩
         */
        LinkedList mockedList = mock(LinkedList.class);
        // 设置桩
        when(mockedList.get(0)).thenReturn("first");
        when(mockedList.get(1)).thenThrow(new RuntimeException());
        // 这里打印"first"
        System.out.println(mockedList.get(0));
        // 这里会抛 runtime exception
        // System.out.println(mockedList.get(1));
        // 这里会打印"null",因为get(999)没有设置
        System.out.println(mockedList.get(999));

        /**
         * 2.2 参数匹配
         */
        LinkedList mockedList2 = mock(LinkedList.class);
        // 使用内置的anyInt()参数匹配Stubbing,当调用get(整型值)时都返回"element"
        when(mockedList2.get(anyInt())).thenReturn("element");
        // 这里打印"element"
        System.out.println(mockedList2.get(999));
        // 你也可以验证参数匹配器
        verify(mockedList2).get(anyInt());

        // 警告：如果你使用了参数匹配器,那么所有参数都应该使用参数匹配器.

        // 正确的，因为eq返回参数匹配器
        // verify(mock).someMethod(anyInt(), anyString(), eq("third argument"));
        // 将会抛异常，因为第三个参数不是参数匹配器
        // verify(mock).someMethod(anyInt(), anyString(), "third argument");

        /**
         * 2.3 为连续的调用做测试桩（迭代式的测试桩）
         */
        Function mock = mock(Function.class);
        when(mock.apply("some arg")).thenReturn("one").thenReturn("two");
        // 第一次调用打印"one"
        System.out.println(mock.apply("some arg"));
        // 第二次调用打印"two"
        System.out.println(mock.apply("some arg"));
        // 后续继续调用,打印"two",以最后一个stub为准
        System.out.println(mock.apply("some arg"));

        // 下面是一个更简洁的写法
        when(mock.apply("some arg")).thenReturn("one", "two", "three");
        System.out.println(mock.apply("some arg"));
        System.out.println(mock.apply("some arg"));
        System.out.println(mock.apply("some arg"));

        // 注意:如果不是.thenReturn()连续调用,而是使用具有相同匹配器或参数的多个Stubbing,则每个Stubbing将覆盖前一个Stubbing
        when(mock.apply("some arg")).thenReturn("one");
        when(mock.apply("some arg")).thenReturn("two");
        System.out.println(mock.apply("some arg"));
        System.out.println(mock.apply("some arg"));

        /**
         * 2.4 为回调做测试桩
         */
        when(mock.apply("some arg")).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                // 获得函数调用的参数
                Object[] args = invocation.getArguments();
                // 获得Mock对象本身
                Object mock = invocation.getMock();
                return "called with arguments: " + args[0];
            }
        });
        // 输出:"called with arguments: some arg"
        System.out.println(mock.apply("some arg"));

        /**
         * 2.5 为未stub的方法设置默认返回值（自1.7开始）
         */
        List listOne = mock(List.class, Mockito.RETURNS_SMART_NULLS);
        List listTwo = mock(List.class, new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return "default value";
            }
        });
        System.out.println(listOne.get(999));
        System.out.println(listTwo.get(999));

        /**
         * 2.6 spy监控真实对象
         */
        List list = new LinkedList();
        List spy = spy(list);

        // 可选的,你可以stub某些方法
        when(spy.size()).thenReturn(100);

        // 调用"真正"的方法
        spy.add("one");
        spy.add("two");

        // 打印"one"
        System.out.println(spy.get(0));
        // size()方法被stub了,打印"100"
        System.out.println(spy.size());

        // 可选,验证spy对象的行为
        verify(spy).add("one");
        verify(spy).add("two");

        // 理解监控真实对象非常重要！
        // 有时候，在监控对象上使用when(Object)来进行打桩是不可能或者不切实际的。
        // 因此在使用spy的时候，请考虑doReturn|Answer|Throw() 这一系列的方法来打桩。
        // 例如：

        // 不可能：因为当调用spy.get(10)时会调用真实对象的get(10)函数，此时会发生IndexOutOfBoundsException。
        // when(spy.get(10)).thenReturn("foo");
        // 你需要使用doReturn来进行打桩
        doReturn("foo").when(spy).get(10);
    }

    // 3. doReturn()|doThrow()| doAnswer()|doNothing()|doCallRealMethod()等用法
    @Test
    public void testDoXXX() throws Exception {
        List mockedList = mock(List.class);
        doThrow(new RuntimeException()).when(mockedList).clear();
        // 以下会抛异常
        mockedList.clear();
    }

    // 4. 参数捕捉（从1.8.0开始）
    @Test
    public void testCapturingArguments() throws Exception {
        List mockedList = mock(List.class);
        ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
        mockedList.add("John");
        // 验证
        verify(mockedList).add(argument.capture());
        // 验证
        assertEquals("John", argument.getValue());
    }

    // 5. 重置（自1.8.0开始）
    @Test
    public void testReset() throws Exception {
        List mock = mock(List.class);
        when(mock.size()).thenReturn(10);
        System.out.println(mock.size());
        reset(mock);
        System.out.println(mock.size());
        // 从这开始,之前的交互和stub将全部失效
    }

    // 6. 序列化（自1.8.1开始）
    @Test
    public void testSerializableMocks() throws Exception {
        List serializableMock = mock(List.class, withSettings().serializable());
    }

    // 7. @Mock注解
    @Mock
    private List<String> list1 = new ArrayList<>();
    @Mock
    private List<String> list2;

    /**
     * 新的注解：@Captor, @Spy, @InjectMocks（1.8.3）
     */

    @Captor
    private ArgumentCaptor<String> argumentCaptor;

    @Spy
    private LinkedList list3 = new LinkedList<>();
    // 也可以这样写,mockito 会自动实例化
    @Spy
    private LinkedList list4;

    // 会自动实例化
    @InjectMocks
    private LinkedList list5 = new LinkedList<>();

    @Before
    public void initMocks() {
        // 必须,否则注解无效
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testAnnotations() throws Exception {
        System.out.println(list1.size());
        System.out.println(list2.size());
        System.out.println(list3.size());
        System.out.println(list4.size());
        System.out.println(list5.size());

        list2.add("one");
        verify(list2).add(argumentCaptor.capture());
    }
}