package cn.pbx.pattern.creation.abstractFactory;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class HaierFactory implements Factory {
    @Override
    public AirCondition buildAC() {
        return new HaierAirCondition();
    }

    @Override
    public WashingMachine buildWM() {
        return new HaierWashingMachine();
    }
}
