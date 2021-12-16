package cn.pbx.pattern.behavior.template;

/**
 * @author BruceXu
 * @date 2021/9/26
 */
public class LeeSin extends Hero {

    public LeeSin() {
        super("盲僧");
    }

    @Override
    public void Q() {
        System.out.println("天音波");
    }

    @Override
    public void W() {
        System.out.println("金钟罩");
    }

    @Override
    public void E() {
        System.out.println("断筋掌");
    }

    @Override
    public void R() {
        System.out.println("神龙摆尾");
    }
}
