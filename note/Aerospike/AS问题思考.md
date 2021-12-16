# AS问题

### 问题1

**情景**：AS跨集群迁移数据，同时不允许停止服务。如何保证数据一致性

**核心思路**

通过批次号来决断在新集群中对数据做何种操作

**方案1**

![image-20210916102629861](https://gitee.com/primabrucexu/image/raw/main/20210916102630.png)

### 问题2

**情景**

一个namespace，1个set，1000w，扫描一个set耗时

**方案**

- 插入数据

  ~~~java
  public void insert(long keyCurrent) {
      Key key = new Key(namespace, setName, IdUtil.getSnowflake().nextId());
      Bin[] bins = new Bin[2];
      bins[0] = new Bin("id", keyCurrent);
      bins[1] = new Bin("value", IdUtil.fastSimpleUUID());
      WritePolicy writePolicy = new WritePolicy();
      writePolicy.expiration = 3600;
      writePolicy.recordExistsAction = RecordExistsAction.CREATE_ONLY;
      client.put(writePolicy, key, bins);
      this.counter.write.count.getAndIncrement();
  }
  ~~~

    - 数据格式：PK是通过雪花算法生产的id，两个bin分别是当前数据序号和一个uuid

      ![image-20210918142606417](https://gitee.com/primabrucexu/image/raw/main/20210918142606.png)

    - 总结：通过活动监视器，基本可以确定瓶颈是网络带宽

      ![image-20210918143108486](https://gitee.com/primabrucexu/image/raw/main/20210918143108.png)

      ![image-20210918143047757](https://gitee.com/primabrucexu/image/raw/main/20210918143047.png)

- Java客户端进行扫描

  ~~~java
  public void scan() {
      CLIENT.scanAll(null, NAMESPACE, SET_NAME, new ScanCallback() {
          @Override
          public void scanCallback(Key key, Record record) throws AerospikeException {
              int i = NUMBER.incrementAndGet();
              if (i % 10000 == 0) {
                  log.info("[{}] records have been scan", i);
              }
          }
      });
  }
  ~~~

**结果**

- 插入结果

![image-20210918144749246](https://gitee.com/primabrucexu/image/raw/main/20210918144825.png)

- 扫描结果

![image-20210918144815141](https://gitee.com/primabrucexu/image/raw/main/20210918144815.png)

### 问题3

**情景**

一个namespace，10个set，每个set100w数据。扫描所有set耗时

**结果**

- 插入结果

![image-20210918164424139](https://gitee.com/primabrucexu/image/raw/main/20210918164424.png)

- 扫描结果

  ![image-20210918165045430](https://gitee.com/primabrucexu/image/raw/main/20210918165045.png)

### 问题4

**情景**

验证scan是否针对快照，还是非快照。可以造1000w数据，scan过程中记数。scan过程中插入1000w，确认scan结束后，计数量是多少

**方案**

- 插入数据，采用asbenchmark插入100w数据

  ~~~bash
  asbenchmark -h 10.58.12.196 -n ns1 -s pbx -e 3600 -Y 1024 -z 128 -k 10000000 -b 2 -w I
  ~~~

- 扫描方案

  ~~~java
  public void scan() {
      CLIENT.scanAll(null, NAMESPACE, SET_NAME, new ScanCallback() {
          @Override
          public void scanCallback(Key key, Record record) throws AerospikeException {
              int i = NUMBER.incrementAndGet();
              if (i % 10000 == 0) {
                  log.info("[{}] records have been scan", i);
              }
          }
      });
  }
  ~~~

**结果**

1. 先插再扫

   扫描时约插入了130w左右的数据

   ![image-20210918172642131](https://gitee.com/primabrucexu/image/raw/main/20210918172642.png)

2. 先扫再插

   扫描前数据量：13237238

   ![image-20210918173123726](https://gitee.com/primabrucexu/image/raw/main/20210918173123.png)

   扫描结果

   ![image-20210918174137081](https://gitee.com/primabrucexu/image/raw/main/20210918174137.png)

   扫描结束时插入数据量

   ![image-20210918174204419](https://gitee.com/primabrucexu/image/raw/main/20210918174204.png)

   ![image-20210918174237961](https://gitee.com/primabrucexu/image/raw/main/20210918174238.png)