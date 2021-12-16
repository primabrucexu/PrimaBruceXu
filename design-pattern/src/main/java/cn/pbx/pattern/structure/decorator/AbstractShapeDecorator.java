package cn.pbx.pattern.structure.decorator;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
public abstract class AbstractShapeDecorator {

    protected Shape shape;

    public AbstractShapeDecorator(Shape shape) {
        this.shape = shape;
    }


    public void info() {
        shape.info();
    }

}
