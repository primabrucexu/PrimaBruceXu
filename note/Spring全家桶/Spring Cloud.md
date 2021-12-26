# SpringCloud

## 1 微服务概述

### 1.1 什么是微服务

-   微服务是近几年流行的一种架构思想，最早由Martin Fowler提出
    -   原文地址：https://martinfowler.com/articles/microservices.html
    -   中文翻译：http://www.bdata-cap.com/newsinfo/1713874.html
-   通常而言
    -   微服务架构是一种架构模式，或者说是一种架构风格， 
    -   它提倡将单一的应用程序划分成一组小的服务，每个服务运行在其独立的自己的进程内，服务之间互相协调，互相配置，为用户提供最终价值。
    -   服务之间采用轻量级的通信机制互相沟通，每个服务都围绕着具体的业务进行构建，并且能够被独立的部署到生产环境中，
    -   另外，应尽量避免统一的，集中式的服务管理机制，
    -   对具体的一个服务而言，应根据业务上下文，选择合适的语言，工具对其进行构建，
    -   可以有一个非常轻量级的集中式管理来协调这些服务，可以使用不同的语言来编写服务，也可以使用不同的数据存储；  
-   **说人话**
    -   微服务就是把以前的 all in one的应用进行拆分，使得其中的各个部分可以独立运行。在配上合理的管理机制，使得这些组件可以灵活的根据实际需求来进行部署

### 1.2 微服务优缺点

-   **优点**
    -   每个服务足够内聚，足够小，代码容易理解，这样能聚焦一个指定的业务功能或业务需求；
    -   开发简单，开发效率提高，一个服务可能就是专一的只干一件事；
    -   微服务能够被小团队单独开发，这个小团队是2~5人的开发人员组成；
    -   微服务是松耦合的，是有功能意义的服务，无论是在开发阶段或部署阶段都是独立的。
    -   微服务能使用不同的语言开发。
    -   易于和第三方集成，微服务允许容易且灵活的方式集成自动部署，通过持续集成工具，如jenkins，Hudson，bamboo
    -   微服务易于被一个开发人员理解，修改和维护，这样小团队能够更关注自己的工作成果。无需通过合作才能体现价值。
    -   微服务允许你利用融合最新技术。
    -   **微服务只是业务逻辑的代码，代码功能明确**
    -   **每个微服务都有自己的存储能力，可以有自己的数据库，也可以有统一数据库**
-   **缺点**
    -   开发人员要处理分布式系统的复杂性
    -   多服务运维难度，随着服务的增加，运维的压力也在增大
    -   系统部署依赖
    -   服务间通信成本
    -   数据一致性
    -   系统集成测试
    -   性能监控.....  

### 1.3 微服务涉及到的技术组件

|    技术条目    |                           解决方案                           |
| :------------: | :----------------------------------------------------------: |
|   微服务开发   |                      Spring、SpringBoot                      |
| 服务配置与管理 |                      Archaius、Diamond                       |
| 服务注册与发现 |                  Eureka、Zookeeper、Consul                   |
|   服务间调用   |                       rest、RPC、gRPC                        |
|    熔断机制    |                        Hystrix、Envoy                        |
|    负载均衡    |                        Ribbon、Nginx                         |
|  用户调用服务  |                       Feign、OpenFeign                       |
|    消息队列    |                  Kafka、RocketMQ、ActiveMQ                   |
|  服务配置中心  |                   SpringCloudConfig、Chef                    |
|    API网关     |                             Zuul                             |
|    服务监控    |             Zabbix、Nagios、Metrics、Specatator              |
|   全链路追踪   |                    Zipkin、Brave、Dapper                     |
|    服务部署    |                            Docker                            |
|     数据流     | SpringCloud Stream(封装与Redis，Rabbit，Kafka等发 送接收消息) |
|  消息事件总线  |                       SpringCloud Bus                        |

### 1.4 微服务的核心问题

-   这么多的服务，用户该怎么访问？
    -   API网关、负载均衡
-   这么多的服务，服务之间如何通信？
    -   服务调用
-   这么多的服务，我该怎么治理？
    -   服务注册与发现

-   这么多的服务，其中一个挂了怎么办？
    -   熔断降级机制

## 2 SpringCloud入门

### 2.1 是什么

-   官网
    -   SpringCloud, 基于SpringBoot提供了一套微服务解决方案，包括服务注册与发现，配置中心，全链路监控，服务网关，负载均衡，熔断器等组件，除了基于NetFlix的开源组件做高度抽象封装之外，还有一些选型中立的开源组件。
    -   SpringCloud利用SpringBoot的开发便利性，巧妙地简化了分布式系统基础设施的开发，SpringCloud为开发人员提供了快速构建分布式系统的一些工具，**包括配置管理，服务发现，断路器，路由，微代理，事件总线，全局锁，决策竞选，分布式会话**等等，他们都可以用SpringBoot的开发风格做到一键启动和部署。
    -   SpringBoot并没有重复造轮子，它只是将目前各家公司开发的比较成熟，经得起实际考研的服务框架组合起来，通过**SpringBoot风格进行再封装，屏蔽掉了复杂的配置和实现原理，最终给开发者留出了一套简单易懂，易部署和易维护的分布式系统开发工具包**
    -   SpringCloud 是 分布式微服务架构下的一站式解决方案，是各个微服务架构落地技术的集合体，俗称微服务全家桶  
-   **说人话**
    -   SpringCloud就是一个工具箱，要什么从里面拿就好了

### 2.2 SpringCloud和SpringBoot

-   SpringBoot专注于快速方便的开发单个个体微服务。
-   SpringCloud是关注全局的微服务协调整理治理框架，它将SpringBoot开发的一个个单体微服务整合并管理起来，为各个微服务之间提供：配置管理，服务发现，断路器，路由，微代理，事件总线，全局锁，决策竞选，分布式会话等等集成服务。
-   SpringBoot可以离开SpringClooud独立使用，开发项目，但是SpringCloud离不开SpringBoot，属于依赖关系
-   **SpringBoot专注于快速、方便的开发单个个体微服务，SpringCloud关注全局的服务治理框架**  

### 2.3 微服务常用套件

-   Spring Cloud Netflix

    |      服务      |    组件     |
    | :------------: | :---------: |
    |   分布式配置   |  Archaius   |
    | 服务注册与发现 |   Eureka    |
    |    服务熔断    |   Hystrix   |
    |    服务调用    | Feign，http |
    |    API网关     |    Zuul     |
    |    负载均衡    |   Ribbon    |

-   Apache Dubbo + Zookeeper

    |     服务     |     组件     |
    | :----------: | :----------: |
    |   API网关    | 无，自己搭配 |
    |   服务通信   |  Dubbo，RPC  |
    | 服务注册中心 |  Zookeeper   |
    |  熔断与降级  | 无，自己搭配 |

-   Spring Cloud Alibaba

    |      服务      |    组件     |
    | :------------: | :---------: |
    |   分布式配置   |    Nacos    |
    | 服务注册与发现 |    Nacos    |
    |    服务熔断    |  Sentinel   |
    |    服务调用    |  Dubbo RPC  |
    |    API网关     | Dubbo Proxy |
    |    负载均衡    |  Dubbo LB   |



### 2.4 构建基础框架

#### 1）父工程

-   pom.xml

    ~~~xml
    <?xml version="1.0" encoding="UTF-8"?>
    <project xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns="http://maven.apache.org/POM/4.0.0"
             xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
        <modelVersion>4.0.0</modelVersion>
    
        <groupId>com.pbx</groupId>
        <artifactId>SpringCloud</artifactId>
        <version>1.0.0</version>
        <modules>
            <module>API</module>
            <module>provider-default-8001</module>
        </modules>
    
        <properties>
            <project.bulid.sourceEncoding>UTF-8</project.bulid.sourceEncoding>
            <maven.compiler.source>11</maven.compiler.source>
            <maven.compiler.target>11</maven.compiler.target>
    
            <!--依赖版本管理-->
            <spring.cloud.version>Hoxton.SR10</spring.cloud.version>
            <spring.boot.version>2.3.8.RELEASE</spring.boot.version>
            <mysql.version>8.0.22</mysql.version>
            <mybatis.version>2.1.4</mybatis.version>
            <lombok.version>1.18.16</lombok.version>
            <druid.version>1.2.4</druid.version>
            <hutool.version>5.5.8</hutool.version>
            <swagger.version>3.0.0</swagger.version>
        </properties>
    
        <!--设置打包方式-->
        <packaging>pom</packaging>
    
        <dependencyManagement>
            <dependencies>
                <!--Spring Cloud 依赖-->
                <dependency>
                    <groupId>org.springframework.cloud</groupId>
                    <artifactId>spring-cloud-dependencies</artifactId>
                    <version>${spring.cloud.version}</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
    
                <!--Spring Boot 依赖-->
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-dependencies</artifactId>
                    <version>${spring.boot.version}</version>
                    <type>pom</type>
                    <scope>import</scope>
                </dependency>
    
                <!--Database-->
                <dependency>
                    <groupId>mysql</groupId>
                    <artifactId>mysql-connector-java</artifactId>
                    <version>${mysql.version}</version>
                </dependency>
                <dependency>
                    <groupId>org.mybatis.spring.boot</groupId>
                    <artifactId>mybatis-spring-boot-starter</artifactId>
                    <version>${mybatis.version}</version>
                </dependency>
                <dependency>
                    <groupId>com.alibaba</groupId>
                    <artifactId>druid</artifactId>
                    <version>${druid.version}</version>
                </dependency>
    
                <!--devtools-->
                <dependency>
                    <groupId>cn.hutool</groupId>
                    <artifactId>hutool-all</artifactId>
                    <version>${hutool.version}</version>
                </dependency>
                <dependency>
                    <groupId>org.projectlombok</groupId>
                    <artifactId>lombok</artifactId>
                    <version>${lombok.version}</version>
                </dependency>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-actuator</artifactId>
                    <version>${spring.boot.version}</version>
                </dependency>
                <dependency>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-devtools</artifactId>
                    <version>${spring.boot.version}</version>
                </dependency>
            </dependencies>
        </dependencyManagement>
    
    </project>
    ~~~
~~~
    
    

#### 2）API组件

-   建表SQL

    ~~~sql
    CREATE TABLE `department` (
      `d_id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '部门id',
      `d_name` varchar(255) DEFAULT NULL COMMENT '部门名',
      PRIMARY KEY (`d_id`)
    ) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
    
    INSERT INTO `department` VALUES (1, '开发部');
    INSERT INTO `department` VALUES (2, '策划部');
    INSERT INTO `department` VALUES (3, '市场部');
    INSERT INTO `department` VALUES (4, '销售部');
    INSERT INTO `department` VALUES (5, '人事部');
~~~

-   maven依赖

    ~~~xml
    <dependencies>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
        </dependency>
    </dependencies>
    ~~~

-   Message

    ~~~java
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Message implements Serializable {
        
        private int code;
        private String msg;
        private Object data;
        
        public Message(int code, String msg) {
            this(code, msg, null);
        }
    }
    ~~~

-   Department

    ~~~java
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class Department implements Serializable {
        
        private Long id;
        private String name;
    }
    ~~~

-   Swagger配置类

    ~~~java
    @EnableOpenApi
    @Configuration
    public abstract class Swagger3 {
    
        @Bean
        public Docket docket() {
            return new Docket(DocumentationType.OAS_30).apiInfo(apiInfo()).
                    select().apis(RequestHandlerSelectors.basePackage("com.pbx.springcloud.controller")).build();
        }
        
        protected abstract ApiInfo apiInfo();
    }
    ~~~

    

#### 3）provider-default-8001

-   maven依赖

    ~~~xml
    <dependencies>
        <!--api-->
        <dependency>
            <groupId>springcloud</groupId>
            <artifactId>API</artifactId>
            <version>1.0.0</version>
        </dependency>
    
        <!--database-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
        </dependency>
    
        <!--web-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
    
        <!--devtools-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
        </dependency>
    </dependencies>
    ~~~

-   application.yaml

    ~~~yaml
    server:
      port: 8001
    
    spring:
      application:
        name: provider-default-8001
      datasource:
        type: com.alibaba.druid.pool.DruidDataSource
        driver-class-name: com.mysql.cj.jdbc.Driver
        url: jdbc:mysql://localhost:3306/cloud-db01?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8
        username: root
        password: 123456
    
    mybatis:
      mapper-locations: classpath:mapper/*.xml
      type-aliases-package: com.pbx.springcloud.pojo
    ~~~

-   mapper

    -   接口

        ~~~java
        @Mapper
        @Repository
        public interface DepartmentMapper {
        
            boolean insertDepartment(Department department);
        
            Department getDepartmentById(@Param("id") Long id);
        
            List<Department> getDepartmentList();
        
            Department getByName(@Param("name") String name);
        }
        ~~~

    -   xml文件

        ~~~xml
        <mapper namespace="com.pbx.springcloud.mapper.DepartmentMapper">
        
            <resultMap id="BaseMap" type="department">
                <id property="id" column="d_id" jdbcType="BIGINT"/>
                <id property="name" column="d_name" jdbcType="VARCHAR"/>
            </resultMap>
        
            <insert id="insertDepartment" parameterType="department">
                insert into department (d_name)
                values (#{name})
            </insert>
        
            <select id="getDepartmentById" parameterType="long" resultMap="BaseMap">
                select *
                from department
                where d_id = #{id}
            </select>
        
            <select id="getDepartmentList" resultType="list" resultMap="BaseMap">
                select *
                from department
            </select>
            <select id="getByName" resultMap="BaseMap">
                select *
                from department
                where d_name = #{name}
            </select>
        
        </mapper>
        ~~~

-   service

    -   接口

        ~~~java
        public interface DepartmentService {
        
            Department insertDepartment(String name);
        
            Department getDepartmentById(@Param("id") Long id);
        
            List<Department> getDepartmentList();
        }
        
        ~~~

    -   实现类

        ~~~java
        @Service
        public class DepartmentServiceImpl implements DepartmentService {
        
            @Autowired
            private DepartmentMapper mapper;
        
            @Override
            public Department insertDepartment(String name) {
                mapper.insertDepartment(new Department(name));
                return mapper.getByName(name);
            }
        
            @Override
            public Department getDepartmentById(Long id) {
                return mapper.getDepartmentById(id);
            }
        
            @Override
            public List<Department> getDepartmentList() {
                return mapper.getDepartmentList();
            }
        }
        
        ~~~

-   controller

    ~~~java
    @RestController
    @RequestMapping("/provider")
    @Slf4j
    public class DepartmentController {
    
        @Autowired
        private DepartmentService service;
    
        @PostMapping("/insert")
        public Message insert(@RequestBody String name, HttpServletRequest request) {
            log.info("host:" + request.getRemoteAddr() + " try to insert a department, name: " + name);
            Department department = new Department();
            department.setName(name);
            Department res = service.insertDepartment(name);
            if (res != null && res.getId() != null) {
                log.info("insert successes, new department = " + res);
                return new Message(200, "插入成功", res);
            } else {
                log.info("insert failed");
                return new Message(400, "插入失败", res);
            }
        }
    
        @GetMapping("/get/{id}")
        public Message getDepartmentById(@PathVariable("id") Long id, HttpServletRequest request) {
            log.info("host:" + request.getRemoteAddr() + " try to get a department by id, id = " + id);
            Department res = service.getDepartmentById(id);
            if (res != null) {
                log.info("search successes, department = " + res);
                return new Message(200, "查找成功", res);
            } else {
                log.info("search failed");
                return new Message(400, "查找失败");
            }
        }
    
        @GetMapping("/get/all")
        public Message getAllDepartment(HttpServletRequest request) {
            log.info(request.getRemoteAddr() + " try to get department list");
            List<Department> list = service.getDepartmentList();
            if (list.size() > 0) {
                log.info("search success, + department nums = " + list.size());
                return new Message(200, "查找成功", list);
            } else {
                log.info("search failed");
                return new Message(400, "查找失败");
            }
        }
    }
    ~~~

-   集成Swagger

    -   配置类

        ~~~java
        @EnableOpenApi
        @Configuration
        public class SwaggerConfig extends Swagger3 {
        
            @Override
            public ApiInfo apiInfo() {
                return new ApiInfoBuilder().title("provider-default-8001").description("default cloud microservice provider-8001").version("1.0").build();
            }
        
        }
        ~~~

    -   访问测试：http://localhost:8001/swagger-ui/index.html#/

        ![image-20210219151836798](https://gitee.com/primabrucexu/image/raw/main/image/20210219151836.png)

#### 4）consumer-default-80

-   maven依赖

    ~~~xml
    <dependencies>
        <!--web-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>io.springfox</groupId>
            <artifactId>springfox-boot-starter</artifactId>
        </dependency>
    
        <!--api-->
        <dependency>
            <groupId>springcloud</groupId>
            <artifactId>API</artifactId>
            <version>1.0.0</version>
        </dependency>
    
        <!--devtools-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>
    ~~~

-   application.yaml

    ```yaml
    server:
      port: 80
    
    spring:
      application:
        name: consumer-default-80
    ```

-   RestTemplate

    ```java
    @Configuration
    public class RestTemplateConfig {
    
        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }
    
    }
    ```

-   controller

    ```java
    @RestController
    @RequestMapping("/consumer")
    public class ConsumerController {
    
        @Autowired
        private RestTemplate template;
    
        private static final String PROVIDER_URL_PREFIX = "http://127.0.0.1:8001/provider";
    
        @GetMapping("/add")
        public Message add(Department department) {
            return template.postForObject(PROVIDER_URL_PREFIX + "/insert", department, Message.class);
        }
    
        @GetMapping("/get/{id}")
        public Message getDepartmentById(@PathVariable Long id) {
            return template.getForObject(PROVIDER_URL_PREFIX + "/get/" + id, Message.class);
        }
    
        @GetMapping("/get/all")
        public Message getDepartmentList() {
            return template.getForObject(PROVIDER_URL_PREFIX + "/get/all", Message.class);
        }
    }
    ```

-   配置Swagger

    ```java
    @Configuration
    @EnableOpenApi
    public class SwaggerConfig extends Swagger3 {
        @Override
        protected ApiInfo apiInfo() {
            return new ApiInfoBuilder()
                    .title("consumer-default-80")
                    .description("Default Spring Cloud Micro Service Consumer :80")
                    .version("Default")
                    .build();
        }
    }
    ```

-   访问测试:http://localhost/swagger-ui/index.html#/

    ![image-20210219160234190](https://gitee.com/primabrucexu/image/raw/main/image/20210219160234.png)



## 3 Eureka

### 3.1 是什么

-   Eureka是Netflix的一个子模块，也是核心模块之一。Eureka是一个基于REST的服务，用于定位服务，以实现云端中间层服务发现和故障转移，服务注册与发现对于微服务来说是非常重要的，有了服务发现与注册，只需要使用服务的标识符，就可以访问到服务，而不需要修改服务调用的配置文件了，功能类似于Dubbo的注册中心，比如Zookeeper；  

### 3.2 基本架构

-   Netflix 在设计Eureka 时，遵循了AP原则。

-   Eureka采用了C-S的架构设计，EurekaServer 作为服务注册功能的服务器，他是服务注册中心而系统中的其他微服务。使用Eureka的客户端连接到EurekaServer并维持心跳连接。这样系统的维护人员就可以通过EurekaServer来监控系统中各个微服务是否正常运行，SpringCloud的一些其他模块（比如Zuul）就可以通过EurekaServer来发现系统中的其他微服务，并执行相关的逻辑；  

-   架构图

    ![image-20210219170736447](https://gitee.com/primabrucexu/image/raw/main/image/20210219170736.png)

-   **Eureka 包含两个组件：Eureka Server 和 Eureka Client 。**

    -   Eureka Server 提供服务注册服务，各个节点启动后，会在EurekaServer中进行注册，这样EurekaServer中的服务注册表中将会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观的看
        到。
    -   Eureka Client是一个Java客户端，用于简化EurekaServer的交互，客户端同时也具备一个内置的，使用轮询负载算法的负载均衡器。在应用启动后，将会向EurekaServer发送心跳（默认周期为30秒）。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳，EurekaServer将会从服务注册表中把这个服务节点移除掉（默认周期为90秒） 

-   三大角色

    -   Eureka Server：提供服务的注册于发现。
    -   Service Provider：将自身服务注册到Eureka中，从而使消费方能够找到。
    -   Service Consumer：服务消费方从Eureka中获取注册服务列表，从而找到消费服务  

### 3.3 单机服务搭建

#### 1）搭建Eureka服务器

-   maven依赖

    ```xml
    <dependencies>
        <!--eureka server-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
            <version>2.2.7.RELEASE</version>
        </dependency>
    
        <!--devtools-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>
    ```

-   application.yaml

    ```yaml
    server:
      port: 7000
      
    eureka:
      instance:
        hostname: localhost
      client:
        register-with-eureka: false   # 是否和Eureka一起注册
        fetch-registry: false   # 是否检索服务
        service-url:
          defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #  注册中心地址
    ```

-   启动类

    ```java
    @SpringBootApplication
    @EnableEurekaServer
    public class RegistryEureka_7000 {
        public static void main(String[] args) {
            SpringApplication.run(RegistryEureka_7000.class, args);
        }
    }
    ```

-   连接测试

    ![image-20210219171034507](https://gitee.com/primabrucexu/image/raw/main/image/20210219171034.png)

#### 2）改造provider

-   maven依赖

    ```xml
    <!--eureka client-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
        <version>3.0.1</version>
    </dependency>
    ```

-   配置文件

    ```yaml
    eureka:
      client:
        service-url:
         defaultZone: http://localhost:7000/eureka/
      instance:
        instance-id: BruceXu:Eureka Provider :8001
    ```

-   服务发现功能

    ~~~java
    @GetMapping("/discovery")
    public Object discovery() {
        List<String> services = discovery.getServices();
        System.out.println("已注册的服务列表：" + services);
        List<ServiceInstance> instances = discovery.getInstances("provider-eureka-8001");
        System.out.println("获取特定的服务实例" + instances);
    
        return this.discovery;
    }
    ~~~

-   启动类

    ```java
    @SpringBootApplication
    @EnableEurekaClient
    public class EurekaProvider_8001 {
        public static void main(String[] args) {
            SpringApplication.run(EurekaProvider_8001.class, args);
        }
    }
    ```

-   访问测试

    ![image-20210219172248460](https://gitee.com/primabrucexu/image/raw/main/image/20210219172248.png)

    

    ![image-20210219182034667](https://gitee.com/primabrucexu/image/raw/main/image/20210219182034.png)



### 3.4 Eureka自我保护机制

-   核心思想：**好死不如赖活着**

-   默认情况下，如果EurekaServer在一定时间内没有接收到某个微服务实例的心跳，EurekaServer将会注销该实例（默认90秒）。但是当网络分区故障发生时，微服务与Eureka之间无法正常通行，以上行为可能变得非常危险了
-   因为微服务本身其实是健康的，此时本不应该注销这个服务。Eureka通过 自我保护机制来解决这个问题
  
-   当EurekaServer节点在短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个节点就会进入自我保护模式。一旦进入该模式，EurekaServer就会保护服务注册表中的信息，不再删除服务注册表中的数据（也就是不会注销任何微服务）。当网络故障恢复后，该EurekaServer节点会自动退出自我保护模式。  
  
-   自我保护模式是一种应对网络异常的安全保护措施。它的架构哲学是宁可同时保留所有微服务（健康的微服务和不健康的微服务都会保留），也不盲目注销任何健康的微服务。使用自我保护模式，可以让Eureka集群更加的健壮和稳定

    -   和宁可杀错不可放过完全相反。Eureka采取了管你活不活，我先留着再说

        

### 3.5 Eureka集群

#### 1）改造host

~~~host
127.0.0.1 eureka7000
127.0.0.1 eureka7001
127.0.0.1 eureka7002
127.0.0.1 eureka7003
127.0.0.1 eureka7004
~~~



#### 2）改造registry

-   application.yaml

    ```yaml
    server:
      port: 7000
    
    spring:
      application:
        name: registry-eureka
    
    eureka:
      instance:
        hostname: eureka7000
        appname: ${spring.application.name}
      client:
        register-with-eureka: true   # 是否和Eureka一起注册
        fetch-registry: true   # 是否检索服务
        service-url:
          defaultZone: http://eureka7001:7001/eureka/, http://eureka7002:7002/eureka/, http://eureka7003:7003/eureka/, http://eureka7004:7004/eureka/
    ```
    
-   ==注意==

    -   如果是在单机上部署多个Eureka注册中心，那么`eureka.instance.hostname` 不能一样。否则必须要保持一直
    -   `spring.application.name` 也要保持一直，不然监控页面中的 `available-replicas` 会为空
    -   `eureka.client.register-with-eureka=true`，在集群的时候，注册中心需要让别的注册中心发现自己，所以需要把自己注册进去，这样的话才能让别的注册中心发现自己
    -   `eureka.client.fetch-registry=true`

#### 3）改造provider

-   application.yaml

    ```yaml
    eureka:
      client:
        service-url:
         defaultZone: http://eureka7000:7000/eureka/, http://eureka7001:7001/eureka/, http://eureka7002:7002/eureka/, http://eureka7003:7003/eureka/, http://eureka7004:7004/eureka/
      instance:
        instance-id: BruceXu:Eureka Provider :8001
    ```

#### 3）测试

![image-20210301151922100](https://gitee.com/primabrucexu/image/raw/main/image/20210301151922.png)

![image-20210301151936006](https://gitee.com/primabrucexu/image/raw/main/image/20210301151936.png)



### 3.6 Eureka vs Zookeeper

#### 1）CAP原则

-   ACID原则
    -   A（Atomicity）原子性
    -   C（Consistency） 一致性
    -   I （Isolation）隔离性
    -   D（Durability）持久性  
-   CAP原则
    -   C（Consistency）强一致性
    -   A（Availability）可用性
    -   P（Partition tolerance）分区容错性  
-   CAP原则又称CAP定理，指的是在一个分布式系统中，一致性（Consistency）、可用性（Availability）、分区容错性（Partition tolerance）。CAP 原则指的是，这三个要素最多只能同时实现两点，不可能三者兼顾。

-   **核心思想**
    -   ==CAP三个里面最多满足两个==

#### 2）Zookeeper

-   **Zookeeper保证的是CP**
-   当向注册中心查询服务列表时，我们可以容忍注册中心返回的是几分钟以前的注册信息，但不能接受服务直接down掉不可用。也就是说，服务注册功能对可用性的要求要高于一致性。
-   但是zk会出现这样一种情况，当master节点因为网络故障与其他节点失去联系时，剩余节点会重新进行leader选举。问题在于，选举leader的时间太长，30~120s，且选举期间整个zk集群都是不可用的，这就导致在选举期间注册服务瘫痪。在云部署的环境下，因为网络问题使得zk集群失去master节点是较大概率会发生的事件，长期不可用是不能容忍的。  

#### 3）Eureka

-   **Eureka保证的是AP**
-   Eureka在设计时就优先保证可用性。
-   Eureka各个节点都是平等的，几个节点挂掉不会影响正常节点的工作，剩余的节点依然可以提供注册和查询服务。而Eureka的客户端在向某个Eureka注册时，如果发现连接失败，则会自动切换至其他节点，只要有一台Eureka还在，就能保住注册服务的可用性，只不过查到的信息可能不是最新的，
-   除此之外，Eureka还有一种自我保护机制，如果在15分钟内超过85%的节点都没有正常的心跳，那么Eureka就认为客户端与注册中心出现了网络故障，此时会出现以下几种情况：
    -   Eureka不再从注册列表中移除因为长时间没收到心跳而应该过期的服务
    -   Eureka仍然能够接受新服务的注册和查询请求，但是不会被同步到其他节点上（即保证当前节点依然可用）
    -   当网络稳定时，当前实例新的注册信息会被同步到其他节点中  

==总结：Eureka可以很好的应对因网络故障导致部分节点失去联系的情况，而不会像zookeeper那样使整个注册服务瘫痪==

## 4 Ribbon

### 4.1 是什么

-   Spring Cloud Ribbon是基于Netflix Ribbon实现的一套客户端负载均衡的工具。
-   简单的说，Ribbon是Netflix发布的开源项目，主要功能是提供客户端的软件负载均衡算法，将NetFlix的中间层服务连接在一起。Ribbon的客户端组件提供一系列完整的配置项如：连接超时、重试等等。简单的说，就是在配置文件中列出LoadBalancer（简称LB：负载均衡）后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等等）去连接这些机器。我们也很容易使用Ribbon实现自定义的负载均衡算法

>   Load Balance介绍

-   负载均衡（Load Balance），在微服务或分布式集群中经常用的一种应用。
    负载均衡简单的说就是将用户的请求平摊的分配到多个服务上，从而达到系统的HA（高可用）。常见的负载均衡软件有 Nginx，Lvs 等等
-   简单分类：集中式和进程式
    -   集中式
        -   在服务方和消费方中间有一个独立的负载均衡设施。所有的请求都先访问这个设施，由这个设施来完成对请求的分发。比如说Nginx
    -   进程式
        -   进程式负载均衡则将负载均衡集成在消费方中，他获取所有可用的服务地址，然后通过负载均衡算法来决定方法哪一个服务

### 4.2 集成Ribbon

#### 1）pom.xml

-   由于新版的Eureka依赖中已经包含了Ribbon组件，所以我们无需再导入Ribbon的依赖

    ![image-20210301164428887](https://gitee.com/primabrucexu/image/raw/main/image/20210301164429.png)

-   **注意：这里如果重复导入，那么要保证Eureka和Ribbon的版本一致**

#### 2）application.yaml

~~~yaml
server:
  port: 80

spring:
  application:
    name: consumer-ribbon-80
eureka:
  client:
    register-with-eureka: false # 作为消费者，不需要向注册中心注册自己
    fetch-registry: true # 但是作为消费者，需要从注册中心中拉去服务信息
    service-url:
      defaultZone: http://eureka7000:7000/eureka/, http://eureka7001:7001/eureka/, http://eureka7002:7002/eureka/, http://eureka7003:7003/eureka/, http://eureka7004:7004/eureka/

~~~

#### 3）启动类

~~~java
@SpringBootApplication
@EnableEurekaClient // 我们要启用Eureka来获取可用的服务
public class RibbonConsumer_80 {
    public static void main(String[] args) {
        SpringApplication.run(RibbonConsumer_80.class, args);
    }
}
~~~

#### 4）负载均衡

~~~java
@Configuration
public class RestTemplateConfig {

    @LoadBalanced	// 一个注解就可以实现默认情况下的负载均衡
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
~~~

#### 5）controller

~~~java
@RestController
@RequestMapping("/consumer")
@Slf4j
public class ConsumerController {

    @Autowired
    private RestTemplate template;

    // private static final String PROVIDER_URL_PREFIX = "http://127.0.0.1:8001/provider";
    // 因为使用了负载均衡，所以访问服务的地址要从一个常量变成一个服务地址
    private static final String PROVIDER_URL_PREFIX = "http://PROVIDER-EUREKA-8001";

    @GetMapping("/add")
    public Message add(Department department) {
        return template.postForObject(PROVIDER_URL_PREFIX + "/provider/insert", department, Message.class);
    }

    @GetMapping("/get/{id}")
    public Message getDepartmentById(@PathVariable Long id) {
        return template.getForObject(PROVIDER_URL_PREFIX + "/provider/get/" + id, Message.class);
    }

    @GetMapping("/get/all")
    public Message getDepartmentList() {
        return template.getForObject(PROVIDER_URL_PREFIX + "/provider/get/all", Message.class);
    }
}
~~~

#### 6）测试

![image-20210301165023623](https://gitee.com/primabrucexu/image/raw/main/image/20210301165023.png)

![image-20210301165123084](https://gitee.com/primabrucexu/image/raw/main/image/20210301165123.png)

### 4.3 负载均衡



#### 1）改造消息传递组件

~~~java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {

    private int code;
    private String source;
    private String msg;
    private Object data;


    public Message(int code, String source, String msg) {
        this(code, msg, source, null);
    }

}
~~~

#### 2）改造controller

~~~java
@RestController
@RequestMapping("/provider")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    @Value("${spring.application.name}")
    private String source;

    @PostMapping("/insert")
    public Message insert(@RequestBody String name, HttpServletRequest request) {
        log.info("host:" + request.getRemoteAddr() + " try to insert a department, name: " + name);
        Department department = new Department();
        department.setName(name);
        Department res = service.insertDepartment(name);
        if (res != null && res.getId() != null) {
            log.info("insert successes, new department = " + res);
            return new Message(200, source, "插入成功", res);
        } else {
            log.info("insert failed");
            return new Message(400, source, "插入失败", res);
        }
    }

    @GetMapping("/get/{id}")
    public Message getDepartmentById(@PathVariable("id") Long id, HttpServletRequest request) {
        log.info("host:" + request.getRemoteAddr() + " try to get a department by id, id = " + id);
        Department res = service.getDepartmentById(id);
        if (res != null) {
            log.info("search successes, department = " + res);
            return new Message(200, source, "查找成功", res);
        } else {
            log.info("search failed");
            return new Message(400, source, "查找失败");
        }
    }

    @GetMapping("/get/all")
    public Message getAllDepartment(HttpServletRequest request) {
        log.info(request.getRemoteAddr() + " try to get department list");
        List<Department> list = service.getDepartmentList();
        if (list.size() > 0) {
            log.info("search success, + department nums = " + list.size());
            return new Message(200, source, "查找成功", list);
        } else {
            log.info("search failed");
            return new Message(400, source, "查找失败");
        }
    }
}@RestController
@RequestMapping("/provider")
@Slf4j
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    // 新增属性
    @Value("${eureka.instance.instance-id}")
    private String source;

    @PostMapping("/insert")
    public Message insert(@RequestBody String name, HttpServletRequest request) {
        log.info("host:" + request.getRemoteAddr() + " try to insert a department, name: " + name);
        Department department = new Department();
        department.setName(name);
        Department res = service.insertDepartment(name);
        if (res != null && res.getId() != null) {
            log.info("insert successes, new department = " + res);
            return new Message(200, source, "插入成功", res);
        } else {
            log.info("insert failed");
            return new Message(400, source, "插入失败", res);
        }
    }

    @GetMapping("/get/{id}")
    public Message getDepartmentById(@PathVariable("id") Long id, HttpServletRequest request) {
        log.info("host:" + request.getRemoteAddr() + " try to get a department by id, id = " + id);
        Department res = service.getDepartmentById(id);
        if (res != null) {
            log.info("search successes, department = " + res);
            return new Message(200, source, "查找成功", res);
        } else {
            log.info("search failed");
            return new Message(400, source, "查找失败");
        }
    }

    @GetMapping("/get/all")
    public Message getAllDepartment(HttpServletRequest request) {
        log.info(request.getRemoteAddr() + " try to get department list");
        List<Department> list = service.getDepartmentList();
        if (list.size() > 0) {
            log.info("search success, + department nums = " + list.size());
            return new Message(200, source, "查找成功", list);
        } else {
            log.info("search failed");
            return new Message(400, source, "查找失败");
        }
    }
}
~~~

#### 3）增加provider

#### 4）测试

![image-20210301184512028](https://gitee.com/primabrucexu/image/raw/main/image/20210301184512.png)

![image-20210301184524395](https://gitee.com/primabrucexu/image/raw/main/image/20210301184524.png)

![image-20210301184533668](https://gitee.com/primabrucexu/image/raw/main/image/20210301184533.png)

### 4.4 IRule

-   Ribbon到底干了什么？

    -   架构图

        ![image-20210301184739816](https://gitee.com/primabrucexu/image/raw/main/image/20210301184739.png)

    -   总结：==Ribbon就干了两件事，查询可用服务，以及负载均衡请求==

-   Ribbon中的负载均衡算法

    |           算法            |                 介绍                 |
    | :-----------------------: | :----------------------------------: |
    | AvailabilityFilteringRule |     排除高并发和掉线的之后的轮询     |
    |     BestAvailableRule     | 在能响应的服务器中选择并发数量最小的 |
    |        RandomRule         |               随机选择               |
    |         RetryRule         |                 重试                 |
    |      RoundRobinRule       |                 轮询                 |
    | WeightedResponseTimeRule  |           平均响应时间最快           |
    |     ZoneAvoidanceRule     |       基于区域和可用性进行选择       |



## 5 Feign

### 5.1 是什么

-   Feign和Ribbon都是Spring Cloud Netflix 中的负载均衡的组件。和Ribbon不同的是，使用Feign可以向我们使用Java中的接口一样方便
-   Feign方便之处
    -   通常，服务的调用分散在各个地方。如果我们使用Ribbon的话，如果说我们需要管理这些调用（维护、更换。。。），那么我们需要修改程序之中的每一处
    -   但是Feign则不一样。我们使用接口的方式进行调用，那么修改一处就好了



### 5.2 集成Feign

#### 1）pom.xml

~~~xml
<dependencies>
    <dependency>
        <groupId>springcloud</groupId>
        <artifactId>API</artifactId>
        <version>1.0.0</version>
    </dependency>

    <!--web-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <!--eureka-->
    <dependency>
       <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>

    <!--feign-->
    <dependency>
       <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
        <version>2.2.7.RELEASE</version>
    </dependency>

    <!--swagger-->
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-boot-starter</artifactId>
    </dependency>

    <!--devtools-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
    </dependency>
</dependencies>
~~~

#### 2）application.yaml

~~~yaml
server:
  port: 80

spring:
  application:
    name: consumer-feign-80
eureka:
  client:
    register-with-eureka: false # 作为消费者，不需要向注册中心注册自己
    fetch-registry: true # 但是作为消费者，需要从注册中心中拉去服务信息
    service-url:
     defaultZone: http://eureka7000:7000/eureka/, http://eureka7001:7001/eureka/, http://eureka7002:7002/eureka/, http://eureka7003:7003/eureka/, http://eureka7004:7004/eureka/

~~~

#### 3）启动类

~~~java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.pbx.springcloud")	// 扫描feign的注解
public class FeignConsumer {
    public static void main(String[] args) {
        SpringApplication.run(FeignConsumer.class, args);
    }
}
~~~

#### 4）FeignService

~~~Java
@Service	// 把Feign注册到Spring容器中
@FeignClient("PROVIDER-EUREKA")	// 配置要访问的服务名
@RequestMapping("/provider")	
public interface FeignService {

    @PostMapping("/add")
    Message add(Department department);

    @GetMapping("/get/{id}")
    Message getDepartmentById(@PathVariable("id") Long id);

    @GetMapping("/get/all")
    Message getDepartmentList();

}

~~~

#### 5）改造Controller

~~~java
@RestController
@RequestMapping("/consumer")
@Slf4j
public class ConsumerController {

    @Autowired
    private FeignService service;	// 这就回到了我们熟系的方式了

    @RequestMapping("/add")
    public Message add(Department department) {
        return service.add(department);
    }

    @RequestMapping("/get/{id}")
    public Message getDepartmentById(@PathVariable("id") Long id) {
        return service.getDepartmentById(id);
    }

    @RequestMapping("/get/all")
    public Message getDepartmentList() {
        return service.getDepartmentList();
    }
}

~~~

#### 6）测试

![image-20210312212044241](https://gitee.com/primabrucexu/image/raw/main/image/20210312212044.png)



## 6 Hystrix

### 6.1 是什么

官方介绍：https://github.com/Netflix/Hystrix/wiki

-   在现代的分布式系统中，通常一个服务会依赖多个其他的服务
    -   如图所示是正常情况下的服务

    ![soa-1-640](https://gitee.com/primabrucexu/image/raw/main/image/20210314193841.png)
    -   当发生了某些错误时

    ![soa-2-640](https://gitee.com/primabrucexu/image/raw/main/image/20210314193932.png)

    -   当只有一个发生错误的时候，我们的服务器完全可以负载其他的正常服务
    -   但是在高并发的情况下，仅一个错误就可以其很快的榨干服务器

    ![soa-3-640](https://gitee.com/primabrucexu/image/raw/main/image/20210314194224.png)

-   这就是Hystrix的目的所在，通过某些手段，提高系统的容灾性能

>   服务雪崩与服务熔断

-   服务雪崩
    -   在服务依赖链中，某些中间节点由于出现问题而无法及时响应调用，从而导致服务崩溃
-   服务熔断
    -   在服务链路中，某些节点出现问题的时候，将该节点降级，熔断这次调用，使得调用者可以快速得到相应信息

>   区分服务熔断和服务降级
>
>   https://zhuanlan.zhihu.com/p/341939685

-   服务降级
    -   通过限制部分低优先级别的服务来保证高优先级的服务正常运行
-   服务熔断
    -   应对雪崩效应的链路自我保护机制，简单来说就是在发生某些错误的时候也能正常返回消息
    -   未进行服务熔断时，程序在发生错误或异常的时候返回的数据可能是一个关于异常的消息之类的，这类消息常常无法被处理，从而导致整个服务链路的暂停。在实现了服务熔断之后，我们可以通过设置来使得返回的消息仍能继续在服务链路中流转下去，从而达到不堵塞服务链路的目的。

### 6.2 集成Hystrix

#### 1）pom.xml

~~~xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
~~~

#### 2）application.yaml

~~~yaml
server:
  port: 8001

spring:
  application:
    name: provider-eureka
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/cloud-db01?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2B8&allowPublicKeyRetrieval=true
    username: root
    password: 123456

mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.pbx.springcloud.pojo

eureka:
  client:
    service-url:
      defaultZone: http://eureka7000:7000/eureka/, http://eureka7001:7001/eureka/, http://eureka7002:7002/eureka/, http://eureka7003:7003/eureka/, http://eureka7004:7004/eureka/
    fetch-registry: true  # 作为服务提供商，需要到Eureka中注册一下
    register-with-eureka: true
  instance:
    instance-id: BruceXu:Eureka Provider:8001

~~~

#### 3）启动类

~~~java
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class HystrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class, args);
    }
}
~~~

#### 4）改造controller

~~~java
@RestController
@RequestMapping("/provider")
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    @Value("${eureka.instance.instance-id}")
    private String source;

    @GetMapping("/get/{id}")
    @HystrixCommand(fallbackMethod = "fallbackGet")
    public Message getDepartmentById(@PathVariable("id") Long id) {
        Department res = service.getDepartmentById(id);
        if (res == null) {
            throw new RuntimeException("找不到");
        }
        return new Message(200, "hystrix", "查找成功", res);
    }

    // 回调方法的参数要和原方法的参数保持一致
    public Message fallbackGet(@PathVariable Long id) {
        return new Message(303, "hystrix", "查找失败，这是Hystrix的失败回调",
                new Department().setId(id).setName("hystrix fallback"));
    }

    @GetMapping("/get/all")
    public Message getAllDepartment(HttpServletRequest request) {
        log.info(request.getRemoteAddr() + " try to get department list");
        List<Department> list = service.getDepartmentList();
        if (list.size() > 0) {
            log.info("search success, + department nums = " + list.size());
            return new Message(200, source, "查找成功", list);
        } else {
            log.info("search failed");
            return new Message(400, source, "查找失败");
        }
    }
}
~~~

#### 5）启动测试

-   正常调用

![image-20210411232457442](https://gitee.com/primabrucexu/image/raw/main/image/20210411232532.png)

-   正常失败的回调

![image-20210411232718690](https://gitee.com/primabrucexu/image/raw/main/image/20210411232718.png)

-   使用熔断之后的回调

![image-20210411232626912](https://gitee.com/primabrucexu/image/raw/main/image/20210411232626.png)

### 6.3 服务降级

相比于服务熔断，是在服务端做手脚。服务降级则是在客户端做手脚

#### 1）pom.xml

~~~xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
~~~

#### 2）application.yaml

~~~yaml
# 开启hystrix
feign:
  hystrix:
    enabled: true
~~~

#### 3）启动类

~~~java
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients("com.pbx.springcloud")
public class FeignConsumer {
    public static void main(String[] args) {
        SpringApplication.run(FeignConsumer.class, args);
    }
}
~~~

#### 4）服务降级配置

-   通过实现FallbackFactory接口来实现服务降级时的通知

~~~java
@Component
public class FallbackService implements FallbackFactory<FeignService> {

    @Override
    public FeignService create(Throwable throwable) {
        return new FeignService() {
            @Override
            public Message add(Department department) {
                return null;
            }

            @Override
            public Message getDepartmentById(Long id) {
                return new Message(304, "client", "服务暂时不可用");
            }

            @Override
            public Message getDepartmentList() {
                return null;
            }
        };
    }
}
~~~

#### 5）测试

-   正常访问

![image-20210412152805774](https://gitee.com/primabrucexu/image/raw/main/image/20210412152805.png)

-   服务降级之后

![image-20210412152832213](https://gitee.com/primabrucexu/image/raw/main/image/20210412152832.png)

### 6.4 DashBoard监控

#### 1）搭建监控端

-   pom.xml

    ~~~xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
    </dependency>
    ~~~

-   启动类

    ~~~java
    @EnableHystrixDashboard
    @SpringBootApplication
    public class DashboardApplication {
        public static void main(String[] args) {
            SpringApplication.run(DashboardApplication.class, args);
        }
    }
    ~~~

#### 2）修改提供者

-   增加依赖

    ~~~xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    ~~~

-   增加对外提供监控信息的servlet bean

    ~~~java
    @Bean
    public ServletRegistrationBean hystrixDashboard() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
        bean.addUrlMappings("/actuator/hystrix.stream");
        return bean;
    }
    ~~~

-   增加配置

    ~~~yaml
    management:
      endpoints:
        web:
          exposure:
            include:  hystrix.stream
    ~~~

#### 3）测试

![image-20210412161352771](https://gitee.com/primabrucexu/image/raw/main/image/20210412161352.png)

#### 4）数据解读

![Hystrix](https://gitee.com/primabrucexu/image/raw/main/image/20210412161830.png)

-   圆圈大小表示当前的并发量
-   绿色表示正常
-   蓝色是触发熔断
-   黄色是超时数
-   紫色是线程池拒绝数
-   红色则是失败或异常

## 7 Zuul

### 7.1 是什么

-   Zuul包含了对请求的路由和过滤两个最主要的功能：
    -   其中路由功能负责将外部请求转发到具体的微服务实例上，是实现外部访问统一入口的基础，而过滤器功能则负责对请求的处理过程进行干预，是实现请求校验，服务聚合等功能的基础。Zuul和Eureka进行整合，将Zuul自身注册为Eureka服务治理下的应用，同时从Eureka中获得其他微服务的消息，也即以后
        的访问微服务都是通过Zuul跳转后获得。

-   注意：Zuul服务最终还是会注册进Eureka
-   提供：代理 + 路由 + 过滤 三大功能  

### 7.2 集成zuul

-   pom.xml

    ~~~xml
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
    </dependency>
    ~~~

-   配置文件

    ~~~yaml
    server:
      port: 7289
    
    spring:
      application:
        name: gateway-zuul
    
    eureka:
      client:
        service-url:
          defaultZone: http://eureka7000:7000/eureka/, http://eureka7001:7001/eureka/, http://eureka7002:7002/eureka/, http://eureka7003:7003/eureka/, http://eureka7004:7004/eureka/
        fetch-registry: true
        register-with-eureka: true
      instance:
        instance-id: zuul.com
        prefer-ip-address: true
    
    zuul:
      # 忽略所有的服务名
      ignored-services: "*"
      # 设置访问前缀
      prefix: /pbx
      # 路由设置，Zuul和Eureka结合使用，可以实现路由的自动配置，自动配置的路由以服务名称为匹配路径
      routes:
        [新访问路径]: [原访问路径]
    ~~~

-   启动类

    ~~~java
    @SpringBootApplication
    @EnableZuulProxy
    public class GatewayApplication {
        public static void main(String[] args) {
            SpringApplication.run(GatewayApplication.class, args);
        }
    }
    ~~~

    