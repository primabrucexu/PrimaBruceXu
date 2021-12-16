package cn.pbx.Algorithm.easy;

/**
 * @author BruceXu
 * @date 2020/9/26
 */
public class Solution_1114_printInorder {
    public static void main(String[] args) throws InterruptedException {
        Foo foo = new Foo();
        foo.first(new Runnable() {
            @Override
            public void run() {
                System.out.println("first");
            }
        });
        foo.second(new Runnable() {
            @Override
            public void run() {
                System.out.println("second");
            }
        });
        foo.third(new Runnable() {
            @Override
            public void run() {
                System.out.println("third");
            }
        });
    }


}

class Foo {

    private int flag = 1;

    public Foo() {

    }

    public void first(Runnable printFirst) throws InterruptedException {
        // printFirst.run() outputs "first". Do not change or remove this line.

        synchronized (this) {
            while (flag != 1) {
                this.wait();
            }
            printFirst.run();
            flag = 2;
            notifyAll();
        }
    }

    public void second(Runnable printSecond) throws InterruptedException {
        // printSecond.run() outputs "second". Do not change or remove this line.
        synchronized (this) {
            while (flag != 2) {
                this.wait();
            }
            printSecond.run();
            flag = 3;
            notifyAll();
        }
    }

    public void third(Runnable printThird) throws InterruptedException {
        // printThird.run() outputs "third". Do not change or remove this line.
        synchronized (this) {
            while (flag != 3) {
                this.wait();
            }
            printThird.run();
            flag = 1;
            notifyAll();
        }
    }
}
