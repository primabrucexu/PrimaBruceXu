package cn.pbx.pattern.behavior.strategy.uuid;

/**
 * @author BruceXu
 * @date 2021/4/23
 */
public class UuidStrategy {

    private final Uuid uuid;

    public UuidStrategy(Uuid uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid.getUuid();
    }

}
