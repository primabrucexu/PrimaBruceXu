package cn.pbx.pattern.structure.decorator;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
public class Triangle implements Shape {
    @Override
    public void info() {
        System.out.println("这是一个三角形");
    }
}
