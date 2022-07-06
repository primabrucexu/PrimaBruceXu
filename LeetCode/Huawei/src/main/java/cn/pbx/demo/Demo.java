package cn.pbx.demo;

/**
 * @author Bruce Xu
 * @date 2022/7/5
 */
public class Demo {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toBinaryString(128));
        sb.append(Integer.toBinaryString(255));
        sb.append(Integer.toBinaryString(255));
        sb.append(Integer.toBinaryString(255));
        System.out.println(sb.length());
        System.out.println(Long.parseLong(sb.toString(), 2));
    }
}
