package cn.pbx.pattern.behavior.strategy.uuid.impl;

import cn.pbx.pattern.behavior.strategy.uuid.Uuid;

/**
 * @author BruceXu
 * @date 2021/4/23
 */
public class SnowUuid implements Uuid {
    @Override
    public String getUuid() {
        return "雪花算法UUID";
    }
}
