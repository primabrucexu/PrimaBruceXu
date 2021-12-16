package cn.pbx.pattern.behavior.observer;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class Miner implements Observer {

    String info() {
        return "矿工对币价变化反映：";
    }

    @Override
    public void reactForRise() {
        info();
        System.out.println(info() + "马上发财了");
    }

    @Override
    public void reactForFall() {
        info();
        System.out.println(info() + "马上破产了");
    }
}
