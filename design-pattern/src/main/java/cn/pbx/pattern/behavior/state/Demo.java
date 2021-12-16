package cn.pbx.pattern.behavior.state;

/**
 * @author BruceXu
 * @date 2021/9/30
 */
public class Demo {
    public static void main(String[] args) {
        Context context = new Context();
        context.start();
        context.update(1);
        context.update(0);
        context.update(2);
        context.update(-1);
        context.update(2);
        context.update(1);
    }
}
