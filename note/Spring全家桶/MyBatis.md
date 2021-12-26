# MyBaits

## 一、简介

### 1.1 什么是MyBatis

-   简介
    -   MyBatis 是一款优秀的持久层框架，它支持自定义 SQL、存储过程以及高级映射。
    -   MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。
    -   MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。
-   特点
    -   简单易学：本身就很小且简单。没有任何第三方依赖，最简单安装只要两个jar文件+配置几个sql映射文件易于学习，易于使用，通过文档和源代码，可以比较完全的掌握它的设计思路和实现。
    -   灵活：mybatis不会对应用程序或者数据库的现有设计强加任何影响。 sql写在xml里，便于统一管理和优化。通过sql语句可以满足操作数据库的所有需求。
    -   解除sql与程序代码的耦合：通过提供DAO层，将业务逻辑和数据访问逻辑分离，使系统的设计更清晰，更易维护，更易单元测试。sql和代码的分离，提高了可维护性。
    -   提供映射标签，支持对象与数据库的orm字段关系映射
    -   提供对象关系映射标签，支持对象关系组建维护
    -   提供xml标签，支持编写动态sql
-   官方文档
    -   https://mybatis.org/mybatis-3/zh/index.html
-   Github
    -   https://github.com/mybatis/mybatis-3

### 1.2 持久化与持久层

#### 1. 持久化

-   什么是持久化？
    -   **持久化是将程序数据在持久状态和瞬时状态间转换的机制。**
        -   简单来说就是把内存中的数据保存在外存上
-   为什么要持久化？
    -   内存的断点即失特性决定了内存无法长久存储数据
    -   虽然我们可以保证某台服务器7*24运行，但是万一意外发生的话，没有持久化的数据就会全部丢失

#### 2. 持久层

-   什么是持久层？

    -   专门负责持久化操作的逻辑层，由该层统一对数据库层进行操作

        ![img](https://gitee.com/primabrucexu/image/raw/main/img/20201030153256.jpeg)

### 1.3 为什么要用MyBatis

-   用的人多啊，用的人多说明肯定优秀啊，不优秀的话为什么用的人多呢

-   简化了传统的JDBC操作，提高了开发效率

-   **所有的事情，不用Mybatis依旧可以做到，只是用了它，所有实现会更加简单！**

    ==技术没有高低之分，只有使用这个技术的人有高低之别==

----

## 二、Hello MyBatis

### 2.1 搭建测试环境

-   建立测试数据库

    ~~~sql
    DROP TABLE IF EXISTS user;
    
    CREATE TABLE `user` (
      `id` int NOT NULL AUTO_INCREMENT,
      `name` varchar(20) DEFAULT (NULL),
      `pwd` varchar(20) DEFAULT ('123456'),
      PRIMARY KEY (`id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
    
    INSERT INTO `mybatis`.`user`(`name`, `pwd`) 
    VALUES ('李四', '12345'),('张三','1234'),('bruce','root');
    ~~~

-   在maven中导入MySQL和MyBatis依赖

    ~~~xml
    <!--MySQL驱动-->
    <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
        <version>8.0.22</version>
    </dependency>
    
    <!--MyBatis-->
    <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
    <dependency>
        <groupId>org.mybatis</groupId>
        <artifactId>mybatis</artifactId>
        <version>3.5.6</version>
    </dependency>
    ~~~

-   创建核心配置文件

    ~~~xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE configuration
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
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
    </configuration>
    ~~~

### 2.2 准备工作

-   创建MyBatis工具类

    -   用于构建 SqlSessionFactory，MyBatis中的SqlSession提供了在数据库执行 SQL 命令所需的所有方法。我们可以通过 SqlSession 实例来直接执行已映射的 SQL 语句

    ~~~java
    package com.pbx.utils;
    
    import org.apache.ibatis.io.Resources;
    import org.apache.ibatis.session.SqlSession;
    import org.apache.ibatis.session.SqlSessionFactory;
    import org.apache.ibatis.session.SqlSessionFactoryBuilder;
    
    import java.io.IOException;
    import java.io.InputStream;
    
    /**
     * @author BruceXu
     * @date 2020/10/30
     */
    public class MyBatisUtils {
        private static SqlSessionFactory sqlSessionFactory;
        static {
            try {
                String resource = "mybatis-config.xml";
                InputStream inputStream = Resources.getResourceAsStream(resource);
                 sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        public static SqlSession getSqlSession() {
            return sqlSessionFactory.openSession();
        }
    }
    ~~~

-   创建实体类

    ~~~java
    package com.pbx.pojo;
    
    /**
     * @author BruceXu
     * @date 2020/10/30
     */
    public class User {
        private int id;
        private String name;
        private String pwd;
    
        public User() {
        }
    
        public User(int id, String name, String pwd) {
            this.id = id;
            this.name = name;
            this.pwd = pwd;
        }
    
        public int getId() {
            return id;
        }
    
        public void setId(int id) {
            this.id = id;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public String getPwd() {
            return pwd;
        }
    
        public void setPwd(String pwd) {
            this.pwd = pwd;
        }
    
        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", pwd='" + pwd + '\'' +
                    '}';
        }
    }
    ~~~

-   编写Mapper接口

    ```java
    package com.pbx.mapper;
    
    import com.pbx.pojo.User;
    
    import java.util.List;
    
    /**
     * @author BruceXu
     * @since 2020/10/30
     */
    public interface UserMapper {
        List<User> getUsers();
    }
    ```

### 2.3 编写代码

-   编写Mapper.xml配置文件

    -   **namespace要和mapper接口文件的包名保持一致**
    -   id要对应上mapper接口中提供的函数名
    -   resultType代表SQL语句的返回值
    -   parameterType表示对应方法中需要接收的参数类型
    -   **如果出现需要传递参数，可以使用 `#{}`表示占位符。而且可以无视实体类中的权限修饰符，可以直接取出其中的属性**

    ~~~xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.pbx.mapper.UserMapper">
        <select id="getUsers" resultType="com.pbx.pojo.User">
        select * from mybatis.user
      </select>
    </mapper>
    ~~~

-   在mybatis-config.xml中注册UserMapper

    ~~~xml
    <mappers>
        <mapper resource="com/pbx/mapper/Mapper.xml" />
    </mappers>
    ~~~

### 2.4 测试

-   编写测试类

    ~~~java
    package com.pbx.mapper;
    
    import com.pbx.pojo.User;
    import com.pbx.utils.MyBatisUtils;
    import org.apache.ibatis.session.SqlSession;
    import org.junit.Test;
    
    import java.util.List;
    
    /**
     * @author BruceXu
     * @date 2020/10/30
     */
    public class TestUserMapper {
    
        @Test
        public void test() {
            SqlSession sqlSession = MyBatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> list = mapper.getUsers();
            for (User user : list) {
                System.out.println(user);
            }
        }
    
    }
    ~~~

-   测试运行

    -   运行出现如下错误

        ![](https://gitee.com/primabrucexu/image/raw/main/img/20201030194436.png)

    -   错误原因如下：Error parsing SQL Mapper Configuration. Cause: java.io.IOException: Could not find resource com/pbx/mapper/Mapper.xml，找不导我们的Mapper.xml文件。但是观察项目结构后发现，诶，Mapper.xml文件明明就有啊，为什么会找不到呢？

        ![image-20201030194811713](https://gitee.com/primabrucexu/image/raw/main/img/20201030194811.png)![image-20201030194901826](https://gitee.com/primabrucexu/image/raw/main/img/20201030194901.png)

        -   这个问题产生的原因是由于maven静态资源的导出存在问题，我们需要调整静态资源的过滤条件，能让maven过滤到他
        
    -    解决方法：修改maven的静态资源过滤

         ~~~xml
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

    -   修改之后，就可以到正确的输出结果了

        ![image-20201030195501941](https://gitee.com/primabrucexu/image/raw/main/img/20201030195501.png)

### 2.5 在Maven中使用MyBatis的一般步骤

1.  **导入相应的数据库驱动和MyBatis依赖**
2.  **编写mybatis-config.xml主要配置文件**
3.  **构建SqlSessionFactory**
4.  **创建实体类**
5.  **创建mapper接口，编写mapper.xml实现SQL操作**
6.  **==在mybatis-config.xml中配置注册使用到的mapper.xml文件==**
7.  **进行使用**

-   注意
    -   每个线程都应该有它自己的 SqlSession 实例。
    -   SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。 
    -   绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。 
    -   也绝不能将 SqlSession 实例的引用放在任何类型的托管作用域中，比如 Servlet 框架中的 HttpSession。 
    -   如果你现在正在使用一种 Web 框架，考虑将 SqlSession 放在一个和 HTTP 请求相似的作用域中。 
    -   ==换句话说，每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它==
    -   不仅仅是在web应用中，每当一个SqlSession用完之后，一定要关闭它

---

## 三、CRUD

-   注意：==增删改必须要当做事务进行执行，执行完之后要记得提交，如果执行失败记得回滚==

### 3.1 Retrieve(select)

-   mapper接口新增

    ~~~java
    User getUserById(int id);
    User getUserByName(String name);
    ~~~

-   xml配置文件新增

    ~~~xml
    <select id="getUserById" parameterType="int" resultType="com.pbx.pojo.User">
        select * from `mybatis`.`user` where `id` = #{id}
    </select>
    <select id="getUserByName" parameterType="String" resultType="com.pbx.pojo.User">
        select * from `mybatis`.`user` where `name` = #{name};
    </select>
    ~~~

-   测试

    ~~~java
    @Test
    public void testSelect() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(1);
        System.out.println(user);
        user = mapper.getUserByName("bruce");
        System.out.println(user);
        sqlSession.close();
    }
    ~~~

    ![image-20201030205835021](https://gitee.com/primabrucexu/image/raw/main/img/20201030205835.png)

### 3.2 Create(insert)

-   mapper接口新增

    ~~~java
    int addUser(User user);
    ~~~

-   xml配置文件新增

    ~~~xml
    <insert id="addUser" parameterType="com.pbx.pojo.User">
        insert into `mybatis`.`user` (`id`, `name`, `pwd`) values (#{id}, #{name}, #{pwd});
    </insert>
    ~~~

-   测试

    ~~~java
    @Test
    public void testInsert() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        mapper.addUser(new User(4, "华某人", "woshishabi"));
        User user = mapper.getUserById(4);
        System.out.println(user);
        sqlSession.commit();
        sqlSession.close();
    }
    ~~~

    ![image-20201030210348353](https://gitee.com/primabrucexu/image/raw/main/img/20201030210348.png)

### 3.3 Update

-   mapper接口新增

    ~~~java
    int updateUserName(User user);
    ~~~

-   xml配置文件新增

    ~~~xml
    <update id="updateUserName" parameterType="com.pbx.pojo.User">
        update `mybatis`.`user` set `name` = #{name} where id = #{id};
    </update>
    ~~~

-   测试

    ~~~java
    @Test
    public void testUpdate() {
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println("更新前：");
        System.out.println(mapper.getUserById(4));
        mapper.updateUserName(new User(4,"小猪","woshishabi"));
        System.out.println("更新后：");
        System.out.println(mapper.getUserById(4));
        sqlSession.commit();
        sqlSession.close();
    }
    ~~~

    ![image-20201030210845688](https://gitee.com/primabrucexu/image/raw/main/img/20201030210845.png)

### 3.4 Delete

-   mapper接口新增

    ~~~java
    int deleteUserById(int id);
    ~~~

-   xml配置文件新增

    ~~~xml
    <delete id="deleteUserById" parameterType="int">
        delete from `mybatis`.`user` where id = #{id};
    </delete>
    ~~~

-   测试

    ~~~java
    @Test
    public void testDelete(){
        SqlSession sqlSession = MyBatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        System.out.println("删除前：");
        System.out.println(mapper.getUserById(4));
        mapper.deleteUserById(4);
        System.out.println("删除后：");
        System.out.println(mapper.getUserById(4));
        sqlSession.commit();
        sqlSession.close();
    }
    ~~~

    ![image-20201030211107522](https://gitee.com/primabrucexu/image/raw/main/img/20201030211107.png)

### 3.5 模糊查询

-   ==注意，在进行模糊查询时，由于需要拼接通配符，所以可能会导致潜在的SQL注入的问题==

-   建议

    -   在Java业务逻辑代码中，传递通配符

        ~~~java
        string wildcardname = “%smi%”;
        list<name> names = mapper.selectlike(wildcardname);
        
        <select id=”selectlike”>
        	select * from foo where bar like #{value}
        </select>
        ~~~

    -   在SQL拼接中使用通配符，

        ~~~xml
        string wildcardname = “smi”;
        list<name> names = mapper.selectlike(wildcardname);
        
        <select id=”selectlike”>
        	select * from foo where bar like "%"#{value}"%"
        </select>
        ~~~

    -   以上方法均目的是在SQL语句的构造时，写死通配符。防止用户奇怪的输入从而导致sql注入的漏洞

-   ==永远不要相信用户的任何输入，安全第一==

### 3.6 奇淫技巧——通过map传递参数

-   在某些时候，实体类中的属性过多，但是我们又只需要修改其中的几个，那么，在业务执行的时候，传递实体类对象会导致操作复杂。

-   mapper接口、

    ~~~java
    int updateUser(Map<String, Object> map);
    ~~~

-   xml配置

    ~~~xml
    <update id="updateUser" parameterType="map">
        update `mybatis`.`user` set `name` = #{uname}, `pwd` = #{upwd} where id = #{uid};
    </update>
    ~~~

-   测试

    ```java
    public void testUpdate2(){
        SqlSession session = MyBatisUtils.getSqlSession();
        UserMapper mapper = session.getMapper(UserMapper.class);
        Map<String, Object> map = new HashMap<>();
        map.put("uid", 1);
        map.put("uname", "hello");
        map.put("upwd", "myu");
        User user = mapper.getUserById(1);
        System.out.println(user);
        mapper.updateUser(map);
        user = mapper.getUserById(1);
        System.out.println(user);
        session.commit();
        session.close();
    }
    ```

    ![image-20201031134922644](https://gitee.com/primabrucexu/image/raw/main/img/20201031134922.png)

-   注意：
  
    -   ==map中的key一定要和xml配置中设置的参数名相匹配==

---

## 四、配置MyBatis

### 4.1 核心配置文件——mybatis-config.xml

-   MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息。 配置文档的顶层结构如下：

-   configuration（配置）
    -   properties（属性）
    -   settings（设置）
    -   typeAliases（类型别名）
    -   typeHandlers（类型处理器）
    -   objectFactory（对象工厂）
    -   plugins（插件）
    -   environments（环境配置）
        -   environment（环境变量）
            -   transactionManager（事务管理器）
            -   dataSource（数据源）
    -   数据库厂商标识（databaseIdProvider）
    -   映射器（mappers）
-   **编写核心配置文件时，必须按照顺序进行**

### 4.2 属性（properties）

-   配置文件中的所有属性都可以在外部进行配置，并可以进行动态替换。

-   你既可以在典型的 Java 属性文件中配置这些属性，也可以在 properties 元素的子元素中设置。

-   示例：

    -   db.properties

    ~~~properties
    driver=com.mysql.jdbc.Driver
    url=jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&characterEncoding=utf8
    username=root
    password=123456
    ~~~

    -   mybatis-config.xml

    ~~~xml
    <properties resource="db.properties">
        <property name="password" value="111111"/>
    </properties>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${user}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
    ~~~

-   注意：如果配置文件中和xml标签中出现同名的属性，优先使用文件的中的值

### 4.3 类型别名（typeAliases）

-   类型别名可为 Java 类型设置一个缩写名字。 

-   它仅用于 XML 配置，意在降低冗余的全限定类名书写。

-   示例：

    -   mybatis-config.xml

        ~~~xml
        <!--方式一-->
        <typeAliases>
        	<typeAlias type="com.pbx.pojo.User" alias="BruceUser"/>
        </typeAliases>
        
        <!--方式二-->
        <typeAliases>
        	<package name="com.pbx.pojo"/>
        </typeAliases>
        ~~~

    -   方式一：

        -   如果这样配置别名，那么可以在mapper.xml文件中任何需要使用 `com.pbx.pojo.User`的地方使用 `BruceUser`

    -   方式二：

        -   直接扫描包名情况下，默认别名为对应实体类的类名，首字母小写。如果实体类中使用了 `@Alias(别名)` 的注解，那么别名则为注解中定义的名称

### 4.4 环境配置（environments）

-   MyBatis 可以配置成适应多种环境，这种机制有助于将 SQL 映射应用于多种数据库之中

-   **尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**

    -   创建指定环境的SQLSessionFactory实例：

        ~~~java
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment);
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(reader, environment, properties);
        ~~~

-   **事务管理器（transactionManager）**

    -   在 MyBatis 中有两种类型的事务管理器（也就是 type="[JDBC|MANAGED]"）：
    -   JDBC – 这个配置直接使用了 JDBC 的提交和回滚设施，它依赖从数据源获得的连接来管理事务作用域。
    -   MANAGED – 这个配置几乎没做什么。（一般不会使用）

-   **数据源（dataSource）**

    -   dataSource 元素使用标准的 JDBC 数据源接口来配置 JDBC 连接对象的资源。有三种内建的数据源类型（也就是 type="[UNPOOLED|POOLED|JNDI]"）

### 4.5 映射器（mappers）

#### 1. mappers标签

-   注册映射了SQL语句的文件

-   ==每个mapper.xml文件必须在mybatis-config.xml文件中注册==

-   常用注册方式：

    -   方式一：使用相对于类路径的资源引用

        ~~~xml
        <mappers>
          <mapper resource="org/mybatis/builder/AuthorMapper.xml"/>
          <mapper resource="org/mybatis/builder/BlogMapper.xml"/>
          <mapper resource="org/mybatis/builder/PostMapper.xml"/>
        </mappers>
        ~~~

    -   方式二：使用映射器接口实现类的完全限定名

        ~~~xml
        <mappers>
          <mapper class="org.mybatis.builder.AuthorMapper"/>
          <mapper class="org.mybatis.builder.BlogMapper"/>
          <mapper class="org.mybatis.builder.PostMapper"/>
        </mappers>
        ~~~

    -   方式三：包扫描模式，注册一个包下面的所有mapper.xml文件

        ~~~xml
        <mappers>
          <package name="org.mybatis.builder"/>
        </mappers>
        ~~~

    -   方式二和方式三注册的mapper.xml文件需要遵守命名规范，不然会找不到对应的文件

        -   **使用和映射器接口实现类完全一样名称**

#### 2. mapper.xml文件

-   文件示例

    ~~~xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
      PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
      "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="org.mybatis.example.BlogMapper">
      <select id="selectBlog" resultType="Blog">
        select * from Blog where id = #{id}
      </select>
    </mapper>
    ~~~

-   namespace：用于标识唯一的mapper接口实现文件，值要为mapper接口文件的完全限定路径

-   id：需要绑定接口中的函数名

-   resultType：绑定函数的返回值类型

-   parameterType：参数类型

    -   如果只有一个参数，可以省略返回值类型
    -   **如果使用实体类作为参数，请使用实体类中的属性作为参数名**
    -   **如果使用map作为参数，请使用map中的key作为参数名**（多个参数或者实体类中存在多个与业务操作无关的属性）

### 4.6 作用域（Scope）和生命周期

-   作用域和生命周期类别是至关重要的，错误的使用会导致非常严重的并发问题。

-   MyBatis运行流程图

    ![image-20201031151820990](https://gitee.com/primabrucexu/image/raw/main/img/20201031151821.png)

-   SqlSessionFactoryBuilder （类似于JDBC中创建连接池的操作）
    -   这个类可以被实例化、使用和丢弃，一旦创建了 SqlSessionFactory，就不再需要它了。 
    -   最佳作用域是方法作用域（也就是局部方法变量）

-   SqlSessionFactory（类似于JDBC中的连接池）
    -   SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。
    -    使用 SqlSessionFactory 的最佳实践是在应用运行期间==**不要重复创建多次**==，多次重建 SqlSessionFactory 被视为一种代码“坏习惯”。
    -   最佳作用域是应用作用域。 有很多方法可以做到，**最简单的就是使用单例模式或者静态单例模式。**

-   SqlSession （类似于JDBC中的连接数据库）
    -   每个线程都应该有它自己的 SqlSession 实例。
    -   SqlSession 的实例不是线程安全的
    -   最佳的作用域是请求或方法作用域。 
        -   绝对不能将 SqlSession 实例的引用放在一个类的静态域，甚至一个类的实例变量也不行。 
        -   也绝不能将 SqlSession 实例的引用放在任何类型的托管作用域中，比如 Servlet 框架中的 HttpSession。 
        -   如果你现在正在使用一种 Web 框架，考虑将 SqlSession 放在一个和 HTTP 请求相似的作用域中。 换句话说，每次收到 HTTP 请求，就可以打开一个 SqlSession，返回一个响应后，就关闭它。 
    -   ==这个关闭操作很重要==，如果不及时关闭，会导致资源的浪费与性能的降低

-   总结
    -   **==SqlSessionFactoryBuilder在SqlSessionFactory被创建之后就可以嗝屁了==**
    -   **==SqlSessionFactory在应用运行期间有且只能存在一个==**
    -   **==SqlSession用完之后要养成随手关闭的习惯==**

---

## 五、ResultMap——结果集映射

-   解决属性名和字段名不一致的问题

### 5.1 查询为null的问题

-   什么时候会出现这个问题？

    -   实体类中设置的属性名和数据库中的字段名不一致

-   问题复现

    -   数据库中字段设置

        ![image-20201101142133610](https://gitee.com/primabrucexu/image/raw/main/img/20201101142133.png)

    -   实体类中的属性设置

        ~~~java
        public class User {
            private int id;
            private String name;
            private String password;
        }
        ~~~

    -   查询测试

        ~~~java
        @Test
        public void test() {
            SqlSession sqlSession = MyBatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> list = mapper.getUsers();
            for (User user : list) {
                System.out.println(user);
            }
            sqlSession.close();
        }
        ~~~

        ![image-20201101142250992](https://gitee.com/primabrucexu/image/raw/main/img/20201101142251.png)

    -   从查询结果中可以看出，数据库中的pwd字段没有正确映射到实体类中的password属性上

### 5.2 问题分析

-   对SQL语句的分析：

    ```sql
    select * from user <==> select id,name,pwd from user
    ```


-   MyBatis通过查询语句中的字段，去对应的实体类中查找相应属性名的set方法。但是，在本例中，实体类中没有pwd的set方法，所以字段pwd的值无法赋值给实体类，也就造成了查询结果中放回为null的情况

-   解决方法

    -   指定别名，使得别名和java类中的属性名一一对应

    -   使用结果集进行映射

        ~~~xml
        <resultMap id="UserMap" type="User">
            <!-- column是数据库表的列名, property是对应实体类的属性名-->
        	<id column="id" property="id"/>
        	<result column="name" property="name"/>
        	<result column="pwd" property="password"/>
        </resultMap>
        ~~~

### 5.3 ResultMap

-   `resultMap` 元素是 MyBatis 中最重要最强大的元素。
-   它可以让你从 90% 的 JDBC `ResultSets` 数据提取代码中解放出来，并在一些情形下允许你进行一些 JDBC 不支持的操作。
-   实际上，在为一些比如连接的复杂语句编写映射代码的时候，一份 `resultMap` 能够代替实现同等功能的数千行代码。
-   ResultMap的设计思想
    -   **对简单的语句做到零配置**
    -   **对于复杂一点的语句，只需要描述语句之间的关系**

#### 1. 自动映射

-   对于简单的语句，我们可以不用手动配置ResultMap，因为ResultMap的自动映射完全可以满足我们的需求，我们需要做的就是确保属性名和查询到的字段名能一一对应上即可

-   示例

    -   数据库的字段名

    ![image-20201101142133610](https://gitee.com/primabrucexu/image/raw/main/img/20201101143628.png)

    -   实体类

        ~~~java
        public class User {
            private int id;
            private String name;
            private String pwd;
            public User() {
            }
        }
        ~~~

    -   mapper.xml

        ~~~xml
        <select id="getUsers" resultType="com.pbx.pojo.User">
            select * from user
        </select>
        ~~~

    -   测试

        ~~~java
        @Test
        public void test() {
            SqlSession sqlSession = MyBatisUtils.getSqlSession();
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            List<User> list = mapper.getUsers();
            for (User user : list) {
                System.out.println(user);
            }
            sqlSession.close();
        }
        ~~~

        ![image-20201101144012976](https://gitee.com/primabrucexu/image/raw/main/img/20201101144013.png)

    -   如果说实体类中的属性和数据库的字段不同，可以在SQL设置别名进行匹配

-   **简单来说，如果你的SQL语句足够简单，那么完全不需要手动配置ResultMap，可以通过SQL设置别名来使得自动装配可以正确执行。**

#### 2. 简单的手动映射

-   示例

    -   mapper.xml

    ```xml
    <resultMap id="userResultMap" type="User">
      <id property="id" column="user_id" />
      <result property="username" column="user_name"/>
      <result property="password" column="hashed_password"/>
    </resultMap>
    
    <select id="selectUsers" resultMap="userResultMap">
      select user_id, user_name, hashed_password
      from some_table
      where id = #{id}
    </select>
    ```

-   注意点
  
    -   在引用它的语句中设置 `resultMap` 属性就行了（注意去掉了 `resultType` 属性）


---

## 六、日志与分页

### 6.1 日志功能

-   MyBatis支持日志记录功能，需要在mybatis-config.xml文件中配置相应的参数以开启日志功能

-   mybatis-config.xml配置

    ~~~xml
    <settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
    </settings>
    ~~~

-   MyBatis支持的日志类型

    >   SLF4J | LOG4J | LOG4J2 | JDK_LOGGING | COMMONS_LOGGING | STDOUT_LOGGING | NO_LOGGING

-   日志输出示例

    ![image-20201101194522540](https://gitee.com/primabrucexu/image/raw/main/img/20201101194522.png)

    -   除了用户的输出之外，其余内容均是日志内容

### 6.2 log4j日志

-   通过使用Log4j，我们可以控制日志信息输送的目的地是控制台、文件、GUI组件，甚至是套接口服务器、NT的事件记录器、UNIX Syslog守护进程等
-   我们也可以控制每一条日志的输出格式
-   通过定义每一条日志信息的级别，我们能够更加细致地控制日志的生成过程。
-   通过一个配置文件来灵活地进行配置，而不需要修改应用的代码。

#### 使用步骤

1.  在maven中导入log4j的依赖

    ```xml
    <dependencies>
        <!-- https://mvnrepository.com/artifact/log4j/log4j -->
        <dependency>
            <groupId>log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.17</version>
        </dependency>
    </dependencies>
    ```

2.  在mybatis-config.xml中开启日志记录功能

    ```xml
    <settings>
        <setting name="logImpl" value="LOG4J"/>
    </settings>
    ```

3.  编写log4j.properties配置文件

    ```properties
    ### set log levels ###
    log4j.rootLogger = debug ,  stdout ,  D ,  E
    
    ### 输出到控制台 ###
    log4j.appender.stdout = org.apache.log4j.ConsoleAppender
    log4j.appender.stdout.encoding = UTF-8
    log4j.appender.stdout.Target = System.out
    log4j.appender.stdout.layout = org.apache.log4j.PatternLayout
    log4j.appender.stdout.layout.ConversionPattern =  %d{ABSOLUTE} %5p %c{1}:%L - %m%n
    
    ### 输出到日志文件 ###
    log4j.appender.D = org.apache.log4j.DailyRollingFileAppender
    log4j.appender.D.encoding = UTF-8
    log4j.appender.D.File = logs/log.log
    log4j.appender.D.Append = true
    log4j.appender.D.Threshold = DEBUG
    log4j.appender.D.layout = org.apache.log4j.PatternLayout
    log4j.appender.D.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
    
    ### 保存异常信息到单独文件 ###
    log4j.appender.E = org.apache.log4j.DailyRollingFileAppender
    log4j.appender.E.encoding = UTF-8
    log4j.appender.E.File = logs/error.log
    log4j.appender.E.Append = true
    log4j.appender.E.Threshold = ERROR
    log4j.appender.E.layout = org.apache.log4j.PatternLayout
    log4j.appender.E.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
    ```

### 6.3 分页

#### 1. 使用LIMIT进行分页

-   ·在SQL层面上进行分页操作

#### 2. 使用RowBounds进行分页

-   目前已经不流行了，这是通过Java代码实现的

-   mapper接口

    ~~~xml
    List<User> getUserByRowBounds();
    ~~~

-   mapper.xml文件

    ~~~xml
    <select id="getUserByRowBounds" resultType="user">
    	select * from user
    </select>
    ~~~

-   测试类

    ~~~java
    @Test
    public void testUserByRowBounds() {
    	SqlSession session = MybatisUtils.getSession();
    	int currentPage = 2; //第几页
    	int pageSize = 2; //每页显示几个
    	RowBounds rowBounds = new RowBounds((currentPage-1)*pageSize,pageSize);
    	//通过session.**方法进行传递rowBounds，[此种方式现在已经不推荐使用了]
    	List<User> users = session.selectList("com.kuang.mapper.UserMapper.getUserByRowBounds",null, rowBounds);
    	for (User user: users){
    		System.out.println(user);
    	}
    }
    ~~~

#### 3. 使用插件PageHelper

----

## 七、使用注解开发

### 7.1 面向接口编程

-   目的：==解耦，可拓展，提高代码复用== 
    -   在分层开发中，上层不用管下层的具体实现，大家都会遵守共同的标准，简化开发
-   在一个面向对象的系统中，系统的各种功能是由许许多多的不同对象协作完成的。在这种情况下，各个对象内部是如何实现自己的,对系统设计人员来讲就不那么重要了；
-   **各个对象之间的协作关系则成为系统设计的关键**。小到不同类之间的通信，大到各模块之间的交互，在系统设计之初都是要着重考虑的，这也是系统设计的主要工作内容。面向接口编程就是指按照这种思想来编程。

-   什么是接口
    -   接口从更深层次的理解，应是定义（规范，约束）与实现（名实分离的原则）的分离。
    -   接口的本身反映了系统设计人员对系统的抽象理解。
    -   接口应有两类：
        -   第一类是对一个 个体的抽象，它可对应为一个抽象体(abstract class)
        -   第二类是对一个 个体某一方面的抽象，即形成一个抽象面（interface）

### 7.2 MyBatis中的注解开发

-   对于像 BlogMapper 这样的映射器类来说，还有另一种方法来完成语句映射。 它们映射的语句可以不用 XML 来配置，而可以使用 Java 注解来配置。比如，上面的 XML 示例可以被替换成如下的配置：

    ```java
    public interface BlogMapper {
      @Select("SELECT * FROM blog WHERE id = #{id}")
      Blog selectBlog(int id);
    }
    ```

-   对于简单的语句，使用注解可以加快开发速度。但对于一些复杂的SQL语句，注解就显得力不从心

-   MyBatis的详细执行流程

    ![img](https://i.loli.net/2020/11/03/lmfunda4ECohqRV.png)



### 7.3 CRUD操作

1.  mapper接口文件

    ~~~java
    package com.pbx.mapper;
    
    import com.pbx.pojo.User;
    import org.apache.ibatis.annotations.*;
    
    import java.util.List;
    import java.util.Map;
    
    /**
     * @author BruceXu
     * @since 2020/10/30
     */
    public interface UserMapper {
        @Select("select * from user")
        List<User> getUsers();
    
        @Select("select * from user where id = #{pid}")
        User getUserByID(@Param("pid") int id);
    
        @Insert("insert into user (id,name,pwd) values (#{id}, #{name}, #{pwd})")
        int insert(User user);
    
        @Update("update user set name = #{newName} where id = #{id}")
        int update(@Param("newName") String name, @Param("id") int id);
    
        @Delete("delete from user where id = #{id}")
        int delete(@Param("id") int id);
    
    }
    ~~~

2.  注册mapper文件

    ~~~xml
    <mappers>
        <mapper class="com.pbx.mapper.UserMapper"/>
    </mappers>
    ~~~

3.  测试类

    ~~~java
    package com.pbx;
    
    import com.pbx.mapper.UserMapper;
    import com.pbx.pojo.User;
    import com.pbx.utils.MyBatisUtils;
    import org.apache.ibatis.session.SqlSession;
    import org.junit.Test;
    
    import java.util.List;
    
    /**
     * @author BruceXu
     * @date 2020/11/3
     */
    public class test {
        @Test
        public void test() {
            SqlSession session = MyBatisUtils.getSqlSession();
            UserMapper mapper = session.getMapper(UserMapper.class);
            List<User> list = mapper.getUsers();
            for (User user : list) {
                System.out.println(user);
            }
            System.out.println("=============================");
            mapper.insert(new User(5, "bf", "123"));
            User t = mapper.getUserByID(5);
            System.out.println(t);
            System.out.println("=============================");
            mapper.update("gf", 5);
            t = mapper.getUserByID(5);
            System.out.println(t);
            System.out.println("=============================");
            mapper.delete(5);
            list = mapper.getUsers();
            for (User user : list) {
                System.out.println(user);
            }
            System.out.println("=============================");
            session.commit();
            session.close();
        }
    }
    ~~~

    ![image-20201103173344555](https://gitee.com/primabrucexu/image/raw/main/img/image-20201103173344555.png)

### 7.4 @Param注解

-   @Param用于指定接口中参数在SQL映射的名字
    -   只有一个参数时，可以不使用（建议还是用上，养成习惯）
    -   在方法有多个参数时，基本类型+String需要使用@Param进行标注名称

---

## 八、处理多对一关系

-   什么是多对一关系？
    -   以公司部门和员工举例：一个公司部门下面可以有很多员工，但是一个员工只能属于一个部门
        -   从部门角度来看，一个部门下可以有多个员工，这就是一对多的关系
        -   从员工角度来看，多个员工均可以为同一个部门工作，这就是多对一的关系

### 8.1 构造测试环境

-   数据库设计

    ~~~sql
    CREATE TABLE `teacher` (
    `id` INT(10) NOT NULL,
    `name` VARCHAR(30) DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=INNODB DEFAULT CHARSET=utf8;
    
    INSERT INTO teacher(`id`, `name`) VALUES (1, '秦老师');
    INSERT INTO teacher(`id`, `name`) VALUES (2, '许老师');
    
    CREATE TABLE `student` (
    `id` INT(10) NOT NULL,
    `name` VARCHAR(30) DEFAULT NULL,
    `tid` INT(10) DEFAULT NULL,
    PRIMARY KEY (`id`),
    ) ENGINE=INNODB DEFAULT CHARSET=utf8;
    
    INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('1', '小明1', '1');
    INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('2', '小红1', '1');
    INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('3', '小张1', '1');
    INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('4', '小李1', '1');
    INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('5', '小王1', '1');
    INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('6', '小明2', '2');
    INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('7', '小红2', '2');
    INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('8', '小张2', '2');
    INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('9', '小李2', '2');
    INSERT INTO `student` (`id`, `name`, `tid`) VALUES ('10', '小王2', '2');
    ~~~

-   实体类设计

    -   Teacher实体类

    ```java
    package com.pbx.pojo;
    
    /**
     * @author BruceXu
     * @date 2020/11/4
     */
    public class Teacher {
        private int id;
        private String name;
    
        public Teacher() {
        }
    
        public Teacher(int id, String name) {
            this.id = id;
            this.name = name;
        }
    
        public int getId() {
            return id;
        }
    
        public void setId(int id) {
            this.id = id;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        @Override
        public String toString() {
            return "Teacher{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }
    }
    ```

    -   Student实体类

    ```java
    package com.pbx.pojo;
    
    /**
     * @author BruceXu
     * @date 2020/11/4
     */
    public class Student {
        private int id;
        private String name;
        private Teacher teacher;
    
        public Student() {
        }
    
        public Student(int id, String name, Teacher teacher) {
            this.id = id;
            this.name = name;
            this.teacher = teacher;
        }
    
        public int getId() {
            return id;
        }
    
        public void setId(int id) {
            this.id = id;
        }
    
        public String getName() {
            return name;
        }
    
        public void setName(String name) {
            this.name = name;
        }
    
        public Teacher getTeacher() {
            return teacher;
        }
    
        public void setTeacher(Teacher teacher) {
            this.teacher = teacher;
        }
    
        @Override
        public String toString() {
            return "Student{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", teacher=" + teacher +
                    '}';
        }
    }
    ```

-   mapper接口

    -   TeacherMapper.java

        ```java
        package com.pbx.mapper;
        
        import com.pbx.pojo.Teacher;
        import org.apache.ibatis.annotations.Param;
        import org.apache.ibatis.annotations.Select;
        
        import java.util.List;
        
        /**
         * @author BruceXu
         * @date 2020/11/4
         */
        public interface TeacherMapper {
        
            @Select("select * from teacher")
            List<Teacher> getTeacher();
            @Select("select * from teacher where id = #{tid}")
            Teacher getTeacherById(@Param("tid") int id);
        
        }
        ```

    -   StudentMapper.java

        ```java
        package com.pbx.mapper;
        
        import com.pbx.pojo.Student;
        
        import java.util.List;
        
        /**
         * @author BruceXu
         * @date 2020/11/4
         */
        public interface StudentMapper {
            List<Student> getStudent();
        }
        ```

-   StudentMapper.xml

    ```xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE mapper
            PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
    <mapper namespace="com.pbx.mapper.StudentMapper">
        <select id="getStudent" resultType="Student">
            select * from student
        </select>
    </mapper>
    ```

-   mybatis-config.xml

    ~~~xml
    <?xml version="1.0" encoding="UTF-8" ?>
    <!DOCTYPE configuration
            PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
            "http://mybatis.org/dtd/mybatis-3-config.dtd">
    <configuration>
        <properties resource="db.properties">
            <property name="password" value="111111"/>
        </properties>
        <typeAliases>
            <typeAlias type="com.pbx.pojo.Student" alias="Student"/>
            <typeAlias type="com.pbx.pojo.Teacher" alias="Teacher"/>
        </typeAliases>
        <environments default="development">
            <environment id="development">
                <transactionManager type="JDBC"/>
                <dataSource type="POOLED">
                    <property name="driver" value="${driver}"/>
                    <property name="url" value="${url}"/>
                    <property name="username" value="${user}"/>
                    <property name="password" value="${password}"/>
                </dataSource>
            </environment>
        </environments>
        <mappers>
            <package name="com.pbx.mapper"/>
        </mappers>
    </configuration>
    ~~~

-   注意：**Student实体类的设计**
  
    -   假设有这么一个需求，我们在查询学生的时候，需要输出对应老师的姓名。那么我们在student实体类的设计时，就要设计成Student类中包含一个Teacher对象。在代码上使用这个解决方案，避免在数据库中使用外键进行对应。

### 8.2  尝试查询老师信息和学生信息

-   尝试SQL查询

    ~~~sql
    SELECT s.id, s.name, t.name 
    from student s, teacher t
    where s.tid = t.id;
    /*
    +----+-------+--------+
    | id | name  | name   |
    +----+-------+--------+
    |  1 | 小明1 | 秦老师 |
    |  2 | 小红1 | 秦老师 |
    |  3 | 小张1 | 秦老师 |
    |  4 | 小李1 | 秦老师 |
    |  5 | 小王1 | 秦老师 |
    |  6 | 小明2 | 许老师 |
    |  7 | 小红2 | 许老师 |
    |  8 | 小张2 | 许老师 |
    |  9 | 小李2 | 许老师 |
    | 10 | 小王2 | 许老师 |
    +----+-------+--------+
    */
    ~~~

-   代码

    ```java
    import com.pbx.mapper.StudentMapper;
    import com.pbx.mapper.TeacherMapper;
    import com.pbx.pojo.Student;
    import com.pbx.pojo.Teacher;
    import com.pbx.utils.MyBatisUtils;
    import org.apache.ibatis.session.SqlSession;
    import org.junit.Test;
    
    import java.util.List;
    
    /**
     * @author BruceXu
     * @date 2020/11/4
     */
    public class MapperTest {
        @Test
        public void getTeacher() {
            System.out.println("getTeacher():");
            SqlSession sqlSession = MyBatisUtils.getSqlSession();
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            List<Teacher> teachers = mapper.getTeacher();
            for (Teacher teacher : teachers) {
                System.out.println(teacher);
            }
            Teacher teacher1 = mapper.getTeacherById(1);
            System.out.println(teacher1);
            Teacher teacher2 = mapper.getTeacherById(2);
            System.out.println(teacher2);
            System.out.println("===================");
            sqlSession.close();
        }
    
        @Test
        public void getStudent(){
            System.out.println("getStudent:");
            SqlSession session = MyBatisUtils.getSqlSession();
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            List<Student> students = mapper.getStudent();
            for (Student student : students) {
                System.out.println(student);
            }
            System.out.println("===================");
            session.close();
        }
    
    }
    ```

-   结果

    ![image-20201104212230038](https://gitee.com/primabrucexu/image/raw/main/img/image-20201104212230038.png)

-   问题：
    -   老师信息可以正确查询，但是学生中的老师信息无法正确查询
    -   因为数据库的字段名没有和实体类中的属性进行映射

### 8.3 进行查询嵌套处理

-   修改StudentMapper.xml

  ```xml
  <mapper namespace="com.pbx.mapper.StudentMapper">
      <select id="getStudent" resultMap="getStudent1">
          select * from student
      </select>
  
      <select id="getTeacher" resultType="Teacher">
          select * from teacher where id = #{id}
      </select>
  
      <resultMap id="getStudent1" type="Student">
          <result property="id" column="id" />
          <result property="name" column="name" />
          <association property="teacher" column="tid" javaType="Teacher" select="getTeacher" />
      </resultMap>
  </mapper>
  ```
  
-   说明

    -   property代表实体类中的属性名，column代表数据库中的字段名
    -   使用查询嵌套处理时，javaType要为子查询语句中对应的实体类类型

-   测试

    ~~~java
    @Test
    public void getStudent(){
        System.out.println("getStudent:");
        SqlSession session = MyBatisUtils.getSqlSession();
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        List<Student> students = mapper.getStudent();
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("===================");
        session.close();
    }
    ~~~

    ![image-20201105142207363](https://gitee.com/primabrucexu/image/raw/main/img/image-20201105142207363.png)

### 8.4 进行结果嵌套处理

-   修改StudentMapper.xml文件如下

    ~~~xml
    <mapper>
    	<select id="getStudent2" resultMap="getStudent2">
            select s.id sid, s.name sname , t.name tname
            from student s,teacher t
            where s.tid = t.id
        </select>
        <resultMap id="getStudent2" type="Student">
            <result property="id" column="sid"/>
            <result property="name" column="sname" />
            <association property="teacher" javaType="Teacher">
                <result property="name" column="tname" />
            </association>
        </resultMap>
    </mapper>
    ~~~

-   说明

    -   如果实体类中存在其他实体类的引用时，在编写resultMap时，需要使用association标签进行关联

-   测试

    ~~~java
    @Test
    public void getStudent2(){
        System.out.println("getStudent2:");
        SqlSession session = MyBatisUtils.getSqlSession();
        StudentMapper mapper = session.getMapper(StudentMapper.class);
        List<Student> students = mapper.getStudent2();
        for (Student student : students) {
            System.out.println(student);
        }
        System.out.println("===================");
        session.close();
    }
    ~~~

    

    ![](https://gitee.com/primabrucexu/image/raw/main/img/image-20201105142731961.png)

    -   这边查出来老师的id=0是因为没有映射teacher中的id。这里可以根据需求进行操作。如果需求需要显示出老师的id，那么只需要加一条映射即可
        -   ==注意，映射配置中的column必须是select中查出来的列名，不然还是会显示id为0==

-   错误示例

    -   mapper.xml

        ~~~xml
        <mapper>
            <select id="getStudent2" resultMap="getStudent2">
                select s.id sid, s.name sname , t.name tname
                from student s,teacher t
                where s.tid = t.id
            </select>
            <resultMap id="getStudent2" type="Student">
                <result property="id" column="sid"/>
                <result property="name" column="sname" />
                <association property="teacher" javaType="Teacher">
                    <result property="id" column="tid" />
                    <result property="name" column="tname" />
                </association>
            </resultMap>
        </mapper>
        ~~~

    -   运行结果

        ![image-20201105143304659](https://gitee.com/primabrucexu/image/raw/main/img/image-20201105143304659.png)

-   正确示例

    -   mapper.xml

        ~~~xml
        <mapper>
            <select id="getStudent2" resultMap="getStudent2">
                select s.id sid, s.name sname , t.name tname, s.tid stid
                from student s,teacher t
                where s.tid = t.id
            </select>
            <resultMap id="getStudent2" type="Student">
                <result property="id" column="sid"/>
                <result property="name" column="sname" />
                <association property="teacher" javaType="Teacher">
                    <result property="id" column="stid" />
                    <result property="name" column="tname" />
                </association>
            </resultMap>
        </mapper>
        ~~~

    -   运行结果

        ![image-20201105143415390](https://gitee.com/primabrucexu/image/raw/main/img/image-20201105143415390.png)

### 8.5 总结

-   查询嵌套类似于SQL中的嵌套查询
-   结果嵌套类似于SQL中的连表查询
-   ==若实体类中存在其他实体类对象的引用，需要使用association标签进行映射处理==
    -   property —— 实体类中属性名
    -   JavaType —— 实体类中所引用其他实体类的对象类型

---

## 九、处理一对多关系

###  9.1 构造测试环境

-   与8.1中的环境一样，唯一不同点在于实体类的设计

    -   Teacher.java

        ~~~java
        public class Teacher {
            private int id;
            private String name;
            private List<Student> studentList;
        }
        ~~~

    -   Student.java

        ~~~java
        public class Student {
            private int id;
            private String name;
            private int tid;
        }
        ~~~

### 9.2 尝试查询

-   测试

-   ```java
    import com.pbx.mapper.StudentMapper;
    import com.pbx.mapper.TeacherMapper;
    import com.pbx.pojo.Student;
    import com.pbx.pojo.Teacher;
    import com.pbx.utils.MyBatisUtils;
    import org.apache.ibatis.session.SqlSession;
    import org.junit.Test;
    
    import java.util.List;
    
    /**
     * @author BruceXu
     * @date 2020/11/5
     */
    public class TestMapper {
    
        @Test
        public void getStudent() {
            System.out.println("getStudent:");
            SqlSession session = MyBatisUtils.getSqlSession();
            StudentMapper mapper = session.getMapper(StudentMapper.class);
            List<Student> studentList = mapper.getStudent();
            for (Student student : studentList) {
                System.out.println(student);
            }
            session.close();
            System.out.println("===================");
        }
    
        @Test
        public void getTeacher() {
            System.out.println("getTeacher:");
            SqlSession session = MyBatisUtils.getSqlSession();
            TeacherMapper mapper = session.getMapper(TeacherMapper.class);
            List<Teacher> teacherList = mapper.getTeacher();
            for (Teacher teacher : teacherList) {
                System.out.println(teacher);
            }
    
            session.close();
        }
    }
    ```

-   结果

    ![image-20201105160329537](https://gitee.com/primabrucexu/image/raw/main/img/image-20201105160329537.png)

    -   同样的可以看出，在没有手动映射的时候，查询老师是查不出学生信息的

### 9.3 查询嵌套处理

-   mapper.xml

    ```xml
    <mapper namespace="com.pbx.mapper.TeacherMapper">
        <select id="getTeacher" resultMap="getTeacher1">
            select * from teacher
        </select>
        <resultMap id="getTeacher1" type="Teacher">
            <collection property="studentList" javaType="ArrayList" ofType="Student" column="id" select="getStudent"/>
        </resultMap>
        <select id="getStudent" resultType="Student">
            select * from student where tid = #{id}
        </select>
    </mapper>
    ```

-   测试

    ![image-20201105160841592](https://gitee.com/primabrucexu/image/raw/main/img/image-20201105160841592.png)

### 9.4 结果嵌套处理

-   mapper.xml

    ```xml
    <mapper>
        <select id="getTeacher2" resultMap="getTeacher2">
            SELECT s.id sid, s.`name` sname, t.`name` tname, t.id tid
            FROM student s, teacher t
            WHERE s.tid = t.id
        </select>
        <resultMap id="getTeacher2" type="Teacher">
            <result property="id" column="tid"/>
            <result property="name" column="tname"/>
            <collection property="studentList" ofType="Student">
                <result property="id" column="sid"/>
                <result property="name" column="sname"/>
                <result property="tid" column="tid"/>
            </collection>
        </resultMap>
    </mapper>
    ```

-   测试

    ![image-20201105172622787](https://gitee.com/primabrucexu/image/raw/main/img/image-20201105172622787.png)

## 十、动态SQL

-   什么是动态SQL？
    -   根据给出参数的不同，通过拼接等操作，生成不同的SQL语句

### 10.1 构造实验环境

-   数据库设计

    ~~~sql
    CREATE TABLE `blog` (
      `id` varchar(50) NOT NULL COMMENT '博客id',
      `title` varchar(100) NOT NULL COMMENT '博客标题',
      `author` varchar(30) NOT NULL COMMENT '博客作者',
      `create_time` datetime NOT NULL COMMENT '创建时间',
      `views` int NOT NULL COMMENT '浏览量'
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8
    ~~~

-   mybatis-config.xml

    ~~~xml
    <settings>
        <!--开启日志以及自动转换驼峰命名-->
        <setting name="logImpl" value="STDOUT_LOGGING"/>
        <setting name="mapUnderscoreToCamelCase" value="true"/>
    </settings>
    ~~~

-   实体类

    ```java
    package com.pbx.pojo;
    
    import java.util.Date;
    
    /**
     * @author BruceXu
     * @date 2020/11/8
     */
    public class Blog {
        private String id;
        private String title;
        private String author;
        private Date createTime;
        private int views;
    }
    ```

-   id工具类

    ```java
    package com.pbx.utils;
    
    import java.util.UUID;
    
    /**
     * @author BruceXu
     * @date 2020/11/8
     */
    public class IDUtils {
        public static String getId() {
            return UUID.randomUUID().toString().replaceAll("-","");
        }
    }
    ```

-   BlogMapper接口

    ~~~java
    package com.pbx.mapper;
    
    import com.pbx.pojo.Blog;
    import org.apache.ibatis.annotations.Insert;
    
    /**
     * @author BruceXu
     * @date 2020/11/8
     */
    public interface BlogMapper {
        @Insert("insert into blog (id, title, author, create_time, views) values (#{id},#{title},#{author},#{createTime},#{views});")
        int addBlog(Blog blog);
    }
    
    ~~~

-   插入数据

    ~~~java
    @Test
    public void addBlog() {
        SqlSession session = MyBatisUtils.getSqlSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Blog blog = new Blog();
        blog.setId(IDUtils.getId());
        blog.setTitle("Mybatis如此简单");
        blog.setAuthor("BruceXu");
        blog.setCreateTime(new Date());
        blog.setViews(9999);
    
        mapper.addBlog(blog);
    
        blog.setId(IDUtils.getId());
        blog.setTitle("Java如此简单");
        mapper.addBlog(blog);
    
        blog.setId(IDUtils.getId());
        blog.setTitle("Spring如此简单");
        mapper.addBlog(blog);
    
        blog.setId(IDUtils.getId());
        blog.setTitle("微服务如此简单");
        mapper.addBlog(blog);
    
        blog.setId(IDUtils.getId());
        blog.setTitle("狂神说Java真的好");
        mapper.addBlog(blog);
        session.commit();
        session.close();
    }
    ~~~

### 10.2 if

-   这个if和高级语言里面的判断语句if是一样的功能

-   **需求**

    -   根据author和title来查询博客，如果一个为空，则就按照另外一个进行查询

-   BlogMapper接口

    ~~~java
    List<Blog> getBlog(Map map);
    ~~~

-   BlogMapper.xml

    ~~~xml
    <mapper namespace="com.pbx.mapper.BlogMapper">
        <select id="getBlog" parameterType="map" resultType="Blog">
            select * from blog where 1=1
            <if test="title != null">
                and title = #{title}
            </if>
            <if test="author != null">
                and author = #{author}
            </if>
        </select>
    </mapper>
    ~~~

-   测试

    ~~~java
    @Test
    public void getBlog() {
        SqlSession session = MyBatisUtils.getSqlSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Map<String, String> map = new HashMap<>();
    //        map.put("title", "Mybatis如此简单");
    //        map.put("author", "BruceXu");
    
        List<Blog> blogList = mapper.getBlog(map);
        for (Blog blog : blogList) {
            System.out.println(blog);
        }
        session.close();
    }
    ~~~

    ![image-20201108230751709](https://gitee.com/primabrucexu/image/raw/main/img/image-20201108230751709.png)

    ![image-20201108231000629](https://gitee.com/primabrucexu/image/raw/main/img/image-20201108231000629.png)

    ![image-20201108231053256](https://gitee.com/primabrucexu/image/raw/main/img/image-20201108231053256.png)

-   **如果说if中的条件有满足的话，则拼接上SQL语句如果没有满足的话，则不进行拼接**

### 10.3 choose (when, otherwise)

-   choose的作用类似于高级语言中的switch~case语句块

-   需求：根据author或title来查询博客，有谁查谁，如果都没有就按照views查询

-   BlogMapper接口

    ~~~java
    List<Blog> getBlog2(Map map);
    ~~~

-   BlogMapper.xml

    ~~~xml
    <mapper>
        <select id="getBlog2" parameterType="map" resultType="Blog">
            select  * from blog where 1=1
            <choose>
                <when test="title != null">
                    and title = #{title}
                </when>
                <when test="author != null">
                    and author = #{author}
                </when>
                <otherwise>
                    and views = 9999
                </otherwise>
            </choose>
        </select>
    </mapper>
    ~~~

-   测试

    ~~~java
    @Test
    public void getBlog2() {
        SqlSession session = MyBatisUtils.getSqlSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Map<String, String> map = new HashMap<>();
    
    //        map.put("title", "Java如此简单");
    //        map.put("author", "BruceXu");
        List<Blog> blogList = mapper.getBlog2(map);
        for (Blog blog : blogList) {
            System.out.println(blog);
        }
        session.close();
    }
    ~~~

    ![image-20201108234816404](https://gitee.com/primabrucexu/image/raw/main/img/image-20201108234816404.png)

    ![image-20201108235014385](https://gitee.com/primabrucexu/image/raw/main/img/image-20201108235014385.png)

    ![image-20201108235101881](https://gitee.com/primabrucexu/image/raw/main/img/image-20201108235101881.png)

-   可以看到，如果when中的条件没有一个匹配上，那么拼接上otherwise中的内容。如果when中有多个匹配，那么则只拼接第一个匹配的

### 10.4 trim (where, set)

-   如果仔细看上面两个测试环境中使用的SQL语句，都会发现存在这么一点 `where 1=1`，这个是为了保证在进行拼接的时候不出现这样的错误：`select * from blog where and author = ...`

-   那么有没有这样的一种方法，能够自动判断是不是要加上and或者空格等符号以保证SQL语句的正确性？

-   trim标签，当然，MyBatis也为我们提前封装好了where和set标签

    ```xml
    <trim prefix="前缀匹配" prefixOverrides="前缀替换" suffix="后缀匹配" suffixOverrides="后缀替换">...</trim>
    ```
    
-   where和set示例

    -   BlogMapper接口

    ~~~java
    List<Blog> getBlog3(Map map);
    int updateBlog(Map map);
    ~~~

    -   BlohMapper.xml

    ~~~xml
    <mapper>
        <select id="getBlog3" parameterType="map" resultType="Blog">
            select * from blog
            <where>
                <if test="title != null">
                    and title = #{title}
                </if>
                <if test="author != null">
                    and author = #{author}
                </if>
            </where>
        </select>
        
        <update id="updateBlog" parameterType="map">
            update blog
            <set>
                <if test="title != null">
                    title = #{title},
                </if>
                <if test="author != null">
                    author = #{author},
                </if>
                <if test="view != null">
                    views = #{view},
                </if>
            </set>
        </update>
    </mapper>
    ~~~

    -   测试

    ~~~java
    @Test
    public void getBlog3() {
        SqlSession session = MyBatisUtils.getSqlSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Map<String, String> map = new HashMap<>();
    //        map.put("title", "Mybatis如此简单");
        map.put("author", "BruceXu");
        List<Blog> blog3 = mapper.getBlog3(map);
        for (Blog blog : blog3) {
            System.out.println(blog);
        }
        session.close();
    }
    
    @Test
    public void updateBlog() {
        SqlSession session = MyBatisUtils.getSqlSession();
        BlogMapper mapper = session.getMapper(BlogMapper.class);
        Map<String, String> map = new HashMap<>();
        map.put("view", "6666");
        map.put("id", "ccfb770fc1b64dbf8fd7379a564dcd9f");
        map.put("title", "狂神Java说的是真的好");
        mapper.updateBlog(map);
    
        List<Blog> blog3 = mapper.getBlog3(map);
        for (Blog blog : blog3) {
            System.out.println(blog);
        }
        session.commit();
        session.close();
    }
    ~~~

    ![image-20201109005119316](https://gitee.com/primabrucexu/image/raw/main/img/image-20201109005119316.png)

    -   可以看到，MyBatis都帮助我们成功的完成了SQL拼接，并没有出现任何错误

### 10.5 SQL片段

-   有些时候，在业务的处理过程中，可能某一部分SQL语句使用比较频繁，所以我们可以将其抽取出来，单独成为一个片段。然后在需要的使用通过 `<include>`标签导入即可

-   提取SQL片段

    ~~~xml
    <sql id="if-title-author">
    	<if test="title != null">
    		title = #{title}
    	</if>
    	<if test="author != null">
    		and author = #{author}
    	</if>
    </sql>
    ~~~

-   引用SQL片段

    ~~~xml
    <select id="queryBlogIf" parameterType="map" resultType="blog">
    	select * from blog
    	<where>
    		<!-- 引用 sql 片段，如果refid 指定的不在本文件中，那么需要在前面加上 namespace-->
    		<include refid="if-title-author" />
    		<!-- 在这里还可以引用其他的 sql 片段 -->
    	</where>
    </select>
    ~~~

### 10.6 foreach

-   动态 SQL 的另一个常见使用场景是对集合进行遍历（尤其是在构建 IN 条件语句的时候）。比如：

```
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  WHERE ID in
  <foreach item="item" index="index" collection="list"
      open="(" separator="," close=")">
        #{item}
  </foreach>
</select>
```

-   *foreach* 元素的功能非常强大，
    -   它允许你指定一个集合，声明可以在元素体内使用的集合项（item）和索引（index）变量。
    -   它也允许你指定开头与结尾的字符串以及集合项迭代之间的分隔符。这个元素也不会错误地添加多余的分隔符，

-   **提示**
    -   你可以将**任何可迭代对象**（如 List、Set 等）、Map 对象或者数组对象作为集合参数传递给 *foreach*。
    -   当使用可迭代对象或者数组时，index 是当前迭代的序号，item 的值是本次迭代获取到的元素。
    -   当使用 Map 对象（或者 Map.Entry 对象的集合）时，index 是键，item 是值。

## 十一、cache（了解）

### 11.1 什么是缓存

1. 什么是缓存 [ Cache ]？
-   存在内存中的临时数据。
-   将用户经常查询的数据放在缓存（内存）中，用户去查询数据就不用从磁盘上(关系型数据库数据文件)查询，从缓存中查询，从而提高查询效率，解决了高并发系统的性能问题。
2. 为什么使用缓存？
-   减少和数据库的交互次数，减少系统开销，提高系统效率。
3. 什么样的数据能使用缓存？
  -   经常查询并且不经常改变的数据。

### 11.2 MyBatis缓存

-   MyBatis包含一个非常强大的查询缓存特性，它可以非常方便地定制和配置缓存。缓存可以极大的提升查询效率。
-   MyBatis系统中默认定义了两级缓存：一级缓存和二级缓存
    -   默认情况下，只有一级缓存开启。（SqlSession级别的缓存，也称为本地缓存）
    -   二级缓存需要手动开启和配置，他是基于namespace级别的缓存。
    -   为了提高扩展性，MyBatis定义了缓存接口Cache。我们可以通过实现Cache接口来自定义二级缓存

### 11.3 一级缓存

-   一级缓存，也叫本地缓存，顾名思义，在一次和数据库通信过程之间，期间查询到的数据都会存放在本地缓存中
-   因此，一级缓存的生命周期为：从SQLSession的SQLSession的关闭

-   ==注意==
    -   一级缓存是默认开启的，无法关闭
    -   一级缓存的几种行为
        -   一级缓存支持手动清除
        -   select时查询缓存，如果没有对应记录，则去数据库中查询
        -   update、delete、insert时会刷新缓存。因为这些操作可能导致之前缓存的数据发生变动。但是也可以人为的设置不去刷新缓存（用于性能调优）
    -   **一级缓存自SQLSession的创建开始便一直存在，直到被关闭。**如果有设置二级缓存，那么其中缓存的数据便会转存到二级缓存之中，如果没有，则会永久丢失

### 11.4 二级缓存

-   二级缓存也叫全局缓存。因为一级缓存的生命周期实在是太短了，所以诞生了二级缓存
-   基于namespace级别工作，一个命名空间对应一个二级缓存。即一个mapper.xml文件对应着一个二级缓存，且不同缓存之间并不能互相通信
-   工作机制
    -   **转存即将完蛋的一级缓存**

### 11.5 缓存流程

![image-20201109192334346](https://gitee.com/primabrucexu/image/raw/main/image/image-20201109192334346.png)

-   每当用户发起请求，MyBatis会先去二级缓存里面找，如果找不到，再去一级缓存里面找，如果没有的话，再去数据库中查

### 11.6 自定义缓存

-   MyBatis也支持用户自己定义缓存的行为，其需要实现org.apache.ibatis.cache.Cache 接口，且提供一个接受 String 参数作为 id 的构造器。





