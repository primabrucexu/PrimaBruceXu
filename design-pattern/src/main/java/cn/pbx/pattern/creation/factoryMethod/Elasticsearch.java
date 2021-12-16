package cn.pbx.pattern.creation.factoryMethod;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
public class Elasticsearch implements Connection {
    @Override
    public void connect() {
        System.out.println("连接es");
    }
}
