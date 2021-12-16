package cn.pbx.pattern.creation.simpleFactory.factory.impl;

import cn.pbx.pattern.creation.simpleFactory.factory.DataBasePool;

/**
 * @author BruceXu
 * @date 2021/4/21
 */
public class MysqlPool implements DataBasePool {
    @Override
    public void info() {
        System.out.println("初始化了MySQL连接池");
    }
}
