package cn.pbx.pattern.creation.abstractFactory;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println("获取海尔系列产品工厂");
        Factory haier = FactoryBuilder.getFactory(FactoryEnum.Haier);
        AirCondition ac1 = haier.buildAC();
        WashingMachine wm1 = haier.buildWM();
        ac1.cooling();
        ac1.heating();
        wm1.laundry();
        System.out.println("======================");
        System.out.println("获取TCL系列工厂");
        Factory tcl = FactoryBuilder.getFactory(FactoryEnum.TCL);
        AirCondition ac2 = tcl.buildAC();
        WashingMachine wm2 = tcl.buildWM();
        ac2.cooling();
        ac2.heating();
        wm2.laundry();
    }
}
