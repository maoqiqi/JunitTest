# Mock 测试

> 作者：March    
> 链接：[Mock 测试](https://github.com/maoqiqi/blog/blob/master/mock.md)    
> 博客：http://blog.csdn.net/u011810138    
> 邮箱：fengqi.mao.march@gmail.com    
> 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。    


## Mock 介绍

#### 1. 什么是 Mock 测试？

在软件开发的世界之外, "mock"一词是指模仿或者效仿。 因此可以将"mock"理解为一个替身，替代者. 在软件开发中提及"mock",通常理解为模拟对象或者Fake。

#### 为什么需要 Mock 测试？

Mock 测试就是在测试过程中，对于某些不容易构造或者不容易获取比较复杂的对象，用一个虚拟的对象（Mock 对象）来创建以便测试的测试方法。

Mock 最大的功能是帮你把单元测试的耦合分解开，如果你的代码对另一个类或者接口有依赖，它能够帮你模拟这些依赖，并帮你验证所调用的依赖的行为。

Mock 测试是单元测试的重要方法之一。


## Mockito 简介

#### 1. Mockito 是什么？

Mockito 是一个强大的用于 Java 开发的模拟测试框架, 通过 Mockito 我们可以创建和配置 Mock 对象, 进而简化有外部依赖的类的测试.

#### 2. Mockito 资源

- 官网：http://mockito.org
- API文档：http://site.mockito.org/mockito/docs/current/org/mockito/Mockito.html
- 项目源码：https://github.com/mockito/mockito

#### 3. Mock的好处是什么?

- 提前创建测试; TDD（测试驱动开发）
  > 这是个最大的好处吧。如果你创建了一个Mock那么你就可以在service接口创建之前写Service Tests了，
  这样你就能在开发过程中把测试添加到你的自动化测试环境中了。换句话说，模拟使你能够使用测试驱动开发。

- 团队可以并行工作
  > 这类似于上面的那点；为不存在的代码创建测试。但前面讲的是开发人员编写测试程序，这里说的是测试团队来创建。
  当还没有任何东西要测的时候测试团队如何来创建测试呢？模拟并针对模拟测试！
  这意味着当service接口需要测试时，实际上QA团队已经有了一套完整的测试组件；
  没有出现一个团队等待另一个团队完成的情况。这使得模拟的效益型尤为突出了。

- 你可以创建一个验证或者演示程序
  > 由于Mocks非常高效，Mocks可以用来创建一个概念证明，作为一个示意图，或者作为一个你正考虑构建项目的演示程序。
  这为你决定项目接下来是否要进行提供了有力的基础，但最重要的还是提供了实际的设计决策。

- 为无法访问的资源编写测试
  > 这个好处不属于实际效益的一种，而是作为一个必要时的“救生圈”。有没有遇到这样的情况？
  当你想要测试一个service接口，但service需要经过防火墙访问，防火墙不能为你打开或者你需要认证才能访问。
  遇到这样情况时，你可以在你能访问的地方使用MockService替代，这就是一个“救生圈”功能。

- Mock 可以交给用户
  > 在有些情况下，某种原因你需要允许一些外部来源访问你的测试系统，像合作伙伴或者客户。
  这些原因导致别人也可以访问你的敏感信息，而你或许只是想允许访问部分测试环境。
  在这种情况下，如何向合作伙伴或者客户提供一个测试系统来开发或者做测试呢？最简单的就是提供一个mock，无论是来自于你的网络或者客户的网络。
  soapUI mock非常容易配置，他可以运行在soapUI或者作为一个war包发布到你的java服务器里面。

- 隔离系统
  > 有时，你希望在没有系统其他部分的影响下测试系统单独的一部分。由于其他系统部分会给测试数据造成干扰，影响根据数据收集得到的测试结论。
  使用mock你可以移除掉除了需要测试部分的系统依赖的模拟。当隔离这些mocks后，mocks就变得非常简单可靠，快速可预见。
  这为你提供了一个移除了随机行为，有重复模式并且可以监控特殊系统的测试环境。


## Mockito 基本使用

#### 依赖

#### 创建 Mockito 对象

#### 配置 Mockito 对象

#### 校验 Mock 对象的方法调用

#### 使用 spy() 部分模拟对象

#### 参数捕获


## Mockito 常用方法

#### 验证行为

> 一旦创建,mock会记录所有交互,你可以验证所有你想要验证的东西.

```java
@Test
public void testVerify() throws Exception {
    // 创建mock对象
    List mockedList = mock(List.class);

    // 使用mock对象
    mockedList.add("one");
    mockedList.add("two");
    mockedList.add("two");
    mockedList.clear();

    // 验证函数的调用次数

    // 验证是否调用过一次 mockedList.add("one");若不是（0次或者大于一次）,测试将不通过
    verify(mockedList).add("one");

    // 验证调用过2次 mockedList.add("two");若不是,测试将不通过
    verify(mockedList, times(2)).add("two");

    // 验证是否调用过一次 mockedList.clear();若没有（0次或者大于一次）,测试将不通过
    verify(mockedList).clear();
}
```

#### Stubbing

```java
@Test
public void testStubbing() throws Exception {
    LinkedList mockedList = mock(LinkedList.class);

    // 设置桩
    when(mockedList.get(0)).thenReturn("first");
    when(mockedList.get(1)).thenThrow(new RuntimeException());

    // 打印"first"
    System.out.println(mockedList.get(0));
    // 这里会抛 runtime exception
    System.out.println(mockedList.get(1));

    // 这里会打印"null",因为get(999)没有设置
    System.out.println(mockedList.get(999));

    // 此处测试通过
    verify(mockedList).get(0);
    // 此处测试将不通过
    verify(mockedList).get(2);
}
```

对于stubbing,有以下几点需要注意：

- 默认情况下，对于有返回值的所有方法，mock会默认返回null、空集合、默认值。比如，int/Integer返回0，boolean/Boolean返回false。
- stubbing可以被覆盖，但是请注意覆盖已有的stubbing有可能不是很好。
- 一旦stubbing，不管调用多少次，方法都会永远返回stubbing的值。
- 当你对同一个方法进行多次stubbing，最后一次stubbing是最重要的。

#### 参数匹配

```java
// 参数匹配
@Test
public void testArgumentMatcher() throws Exception {
    LinkedList mockedList = mock(LinkedList.class);
    // 用内置的参数匹配器来stub
    when(mockedList.get(anyInt())).thenReturn("element");

    // 打印"element"
    System.out.println(mockedList.get(999));

    // 你也可以用参数匹配器来验证,此处测试通过
    verify(mockedList).get(anyInt());
    // 此处测试将不通过,因为没调用get(33)
    verify(mockedList).get(eq(33));
}
```

> 警告：如果你使用了参数匹配器,那么所有参数都应该使用参数匹配器.

```java
// 正确的，因为eq返回参数匹配器
verify(mock).someMethod(anyInt(), anyString(), eq("third argument"));
// 将会抛异常，因为第三个参数不是参数匹配器，一旦使用了参数匹配器来验证，那么所有参数都应该使用参数匹配
verify(mock).someMethod(anyInt(), anyString(), "third argument");
```

#### 验证准确的调用次数，最多、最少、从未等

```java
@Test
public void testInvocationTimes() throws Exception {
    LinkedList mockedList = mock(LinkedList.class);

    mockedList.add("once");

    mockedList.add("twice");
    mockedList.add("twice");

    mockedList.add("three times");
    mockedList.add("three times");
    mockedList.add("three times");

    // 下面两个是等价的,默认使用times(1)
    verify(mockedList).add("once");
    verify(mockedList, times(1)).add("once");

    // 验证准确的调用次数
    verify(mockedList, times(2)).add("twice");
    verify(mockedList, times(3)).add("three times");

    // 从未调用过. never()是times(0)的别名
    verify(mockedList, never()).add("never happened");

    // 用atLeast()/atMost()验证
    verify(mockedList, atLeastOnce()).add("three times");
    // 下面这句将不能通过测试
    verify(mockedList, atLeast(2)).add("five times");
    verify(mockedList, atMost(5)).add("three times");
}
```

#### 为void方法抛异常

```java
@Test
public void testVoidMethodsWithExceptions() throws Exception {
    LinkedList mockedList = mock(LinkedList.class);
    doThrow(new RuntimeException()).when(mockedList).clear();
    // 下面会抛 RuntimeException
    mockedList.clear();
}
```

#### 验证调用顺序

```java
@Test
public void testVerificationInOrder() throws Exception {
    List singleMock = mock(List.class);

    // 使用单个mock对象
    singleMock.add("was added first");
    singleMock.add("was added second");

    // 创建inOrder
    InOrder inOrder = inOrder(singleMock);

    // 验证调用次数,若是调换两句,将会出错,因为singleMock.add("was added first")是先调用的
    inOrder.verify(singleMock).add("was added first");
    inOrder.verify(singleMock).add("was added second");

    // 多个mock对象
    List firstMock = mock(List.class);
    List secondMock = mock(List.class);

    firstMock.add("was called first");
    secondMock.add("was called second");

    // 创建多个mock对象的inOrder
    inOrder = inOrder(firstMock, secondMock);

    // 验证firstMock 先于 secondMock 调用
    inOrder.verify(firstMock).add("was called first");
    inOrder.verify(secondMock).add("was called second");
}
```

#### 验证mock对象没有产生过交互

```java
@Test
public void testInteractionNeverHappened() {
    List mockOne = mock(List.class);
    List mockTwo = mock(List.class);

    // 测试通过
    verifyZeroInteractions(mockOne, mockTwo);

    mockOne.add("");
    // 测试不通过,因为 mockOne 已经发生过交互了
    verifyZeroInteractions(mockOne, mockTwo);
}
```

#### 查找是否有未验证的交互

> 不建议过多使用，api原文：A word of warning: Some users who did a lot of classic,
expect-run-verify mocking tend to use verifyNoMoreInteractions() very often, even in every test method.
verifyNoMoreInteractions() is not recommended to use in every test method.
verifyNoMoreInteractions() is a handy assertion from the interaction testing toolkit.
Use it only when it’s relevant. Abusing it leads to overspecified, less maintainable tests.

```java
@Test
public void testFindingRedundantInvocations() throws Exception {
    List mockedList = mock(List.class);

    mockedList.add("one");
    mockedList.add("two");

    verify(mockedList).add("one");

    // 验证失败,因为mockedList.add("two")尚未验证
    verifyNoMoreInteractions(mockedList);
}
```

#### @Mock注解

```java
// @Mock注解
@Mock
List<String> mockedList;

@Before
public void initMocks() {
    // 必须,否则注解无效
    MockitoAnnotations.initMocks(this);
}

@Test
public void testMock() throws Exception {
    mockedList.add("one");
    verify(mockedList).add("one");
}
```

#### 根据调用顺序设置不同的stubbing

```java
// 根据调用顺序设置不同的stubbing

private interface TestMockInterface {
    String someMethod(String arg);
}

@Test
public void testStubbingConsecutiveCalls() throws Exception {
    TestMockInterface mock = mock(TestMockInterface.class);

    when(mock.someMethod("some arg")).thenThrow(new RuntimeException("")).thenReturn("foo");

    // 第一次调用,抛RuntimeException
    mock.someMethod("some arg");

    // 第二次调用返回foo
    System.out.println(mock.someMethod("some arg"));

    // 后续继续调用,返回"foo",以最后一个stub为准
    System.out.println(mock.someMethod("some arg"));

    // 下面是一个更简洁的写法
    when(mock.someMethod("some arg")).thenReturn("one", "two", "three");
    System.out.println(mock.someMethod("some arg"));
    System.out.println(mock.someMethod("some arg"));
    System.out.println(mock.someMethod("some arg"));
}
```

#### doReturn()|doThrow()| doAnswer()|doNothing()|doCallRealMethod()等用法

```java
@Test
public void testDoXXX() throws Exception {
    List mockedList = mock(List.class);
    doThrow(new RuntimeException()).when(mockedList).clear();
    // 以下会抛异常
    mockedList.clear();
}
```

#### spy监视真正的对象

- spy是创建一个拷贝，如果你保留原始的list，并用它来进行操作，那么spy并不能检测到其交互
- spy一个真正的对象+试图stub一个final方法，这样是会有问题的

```java
@Test
public void testSpy() throws Exception {
    List list = new LinkedList();
    List spy = spy(list);

    // 可选的,你可以stub某些方法
    when(spy.size()).thenReturn(100);

    // 调用"真正"的方法
    spy.add("one");
    spy.add("two");

    // 打印one
    System.out.println(spy.get(0));

    // size()方法被stub了,打印100
    System.out.println(spy.size());

    // 可选,验证spy对象的行为
    verify(spy).add("one");
    verify(spy).add("two");

    // 下面写法有问题,spy.get(10)会抛IndexOutOfBoundsException异常
    when(spy.get(10)).thenReturn("foo");
    // 可用以下方式
    doReturn("foo").when(spy).get(10);
}
```

#### 为未stub的方法设置默认返回值

```java
@Test
public void testDefaultValue() throws Exception {
    List listOne = mock(List.class, Mockito.RETURNS_SMART_NULLS);
    List listTwo = mock(List.class, new Answer() {

        @Override
        public Object answer(InvocationOnMock invocation) throws Throwable {
            // TODO: return default value here
            return null;
        }
    });
}
```

#### 参数捕捉

```java
@Test
public void testCapturingArguments() throws Exception {
    List mockedList = mock(List.class);

    ArgumentCaptor<String> argument = ArgumentCaptor.forClass(String.class);
    mockedList.add("John");

    // 验证后再捕捉参数
    verify(mockedList).add(argument.capture());
    // 验证参数
    assertEquals("John", argument.getValue());
}
```

#### s


#### 重置mocks

> Don’t harm yourself. reset() in the middle of the test method is a code smell (you’re probably testing too much).

```java
@Test
public void testReset() throws Exception {
    List mock = mock(List.class);
    when(mock.size()).thenReturn(10);
    mock.add(1);
    reset(mock);
    // 从这开始,之前的交互和stub将全部失效
}
```

#### Serializable mocks

> WARNING: This should be rarely used in unit testing.

```java
@Test
public void testSerializableMocks() throws Exception {
    List serializableMock = mock(List.class, withSettings().serializable());
}
```

#### 更多的注解：@Captor, @Spy, @InjectMocks

- @Captor 创建ArgumentCaptor.
- @Spy 可以代替spy(Object).
- @InjectMocks 如果此注解声明的变量需要用到mock对象,mockito会自动注入mock或spy成员

```java
// 可以这样写
@Spy
LinkedList list1 = new LinkedList();
// 也可以这样写,mockito 会自动实例化 list1
@Spy
LinkedList list2;

// 会自动实例化
@InjectMocks
LinkedList list3;
```

#### 超时验证

```java
private interface TimeMockTest {
    void someMethod();
}

@Test
public void testTimeout() throws Exception {
    TimeMockTest mock = mock(TimeMockTest.class);
    mock.someMethod();

    // 测试程序将会在下面这句阻塞100毫秒，timeout的时候再进行验证是否执行过someMethod()
    verify(mock, timeout(100)).someMethod();

    // 和上面代码等价
    verify(mock, timeout(100).times(1)).someMethod();

    // 阻塞100ms，timeout的时候再验证是否刚好执行了2次
    verify(mock, timeout(100).times(2)).someMethod();

    // timeout的时候，验证至少执行了2次
    verify(mock, timeout(100).atLeast(2)).someMethod();

    // timeout时间后，用自定义的检验模式验证someMethod()
    VerificationMode yourOwnVerificationMode = new VerificationMode() {

        @Override
        public void verify(VerificationData data) {
            // TODO: implement me
        }
    };
    verify(mock, new Timeout(100, yourOwnVerificationMode)).someMethod();
}
```

#### 查看是否mock或者spy

```java
@Test
public void testMockOrSpy() throws Exception {
    LinkedList mockedList = mock(LinkedList.class);
    Mockito.mockingDetails(mockedList).isMock();
    Mockito.mockingDetails(mockedList).isSpy();
}
```

#### --------------------------------------------------

验证行为
method
Mockito.verify() ：验证Mock对象的方法是否被调用。
Mockito.times() ：调用Mock对象的方法次数。
Mockito.atMost(count) , Mockito.atLeast(count) , Mockito.never() ：最多次数，最少次数，0次调用。
Mockito.anyInt() , Mockito.anyLong() , Mockito.anyDouble(),...参数设置-任意的Int类型，任意的Long类型...


Mock对象只能调用stubbed方法，调用不了它真实的方法。
但Mockito可以监视一个真实的对象，这时对它进行方法调用时它将调用真实的方法，同时也可以stubbing这个对象的方法让它返回我们的期望值。
另外不论是否是真实的方法调用都可以进行verify验证。
和创建mock对象一样，对于final类、匿名类和Java的基本类型是无法进行spy的。

**监视对象**
监视一个对象需要调用spy(T object)方法，如：List spy = spy(new LinkedList());那么spy变量就在监视LinkedList实例。
**被监视对象的Stubbing**
stubbing被监视对象的方法时要慎用when(Object)


模拟对象
模拟方法调用的返回值
模拟方法调用抛出异常
模拟调用方法时的参数匹配
模拟方法调用次数
校验行为
模拟方法调用(Stubbing)
参数匹配
校验方法调用次数
模拟无返回方法抛出异常
校验方法调用顺序
校验方法是否从未调用
快速创建Mock对象
自定义返回不同结果
对返回结果进行拦截
Mock函数操作
暗中调用真实对象
改变默认返回值
捕获函数的参数值
部分Mock
重置Mock
序列化
检查超时
Mock详情
、
https://www.jianshu.com/p/77db26b4fb54