# Kafka

## 1 消息中间件

### 1.1 概述

​    	消息中间件利用高效可靠的消息传递机制进行平台无关的数据交流，并基于数据通信来进行分布式系统的集成。通过提供消息传递和消息排队模型，它可以在分布式环境下扩展进程间的通信。		

- 关键词
    - 异步、消息传递、分布式

### 1.2 MQ的构成

-   Broker
    -   MQ的主题，作为server提供核心业务功能
-   Producer
    -   生产者，生产者要做的就是把生成消息，然后传递给broker
-   Consumer
    -   消费者，从MQ里面拉取消息并进行业务处理
-   Topic
    -   发布订阅模式下的channel，可以理解为该模式下的消息都发到这里
-   Queue
    -   点对点模式下消息存储的地方
-   Message
    -   消息，不同的MQ对消息有不同的封装方式

### 1.3 MQ的工作模式

#### 1）点对点

-   示意图

    ![9nbee9vy8f](https://gitee.com/primabrucexu/image/raw/main/20210401153556.jpeg)

-   **重点**

    -   该模式下，一条消息只能由一个消费者消费。当该条消息被消费之后，它就会被删除。
    -   尽管可以存在多个消费者，但是一条消息只能被一个消费者消费

#### 2）发布/订阅

-   示意图

    ![tqbka12xwt](https://gitee.com/primabrucexu/image/raw/main/20210401153620.jpeg)

-   **重点**
    -   在该模式下，Topic里面的消息可以被多个消费者消费。（消费之后消息丢不丢取决于MQ的设置）
    -   如果当前没有可用的消费者，消息会保留到至少有一个消费者可用时

### 1.4 为什么要使用MQ

#### 1）解耦

![qq5stbz1tk](https://gitee.com/primabrucexu/image/raw/main/20210401155252.png)

A系统生成数据，BCD系统需要这个数据。在没有MQ的时候，那我A系统就要考虑如下问题：

>   如果BCD中有人挂了，我是要重发还是存起来？如果要的话该怎么重发？怎么确定BCD已经收到了？又一个E新增的话怎么办？如果有一个不需要了怎么处理？。。。
>
>   这工作量可太大了

在使用MQ之后，A系统生产的数据，直接就丢到MQ里面就好了，谁要谁来拿，不要赶紧爬。哪里还用考虑那么多的问题

![17kmd7l1pw](https://gitee.com/primabrucexu/image/raw/main/20210401155945.png)

#### 2）异步

典型场景：积分通常在消费之后的一顿时间内到账

![image-20210401163444416](https://gitee.com/primabrucexu/image/raw/main/20210401163444.png)

​		在消费的时候，顾客一定是在第一位的，因为给钱的都是大爷嘛。你有没有见过说因为某品牌积分系统故障而不让你消费的报道吗？（显然没有啊，积分只是用来激励消费这的一种辅助措施，资本家怎么可能会因小失大）

![image-20210401163817376](https://gitee.com/primabrucexu/image/raw/main/20210401163817.png)

​		==所以在业务执行流程中，所有无关紧要的事情都可以通过消息队列异步处理，没有必要来占据主流程的处理时间==

#### 3）削峰

典型场景：双十一

![image-20210401162505331](https://gitee.com/primabrucexu/image/raw/main/20210401162505.png)

在双十一的时候，几个亿的并发是跑不掉的。受限于系统等因素，类似于MySQL这类的数据库是完全扛不住这么高的并发。要是这么高的并发直接把MySQL冲烂了，双十一淘宝直接G了，那你可以提前收拾东西了（马爸爸能放过你我是不信的）

![image-20210401162719333](https://gitee.com/primabrucexu/image/raw/main/20210401162719.png)

这个时候MQ的重要性就体现出来了。尤其是Kafka这种可以无限存储数据的MQ就显得尤为重要（在内存够用的情况下）。MQ可以把数据都存在自己里面，然后MySQL慢慢从MQ里面拉去，这样MySQL就不会因为高并发而暴毙，你的饭碗也保住了

### 1.5 MQ的技术难点

#### 1）可用性

采用集群的模式来保证可用性。（1个不行来2个，2个不行来5个...）

受限于CAP理论，高并发势必会导致数据可靠性的下降。不过现在市面成熟的消息中间件都可以通过配置来进行优化，减少数据丢失的可能性

#### 2）消息重复消费

通过确认机制来标识消息已经被消费过了，Kafka有offset，RabbitMQ有ack机制，通常来说消费者也可以采取某些手段来避免消费

#### 3）消息丢失

-   生产者丢失数据
    -   通过事务等操作来重试
-   MQ丢失数据
    -   持久化或冗余，一份数据有多个备份
-   消费者丢失数据
    -   通常是使用了自动确认机制，它在收到消息之后会通知MQ，MQ收到通知就会把消息给删了。改为手动确认即可

#### 4）消息的顺序性

​		如果要保证消息有序，那么就只能有一个消费者，但就会损失了吞吐量，因为受限于CAP理论，强一致就没法保证高并发。

​		为了在高并发的情况下尽量保证有序性，这就需要消费者自己通过某些逻辑或者算法来实现。比如说设置一个标识变量，然后不停的去判断

### 1.6 常用MQ对比

![图片](https://gitee.com/primabrucexu/image/raw/main/20210402143212)

## 2 Kafka入门

### 2.1 架构

![图片](https://gitee.com/primabrucexu/image/raw/main/20210402143309)

-   **Producer**：生产者，负责生产消息然后发送给Kafka

-   **Consumer**：消费者，负责从从Kafka上获取消息并进行处理

-   **Consumer Group**：一个消费者组可以包含一个或多个消费者。使用多分区 + 多消费者方式可以极大提高数据下游的处理速度，同一消费组中的消费者不会重复消费消息，同样的，不同消费组中的消费者消息消息时互不影响。Kafka 就是通过消费组的方式来实现消息 P2P 模式和广播模式。

    如图所示

    ![img](https://kafka.apachecn.org/10/images/consumer-groups.png)

-   **Broker：** 服务代理节点，也就是Kafka服务器

-   **Topic：** Kafka中的消息通过Topic来进行划分，也就是消息处理和分发的地方

-   **Partition：** 分区就是Topic一部分，一个Topic可以划分多个分区，每个分区的内容不同。分区在储存层面上可以当做一个可追加的日志（Log）文件，消息在被追加到分区日志文件的时候都会分配一个特定的偏移量（offset）。

    ![log_anatomy](https://gitee.com/primabrucexu/image/raw/main/20210401181355.png)

-   **Offset：** offset是消息在分区中的唯一标识。消费者通过offset考试Kafka它要消费的是哪一条数据

-   **Replication**：副本，是 Kafka 保证数据高可用的方式，Kafka中同一 Partition 的数据可以在多 Broker 上存在多个副本，==但只有主副本对外提供读写服务==。当主副本所在 broker 崩溃或网络异常，Kafka 会在 Controller 的管理下会重新选择新的 Leader 副本对外提供读写服务



### 2.2 功能

#### 1）MQ

-   Kafka的优势在于每个topic都有以下特性—可以扩展处理并且允许多订阅者模式—不需要只选择其中一个.
-   Kafka相比于传统消息队列还具有更严格的顺序保证（Kafka可以保证先生产出来的数据一定比后生产出来的数据在日志中早出现）
-   Kafka 设计的更好。
    -   topic中的partition是一个并行的概念。 Kafka能够为一个消费者池提供顺序保证和负载平衡，是通过将topic中的partition分配给消费者组中的消费者来实现的， 以便每个分区由消费组中的一个消费者消耗。通过这样，我们能够确保消费者是该分区的唯一读者，并按顺序消费数据。 众多分区保证了多个消费者实例间的负载均衡。但请注意，消费者组中的消费者实例个数不能超过分区的数量。

#### 2）存储系统

-   数据写入Kafka后被写到磁盘，并且进行备份以便容错。直到完全备份，Kafka才让生产者认为完成写入，即使写入失败Kafka也会确保继续写入
-   Kafka使用磁盘结构，具有很好的扩展性—50kb和50TB的数据在server上表现一致。
-   可以存储大量数据，并且可通过客户端控制它读取数据的位置，您可认为Kafka是一种高性能、低延迟、具备日志存储、备份和传播功能的分布式文件系统。

#### 3）流处理

-   读写和存储流式数据
-   在Kafka中，流处理器不断地从输入的topic获取流数据，处理数据后，再不断生产流数据到输出的topic中去。
-   Stream API 允许应用做一些复杂的处理，比如将流数据聚合或者join。

#### 4）批处理

## 3 Kafka高性能的秘密

-   关键词：顺序读写、page cache、0拷贝、Reactor、文件结构、数据压缩、分区并发

### 3.1 磁盘IO

#### 1）顺序写

>   读写磁盘慢在哪里

![图片](https://gitee.com/primabrucexu/image/raw/main/20210402135831)

回顾一下磁盘IO的过程：寻道、旋转、数据传输。通常来说，数据传输的时间消耗通常可以忽略不计。所以大头就剩下了寻道和旋转了。

1.  寻道时间

    寻道指的是把磁头指到正确的磁道上。平均时间为3-15ms

2.  旋转延迟

    旋转延迟指的是把目标扇区转过去的时间。通常用磁盘旋转一周时间的一半表示。比如7200rpm的磁盘旋转延迟约为：$t=\frac{60*1000}{7200*2}=4.1667ms$

所以，顺序写可以极大的节省磁盘寻道和旋转的时间，从而可以极大的提高磁盘IO。同时操作系统对顺序读写进行了大量的优化，通过read-ahead 和 write-behind技术可以极高的加快读写速度

#### 2）page cache

​		为了加快读写磁盘的速度，操作系统会将空闲的内存做page cache，所有对磁盘的读写操作都通过这个cache来操作。除非这个cache里面没有命中才会去进行IO操作

​		所以，Kafka会先将数据写在page cache中，等待操作系统的调用将数据写会磁盘。读的时候也先从page cache里面读，没有的话才会去磁盘里面读，而且还会把临近的数据也一起读出来放到page cache中

#### 3）0拷贝

>   传统的IO模式

从磁盘读取文件并从网络发送实际上要经历四次完整的文件拷贝

![gvx7kghuej](https://gitee.com/primabrucexu/image/raw/main/20210402150442.jpeg)

1.  从磁盘将文件copy到操作系统内核缓冲区中
2.  从内核缓冲区中copy到应用程序空间
3.  从程序空间copy到socket发送缓冲区
4.  从socket缓冲区中copy到网卡上，网卡发送

这是操作系统为了安全考虑，将用户内存、应用内存和系统内存做了划分

>   0拷贝

0拷贝指的不是真正意义上的0拷贝，而是一种减少拷贝次数的技术。涉及到操作系统中的`sendfile`和`mmap`技术

-   mmap	

    mmap=Memory Mapped Files，也就是说磁盘上的文件会被直接映射到物理内存中，文件在内存上的改变会被同步磁盘上（操作系统决定什么时候同步）。也就是说，mmap可以让用户通过内存来读写文件

-   sendfile

    sendfile可以使得数据直接在内核空间中进行传输，不需要在copy到用户空间。结合mmap，就想这样

    ![6ya3m48b67](https://gitee.com/primabrucexu/image/raw/main/20210402150925.png)

### 3.2 网络优化

#### 1）Reactor线程模型

![图片](https://gitee.com/primabrucexu/image/raw/main/20210402151221.png)

Reacotr 模型主要分为三个角色

-   Reactor：把 IO 事件分配给对应的 handler 处理
-   Acceptor：处理客户端连接事件
-   Handler：处理非阻塞的任务

>   连接请求

`Acceptor`线程，用于处理新的连接，`Acceptor` 有 N 个 `Processor` 线程 select 和 read socket 请求，N 个 `Handler` 线程处理请求并相应，即处理业务逻辑。

### 3.3 文件

#### 1）数据压缩

Kafka支持多种压缩算法，可以节省磁盘和网络IO的开销

#### 2）数据存储

因为Kafka是运行在JVM上的，但是它不存储对象，它存储的是数据，因此不需要考虑GC等问题带来的负担。与此同时，再加上他使用的是mmap进行文件读写，所以不需要考虑内存空间不够用的问题（有虚拟内存保底）

#### 3）文件结构

Kafka分区中的日志文件会按照大小划分为一个个的segment

![图片](https://gitee.com/primabrucexu/image/raw/main/20210402153236.png)

-   segment file 组成：由 2 部分组成，分别为 index file 和 data file，此 2 个文件一一对应，成对出现，后缀”.index”和“.log”分别表示为 segment 索引文件、数据文件。
-   segment 文件命名规则：partion 全局的第一个 segment 从 0 开始，后续每个 segment 文件名为上一个 segment 文件最后一条消息的 offset 值。数值最大为 64 位 long 大小，19 位数字字符长度，没有数字用 0 填充。
-   index 采用稀疏索引，这样每个 index 文件大小有限，同时，mmap可以让Kafka通过操作内存操作磁盘，减少磁盘IO

## 4 使用Kafka

### 4.1 Producer

-   生成消息流程图

    ![图片](https://gitee.com/primabrucexu/image/raw/main/20210402154501.png)

-   参数配置

    -   `bootstrap.server`

        >   指定 Kafka 的 Broker 的地址

    -   `key.serializer`

        >   key 序列化器

    -   `value.serializer`

        >   value 序列化器

    -   `batch.num.messages`

        >   默认值：200，每次批量消息的数量，只对 asyc 起作用。

    -   `request.required.acks`

        >   默认值：0，0 表示 producer 毋须等待 leader 的确认，1 代表需要 leader 确认写入它的本地 log 并立即确认，-1 代表所有的备份都完成后确认。只对 async 模式起作用，这个参数的调整是数据不丢失和发送效率的 tradeoff，如果对数据丢失不敏感而在乎效率的场景可以考虑设置为 0，这样可以大大提高 producer 发送数据的效率。

    -   `request.timeout.ms`

        >   默认值：10000，确认超时时间。

    -   `partitioner.class`

        >   默认值：kafka.producer.DefaultPartitioner，必须实现 kafka.producer.Partitioner，根据 Key 提供一个分区策略。*有时候我们需要相同类型的消息必须顺序处理，这样我们就必须自定义分配策略，从而将相同类型的数据分配到同一个分区中。*

    -   `producer.type`

        >   默认值：sync，指定消息发送是同步还是异步。异步 asyc 成批发送用 kafka.producer.AyncProducer， 同步 sync 用 kafka.producer.SyncProducer。同步和异步发送也会影响消息生产的效率。

    -   `compression.topic`

        >   默认值：none，消息压缩，默认不压缩。其余压缩方式还有，"gzip"、"snappy"和"lz4"。对消息的压缩可以极大地减少网络传输量、降低网络 IO，从而提高整体性能。

    -   `compressed.topics`

        >   默认值：null，在设置了压缩的情况下，可以指定特定的 topic 压缩，未指定则全部压缩。

    -   `message.send.max.retries`

        >   默认值：3，消息发送最大尝试次数。

    -   `retry.backoff.ms`

        >   默认值：300，每次尝试增加的额外的间隔时间。

    -   `topic.metadata.refresh.interval.ms`

        >   默认值：600000，定期的获取元数据的时间。当分区丢失，leader 不可用时 producer 也会主动获取元数据，如果为 0，则每次发送完消息就获取元数据，不推荐。如果为负值，则只有在失败的情况下获取元数据。

    -   `queue.buffering.max.ms`

        >   默认值：5000，在 producer queue 的缓存的数据最大时间，仅仅 for asyc。

    -   `queue.buffering.max.message`

        >   默认值：10000，producer 缓存的消息的最大数量，仅仅 for asyc。

    -   `queue.enqueue.timeout.ms`

        >   默认值：-1，0 当 queue 满时丢掉，负值是 queue 满时 block, 正值是 queue 满时 block 相应的时间，仅仅 for asyc。

### 4.2 Consumer

-   Kafka中有consumer group的概念，每个分区只能被一个消费组中的一个消费者所消费。
    -   当分区数量大于等于组内消费者数量的时候，每个消费者都会获得相同数量的分区
    -   当分区数量小于消费者数量的时候，会出现有消费者拿不到分区的情况
-   Kafka的consumer客户端不是线程安全的，使用的时候需要自行处理
-   常用参数配置
    -   `bootstrap.servers`：连接 broker 地址，`host：port` 格式。
    -   `group.id`：消费者隶属的消费组。
    -   `key.deserializer`：与生产者的`key.serializer`对应，key 的反序列化方式。
    -   value.deserializer：与生产者的`value.serializer`对应，value 的反序列化方式。
    -   `session.timeout.ms`：coordinator 检测失败的时间。默认 10s 该参数是 Consumer Group 主动检测 （组内成员 comsummer) 崩溃的时间间隔，类似于心跳过期时间。
    -   `auto.offset.reset`：该属性指定了消费者在读取一个没有偏移量后者偏移量无效（消费者长时间失效当前的偏移量已经过时并且被删除了）的分区的情况下，应该作何处理，默认值是 latest，也就是从最新记录读取数据（消费者启动之后生成的记录），另一个值是 earliest，意思是在偏移量无效的情况下，消费者从起始位置开始读取数据。
    -   `enable.auto.commit`：否自动提交位移，如果为`false`，则需要在程序中手动提交位移。对于精确到一次的语义，最好手动提交位移
    -   `fetch.max.bytes`：单次拉取数据的最大字节数量
    -   `max.poll.records`：单次 poll 调用返回的最大消息数，如果处理逻辑很轻量，可以适当提高该值。但是`max.poll.records`条数据需要在在 session.timeout.ms 这个时间内处理完 。默认值为 500
    -   `request.timeout.ms`：一次请求响应的最长等待时间。如果在超时时间内未得到响应，kafka 要么重发这条消息，要么超过重试次数的情况下直接置为失败。
-   