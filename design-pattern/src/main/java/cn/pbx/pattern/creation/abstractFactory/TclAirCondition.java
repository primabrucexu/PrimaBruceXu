package cn.pbx.pattern.creation.abstractFactory;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class TclAirCondition implements AirCondition {
    @Override
    public void cooling() {
        System.out.println("TCL空调制冷");
    }

    @Override
    public void heating() {
        System.out.println("TCL空调制热");
    }
}
