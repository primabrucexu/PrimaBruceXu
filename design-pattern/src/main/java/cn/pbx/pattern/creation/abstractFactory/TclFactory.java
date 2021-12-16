package cn.pbx.pattern.creation.abstractFactory;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class TclFactory implements Factory {
    @Override
    public AirCondition buildAC() {
        return new TclAirCondition();
    }

    @Override
    public WashingMachine buildWM() {
        return new TclWashingMachine();
    }
}
