# Spring5

## 一、Spring概述

### 1.1 什么是Spring？

-   Spring是一个开放源代码的设计层面框架，**它解决的是业务逻辑层和其他各层的松耦合问题**，它将面向接口的编程思想贯穿整个系统应用。
-   关键词
    -   ==轻量、控制反转（IoC）、面向切面（AOP）、容器、框架、MVC==

### 1.2 Spring组件

![image-20201110231144396](https://gitee.com/primabrucexu/image/raw/main/image/20201110232423.png)

-   Spring 框架是一个分层架构，由 7 个定义良好的模块组成。Spring 模块构建在核心容器之上，核心容器定义了创建、配置和管理 bean 的方式。组成 Spring 框架的每个模块（或组件）都可以单独存在，或者与其他一个或多个模块联合实现。

![image-20201110231155735](https://gitee.com/primabrucexu/image/raw/main/image/20201110232425.png)

-   **核心容器：**
    -   核心容器提供 Spring 框架的基本功能。核心容器的主要组件是 BeanFactory ，它是工厂模式的实现。BeanFactory 使用控制反转（IOC） 模式将应用程序的配置和依赖性规范与实际的应用程序代码分开。
-   **Spring 上下文：**
    -   Spring 上下文是一个配置文件，向 Spring 框架提供上下文信息。Spring 上下文包括企业服务，例如 JNDI、EJB、电子邮件、国际化、校验和调度功能。
-   **Spring AOP**
    -   通过配置管理特性，Spring AOP 模块直接将面向切面的编程功能 , 集成到了 Spring框架中。所以，可以很容易地使 Spring 框架管理任何支持 AOP的对象。
    -   Spring AOP 模块为基于Spring 的应用程序中的对象提供了事务管理服务。通过使用 Spring AOP，不用依赖组件，就可以将声明性事务管理集成到应用程序中。
-   **Spring DAO：**
    -   JDBC DAO 抽象层提供了有意义的异常层次结构，可用该结构来管理异常处理和不同数据库供应商抛出的错误消息。
    -   异常层次结构简化了错误处理，并且极大地降低了需要编写的异常代码数量（例如打开和关闭连接）。Spring DAO 的面向 JDBC 的异常遵从通用的 DAO 异常层次结构。
-   **Spring ORM：**
    -   Spring 框架插入了若干个 ORM 框架，从而提供了 ORM 的对象关系工具，其中包括 JDO、Hibernate 和 iBatis SQL Map。所有这些都遵从 Spring 的通用事务和 DAO 异常层次结构。
-   **Spring Web 模块：**
    -   Web 上下文模块建立在应用程序上下文模块之上，为基于 Web 的应用程序提供了上下文。所以，Spring 框架支持与 Jakarta Struts 的集成。
    -   Web 模块还简化了处理多部分请求以及将请求参数绑定到域对象的工作。
-   **Spring MVC 框架：**
    -   MVC 框架是一个全功能的构建 Web 应用程序的 MVC 实现。通过策略接口，MVC 框架变成为高度可配置的，MVC 容纳了大量视图技术，其中包括 JSP、Velocity、Tiles、iText和 POI。

## 二、IoC —— 控制反转

-   IoC其实是一种思想上的表现，核心概念就是将程序中创建对象的权利由程序猿变更给了用户
    -   **KEY**：==对象从程序猿创建变成了用户创建==
-   为了更好的理解什么是IoC，下面有三个例子，分别演示了3种不同的创建对象的方式

### 2.1 传统方式

-   UserDao接口

    ~~~java
    public interface UserDao {
        public void getUser();
    }
    ~~~

-   UserDao实现

    -   默认实现

        ```java
        public class UserDaoImpl implements UserDao {
            @Override
            public void getUser() {
                System.out.println("UserDaoImpl :: getUser()");
            }
        }
        ```

    -   MySQL实现

        ```java
        public class UserDaoMysqlImpl implements UserDao {
            @Override
            public void getUser() {
                System.out.println("UserDaoMysqlImpl :: getUser()");
            }
        }
        ```

    -   SQLServer实现

        ```java
        public class UserDaoSqlserverImpl implements UserDao {
            @Override
            public void getUser() {
                System.out.println("UserDaoSqlserverImpl :: getUser()");
            }
        }
        ```

-   UserService接口

    ~~~java
    public interface UserService {
        public void getUser();
    }
    ~~~

-   UserService实现

    ~~~java
    public class UserServiceImpl implements UserService {
        private UserDao user = new UserDaoImpl();
        @Override
        public void getUser() {
            System.out.println("UserService :: getUser()");
            user.getUser();
        }
    }
    ~~~

-   模拟用户使用

    ```java
    @Test
    public void useUser() {
        UserService userService = new UserServiceImpl();
        userService.getUser();
    }
    ```

    ![image-20201111151943205](https://gitee.com/primabrucexu/image/raw/main/image/20201111151943.png)

    -   根据运行结果可以看到，这个用户是默认实现。假设我们不想用默认实现，想要用mysql实现怎么办呢？
        -   需要修改UserService实现类中的代码，从 `new UserDaoImpl() ` 改成 `new UserDaoMysqlImpl()` ，这样就能使用mysql实现

-   弊端：

    -   每次用户变化需求，程序猿就要修改一次UserService实现类中的代码。如果说用户需求变来变去或者说底层代码很复杂，那么这对于开发来说是极其不友好的

### 2.2 通过setter方法注入（IoC的雏形）

-   较传统方式，极大减少了因需求变化而改变的代码量

-   修改UserService实现类

    ```java
    public class UserServiceImpl implements UserService {
        private UserDao user;
        
        public void setUser(UserDao user) {
            this.user = user;
        }
        
        @Override
        public void getUser() {
            System.out.println("UserService :: getUser()");
            user.getUser();
        }
    }
    ```

-   模拟用户使用

    ```java
    @Test
    public void useUser() {
        UserServiceImpl userService = new UserServiceImpl();
        UserDao user = new UserDaoImpl();
        userService.setUser(user);
        userService.getUser();
    }
    ```

    -   通过set方法，我们只需要传递不同的UserDao实现类给UserService，就能使用不同的UserDao实现。这样一来就方便了很多

-   这就是IoC的雏形

### 2.3 通过Spring托管

-   导入spring依赖

    ~~~xml
    <!--使用这个依赖是因为spring-webmvc包含了大多数spring的组件，省去一个一个导入的步骤-->
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>5.2.9.RELEASE</version>
    </dependency>
    ~~~

    

-   Spring容器核心配置文件——beans.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
            https://www.springframework.org/schema/beans/spring-beans.xsd">
        
        <bean id="mysql" class="com.pbx.dao.UserDaoMysqlImpl" />
        <bean id="default" class="com.pbx.dao.UserDaoImpl" />
        <bean id="sqlserver" class="com.pbx.dao.UserDaoSqlserverImpl" />
        
        <bean id="user" class="com.pbx.service.UserServiceImpl">
            <property name="user" ref="mysql"/>
        </bean>
    </beans>
    ```

-   模拟用户使用

    ```java
    @Test
    public void springTest() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        UserService user = (UserService) context.getBean("user");
        user.getUser();
    }
    ```

    ![image-20201111153542704](https://gitee.com/primabrucexu/image/raw/main/image/20201111153542.png)

-   如果说我们需要调用不同的实现，只需要修改配置文件中的相关参数即可

### 2.4 小结

-   纵览全部三种不同的实现方式
    -   传统方式
        -   灵活性低，较为笨重，每次有新需求的时候都需要修改底层代码，工作量大
    -   setter方式注入
        -   较为灵活，出现新需求时，无需修改底层代码，只需要根据需求的不同传递不同的参数即可。简化了业务处理逻辑。
        -   假设我们服务的使用者是具有一定编程基础的人群，通过setter方式进行注入的方式看起来也不是不可以。毕竟作为用户来说，只需要传递不同类型的参数即可。
        -   看起来还是不错的一种方案。但是若是业务处理时涉及到的其他组件时，这种方法依旧不好
    -   Spring托管
        -   Spring托管就完全避免了上述的弊端。
        -   因为需求的改动，完全不需要涉及源码层次的修改。==用户需要做的就是按照自身需求修改配置文件==。
        -   用户甚至不需要知道你底层是干什么的，只需要知道我这么改就可满足我的需求。而我们底层的实现中，又很好的遵守了一些规范，这样，每当需要做不同的事情时，我们底层的代码修改量就很少了
-   IoC的核心思想，就是将某些具体实现类的对象的创建权利从开发人员手中交给使用的人。
    -   ==对象全部交给Spring托管，由Spring创建、管理和装配==
    -   本质：**解耦**

## 三、使用Spring管理对象

### 3.1 对象被创建的时间

-   创建实体类

    ~~~java
    public class User {
        private String name;
        private int id;
        private String nickname;
    }
    ~~~

-   托管实体类

    ~~~xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
        
        <bean id="user" class="com.pbx.pojo.User">
            <property name="id" value="1"/>
        </bean>
        
    </beans>
    ~~~

-   测试

    ```java
    @Test
    public void test() {
        System.out.println(1);
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        System.out.println(2);
        User user = (User) context.getBean("user");
        System.out.println(3);
        user.toString();
    }
    ```

    ![image-20201112155733620](https://gitee.com/primabrucexu/image/raw/main/image/20201112155733.png)

-   结论

    -   **被Spring托管的对象，在ApplicationContext对象创建之后就已经被创建出来**
    -   且通过配置文件注入了相应的属性值，然后等待调用。
    -   默认通过托管对象的无参构造器创建，但也可以通过有参构造器进行创建

### 3.2 创建对象的方式

-   Spring默认通过无参构造器创建对象，然后使用setter方法注入属性值。若没有无参构造器的话，Spring可以通过有参构造器创建对象

-   创建实体类

    ```java
    public class User {
        private int id;
        private String name;
        private String nickname;
    
        public User() {
            System.out.println("User::User()");
        }
    
        public User(int id, String name, String nickname) {
            System.out.println("User::User(String name, int id, String nickname)");
            this.id = id;
            this.name = name;
            this.nickname = nickname;
        }
        // 省略getter和setter方法
    }
    ```

-   编写配置

    ```xml
    <bean id="user1" class="com.pbx.pojo.User">
        <property name="id" value="1"/>
        <property name="name" value="张三"/>
        <property name="nickname" value="帅哥"/>
    </bean>
    
    <!--通过有参构造器的参数下标绑定参数-->
    <bean id="user2" class="com.pbx.pojo.User">
        <constructor-arg index="0" value="2" />
        <constructor-arg index="1" value="李四" />
        <constructor-arg index="2" value="小伙"/>
    </bean>
    ```

-   测试

    ```java
    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        User user1 = (User) context.getBean("user1");
        System.out.println(user1.toString());
        User user2 = (User) context.getBean("user2");
        System.out.println(user2.toString());
    }
    ```

    ![image-20201112161536246](https://gitee.com/primabrucexu/image/raw/main/image/20201112161536.png)

-   User1是通过默认方式（无参构造器）创建的，User2是通过有参构造器的方式创建的。二者都是被Spring托管的对象。使用有参构造器创建对象时，需要绑定参数，这里会在DI的时候详细说明

## 四、配置Spring

### 4.1 别名

alias标签可以给被Spring托管的对象设置个别名

~~~xml
<alias name="user1" alias="u1"/>
<bean id="user1" class="com.pbx.pojo.User">
    <property name="id" value="1"/>
    <property name="name" value="张三"/>
    <property name="nickname" value="帅哥"/>
</bean>
~~~



### 4.2 Bean配置

```xml
<bean id="user1" class="com.pbx.pojo.User" name="user">
    <property name="id" value="1"/>
    <property name="name" value="张三"/>
    <property name="nickname" value="帅哥"/>
</bean>
```

-   name属性下可以配置别名，而且可以配置多个别名



### 4.3 import

-   用于和其他配置文件进行合并的。会自动删除重复信息



## 五、依赖注入——DI

### 5.1 Setter注入（最常用）

-   实体类
  

    ```java
    public class Address {
        private String province;
        private String city;
        private String district;
    }
    ```
  
    ```java
    public class People {
        private String name;
        private Address address;
        private String[] cars;
        private List<String> phones;
        private Map<String, String> relationship;
        private Set<String> vips;
        private String wife;
        private Properties info;
    }
    ```


-   beans.xml配置
  
  ```xml
  <bean id="address" class="com.pbx.pojo.Address">
    <property name="province" value="浙江省"/>
    <property name="city" value="杭州市"/>
    <property name="district" value="西湖区"/>
  </bean>
  
  <bean id="bruce" class="com.pbx.pojo.People">
    <!--常规注入-->
    <property name="name" value="BruceXu" />
      
    <!--引用Bean进行注入-->
    <property name="address" ref="address"/>
      
    <!--注入数组-->
    <property name="cars">
        <array>
            <value>奔驰</value>
            <value>奥迪</value>
            <value>宝马</value>
        </array>
    </property>
      
    <!--注入List-->
    <property name="phones">
        <list>
            <value>iphone</value>
            <value>华为</value>
            <value>小米</value>
        </list>
    </property>
      
    <!--注入Map-->
    <property name="relationship">
        <map>
            <entry key="老板" value="马云"/>
            <entry key="二次元老婆" value="蕾姆"/>
        </map>
    </property>
      
    <!--注入Set-->
    <property name="vips">
        <set>
            <value>爱奇艺</value>
            <value>腾讯视频</value>
            <value>芒果tv</value>
        </set>
    </property>
      
    <!--注入空指针-->
    <property name="wife">
        <null/>
    </property>
      
    <!--注入Properties-->
    <property name="info">
        <props>
            <prop key="手机号">11122223333</prop>
            <prop key="身份证号">111111222222223333</prop>
        </props>
    </property>
      
  </bean>
  ```
  
-   测试

  ~~~java
  package com.pbx;
  
  import com.pbx.pojo.People;
  import org.springframework.context.ApplicationContext;
  import org.springframework.context.support.ClassPathXmlApplicationContext;
  
  /**
   * @author BruceXu
   * @date 2020/11/17
   */
  public class Test {
      public static void main(String[] args) {
          ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
          People people = (People) context.getBean("bruce");
          System.out.println(people);
      }
  }
  ~~~

  ![image-20201117213638275](https://gitee.com/primabrucexu/image/raw/main/image/20201117213638.png)

### 5.2 构造器注入

-   实体类

    ```java
    public class Student {
        private int id;
        private String name;
        private String garde;
        
        public Student(int id, String name, String garde) {
            this.id = id;
            this.name = name;
            this.garde = garde;
        }
    }
    ```

-   beans.xml

    ```xml
    <!--通过构造器参数下标注入-->
    <bean id="student1" class="com.pbx.pojo.Student">
        <constructor-arg index="0" value="1"/>
        <constructor-arg index="1" value="张三"/>
        <constructor-arg index="2" value="一年级"/>
    </bean>
    
    <!--通过构造器形参名称注入-->
    <bean id="student2" class="com.pbx.pojo.Student">
        <constructor-arg name="id" value="2"/>
        <constructor-arg name="name" value="李四"/>
        <constructor-arg name="garde" value="二年级"/>
    </bean>
    
    <!--通过参数类型注入-->
    <bean id="student3" class="com.pbx.pojo.Student">
        <constructor-arg type="int" value="3"/>
        <constructor-arg type="java.lang.String" value="王五"/>
        <constructor-arg type="java.lang.String" value="三年级"/>
    </bean>
    
    <bean id="student4" class="com.pbx.pojo.Student">
        <constructor-arg type="int" value="3"/>
        <constructor-arg type="java.lang.String" value="四年级"/>
        <constructor-arg type="java.lang.String" value="赵六"/>
    </bean>
    ```

-   测试

    ```java
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Student student = (Student) context.getBean("student1");
        System.out.println(student);
        System.out.println("==============");
        student = (Student) context.getBean("student2");
        System.out.println(student);
        System.out.println("==============");
        student = (Student) context.getBean("student3");
        System.out.println(student);
        System.out.println("==============");
        student = (Student) context.getBean("student4");
        System.out.println(student);
    }
    ```

    ![image-20201117214749189](https://gitee.com/primabrucexu/image/raw/main/image/20201117214749.png)

### 5.3 命名空间注入

-   命名空间注入的方式可以简化Spring配置文件的编写，适用于简单类型属性的注入

-   实体类

    ~~~java
    public class User {
        private int id;
        private String name;
        private Address address;
    }
    ~~~

-   user.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:p="http://www.springframework.org/schema/p"
           xmlns:c="http://www.springframework.org/schema/c"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
        <bean id="user1" class="com.pbx.pojo.User" p:id="1" p:name="张三"/>
        <bean id="user2" class="com.pbx.pojo.User" c:id="2" c:name="李四"/>
        
    </beans>
    ```

-   测试

    ```java
    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("user.xml");
        User user1 = context.getBean("user1", User.class);
        System.out.println(user1);
        User user2 = context.getBean("user2", User.class);
        System.out.println(user2);
    }
    ```

![](https://gitee.com/primabrucexu/image/raw/main/image/20201117220439.png)

-   注意

    -   要使用命名空间注入，需要导入命名空间的约束文件

        ~~~xml
        xmlns:p="http://www.springframework.org/schema/p"
        xmlns:c="http://www.springframework.org/schema/c"
        ~~~

    -   p命名空间和c命名空间实际上是代表标签  `<property>` 和 `<constructor-arg>`

### 5.4 自动装配

-   Spring可以不用显式的配置bean的property属性，通过自动装配也可以完成Bean的注入

-   Spring‘的自动装配机制主要依赖两个操作

    -   组件扫描：Spring会扫描被容器中的bean
    -   自动装配：Spring会根据某些条件来自动装配那些设置了自动装配的Bean

-   常见的自动装配方式

    -   `byName`
        -   Spring会自动寻找和bean中属性名同名的bean进行装配
    -   `byType`
        -   Spring会自动寻找和bean中属性类型相同的bean进行装配。如果有多个类型相同的bean，那么要报错

-   环境搭建

    -   实体类

        ```java
        public class People {
            private String name;
            private Dog dog;
            private Cat cat;
        }
        ```

        ```java
        public class Cat implements Pet {
            @Override
            public void shout() {
                System.out.println("Cat::shout()");
            }
        }
        ```

        ```java
        public class Dog implements Pet {
            @Override
            public void shout() {
                System.out.println("Dog::shout()");
            }
        }
        ```

    -   beans.xml

        ```xml
        <bean id="cat" class="com.pbx.pojo.Cat"/>
        <bean id="dog" class="com.pbx.pojo.Dog"/>
        
        <bean id="p1" class="com.pbx.pojo.People" autowire="byName">
        </bean>
        
        <bean id="p2" class="com.pbx.pojo.People" autowire="byType">
        </bean>
        ```

-   测试

    ```java
    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        People p1 = context.getBean("p1", People.class);
        People p2 = context.getBean("p2", People.class);
    
        p1.show();
        System.out.println("================");
        p2.show();
    
    }
    ```

    ![image-20201118151726467](https://gitee.com/primabrucexu/image/raw/main/image/20201118151726.png)

## 六、Bean的作用域

-   Bean的作用域

    ![image-20201118152138365](https://gitee.com/primabrucexu/image/raw/main/image/20201118152138.png)

### 6.1 singleton作用域

-   **singleton是bean的默认作用域**

-   不管你怎么使用，Spring容器在创建对象的时候只创建一个，每次从容器中获取的Bean都是同一个对象

    ![singleton](https://docs.spring.io/spring-framework/docs/current/reference/html/images/singleton.png)

### 6.2 prototype作用域

-   和singleton相对应的，每次从容器中获取对象时，Spring都会创建一个新的对象交给你

-   也就是说每次拿到的对象都是不同的对象

    ![prototype](https://docs.spring.io/spring-framework/docs/current/reference/html/images/prototype.png)

### 6.3 其他的作用域

-   request, session, application, websocket 这些作用域只有在使用Spring-web容器时才会生效，不然则会抛出异常

-   Request

    -   当一个bean的作用域为Request，表示在一次HTTP请求中，一个bean定义对应一个实例；

    -   即每个HTTP请求都会有各自的bean实例，它们依据某个bean定义创建而成。

        ~~~xml
        <bean id="loginAction" class=cn.csdn.LoginAction" 1 scope="request"/>
        ~~~

    -   针对每次HTTP请求，Spring容器会根据loginAction bean的定义创建一个全新的LoginAction bean实例，且该loginAction bean实例仅在当前HTTP request内有效，因此可以根据需要放心的更改所建实例的内部状态，而其他请求中根据loginAction bean定义创建的实例，将不会看到这些特定于某个请求的状态变化。当处理请求结束，request作用域的bean实例将被销毁。  


-   Session

    -   当一个bean的作用域为Session，表示在一个HTTP Session中，一个bean定义对应一个实例。

        ~~~xml
        <bean id="userPreferences" class="com.foo.UserPreferences" scope="session"/>
        ~~~

    -   针对某个HTTP Session，Spring容器会根据userPreferences bean定义创建一个全新的userPreferences bean实例，且该userPreferences bean仅在当前HTTP Session内有效。

    -   与request作用域一样，可以根据需要放心的更改所创建实例的内部状态，而别的HTTP Session中根据userPreferences创建的实例，将不会看到这些特定于某个HTTP Session的状态变化。

    -   当HTTP Session 最终被废弃的时候，在该HTTP Session作用域内的bean也会被废弃掉。

##  七、使用注解开发

### 7.1 准备工作

-   确保maven项目中已经正确的添加了aop依赖

-   在spring配置文件中，引入相关的约束

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
            https://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            https://www.springframework.org/schema/context/spring-context.xsd">
    
        
        <context:annotation-config/>
        
    </beans>
    ```



### 7.2 注册Bean

-   告诉Spring，哪些类需要被托管

    ~~~xml
    <!--选择需要扫描注解的包-->
    <context:component-scan base-package="com.pbx.pojo"/>
    ~~~

-   在类名上添加 `@Component` 注解，如果注解中不传递名称，bean默认的id就是全小写的类名

    ~~~java
    package com.pbx.pojo;
    
    import org.springframework.stereotype.Component;
    
    @Component("u1")
    public class User {
        private int id;
        private String name;
    }
    ~~~

-   对于这样配置的Bean，它的id为 “u1"，如果没有给出，那么默认的id为user

-   **扩展内容**
    -   `@Component` 注解有三个衍生注解，他们分别对应了web开发中的三个层，不过功能都是一样的
        -   `@Controller` —— web层
        -   `@Service` —— service层
        -   `@Repository` —— dao层



### 7.3 注入属性值

-   在需要注入值的属性或者setter方法上使用 `@Value()` 进行注入属性值
-   通过注解属性，只能实现一些值的注入，不能注入引用类型以及List等复杂类型的属性

-   实体类

    ```java
    package com.pbx.pojo;
    
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Component;
    
    @Component("u1")
    public class User {
        @Value("1")
        private int id;
    
        @Value("张三")
        private String name;
    
        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    ```

-   测试

    ```java
    @Test
    public void test02() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        User u1 = context.getBean("u1", User.class);
        System.out.println(u1);
    }
    ```

    ![image-20201121143515131](https://gitee.com/primabrucexu/image/raw/main/image/20201121143515.png)

### 7.4 @Autowired

-   `@Autowired`注解是通过类型进行自动装配的，如果有多个同类型的bean，则需要配合`@Qualifier(value = "beanid")` 指定某个特定的bean

-   实体类

    ```java
    public class People {
        private String name;
    
        @Autowired
        private Cat cat;
    
        @Autowired
        private Dog dog;
    }
    ```

-   beans.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
            https://www.springframework.org/schema/beans/spring-beans.xsd
            http://www.springframework.org/schema/context
            https://www.springframework.org/schema/context/spring-context.xsd">
    
        <context:annotation-config/>
    
        <bean id="cat1" class="com.pbx.pojo.Cat">
            <property name="name" value="cat1"/>
        </bean>
        <bean id="dog1" class="com.pbx.pojo.Dog">
            <property name="name" value="dog1"/>
        </bean>
    
        <bean id="people" class="com.pbx.pojo.People">
            <property name="name" value="张三"/>
        </bean>
    
    </beans>
    ```

-   测试

    ```java
    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        People people = context.getBean("people", People.class);
        people.show();
    }
    ```

    ![image-20201120145109216](https://gitee.com/primabrucexu/image/raw/main/image/20201120145109.png)

    


### 7.5 作用域

-   `@Scope` 
    -   singleton：默认的，Spring会采用单例模式创建这个对象。关闭工厂 ，所有的对象都会销毁。
    -   prototype：多例模式。关闭工厂 ，所有的对象不会销毁。内部的垃圾回收机制会回收

### 7.6 基于Java类配置Spring

-   以上都是使用xml文件和注解配合进行开发的。这里介绍的就是完全使用Java机制进行配置Spring，完全不需要xml文件

-   JavaConfig是通过Java类的方式来提供Bean的定义信息，不需要依赖任何的xml文件

-   使用配置类配置Spring的方式在之后的SpringBoot和SpringCloud中会大量出现

-   编写配置类

    ```java
    @Configuration
    public class MyConfig {
        
        
        @Bean
        public User getUser() {
            return new User();
        }
    }
    ```

-   实体类

    ```java
    @Component
    public class User {
    
        @Value("1")
        private int id;
    
        @Value("李四")
        private String name;
    
        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    ```

-   测试

    ```java
    @Test
    public void test01() {
        ApplicationContext context = new AnnotationConfigApplicationContext(MyConfig.class);
        User getUser = context.getBean("getUser", User.class);
        System.out.println(getUser);
    }
    ```

    ![image-20201121155327948](https://gitee.com/primabrucexu/image/raw/main/image/20201121155328.png)

-   注意
  
    -   使用配置类配置Spring时，方法名就是原来bean标签中的id，返回值类型就是以前bean标签中的class

---

## 八、AOP和代理模式

### 8.1 AOP介绍

-   什么是AOP？关键词：==解耦==

    -   可以通过预编译方式和运行期动态代理实现在**不修改源代码**的情况下给程序动态**统一添加功能**的一种技术
    -   利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的**耦合度降低**，提高程序的可重用性，同时提高了开发的效率。
    -   AOP可以看做是OOP的衍生

-   详细阐述

    -   如图所示，一个常见的Web业务执行流程中

        ![image-20201122152943616](https://gitee.com/primabrucexu/image/raw/main/image/20201122152943.png)
        -   一个WEB业务，被分成了DAO层、Service层等。
        -   这么做的好处就是明确了每一层的职责， 对于这一层的来说，我只需要做好关于我自己的事情就好了，然后把对象交给下一层。至于下一层会干什么和我没有关系。**（高内聚，低耦合）**

    -   web业务的分层开发，可以看做是将一个业务流程，用刀切开，每一层之间就是一个切面。==**编程中，对象与对象之间，方法与方法之间，模块与模块之间也都是是一个个切面。**==

    -   假设我们在某一层中，需要做这么一些处理：记录日志，安全性检验，权限验证等。那么对于这一层的的业务来说，也相当于被分成了这么几层

        ![image-20201122154529234](https://gitee.com/primabrucexu/image/raw/main/image/20201122154529.png)

        -   从这个流程图中可以看到，真正的业务处理中，执行业务的功能代码只占了一小部分，取余的都是一些附加功能代码。当这个这个业务多次重复执行的时候，这部分附加功能可能完全都是一样的，没有必要在重复写一遍代码。

        -   我们可以将这些附加的功能全部抽取出来，让这个业务只专注于自己的业务处理。

        -   就像下图展示的一样，我们将附加功能抽取出来，通过Spring将这些附加功能在注入到需要调用的地方，这样，业务就可以专心的处理自己的业务了，不需要关注其他功能的处理。这样，就可以算作一次简单的AOP

        -   **上面操作的流程，就叫做AOP。** 附加功能的抽取就是切面，调用附加功能的地方就是切入点

            ![image-20201122155313795](https://gitee.com/primabrucexu/image/raw/main/image/20201122155313.png)

### 8.2 代理模式

-   提到了AOP，就不得不提代理模式。这是因为Spring中AOP的底层实现就是基于代理模式实现的。所以掌握好代理模式的设计思想，是掌握AOP的基础

-   ==那么什么是代理模式？==
    -   代理模式，也叫委托模式，其定义是给某一个对象提供一个代理对象，并由代理对象控制对原对象的引用。它包含了三个角色：
        -   Subject：抽象主题角色。可以是抽象类也可以是接口，是一个最普通的业务类型定义。
        -   RealSubject：具体主题角色，也就是被代理的对象，是业务逻辑的具体执行者。
        -   Proxy：代理主题角色。负责读具体主题角色的引用，通过真实角色的业务逻辑方法来实现抽象方法，并在前后可以附加自己的操作。
    -   现实生活中的演员就是一个很好的例子
        -   一般来说，演员主要的工作就是演戏，其他的事情就交给了经纪人去做。让经纪人来负责安排演员演什么戏，拿多少钱，安排档期等等除了演戏之外的琐事
        -   对于这个例子。演员的主要业务就是演戏，那么演戏就是**Subject**；处理各种大事小事的经纪人就是**Proxy**；负责演戏的演员就是**RealSubject**
-   代理模式的优缺点
    -   优点：分工明确，低耦合，扩展性强
    -   缺点：加入代理会导致性能的降低，实现代理需要额外的工作
-   代理模式的应用场景
    -   1、远程代理。 2、虚拟代理。 3、Copy-on-Write 代理。 4、保护（Protect or Access）代理。 5、Cache代理。 6、防火墙（Firewall）代理。 7、同步化（Synchronization）代理。 8、智能引用（Smart Reference）代理。
-   如何实现代理模式？
    -   增加一个中间层，实现与被代理类的组合

### 8.3 静态代理

-   定义一个最普通的业务类型，Subject

    ```java
    public interface Actor {
        public void act();
    }
    ```

-   定义一个实现了业务的具体执行者，也就是RealSubject

    ```java
    public class Star implements Actor {
    
        private String name;
    
        public Star(String name) {
            this.name = name;
        }
    
        @Override
        public void act() {
            System.out.println(name + "来演戏");
        }
    }
    ```

-   定义代理类，也就是Proxy，代理类负责其他和演戏无关的事情的处理

    ```java
    public class Agent implements Actor {
    
        private Star star;
    
        public void setStar(Star star) {
            this.star = star;
        }
    
        private void arrange() {
            System.out.println("安排档期");
        }
    
        private void signContact() {
            System.out.println("签约");
        }
    
        private void paid() {
            System.out.println("获取片酬");
        }
    
        @Override
        public void act() {
            arrange();
            signContact();
            star.act();
            paid();
        }
    }
    ```

-   运行测试

    ```java
    @Test
    public void test01() {
        Agent agent = new Agent();
        agent.setStar(new Star("刘德华"));
        agent.act();
    }
    ```

    ![image-20201122163120502](https://gitee.com/primabrucexu/image/raw/main/image/20201122163120.png)

-   小结：

    -   代理类Agent中的一些处理方法通常会设置成对外不可见的。

    -   当外界需要找演员来演戏的时候，就只需要把演员丢给Agent，然后Agent会来安排其他的事情。

    -   虽然看上去是调用了Agent的act方法，但是最后关于act的实现还是是用了Star类中的act的实现。

    -   所以对于剧组（使用者）看不出来是Agent，所以说Agent是Star的代理类

        

### 8.4 动态代理

-   既然有静态代理，那么就势必会有动态代理
-   为什么会出现动态代理？
    -   对于静态代理来说，代理的是一个RealSubject。我们继续沿用静态代理的情景。
        -   我们先横向扩展业务。以前只有Star去演戏，这个时候我们有了Dancer可以去跳舞，有了Joker去表演喜剧，等一系列的扩展。
        -   这个时候我们可以看出，由于我们的Agent代理的角色是在代码中写死的Star，那么我们想要代理Dancer和Joker就势必要重新编写他们的代理类，
        -   这意味着什么？这意味着每有一个新的实现类被创建，静态代理类就要相应的增加一个。这样就大大增加了工作量
    -   那么能不能将代理类设计成代理的是一个`Interface` ，这样对于相同接口的不同实现类，就不要重新在编写一个代理类了。
    -   在这样的需求下，动态代理出现了
-   动态代理和静态代理的区别
  
-   ==静态代理，代理的是一个实现了具体业务的类；而动态代理，代理的是一个接口==，所有实现了这个接口的类都可以丢给动态代理
  
-   动态代理分为两类 : 一类是基于接口动态代理 , 一类是基于类的动态代理

    -   基于接口的动态代理----JDK动态代理

    -   基于类的动态代理--cglib

    -   现在用的比较多的是 javasist 来生成动态代理

        

>   实现基于接口的动态代理类

-   必要的知识

    -   ==***接口 `InvocationHandler`  是由代理实例的调用处理程序实现的接口。***==

        -   每个代理实例都有一个关联的调用处理程序。在代理实例上调用方法时，对该方法调用进行编码，并将其分派到其调用处理程序的invoke方法。

    -   ***==类 `Proxy` 提供静态方法来创建对象，这些对象的行为类似于接口实例，但允许自定义方法调用。==***

        -   Proxy类是在运行时创建的类，该类实现指定的接口列表（称为代理接口）。
        -   **代理实例是Proxy类的实例。每个代理实例都有一个关联的调用处理程序对象，该对象实现接口InvocationHandler**。调用处理程序将适当地处理编码的方法调用，并且返回的结果将作为代理实例上方法调用的结果返回。

        

-   定义一个普通的业务，这里沿用静态代理的Subject

    ~~~java
    public interface Actor {
        public void act();
    }
    ~~~

-   实现这个业务的具体实现类，Star，Dancer，Joker

    ```java
    public class Star implements Actor {
    
        private String name;
    
        public Star(String name) {
            this.name = name;
        }
    
        @Override
        public void act() {
            System.out.println(name + "来演戏");
        }
    }
    ```

    ```java
    public class Dancer implements Actor {
        private String name;
    
        public Dancer(String name) {
            this.name = name;
        }
    
        @Override
        public void act() {
            System.out.println(name + "跳舞");
        }
    }	
    ```

    ~~~java
    public class Joker implements Actor {
    
        private String name;
    
        public Joker(String name) {
            this.name = name;
        }
    
        @Override
        public void act() {
            System.out.println(name + "表演喜剧");
        }
    }
    ~~~

-   实现动态代理

    ~~~java
    package com.pbx.dynamicProxy;
    
    import java.lang.reflect.InvocationHandler;
    import java.lang.reflect.Method;
    import java.lang.reflect.Proxy;
    
    
    /**
     * @author BruceXu
     * @date 2020/11/22
     */
    public class DynamicAgent implements InvocationHandler {
    
        private Actor actor;
    
        public void setActor(Actor actor) {
            this.actor = actor;
        }
    
        public Actor getActor() {
            return (Actor) Proxy.newProxyInstance(this.getClass().getClassLoader(), actor.getClass().getInterfaces(), this);
        }
    
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            arrange();
            signContact();
            Object res = method.invoke(actor, args);
            paid();
            return res;
        }
    
        private void arrange() {
            System.out.println("安排档期");
        }
    
        private void signContact() {
            System.out.println("签约");
        }
    
        private void paid() {
            System.out.println("获取片酬");
        }
    }
    ~~~

    -   **对于这个动态代理类，我们并没有想创建静态代理一样去创建一个动态代理的类。我们实际编写的是一个动态处理器，真正的代理对象，在运行时由JDK通过反射去创建出来**

-   测试

    ```java
    @Test
    public void test01() {
        DynamicAgent agent = new DynamicAgent();
        agent.setActor(new Dancer("芭蕾舞演员"));
        Actor proxiedActor = agent.getProxy();
        proxiedActor.act();
        System.out.println("===========================");
        agent.setActor(new Star("芭蕾舞演员"));
        proxiedActor = agent.getProxy();
        proxiedActor.act();
        System.out.println("===========================");
        agent.setActor(new Joker("芭蕾舞演员"));
        proxiedActor = agent.getProxy();
        proxiedActor.act();
    }
    ```

    ![image-20201122212459639](https://gitee.com/primabrucexu/image/raw/main/image/20201122212459.png)

### 8.5 AOP和代理模式之间的关系

-   AOP和代理模式都是为了解决同一个问题
    -   在不破坏原始代码的情况下，对已有代码进扩展，增加新功能
    -   或者对原有代码进行解耦，降低业务处理时各部分的关联度

-   AOP的底层通过代理模式实现，了解代理模式的工作方式，对于学习AOP很有帮助

    

## 九、AOP

### 9.1 AOP概述

-   什么是AOP？关键词：==解耦==

    -   可以通过预编译方式和运行期动态代理实现在**不修改源代码**的情况下给程序动态**统一添加功能**的一种技术
    -   利用AOP可以对业务逻辑的各个部分进行隔离，从而使得业务逻辑各部分之间的**耦合度降低**，提高程序的可重用性，同时提高了开发的效率。
    -   AOP可以看做是OOP的衍生

-   详细阐述

    -   如图所示，一个常见的Web业务执行流程中

        ![image-20201122152943616](https://gitee.com/primabrucexu/image/raw/main/image/20201122152943.png)

        -   一个WEB业务，被分成了DAO层、Service层等。
        -   这么做的好处就是明确了每一层的职责， 对于这一层的来说，我只需要做好关于我自己的事情就好了，然后把对象交给下一层。至于下一层会干什么和我没有关系。**（高内聚，低耦合）**

    -   web业务的分层开发，可以看做是将一个业务流程，用刀切开，每一层之间就是一个切面。==**编程中，对象与对象之间，方法与方法之间，模块与模块之间也都是是一个个切面。**==

    -   假设我们在某一层中，需要做这么一些处理：记录日志，安全性检验，权限验证等。那么对于这一层的的业务来说，也相当于被分成了这么几层

        ![image-20201122154529234](https://gitee.com/primabrucexu/image/raw/main/image/20201122154529.png)

        -   从这个流程图中可以看到，真正的业务处理中，执行业务的功能代码只占了一小部分，取余的都是一些附加功能代码。当这个这个业务多次重复执行的时候，这部分附加功能可能完全都是一样的，没有必要在重复写一遍代码。

        -   我们可以将这些附加的功能全部抽取出来，让这个业务只专注于自己的业务处理。

        -   就像下图展示的一样，我们将附加功能抽取出来，通过Spring将这些附加功能在注入到需要调用的地方，这样，业务就可以专心的处理自己的业务了，不需要关注其他功能的处理。这样，就可以算作一次简单的AOP

        -   **上面操作的流程，就叫做AOP。** 附加功能的抽取就是切面，调用附加功能的地方就是切入点

            ![image-20201122155313795](https://gitee.com/primabrucexu/image/raw/main/image/20201122155313.png)



### 9.2 Spring中的AOP

-   ==Spring提供了声明式事务，同时支持用户自定义切面==

-   Spring中AOP的一些概念，（了解就好了，开发的时候用不到）
    -   横切关注点：跨越应用程序多个模块的方法或功能。即是，与我们业务逻辑无关的，但是我们需要关注的部分，就是横切关注点。如日志 , 安全 , 缓存 , 事务等等 ....
    -   切面（ASPECT）：横切关注点 被模块化 的特殊对象。即，它是一个类。
    -   通知（Advice）：切面必须要完成的工作。即，它是类中的一个方法。
    -   目标（Target）：被通知对象。
    -   代理（Proxy）：向目标对象应用通知之后创建的对象。
    -   切入点（PointCut）：切面通知 执行的 “地点”的定义。
    -   连接点（JointPoint）：与切入点匹配的执行点。

-   下面这张图很好的说明了各种概念到底是什么玩意儿

    ![在这里插入图片描述](https://img-blog.csdnimg.cn/20190225155448621.jpg?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzQxOTgxMTA3,size_16,color_FFFFFF,t_70)

-   SpringAOP中支持的5中Advice类型

    ![image-20201124193853910](https://gitee.com/primabrucexu/image/raw/main/image/20201124193853.png)

### 9.3 在Spring中实现AOP

-   在Spring中，有3种不同实现AOP的方式
    -   利用Spring中自带的Advice API去实现advice
    -   自定义切面类，在切面类中实现各种advice
    -   使用注解开发



#### 方式一：使用Spring API

-   定义我们的业务接口和我们的业务实现类

    -   业务接口

        ```java
        public interface UserService {
            void add();
            void delete();
            void update();
            void select();
        }
        ```

    -   业务实现类

        ```java
        public class UserServiceImpl implements UserService {
            @Override
            public void add() {
                System.out.println("UserServiceImpl::add()");
            }
        
            @Override
            public void delete() {
                System.out.println("UserServiceImpl::delete()");
            }
        
            @Override
            public void update() {
                System.out.println("UserServiceImpl::update()");
            }
        
            @Override
            public void select() {
                System.out.println("UserServiceImpl::select()");
            }
        }
        ```

-   根据需求实现advice

    -   实现一个前置通知

        ```java
        package com.pbx.advice;
        
        import org.springframework.aop.MethodBeforeAdvice;
        
        import java.lang.reflect.Method;
        
        /**
         * @author BruceXu
         * @date 2020/11/24
         */
        public class beforeLog implements MethodBeforeAdvice {
            @Override
            public void before(Method method, Object[] objects, Object o) throws Throwable {
                System.out.println("在" + o.getClass().getName() + "::" + method.getName() + "执行前");
            }
        }
        
        ```

    -   实现一个后置通知

        ```java
        package com.pbx.advice;
        
        import org.springframework.aop.AfterReturningAdvice;
        
        import java.lang.reflect.Method;
        
        /**
         * @author BruceXu
         * @date 2020/11/24
         */
        public class afterLog implements AfterReturningAdvice {
        
            @Override
            public void afterReturning(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                System.out.println("在" + target.getClass().getName() + "::" + method.getName() + "执行后，结果为：" + returnValue);
            }
        }
        
        ```

-   在Spring配置文件中将advice和aspect相关联

    ```xml
    <!--记得提前导入aop的约束-->
    
    <!--定义bean-->
    <bean id="userService" class="com.pbx.service.UserServiceImpl"/>
    <bean id="beforeAdvice" class="com.pbx.advice.beforeLog"/>
    <bean id="afterAdvice" class="com.pbx.advice.afterLog"/>
    
    <!--配置aop-->
    <aop:config>
        <aop:pointcut id="point1" expression="execution(* com.pbx.service.UserServiceImpl.*(..))"/>
        <aop:advisor advice-ref="beforeAdvice" pointcut-ref="point1"/>
        <aop:advisor advice-ref="afterAdvice" pointcut-ref="point1"/>
    </aop:config>
    ```

-   测试

    ```java
    @Test
    public void test01() {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop01.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
    }
    ```

    ![image-20201124203001671](https://gitee.com/primabrucexu/image/raw/main/image/20201124203001.png)



#### 方式二：自定义切面类

-   沿用之前的业务接口和实现类

-   自定义切面类

    ~~~java
    public class MyAspect {
    
        public void before() {
            System.out.println("---- before advice ----");
        }
    
        public void after() {
            System.out.println("---- after advice ----");
        }
    
    }
    ~~~

-   配置AOP

    ```xml
    <!--定义bean-->
    <bean id="MyAspect" class="com.pbx.aspect.MyAspect"/>
    <bean id="userService" class="com.pbx.service.UserServiceImpl"/>
    
    <!--配置AOP-->
    <aop:config>
        <aop:aspect ref="MyAspect">
            <aop:pointcut id="point1" expression="execution(* com.pbx.service.UserServiceImpl.*(..))"/>
            <aop:before method="before" pointcut-ref="point1"/>
            <aop:after method="after" pointcut-ref="point1"/>
        </aop:aspect>
    </aop:config>
    ```

-   测试

    ```java
    @Test
    public void test02() {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop02.xml");
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
    }
    ```

    ![image-20201124203930418](https://gitee.com/primabrucexu/image/raw/main/image/20201124203930.png)



#### 方式三：注解实现aop

-   实现一个使用注解的增强类

    ```java
    package com.pbx.aspect;
    
    import org.aspectj.lang.ProceedingJoinPoint;
    import org.aspectj.lang.annotation.After;
    import org.aspectj.lang.annotation.Around;
    import org.aspectj.lang.annotation.Aspect;
    import org.aspectj.lang.annotation.Before;
    
    /**
     * @author BruceXu
     * @date 2020/11/24
     */
    
    @Aspect
    public class MyAspect2 {
    
        @Before("execution(* com.pbx.service.UserServiceImpl.*(..))")
        public void before() {
            System.out.println("---- before advice ----");
        }
    
        @After("execution(* com.pbx.service.UserServiceImpl.*(..))")
        public void after() {
            System.out.println("---- after advice ----");
        }
    
        @Around("execution(* com.pbx.service.UserServiceImpl.*(..))")
        public Object surround(ProceedingJoinPoint point) throws Throwable {
            System.out.println("---- before around ----");
    
            System.out.println("方法签名为："+point.getSignature());
            Object proceed = point.proceed();
            System.out.println("---- after around ----");
            return proceed;
        }
    
    }
    ```

-   配置Spring

    ```xml
    <!--注册bean-->
    <bean id="userService3" class="com.pbx.service.UserServiceImpl"/>
    <bean id="Aspect2" class="com.pbx.aspect.MyAspect2"/>
    <!--自动代理-->
    <aop:aspectj-autoproxy/>
    ```

-   测试

    ```java
    @Test
    public void test03() {
        ApplicationContext context = new ClassPathXmlApplicationContext("aop03.xml");
        UserService userService = context.getBean("userService3", UserService.class);
        userService.add();
    }
    ```

    ![image-20201124205431696](https://gitee.com/primabrucexu/image/raw/main/image/20201124205431.png)

---

## 十、整合MyBatis

**准备工作**

-   导入Maven依赖，配置静态资源过滤

    ~~~xml
    <dependencies>
        <!--Spring框架-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.1</version>
        </dependency>
    
        <!--MyBatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.6</version>
        </dependency>
    
        <!--MySQL驱动-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.22</version>
        </dependency>
    
        <!--Spring数据库组件-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.3.1</version>
        </dependency>
    
        <!--Spring整合MyBatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.6</version>
        </dependency>
    
        <!--AOP-->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.6</version>
        </dependency>
    
        <!--测试组件-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <!--配置静态文件过滤-->
    <build>
        <resources>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>false</filtering>
            </resource>
        </resources>
    </build>
    ~~~

-   创建实体类

    ```java
    public class User {
        private int id;
        private String name;
        private String pwd;
    }
    ```

### 10.1 旧方式使用MyBatis

-   配置MyBatis

    ~~~xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE configuration
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
        
        <typeAliases>
            <package name="com.pbx.pojo"/>
        </typeAliases>
        
        <environments default="development">
            <environment id="development">
                <transactionManager type="JDBC"/>
                <dataSource type="POOLED">
                    <property name="driver" value="com.mysql.cj.jdbc.Driver"/>
                    <property name="url" value="jdbc:mysql://localhost:3306/mybatis?
                    useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=GMT%2B8"/>
                    <property name="username" value="root"/>
                    <property name="password" value="123456"/>
                </dataSource>
            </environment>
        </environments>
        
        <!--注册映射器-->
        <mappers>
            <package name="com.pbx.mapper"/>
        </mappers>
        
    </configuration>
    ~~~

-   Mapper接口

    ```java
    public interface UserMapper {
        public List<User> getAllUser();
    }
    ```

-   Mapper.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.pbx.mapper.UserMapper">
        <select id="getAllUser" resultType="com.pbx.pojo.User">
            select * from mybatis.user
        </select>
    </mapper>
    ```

-   测试

    ```java
    @Test
    public void test1() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> allUser = mapper.getAllUser();
        for (User user : allUser) {
            System.out.println(user);
        }
    }
    ```

    ![image-20201126151410864](https://gitee.com/primabrucexu/image/raw/main/image/20201126151410.png)

-   小结：传统使用MyBatis的步骤
    -   编写MyBatis配置文件
    -   制作Mapper接口和Mapper.xml映射文件
    -   按照配置文件创建SqlSessionFactory
    -   使用

### 10.2 MyBatis-Spring

-   MyBatis-Spring 是什么
    -   MyBatis-Spring 会帮助你将 MyBatis 代码无缝地整合到 Spring 中。它将允许 MyBatis 参与到 Spring 的事务管理之中，创建映射器 mapper 和 `SqlSession` 并注入到 bean 中，以及将 Mybatis 的异常转换为 Spring 的 `DataAccessException`。 最终，可以做到应用代码不依赖于 MyBatis，Spring 或 MyBatis-Spring。
    -   简单来说，这个东西可以帮助我们将MyBatis快速整合到Spring中
-   重要组件：`SqlSessionFactoryBean` 和 `SqlSessionTemplate` 、 `SqlSessionDaoSupport`
    -   `SqlSessionFactoryBean` 
        -   他是用来创建`SqlSessionFactory` 的



### 10.3 使用 SqlSessionTemplate 整合

-   使用Spring配置数据源

    ```xml
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="url" value="jdbc:mysql://localhost:3306/mybatis?
                useSSL=true&amp;useUnicode=true&amp;characterEncoding=utf8&amp;serverTimezone=GMT%2B8"/>
        <property name="driverClassName" value="com.mysql.cj.jdbc.Driver"/>
        <property name="username" value="root"/>
        <property name="password" value="123456"/>
    </bean>
    ```

-   使用 SqlSessionFactoryBean 创建 SqlSessionFactory

    ```xml
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <!--关联MyBatis配置文件，使得配置文件和Spring文件可以同时生效-->
        <property name="configLocation" value="classpath:mybatis-config.xml"/>
        <!--注册mapper.xml文件-->
        <property name="mapperLocations" value="classpath:com/pbx/mapper/*.xml"/>
    </bean>
    ```

-   注入SqlSessionTemplate

    -   SqlSessionTemplate没有setter方法，所以只能通过构造器注入

    ```xml
    <!--SqlSessionTemplate-->
    <bean id="sqlSession" class="org.mybatis.spring.SqlSessionTemplate">
        <constructor-arg index="0" ref="sqlSessionFactory"/>
    </bean>
    ```

-   实现一个数据映射器类，并将它交由Spring托管

    -   ==它会实现mapper接口。这么做是为了给Spring提供可供管理的对象，我们使用MyBatis的时候，就是使用这个对象==

    ```java
    public class UserMapperImpl implements UserMapper {
    
        private SqlSessionTemplate sqlSession;
    
        public void setSqlSession(SqlSessionTemplate sqlSession) {
            this.sqlSession = sqlSession;
        }
    
        @Override
        public List<User> getAllUser() {
            return sqlSession.getMapper(UserMapper.class).getAllUser();
        }
    }
    ```

-   测试

      ~~~java
    @Test
    public void test2() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper mapper = context.getBean("userMapperImpl", UserMapper.class);
        List<User> allUser = mapper.getAllUser();
        for (User user : allUser) {
            System.out.println(user);
        }
    }
    ~~~

    ![image-20201126160913680](https://gitee.com/primabrucexu/image/raw/main/image/20201126160913.png)

-   注意：在被Spring管理之后，记得删除MyBatis配置文件中被整合的部分，不然会出现配置冲突的问题

    -   托管之后的配置文件

    ~~~xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE configuration
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
        <typeAliases>
            <package name="com.pbx.pojo"/>
        </typeAliases>
    </configuration>
    ~~~



### 10.4 使用 SqlSessionDaoSupport 整合

-   `SqlSessionDaoSupport` 是一个抽象的支持类，用来为你提供 `SqlSession`。调用 `getSqlSession()` 方法你会得到一个 `SqlSessionTemplate`，之后可以用于执行 SQL 方法，就像下面这样:

    ```java
    public class UserDaoImpl extends SqlSessionDaoSupport implements UserDao {
      public User getUser(String userId) {
        return getSqlSession().selectOne("org.mybatis.spring.sample.mapper.UserMapper.getUser", userId);
      }
    }
    ```

-   在这个类里面，通常更倾向于使用 `MapperFactoryBean`，因为它不需要额外的代码。但是，如果你需要在 DAO 中做其它非 MyBatis 的工作或需要一个非抽象的实现类，那么这个类就很有用了。

-   `SqlSessionDaoSupport` 需要通过属性设置一个 `sqlSessionFactory` 或 `SqlSessionTemplate`。如果两个属性都被设置了，那么 `SqlSessionFactory` 将被忽略。

-   假设类 `UserMapperImpl` 是 `SqlSessionDaoSupport` 的子类，可以编写如下的 Spring 配置来执行设置：

    ~~~xml
    <bean id="userDao" class="org.mybatis.spring.sample.dao.UserDaoImpl">
      <property name="sqlSessionFactory" ref="sqlSessionFactory" />
    </bean>
    ~~~

    

## 十一、声明式事务

### 11.1 什么是事务？

-   ==事务就是把一系列的数据库操作看做一个最小的、不可分割的动作，要么都成功，要么都失败。用于确保数据的一致性和完整性==
-   事务的ACID原则
    -   **原子性（Atomic）**
        -   一个事务（transaction）中的所有操作，要么全部完成，要么全部不完成，不会结束在中间某个环节。事务在执行过程中发生错误，会被回滚（Rollback）到事务开始前的状态，就像这个事务从来没有执行过一样。
    -   **一致性（Consistency）**
        -   在事务开始之前和事务结束以后，数据库的完整性没有被破坏。这表示写入的资料必须完全符合所有的预设规则，这包含资料的精确度、串联性以及后续数据库可以自发性地完成预定的工作。
        -   例如：转账过程中，AB两个共1000元，那么不管事务怎么执行，两个人的钱的总和一定是1000不会变。除非有别的新事务发生
    -   **隔离性（Isolation）**
        -   数据库允许多个并发事务同时对其数据进行读写和修改的能力，隔离性可以防止多个事务并发执行时由于交叉执行而导致数据的不一致。事务隔离分为不同级别，包括读未提交（Read uncommitted）、读提交（read committed）、可重复读（repeatable read）和串行化（Serializable）。
    -   **持久性（Durability）**
        -   事务处理结束后，对数据的修改就是永久的，即便系统故障也不会丢失。如果在系统故障前，事务没有提交，则回滚。不然，则保证更改


### 11.2 Spring的事务管理

-   Spring在不同的事务管理API之上定义了一个抽象层，使得开发人员不必了解底层的事务管理API就可以使用Spring的事务管理机制。
-   Spring支持编程式事务和声明式事务



**编程式事务**

-   最传统的事务管理方法
-   将涉及到事务提交和回滚的代码嵌入到业务执行过程中
-   每一个独立的业务都需要包含

**声明式事务（最常用）**

-   通过AOP的方式，将事务管理插入到业务执行过程中
-   简单，解耦，模块化



### 11.3 声明式事务

-   注意

    -   要使用Spring进行事务管理，需要配置Spring的事务管理器

        ~~~xml
        <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
          <constructor-arg ref="dataSource" />
        </bean>
        ~~~

    -   在配置文件中导入Spring中对事务的约束

        ~~~xml
        xmlns:tx="http://www.springframework.org/schema/tx"
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd
        ~~~

-   开启Spring事务管理

    ```xml
    <!--开启Spring事务管理-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    ```

-   配置事务通知，也就是说什么样的操作需要事务

    ```xml
    <!--配置事务通知-->
    <tx:advice id="interceptor" transaction-manager="transactionManager">
        <tx:attributes>
            <!--配置方法要使用的事务隔离等级和传播特性，一般使用默认的即可-->
            <tx:method name="*" propagation="REQUIRED" isolation="DEFAULT"/>
        </tx:attributes>
    </tx:advice>
    ```

-   配置AOP

    ```xml
    <aop:config>
        <aop:pointcut id="transaction" expression="execution(* com.pbx.mapper.*.*(..))"/>
        <aop:advisor advice-ref="interceptor" pointcut-ref="transaction"/>
    </aop:config>
    ```

-   测试

    -   test是在一个业务实现类中的具体业务处理

        ```java
        public void test() {
            UserMapper mapper = getSqlSession().getMapper(UserMapper.class);
            mapper.addUser(new User(5, "吴尊", "1235"));
            mapper.deleteUser(12);
            for (User user : mapper.getAllUser()) {
                System.out.println(user);
            }
        }
        ```

    -   测试类，因为没有12条数据，所以删除时会报错

        ```java
        @Test
        public void test4() {
            UserMapperImpl2 bean = context.getBean("userMapperImpl2", UserMapperImpl2.class);
            bean.test();
        }
        ```

        ![image-20201129232630137](https://gitee.com/primabrucexu/image/raw/main/image/20201129232630.png)

    -   执行完之后，可以看到id为5的数据并没有添加到数据库中

















