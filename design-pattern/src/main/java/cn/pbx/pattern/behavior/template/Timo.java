package cn.pbx.pattern.behavior.template;

/**
 * @author BruceXu
 * @date 2021/9/26
 */
public class Timo extends Hero {

    public Timo() {
        super("提莫");
    }

    @Override
    public void Q() {
        System.out.println("致盲吹箭");
    }

    @Override
    public void W() {
        System.out.println("提莫快跑");
    }

    @Override
    public void E() {
        System.out.println("荼毒");
    }

    @Override
    public void R() {
        System.out.println("蘑菇炸弹");
    }
}
