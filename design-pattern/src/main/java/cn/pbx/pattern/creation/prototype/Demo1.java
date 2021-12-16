package cn.pbx.pattern.creation.prototype;

/**
 * @author BruceXu
 * @date 2021/6/1
 */
public class Demo1 {
    public static void main(String[] args) throws CloneNotSupportedException {
        User u1 = new User("张三", 10, new Hometown("浙江", "杭州"));
        User u2 = u1.clone();
        System.out.println("===== u1 =====");
        System.out.println(u1.getHometown());
        System.out.println(u1.getHometown().hashCode());
        System.out.println("===== u2 =====");
        System.out.println(u2.getHometown());
        System.out.println(u2.getHometown().hashCode());
    }
}
