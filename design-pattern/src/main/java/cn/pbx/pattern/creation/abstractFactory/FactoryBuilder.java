package cn.pbx.pattern.creation.abstractFactory;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class FactoryBuilder {

    public static Factory getFactory(FactoryEnum factoryEnum) {
        switch (factoryEnum) {
            case TCL:
                return new TclFactory();
            case Haier:
                return new HaierFactory();
            default:
                return null;
        }
    }

}
