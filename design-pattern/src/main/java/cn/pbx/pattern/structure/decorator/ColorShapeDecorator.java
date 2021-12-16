package cn.pbx.pattern.structure.decorator;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
public class ColorShapeDecorator extends AbstractShapeDecorator {

    private String color;

    public ColorShapeDecorator(Shape shape) {
        super(shape);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public void info() {
        super.info();
        System.out.println("他的颜色是" + getColor());
    }
}
