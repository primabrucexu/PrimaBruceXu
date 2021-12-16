package cn.pbx.pattern.behavior.strategy.uuid.impl;

import cn.pbx.pattern.behavior.strategy.uuid.Uuid;

/**
 * @author BruceXu
 * @date 2021/4/23
 */
public class TimeUuid implements Uuid {
    @Override
    public String getUuid() {
        return "时间戳UUID";
    }
}
