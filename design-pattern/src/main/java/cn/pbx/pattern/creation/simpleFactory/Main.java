package cn.pbx.pattern.creation.simpleFactory;

import cn.pbx.pattern.creation.simpleFactory.factory.DataBaseFactory;
import cn.pbx.pattern.creation.simpleFactory.factory.DataBasePool;

/**
 * @author BruceXu
 * @date 2021/4/21
 */
public class Main {
    public static void main(String[] args) {
        DataBaseFactory factory = new DataBaseFactory();
        DataBasePool pool1 = factory.getPool("mysql");
        pool1.info();
        DataBasePool pool2 = factory.getPool("oracle");
        pool2.info();
    }
}
