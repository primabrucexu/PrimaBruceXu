package cn.pbx.pattern.behavior.strategy;

import cn.pbx.pattern.behavior.strategy.uuid.UuidStrategy;
import cn.pbx.pattern.behavior.strategy.uuid.impl.RandomUuid;

/**
 * @author BruceXu
 * @date 2021/4/23
 */
public class Main {
    public static void main(String[] args) {
        UuidStrategy uuidStrategy = new UuidStrategy(new RandomUuid());
        System.out.println(uuidStrategy.getUuid());
    }
}
