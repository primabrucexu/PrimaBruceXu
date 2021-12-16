package cn.pbx.pattern.creation.abstractFactory;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class HaierWashingMachine implements WashingMachine {
    @Override
    public void laundry() {
        System.out.println("海尔洗衣机洗衣");
    }
}
