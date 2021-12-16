package cn.pbx.pattern.creation.factoryMethod;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
public class ConnectionFactory implements IFactory {

    private final IFactory factory;

    public ConnectionFactory(IFactory factory) {
        this.factory = factory;
    }

    @Override
    public Connection build() {
        return factory.build();
    }
}
