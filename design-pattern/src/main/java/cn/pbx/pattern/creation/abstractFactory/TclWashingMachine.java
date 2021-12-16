package cn.pbx.pattern.creation.abstractFactory;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class TclWashingMachine implements WashingMachine {
    @Override
    public void laundry() {
        System.out.println("TCL洗衣机洗衣");
    }
}
