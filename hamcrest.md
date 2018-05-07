# Hamcrest

> 作者：March    
> 链接：[Hamcrest](https://github.com/maoqiqi/blog/blob/master/hamcrest.md)    
> 博客：http://blog.csdn.net/u011810138    
> 邮箱：fengqi.mao.march@gmail.com    
> 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。    

Hamcrest是一个匹配器库。它可以在测试中创建拥有灵活表达式的匹配器。所以它们也是主要用于测试。

## 简介

### 概述

JUnit框架用一组assert方法封装了一些常用的断言。这些assert方法可以帮我们简化单元测试的编写。
这样的话，Junit就可以根据这些断言是否抛出 AssertionFailedError 错误来判断测试用例的执行结果。

使用过Junit的应该有过体验：在实际开发中，一些基本的断言，如equals,null,true它们的可读性并不是很好。
而且很多时候我们要比较对象、集合、Map等数据结构。这样我们要么进行大段的字段获取再断言。或者干脆自己编写表达式并断言其结果。

Hamcrest提供了一套匹配符Matcher，这些匹配符更接近自然语言，可读性高，更加灵活。

Hamcrest 提供了大量被称为“匹配器”的方法。其中每个匹配器都设计用于执行特定的比较操作。
Hamcrest的可扩展性很好，让你能够创建自定义的匹配器。

### 支持语言

Hamcrest 支持以下几种语言，详情见http://hamcrest.org/

- Java
- Python
- Ruby
- Objective-C
- PHP
- Erlang
- Swift

## 资源

### 官网

http://hamcrest.org/

### Java

http://hamcrest.org/JavaHamcrest/

### Source Repository

https://github.com/hamcrest/JavaHamcrest

### API Documentation (JavaDoc)

http://hamcrest.org/JavaHamcrest/javadoc/

### Extensions

https://github.com/hamcrest/JavaHamcrest/wiki/Related-Projects

### 下载

您可以从从[maven central][1]获取Hamcrest二进制文件。


## 包说明

### hamcrest-core.jar

This is the core API to be used by third-party framework providers. This includes a foundation set of matcher implementations for common operations. This API is stable and will rarely change. You will need this library as a minimum.

这是使用第三方框架所必须的核心API。这个Jar包中包含了一组基本的匹配器来执行一般的操作。这个API是稳定的，并且很少会改变。这是你所需要的最基本的库。

### hamcrest-library.jar

The ever-growing library of Matcher implementations which are based on the core functionality in hamcrest-core.jar. This will grow between releases.

这是一个逐渐增加的匹配器库，这些匹配器的实现是基于hamcrest-core.jar的核心功能。这将逐渐变化在releases版本之间。

### hamcrest-generator.jar

A tool to allow many Matcher implementations to be combined into a single class with static methods returning the different matchers so users don't have to remember many classes/packages to import. Generates code. This library is only used internally at compile time. It is not necessary for the use of any of the other hamcrest libraries at runtime.

这是一个工具，允许实现多个匹配器组合成一个类并通过静态方法返回不同的匹配器，因此使用者没必要记住很多类/包的名称来导入了。这个库是在编译时只在内部使用。在运行的时候没有必要使用其他hamcrest库。

### hamcrest-integration.jar

Provides integration between Hamcrest and other testing tools, including JUnit (3 and 4), TestNG, jMock and EasyMock. Uses hamcrest-core.jar and hamcrest-library.jar.

在使用hamcrest-core.jar and hamcrest-library.jar这两个包时，该包提供了它们和其他测试工具之间的集成,包括JUnit(3和4),TestNG,jMock和EasyMock。

### hamcrest-all.jar

One jar containing all classes of all the other jars.

这一个Jar包包含了其它Jar包中的所有类。


## Hamcrest API

- Core

  - `anything`：总是匹配,如果你不关心测试下的对象是什么是有用的
  - `describedAs`：添加一个定制的失败表述装饰器
  - `is`：改进可读性装饰器

- Logical

  - `allOf`：如果所有匹配器都匹配才匹配,像Java里的&&
  - `anyOf`如果任何匹配器匹配就匹配,像Java里的||
  - `not`：如果包装的匹配器不匹配器时匹配,反之亦然

- Object

  - `equalTo`：测试对象相等使用Object.equals方法
  - `hasToString`：测试Object.toString方法
  - `instanceOf, isCompatibleType`：测试类型
  - `notNullValue, nullValue`：测试null
  - `sameInstance`：测试对象实例

- Beans

  - `hasProperty`：测试JavaBeans属性

- Collections

  - `array`：测试一个数组元素
  - `hasEntry, hasKey, hasValue`：测试一个Map包含一个实体,键或者值
  - `hasItem, hasItems`：测试一个集合包含一个元素
  - `hasItemInArray`：测试一个数组包含一个元素

- Number

  - `closeTo`：测试浮点值接近给定的值
  - `greaterThan, greaterThanOrEqualTo, lessThan, lessThanOrEqualTo`：测试次序

- Text

  - `equalToIgnoringCase`：测试字符串相等忽略大小写
  - `equalToIgnoringWhiteSpace`：测试字符串忽略空白
  - `containsString, endsWith, startsWith`：测试字符串匹配


## 基本使用


[1]: http://search.maven.org/#search%7Cga%7C1%7Cg%3Aorg.hamcrest