# 		Elasticsearch

## 1. 简介

### 1.1 前世今生

>   前世（简化封装 Lucene ）

2004年有一个以色列小伙子名字叫谢伊·班农（ShayBanon）他成亲不久来到伦敦。因为当时他的夫人正好在伦敦学厨师。初来乍到，也没有找到工作，于是班农就打算写一个叫作iCook的小程序来管理和搜索菜谱。一来练练手方便找工作；二来这个小工具还可以给夫人用。

班农在编写 iCook 的过程中使用了 Lucene ，感受到了直接使用 Lucene 开发程序的各种暴击和痛苦。于是他在 Lucene 之上封装了一个叫作 Compass 的程序框架，与 Hibernate 和 JPA 等 ORM 框架进行集成，通过操作对象的方式来自动调用 Lucene 以构建索引。

这样做的好处是可以很方便地实现对 ”领域对象“ 进行索引的创建，并实现 ”字段级别“ 的检索，以及实现 “全文搜索” 功能。可以 Compass 大大简化了给 Java 程序添加搜索功能的开发。Compass 开源出来，变得很流行。

在 Compass 编写到 2.x 版本的时候，社区面出现了更多需求，比如有处理更多数据的能力以及分布式的设计。班农发现只有重写 Compass ，才更好地实现这些分布式搜索的需求。于是 Compass 3.0 就没有了，取而代之的是一个全新的项目，也就是 Elasticsearch 。

得益于之前 Compass 的技术积累，Elasticsearch 问世之初就考虑到了易用性。

Elasticsearch 作为一个独立的搜索服务器提供了非常方便的搜索功能。用户完全不用关心底层 Lucene 的细节，只需要通过标准的 Http+RESTful 格的 API 就可以行索引数据的增删改查。数据的输入输出用 JSON 格式，以文档和面向对象的方式，样就非常方便地理解和表达领域数据。

简单来说，Elasticsearch 就是一个 Compass 的  RESTful 实现。

同时 Elasticsearch 基于分片和副本的方式实现了一个分布式的 Lucene Directory ，再结合 Map-reduce 的理念实现了一个简单的搜索求分发合并的策略，轻松化解了海量索引和分布式高可用的问题。

>   今生（Elastic Stack，https://www.elastic.co/cn/what-is/elk-stack）

-   Logstash

    Logstash 是一个开源的日志处理工具，用 JRuby 写的。主要特点是基于灵活的 Pipeline 管道架构来处理数据。什么意思呢？可以理解为将数据放进一个管道内进行处理，并且就和真正的自来水管一样，管道由一截一截管子组成。每一个小管代表着一个数据处理的流程，每一个流程只做一件事情，然后可以根据数据的处理需要，选择多个不同类型的管子灵活组装。

    一开始 Elasticsearch 只是作为其中的一个输出存储，主要用于存储日志数据。不过，随着大家的使用发现， Elasticsearch 不仅可以存储大量的数据，水平伸缩也很方便，而且还具有很高的查找效率（全文搜索）。

    全文搜索在日志分析里面是非常基础的一个功能。通过一个关键字就能定位具体的详细日志，相比存放到关系型数据库和普同的文件存储，Elasticsearch 优势非常明显。于是 Logstash 搭配 Elasticsearch 变得很受欢 。

-   Kibana

    由于 Logstash 自带的 UI 查询日志的界面有点简陋，于是有一个叫作 Rashid Khan 的运维工程师表示完全忍不了了，用 PHP 写了一个叫作 Kibana 的程序，一个更好看和更好用的前端界面。PHP 写完一版，他又用 Ruby 写一版，后面又用 AngularJS 写了一版，不仅有日志的搜索和查看，还加上了一些统计展示功能。

    这个时候 Elasticsearch 已经有 Facet 概念，也就是分面统计（1.0 之后推出了 Aggregation 来代替 Facet ），可以对数据中的某个字段进行单个维度的统计，支持多种统计类型。比如，TermFacet 可以计算字段中某些值出现了多少次；Histogram Facet 可以按时区进行汇总统计等。这些统计功能在前端 UI 就可以利用起来，展示一些饼图、时间曲线等等。在运维的分析里面自然也都是需要的。慢慢的，Kibana越做越复杂，支持的功能越来越多。

-   Beats

    Beats 是一系列非常轻量型的单一功能数据采集器，常见的有 Packetbeat 、Metricbeat、Auditbeat、Filebeat

**Elastic Stack 是一套完整的，从全局性能监控到代码级别排障的技术栈**

-   Elastic Stack 完整版图

    ![image-20210702155553692](https://gitee.com/primabrucexu/image/raw/main/20210702155554.png)

### 1.2 应用场景

#### 1.2.1 企业搜索

**简介**

企业搜索就是企业用的搜索服务。既可以是对内提供搜索服务，也可以是对外的搜索服务。对内的话主要搜索项目信息、文档信息、代码信息等；对外基于公司业务而提供搜索

**特点**

-   数据来源不同
-   数据内容不同
-   更新频率不同
-   数据完整行不同
-   搜索结果不同
-   不同用户需求不同

**总结**

企业搜索的业务场景决定了企业搜索的特点和需求， Elastic 在 Elasticsearch 强大功能的基础之上，构建了更加易用的企业搜索解决方案 Elastic Enterprise Search。Elastic Enterprise Search 对企业搜索场景提供了从部署到权限控制、从文档接入到查询优化、从前端 UI 到结果控制的全场景覆盖的支持能力，虽然其相比自己构建一套企业搜索系统的门槛已非常低，易用性也非常好，但毕竟是一套接口完善、功能众多、相对复杂的系统。

#### 1.2.2 可观测性

**简介**

>   ”If you can’t measure it, you can’t manager it”

要良好的管理一个系统，我们需要先做到良好的观测和测量。业界对可测性的定义由Logging（日志），Metrics （指标）和 Tracing （跟踪）组成。通常，需要多个技术栈的建设，才能实现完整的可观测性。而 Elastic Stack 则是一个一站式全栈的可观测性解决方案



### 1.3 重大版本变更

>   参考资料：https://www.cnblogs.com/flyrock/p/11614396.html

#### ***5.x***

-   Lucene 6.x 的支持，磁盘空间少一半；索引时间少一半；查询性能提升25%；支持IPV6。
-   Internal engine级别移除了用于避免同一文档并发更新的竞争锁，带来15%-20%的性能提升
-   **Shrink API** ，它可将分片数进行收缩成它的因数，如之前你是15个分片，你可以收缩成5个或者3个又或者1个，那么我们就可以想象成这样一种场景，在写入压力非常大的收集阶段，设置足够多的索引，充分利用shard的并行写能力，索引写完之后收缩成更少的shard，提高查询性能
-   提供了第一个Java原生的REST客户端SDK
-   IngestNode，之前如果需要对数据进行加工，都是在索引之前进行处理，比如logstash可以对日志进行结构化和转换，现在直接在es就可以处理了
-   提供了 Painless 脚本，代替Groovy脚本
-   移除 site plugins ，就是说 head 、 bigdesk 都不能直接装 es 里面了，不过可以部署独立站点（反正都是静态文件）或开发 kibana 插件
-   新增 Sliced Scroll类型，现在Scroll接口可以并发来进行数据遍历了。每个Scroll请求，可以分成多个Slice请求，可以理解为切片，各Slice独立并行，利用Scroll重建或者遍历要快很多倍。
-   **新增了Profile API**

-   **新增了Rollover API**
-   **新增Reindex**
-   提供了第一个Java原生的REST客户端SDK 基于HTTP协议的客户端对Elasticsearch的依赖解耦，没有jar包冲突，提供了集群节点自动发现、日志处理、节点请求失败自动进行请求轮询，充分发挥Elasticsearch的高可用能力
-   **引入新的字段类型 Text/Keyword 来替换 String**
-   **限制索引请求大小，避免大量并发请求压垮 ES**
-   **限制单个请求的 shards 数量，默认 1000 个**

#### ***6.x***

-   **稀疏性 Doc Values 的支持**
-   **Index sorting，即索引阶段的排序。**
-   **顺序号的支持，每个 es 的操作都有一个顺序编号（类似增量设计）**
-   无缝滚动升级
-   Removal of types，在 6.0 里面，开始不支持一个 index 里面存在多个 type
-   **Index-template inheritance**，索引版本的继承，目前索引模板是所有匹配的都会合并，这样会造成索引模板有一些冲突问题， 6.0 将会只匹配一个，索引创建时也会进行验证
-   **Load aware shard routing**， 基于负载的请求路由，目前的搜索请求是全节点轮询，那么性能最慢的节点往往会造成整体的延迟增加，新的实现方式将基于队列的耗费时间自动调节队列长度，负载高的节点的队列长度将减少，让其他节点分摊更多的压力，搜索和索引都将基于这种机制。
-   已经关闭的索引将也支持 replica 的自动处理，确保数据可靠。

#### ***7.x***

-   集群连接变化：TransportClient被废弃 以至于，es7的java代码，只能使用restclient。然后，个人综合了一下，对于java编程，建议采用 High-level-rest-client 的方式操作ES集群
-   **ES安装包内嵌jdk**，==官方表示未来的es版本将不在支持11一下的JDK==

-   Lucene 9
-   重大改进-**正式废除单个索引下多Type的支持** es6时，官方就提到了es7会删除type，并且es6时已经规定每一个index只能有一个type。在es7中使用默认的_doc作为type，官方说在8.x版本会彻底移除type。 api请求方式也发送变化，如获得某索引的某ID的文档：GET index/_doc/id其中index和id为具体的值
-   7.1开始，Security功能免费使用
-   ECK-ElasticSearch Operator on Kubernetes
-   引入了真正的内存断路器，它可以更精准地检测出无法处理的请求，并防止它们使单个节点不稳定
-   **Zen2 是 Elasticsearch 的全新集群协调层**，提高了可靠性、性能和用户体验，变得更快、更安全，并更易于使用==（更换了选主算法，根本上解决了脑裂问题）==
    -   新功能
        -   New Cluster coordination
        -   Feature - Complete High Level REST Client
        -   Script Score Query
-   性能优化
    -   Weak-AND算法提高查询性能
    -   默认的Primary Shared数从5改为1，避免Over Sharding
    -   更快的前 k 个查询
    -   间隔查询(Intervals queries) 某些搜索用例（例如，法律和专利搜索）引入了查找单词或短语彼此相距一定距离的记录的需要。 Elasticsearch 7.0中的间隔查询引入了一种构建此类查询的全新方式，与之前的方法（跨度查询span queries）相比，使用和定义更加简单。 与跨度查询相比，间隔查询对边缘情况的适应性更强。



---

## 2. 基础入门

### 2.1 重要概念

#### 2.1.1 集群和节点

##### 1）cluster

Elasticsearch集群是由一个或多个节点组成，通过其集群名称来进行唯一标识。节点在搜索到集群之后，通过判断自身的 `cluster.name` 来决定是否加入该集群

##### 2）node

节点就是单个的Elasticsearch实例。在一般情况下，每个节点都在独立的容器中运行。根据节点的作用，node具有如下的角色分配：

-   **master-eligible node**

    -   主节点负责轻量级群集范围的操作，例如创建或删除索引，跟踪哪些节点是群集的一部分，并决定哪些分片分配给哪些节点。 群集健康是具有稳定主节点的重要性。只有具有主节点资格的、不是只能投票的节点才有可能被选举为主节点

    -   专职的主节点

        -   通常来说，master 是保证集群稳定运行的关键。如果 master 身兼数职的话，就可能导致无法及时的管理集群，从而导致集群无法正常运行。这种情况在集群规模较小的时候极少发生，一旦集群规模扩大，一些专职的 master 就显得十分必要。

        -   这么设置可以创建一个专职的主节点

            ~~~yaml
            node.roles: [ master ]
            ~~~

    -   只能投票的主节点

        -   该类型的节点只能投票，且无法被选举成为主节点。（注意，只有具有master角色的节点才能被设置为`voting_only`）

        -   这种角色通常被用于哪些你不想让他成为主节点的节点，这些节点可能承担了很多职责，从而不适合成为主节点。但为了有足够的主节点参与投票，所以不得不出此下策。这是历史遗留的问题，未来能不能修复我们不得而知

            ~~~yaml
            node.roles: [ data, master, voting_only ]
            ~~~
        
    -   一个节点能成功当选master需要的票数为 `N/2 + 1`，其中 `N` 是具有主节点资格的节点数
    
        -   在ES7之前的版本中，这项配置是避免集群脑裂的关键，但这往往难以配置，因为往集群中加入一个新节点这个值可能就会发生变化。
        -   在ES7，我们就不需要这个配置，后面会详细描述



-   **data node**

    -   数据节点保存着分片。其主要职责是和数据相关的操作，如CRUD、搜索、聚合等。因此数据节点对IO、内存、CPU等硬件资源要求较高。使用专职的数据节点有助于把管理和数据存储分隔开，能更充分的利用系统资源

        ~~~yaml
        node.roles: [ data ]
        ~~~

    -   数据节点还可以根据数据的使用频率划分为 `hot data node`、 `warm data node`、`cold data node`三种，数据的使用更新频率依次降低，对硬件资源的需求也逐渐降低

        ~~~yaml
        node.roles: [ data_hot/data_warm/data_cold ]
        ~~~

        

-   **ingest node**

    -   ingest node 是用于数据的预处理操作，是预处理程序的组成部分之一

        ~~~yaml
        node.roles: [ ingest ]
        ~~~

        

-   **coordinating node**

    -   一些类似于搜索操作、批量索引操作等请求中可能会涉及到多个数据节点。协调节点的作用就是将这些跨节点的请求分发到具体的、正确的数据节点上。数据节点本地执行请求之后，再将操作结果返回给协调节点，由协调节点完成结果的组装输出。

    -   在默认情况下， 每个节点都具有协调这个角色。但在规模较大的集群中，拥有专职的协调节点是非常有必要的，因为分发请求和生成最终的结果需要较多的内存空间和cpu资源。我们可以通过让节点不具有任何角色来充当专职的协调节点

        ~~~yaml
        node.roles: []
        ~~~



#### 2.1.2 文档和索引

##### 1）文档

​	文档是ES搜索的最小数据单元。简单说，文档相当于关系型数据库中的行。但他有具有一些关系型数据库所没有的特点

-   文档是独立的
-   文档中还可以包含文档，可以套娃
-   文档的结构非常灵活。不同于关系型数据库，无需实现定义文档的结构，可以随用随加

##### 2）索引

在ES中，索引是文档的集合，每个索引由一个或多个文档组成，并且这些文档可以分布在不同的分片之中。这类似于关系型数据库中的 database 。它只是一种逻辑命名空间，内部包含的是 `1~N` 个主分片，`0~N` 个副本



#### 2.1.3 分片

一个索引可以存储海量的数据。比如一个具有10 亿文档的索引占据1 TB 的磁盘空间，而任一节点都没有这么大的磁盘空间或单个节点处理响应太慢。

为了解决这个问题，ES选择将索引划分为多分，这些划分出来的部分就叫分片（shard）。ES会自动管理这些分片的分布和排列，我们不用操心

有了分片，我们可以随意扩展数据容量，可以在多个节点上进行并行操作，进一步提高了吞吐量

##### 1）Primary shard

​	索引可以划分为一个或多个主分片。一旦主分片的数量被确定之后，就不能再修改。这是因为数据被索引在主分片上。如果要变更主分片的数量，那么在需要重新索引数据，否则可能导致查询失败

##### 2）Replica shard

副本就是主分片的备份，其数量可以随意更改而不影响数据的保存。使用副本有两个目的：

-   冗余主分片，在主分片出现故障的时候顶上
-   提供并发操作。副本分片也可以执行读操作，这样可以大大的提高系统的吞吐量

==ES永远不会在主分片所在节点上启动副本==

##### 3）分片健康状态

分片健康状态就是分片的分配状态，可以间接指示集群当前工作状态

-   红色：至少存在一个主分片未分配
-   黄色：主分片均已分配，但至少一个副本为分配
-   绿色：everything is good

#### 2.1.4 搜索原理

ES是通过封装Lucene来实现其搜索功能。Lucene采用一种叫倒排索引的技术来实现快速搜索。

##### 1）倒排索引

假设我们有两个文档，每个文档的正文字段如下：

-   The quick brown fox jumped over the lazy dog

-   Quick brown foxes leap over lazy dogs in summer


那么倒排索引之后的结果如下：

![image-20210705153127300](https://gitee.com/primabrucexu/image/raw/main/20210705153127.png)

现在我们要搜索 Quick brown只需要根据倒排索引之后的结果反向去查找文档即可：

![image-20210705153240715](https://gitee.com/primabrucexu/image/raw/main/20210705153240.png)

两个文档都匹配，如果我们仅从匹配数量来判断相关性来说，文档1比文档2更符合我们预期的搜索结果

##### 2）分析

目前来说，我们的倒排索引存在着一些问题，如果解决了这些问题的话，我们的搜索结果会更准确

-   `Quick` 和 `quick` 以独立的词条出现，然而用户可能认为它们是相同的词。

-   `fox` 和 `foxes` 非常相似, 就像 `dog` 和 `dogs` ；他们有相同的词根。

-   `jumped` 和 `leap`, 尽管没有相同的词根，但他们的意思很相近。他们是同义词


如果我们将词条规范为标准模式，那么我们可以找到与用户搜索的词条不完全一致，但具有足够相关性的文档。例如：

-   `Quick` 可以小写化为 `quick` 。
-   `foxes` 可以 *词干提取* --变为词根的格式-- 为 `fox` 。类似的， `dogs` 可以为提取为 `dog` 。
-   `jumped` 和 `leap` 是同义词，可以索引为相同的单词 `jump。`

现在，我们的倒排索引像这样

![image-20210705155030806](https://gitee.com/primabrucexu/image/raw/main/20210705155030.png)

到这一步，已经算是完成了50%，因为我们格式化了源数据，还需要对我们的查询输入做同样的操作，这样，才能完美的查询



### 2.2 常用API

#### 2.2.1 信息API

| 请求                   | 作用                                          |
| ---------------------- | --------------------------------------------- |
| GET /_cat/aliases      | 获取别名信息                                  |
| GET /_cat/indices      | 获取索引信息                                  |
| GET /_cat/shards       | 获取索引的分片信息                            |
| GET /_cat/nodes        | 获取节点的基本信息                            |
| GET /_cluster/settings | 获取集群的设置信息，只展示通过API修改过的设置 |
| GET /_cluster/health   | 获取集群的健康状态                            |
| GET /_cluster/state    | 获取集群的详细信息                            |
| GET /_cluster/stats    | 获取集群的简略状态信息                        |
| GET /_nodes            | 获取节点的信息                                |
| GET /_nodes/stats      | 获取节点的信息                                |



#### 2.2.2 CRUD

##### 1）操作文档

-   `GET <index>/_doc/<_id>`，`GET <index>/_source/<_id>`
    -   获取制定的文档ID的文档，使用 `_doc` 获得的是全部文档内容，`_source` 值获取正文内容

-   `POST <index>/_doc/<_id>`
    -   创建一个具有给定ID的文档，如果没有给出ID，ES会自动分配一个

- `DELETE /<index>/_doc/<_id>`
    - 删除给出id的文档

- `POST /<index>/_delete_by_query`
    - 根据条件删除文档，请求体如下：

    ~~~json
    {
      "query": {
        "match": {
          
        }
      }
    }
    ~~~

- `POST /<index>/_update/<_id>`
    - 更新制定文档，请求体中为需要更新的内容

- `POST /_bulk`，`POST /<index>/_bulk`

  - 批量操作，请求体格式如下
  
    ~~~json
    { "index" : { "_index" : "test", "_id" : "1" } }	// 创建索引，下一行是需要创建索引的数据
    { "field1" : "value1" }
    { "delete" : { "_index" : "test", "_id" : "2" } }	// 删除文档
    { "create" : { "_index" : "test", "_id" : "3" } } 	// 创建文档，下一行是文档的数据
    { "field1" : "value3" }
    { "update" : {"_id" : "1", "_index" : "test"} }		// 更新文档，下一行是数据
    { "doc" : {"field2" : "value2"} }
    ~~~
  
-   `POST _reindex`

    -   重构索引，请求体如下

        ~~~json
        {
          "source": {
            "index": "my-index-000001"
          },
          "dest": {
            "index": "my-new-index-000001"
          }
        }
        ~~~

        

##### 2）操作索引

-   创建索引，如果索引已存在的话则更新

    ~~~json
    PUT /<index>
    {
      "settings": {
        "index": {
          "number_of_shards": 3,  
          "number_of_replicas": 2 
        }
      },
      "mappings": {
        "properties": {
          "field1": { "type": "text" }
        }
      }
    }
    ~~~

-   删除索引：`DELETE /<index>`， 支持通配符

-   获取索引信息：`GET /<index>`，支持通配符

-   判断索引是否存在：`HEAD /<index>`，支持通配符

-   获取索引映射：`GET /<target>/_mapping`

-   更新索引映射

    ~~~json
    PUT /<index>/_mapping
    {
      "properties": {
        "field": {
          "type": "keyword"
        }
      }
    }
    ~~~

-   更新或创建别名：`PUT /<index>/_alias/<alias>`

-   查看别名：`GET /<index>/_alias`，`GET /_alias`

-   删除别名：`DELETE /<index>/_aliases/<alias>`

-   更新索引设置：`PUT /<index>/_settings`，请求体为详细索引设置
-   查看所有设置：`GET /<index>/_settings`



#### 2.2.3 搜索API

- 常规搜索：`POST /<index>/_search`，请求体如下

  ~~~json
  // 简单搜索
  {
    "query": {
      "term": {
        "user.id": "kimchy"
      }
    }，
    "explain": true, // 在结果中返回有关分数计算的详细信息
    ""
    "scroll" : "1m",	// 滚动搜索数据量，以及上一次滚动搜索的结果
    "scroll_id" : "DXF1ZXJ5QW5kRmV0Y2gBAAAAAAAAAD4WYm9laVYtZndUQlNsdDcwakFMNjU
  1QQ=="
  }
  
  // 复杂查询
  {
    "query": {
      "bool": {
        "must":     { "match": { "title": "quick" }},
        "must_not": { "match": { "title": "lazy"  }},
        "should": [
                    { "match": { "title": "brown" }},
                    { "match": { "title": "dog"   }}
        ]
      }
    }
  }
  ~~~

### 2.3 配置Elasticsearch

#### 2.3.1 ES配置文件

~~~yaml
cluster.name: my-application	# 集群名称，

node.name: node-1	# 节点名称
node.roles: [master, data, ingest, data_cold, data_warm, data_hot]	# 节点角色配置

path.data: /path/to/data	# 数据存储路径
path.logs: /path/to/logs	# 日志存储位置

bootstrap.memory_lock: true

network.host: 192.168.0.1	# 节点ip
http.port: 9200	# 节点对外访问端口
transport.port: 9300	# 节点通信端口

discovery.zen.minimum_master_nodes: 2	# 最小主节点数，ES7之前的版本必须合理配置，不然可能导致脑裂问题；ES7之后该配置项失效（无论配多少都不会生效）

# ES7之后的配置，initial_master_nodes的值要和node.name完全一致。在集群启动的时候，要有半数以上的initial_master_nodes存活才可以成功建立集群。
discovery.seed_hosts: ["host1", "ip1", "ip2:port2"]	
cluster.initial_master_nodes: ["node-1", "node-2"]
~~~

#### 2.3.2 重要的系统配置

##### 1）禁用内存交换

大多数操作系统会尽可能将未使用的内存空间来作为文件系统的缓存，同时交换应用程序为使用的空间。内存交换会导致ES节点不稳定，因为这会影响GC的工作效率，从而导致节点无法响应

- 操作系统层面

  ~~~bash
  sudo swapoff -a
  ~~~

  - 这会暂时禁用操作系统的内存交换。如果需要永久禁用，则需要修改`/etc/fstab`文件

- 应用层面

  ~~~yaml
  bootstrap.memory_lock: true
  ~~~

  - 这个选项会让ES在启动的时候就锁定分片的内存空间，不过要注意不能锁定多于当前可用内存。

  - 这个选项出于某些原因，仍有可能失效，大多数情况下是ES没有权限锁定内存，需要编辑 `/etc/security/limits.conf`文件

    ~~~bash
    # allow user 'elasticsearch' mlockall
    elasticsearch soft memlock unlimited
    elasticsearch hard memlock unlimited
    ~~~

##### 2）增加文件句柄

​	ES在运行的使用会使用大量的文件句柄，如果没有足够的文件句柄，则可能会造成数据丢失。ES最少需要65535个文件句柄

- 系统命令

  ~~~bash
  ulimit -n 65535
  ~~~

- 系统配置文件

  ~~~bash
  # elasticsearch用户最多可以使用65535个文件
  elasticsearch  -  nofile  65535
  ~~~

##### 3）确保能创建足够的线程

​	ES在不同操作的情况下，使用不同大小的线程池。要确保ES在需要的时候能创建足够多的线程，ES最少需要能创建4096个线程

- 系统命令

  ~~~bash
  ulimit -n 4096
  ~~~

- 系统配置文件

  ~~~bash
  # elasticsearch用户最多可以使用4096个线程
  elasticsearch soft nproc 4096
  elasticsearch hard nproc 4096
  ~~~

#### 2.3.3 Heap大小设置

默认情况下，ES会自动根据节点的角色和总内存空间来自动设置堆空间（至少需要JDK14），但手动更改JVM堆大小也是十分常见的操作，修改 `jvm.options` 即可，==但最大不要超过32GB==

如果服务器内存空间非常大的话，可以考虑运行多个ES实例而不是使用大内存空间

在单独部署的情况下，建议给ES分配的堆空间为总内存空间的50%，剩余的内存空间留给Lucene实例

> - ==为什么不建议给ES分配过多的内存空间？==
>
>   ES使用的JVM会使用一种叫压缩对象指针的技术。其通过使用偏移量来表示内存空间地址，从而减少了指针的大小，节约了内存空间。
>
> - 关于压缩指针，可以参考这篇文章：https://blog.csdn.net/liujianyangbj/article/details/108049482



### 2.4 源码环境构建

>   编译和加载过程中可能会出现JDK版本不匹配的问题，手动替换JDK版本即可

-   从GitHub上获取ES源码：https://github.com/elastic/elasticsearch，同时准备一个对应发行版的ES

-   用IDEA打开ES源码，在 `Preferences -> Build -> Build Tools -> Gradle` 中设置好grade要使用的JDK，在 `File -> Project Structure -> Project Settings -> Project -> Project SDK` 中设置JDK版本

-   idea会自动导入gradle项目，期间会联网下载gradle。如果不想等待，可以在 `项目文件夹/gradle/wrapper/gradle-wrapper.properties` 中手动设置gradle的位置，从而跳过下载

    ![image-20210702183926159](https://gitee.com/primabrucexu/image/raw/main/20210702183926.png)
    -   手动设置的话可能会出现sha256校验失败等问题，注释相关配置即可

-   等待gradle构建完成

-   直接运行主启动类：`server/src/main/java/org.elasticsearch.bootstrap.Elasticsearch` 

-   常见错误

    1. *`the system property [es.path.conf] must be set`*
    - 设置jvm，`-Des.path.conf`，value是发行版es的配置目录，使用默认配置即可
    2. `*path.home is not configured*`
    - 设置jvm，`-Des.path.home`，value是发行版es的根目录
    3. *`ERROR Could not register mbeans java.security.AccessControlException: access denied ("javax.management.MBeanTrustPermission" "register")`*
    - 设置jvm，`-Dlog4j2.disable.jmx=true`
    4. *`Unknown codebases [codebase.elasticsearch-plugin-classloader, codebase.elasticsearch, codebase.elasticsearch-secure-sm]`*
    - 注释掉相应的检查报错异常，需要相应的jar包，而ide生成的class文件，所以检测不到
    5. *`Plugin [xxx] was built for Elasticsearch version xxx but version xxx is running`*
       - 插件版本不对应，注释掉相应检查逻辑即可

-   启动成功后访问：http://localhost:9200/测试


---

## 3. 深入探究

### 3.1 分片

#### 3.1.1 文档路由到分片

ES为了实现分布式的搜索，会将索引划分为数个主分片和副本。那么，当我们查询一个文档时，ES怎么知道这个文档存储在哪个分片上呢？当我们存储一个分片时，ES又该如何安排它存储在那个分片上呢？

实际上，这个过程是根据这个公式来决定的：

​	`shard = hash(routing) % number_of_primary_shards`

- `routing` 是一个变量，可以由用户给定，也可以由系统决定，它默认是文档的id。
- `routing` 通过哈希函数经过计算之后，再用除以主分片数得到的余数来确定该文档所属的分片位置

这也就解释了为什么要在创建索引的时候为什么就要明确主分片的数量且不能再改变。因为如果主分片数量改变之后，就有可能导致数据丢失。

#### 3.1.2 分片数量

既然主分片数量在确定之后，那么是不是应该在创建的时候设置越多的主分片数越好？

显然不是！这里我们要先建立一个概念：==每个分片都是一个完整的Lucene实例==

这意味着创建和使用分片是有代价的，每个分片都或多或少的占用系统资源。同时，每次搜索请求都会要求所有的分片返回搜索结果，如果这些分片都集中在同一台服务器上，这可能引起资源竞争从而使得无法正常响应。

> 分片的数量和大小控制的一些经验之谈

- 可以手动合并较小的分片来减轻系统负担。通常情况下，分片合理的大小在几GB~几十GB之间，但最大不要超过50GB
- ES节点所持有的分片数应当和其堆空间大小成正比，这个比例一般要低于20~25个分片每GB
- 虽然ES可以并行的在多个分片内执行查询操作，但查询大量小分片不一定比查询几个大分片快

#### 3.1.3 快速搜索

分片能进行搜索的原理是使用了倒排索引，那么它是怎么实现快速搜索的呢

##### 1）倒排索引的不可变性

==倒排索引在被写入磁盘后，是永远不可变的==，也就是它永远不能被修改。这种特性具有如下价值：

- 不需要锁。如果你从来不更新索引，你就不需要担心多进程同时修改数据的问题。
- 一旦索引被读入内核的文件系统缓存，便会留在哪里，由于其不变性。只要文件系统缓存中还有足够的空间，那么大部分读请求会直接请求内存，而不会命中磁盘。这提供了很大的性能提升。
- 其它缓存(像filter缓存)，在索引的生命周期内始终有效。它们不需要在每次数据改变时被重建，因为数据不会变化。
- 写入单个大的倒排索引允许数据被压缩，减少磁盘 I/O 和需要被缓存到内存的索引的使用量。

但这种不变性也会带来一些麻烦：如果你需要让一个新的文档 可被搜索，你需要重建整个索引。这要么对一个索引所能包含的数据量造成了很大的限制，要么对索引可被更新的频率造成了很大的限制。

##### 2）并行搜索

并行搜索指的是ES会在主分片及其所有副本分片上进行并行搜索，以加快搜索速度。这里不再赘述

#### 3.1.4 分片的细节

既然倒排索引都是不可变的，那么当有更多的文档被创建之后该怎么办呢？难道说每创建一个文档都要重新创建一次倒排索引吗？

答案是：创建更多的倒排索引，用额外的倒排索引来记录新增加的文档记录。在搜索的时候，每个倒排索引都会根据时间顺序来进行检索，最后再将结果合并。

ES是基于Lucene来实现搜索功能，Lucene中提出了**按段搜索**的概念，每一段其实就是一个倒排索引。多个段和提交点就构成了Lucene中可进行搜索的**索引**，也就是ES中的分片。

##### 1）索引的更新

1. 新文档被收集到内存索引缓存中包含新文档的 Lucene 索引

2. 不时地, 缓存被 *提交* ：

   - 一个新的**段**，一个追加的倒排索引—被写入磁盘。
   - 一个新的包含新段名字的 **提交点** 被写入磁盘。
   - 磁盘进行 **同步**，所有在文件系统缓存中等待的写入都刷新到磁盘，以确保它们被写入物理文件。

   ![A Lucene index with new documents in the in-memory buffer, ready to commit](https://gitee.com/primabrucexu/image/raw/main/20210706180055.png)

3. 新的段被开启，让它包含的文档可见以被搜索。**由于物理写入磁盘开销大，所以在实际运行的过程中，只要数据在缓存中，就可以被外部所读取访问到了。因为Lucene 允许新段被写入和打开—使其包含的文档在未进行一次完整提交时便对搜索可见。 这种方式比进行一次提交代价要小得多，并且在不影响性能的前提下可以被频繁地执行。**

4. 内存缓存被清空，等待接收新的文档。

   ![After a commit, a new segment is added to the index and the buffer is cleared](https://gitee.com/primabrucexu/image/raw/main/20210706180107.png)

5. 当一个查询被触发，所有已知的段按顺序被查询。词项统计会对所有段的结果进行聚合，以保证每个词和每个文档的关联都被准确计算。 这种方式可以用相对较低的成本将新文档添加到索引。



##### 2）持久化问题

​		由于这些数据在文件系统的缓存中，如果没有用 `fsync` 把数据从文件系统缓存刷（flush）到硬盘，我们不能保证数据在断电甚至是程序正常退出之后依然存在。为了保证 Elasticsearch 的可靠性，需要确保数据变化被持久化到磁盘。

​		Elasticsearch为了保证数据的完整性，增加了一个叫 `translog`（事务日志）的东西来协助保存数据。所以总的工作流程如下

1. 一个文档被索引之后，就会被添加到内存缓冲区，同时追加到了 translog

   ![New documents are added to the in-memory buffer and appended to the transaction log](https://gitee.com/primabrucexu/image/raw/main/20210706181147.png)

2. 刷新（refresh）使分片处于提交的状态，分片每秒被刷新（refresh）一次：

   - 这些在内存缓冲区的文档被写入到一个新的段中，且没有进行 `fsync` 操作。
   - 这个段被打开，使其可被搜索。
   - 内存缓冲区被清空。

   ![After a refresh, the buffer is cleared but the transaction log is not](https://gitee.com/primabrucexu/image/raw/main/20210706181231.png)

3. 这个进程继续工作，更多的文档被添加到内存缓冲区和追加到事务日志

   ![The transaction log keeps accumulating documents](https://gitee.com/primabrucexu/image/raw/main/20210706181257.png)

4. 每隔一段时间，translog 变得越来越大，索引被刷新（flush）；一个新的 translog 被创建，并且一个全量提交被执行：

   - 所有在内存缓冲区的文档都被写入一个新的段。
   - 缓冲区被清空。
   - 一个提交点被写入硬盘。
   - 文件系统缓存通过 `fsync` 被刷新（flush）。
   - 老的 translog 被删除。

![After a flush, the segments are fully commited and the transaction log is cleared](https://gitee.com/primabrucexu/image/raw/main/20210706181352.png)

​		**translog 提供所有还没有被刷到磁盘的操作的一个持久化纪录。当 Elasticsearch 启动的时候， 它会从磁盘中使用最后一个提交点去恢复已知的段，并且会重放 translog 中所有在最后一次提交后发生的变更操作。**

​		**translog 也被用来提供实时 CRUD 。当你试着通过ID查询、更新、删除一个文档，它会在尝试从相应的段中检索之前， 首先检查 translog 任何最近的变更。这意味着它总是能够实时地获取到文档的最新版本。**

> ***translog的安全性***
>
> 在文件被 `fsync` 到磁盘前，被写入的文件在重启之后就会丢失。默认 translog 是每 5 秒被 `fsync` 刷新到硬盘， 或者在每次写请求完成之后执行(e.g. index, delete, update, bulk)。这个过程在主分片和复制分片都会发生。最终， 基本上，这意味着在整个请求被 `fsync` 到主分片和复制分片的translog之前，你的客户端不会得到一个 200 OK 响应。
>
> 在每次请求后都执行一个 fsync 会带来一些性能损失，尽管实践表明这种损失相对较小（特别是bulk导入，它在一次请求中平摊了大量文档的开销）。
>
> 但是对于一些大容量的偶尔丢失几秒数据问题也并不严重的集群，使用异步的 fsync 还是比较有益的。比如，写入的数据被缓存到内存中，再每5秒执行一次 `fsync` 。
>
> 这个行为可以通过设置 `durability` 参数为 `async` 来启用：
>
> ```js
> PUT /my_index/_settings
> {
>     "index.translog.durability": "async",
>     "index.translog.sync_interval": "5s"
> }
> ```
>
> 这个选项可以针对索引单独设置，并且可以动态进行修改。如果你决定使用异步 translog 的话，你需要 *保证* 在发生crash时，丢失掉 `sync_interval` 时间段的数据也无所谓。请在决定前知晓这个特性。
>
> 如果你不确定这个行为的后果，最好是使用默认的参数（ `"index.translog.durability": "request"` ）来避免数据丢失。



##### 3）段合并

由于自动刷新流程每秒会创建一个新的段 ，这样会导致短时间内的段数量暴增。而段数目太多会带来较大的麻烦。 每一个段都会消耗文件句柄、内存和cpu运行周期。更重要的是，每个搜索请求都必须轮流检查每个段；所以段越多，搜索也就越慢。

Elasticsearch通过在后台进行段合并来解决这个问题。小的段被合并到大的段，然后这些大的段再被合并到更大的段。在合并的时候，Elasticsearch会将哪些被标记为删除的文档从文件系统中删除，更新前的旧文档也不会被拷贝到新的段中

==段合并是在索引和搜索时自动进行的操作==，这意味着小段的合并可能不会对ES运行带来较大的影响，但大段的合并需要消耗大量的I/O和CPU资源，如果任其发展会影响搜索性能。所以ES在默认情况下会限制段合并流程的资源，用于保证搜索服务不受太大影响。

> `optimize`，手动段合并API
>
> ```json
> POST /<index>/_optimize?max_num_segments=1 
> ```
>
> - 注意：通过`optimize`进行段合并时，ES不会对其资源进行限制，这可能会消耗掉你节点上全部的I/O资源, 使其没有余裕来处理搜索请求，从而有可能使集群失去响应。 
> - 注意：`optimize` API 不应该 被用在一个活跃的索引，一个正积极更新的索引。后台合并流程已经可以很好地完成工作。



### 3.2 master选举机制

#### 3.2.1 选举算法

##### 1）bully算法

**核心思想**

- 假定所有的节点都具有一个可以比较的ID，通过比较这个ID来选举master

**流程说明**

1. 节点向所有比自己ID大的节点发送选举信息（election），告诉他们我选你
2. 如果收到了回复消息（alive），这说明有人比自己“资历”更老，要让他去做老大，他只能乖乖等着老大选举
   1. 等待老大成功选举的消息（victory）
   2. 如果超时之后还没有成功选举消息，那么重新发送选举信息
3. 如果没有收到任何回复消息（alive），那么就自己当老大，同时向其他节点发送当选信息（victory）

**示例**

1. 首先，我们有6个节点的集群，所有节点都互联，P6是master

   ![img](https://gitee.com/primabrucexu/image/raw/main/20210707102315.gif)

2. P6挂了

   ![img](https://gitee.com/primabrucexu/image/raw/main/20210707102414.gif)

3. P3发现P6挂了，于是向所有比自己ID大的节点发送选举消息（election）

   - 要给P6发的原因是P6有可能恢复了，所以P6也要发

   ![img](https://gitee.com/primabrucexu/image/raw/main/20210707102532.gif)

4. P4和P5都收到了消息，并表示他们会接手，你就不用管了（bully P3）

   ![img](https://gitee.com/primabrucexu/image/raw/main/20210707103023.gif)

5. P4开始接管选主流程，它开始向P5和P6发送选举信息

   ![img](https://gitee.com/primabrucexu/image/raw/main/20210707103744.gif)

6. 只有P5响应了，P5从这里开始接管选举（bully p4）

   ![img](https://gitee.com/primabrucexu/image/raw/main/20210707103827.gif)

7. P5发送选举信息

   ![img](https://gitee.com/primabrucexu/image/raw/main/20210707103929.gif)

8. 没有人能响应P5的选举信息，于是P5当选master，同时告诉别人他是master

   ![img](https://gitee.com/primabrucexu/image/raw/main/20210707104004.gif)

**优缺点**

- 优点
  - 简单粗暴，只要我比你大，我就来组织选举
- 缺点
  - master假死会使得集群状态不稳定。假定P6在P5发布当选信息后重新上线，P5检测到P6的话，又会重新开启选举，因为P6的id比P5大
  - 脑裂问题，当出现网络分区的时候，一个集群可能会选举出两个master（因为网络通信受限）



##### 2）raft算法

raft算法首先将系统中角色定义为三种：leader、follower、candidate。同时将系统一致性拆分为Leader选举（Leader election）、日志同步（Log replication）、安全性（Safety）、日志压缩（Log compaction）、成员变更（Membership change）等多个子问题。这里，我们只讨论Leader election

**核心思想**

- 每个leader都有一个任期（term），在它的任期内，他是老大；只要发现有人的任期比自己大，他就会无条件的加入

**选主流程**

1. follower在一段时间内没有收到leader发送来的确认信息之后会转变为candidate
2. candidate等待投票请求
   - 收到投票请求，投票，然后等待选举结果
   - 超时，给自己投票，发送投票请求
3. 收到足够投票请求后，成功当选leader，开始维护集群

<img src="/Users/bruce/Desktop/20210707115050.png" alt="raft选举流程" style="zoom:50%;" />

**参考资料**

-   [一文搞懂Raft算法](https://www.cnblogs.com/xybaby/p/10124083.html)
-   [raft算法动画演示](http://thesecretlivesofdata.com/raft/)
-   [raft论文](https://web.stanford.edu/~ouster/cgi-bin/papers/raft-atc14)

#### 3.2.2 选举实现

##### 1）es6.8

- 逻辑流程图

  <img src="https://gitee.com/primabrucexu/image/raw/main/20210707171647.png" alt="es6.8 选主流程" style="zoom:50%;" />

- 源代码

  - 集群初始化
  
    ~~~java
    /**
     * the main function of a join thread. This function is guaranteed to join the cluster
     * or spawn a new join thread upon failure to do so.
     */
    private void innerJoinCluster() {
        DiscoveryNode masterNode = null;
        final Thread currentThread = Thread.currentThread();
        nodeJoinController.startElectionContext();
        while (masterNode == null && joinThreadControl.joinThreadActive(currentThread)) {
            masterNode = findMaster();
        }
    
        if (joinThreadControl.joinThreadActive(currentThread) == false) {
            logger.trace("thread is no longer in currentJoinThread. Stopping.");
            return;
        }
    
        // 如果当前节点是被选出来的master，那么他就成功当选master，开始接受其他节点的连接请求
        // 如果没有成功当选master，那么就去加入master
        // 这里也解释了为什么在判断存活master的时候不能把自己算进去。因为把自己算进去的话，所有节点都会认为自己是master，
        if (transportService.getLocalNode().equals(masterNode)) {
            final int requiredJoins = Math.max(0, electMaster.minimumMasterNodes() - 1); // we count as one
            logger.debug("elected as master, waiting for incoming joins ([{}] needed)", requiredJoins);
            nodeJoinController.waitToBeElectedAsMaster(requiredJoins, masterElectionWaitForJoinsTimeout,
                    new NodeJoinController.ElectionCallback() {
                        @Override
                        public void onElectedAsMaster(ClusterState state) {
                            synchronized (stateMutex) {
                                joinThreadControl.markThreadAsDone(currentThread);
                            }
                        }
    
                        @Override
                        public void onFailure(Throwable t) {
                            logger.trace("failed while waiting for nodes to join, rejoining", t);
                            synchronized (stateMutex) {
                                joinThreadControl.markThreadAsDoneAndStartNew(currentThread);
                            }
                        }
                    }
    
            );
        } else {
            // process any incoming joins (they will fail because we are not the master)
            nodeJoinController.stopElectionContext(masterNode + " elected");
    
            // send join request
            final boolean success = joinElectedMaster(masterNode);
    
            synchronized (stateMutex) {
                if (success) {
                    DiscoveryNode currentMasterNode = this.clusterState().getNodes().getMasterNode();
                    if (currentMasterNode == null) {
                        // Post 1.3.0, the master should publish a new cluster state before acking our join request. we now should have
                        // a valid master.
                        logger.debug("no master node is set, despite of join request completing. retrying pings.");
                        joinThreadControl.markThreadAsDoneAndStartNew(currentThread);
                    } else if (currentMasterNode.equals(masterNode) == false) {
                        // update cluster state
                        joinThreadControl.stopRunningThreadAndRejoin("master_switched_while_finalizing_join");
                    }
    
                    joinThreadControl.markThreadAsDone(currentThread);
                } else {
                    // failed to join. Try again...
                    joinThreadControl.markThreadAsDoneAndStartNew(currentThread);
                }
            }
        }
    }
    ~~~
  
  - 选主逻辑
  
    ~~~java
    private DiscoveryNode findMaster() {
        logger.trace("starting to ping");
        List<ZenPing.PingResponse> fullPingResponses = pingAndWait(pingTimeout).toList();
        if (fullPingResponses == null) {
            logger.trace("No full ping responses");
            return null;
        }
        if (logger.isTraceEnabled()) {
            StringBuilder sb = new StringBuilder();
            if (fullPingResponses.size() == 0) {
                sb.append(" {none}");
            } else {
                for (ZenPing.PingResponse pingResponse : fullPingResponses) {
                    sb.append("\n\t--> ").append(pingResponse);
                }
            }
            logger.trace("full ping responses:{}", sb);
        }
    
        final DiscoveryNode localNode = transportService.getLocalNode();
    
        // add our selves
        assert fullPingResponses.stream().map(ZenPing.PingResponse::node)
            .filter(n -> n.equals(localNode)).findAny().isPresent() == false;
    
        fullPingResponses.add(new ZenPing.PingResponse(localNode, null, this.clusterState()));
    
        // filter responses
        final List<ZenPing.PingResponse> pingResponses = filterPingResponses(fullPingResponses, masterElectionIgnoreNonMasters, logger);
    
        List<DiscoveryNode> activeMasters = new ArrayList<>();
        for (ZenPing.PingResponse pingResponse : pingResponses) {
            // We can't include the local node in pingMasters list, otherwise we may up electing ourselves without
            // any check / verifications from other nodes in ZenDiscover#innerJoinCluster()
            if (pingResponse.master() != null && localNode.equals(pingResponse.master()) == false) {
                activeMasters.add(pingResponse.master());
            }
        }
    
        // nodes discovered during pinging
        List<ElectMasterService.MasterCandidate> masterCandidates = new ArrayList<>();
        for (ZenPing.PingResponse pingResponse : pingResponses) {
            if (pingResponse.node().isMasterNode()) {
                masterCandidates.add(new ElectMasterService.MasterCandidate(pingResponse.node(), pingResponse.getClusterStateVersion()));
            }
        }
    
        // activeMasters为空的时候有两种情况：1.当前节点能看到的所有节点都选出了一个共同的master，且那个节点就是本地节点；2.没有master
        // 1 --> 需要发布选主信息，告诉别人，master是谁
        // 2 --> 既然大家都没有master，那么就来尝试选举master
        // activeMasters不为空时，表示其他节点已经选出了一个master，当前节点要做的事情就是加入这个master
        if (activeMasters.isEmpty()) {
            if (electMaster.hasEnoughCandidates(masterCandidates)) {
                final ElectMasterService.MasterCandidate winner = electMaster.electMaster(masterCandidates);
                logger.trace("candidate {} won election", winner);
                return winner.getNode();
            } else {
                // if we don't have enough master nodes, we bail, because there are not enough master to elect from
                logger.warn("not enough master nodes discovered during pinging (found [{}], but needed [{}]), pinging again",
                            masterCandidates, electMaster.minimumMasterNodes());
                return null;
            }
        } else {
            assert activeMasters.contains(localNode) == false :
                "local node should never be elected as master when other nodes indicate an active master";
            // lets tie break between discovered nodes
            return electMaster.tieBreakActiveMasters(activeMasters);
        }
    }
    ~~~
  
  - 法定人数判断逻辑
  
    ~~~java
    // 变量 minimumMasterNodes 就是配置项 discovery.zen.minimum_master_nodes
    public boolean hasEnoughCandidates(Collection<MasterCandidate> candidates) {
        if (candidates.isEmpty()) {
            return false;
        }
        if (minimumMasterNodes < 1) {
            return true;
        }
        assert candidates.stream().map(MasterCandidate::getNode).collect(Collectors.toSet()).size() == candidates.size() :
            "duplicates ahead: " + candidates;
        return candidates.size() >= minimumMasterNodes;
    }
    ~~~
  
  - 节点比较逻辑
  
    ~~~java
    /**
     * compares two candidates to indicate which the a better master.
     * A higher cluster state version is better
     *
     * @return -1 if c1 is a batter candidate, 1 if c2.
     */
    public static int compare(MasterCandidate c1, MasterCandidate c2) {
        // we explicitly swap c1 and c2 here. the code expects "better" is lower in a sorted
        // list, so if c2 has a higher cluster state version, it needs to come first.
        int ret = Long.compare(c2.clusterStateVersion, c1.clusterStateVersion);
        if (ret == 0) {
            ret = compareNodes(c1.getNode(), c2.getNode());
        }
        return ret;
    }
    ~~~
  
- **分析**

  - 什么时候开始选主投票？
    - 集群刚启动时
    - master检测到其他节点离开时
    - 其他节点检测到master离开时
  - 在选主进行的时候，有新的节点加怎么办？
    - ES会暂时搁置这些加入请求，直到选主结束之后再来处理。如果本地节点成功当选，就接收这些连接请求；如果没有成功当选，则丢弃这些请求
    - 这些新发现的节点不会被计算到候选者中
  - 每个节点选举出来的master可能不一样，是怎么做到不脑裂的？
  
    - ping过程中发现的候选者数量要大于等于设置项 `discovery.zen.minimum_master_nodes`
  
  - 为什么会出现脑裂，不是已经有 `discovery.zen.minimum_master_nodes` 配置了吗？
    - 假设一开始集群规模为3，那么配置为2是没有任何问题的。但是，一旦集群规模扩大到7，那么合理的配置因为为4。于是，新节点的配置为4，而老节点的配置为2。如果没有及时更新老节点的配置，就会存在脑裂的风险（试想一下，在主节点挂掉时，2个旧节点又恰好和4个新节点产生了网络分区，而由于节点配置项不统一，就会导致脑裂）

##### 2）es7.13

https://www.easyice.cn/archives/332

**流程图**

<img src="https://gitee.com/primabrucexu/image/raw/main/20210709114748.png" alt="es7选主" style="zoom: 50%;" />

- 和标准raft算法的不同之处
  - 在集群启动时，节点默认为candidate
  - candidate不做任何投票限制，这有可能导致产生多个leader，ES选择的是最新的leader（term最大的）
  - candidate在投票的时候，最后才会给自己投票，防止出现同票现象
  - 有预选环节，同预选环节来防止不必要的选举。（比如只有一个节点认为leader离线）
- 和旧版不同之处
  - `cluster.initial_master_nodes`配置。实际上，这个配置只在集群启动时发挥作用。这个配置建议配置上所有具有master资格的节点，且所有节点配置相同。实际上，在集群启动时，es没有足够的信息来判断是否具有足够的法定人数，所以只能依靠这个来进行判断。所有节点配置一致可以保证所有节点认为的法定人数一致，而不会形成多个集群

**为什么说新版本杜绝了脑裂问题？** ==动态法定人数和新旧两种集群状态==

- 因为新版本中的法定投票人数不再由设置决定，而是变成了一个动态更新的值。由ES在依据存活节点数量来判断是否有足够的参与人数。当存活人数为偶数时，es会多排除一个，使得存活人数始终是奇数。（为什么？）

  ~~~java
  public boolean hasQuorum(Collection<String> votes) {
      final HashSet<String> intersection = new HashSet<>(nodeIds);
      intersection.retainAll(votes);
      return intersection.size() * 2 > nodeIds.size();
  }
  ~~~

- 当我们分析是否存在法定人数的调用链时，会看到有这么一段判断，为什么会进行两次是否法定的判断？

  ~~~java
  /**
   * Whether there is an election quorum from the point of view of the given local node under the provided voting configurations
   */
  // satisfiesAdditionalQuorumConstraints由具体实现，这里始终返回true
  public final boolean isElectionQuorum(DiscoveryNode localNode, long localCurrentTerm, long localAcceptedTerm, long localAcceptedVersion,
                                        VotingConfiguration lastCommittedConfiguration, VotingConfiguration lastAcceptedConfiguration,
                                        VoteCollection joinVotes) {
      return joinVotes.isQuorum(lastCommittedConfiguration) &&
          joinVotes.isQuorum(lastAcceptedConfiguration) &&
          satisfiesAdditionalQuorumConstraints(localNode, localCurrentTerm, localAcceptedTerm, localAcceptedVersion,
              lastCommittedConfiguration, lastAcceptedConfiguration, joinVotes);
  }
  ~~~

  >  因为集群状态中保留了新旧两种投票配置，这两个配置在集群稳定时是相同的，但在集群扩张或缩小时会不同。由于在集群变化时，总有那么一个时刻会同时存在新旧两种集群状态，部分节点为新状态，部分节点为旧状态。如果我们只根据其中一个状态来判断法定人数的话，这就会导致脑裂。所以要判断两次，防止出现脑裂现象。



### 3.3 启动Elasticsearch

#### 3.3.1 启动流程

1. 检查构建运行环境

2. 检查Lucene版本，因为不同版本的ES要用不同版本的Lucene

3. 创建node实例，这个实例就是运行时的ES

4. 运行node实例

   1. 加载并运行其余子模块
   2. 在node启动成功之后，对其进行检查

5. 运行keepAlive线程。主线程在启动流程结束之后会退出，该线程是唯一的用户线程，用于保证Java进程存活。ES的运行依赖于其他线程池而不依赖于用户线程

   

#### 3.3.2 启动子模块

Elasticsearch在获得了node实例只有，通过 `guice` 组件将其依赖的子模块进行依赖注入。其组件模块大多数继承自抽象类`AbstractLifecycleComponent`，该接口完美解耦内部组件，其启动过程就是组件的初始化过程。

- 通过stop函数，可以清楚的看到ES运行时启动的子模块（子模块中还可能会依赖其他模块）

```java
private Node stop() {
    if (lifecycle.moveToStopped() == false) {
        return this;
    }
    logger.info("stopping ...");

    injector.getInstance(ResourceWatcherService.class).close();
    injector.getInstance(HttpServerTransport.class).stop();

    injector.getInstance(SnapshotsService.class).stop();
    injector.getInstance(SnapshotShardsService.class).stop();
    injector.getInstance(RepositoriesService.class).stop();
    // stop any changes happening as a result of cluster state changes
    injector.getInstance(IndicesClusterStateService.class).stop();
    // close discovery early to not react to pings anymore.
    // This can confuse other nodes and delay things - mostly if we're the master and we're running tests.
    injector.getInstance(Discovery.class).stop();
    // we close indices first, so operations won't be allowed on it
    injector.getInstance(ClusterService.class).stop();
    injector.getInstance(NodeConnectionsService.class).stop();
    injector.getInstance(FsHealthService.class).stop();
    nodeService.getMonitorService().stop();
    injector.getInstance(GatewayService.class).stop();
    injector.getInstance(SearchService.class).stop();
    injector.getInstance(TransportService.class).stop();

    pluginLifecycleComponents.forEach(LifecycleComponent::stop);
    // we should stop this last since it waits for resources to get released
    // if we had scroll searchers etc or recovery going on we wait for to finish.
    injector.getInstance(IndicesService.class).stop();
    logger.info("stopped");

    return this;
}
```



### 3.4 Elasticsearch的数据一致性

#### 3.4.1 PacificA算法

Elasticsearch的数据存储模型基于的是主从模式，其具体实现中借鉴了微软的PacificA算法的一些思想理念。这里我们先简单介绍一下PacificA算法

##### 1）特点

- 设计了一个通用和抽象的复制框架，模型容易验证正确性，实现不同的策略实例
- 配置管理和数据复制分离，Paxos负责管理配置，主副本策略负责数据复制
- 将错误检测和配置更新容在数据复制的交互里面，去中心化，降低瓶颈

##### 2）**专业术语**

- Replica Group：互为副本的一组数据的集合，其中有一个primary，其余都是secondary
- Configuration: Repica Group的元数据，如副本有哪些，谁是primary等
- Configuration Manager: 配置一致性管理工具
- Serial Number(SN): 代表Update的执行顺序，每次update增加1
- Prepared List: Update操作的序列
- Committed List: 已经提交的update序列，是Prepared List的前缀

##### 3）读写流程

**写入流程**

1. 写请求进入主副本节点（简称主节点），该节点为它分配一个SN。使用SN创建一个`updateRequest`，然后将其插入`prepareList`
2. 主节点将`updateRequest`发送给从副本节点（简称从副本）。从节点在接收到只有同样将其插入到自身的`prepare list`之中，然后返回一个`ack`给主节点
3. 当主节点接收到所有从节点的ack之后，认为这条数据已经被所有从节点正确接收，此时可以进行提交，将此`UpdateRequest`放入`committed list`，`committed list`向前移动。
4. 主节点告知客户端成功操作。对于每一个Prepare消息，主节点告知所有从节点自己 `committed point` 的位置。从节点在收到通知后移动自身 `committed point` 到相同位置。

> 因为主节点只有在收到所有从节点ack之后才会提交数据到 `committed list`，这样就保证了所有从节点中的数据始终是主节点数据的子集，从而保证了数据的一致性

**读取流程**

为了能够获取到最新的数据，所有读取操作只能在主节点中进行。如果应用可以容忍一定的读取窗口，也可以在从节点中进行

##### 4）配置管理

全局的配置管理器负责管理所有副本组的设置。节点可以向管理器提出变更副本的请求，但必须附带上当前配置的版本号。只有当版本号与配置器版本号一致时，配置器才会响应变更请求

##### 5）故障检测

pacificA通过租约机制来(lease)来进行主节点和从节点间的互相检测。主节点会定期向从节点获取租约，其可能产生以下情况

1. 从节点没有及时（lease period）回复。主节点向配置管理器报告从节点异常，同时将自身降级
2. 主节点没有及时（grace period）回复。从节点向配置管理器报告主节点异常，同时提升自己为新的主节点。当有多个从节点同时发现主节点异常时，按照先来后到的顺序提升

==通过简单分析可知，当grace period > lease period时，就不会出现二主的问题==

> 当发生网络分区时，由于grace period > lease period，那么主必然先探测到从下线，于是自身降级。而从探测到主下线之后提升自己为主。由于这时主已经下线，所以不存在二主的情况

##### 6）ES中的PacificA

- master类似于配置管理器，负责维护索引信息
- SequenceNumber和Checkpoint类似PacificA算法中的Serial Number和Committed Point
- 集群状态中的routing_table存储了所有索引、索引有哪些shard、各自的主分片，以及位于哪个节点等信息，类似副本组



#### 3.4.2 Elasticsearch中的分片如何工作

ES会把索引拆分成多个主分片来进行存储，同时，这些主分片都有多个副本。这些副本就是replication group（副本组）。这种数据副本模型属于主备模式，主分片是所有索引操作的入口。它负责检查操作是否有效，一旦主分片接收操作，其副本也要接收相应的操作

##### 1）写入

**概述**

每个索引操作首先会解析到相应的副本组中，然后内部路由到相应的主分片上，主分片负责验证操作并转发请求到副本上。

ES在运行时，master会维护一个同步副本列表（in-sync copies），该列表包含了所有可用的副本和主分片信息，其中的副本都可以保证完成所有的索引处理操作，并给用户返回ack。主分片负责维护数据的一致性，因此必须将操作同步给在列表中的全部副本

**流程图**

<img src="https://gitee.com/primabrucexu/image/raw/main/20210712140947.png" alt="ES数据写入流程" style="zoom:50%;" />

**异常处理**

- 主分片写入失败
  - 主分片节点通知master给自己降级，重新选一个主分片

- 副本写入失败
  - 主分片通知master，将该副本从同步副本列表中移除，同时master会在其余节点重建该副本
- 主分片网络隔离
  - 副本会拒绝来自过时主分片的操作。主分片访问master来获取最新的状态
- 没有任何副本
  - 可以成功写入，但是数据安全无法保证。也可以设置`wait_for_active_shards`来保证数据安全性



##### 2）读取

**概述**

主从模式下的一个好处就是所有主分片和副本的数据都是一致的（正在同步的除外）。因此主分片和同步副本列表中的副本均可以提供读取服务，这么做可以极大的提供吞吐量，提供优秀的并发性能。所以读取操作可以在同步副本列表（in-sync copies）中的任意分片上进行

**流程图**

<img src="https://gitee.com/primabrucexu/image/raw/main/20210712144108.png" alt="ES数据读取流程" style="zoom: 50%;" />

**注意**

- 对于部分请求（如_search），ES倾向于及时返回结果，即使这个结果不是完整的
- 在正常情况下，读操作只在相关分片中执行一次，除非当读取有错误发生
- 对于在写操作响应前的读操作，有一定可能获取到还未写成功的数据。（因为写操作需要全部分片确认，而只要有一个分片写完，那么就有可能读取到这个数据）



### 3.5 索引恢复流程

在ES中，索引有5中恢复的方式，具体方式和用途如下所示。这里我们主要关注的是Peer Recovery，这也是故障恢复使用的方式

| 类型                        | 描述                                       |
| --------------------------- | ------------------------------------------ |
| EmptyStoreRecoverySource    | 从新的副本中恢复                           |
| ExistingStoreRecoverySource | 从现有的磁盘存储中恢复                     |
| PeerRecoverySource          | 从主分片中恢复                             |
| SnapshotRecoverySource      | 从快照中恢复                               |
| LocalShardsRecoverySource   | 从同一节点上的其他分片恢复（收缩索引操作） |

**ES恢复分片的核心思想：**通过Lucene分段和translog来进行恢复

#### 3.5.1 相关概念

##### 1） sequence number

sequence number是用于标记分片中操作的序列号，

##### 2） primary term

primary term是用于标记主分片状态的数据量。它由master统一进行分配，当副本组的主分片发生变化时（通常是提升新副本为主分片），primary term递增。

> 通过primary term和sequence number，我们就可以很好的标识在分片上进行的操作顺序。

##### 3） checkpoint

> 有了primary term 和 sequence number 之后，我们就可以检测出副本和主分片的差异，并使他们重新对齐

ES通过维护 global checkpoint 和 local checkpoint 来比较主分片和副本之间的差异。

- global checkpoint

  global checkpoint 是所有副本都已经完成操作的序列号。它由主分片来维护。主分片通过跟踪在副本上完成的操作来实现。当主分片检测到所有副本均已给出大于当前操作的序列号之后，它会向前推进 global checkpoint 

- local checkpoint

  local checkpoint 也是一个序列号，低于 local checkpoint 的操作表示均已在本地副本上执行成功（写Lucene和translog成功，不一定刷盘）。当副本向主分片ack一个操作之后，它们也会更新 local checkpoint

> 通过比较 global checkpoint 和 local checkpoint，这样就只需要比较一小部分的操作，从而提高了检查效率

##### 4）基于文件恢复和基于操作恢复

由于主分片和副本都是独立的Lucene索引，它们会各自执行自己的Lucene段合并。即使它们保存相同的文档和执行相同的操作，它们的文件也不会是一样的。更何况通过文件恢复还需要通过网络传输大量的数据文件。因此，**ES更倾向于通过操作来进行恢复**

##### 5）恢复状态阶段

| 阶段         | 描述                                         |
| :----------- | :------------------------------------------- |
| INIT         |                                              |
| INDEX        | 恢复Lucene文件，使用本地文件或从复制一个新的 |
| VERIFY_INDEX | 验证索引                                     |
| TRANSLOG     | 启动engine，重置translog，建立Lucene索引     |
| FINALIZE     |                                              |
| DONE         | 完成                                         |

​	

#### 3.5.2 恢复流程

<img src="https://gitee.com/primabrucexu/image/raw/main/20210713174728.png" alt="ES数据恢复流程" style="zoom:50%;" />



#### 3.5.3 核心问题

==假设在副本恢复期间一直有写操作，如何保证主分片和副本的数据一致==

主要面临的问题有两个：**translog会随着flush而删除**，**以及重做操作的时序**

##### 1）保留translog

在ES早期版本中，副本恢复时，主分片会有如下动作

- phase1

  把主分片的Lucene做快照，发给副本。期间不阻塞写操作，新增写操作记录在translog中

- phase2

  把主分片的translog做快照，发给副本重做操作。期间不阻塞写操作，新增写操作同样记录在translog中

- phase3

  给主分片加写锁，把剩余的translog发送给副本。此时数据量很小，所以阻塞时间很短

在ES2中，重构了translog的文件管理模块，允许存在多个translog文件。其维护一个引用文件的列表，包括未完成的recovery，以及那些包含尚未提交到Lucene的operations的文件。

同时提出了translog.view的概念，允许recovery获取一个引用，包括所有当前未提交的translog 文件，以及所有未来新创建的 translog文件，直到view关闭。它们可以使用这个view做operations的遍历操作。在后续版本中，用TranslogDeletionPolicy来translog.view。核心思想不变。

于是现阶段流程如下：

- phase1

  获取translog保留锁，从获取保留锁开始，会保留translog不受其刷盘清空的影响。然后调用Lucene接口把shard做快照，快照含有shard中已经刷到磁盘的文件引用，把这些shard数据复制到副本节点。在phase1结束前，会向副分片节点发送告知对方启动Engine，在phase2开始之前，副分片就可以正常处理写请求了。

- phase2

  对translog做快照，这个快照里包含从phase1开始，到执行translog快照期间的新增索引。将这些translog发送到副分片所在节点进行重放。



##### 2）时序问题

假设在第一阶段执行期间，有客户端索引操作要求将docA的内容写为1，主分片执行了这个操作，而副分片由于尚未就绪所以没有执行。第二阶段期间客户端索引操作要求写 docA 的内容为2，此时副分片已经就绪，先执行将docA写为2的新增请求，然后又收到了从主分片所在节点发送过来的translog重复写docA为1的请求该如何处理？

写操作有三种类型：创建索引、更新、删除。其中创建索引无需考虑时序问题，那么会造成时序问题的操作就是更新和删除。

<img src="https://gitee.com/primabrucexu/image/raw/main/20210713182640.png" alt="image-20210713182639977"  />

还记得ES的文档中有version这么一个字段吗？这个字段我们能拿来做乐观锁用。同样的原理，**ES也采用version字段来标记操作的顺序**

ES在执行写操作的时候，通过比较操作所带的版本号和预期的版本号来决定这个操作要不要执行。在恢复过程中，的实现逻辑就是通过比较已有文档版本号和操作的版本号来判断这个操作是否过时



#### 3.5.5 如何提高恢复效率

- 配置项 `cluster.routing.allocation.node_concurrent_recoveries` 决定了单个节点执行恢复时的最大并发数，默认为2
- 配置项`indices.recovery.max_bytes_per_sec`决定节点间复制数据时的限速，可以适当提高此值或取消限速。但只有phase1可以配置限速
- 配置项`cluster.routing.allocation.node_initial_primaries_recoveries`决定了单个节点执行主分片recovery时的最大并发数，默认为4。由于主分片的恢复不涉及在网络上复制数据，仅在本地磁盘读写，所以在节点配置了多个数据磁盘的情况下，可以适当提高此值
- 在重启集群之前，先暂停写入，手动执行sync flush。这样可以有机会跳过phase1
- 合并 Lucene 分段，对于冷索引甚至不再更新的索引执行_forcemerge，较少的Lucene分段可以提升恢复效率，例如，减少对比，降低文件传输请求数量。
- 适当地多保留些 translog，配置项 `index.translog.retention.size` 默认最大保留512MB, `index.translog.retention.age`默认为不超过12小时。调整这两个配置可让恢复过程有机会跳过phase1。



### 3.6 索引生命周期管理（ILM）

#### 3.6.1 简介

ES在6.7中正式引入了索引生命周期管理功能，其根据索引的更新和搜索评率，将索引的生命周期划分为5个阶段

- **hot** —— 索引在非常活跃的更新和查询
- **warm** —— 索引几乎不再更新，但仍有较多的查询
- **cold** —— 索引不再更新，并且不经常查询。 信息仍然可以搜索，但查询速度较慢
- **frozen** —— 索引不再更新，并且很少查询。 信息仍然可以搜索，但查询速度非常慢
- **Delete** —— 不再需要这个索引和它的数据了

ILM在索引阶段变化时执行如下操作：

- **Rollover** —— 当索引触发限制条件后，创建新的索引                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
- **Shrink** —— 缩减索引的主分片数量
- **Force merge** —— 强制合并分片中段的数量
- **Freeze** —— 冻结索引，使其只读
- **Delete** —— 删除索引

**注意**

ILM在索引有分片为分配时也会执行更新策略，这可能导致某些不可控的后果。同时，ILM定期运行，检查索引是否符合策略条件，并执行任何需要的步骤。 为避免竞争条件，ILM可能需要多次运行以执行完成操作所需的所有步骤。这可能导致执行时间会比预期的要长

#### 3.6.2 生命周期策略

**实例与说明**

~~~json
PUT _ilm/policy/hot-warm-cold-delete-60days
{
  "policy": {
    "phases": {
      "hot": {
        "actions": {
          "rollover": {
            "max_size":"50gb",	// 设置该阶段索引的最大大小
            "max_age":"30d"		// 设置该阶段索引的最长持续时间
          },
          "set_priority": {
            "priority":50		// 设置恢复优先级
          }
        }
      },
      "warm": {
        "min_age":"7d",			// 设置进入该阶段索引的最小年龄
        "actions": {
          "forcemerge": {		// 段强制合并设置
            "max_num_segments":1
          },
          "shrink": {			// 缩减主分片设置
            "number_of_shards":1
          },
          "allocate": {			// 再分配节点设置
            "require": {
              "data": "warm"
            },
            "number_of_replicas" : 2	// warm阶段中的副本数量
          },
          "set_priority": {
            "priority":25
          }
        }
      }
    }
  }
}
~~~



### 3.7 ES中的缓存

Elasticsearch在运行过程中会使用到各种各样的缓存，如Query cache、Request cache、page cache、fielddata。这里主要讨论Query cache 和 Request cache

#### 3.7.1 Shard Request Cache

##### 1）简介

顾名思义，这是分片级别的缓存。缓存采用LRU机制，key包含shard、indexreader、查询请求的信息，value是查询结果序列化之后的数据。

由于客户端查询请求会被序列化之后作为key的一部分，所以有可能同样的查询、json内容顺序不同等变化都会导致无法命中缓存

由于这是分片级别的缓存，所以当新的段写入到分片后，原有的缓存将会失效

##### 2）缓存策略

不是所有的查询请求都会被缓存。在IndicesService#canCache中定义了哪些请求时不能被缓存的。不被缓存的情况有

- 使用了scroll滚动查询
- 查询类型不是QUERY_THEN_FETCH
- 带有分析的查询，设置了profile属性
- 设置不需要缓存
- 范围查询中带有now的。这种查询是毫秒级别的，缓存没有意义

##### 3）配置项与监控api

- `indices.requests.cache.size`：设置缓存占用堆空间的大小，默认是1%

- `index.requests.cache.enable`：缓存开关
- `GET /_nodes/stats/indices/request_cache?human`

#### 3.7.2 Node Query Cache

##### 1）简介

相比于Shard Request Cache这种分片级别的缓存，Node Query Cache就只缓存filter中过滤的结果。所以，Node Query Cache又被称为Filter Cache。

**Filter查询和普通查询最主要的区别就是不会计算评分**

它可以缓存不同查询之间使用到重复数据的部分。比如说张三查询了近一个月内火腿肠的销量，李四查询了近一个月内火腿肠不合格的数量。这两次查询的共同之处是时间范围都是一个月内。而Node Query Cache会在第一次查询时对近一个月内的数据打上标记，在下一次查询的时候只去搜索这些打过标记的数据。

这种缓存是Lucene中实现的，ES主要进行的是策略控制。

##### 2）工作原理

Node Query Cache使用位图这种数据结构来对数据进行标记。首先创建一个大小为maxDoc（文档数量）的位图：FixedBitSet。然后在遍历过程中对命中的文档在位图对应位置做标记。例如，`[1,2,7]`位置的文档命中了查询条件，那么对应的位图则为`[1,1,0,0,0,0,1]`。当一个查询中有多个filter条件时，只需要做位运算即可快速准确的找到命中的文档

##### 3）缓存策略

ES使用UsageTrackingQueryCachingPolicy作为默认的缓存策略。这个策略的主要关注点是：

- 是不是特定类型的查询。如term query（精确查询）、MatchAllDocsQuery、MatchNoDocsQuery。以及子查询为空的BooleanQuery、DisjunctionMaxQuery
- 某条 query 的访问频率大于等于特定阈值之后，该 query结果才会被缓存。对于访问频率，主要分为2类，一类是访问2次就会被缓存，包括： MultiTermQuery、MultiTermQueryConstantScoreWrapper、TermInSetQuery、PointQuery 在 isCostly方法中定义。其余类型的查询访问5次会被缓存。

> 如何统计某个query的频率？
>
> Lucene中维护了一个长度为256的环形buffer。每个查询在被hash之后保存进去。因此，上面的频率可以理解为最近256次查询中出现的频率

##### 4）配置项与监控

- `indices.queries.cache.count`，缓存查询的数量，默认是1w
- `indices.queries.cache.size`，缓存占堆内存的大小，默认是10%
- `GET /_nodes/stats/indices/query_cache?human`

----

### 3.8 Cluster

#### 3.8.1 集群状态

##### 1）**API命令**

`curl -X GET ＂localhost:9200/_cluster/state＂`

在默认情况下，协调节点在接收到这个命令之后会将其转发到主节点上，以此来确保得到的集群状态是最新的。当需要检查本地节点中集群状态时，可以通过参数 `local=true` 来请求本地节点的状态

##### 2）细节处理

由于集群状态中会包含大量的信息细节，包括全部节点信息、索引元数据、路由信息等。同时集群状态需要频繁更新。所以，为了提高效率，集群中的节点都是通过差量更新的方式来获取集群状态信息。如果某个节点之前并不存在与集群状态中，此时才会进行全量更新。

状态信息示例

~~~json
{
    "cluster_name": "elasticsearch",
    "cluster_uuid": "PHU-5qILQe6UsO2FPwUU9A",
    "version": 46,
    "state_uuid": "W8OaiKOrTTO_pft0yiRgYQ",
    "master_node": "QwvmJ1i2QCir15mByfgpnQ",
    "blocks": {
        // 阻塞信息
    },
    "nodes": { 
        // 节点信息
    },
    "metadata":{
        // 索引元数据，包括索引模板、别名、映射等
    },
    "routing_table": {
        // 分片路由表。记录每个分片存的信息
    },
    "routing_nodes": {
        // 接电路由信息。记录每个节点上存的分片信息
    }
}
~~~







---

## 4. 性能优化与故障诊断

### 4.1 写入速度优化

#### 4.1.1 增大translog flush间隔

**配置项**：`index.translog.durability`

默认值是`request`，也就是每次写入完成后马上进行刷盘。这实际上是影响ES写入性能最大的因素。但只有这样，才能保证每个写操作都是安全的

如果系统可以忍受一定概率的数据丢失，那么可以调整为 `async`，同时增大刷盘间隔和translog最大大小来提高写入性能

~~~yaml
index.translog.durability: async
index.translog.sync_interval: 5s
index.translog.flush_threshold_size: 512mb
~~~



#### 4.1.2 增大index refresh间隔

**配置项：** `index.refresh_interval`

默认是1秒，这意味着数据在写入后1秒就可以被搜索到了。如果间隔过短，则会产生大量Lucene的段，这将导致频繁的段合并。如果不需要这么高的实时搜索性能，可以适当考虑降低索引refresh周期



#### 4.1.3 段合并优化

段合并是ES在运行中不可避免的操作，通过段合并可以提高搜索的速度，并节约系统资源。但由于段合并时需要消耗大量的CPU和I\O资源，所以，过于频繁的段合并反而会拖慢ES的运行速度

**配置项：**`index.merge.scheduler.max_thread_count`

默认值是`Math.max(1, Math.min(4, <<node.processors, node.processors>> / 2))`。默认值的设置对于ssd来说很友好，但如果只有一块机械硬盘的话，还是设置成1比较好



#### 4.1.4 indexing buffer

index buffer是在ES为doc创建索引时使用。当缓冲区写满之后，会生成一个新的段。每个分片都有自己的buffer，在实际计算每个分片有多少buffer的时候要除以分片数

**配置项**

~~~yaml
indices.memory.index_buffer_size: 10%
indices.memory.min_index_buffer_size: 48MB
indices.memory.max_index_buffer_size: 100MB # 默认是无限制
~~~



#### 4.1.5 使用bulk请求

批量写入比多次单个写入要高效的多，但要注意**控制批量请求的大小和数量。**

如果太大的话内存扛不住

如果过多的话，则可能造成过长的等待队列，这可能会引起频繁gc



#### 4.1.6 调整索引过程

##### 1）使用自动生成的文档id

如果手动给出了文档id，那么ES在写入时会先去检查是否要执行更新操作，这属于不必要的操作

##### 2）调整字段mapping

对于不需要建立索引的字段，index属性设置为no或者not_analyzed，这么做可以降低CPU的占用

##### 3）调整 _source 字段

source 字段用于存储 doc 原始数据，对于部分不需要存储的字段，可以通过 includes excludes过滤，或者将source禁用，一般用于索引和数据分离。



### 4.2 搜索速度优化

#### 4.2.1 预留足够的内存空间

在一般情况下，应用程序的读写都会被操作系统cache（除了direct方式），cache保存在系统物理内存中（线上应该禁用swap），命中cache可以降低对磁盘的直接访问频率。搜索很依赖对系统cache 的命中，如果某个请求需要从磁盘读取数据，则一定会产生相对较高的延迟。**应该至少为系统cache预留一半的可用物理内存，更大的内存有更高的cache命中率。**



#### 4.2.2 使用冗余字段

ES虽然允许join操作，但嵌套查询的效率比普通查询低好几倍，而父子文档可能会更慢。

**所以，使用冗余字段往往比嵌套查询和父子文档来高效的多**

同时，冗余字段还在某些特定方面表现优秀

如果所有的文档都有某些相同字段，并且大多数查询都会对其在一个固定的范围上进行聚合。那我们可以在创建索引的时候把这个固定的范围带上，并使用term聚合来加快聚合速度

例如：搜索商品时按照价格区间进行搜索，那么我们可以在创建索引的时候加入价格区间这个字段来加快索引



#### 4.2.3 强制进行段合并

对于几乎不怎么更新或者不再更新的索引执行强制段合并。因为较少了段的数量，结果的合并速度会提高，同时还能提高恢复的速度



#### 4.2.4 使用全局序号（global ordinals）

全局序号是一种数据结构，用于在keyword字段上进行term聚合。它用一个数值来代表字段中的字符串值，然后为每一数值分配一个 bucket。默认情况下，他们会延迟构建。但由于ES不知道哪些字段会被用于term聚合，所以我们要提前告诉他

~~~json
PUT index
{
    "mappings":{
        "type":{
            "properties": {
                "foo":{
                    "type": "keyword",
                    "eager_global_ordinals": true
                }
            }
        }
    }
}
~~~



#### 4.2.5 限制搜索请求涉及到的分片数

一个搜索请求涉及的分片数量越多，协调节点的CPU和内存压力就越大。默认情况下，ES会拒绝超过1000个分片的搜索请求。我们应该更好地组织数据，让搜索请求的分片数更少。如果想调节这个值，则可以通过action.search.shard_count配置项进行修改。虽然限制搜索的分片数并不能直接提升单个搜索请求的速度，但协调节点的压力会间接影响搜索速度，例如，占用更多内存会产生更多的GC压力，可能导致更多的stop-the-world时间等，因此间接影响了协调节点的性能



#### 4.2.6 使用ARS来提高响应速度

自适应分片选择（Adaptive Replica Selection）可以避免搜索请求路由到负载较高的节点上。该选项在集群负载均衡是能略微提高吞吐量和响应速度；在出现繁忙节点时，能有效提高吞吐量和响应速度

**配置项：** `cluster.routing.use_adaptive_replica_selection`

该配置从ES6.1开始启用，但默认是关闭状态。不过再ES7中，它将默认开启

官方对其有详细说明：https://www.elastic.co/cn/blog/improving-response-latency-in-elasticsearch-with-adaptive-replica-selection



### 4.3 故障诊断常用手段

1. profile API 来定位慢查询
2. explain API 来分析为什么分片分配失败
3. cat API 来检查内存使用情况



### 4.4 内存里都有些啥

1. bulk队列
2. Netty内存池
3. index buffer，写入缓冲区
4. 数据集的聚合
5. segment
6. 各种cache，fielddata cache，node query cache，shard request cache



> 推荐阅读
>
> 1. 使用索引生命周期管理实现热温冷架构：https://www.elastic.co/cn/blog/implementing-hot-warm-cold-in-elasticsearch-with-index-lifecycle-management
> 2. 高级调优：查找并修复 Elasticsearch 慢查询：https://www.elastic.co/cn/blog/advanced-tuning-finding-and-fixing-slow-elasticsearch-queries
> 3. Elasticsearch 集群协调迎来新时代:https://www.elastic.co/cn/blog/a-new-era-for-cluster-coordination-in-elasticsearch
> 4. Elasticsearch 缓存深度剖析：一次提高一种缓存的查询速度，https://www.elastic.co/cn/blog/elasticsearch-caching-deep-dive-boosting-query-speed-one-cache-at-a-time
> 5. 使用ARS来提高搜索响应速度：https://www.elastic.co/cn/blog/improving-response-latency-in-elasticsearch-with-adaptive-replica-selection
> 6. https://www.elastic.co/guide/en/elasticsearch/reference/current/removal-of-types.html#_migrating_multi_type_indices_to_single_type









