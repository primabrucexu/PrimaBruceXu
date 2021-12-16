package cn.pbx.pattern.creation.prototype;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
@Getter
@Setter
@AllArgsConstructor
public class Hometown implements Cloneable {

    private String province;
    private String city;

    @Override
    protected Hometown clone() throws CloneNotSupportedException {
        return (Hometown) super.clone();
    }
}
