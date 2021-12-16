package cn.pbx.pattern.structure.proxy;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
public class Coder implements Programme {
    @Override
    public void code(String type) {
        System.out.println("程序猿写代码" + "，写的是" + type);
    }
}
