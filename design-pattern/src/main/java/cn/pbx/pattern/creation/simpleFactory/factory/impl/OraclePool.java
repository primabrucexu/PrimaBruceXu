package cn.pbx.pattern.creation.simpleFactory.factory.impl;

import cn.pbx.pattern.creation.simpleFactory.factory.DataBasePool;

/**
 * @author BruceXu
 * @date 2021/4/21
 */
public class OraclePool implements DataBasePool {
    @Override
    public void info() {
        System.out.printf("初始化Oracle连接池");
    }
}
