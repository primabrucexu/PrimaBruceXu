package cn.pbx.pattern.behavior.observer;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class Scalper implements Observer {

    String info() {
        return "黄牛对币价变化反映：";
    }

    @Override
    public void reactForRise() {
        info();
        System.out.println(info() + "舒服了，又能多赚钱了");
    }

    @Override
    public void reactForFall() {
        info();
        System.out.println(info() + "淦，又没钱赚了");
    }
}
