package cn.pbx.pattern.creation.builder.product;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author BruceXu
 * @date 2021/9/27
 */
@Data
@Accessors(chain = true)

public class GPU {

    private String brand;
    private String model;
    private Integer graphicMemory;
    private String memoryType;
    private Integer CUDA;
    private Boolean RTX;
    private Integer TDP;

}
