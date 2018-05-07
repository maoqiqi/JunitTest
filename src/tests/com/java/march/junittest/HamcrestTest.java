package com.java.march.junittest;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThat;

public class HamcrestTest {

    @Test
    public void testExceptionMessage() {
        try {
            throw new Exception("Hello World");
        } catch (Exception e) {
            assertThat(e.getMessage(), Matchers.containsString("Hello"));
        }
    }

    class UserBean {

        private String name;

        private int age;

        public UserBean() {
        }

        public UserBean(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "UserBean{" + "name='" + name + "', age=" + age + "}";
        }
    }

    private UserBean userBean1;
    private UserBean userBean2;
    private UserBean userBean3;

    private String[] strArray;
    private List<UserBean> list;
    private Map<String, UserBean> map;

    @Before
    public void setup() {
        userBean1 = new UserBean();
        userBean2 = new UserBean("march", 10);
        userBean3 = userBean2;

        strArray = new String[]{"1", "2"};

        list = new ArrayList<>();
        list.add(userBean1);
        list.add(userBean2);

        map = new HashMap<>();
        map.put("userBean1", userBean1);
        map.put("userBean2", userBean2);
        map.put("userBean3", userBean3);
    }

    @Test
    public void testHamcrest() {
        // Core 相关方法
        // is：如果包装的匹配器匹配器时匹配,反之亦然
        assertThat(userBean2, Matchers.is(userBean3));
        assertThat("Hello World", Matchers.is(Matchers.endsWith("World")));

        // Logical 相关方法
        // allOf：如果所有匹配器都匹配才匹配
        // assertThat("Hello World", Matchers.allOf(Matchers.endsWith("World"), Matchers.startsWith("Hello")));
        // anyOf：如果任何匹配器匹配就匹配
        // assertThat("Hello World", Matchers.anyOf(Matchers.endsWith("March"), Matchers.notNullValue()));
        // not：如果包装的匹配器不匹配器时匹配,反之亦然
        assertThat("Hello World", Matchers.not(Matchers.endsWith("March")));

        // Object 相关方法
        // equalTo：判断2个对象是否相等,使用Object.equals方法
        assertThat(userBean2, Matchers.equalTo(userBean3));
        // hasToString：判断一个对象的toString方法
        assertThat(userBean1, Matchers.hasToString("UserBean{name='null', age=0}"));
        // instanceOf：判断对象是否为某个类的实例对象
        assertThat(userBean1, Matchers.instanceOf(UserBean.class));
        // nullValue：判断对象是否为null值
        assertThat(null, Matchers.nullValue());
        // notNullValue:判断对象不为null值
        assertThat(userBean1, Matchers.notNullValue());
        // sameInstance：测试2个对象是否同一个实例
        assertThat(userBean2, Matchers.sameInstance(userBean3));

        // Beans 相关方法
        assertThat(userBean1, Matchers.hasProperty("name"));

        // Collections 相关方法
        assertThat(strArray, Matchers.array(Matchers.equalTo("1"), Matchers.equalTo("2")));
        // hasEntry, hasKey, hasValue：测试一个Map包含一个实体,键或者值
        assertThat(map, Matchers.hasEntry("userBean1", userBean1));
        assertThat(map, Matchers.hasKey("userBean1"));
        assertThat(map, Matchers.hasValue(userBean1));
        // hasItem, hasItems：测试一个集合包含一个元素
        assertThat(list, Matchers.hasItem(userBean1));
        // assertThat(list, Matchers.hasItems(userBean1, userBean2));
        // hasItemInArray：测试一个数组包含一个元素
        assertThat(strArray, Matchers.hasItemInArray("1"));
        // in：测试一个对象在一个集合中
        assertThat(userBean1, Matchers.isIn(list));

        // Number 相关方法
        // closeTo：测试浮点值接近给定的值
        assertThat(1.5, Matchers.closeTo(1.0, 0.6));
        // greaterThan, greaterThanOrEqualTo, lessThan, lessThanOrEqualTo：测试大于,小于
        assertThat(1.0, Matchers.greaterThan(0.5));
        assertThat(1.5, Matchers.lessThanOrEqualTo(1.5));

        // Text 相关方法
        // equalToIgnoringCase：测试字符串相等忽略大小写
        assertThat("Hello World", Matchers.equalToIgnoringCase("hello world"));
        // equalToIgnoringWhiteSpace：测试字符串忽略空白
        assertThat("  Hello World", Matchers.equalToIgnoringWhiteSpace("Hello World"));
        // containsString, endsWith, startsWith：测试字符串匹配
        assertThat("Hello World", Matchers.containsString("Hello"));
        assertThat("Hello World", Matchers.startsWith("Hello"));
        assertThat("Hello Hello", Matchers.endsWith("Hello"));
    }

    // 扩展Hamcrest的Matcher接口自定义匹配器
    @Test
    public void testCustomMatcher() {
        Matcher<String> matcher = new BaseMatcher<String>() {

            @Override
            public boolean matches(Object item) {
                if (!(item instanceof String)) {
                    return false;
                }
                return ((String) item).contains("March");
            }

            @Override
            public void describeTo(Description description) {
                description.appendText("字符串必须包含March这个单词...");
            }
        };
        assertThat("March in Chain!", matcher);
    }
}