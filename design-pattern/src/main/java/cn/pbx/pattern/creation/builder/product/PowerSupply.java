package cn.pbx.pattern.creation.builder.product;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author BruceXu
 * @date 2021/9/27
 */
@Data
@Accessors(chain = true)
public class PowerSupply {

    private String level;
    private Integer power;

}
