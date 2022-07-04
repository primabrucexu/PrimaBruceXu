package cn.pbx.week.single.week290;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Bruce Xu
 * @date 2022/7/4
 */
public class No2250 {
    public static void main(String[] args) {
        No2250 demo = new No2250();
        int[][] rectangles = {
                {1, 2}, {2, 3}, {2, 5}
        };
        int[][] points = {
                {2, 1}, {1, 4}
        };
        System.out.println(Arrays.toString(demo.countRectangles(rectangles, points)));

    }

    // todo 超时，待优化
    public int[] countRectangles(int[][] rectangles, int[][] points) {
        List<Rectangle> list = Arrays.stream(rectangles).map(Rectangle::new).collect(Collectors.toList());
        int[] res = new int[points.length];
        int i = 0;
        for (int[] point : points) {
            Point p = new Point(point);
            int count = (int) list.stream().filter(o -> o.isXYInRectangle(p)).count();
            res[i++] = count;
        }

        return res;
    }

    static class Rectangle {

        int l;
        int h;

        Set<Point> points = new HashSet<>();

        public Rectangle(int[] array) {
            this.l = array[0];
            this.h = array[1];
            for (int i = 0; i <= l; i++) {
                for (int j = 0; j <= h; j++) {
                    points.add(new Point(i, j));
                }
            }
        }

        public boolean isXYInRectangle(Point point) {
            return points.contains(point);
        }

    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point(int[] array) {
            this.x = array[0];
            this.y = array[1];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Point point = (Point) o;
            return x == point.x && y == point.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }


}
