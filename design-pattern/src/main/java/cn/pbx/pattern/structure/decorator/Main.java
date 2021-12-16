package cn.pbx.pattern.structure.decorator;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
public class Main {


    public static void main(String[] args) {
        ColorShapeDecorator circleDecorator = new ColorShapeDecorator(new Circle());
        circleDecorator.setColor("red");
        circleDecorator.info();

        ColorShapeDecorator triangleDecorator = new ColorShapeDecorator(new Triangle());
        triangleDecorator.setColor("blue");
        triangleDecorator.info();
    }
}
