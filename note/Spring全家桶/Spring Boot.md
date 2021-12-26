SpringBoot

## 1 SpringBoot入门

### 1.1 SpringBoot简介

>   什么是Spring？

-   Spring是为了简化企业开发负责性而创建的一个框架



>   Spring如何简化Java开发的？

1.  基于pojo的轻量级编程，实现了**最小入侵**，所有的东西都可以被Spring容器进行管理
2.  **IoC**和**面向接口**编程降低了各个组件之间的耦合性
3.  通过 **AOP** 实现了业务功能的横向扩展
4.  通过模板（Template）减少重复工作



>   SpringBoot是什么？

-   SpringBoot 是类似于 SpringMVC 的一种 java web 框架
-   SpringBoot 通过自动装配，减少了大量的Spring配置。因为使用了自动装配，所以SpringBoot 中**约定大于配置**



>   SpringBoot的优点

-   为所有 Spring 开发提供快速且广泛可访问的入门体验。
-   开箱即用，使用各种默认配置来简化项目配置
-   提供大型项目所共有的一系列非功能特性（如嵌入式服务器、security）
-   绝对没有代码生成，也不需要 XML 配置

### 1.2 Hello SpringBoot

-   新建一个IDEA项目，选择Spring Initiallizr

    ![image-20201216164116953](https://gitee.com/primabrucexu/image/raw/main/image/20201216164117.png)

-   填好项目配置之后，记得在这里选上spring web的依赖

    ![image-20201216164313108](https://gitee.com/primabrucexu/image/raw/main/image/20201216164313.png)

-   在Finish之后，idea会自动完成SpringBoot项目的创建。如果是第一次创建SpringBoot项目的话，需要下载相关依赖，这个要等待一会

-   随便写一个接口

    -   Controller

        ```java
        @RestController
        public class HelloController {
            
            @RequestMapping("/hello")
            public String hello() {
                return "Hello SpringBoot";
            }
            
        }
        ```

-   点击运行

    ![image-20201216165326496](https://gitee.com/primabrucexu/image/raw/main/image/20201216165326.png)

-   可以看到，我只写了一个Controller来处理一个请求，没有做任何多余的工作，没有配置SpringMVC，没有配置web.xml 。。。。。。

-   这就是SpringBoot的强大之处，快速部署

>   彩蛋

-   在resources目录下，新建一个banner.txt文件，即可更改SpringBoot的启动界面

-   原界面

    ![image-20201216165642020](https://gitee.com/primabrucexu/image/raw/main/image/20201216165642.png)

-   新界面

    ![image-20201216173745482](https://gitee.com/primabrucexu/image/raw/main/image/20201216173745.png)



## 2 初看自动装配

### 2.1 pom.xml

-   SpringBoot的maven配置文件主要由这么几个部分组成

-   父项目依赖

    ~~~xml
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.4.1</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
    ~~~

    -   父项目里面有所有SpringBoot整合的依赖，我们如果需要添加其他支持，只需要从中引用对应的启动器即可

-   启动器

    ~~~xml
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    ~~~

    -   SpringBoot将所有支持的场景都进行抽取，然后做成了一个个单独的启动器。
    -   SpringBoot中的所有启动器都已经配置好了必要的参数，以此实现 **开箱即用**



### 2.2 主启动类

-   代码

    ~~~java
    @SpringBootApplication
    public class Springboot01HellospringbootApplication {
    
        public static void main(String[] args) {
            SpringApplication.run(Springboot01HellospringbootApplication.class, args);
        }
    
    }
    ~~~

-   这就是所有SpringBoot应用程序的主入口，执行主启动类的main方法就可以启动SpringBoot

**深入分析**

>   @SpringBootApplication

-   进入源码查看

    ![image-20201216202817141](https://gitee.com/primabrucexu/image/raw/main/image/20201216202817.png)

-   `@ComponentScan`

    -   ComponentScan这个我们都熟悉。在Spring中，我们通过配置这个属性来使得我们可以使用 `@Component` 等注解进行开发。由此可以推断这个注解的作用是扫描我们SpringBoot中的注解用的

-   `@SpringBootConfiguration`

    -   阅读注释

        ~~~java
         Indicates that a class provides Spring Boot application
         {@link Configuration @Configuration}. Can be used as an alternative to the Spring's
         standard {@code @Configuration} annotation so that configuration can be found
         automatically (for example in tests).
         <p>
         Application should only ever include <em>one</em> {@code @SpringBootConfiguration} and
         most idiomatic Spring Boot applications will inherit it from
         {@code @SpringBootApplication}.
        
         @author Phillip Webb
         @author Andy Wilkinson
         @since 1.4.0
        
        ~~~

    -   通过注释，我们可以得知这个注解的作用就是告诉Spring，这个类是SpringBoot的应用程序，需要被扫描

-   `@EnableAutoConfiguration`

    -   这个注解的作用是开启Spring的自动配置，尝试推导出你要使用哪些bean。同时可以用于指定不需要自动配置的类

    -   同时引用了一个自动配置选择器的类`AutoConfigurationImportSelector` 

    -   源码中的注释同时还提到了，所有自动配置的类，都是Spring中的bean，他们都用下面的三个注解进行标注

        ![image-20201216204810751](https://gitee.com/primabrucexu/image/raw/main/image/20201216204810.png)

-   `AutoConfigurationImportSelector`

    -   这个类的就是SpringBoot的自动装配的主力军，他的作用就是完成自动装配

    -   `getCandidateConfigurations()` 用于获取需要配自动配置的类的名字，是完成自动装配的关键。比如说web项目的话，需要配置SpringMVC中的DispatcherServlet。

        ~~~java
        /**
         * Return the auto-configuration class names that should be considered. By default
         * this method will load candidates using {@link SpringFactoriesLoader} with
         * {@link #getSpringFactoriesLoaderFactoryClass()}.
         * @param metadata the source metadata
         * @param attributes the {@link #getAttributes(AnnotationMetadata) annotation
         * attributes}
         * @return a list of candidate configurations
         */
        protected List<String> getCandidateConfigurations(AnnotationMetadata metadata, AnnotationAttributes attributes) {
            List<String> configurations = SpringFactoriesLoader.loadFactoryNames(getSpringFactoriesLoaderFactoryClass(),
                    getBeanClassLoader());
            Assert.notEmpty(configurations, "No auto configuration classes found in META-INF/spring.factories. If you "
                    + "are using a custom packaging, make sure that file is correct.");
            return configurations;
        }
        ~~~

-   `SpringFactoriesLoader`

    -   这个类是Spring内部的工厂加载类，用来加载各种工厂的。需要被加载的工厂都以 `properties` 的格式存储在 `META-INF/spring.factories` 文件中
    -   得知这点，我们找到`AutoConfigurationImportSelector`所在的jar包，找到 `spring.factories` 文件

-   `spring-boot-autoconfigure-2.4.1` 包中的`spring.factories`

    ![image-20201216212204305](https://gitee.com/primabrucexu/image/raw/main/image/20201216212204.png)

    -   找到这个文件之后，就一目了然了。原来实现自动装配的都是这些类。他们这些类都是Java配置类（Java配置类就是通过Java的代码配置Spring）

-   ==SpringBoot自动装配小结==

    ![image-20201217162251050](https://gitee.com/primabrucexu/image/raw/main/image/20201217162251.png)

    

### 2.3 SpringApplication

-   SpringApplication这个类主要做了这么几个事情
    -   推断应用类型，看看是不是WEB应用
    -   查找并加载所有可用的初始化值，设置到initializers中
    -   找出所有的应用程序监听器，设置到listeners属性中
    -   加载main方法的定义类，找到运行的主类



## 3 配置SpringBoot

### 3.1 外部配置文件

-   SpringBoot在启动时自动到以下路径去搜索配置文件。其中，配置文件的名称都是统一的 `application.xxx` 

    -   `classpath` ，也就是 `resources` 目录，这也是生成SpringBoot项目时默认的配置文件位置。
    -   `classpath/config` ，也就是 `resources/config` 
    -   `currentpath`，也就是当前项目所在的目录
    -   `currentpath/config` ，也就是项目根目录下的 `config`
    -   **注意：这四个位置的配置文件优先级由低到高。SpringBoot会使用高优先级的配置文件**

-   SpringBoot支持 `properties` 和 `yaml` 两种格式的配置文件。其中，官方推荐我们使用 `yaml` 

  ​    

### 3.2 YAML

>   什么是YAML

-   YAML 是 "YAML Ain't a Markup Language"（YAML 不是一种标记语言）的递归缩写。在开发的这种语言时，YAML 的意思其实是："Yet Another Markup Language"（仍是一种标记语言）。
-   YAML 的语法和其他高级语言类似，并且可以简单表示变量、数组、map等数据形态。它使用空白符号缩进和大量依赖外观的特色，特别适合用来表达或编辑数据结构、各种配置文件、倾印调试内容、文件大纲（例如：许多电子邮件标题格式和YAML非常接近）。



>   语法

-   大小写敏感

-   使用缩进表示层级关系，对于缩进非常严格（只允许使用空格缩进）

-   缩进的空格数不重要，只要相同层级的元素左对齐即可

-   变量，`key: value` ，其中**冒号后面必须跟一个空格**

-   数组，`key: [v1,v2,v3,v4]` 或在行开始使用 `-` 

-   map，`key: {k1: v1, k2: v2}`

-   常见变量类型示例

    ~~~~yaml
    boolean: 
        - TRUE  #true,True都可以
        - FALSE  #false，False都可以
    float:
        - 3.14
        - 6.8523015e+5  #可以使用科学计数法
    int:
        - 123
        - 0b1010_0111_0100_1010_1110    #二进制表示
    null:
        nodeName: 'node'
        parent: ~  #使用~表示null
    string:
        - 哈哈
        - 'Hello world'  #可以使用双引号或者单引号包裹特殊字符
        - newline
          newline2    #字符串可以拆成多行，每一行会被转化成一个空格
    date:
        - 2018-02-17    #日期必须使用ISO 8601格式，即yyyy-MM-dd
    datetime: 
        -  2018-02-17T15:02:31+08:00    #时间使用ISO 8601格式，时间和日期之间使用T连接，最后使用+代表时区
    ~~~~



### 3.3 注入YAML

-   `yaml` 的强大之处在于他可以给任意的一个Java类注入属性。

-   示例

    -   编写一个实体类

        ~~~java
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        @Component
        @ConfigurationProperties("people")
        public class People {
            private String name;
            private int age;
            private List<String> car;
            private String[] wife;
            private Map<String, String> map;
        }
        ~~~

    -   配置yaml文件

        ~~~yaml
        people:
          name: 张三
          age: 18
          car: [宝马, 奔驰, 兰博基尼]
          wife: [蕾姆, 2B]
          map: {k1: v1, k2: v2}
        ~~~

    -   测试

        ~~~java
        @SpringBootTest
        class Springboot01HelloSpringBootApplicationTests {
        
            @Autowired
            private People people;
        
            @Test
            void contextLoads() {
                System.out.println(people);
            }
        
        }
        ~~~

        

        ![image-20201217155654546](https://gitee.com/primabrucexu/image/raw/main/image/20201217155654.png)



## 4 SpringBoot操作数据库

### 4.1 SpringData

>   什么是SpringData

-   SpringData是Spring全家桶中专门用于处理数据访问层的组件了，它使用Spring的方式对所有SQL和NoSQL数据库进行统一处理
-   在所有Spring项目的底层，都统一使用SpringData处理各种数据库



### 4.2 集成JDBC

>   导入对应的依赖

~~~xml
<dependencies>
    <!--JDBC-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
	<!--MySQL驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <scope>runtime</scope>
    </dependency>
</dependencies>
~~~



>   配置SpringBoot

-   配置文件

    ~~~yaml
    spring:
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        username: root
        password: 123456
        url: jdbc:mysql://localhost:3306/springboot?serverTimezone=GMT%2B8&useSSL=true&useUnicode=true&characterEncoding=utf8
    ~~~

-   测试

    ~~~java
    @SpringBootTest
    class Springboot02DbApplicationTests {
    
        @Autowired
        DataSource dataSource;
    
        @Test
        void contextLoads() {
            System.out.println(dataSource.getClass());
        }
    
    }
    ~~~

    ![image-20201217211131863](https://gitee.com/primabrucexu/image/raw/main/image/20201217211131.png)



>   CRUD

-   Spring对原生的JDBC进行轻量级封装，大大简化了JDBC的操作。即使在没有第三方ORM框架的情况下，也能完成一些简单的CRUD操作

-   Spring将原生的JDBC封装成了 `JDBCTemplate` 并完成了自动配置，我们直接拿来用就好了

-   Controller

    ~~~java
    @RestController
    public class JDBCController {
    
        @Autowired
        private JdbcTemplate template;
    
        @RequestMapping("/list")
        public List<Map<String, Object>> getAllDepartment() {
            String sql = "select * from springboot.department";
            return template.queryForList(sql);
        }
    
    }
    ~~~

    -   这边是一个小技巧，在没有实体类的情况下，可以使用map来装载查询出来的数据

    ![image-20201217212959214](https://gitee.com/primabrucexu/image/raw/main/image/20201217212959.png)

    

### 4.3 集成Druid

-   Drudi号称是Java中最好的数据库连接池，他继承了C3P0、DBCP 等 DB 池的优点，同时具有强大的日志监控功能

-   这里主要介绍的是它的监控功能

-   配置SpringBoot

    -   要想使得SpringBoot切换连接池，我们可以通过 `spring.datasource.type` 属性进行手动切换。

    ~~~yaml
    # 配置基本的数据库连接信息
    spring:
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        username: root
        password: 123456
        url: jdbc:mysql://localhost:3306/springboot?serverTimezone=GMT%2B8&useSSL=true&useUnicode=true&characterEncoding=utf8
        type: com.alibaba.druid.pool.DruidDataSource
    ~~~

-   `Druid`配置类，这里顺便复习一下Java配置类

    ~~~java
    package com.pbx.config;
    
    import com.alibaba.druid.pool.DruidDataSource;
    import com.alibaba.druid.support.http.StatViewServlet;
    import org.springframework.boot.context.properties.ConfigurationProperties;
    import org.springframework.boot.web.servlet.ServletRegistrationBean;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    
    import javax.servlet.Servlet;
    import javax.sql.DataSource;
    import java.util.HashMap;
    import java.util.Map;
    
    /**
     * @author BruceXu
     * @date 2020/12/17
     */
    @Configuration
    public class DruidConfig {
    
        @Bean
        @ConfigurationProperties("druid")
        public DataSource druidDataSource() {
            return new DruidDataSource();
        }
    
        @Bean
        public ServletRegistrationBean<Servlet> druidServlet() {    // 配置监控
            ServletRegistrationBean<Servlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
            Map<String, String> map = new HashMap<>();
            //后台管理界面的登录账号密码
            map.put("loginUsername", "admin");
            map.put("loginPassword", "123456");
    
            // 访问白名单
            map.put("allow", "");
    
            // 黑名单
            map.put("deny", "192.168.1.1");
    
            bean.setInitParameters(map);
            return bean;
        }
    
    }
    ~~~

    -   这里给出Druid可供配置的所有后台参数

        ![image-20201217220512819](https://gitee.com/primabrucexu/image/raw/main/image/20201217220513.png)

-   测试

    ![image-20201217222848386](https://gitee.com/primabrucexu/image/raw/main/image/20201217222848.png)

-   **排坑**

    ~~~xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jdbc</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    ~~~

    -   使用JDBC时，使用第二个依赖，不要用第一个，不然会引起异常，排查时具体表现为 jdbc的url not set

        ![image-20201217223223708](https://gitee.com/primabrucexu/image/raw/main/image/20201217223223.png)



### 4.4 集成MyBatis

>   导入依赖

~~~xml
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.4</version>
</dependency>
~~~



>   实体类

~~~java
@Repository
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Department {
    private int id;
    private String name;
}
~~~



>   mapper接口

~~~java
@Mapper
@Repository
public interface DepartmentMapper {

    List<Department> getAllDepartment();

    Department getDepartmentById(@Param("id") int id);

}
~~~



>   mapper.xml

-   记得在SpringBoot中配置这些配置文件的位置，要不就启用全局的mybatis-config文件进行配置，不然就会注册不到mapper

~~~xml
<mapper namespace="com.pbx.mapper.DepartmentMapper">

    <select id="getDepartmentList" resultType="com.pbx.pojo.Department">
        select * from springboot.department
    </select>
    <select id="getDepartmentById" resultType="com.pbx.pojo.Department">
        select * from springboot.department where id = #{id}
    </select>

</mapper>
~~~

application.yaml

~~~yaml
mybatis:
  mapper-locations: classpath:mybatis/mapper/*.xml
~~~



>   测试

~~~java
@SpringBootTest
class Springboot03ApplicationTests {

     @Autowired
     private DataSource dataSource;

     @Autowired
     private DepartmentMapper mapper;

    @Test
    void contextLoads() {
        System.out.println(dataSource.getClass());

        List<Department> list = mapper.getDepartmentList();
        System.out.println(list);

    }

}
~~~

![image-20201217234222180](https://gitee.com/primabrucexu/image/raw/main/image/20201217234222.png)



## 5 SpringBoot开发web项目

### 5.1 静态资源映射

>   静态资源映射规则

-   在开发一个web项目中，我们不可避免的要使用到许多静态资源，如CSS、JS等，那么SpringBoot是怎么处理这些资源的呢？

-   官方文档

    ![image-20201218153115463](https://gitee.com/primabrucexu/image/raw/main/image/20201218153115.png)

-   官方文档告诉了我们SpringBoot中默认存放静态资源的几个位置，`/static` 、 `/public`、 `/resources` 、`/META-INF/resources`。或者是配置文件中指定的位置

-   除了这些常规手段之外，还提到了一种特殊的静态资源 `webjars`，所有的 `webjars` 会被映射到路径 `/webjars/**` 上

    -   什么是 `webjars` ？

        -   按照 `jar` 的格式进行打包的静态资源文件。这种格式的文件管理和方便，只需要引入依赖即可使用。

    -   示例

        -   引入maven依赖

            ~~~xml
            <dependency>
                <groupId>org.webjars</groupId>
                <artifactId>jquery</artifactId>
                <version>3.5.1</version>
            </dependency>
            ~~~

        -   访问测试

            ![image-20201218155717735](https://gitee.com/primabrucexu/image/raw/main/image/20201218155717.png)

            红色框出来的文件都会被映射到url `/webjars` 下

            ![image-20201218155806349](https://gitee.com/primabrucexu/image/raw/main/image/20201218155806.png)



### 5.2 首页处理

-   官方文档给出的说明是，SpringBoot会在静态资源的目录下查找一个叫 `index.html` 的文件，将它作为首页。如果这个找不到的话，就会在模板中找 `index` 的模板

-   使用循序

    -   `index.html` > `index` 模板

    

### 5.3 ThymeLeaf

>   模板引擎

-   Web开发的模板引擎是为了使**用户界面与业务数据（内容）分离**而产生的，它可以生成特定格式的文档，用于网站的模板引擎就会生成一个标准的HTML文档。

-   我们之前使用的jsp就是一种模板引擎，不过他已经过时了，我们就不在使用它了。

-   这张图可以告诉你模板引擎怎么工作的

    ![image-20201218161130289](https://gitee.com/primabrucexu/image/raw/main/image/20201218161130.png)

-   SpringBoot原生支持的模板引擎：FreeMaker、Groovy、Thymeleaf、Mustache，其中官方建议我们使用Thymeleaf



>   Thymeleaf简介

-   Thymeleaf是用来开发Web和独立环境项目的服务器端的Java模版引擎

-   Thymeleaf与SpringMVC的视图技术，及SpringBoot的自动化配置集成非常完美，几乎没有任何成本，你只用关注Thymeleaf的语法即可。

-   优点

    -   **动静结合**：Thymeleaf 在有网络和无网络的环境下皆可运行，即它可以让美工在浏览器查看页面的静态效果，也可以让程序员在服务器查看带数据的动态页面效果。这是由于它支持 html 原型，然后在 html 标签里增加额外的属性来达到模板+数据的展示方式。浏览器解释 html 时会忽略未定义的标签属性，所以 thymeleaf 的模板可以静态地运行；当有数据返回到页面时，Thymeleaf 标签会动态地替换掉静态内容，使页面动态显示。
    -   **开箱即用**：它提供标准和spring标准两种方言，可以直接套用模板实现JSTL、 OGNL表达式效果，避免每套模板、改jstl、改标签的困扰。同时开发人员也可以扩展和创建自定义的方言。
    -   **多方言支持**：Thymeleaf 提供spring标准方言和一个与 SpringMVC 完美集成的可选模块，可以快速的实现表单绑定、属性编辑器、国际化等功能。
    -   **SpringBoot完美整合**，SpringBoot提供了Thymeleaf的默认配置，并且为Thymeleaf设置了视图解析器，我们可以像以前操作jsp一样来操作Thymeleaf。代码几乎没有任何区别，就是在模板语法上有区别

-   如何使用

    -   导入Thymeleaf启动器

        ~~~xml
        <!--thymeleaf-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        ~~~

    -   把模板文件放到template文件夹下

      ​    

>   语法

参考：https://www.cnblogs.com/jiangbei/p/8462294.html

-   创建HTML，这样的话才能在上下文中使用`th`标签

    ~~~html
    <html xmlns:th="http://www.thymeleaf.org">
    ~~~

-   变量`${variable}`, `*{...}`

    ~~~html
    <p th:text="'Hello！, '+${name}+'!'">World</p>
    
    <div th:object="${session.user}">
        <p>Name: <span th:text="*{firstName}">Sebastian</span>.</p>
        <p>Surname: <span th:text="*{lastName}">Pepper</span>.</p> 
        <p>Nationality: <span th:text="*{nationality}">Saturn</span>.</p>
    </div> 
    <!--等价于-->
    <div>
        <p>Name: <span th:text="${session.user.firstName}">Sebastian</span>.</p> 
        <p>Surname: <span th:text="${session.user.lastName}">Pepper</span>.</p> 
        <p>Nationality: <span th:text="${session.user.nationality}">Saturn</span>.</p>
    </div>
    ~~~

-   链接表达式 `@{…}`

    ~~~html
    <a href="details.html" th:href="@{http://localhost:8080/gtvg/order/details(orderId=${o.id})}">view</a> 
    ~~~

-   文本替换，在替换时不能进行条件判断

    ~~~html
    <span th:text="'Welcome to our application, ' + ${user.name} + '!'"/>
    ~~~

-   运算符

    -   算数运算符：`+`,`-`,`*`,`/`,`%`
    -   逻辑运算符： `>`, `<`, `>=` ，`<=` ，`==`， `!=`
    -   条件运算符：
        -   If-then: (if) ? (then)
        -   If-then-else: (if) ? (then) : (else)
        -   Default: (value) ?: (defaultvalue)

-   条件选择 `th:if`， `th:unless`， `th:switch`

    ~~~html
    <a th:href="@{/login}" th:if=${session.user == null}>Login</a>
    <a th:href="@{/login}" th:unless=${session.user != null}>Login</a>
    <div th:switch="${user.role}">
      <p th:case="'admin'">User is an administrator</p>
      <p th:case="#{roles.manager}">User is a manager</p>
      <p th:case="*">User is some other thing</p>
    </div>
    ~~~

-   循环 `th:each`

    ~~~html
    <table>
        <tr>
            <th>ID</th>
            <th>NAME</th>
            <th>AGE</th>
        </tr>
        <tr th:each="emp : ${empList}">
            <td th:text="${emp.id}">1</td>
            <td th:text="${emp.name}">海</td>
            <td th:text="${emp.age}">18</td>
        </tr>
    </table>
    ~~~



### 5.4 配置SpringMVC

>   默认配置下的SpringMVC

-   包含 `ContentNegotiatingViewResolver` 和 `BeanNameViewResolver` 
-   映射了静态资源路径，包括支持了`Webjars`
-   自动注册了一些类型转换器和格式转化器
-   支持 Http 消息转换和 错误代码定制
-   支持静态首页
-   初始化数据绑定器（自动绑定数据到JavaBean上）

>   如何扩展SpringMVC

-   官方文档

    ![image-20201222145117544](https://gitee.com/primabrucexu/image/raw/main/image/20201222145117.png)

-   翻译

    -   如果你在默认的配置上增加额外的配置，实现接口`WebMvcConfigurer` ，只给这个类加上 `@Configuration` 注解，**千万别用 `@EnableWebMvc` `**
    -   如果想定制一些组件，就实现 `WebMvcRegistrations` 接口
    -   如果想全面接管SpringMVC，就给你的配置类同时加上 `@Configuration` 和 `@EnableWebMvc`

-   源码分析

    -   我们找到 `WebMvcAutoConfiguration` ，这个是SpringMVC的自动配置类。可以看到这个类的有这么一个生效条件 `@ConditionalOnMissingBean(WebMvcConfigurationSupport.class)`，这个注解的意思是，当不存在 `WebMvcConfigurationSupport` 这个类的时候，默认配置才会生效

        ![image-20201222150215436](https://gitee.com/primabrucexu/image/raw/main/image/20201222150215.png)

    -   下面我们再来看 `@EnableWebMvc` 的源码，可以看见他引入了一个叫 `DelegatingWebMvcConfiguration` 的类。字面意思就是托管WebMVC的自动配置。

        ![image-20201222150457546](https://gitee.com/primabrucexu/image/raw/main/image/20201222150457.png)

    -   再深入查看源码之后，这个 `DelegatingWebMvcConfiguration` 实际上继承了 `WebMvcConfigurationSupport` ，这也就是说为什么我们在扩展SpringMVC的时候，不要加上 `@EnableWebMvc` ` 的注解

        ![image-20201222150758561](https://gitee.com/primabrucexu/image/raw/main/image/20201222150758.png)

### 5.5 i18n国际化

-   SpringBoot支持本地化消息用于迎合不同用户的语言需求

-   默认情况下，SpringBoot会在 `classpath` 下查找消息资源包，也可以通过配置文件指定位置

-   示例

    -   在 `resources` 目录下创建 `i18n` 文件夹，IDEA会自动识别这个文件夹为国际化的文件

    -   在SpringBoot中配置国际化资源路径

        ~~~yaml
        spring:
          messages:
            basename: i18n.message
        ~~~

    -   创建中文的配置文件和英文的配置文件

        -   zh_CN

            ~~~properties
            alert=请登录
            loginBtn=登录
            password=密码
            remember=记住密码（不建议在公共电脑上勾选）
            username=用户名
            ~~~

        -   en_US

            ~~~properties
            alert=Please sign in
            loginBtn=SIGN IN
            password=Password
            remember=Remember Me (not recommended to set on public computers)
            username=username
            ~~~

    -   在首页中使用

        ![image-20201222202811758](https://gitee.com/primabrucexu/image/raw/main/image/20201222202811.png)

    -   效果展示

        ![image-20201222202902252](https://gitee.com/primabrucexu/image/raw/main/image/20201222202902.png)

-   源码分析

    -   在 `WebMvcAutoConfiguration` 中有关于选择 本地资源的内容，具体逻辑如下

        -   先判断是否总是使用配置的语言环境
        -   如果不是的话，则根据请求头中的 `Accept-Language` 解析使用什么样的语言资源

        ![image-20201222204001721](https://gitee.com/primabrucexu/image/raw/main/image/20201222204001.png)

### 5.6 上手操作

#### 1）准备工作

-   把前端模板放到该放的地方

-   导入Maven依赖

    ```xml
    <dependencies>
        <!--web-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        
        <!--数据库-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.4</version>
        </dependency>
        
        <!--配置工具-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    ```

-   实体类

    ```java
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Repository
    public class Department {
    
        private int id;
        private String name;
    
    }
    ```

    ~~~java
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Repository
    public class Employee {
    
        private int id;
        private String name;
        private String email;
        private int gender;
        private Department department;
        private Date birth;
    
    }
    ~~~

-   配置数据库连接信息

    ~~~yaml
    spring:
      datasource:
        driver-class-name: com.mysql.cj.jdbc.Driver
        username: root
        password: 123456
        url: jdbc:mysql://localhost:3306/springboot?serverTimezone=GMT%2B8&useSSL=true&useUnicode=true&characterEncoding=utf8
    ~~~



#### 2）MyBatis整合

-   配置MyBatis

    ~~~yaml
    mybatis:
      mapper-locations: classpath:mybatis/mapper/*.xml
      type-aliases-package: com.pbx.pojo
    ~~~

-   mapper

    -   DepartmentMapper.java

        ~~~java
        @Mapper
        @Repository
        public interface DepartmentMapper {
            List<Department> getDepartmentList();
            Department getDepartmentById(@Param("departmentID") int id);
            int countByDepartmentId(@Param("departmentID")int id);
            int updateDepartment(Department department);
            int deleteDepartment(@Param("departmentID") int id);
            int insertDepartment(Department department);
        }
        ~~~

    

-   DepartmentMapper.xml

    ```xml
      <mapper namespace="com.pbx.mapper.DepartmentMapper">
      
          <select id="getDepartmentList" resultType="com.pbx.pojo.Department">
              select *
              from springboot.department
          </select>
      
          <select id="getDepartmentById" resultType="com.pbx.pojo.Department">
              select *
              from springboot.department
              where id = #{departmentID}
          </select>
          <select id="countByDepartmentId" resultType="java.lang.Integer">
              SELECT COUNT(id) num FROM springboot.employee WHERE department = #{departmentID}
          </select>
          <update id="updateDepartment" parameterType="com.pbx.pojo.Department">
              update springboot.department
              set name = #{name}
              where id = #{id};
          </update>
          <delete id="deleteDepartment">
              delete
              from springboot.department
              where id = #{departmentID};
          </delete>
      <insert id="insertDepartment" parameterType="com.pbx.pojo.Department">
              insert into springboot.department (id, name)
          values (#{id}, #{name})
          </insert>
      </mapper>
    
    ```

      -   EmployeeMapper.java

          ~~~java
          @Mapper
          @Repository
          public interface EmployeeMapper {
              List<Employee> getEmployeeList();
          Employee getOneById(@Param("employeeID") int id);
              int addEmployee(Employee employee);
          int updateEmployee(Employee employee);
              int deleteEmployee(int id);
          }
          ~~~

      -   EmployeeMapper.xml

          ~~~xml
          <mapper namespace="com.pbx.mapper.EmployeeMapper">
              <!--因为实体类的字段和数据库的字段不一致，所以要配置这resultMap和parameterMap-->
              <resultMap id="resultMap" type="com.pbx.pojo.Employee">
                  <!--有association之后就要配置所有属性的映射-->
                  <result property="id" column="id"/>
                  <result property="name" column="last_name"/>
                  <result property="email" column="email"/>	
                  <result property="gender" column="gender"/>
                  <result property="birth" column="birth"/>
                  <association property="department" javaType="com.pbx.pojo.Department">
                      <result property="id" column="did"/>
                      <result property="name" column="dname"/>
                  </association>
              </resultMap>
              <select id="getEmployeeList" resultMap="resultMap">
                  select e.id, e.last_name, e.email, e.gender, e.department, e.birth, d.id did, d.name dname
                  from springboot.employee e, springboot.department d
                  where e.department = d.id
              </select>
              <select id="getOneById" resultMap="resultMap">
                  select e.id, e.last_name, e.email, e.gender, e.department, e.birth, d.id did, d.name dname
                  from springboot.employee e,  springboot.department d
                  where e.id = #{employeeID} and e.department = d.id
              </select>
              <insert id="addEmployee" parameterType="com.pbx.pojo.Employee">
                  insert into springboot.employee(last_name, email, gender, department, birth)
                  values (#{name}, #{email}, #{gender}, #{department.id}, #{birth})
              </insert>
              <update id="updateEmployee" parameterType="com.pbx.pojo.Employee">
                  update springboot.employee
                  set last_name = #{name}, email=#{email}, gender=#{gender}, department=#{department.id}
                  where id = #{id}
              </update>
              <delete id="deleteEmployee">
                  delete
                  from springboot.employee
                  where id = #{id}
              </delete>
          </mapper>
          ~~~

-   测试

    ~~~java
    @Test
    public void DepartmentTest() {
        DMapper.insertDepartment(new Department(106, "干饭部门"));
        showDepartment();
        DMapper.updateDepartment(new Department(106, "打工部"));
        showDepartment();
        DMapper.deleteDepartment(106);
        showDepartment();
    }
    
    public void showDepartment() {
        for (Department department : DMapper.getDepartmentList()) {
            System.out.println(department);
        }
        System.out.println("====================");
    }
    ~~~

    ![image-20201222180625744](https://gitee.com/primabrucexu/image/raw/main/image/20201222180625.png)

    ~~~java
    @Test
    public void EmployeeTest() {
        EMapper.addEmployee(new Employee(1, "布鲁斯儿", "123@qq.com", 1,
                DMapper.getDepartmentById(101), new Date()));
        showEmployee();
        Map<String, Object> map = new HashMap<>();
        map.put("id", 1);
        map.put("name", "里卡呆");
        EMapper.updateEmployee(map);
        showEmployee();
        EMapper.deleteEmployee(1);
        showEmployee();
    }
    
    public void showEmployee() {
        for (Employee employee : EMapper.getEmployeeList()) {
            System.out.println(employee);
        }
        System.out.println("=============");
    }
    ~~~

    ![image-20201222184323736](https://gitee.com/primabrucexu/image/raw/main/image/20201222184323.png)



#### 3）首页

-   在配置类中给首页添加几个映射

    ~~~java
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index.html").setViewName("index");
        registry.addViewController("/index").setViewName("index");
    }
    ~~~

-   这样的话，访问上述任一路径都可以到达我们的首页，同时为了保证所有的静态资源能被正确引用到，这里我们要用 `Thymeleaf` 管理所有的静态资源

    -   具体表现为用 `Thymeleaf` 来引用

        ```html
        <!-- Bootstrap core CSS -->
        <link th:href="@{/css/bootstrap.min.css}" rel="stylesheet">
        <!-- Custom styles for this template -->
        <link th:href="@{/css/signin.css}" rel="stylesheet">
        ```



#### 4）i18n 国际化

-   在SpringBoot中配置国际化资源路径

    ~~~yaml
    spring:
      messages:
        basename: i18n.message
    ~~~

-   创建中文的配置文件和英文的配置文件

    -   zh_CN

        ~~~properties
        alert=请登录
        loginBtn=登录
        password=密码
        remember=记住密码（不建议在公共电脑上勾选）
        username=用户名
        ~~~

    -   en_US

        ~~~properties
        alert=Please sign in
        loginBtn=SIGN IN
        password=Password
        remember=Remember Me (not recommended to set on public computers)
        username=username
        ~~~

-   在首页中使用

    ![image-20201222202811758](https://gitee.com/primabrucexu/image/raw/main/image/20201222202811.png)

-   实现中英文切换

    -   给两个链接添加地址

        ~~~html
        <a class="btn btn-sm" th:href="@{/zh/index}">中文</a>
        <a class="btn btn-sm" th:href="@{/en/index}">English</a>
        ~~~

    -   配置Controller

        ~~~java
        @GetMapping("/{s}/index")
        public String changeLanguage(@PathVariable String s, HttpSession session) {
            // 为了保证语言信息能跟着一起走，所有要放到session里面
            session.setAttribute("language", s);
            return "index";
        }
        ~~~

    -   重新配置Locale

        ~~~java
        public class LanguageConfig implements LocaleResolver {
            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                String language = (String) request.getSession().getAttribute("language");
                if ("zh".equals(language)) {
                    return new Locale("zh", "CN");
                } else if ("en".equals(language)) {
                    return new Locale("en", "US");
                }
                return request.getLocale();
            }
        
            @Override
            public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        
            }
        }
        ~~~

    -   在MVC配置类中注入

        ~~~java
        @Bean
        public LocaleResolver localeResolver() {
            return new LanguageConfig();
        }
        ~~~

-   效果展示

    ![image-20201222202902252](https://gitee.com/primabrucexu/image/raw/main/image/20201222202902.png)

    ![image-20201223215737382](https://gitee.com/primabrucexu/image/raw/main/image/20201223215737.png)



#### 5）登录

-   修改index.html中的相应元素，使其能将登录表单提交到相应的位置

    ![image-20201223224339252](https://gitee.com/primabrucexu/image/raw/main/image/20201223224339.png)

-   User实体类

    ```java
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Repository
    public class User {
    
        private int id;
        private String username;
        private String password;
    
    }
    ```

-   mapper接口和xml

    ~~~java
    @Mapper
    @Repository
    public interface UserMapper {
    
        User getUser(Map<String, String> map);
    
    }
    ~~~

    ~~~xml
    <mapper namespace="com.pbx.mapper.UserMapper">
    
        <select id="getUser" parameterType="map" resultType="com.pbx.pojo.User">
            select * from springboot.user
            where username = #{username} and password = #{password}
        </select>
    </mapper>
    ~~~

-   登录服务

    ~~~java
    @Service
    public class LoginService {
    
        @Autowired
        private UserMapper mapper;
    
        public void setMapper(UserMapper mapper) {
            this.mapper = mapper;
        }
    
        public boolean login(Map<String, String> map) {
            return mapper.getUser(map) != null;
        }
    }
    ~~~

-   Controller设置

    ~~~java
    @Autowired
    private LoginService loginService;
    
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        Map<String, String> map = new HashMap<>(2);
        map.put("username", username);
        map.put("password", password);
        if (loginService.login(map)) {
            session.setAttribute("user", username);
            session.setAttribute("login", true);
            return "redirect:/dashboard";
        }
        return "index";
    }
    ~~~

-   测试

    ![image-20201223232754772](https://gitee.com/primabrucexu/image/raw/main/image/20201223232754.png)

#### 6）登录拦截器

-   现在我们的主页可以在没有登录的情况下直接访问，那么我们就需要使用拦截器去拦截所有的未登录情况下的所有请求，这也就是在登录的时候给session加了一个状态的原因

-   自定义拦截器

    ```java
    public class LoginInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            Object login = request.getSession().getAttribute("login");
            if (login == null || login == Boolean.FALSE) {
                request.getRequestDispatcher("/index").forward(request, response);
                return false;
            } else {
                return true;
            }
        }
    }
    ```

-   注册拦截器

    ~~~java
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] exclude = {"/", "/index","/index.html","/login","/jss/**","/css/**","/js/**","/*/index"};
        registry.addInterceptor(new LoginInterceptor()).excludePathPatterns(exclude);
    }
    ~~~

    

#### 7）主页

-   抽取公共组件，简化模板文件结构，提高代码复用，同时完成链接跳转功能

    ~~~html
    <!DOCTYPE html>
    <html lang="en" xmlns:th="http://www.thymeleaf.org">
    
        <nav class="navbar navbar-dark sticky-top bg-dark flex-md-nowrap p-0" th:fragment="topbar">
            <a class="navbar-brand col-sm-3 col-md-2 mr-0" style="color:white;" th:text="${session.user}"></a>
            <ul class="navbar-nav px-3">
                <li class="nav-item text-nowrap">
                    <a class="nav-link" th:href="@{/quit}" th:text="#{main.signout}"></a>
                </li>
            </ul>
        </nav>
        <nav class="col-md-2 d-none d-md-block bg-light sidebar" th:fragment="sidebar">
            <div class="sidebar-sticky">
                <ul class="nav flex-column">
                    <li class="nav-item">
                        <a th:class="${current=='main' ? 'nav-link active' : 'nav-link'}" th:href="@{/main}"
                           th:text="#{main.main}">
                        </a>
                    </li>
                    <li class="nav-item">
                        <a th:class="${current=='employee' ? 'nav-link active' : 'nav-link'}" th:href="@{/employee}"
                           th:text="#{main.employee}">
                        </a>
                    </li>
                    <li class="nav-item">
                        <a th:class="${current=='department' ? 'nav-link active' : 'nav-link'}" th:href="@{/department}"
                           th:text="#{main.department}">
                        </a>
                    </li>
                </ul>
            </div>
        </nav>
        
    </html>
    ~~~

-   主页页面

    ~~~html
    <body>
    
    <div th:replace="~{component/part::topbar}"></div>
    
    <div class="container-fluid">
        <div class="row">
            <div th:replace="~{component/part::sidebar}"></div>
    
            <main class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4" role="main">
                <h1 th:text="'Hello ' + ${session.user}"></h1>
            </main>
        </div>
    </div>
    
    </body>
    ~~~

-   主页Controller

    ~~~java
    @RequestMapping({"/dashboard", "/main"})
    public String mainPage(Model model) {
        model.addAttribute("current", "main");
        return "detail/main";
    }
    @RequestMapping("/quit")
    public String quit(HttpSession session) {
        session.invalidate();
        return "index";
    }
    ~~~

-   效果展示

    ![image-20201224165558744](https://gitee.com/primabrucexu/image/raw/main/image/20201224165729.png)



#### 8）部门信息展示

-   页面

    ~~~html
    <body>
    
    <div th:replace="~{component/part::topbar}"></div>
    
    <div class="container-fluid">
        <div class="row">
            <div th:replace="~{component/part::sidebar}"></div>
    
            <main class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4" role="main">
                <h1 th:text="#{main.department.title}">Department</h1>
                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                        <tr>
                            <th th:text="#{main.department.id}"></th>
                            <th th:text="#{main.department.name}"></th>
                            <th th:text="#{main.department.nums}"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr th:each="k:${map}">
                            <td th:text="${k.getKey().getId()}"></td>
                            <td th:text="${k.getKey().getName()}"></td>
                            <td th:text="${map.get(k.getKey())}"></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>
    
    </body>
    ~~~

-   mapper

    -   接口

        ~~~java
        @Mapper
        @Repository
        public interface DepartmentMapper {
            List<Department> getDepartmentList();
            int countByDepartmentId(@Param("departmentID")int id);
        }
        ~~~

    -   映射文件

        ~~~xml
        <mapper namespace="com.pbx.mapper.DepartmentMapper">
            <select id="getDepartmentList" resultType="com.pbx.pojo.Department">
                select *
                from springboot.department
            </select>
            <select id="countByDepartmentId" resultType="java.lang.Integer">
                SELECT COUNT(id) num FROM springboot.employee WHERE department = #{departmentID}
            </select>
        </mapper>
        ~~~

-   Service

    ~~~java
    @Service
    public class DepartmentService {
        @Autowired
        private DepartmentMapper mapper;
    
        private List<Department> getDepartment() {
            return mapper.getDepartmentList();
        }
    
        public Map<Department, Integer> countDepartment() {
            Map<Department, Integer> map = new LinkedHashMap<>();
            List<Department> departmentList = getDepartment();
            for (Department department : departmentList) {
                map.put(department, mapper.countByDepartmentId(department.getId()));
            }
            System.out.println(map);
            return map;
        }
    }
    ~~~

-   Controller

    ~~~java
    @GetMapping("/department")
    public String department(Model model) {
        model.addAttribute("current", "department");
        Map<Department, Integer> map = departmentService.countDepartment();
        model.addAttribute("map", map);
        return "detail/department";
    }
    ~~~

-   效果

    ![image-20201224174429008](https://gitee.com/primabrucexu/image/raw/main/image/20201224174429.png)



#### 9）员工信息展示

-   页面

    ~~~html
    <body>
    
    <div th:replace="~{component/part::topbar}"></div>
    <div class="container-fluid">
        <div class="row">
            <div th:replace="~{component/part::sidebar}"></div>
            <main class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4" role="main">
                <h2 th:text="#{main.employee.title}"></h2>
                <div class="table-responsive">
                    <table class="table table-striped table-sm">
                        <thead>
                        <tr>
                            <th th:text="#{main.employee.id}"></th>
                            <th th:text="#{main.employee.name}"></th>
                            <th th:text="#{main.employee.email}"></th>
                            <th th:text="#{main.employee.gender}"></th>
                            <th th:text="#{main.employee.department}"></th>
                            <th th:text="#{main.employee.birth}"></th>
                            <th th:text="#{main.employee.action}"></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr th:each="l:${list}">
                            <td th:text="${l.getId()}"></td>
                            <td th:text="${l.getName()}"></td>
                            <td th:text="${l.getEmail()}"></td>
                            <td th:text="${l.getGender()==0 ? '女' : '男'}"></td>
                            <td th:text="${l.getDepartment().getName()}"></td>
                            <td th:text="${l.getBirth()}"></td>
                            <td>
                                <button class="btn btn-sm btn-primary" th:text="#{main.employee.edit}"></button>
                                <button class="btn btn-sm btn-danger" th:text="#{main.employee.delete}"></button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </main>
        </div>
    </div>
    
    </body>
    ~~~

-   mapper

    -   接口

        ~~~java
        @Mapper
        @Repository
        public interface EmployeeMapper {
        
            List<Employee> getEmployeeList();
        
            Employee getOneById(@Param("employeeID") int id);
        
            int addEmployee(Employee employee);
        
            int updateEmployee(Map<String, Object> map);
        
            int deleteEmployee(int id);
        
        }
        ~~~

    -   映射文件

        ~~~xml
        <mapper namespace="com.pbx.mapper.EmployeeMapper">
            <!--因为实体类的字段和数据库的字段不一致，所以要配置这resultMap和parameterMap-->
            <resultMap id="resultMap" type="com.pbx.pojo.Employee">
                <!--有association之后就要配置所有属性的映射-->
                <result property="id" column="id"/>
                <result property="name" column="last_name"/>
                <result property="email" column="email"/>
                <result property="gender" column="gender"/>
                <result property="birth" column="birth"/>
                <association property="department" javaType="com.pbx.pojo.Department">
                    <result property="id" column="did"/>
                    <result property="name" column="dname"/>
                </association>
            </resultMap>
            <select id="getEmployeeList" resultMap="resultMap">
                select e.id, e.last_name, e.email, e.gender, e.department, e.birth, d.id did, d.name dname
                from springboot.employee e, springboot.department d
                where e.department = d.id
            </select>
            <select id="getOneById" resultMap="resultMap">
                select *
                from springboot.employee
                where id = #{employeeID}
            </select>
            <insert id="addEmployee" parameterType="com.pbx.pojo.Employee">
                insert into springboot.employee(id, last_name, email, gender, department, birth)
                values (#{id}, #{name}, #{email}, #{gender}, #{department.id}, #{birth})
            </insert>
            <update id="updateEmployee" parameterType="map">
                update springboot.employee
                <set>
                    <if test="name != null">
                        last_name = #{name}
                    </if>
                    <if test="email != null">
                        email = #{email}
                    </if>
                    <if test="gender != null">
                        gender = #{gender}
                    </if>
                    <if test="department != null">
                        department = #{department}
                    </if>
                    <if test="birth != null">
                        birth = #{birth}
                    </if>
                </set>
                where id = #{id}
            </update>
            <delete id="deleteEmployee">
                delete
                from springboot.employee
                where id = #{id}
            </delete>
        </mapper>
        ~~~

-   Service

    ~~~java
    @Service
    public class EmployeeService {
    
        @Autowired
        private EmployeeMapper mapper;
    
        public List<Employee> getEmployList() {
            return mapper.getEmployeeList();
        }
    
    }
    ~~~

-   Controller

    ~~~java
    @GetMapping("/employee")
    public String employee(Model model) {
        model.addAttribute("current", "employee");
        model.addAttribute("list", employeeService.getEmployList());
        return "detail/employee";
    }
    ~~~

-   展示

    ![image-20201224181940236](https://gitee.com/primabrucexu/image/raw/main/image/20201224181940.png)



#### 10）增、删、改员工

>   增加

-   跳转链接

    ~~~html
    <a th:href="@{/delete/}+${l.getId()}"><button class="btn btn-sm btn-danger" th:text="#{main.employee.delete}"></button></a>
    ~~~

-   页面

    ~~~html
    <body>
    <div th:replace="~{component/part::topbar}"></div>
    <div class="container-fluid">
        <div class="row">
            <div th:replace="~{component/part::sidebar}"></div>
            <main class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4" role="main">
                <form action="/add" method="post">
                    <div class="form-group">
                        <label for="name">姓名</label>
                        <input type="text" class="form-control" id="name" placeholder="姓名" name="name">
                    </div>
                    <div class="form-group">
                        <label for="email">邮箱</label>
                        <input type="email" class="form-control" id="email" placeholder="邮箱" name="email">
                    </div>
                    <div class="form-group">
                        <label>性别</label>
                        <div class="form-check">
                            <input class="form-check-inline" type="radio" name="gender" value="0">
                            <label class="form-check-label">男</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-inline" type="radio" name="gender" value="1">
                            <label class="form-check-label">女</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="department">部门</label>
                        <select class="form-control" id="department" name="department.id">
                            <option th:each="dept:${list}" th:text="${dept.getName()}" th:value="${dept.getId()}"></option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="birth">出生日期</label>
                        <input type="date" class="form-control" id="birth" placeholder="出生日期" name="birth">
                    </div>
                    <button type="submit" class="btn btn-default">提交</button>
                </form>
            </main>
        </div>
    </div>
    </body>
    ~~~

-   Controller

    ~~~java
    @GetMapping("/add")
    public String toAdd(Model model) {
        model.addAttribute("list", departmentService.getDepartment());
        return "detail/add";
    }
    
    @PostMapping("/add")
    public String add(Employee employee) {
        System.out.println(employee);
        employeeService.addEmployee(employee);
        return "redirect:/employee";
    }
    ~~~

-   展示

    ![image-20201224204502791](https://gitee.com/primabrucexu/image/raw/main/image/20201224204502.png)

    ![image-20201224204513311](https://gitee.com/primabrucexu/image/raw/main/image/20201224204513.png)

-   注意点：

    -   日期格式的Formatter要记得设置

        ~~~yaml
        spring:
          mvc:
            format:
              date: yyyy-mm-dd
        ~~~



>   修改

-   跳转链接

    ~~~html
    <a th:href="@{/edit/}+${l.getId()}"><button class="btn btn-sm btn-primary" th:text="#{main.employee.edit}"></button></a>
    ~~~

-   页面

    ~~~html
    <body>
    <div th:replace="~{component/part::topbar}"></div>
    <div class="container-fluid">
        <div class="row">
            <div th:replace="~{component/part::sidebar}"></div>
            <main class="col-md-9 ml-sm-auto col-lg-10 pt-3 px-4" role="main">
                <form th:action="@{/edit/}+${employee.getId()}" method="post">
                    <div class="form-group">
                        <label for="name">姓名</label>
                        <input type="text" class="form-control" id="name" th:value="${employee.getName()}" th:name="${'name'}">
                    </div>
                    <div class="form-group">
                        <label for="email">邮箱</label>
                        <input type="email" class="form-control" id="email" th:value="${employee.getEmail()}" name="email">
                    </div>
                    <div class="form-group">
                        <label>性别</label>
                        <div class="form-check">
                            <input th:checked="${employee.getGender()==0}" class="form-check-inline" type="radio" name="gender" value="0">
                            <label class="form-check-label">男</label>
                        </div>
                        <div class="form-check">
                            <input th:checked="${employee.getGender()==1}" class="form-check-inline" type="radio" name="gender" value="1">
                            <label class="form-check-label">女</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="department">部门</label>
                        <select class="form-control" id="department" name="department.id">
                            <option th:selected="${dept.getId()==employee.getDepartment().getId()}" th:each="dept:${list}" th:text="${dept.getName()}" th:value="${dept.getId()}"></option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="birth">出生日期</label>
                        <input type="date" class="form-control" id="birth" th:value="${#dates.format(employee.getBirth(),'yyyy-mm-dd')}" name="birth">
                    </div>
                    <button type="submit" class="btn btn-default">提交</button>
                </form>
            </main>
        </div>
    </div>
    </body>
    ~~~

-   Controller

    ~~~java
    @GetMapping("/edit/{id}")
    public String toUpdate(@PathVariable int id, Model model) {
        Employee employee = employeeService.getOneByID(id);
        System.out.println(employee);
        model.addAttribute("employee", employee);
        model.addAttribute("list", departmentService.getDepartment());
        return "detail/edit";
    }
    
    @PostMapping("/edit/{id}")
    public String udate(@PathVariable int id, Employee employee) {
        System.out.println(id);
        System.out.println(employee);
        employeeService.updateEmployee(employee);
        return "redirect:/employee";
    }
    ~~~

-   展示

    ![image-20201224220434119](https://gitee.com/primabrucexu/image/raw/main/image/20201224220434.png)

    ![image-20201224220446739](https://gitee.com/primabrucexu/image/raw/main/image/20201224220446.png)

    ![image-20201224220501607](https://gitee.com/primabrucexu/image/raw/main/image/20201224220501.png)



>   删除

-   跳转链接

    ~~~html
    <a th:href="@{/delete/}+${l.getId()}"><button class="btn btn-sm btn-danger" th:text="#{main.employee.delete}"></button></a>
    ~~~

-   Controller

    ~~~java
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        employeeService.deleteEmployee(id);
        return "redirect:/employee";
    }
    ~~~

-    演示

     ![image-20201224220948227](https://gitee.com/primabrucexu/image/raw/main/image/20201224220948.png)

     ![image-20201224220954612](https://gitee.com/primabrucexu/image/raw/main/image/20201224220954.png)

## 6 Swagger

### 6.1 Swagger简介

>   什么是Swagger

Swagger号称是世界上最流行的API框架，是一种自动生成API文档的工具

-   为什么会出现Swagger？
    -   在现在前后端分离的时代下，前后端通过API接口进行交互。但是开发经常改变接口，但是接口文档却没有得到及时更新，这样就会导致问题的出现。
    -   于是，Swagger这个自动API文档生成器便应运而生



### 6.2 SpringBoot集成Swagger

-   随着OpenApi迭代到3.0版本，Swagger也随之更新到了3.0版本，这里给出使用2.x和3.x版本的一些不同之处
    -   https://blog.csdn.net/qq_40962552/article/details/110409891
    -   https://www.cnblogs.com/wang-sky/p/13786182.html

-   导入依赖

    ~~~xml
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-boot-starter</artifactId>
        <version>3.0.0</version>
    </dependency>
    ~~~

-   Controller

    ~~~java
    @RestController
    public class HelloController {
    
        @GetMapping("/getHello")
        public String getHello() {
            return "hello";
        }
    
        @PostMapping("/getHello")
        public String postHello() {
            return "hello";
        }
    
    }
    ~~~

-   Swagger配置类

    ~~~java
    @Configuration
    @EnableOpenApi
    public class SwaggerConfig {
    }
    ~~~

-   访问测试

    -   ui访问地址：http://localhost:8080/swagger-ui/index.html

    ![](https://gitee.com/primabrucexu/image/raw/main/image/20210128190507.png)

### 6.3 配置Swagger

-   Swagger工作的时候只需要配置一个Docket实例对象，这个对象里面含有所有Swagger需要的信息

-   配置类

    ~~~java
    @Configuration
    @EnableOpenApi
public class SwaggerConfig {
        @Bean
        public Docket docket() {
            return new Docket(DocumentationType.OAS_30);
        }
    }
    ~~~
    
-   详细配置

    ~~~java
    @Configuration
    @EnableOpenApi
    public class SwaggerConfig {
    
        @Bean
        public Docket docket() {
            return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo()) // api文档信息展示
                .select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.none()).build() // 扫描的接口
                .enable(true) // 是否开启Swagger
                .groupName("PBX"); // 分组名
        }
    
        private ApiInfo apiInfo() {
            Contact contact = new Contact("brucexu", "https://www.cnblogs.com/primabrucexu/", "primabrucexu@gmail.com");
            return new ApiInfo("Swagger3配置", "Swagger配置演示", "1.0", "https://www.cnblogs.com/primabrucexu/",
                    contact, "开源证书", "证书连接", new ArrayList<>());
        }
    
    }
    ~~~

-   接口扫描详解

    -   `apis`
        -   `any` ，扫描全部的接口
        -   `none` ，不扫描任何接口
        -   `basePackage`，扫描指定包下的接口
        -   `withClassAnnotation`，扫描类上具有指定注解的接口
        -   `withMethodAnnotation`，扫描方法上具有指定注解的接口
    -   `paths`，扫描指定路径下的注解
        -   `any` ，扫描全部的接口
        -   `none` ，不扫描任何接口
        -   `regex`，正则表达式匹配
        -   `ant`，ant表达式匹配

### 6.4 常用注解

|      2.x注解      |  3.x注解   |     描述     |
| :---------------: | :--------: | :----------: |
|     @ApiModel     |  @Schema   |   对象描述   |
| @ApiModelProperty |  @Schema   | 对象属性描述 |
|   @ApiOperation   | @Operation |   方法描述   |
|     @ApiParam     | @Parameter | 对参数的描述 |



## 7 SpringSecurity





## 8 分布式微服务基础

### 8.1 分布式理论

#### 1）是什么

-   分布式系统是由一组通过网络进行通信、为了完成共同的任务而协调工作的计算机节点组成的系统。
-   分布式系统的出现是为了用廉价的、普通的机器完成单个计算机无法完成的计算、存储任务。其目的是**利用更多的机器，处理更多的数据**。
-   **说人话**，团结就是力量

#### 2）为什么会出现

-   首先需要明确的是，只有当单个节点的处理能力无法满足日益增长的计算、存储任务的时候，且硬件的提升（加内存、加磁盘、使用更好的CPU）高昂到得不偿失的时候，应用程序也不能进一步优化的时候，我们才需要考虑分布式系统。  
-   因为，分布式系统要解决的问题本身就是和单机系统一样的，而由于分布式系统多节点、通过网络通信的拓扑结构，会引入很多单机系统没有的问题，为了解决这些问题又会引入更多的机制、协议，带来更多的问题  

#### 3）技术难点

-   **这么多的服务，我的用户该怎么来访问**

-   **这么多的服务，我的服务和服务之间该怎么进行通讯**

-   **这么多服务我该怎么管理**

-   **有服务挂掉了，怎么处理**

    

### 8.2 RPC

-   什么是RPC

    -   RPC（Remote Procedure Call）是指远程过程调用，是一种进程间通信方式，他是一种技术的思想
    -   **说人话**，遥控器

-   RPC过程

    ![rpc](https://gitee.com/primabrucexu/image/raw/main/image/20210131205954.jpg)

-   **技术难点**

    -   网络通信（目前技术水平下，网络传输仍然不那么可靠）
    -   序列化

### 8.3 Dubbo

-   什么是Dubbo

    -   Apache Dubbo is a high-performance, java based open source RPC framework.
    -   **说人话**，Dubbo是基于Java的RPC框架。提供了三大核心能力：面向接口的远程方法调用，智能容错和负载均衡，以及服务自动注册和发现。  

-   工作流程

    ![dubbo](https://gitee.com/primabrucexu/image/raw/main/image/20210131210442.jpg)

### 8.4 Zookeeper

-   什么是Zookeeper
    -   从上面Dubbo的工作流程中可以看到，Registry这个东西那是相当重要啊，而Zookeeper就是Dubbo官方推荐我们使用的注册中心
    -   **说人话**，就是一个用来注册和分发服务的中间件



### 8.5 分布式初体验

#### 1）准备工作

-   安装Zookeeper

    -   到Zookeeper官网上下载下来解压就好了
    -   运行bin目录下的zkServer即可开启Zookeeper
        -   如果闪退，则在文件最后一行加上pause就能看到错误信息

-   安装Dubbo-admin，这个是Dubbo的监控程序，通过他，我们可以直观的看到我们部署了那些服务

    -   Github：https://github.com/apache/dubbo-admin

    -   注意要下载master分支下的，默认使develop分支。同时下载下来的Maven项目需要打包

    -   启动界面，默认端口在7001，默认账号密码root-root

        ![image-20210131214049208](https://gitee.com/primabrucexu/image/raw/main/image/20210131214049.png)

#### 2）搭建服务提供商

-   导入依赖

    ~~~xml
    <!-- Dubbo -->
    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo-spring-boot-starter</artifactId>
        <version>2.7.8</version>
    </dependency>
    <!-- zookeeper -->
    <dependency>
        <groupId>org.apache.curator</groupId>
        <artifactId>curator-framework</artifactId>
        <version>5.1.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.curator</groupId>
        <artifactId>curator-recipes</artifactId>
        <version>5.1.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId>
        <version>3.6.2</version>
        <exclusions>
            <exclusion>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-log4j12</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    ~~~

-   编写服务类

    -   接口

        ~~~java
        public interface SellService {
            String sell();
        }
        ~~~

    -   实现类

        ~~~java
        @DubboService
        @Service
        public class SellServiceImpl implements SellService {
            @Override
            public String sell() {
                return "我买了Lamborghini";
            }
        }
        ~~~

        -   最新版本的Dubbo中，@Service注解已经过时，官方要我们用@DubboService来暴露服务

-   配置文件

    ~~~yaml
    dubbo:
      application:
        name: 出售服务
      registry:
        address: zookeeper://127.0.0.1:2181
      scan:
        base-packages: com.pbx.service
    
    server:
      port: 9000
    ~~~



#### 3）服务调用者

-   导入依赖

    ~~~xml
    <!-- Dubbo -->
    <dependency>
        <groupId>org.apache.dubbo</groupId>
        <artifactId>dubbo-spring-boot-starter</artifactId>
        <version>2.7.8</version>
    </dependency>
    <!-- zookeeper -->
    <dependency>
        <groupId>org.apache.curator</groupId>
        <artifactId>curator-framework</artifactId>
        <version>5.1.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.curator</groupId>
        <artifactId>curator-recipes</artifactId>
        <version>5.1.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.zookeeper</groupId>
        <artifactId>zookeeper</artifactId>
        <version>3.6.2</version>
        <exclusions>
            <exclusion>
                <groupId>org.slf4j</groupId>
                <artifactId>slf4j-log4j12</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    ~~~

-   Service实现类

    ~~~java
    @Service
    public class BuyServiceImpl implements BuyService {
    
        @Override
        public void buy() {
    
        }
    }
    ~~~

    -   通常情况下，这里调用远程服务的方式是通过pom导入，这里简化步骤，直接建立一个完全一样的服务接口

-   配置Dubbo

    ~~~yaml
    dubbo:
      application:
        name: 购买服务
      registry:
        address: zookeeper://127.0.0.1:2181
    server:
      port: 9001
    ~~~

-   测试，进行测试的时候要同时开启两个SpringBoot项目和Zookeeper

    ~~~java
    @Autowired
    BuyService buyService;
    
    @Test
    void contextLoads() throws InterruptedException {
        while (true) {
            buyService.buy();
            Thread.sleep(3000);
        }
    }
    ~~~

    ![image-20210131224351035](https://gitee.com/primabrucexu/image/raw/main/image/20210131224351.png)

-   同时在Dubbo-admin中也可以看见

    ![image-20210131224138890](https://gitee.com/primabrucexu/image/raw/main/image/20210131224139.png)

    ![image-20210131224305360](https://gitee.com/primabrucexu/image/raw/main/image/20210131224305.png)