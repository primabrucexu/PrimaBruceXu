package cn.pbx.week.two.week19;

/**
 * @author Bruce Xu
 * @date 2022/7/4
 */
public class No1344 {
    public double angleClock(int hour, int minutes) {
        // 时针每小时走30度，每分钟走0.5度
        // 分针每小时走360度，每分钟走6度，
        double angle = 0d;
        if (hour == 12) {
            hour = 0;
        }
        if (minutes == 60) {
            minutes = 0;
        }
        double m = minutes * 6.0;
        double h = hour * 30 + minutes * 0.5;
        angle = Math.abs(h - m);
        if (angle > 180) {
            angle = 360 - angle;
        }

        return angle;
    }
}
