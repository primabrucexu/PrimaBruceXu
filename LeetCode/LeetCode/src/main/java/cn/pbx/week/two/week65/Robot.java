package cn.pbx.week.two.week65;

import java.util.Arrays;

/**
 * @author Bruce Xu
 * @date 2022/6/29
 */
// fixme 超时 119 / 142 个通过测试用例
public class Robot {

    int width;
    int height;

    int curX;
    int curY;

    Direction direction = Direction.East;

    public Robot(int width, int height) {
        this.width = width - 1;
        this.height = height - 1;
        curX = 0;
        curY = 0;
    }

    public static void main(String[] args) {
        Robot robot = new Robot(6, 3);
        robot.step(2);
        System.out.println(Arrays.toString(robot.getPos()));
        System.out.println(robot.getDir());
        robot.step(2);
        System.out.println(Arrays.toString(robot.getPos()));
        System.out.println(robot.getDir());
        robot.step(2);
        System.out.println(Arrays.toString(robot.getPos()));
        System.out.println(robot.getDir());
        robot.step(1);
        System.out.println(Arrays.toString(robot.getPos()));
        System.out.println(robot.getDir());
        robot.step(4);
        System.out.println(Arrays.toString(robot.getPos()));
        System.out.println(robot.getDir());
    }

    public void step(int num) {
        switch (direction) {
            case East:
                if (curX + num <= width) {
                    curX += num;
                    return;
                }
                num = num - (width - curX);
                curX = width;
                this.direction = direction.next();
                step(num);
                break;
            case South:
                if (curY - num >= 0) {
                    curY -= num;
                    return;
                }
                num -= curY;
                curY = 0;
                this.direction = direction.next();
                step(num);
                break;
            case West:
                if (curX - num >= 0) {
                    curX -= num;
                    return;
                }
                num -= curX;
                curX = 0;
                this.direction = direction.next();
                step(num);
                return;
            case North:
                if (curY + num <= height) {
                    curY += num;
                    return;
                }
                num = num - (height - curY);
                curY = height;
                this.direction = direction.next();
                step(num);
                return;
            default:
                return;
        }
    }

    public int[] getPos() {
        return new int[]{curX, curY};
    }

    public String getDir() {
        return direction.name();
    }

    enum Direction {
        East,
        West,
        North,
        South;

        public Direction next() {
            switch (this) {
                case East:
                    return North;
                case West:
                    return South;
                case North:
                    return West;
                case South:
                    return East;
                default:
                    return null;
            }
        }

    }

}
