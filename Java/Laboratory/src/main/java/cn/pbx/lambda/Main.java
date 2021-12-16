package cn.pbx.lambda;

/**
 * @author BruceXu
 * @date 2021/12/16
 */
public class Main {
    public static void main(String[] args) {
        Runnable r1=null;
        for(int i=0; i<2; i++) {
            Runnable r2=System::gc;
            if(r1==null) {
                r1=r2;
            }
            else {
                System.out.println(r1==r2? "shared": "unshared");
            }
        }
    }
}
