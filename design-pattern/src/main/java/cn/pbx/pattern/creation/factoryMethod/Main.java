package cn.pbx.pattern.creation.factoryMethod;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
public class Main {
    public static void main(String[] args) {
        ConnectionFactory f1 = new ConnectionFactory(new MysqlConnectionFactory());
        f1.build().connect();

        ConnectionFactory f2 = new ConnectionFactory(new RedisConnectionFactory());
        f2.build().connect();

        ConnectionFactory f3 = new ConnectionFactory(new IFactory() {
            @Override
            public Connection build() {
                return new Connection() {
                    @Override
                    public void connect() {
                        System.out.println("新增连接");
                    }
                };
            }
        });
        f3.build().connect();
    }
}
