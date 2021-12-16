package cn.pbx.pattern.behavior.observer;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public class Player implements Observer {

    String info() {
        return "玩家对币价变化反映：";
    }

    @Override
    public void reactForRise() {
        System.out.println(info() + "艹，显卡又贵了");
    }

    @Override
    public void reactForFall() {
        System.out.println(info() + "舒服了，显卡降价了");
    }
}
