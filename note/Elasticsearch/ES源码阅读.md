# Elasticsearch源码阅读

*ES版本：7.14*

## 第1章 启动与关闭

### 1.1 节点启动

#### 1.1.1 启动流程分析

ES节点在启动时主要流程

1. 解析配置
2. 检查环境
3. 初始化内部资源
4. 启动各个模块和keepAlive线程

#### 1.1.2 解析配置

解析配置包括解析命令行参数和外部配置文件（ES配置和日志配置）

JVM参数是在启动脚本中解析的。

#### 1.1.3 检查环境

- ES节点初始化前检查项

  1. **Lucene版本**

     ES各版本对使用Lucene版本有不同的要求

  2. **是否是root用户启动**

     出于安全角度，不允许使用root用户启动ES

  3. **尝试锁定内存空间**

     确保ES进程的内存空间不被其他进程占用

  4. **最大线程数检查**

     ES最少要求能创建2048个线程

  5. **虚拟内存检查**

     ES使用mmap从文件系统映射分片上的索引信息到内存中，内存映射会占用进程中虚拟内存地址空间的一部分，其大小等于要映射的文件的大小。

  6. **最大文件大小检查**

     段文件和事务日志文件存储在本地磁盘中，它们可能会非常大，在有最大文件大小限制的操作系统中，可能会导致写入失败。建议将最大文件的大小设置为无限。

  7. **检查JAR包**

     检查Jar包是否冲突或过时

- ES节点初始化后检查项

  | 顺序 |      检测项      |                             条件                             |      系统      |               说明               |
  | :--: | :--------------: | :----------------------------------------------------------: | :------------: | :------------------------------: |
  |  1   |    JVM堆空间     |                      最小值和最大值一致                      |                |     减少堆大小变化带来的抖动     |
  |  2   |   文件句柄数量   |                   mac为10240，否则为65536                    |                |                                  |
  |  3   |     内存锁定     |                         设置内存锁定                         |                |     防止ES内存被交换到硬盘上     |
  |  4   |    最大线程数    |                           最少4096                           |     Linux      |                                  |
  |  5   | 虚拟内存空间检查 |                                                              | Linux & Mac OS |                                  |
  |  6   | 最大文件大小检查 |                           unlimit                            | Linux & Mac OS |                                  |
  |  7   |  内存映射区数量  |                mmapfs或hybridfs时，最少262144                |     Linux      |                                  |
  |  8   |   JVM模式检查    |                       server模式的JVM                        |                |      client模式的JVM性能低       |
  |  9   |   串行GC收集器   |                         不使用串行GC                         |                |           串行GC效率低           |
  |  10  |  系统调用过滤器  |                                                              |                |               安全               |
  |  11  |   JVM on error   |                       JVM设置相关参数                        |                |                                  |
  |  12  | JVM on OOM error |                       JVM设置相关参数                        |                |                                  |
  |  13  |   JDK版本检查    |                        非预览版本JDK                         |                |      预览版本的JDK性能拉胯       |
  |  14  |   G1GC适配检查   |                      JDK不早于 JDK 8u40                      |                | 早期版本的G1GC会导致索引文件损坏 |
  |  15  | Java安全管理检查 |                      设置Java安全管理器                      |                |                                  |
  |  16  |   集群发现配置   |                       设置至少一个配置                       |                |         防止创建多个集群         |
  |  17  |    x-pack检查    | [官方说明](https://gitee.com/primabrucexu/image/raw/main/pic/2021/09/20210929100600.html) |                |                                  |
  

#### 1.1.4 内部初始化

> 所有初始化的工作都封装在org.elasticsearch.bootstrap.Boostrap#setup

内部初始化的工作主要就是将各个内部模块托管给Guice。Guice是一个类似于Spring的轻量级依赖注入框架

#### 1.1.5 启动

内部模块启动列表

| 序号 |           模块名           |                  说明                  |
| :--: | :------------------------: | :------------------------------------: |
|  1   |       PluginService        |               先启动插件               |
|  2   |       IndicesService       |                索引服务                |
|  3   | IndicesClusterStateService |              索引信息状态              |
|  4   |      SnapshotsService      |                  快照                  |
|  5   |   SnapshotShardsService    |          数据节点快照管理服务          |
|  6   |    RepositoriesService     |    维护和提供对节点上快照仓库的访问    |
|  7   |       SearchService        |                搜索服务                |
|  8   |      FsHealthService       |   定期写一个临时文件检查文件系统状态   |
|  9   |       MonitorService       |                内部监控                |
|  10  |   NodeConnectionsService   | 主要管理当前节点和集群内其余节点的通信 |
|  11  |       GatewayService       |                                        |
|  12  |      TransportService      |              网络传输服务              |
|  13  | PeerRecoverySourceService  |              分片恢复服务              |
|  14  |      GatewayMetaState      |             集群元数据管理             |
|  15  |         Discovery          |              集群发现服务              |
|  16  |       ClusterService       |                集群服务                |
|  17  |    HttpServerTransport     |                http服务                |



### 1.2 集群启动

### 1.3 节点关闭











































---

## 第2章 集群选主（TODO）

----

## 第3章 写入文档（TODO）

---

## 第4章 GET文档（TODO）

---

## 第5章 搜索文档（TODO）

---

## 第6章 持久化和恢复（TODO）

----

## 第7章 Gateway模块（TODO）

---

## 第8章 Allocation模块（TODO）

----

## 第9章 Snapshot模块（TODO）

---

## 第10章 Cluster模块（TODO）

---

## 第11章 Transport模块（TODO）

---

## 第12章 ThreadPool模块（TODO）

---

## 第13章 其他模块（TODO）

---

## 第14章 总结（TODO）

---

