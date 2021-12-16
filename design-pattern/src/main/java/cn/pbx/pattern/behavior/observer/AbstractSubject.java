package cn.pbx.pattern.behavior.observer;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

/**
 * @author BruceXu
 * @date 2021/9/28
 */
public abstract class AbstractSubject implements Subject {

    protected Set<Observer> set;

    public AbstractSubject() {
        set = new HashSet<>();
    }

    public void add(Observer observer) {
        set.add(observer);
    }

    public void remove(Observer observer) {
        set.remove(observer);
    }

    public abstract void change(BigDecimal change);

    @Override
    public void higher() {
        set.forEach(Observer::reactForRise);
    }

    @Override
    public void lower() {
        set.forEach(Observer::reactForFall);
    }
}
