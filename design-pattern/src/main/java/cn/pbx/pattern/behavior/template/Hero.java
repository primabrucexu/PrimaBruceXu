package cn.pbx.pattern.behavior.template;

/**
 * @author BruceXu
 * @date 2021/9/26
 */

public abstract class Hero {

    protected String name;

    public Hero(String name) {
        this.name = name;
    }

    public abstract void Q();

    public abstract void W();

    public abstract void E();

    public abstract void R();

    public void play() {
        System.out.println("英雄" + name + "有如下技能");
        Q();
        W();
        E();
        R();
    }

}
