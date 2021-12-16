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
public class User implements Cloneable {

    private String name;
    private int age;
    private Hometown hometown;

    @Override
    protected User clone() throws CloneNotSupportedException {
        User clone = (User) super.clone();
        clone.setHometown(hometown.clone());
        return clone;
    }
}
