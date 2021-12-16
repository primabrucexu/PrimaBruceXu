package cn.pbx.pattern.creation.factoryMethod;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
public class RedisConnectionFactory implements IFactory {
    @Override
    public Connection build() {
        return new Redis();
    }
}
