# SpringMVC

## 一、回顾MVC与Servlet

### 1.1 回顾MVC

-   **什么是MVC？**

    -   MVC的全称是Model-View-Controller（模型-视图-控制器）
    -   **Model（模型）** —— 模型代表一个存取数据的对象或 JAVA POJO。它也可以带有逻辑，在数据变化时更新控制器。
    -   **View（视图）** —— 视图代表模型包含的数据的可视化。
    -   **Controller（控制器）** —— 控制器作用于模型和视图上。它控制数据流向模型对象，并在数据变化时更新视图。它使视图与模型分离开。

-   **为什么要使用MVC？**

    -   MVC要实现的目标是将软件用户界面和业务逻辑分离以使<u>代码可扩展性、可复用性、可维护性、灵活性加强。</u>

-   **正确的认识MVC**

    -   我一开始对MVC的认识：

        -   Model对应实体类和数据库操作，View负责界面，Controller则负责业务处理逻辑。相信这也是很多人对于MVC的认识和理解

    -   这种认识存在一个中误区，把MVC当成了三层架构（表示层（UI）、业务逻辑层（BLL）和数据访问层（DAL））

    -   实际上，这根本不是MVC的定义

        >-   V即View视图是指用户看到并与之交互的界面。比如由html元素组成的网页界面，或者软件的客户端界面。MVC的好处之一在于它能为应用程序处理很多不同的视图。在视图中其实没有真正的处理发生，它只是作为一种输出数据并允许用户操纵的方式。
        >
        >-   M即model模型是指模型表示业务规则。在MVC的三个部件中，模型拥有最多的处理任务。被模型返回的数据是中立的，模型与数据格式无关，这样一个模型能为多个视图提供数据，由于应用于模型的代码只需写一次就可以被多个视图重用，所以减少了代码的重复性。
        >
        >-   C即controller控制器是指控制器接受用户的输入并调用模型和视图去完成用户的需求，==控制器本身不输出任何东西和做任何处理==。它只是接收请求并决定调用哪个模型构件去处理请求，然后再确定用哪个视图来显示返回的数据。
        >-   摘自百度百科

    -   简单的概括一下，

        -   **Controller层，==只（ONLY）==，负责把View中的业务交给正确的Model去做，真正的业务逻辑在Model中**

-   **使用MVC框架的目的：**

    -   将URL映射到java的类或者方法上
    -   封装用户提交的数据
    -   处理用户请求，调用相应的业务处理，封装响应后的数据
    -   渲染页面

### 1.2 回顾Servlet

-   什么是Servlet？
    -   Servlet是sun公司提供的一门用于开发动态web资源的技术 。Sun公司在其API中提供了一个servlet接口，用户若想用发一个动态web资源(即开发一个Java程序向浏览器输出数据)，需要完成以下2个步骤：
        　　1、编写一个Java类，实现servlet接口。
            　　2、把开发好的Java类部署到web服务器中。
    -   通常我们也把实现了servlet接口的java程序，称之为Servlet
-   Servlet的运行流程
    -   web服务器收到客户端的Servlet访问请求后会调用Servlet程序
        -   Web服务器首先检查是否已经装载并创建了该Servlet的实例对象。如果是，则直接执行第④步，否则，执行第②步。
        -   装载并创建该Servlet的一个实例对象。
        -   调用Servlet实例对象的init()方法。
        -   创建一个用于封装HTTP请求消息的HttpServletRequest对象和一个代表HTTP响应消息的HttpServletResponse对象，然后调用Servlet的service()方法并将请求和响应对象作为参数传递进去。
        -   WEB应用程序被停止或重新启动之前，Servlet引擎将卸载Servlet，并在卸载之前调用Servlet的destroy()方法。
        
    -   图解
    
      ![19、JavaWeb](https://gitee.com/primabrucexu/image/raw/main/image/20201203201306.jpg)
      
      

## 二、什么是SpringMVC

### 2.1 简介

-   SpringMVC是Spring Framework中的一部分，基于Java实现的一种轻量级Web框架
-   **SpringMVC和Servlet关系**
    -   Servlet做的就是实现页面的跳转，负责给出请求对于的响应
    -   SpringMVC只是将Servlet进行封装，产出是DispatcherServlet。在使用SpringMVC的时候，我们只需要配置这么一个Servlet即可。 
    -   DispatcherServlet负责帮我们完成所有页面的跳转
    -   本质：还是使用了Servlet进行跳转，只不过SpringMVC将它进行封装，我们不需要手动去配置各种mapping映射，只需要告诉Spring容器，哪个页面对应哪个Java类即可

### 2.2 DispatcherServlet

-   Spring的web框架围绕DispatcherServlet设计。 DispatcherServlet的作用是将请求分发到不同的处 理器。
-   从Spring 2.5开始，使用Java 5或者以上版本的用户可以采用基于注解的controller声明方式。
-   Spring MVC框架像许多其他MVC框架一样, 以请求为驱动 , 围绕一个中心Servlet分派请求及提供其他功能，**DispatcherServlet是一个Servlet的具体实现类(它继承自HttpServlet 基类)。**

### 2.3 SpringMVC执行流程

![img](https://images2018.cnblogs.com/blog/1370903/201808/1370903-20180827201021158-682489195.png)

-   DispatcherServlet接收到用户请求
-   通过HandleMapping和HandlerAdapter找到对应的Handler处理请求
-   Handler处理完之后，返回ModelAndView
-   解析ModelAndView，生成对应的视图
-   将视图返回给用户

## 三、Hello SpringMVC

### 3.1 Servlet版

-   编写一个Servlet类

    ~~~java
    package com.pbx.servlet;
    
    import javax.servlet.ServletException;
    import javax.servlet.http.HttpServlet;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import java.io.IOException;
    
    /**
     * @author BruceXu
     * @date 2020/12/4
     */
    public class Hello extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String id = req.getParameter("id");
            if ("1".equals(id)) {
                req.getSession().setAttribute("msg", "id = 1");
            } else {
                req.getSession().setAttribute("msg", "id != 1");
            }
            // 进行视图跳转
            req.getRequestDispatcher("WEB-INF/jsp/hello.jsp").forward(req, resp);
        }
    
        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            super.doPost(req, resp);
        }
    }
    ~~~

-    写一个jsp作为View

    ```jsp
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>Hello</title>
    </head>
    <body>
    ${msg}
    </body>
    </html>
    ```

-   配置ServletMapping

    ```xml
    <servlet>
        <servlet-name>hello</servlet-name>
        <servlet-class>com.pbx.servlet.Hello</servlet-class>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>hello</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>
    ```

-   测试：

    ![image-20201205175839514](https://gitee.com/primabrucexu/image/raw/main/image/20201205175839.png)

-   部署一个Servlet的步骤

    1.  编写Servlet的具体实现类
    2.  编写View文件
    3.  在web.xml中配置Servlet



### 3.2 配置版

-   在web.xml中配置DispatcherServlet

    ```xml
    <!--注册DispatcherServlet-->
    <servlet>
        <servlet-name>dispatcherServlet</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
    </servlet>
    <!--SpringMVC的DispatcherServlet需要拦截所有请求，然后进行再分配-->
    <servlet-mapping>
        <servlet-name>dispatcherServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    ```

-   实现一个Controller

    ```java
    package com.pbx.controller;
    
    import org.springframework.web.servlet.ModelAndView;
    import org.springframework.web.servlet.mvc.Controller;
    
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    
    /**
     * @author BruceXu
     * @date 2020/12/5
     */
    public class Hello implements Controller {
        @Override
        public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
            ModelAndView mv = new ModelAndView();
            mv.addObject("msg", "Hello World SpringMVC");
            // 在这里进行视图跳转
            mv.setViewName("hello");
            return mv;
        }
    }
    ```

-   在Spring中注册`HandlerMapping`、`HandlerAdapter`、`ResourceViewResolve`、`Handler` （前三个是SpringMVC中不可获取的三大件）

    ~~~xml
    <!--处理器映射器 HandlerMapping-->
        <bean class="org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping"/>
        <!--处理器适配器 HandlerAdapter-->
        <bean class="org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter"/>
        <!--视图解析器 ResourceViewResolver-->
        <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
            <property name="prefix" value="/WEB-INF/jsp/"/>
            <property name="suffix" value=".jsp"/>
        </bean>
    
        <!--注册Handler-->
        <bean id="/hello" class="com.pbx.controller.Hello"/>
    ~~~

-   测试

    ![](https://gitee.com/primabrucexu/image/raw/main/image/20201205182830.png)

-   这么看起来，使用配置的SpringMVC并没有怎么简化
-   不要着急，我们别忘了Java中注解这个“作弊手段”

### 3.3 注解版

-   老样子，先注册DispatcherServlet，这个可是重中之重，必不可少

    ~~~xml
    <servlet>
        <servlet-name>springmvc</servlet-name>
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>classpath:springmvc-servlet.xml</param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    
    <servlet-mapping>
        <servlet-name>springmvc</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
    ~~~

-   添加SpringMVC配置文件

    ```xml
    <!--自动扫描注解-->
    <context:component-scan base-package="com.pbx.controller"/>
    <!--过滤掉静态资源，防止访问静态资源时也要被DispatcherServlet处理-->
    <mvc:default-servlet-handler/>
    <!--开启注解驱动-->
    <mvc:annotation-driven/>
    
    <!--视图处理器-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver" id="internalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/jsp/"/>
        <property name="suffix" value=".jsp"/>
    </bean>
    ```

-   实现一个Controller

    ```java
    package com.pbx.controller;
    
    import org.springframework.stereotype.Controller;
    import org.springframework.ui.Model;
    import org.springframework.web.bind.annotation.RequestMapping;
    
    /**
     * @author BruceXu
     * @date 2020/12/5
     */
    
    @Controller
    public class Hello {
    
        // 返回值为需要跳转到的视图文件名
        @RequestMapping("/hello")
        public String method(Model model) {
            model.addAttribute("msg", "Hello World Real SpringMVC");
    
            return "hello";
        }
    
    }
    ```

-   测试

    ![image-20201205190414993](https://gitee.com/primabrucexu/image/raw/main/image/20201205190415.png)



### 3.4 总结

-   SpringMVC三大组件：`HandlerMapping`、`HandlerAdapter`、`ResourceViewResolve`

-   在使用注解进行开发时，需要我们配置的只有视图解析器，其他的完全可以通过注解来代替配置文件的功能
-   SpringMVC开发的一般流程
    -   注册DispatcherServlet
    -   配置SpringMVC
    -   实现Controller类



## 四、RequestMapping与RESTful

### 4.1 @RequestMapping

-   **@RequestMapping** 注解用于将URL映射到一个控制器或者控制器中的一个方法上。如果作用在类上的话，访问地址就是类上的URL+方法上的URL

-    作用在方法上，访问路径：/test

    ~~~java
    @Controller
    public class TestController {
    
        @RequestMapping("/test")
        public String test1(Model model) {
            model.addAttribute("from", "TestController::test1()");
            model.addAttribute("msg", "test1");
            return "test";
        }
    
    }
    ~~~

    ![image-20201206143632009](https://gitee.com/primabrucexu/image/raw/main/image/20201206143632.png)

-   作用在类上，访问路径：/TestController/test

    ~~~java
    @Controller
    @RequestMapping("/TestController")
    public class TestController {
    
        @RequestMapping("/test")
        public String test1(Model model) {
            model.addAttribute("from", "TestController::test1()");
            model.addAttribute("msg", "test1");
            return "test";
        }
    } 
    ~~~

    ![image-20201206145202457](https://gitee.com/primabrucexu/image/raw/main/image/20201206145202.png)



### 4.2 RESTful风格

-   什么是RESTful？

    -   RESTful是一种设计风格，适用于网络应用程序

-   特点

    -   每一个URL代表1种资源；
    -   客户端使用GET、POST、PUT、DELETE4个表示操作方式的动词对服务端资源进行操作：GET用来获取资源，POST用来新建资源（也可以用于更新资源），PUT用来更新资源，DELETE用来删除资源；
    -   通过操作资源的表现形式来操作资源；
    -   资源的表现形式是XML或者HTML；
    -   客户端与服务端之间的交互在请求之间是无状态的，从客户端到服务端的每个请求都必须包含理解请求所必需的信息。
    -   一句话
        -   RESTful要求使用标准的HTTP方法对资源进行操作，**所以URL只应该来表示资源的名称，而不应该包括资源的操作**。 简单来说，==URL不应该使用动作来描述==。

-   传统的方式操作资源

    >-   http://127.0.0.1/item/queryItem.action?id=1 查询,GET
    >-   http://127.0.0.1/item/saveItem.action 新增,POST
    >-   http://127.0.0.1/item/updateItem.action 更新,POST
    >-   http://127.0.0.1/item/deleteItem.action?id=1 删除,GET或POST  
    -   在传统的模式下，对资源的操作是通过访问不同域名实现的

-   RESTful

    >-   http://127.0.0.1/item/1 查询,GET
    >-   http://127.0.0.1/item 新增,POST
    >-   http://127.0.0.1/item 更新,PUT
    >-   http://127.0.0.1/item/1 删除,DELETE

    -   使用RESTful操作资源，对资源的不同操作模式，是通过请求的不同方法实现的

### 4.3 SpringMVC中的RESTful

-   SpringMVC中，原生的支持RESTful设计。

-   点开@RequestMapping的源码，可以看到其中有关于请求方法的字段。

    ![image-20201206150446570](https://gitee.com/primabrucexu/image/raw/main/image/20201206150446.png)

-   其中，`RequestMethod` 是一个枚举类，其中包含了 `GET`、`HEAD`、`POST`、`PUT`、`PATCH`、`DELETE`、`OPTIONS`、`TRACE `等http的请求方法。

-   如果要使用RESTful的话，只需要在注解上加上响应的请求方法进行限定即可

-   例如

    ```java
    @Controller
    public class TestController {
    
        @RequestMapping(value = "/test", method = RequestMethod.POST)
        public String test1(Model model) {
            model.addAttribute("from", "TestController::test1()");
            model.addAttribute("msg", "test1");
            return "test";
        }
    
    }
    ```

    ![image-20201206151126965](https://gitee.com/primabrucexu/image/raw/main/image/20201206151127.png)

    -   在方法上限定了只能使用post方法去访问，那么我们使用普通的get方法就无法取得访问，要使用POST方法去请求才能正常访问

    ![image-20201206151301731](https://gitee.com/primabrucexu/image/raw/main/image/20201206151301.png)

-   实际上，@RequestMapping注解有许多衍生注解，他们直接限定了访问的方法，他们是 

    -   `@GetMapping`、`@PostMapping`、`@PutMapping`、`@DeleteMapping`、`@PatchMapping`

-   示例

    ~~~java
    @Controller
    public class DataController {
    
        @GetMapping("/t1/{id}/{name}")
        public String test1(@PathVariable int id, @PathVariable String name, Model model) {
            model.addAttribute("id", id);
            model.addAttribute("name", name);
            return "/test.jsp";
        }
    
    }
    ~~~

    ![image-20201206205855955](https://gitee.com/primabrucexu/image/raw/main/image/20201206205856.png)

-   只需要在参数名前加上 @PathVariable 注解即可使用路径作为参数进行传递

---

## 五、转发和重定向

### 5.1 ServletAPI

-   通过设置ServletAPI , 不需要视图解析器 
    -   通过HttpServletResponse实现重定向和转发

~~~java
@Controller
public class Go {
    @RequestMapping("/result/redirect")
    public void sendRedirect(HttpServletRequest req, HttpServletResponse rsp) throws IOException {
        // 重定向
        rsp.sendRedirect("/index.jsp");
    }
    
    @RequestMapping("/result/forward")
    public void forward(HttpServletRequest req, HttpServletResponse rsp) throws Exception {
        // 转发
        req.getRequestDispatcher("/WEB-INF/jsp/test.jsp").forward(req, rsp);
    }
}
~~~

### 5.2 SpringMVC

-   SpringMVC中可以通过前缀来支持重定向或者转发
    -   默认不加前缀时，转发
    -   前缀 `forward:` 表示转发，`redirect:` 表示重定向

~~~java
@Controller
public class Go {
    @RequestMapping("/result/t1")
    public String test1() {
        return "forward:/index.jsp";
    }
    
    @RequestMapping("/result/t2")
    public String test2() {
        return "redirect:/index.jsp";
    }

}
~~~

-   **注意，在进行转发和重定向时，要注意视图解析器对URL的影响**



## 六、数据处理

### 6.1 处理提交的数据

-     基本类型数据

    ~~~java
    @GetMapping("/t1")
    public String test1(@RequestParam("id") int id,@RequestParam("name") String name, Model model) {
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        return "test.jsp";
    }
    ~~~

    ![image-20201206210124402](https://gitee.com/primabrucexu/image/raw/main/image/20201206210124.png)

-   对象

    -   SpringMVC中，要求提交的表单域和对象的属性名一致 , 参数使用对象属性名即可提取

    -   Controller

        ```java
        @GetMapping("/t2")
        public String test2(User user, Model model) {
            model.addAttribute("user", user);
            return "test.jsp";
        }
        ```

    -   实体类

        ```java
        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public class User {
        
            private int id;
            private String name;
            private int age;
        
        }
        ```

    ![](https://gitee.com/primabrucexu/image/raw/main/image/20201206210634.png)

-   复杂类型数据

    -   SpringMVC对于复杂类型的数据并不非常友好，因为对于复杂类型的数据，通常使用JSON、XML等格式进行传输，这里只简单介绍一下数组类型的。List等类型的数据，还需要进行包装，这无疑是多此一举的操作

    -   数组，要对数组类型进行绑定，只需要对一个参数传入多个值即可

        -   Controller

            ~~~java
            @RequestMapping("/t3")
            public String test3(String[] hobby, Model model) {
                model.addAttribute("value", Arrays.toString(hobby));
                return "test.jsp";
            }
            ~~~

            ![](https://gitee.com/primabrucexu/image/raw/main/image/20201206214555.png)

-   **总结**

    -   别指望通过URL向后端传递各种复杂的数据。SpringMVC处理简单类型的数据传出还可以，但是对于复杂类型的数据，还是老老实实的用Json去传吧

### 6.2 向View传递数据

-   Model

    -   SpringMVC中的Model可以携带少量和简单的数据返回到前端，如

        ~~~java
        @GetMapping("/t1")
        public String test1(@RequestParam("id") int id,@RequestParam("name") String name, Model model) {
            model.addAttribute("id", id);
            model.addAttribute("name", name);
            return "test.jsp";
        }
        ~~~

-   ModelMap

    -   ModelMap继承了LinkedHashMap，这使得他具有Map的一些特性，优化了数据的封装过程
    -   ModelMap对象主要用于传递控制方法处理数据到结果页面，也就是说我们把结果页面上需要的数据放到ModelMap对象中即可，他的作用类似于request对象的setAttribute方法的作用:用来在一个请求过程中传递处理的数据。

    ~~~java
    @RequestMapping("/hello")
    public String hello(@RequestParam("username") String name, ModelMap model){
        //封装要显示到视图中的数据
        //相当于req.setAttribute("name",name);
        model.addAttribute("name",name);
        System.out.println(name);
        return "hello";
    }
    ~~~

    

### 6.3 处理乱码

-   SpringMVC中内置了一个过滤器，可以处理大多数情况下的乱码问题

-   设置方法

    ~~~xml
    <filter>
        <filter-name>encoding</filter-name>
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
        <init-param>
            <param-name>encoding</param-name>
            <param-value>utf-8</param-value>
        </init-param>
    </filter>
    <filter-mapping>
        <filter-name>encoding</filter-name>
        <url-pattern>/*</url-pattern>
    </filter-mapping>
    ~~~

-   注意：

    -   `/*`和`/`的区别
        -   `/*` ：会匹配任何请求
        -   `/` ：匹配任何请求，`*.jsp` 除外

---

## 七、SSM整合

### 7.1 环境配置

-   IDE：IDEA

-   数据库：MySQL 8.0.21

-   JDK14 + maven 3.6.3

-   maven依赖

    ~~~xml
    <dependencies>
        <!--JUnit-->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>
        
        <!--Spring框架-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>5.3.1</version>
        </dependency>
        
        <!--lombok-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.16</version>
            <scope>provided</scope>
        </dependency>
        
        <!--MySQL-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.22</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>5.3.1</version>
        </dependency>
      
        <!--MyBatis-->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>3.5.6</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>2.0.6</version>
        </dependency>
        
        <!--Servlet-->
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>javax.servlet-api</artifactId>
            <version>4.0.1</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        <dependency>
            <groupId>javax.servlet</groupId>
            <artifactId>jsp-api</artifactId>
            <version>2.0</version>
        </dependency>
    </dependencies>
    ~~~
    
-   设置maven静态资源过滤

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

    

### 7.2 数据库搭建

~~~sql
CREATE DATABASE `ssmbuild`;
USE `ssmbuild`;
DROP TABLE IF EXISTS `books`;
CREATE TABLE `books` (
`bookID` INT(10) NOT NULL AUTO_INCREMENT COMMENT '书id',
`bookName` VARCHAR(100) NOT NULL COMMENT '书名',
`bookCounts` INT(11) NOT NULL COMMENT '数量',
`detail` VARCHAR(200) NOT NULL COMMENT '描述',
KEY `bookID` (`bookID`)
) ENGINE=INNODB DEFAULT CHARSET=utf8
INSERT INTO `books`(`bookID`,`bookName`,`bookCounts`,`detail`)VALUES
(1,'Java',1,'从入门到放弃'),
(2,'MySQL',10,'从删库到跑路'),
(3,'Linux',5,'从进门到进牢');
~~~

### 7.3 系统架构设计

-   pojo：实体类包
-   mapper：MyBatis操作数据库接口，和pojo一起组成了Model
-   controller：Controller层
-   service：服务层

### 7.4 MyBatis

-   mysql配置文件

    ~~~properties
    driver=com.mysql.cj.jdbc.Driver
    url=jdbc:mysql://localhost:3306/mybatis?useSSL=true&useUnicode=true&characterEncoding=utf8&serverTimezone=GMT+8
    user=root
    pwd=123456
    ~~~

-   mybatis-config

    ~~~xml
    <typeAliases>
        <package name="com.pbx.pojo"/>
    </typeAliases>
    
    <mappers>
        <package name="com.pbx.mapper"/>
    </mappers>
    ~~~

-   spring整合MyBatis

    ```xml
    <!--扫描配置文件-->
    <context:property-placeholder location="classpath:mysql.properties"/>
    
    <!--配置数据源-->
    <bean id="dataSource" class="org.springframework.jdbc.datasource.DriverManagerDataSource">
        <property name="driverClassName" value="${driver}"/>
        <property name="url" value="${url}"/>
        <property name="username" value="${user}"/>
        <property name="password" value="${pwd}"/>
    </bean>
    
    <!--配置SQLSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"/>
        <property name="configLocation" value="mybatis-config.xml"/>
    </bean>
    
    <!--自动扫描mapper接口，生成实体类，交由Spring管理-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>
        <property name="basePackage" value="com.pbx.mapper"/>
    </bean>
    ```

-   实体类

    ```java
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Book {
    
        private int bookID;
        private String bookName;
        private int bookCount;
        private String detail;
    
    }
    ```

-   mapper接口

    ```java
    public interface BookMapper {
        // 增
        int addBook(Book book);
    
        // 删
        int deleteBookByID(@Param("bookid") int id);
    
        // 改
        int updateBook(Book book);
    
        // 查
        List<Book> selectAllBook();
        List<Book> selectBookByName(@Param("bookname") String name);
        Book selectBookByID(@Param("bookid") int id);
    }
    ```

-   mapper.xml

    ```xml
    <mapper namespace="com.pbx.mapper.BookMapper">
        <insert id="addBook" parameterType="book">
            insert into ssmbuild.books (bookName, bookCounts, detail) value (#{bookName}, #{bookCounts}, #{detail})
        </insert>
    
        <update id="updateBook" parameterType="book">
            update ssmbuild.books
            <set>
                <if test="bookName != null">bookName = #{bookName}</if>
                <if test="bookCounts != null">bookCounts = #{bookCounts}</if>
                <if test="detail != null">detail = #{detail}</if>
            </set>
            where bookID = #{bookID};
        </update>
    
        <delete id="deleteBookByID" parameterType="int">
            delete from ssmbuild.books where bookID = #{bookid};
        </delete>
    
        <select id="selectAllBook" resultType="book">
            select * from ssmbuild.books;
        </select>
        <select id="selectBookByName" resultType="book">
            select * from ssmbuild.books where bookName like '%'#{bookname}'%';
        </select>
        <select id="selectBookByID" resultType="com.pbx.pojo.Book">
            select * from ssmbuild.books where bookID = #{bookid};
        </select>
    </mapper>
    ```

### 7.5  Service层

-   service接口

    ```java
    public interface BookService {
        // 增
        int addBook(Book book);
    
        // 删
        int deleteBookByID(int id);
    
        // 改
        int updateBook(Book book);
    
        // 查
        List<Book> selectAllBook();
        List<Book> selectBookByName(String name);
        Book selectBookByID(int id);
    }
    ```

-   service实现类

    ```java
    public class BookServiceImpl implements BookService {
    
        private BookMapper mapper;
    
        public void setMapper(BookMapper mapper) {
            this.mapper = mapper;
        }
    
        @Override
        public int addBook(Book book) {
            return mapper.addBook(book);
        }
    
        @Override
        public int deleteBookByID(int id) {
            return mapper.deleteBookByID(id);
        }
    
        @Override
        public int updateBook(Book book) {
            return mapper.updateBook(book);
        }
    
        @Override
        public List<Book> selectAllBook() {
            return mapper.selectAllBook();
        }
    
        @Override
        public List<Book> selectBookByName(String name) {
            return mapper.selectBookByName(name);
        }
    
        @Override
        public Book selectBookByID(int id) {
            return mapper.selectBookByID(id);
        }
    }
    ```

-   spring整合service

    ```xml
    <!--给Service注入mapper-->
    <bean id="bookServiceImpl" class="com.pbx.service.BookServiceImpl">
        <property name="mapper" ref="bookMapper"/>
    </bean>
    
    <!--开启事务管理-->
    <bean class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>
    ```

### 7.6 View层 + Controller层

-   首页，index.jsp

    ~~~jsp
    <%@ page language="java" contentType="text/html; charset=UTF-8"
             pageEncoding="UTF-8" %>
    <!DOCTYPE HTML>
    <html>
    
    <head>
      <title>首页</title>
      <style type="text/css">
        a {
          text-decoration: none;
          color: black;
          font-size: 18px;
        }
    
        h3 {
          width: 180px;
          height: 38px;
          margin: 100px auto;
          text-align: center;
          line-height: 38px;
          background: deepskyblue;
          border-radius: 4px;
        }
      </style>
    </head>
    
    <body>
      <h3>
        <a href="${pageContext.request.contextPath}/book/allBook">点击进入详情页</a>
      </h3>
    </body>
    
    </html>
    ~~~

-   allBook.jsp

    ```jsp
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>书籍列表</title>
        <meta name="viewport" content="width=device-width, initialscale=
    1.0">
        <!-- 引入 Bootstrap -->
        <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="page-header"><h1>
                    <small>书籍列表 —— 显示所有书籍</small>
                </h1>
                </div>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 column">
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/book/toAdd">新增</a>
            </div>
        </div>
        <div class="row clearfix">
            <div class="col-md-12 column">
                <table class="table table-hover table-striped">
                    <thead>
                    <tr>
                        <th>书籍编号</th>
                        <th>书籍名字</th>
                        <th>书籍数量</th>
                        <th>书籍详情</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="book" items="${requestScope.get('list')}">
                        <tr>
                            <td>${book.getBookID()}</td>
                            <td>${book.getBookName()}</td>
                            <td>${book.getBookCounts()}</td>
                            <td>${book.getDetail()}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/book/toUpdate/${book.getBookID()}">更改</a> |
                                <a href="${pageContext.request.contextPath}/book/delete/${book.getBookID()}">删除</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    ```

-   addBook.jsp

    ~~~jsp
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>新增书籍</title>
        <meta name="viewport" content="width=device-width, initialscale=1.0">
        <!-- 引入 Bootstrap -->
        <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="page-header">
                    <h1>
                        <small>新增书籍</small>
                    </h1>
                </div>
            </div>
        </div>
        <form action="${pageContext.request.contextPath}/book/addBook" method="post">
            <div class="form-group">
                <label for="bookname">书籍名称</label>
                <input type="text" class="form-control" name="bookName" id="bookname" required>
            </div>
            <div class="form-group">
                <label for="bookcount">书籍数量</label>
                <input type="text" class="form-control" name="bookCounts" id="bookcount" required>
            </div>
            <div class="form-group">
                <label for="detail">书籍详情</label>
                <input type="text" class="form-control" name="detail" id="detail" required>
            </div>
            <div class="form-group">
                <input type="submit" class="form-control" value="提交">
            </div>
        </form>
    </div>
    ~~~

-   updateBook.jsp

    ~~~jsp
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>
    <head>
        <title>新增书籍</title>
        <meta name="viewport" content="width=device-width, initialscale=1.0">
        <!-- 引入 Bootstrap -->
        <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    </head>
    <body>
    <div class="container">
        <div class="row clearfix">
            <div class="col-md-12 column">
                <div class="page-header">
                    <h1>
                        <small>修改书籍</small>
                    </h1>
                </div>
            </div>
        </div>
        <form action="${pageContext.request.contextPath}/book/updateBook/${oldBook.bookID}" method="post">
            <div class="form-group">
                <label for="bookname">新书籍名称</label>
                <input type="text" class="form-control" name="bookName" id="bookname" value="${oldBook.bookName}">
            </div>
            <div class="form-group">
                <label for="bookcount">书籍数量</label>
                <input type="text" class="form-control" name="bookCounts" id="bookcount" value="${oldBook.bookCounts}">
            </div>
            <div class="form-group">
                <label for="detail">书籍详情</label>
                <input type="text" class="form-control" name="detail" id="detail" value="${oldBook.detail}">
            </div>
            <div class="form-group">
                <input type="submit" class="form-control" value="提交">
            </div>
        </form>
    </div>
    ~~~

-   Controller

    ~~~java
    @Controller
    @RequestMapping("/book")
public class BookController {
    
        @Autowired
        @Qualifier("bookServiceImpl")
        private BookService service;
    
        @RequestMapping("/allBook")
        public String getAllBook(Model model) {
            model.addAttribute("list", service.selectAllBook());
            return "allBook";
        }
    
        @RequestMapping("/toAdd")
        public String toAddPaper() {
            return "addBook";
        }
    
        @RequestMapping("/addBook")
        public String addBook(Book book) {
            System.out.println(book);
            service.addBook(book);
            return "redirect:allBook";
        }
    
        @RequestMapping("/delete/{id}")
        public String deleteBook(@PathVariable String id) {
            service.deleteBookByID(Integer.parseInt(id));
            return "redirect:/book/allBook";
        }
    
        @RequestMapping("/toUpdate/{id}")
        public String toUpdate(@PathVariable int id, Model model) {
            model.addAttribute("oldBook", service.selectBookByID(id));
            return "updateBook";
        }
    
        @RequestMapping("/updateBook/{id}")
        public String updateBook(@PathVariable int id, Book book) {
            book.setBookID(id);
            service.updateBook(book);
            return "redirect:/book/allBook";
        }
    
    }
    ~~~
    



## 八、JSON

### 8.1 什么是JSON

-   JSON: **J**ava**S**cript **O**bject **N**otation(JavaScript 对象表示法)
-   JSON 是存储和交换文本信息的语法，类似 XML。
-   JSON 比 XML 更小、更快，更易解析。
-   用处
    -   在现在前后端分离的时代中，前端和后端只管做自己的事情。如果需要数据交换的话，双方遵从约定的接口，从接口中拿到数据。
    -   而JSON相比于XML的优势就在于此。
        -   JSON本质上就是JS中对象的字符串表达形式，于是，前端可以非常方便的构建
        -   而在服务器上，制作一份JSON数据和制作一个XML文件并无多大区别
        -   相比于XML，JSON最大的优势还是在于解析上。XML的解析要依赖于特定的解析器，而JSON的解析则可通过JS中的内置函数完成，这点相当便捷

### 8.2 在SpringMVC中返回JSON数据

-   在SpringMVC中，使用 `@Controller` + `@ResponseBody` 或者 `@RestController` 即可使得请求返回String类型的字符串

-   在操作之前， 我们要用到的JSON格式化的工具：jackson。这种重复造轮子的事情能不做还是别去做了

    ~~~xml
    <dependency>
        <groupId>com.fasterxml.jackson.core</groupId>
        <artifactId>jackson-databind</artifactId>
        <version>2.12.0</version>
    </dependency>
    ~~~

-    示例

     ~~~java
     @Controller
     public class JsonController {
     
         @RequestMapping("/j1")
         @ResponseBody
         public String json1() {
             User user = new User(1,"张三",18);
             return JSON.toJSONString(user);
         }
     }
     ~~~

     ![image-20201207231531998](https://gitee.com/primabrucexu/image/raw/main/image/20201207231532.png)

-   这边发现出现了乱码的问题，需要设置下SpringMVC的String编码格式

    ~~~xml
    <mvc:annotation-driven>
        <mvc:message-converters>
            <bean class="org.springframework.http.converter.StringHttpMessageConverter">
                <property name="defaultCharset" value="utf-8"/>
            </bean>
        </mvc:message-converters>
    </mvc:annotation-driven>
    ~~~

-   注意：

    -   在使用JSON的时候要注意编码问题，使用统一的编码格式能避免出现乱码的问题

## 九、AJAX

### 9.1 什么是AJAX