package cn.pbx.week.single.week290;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author Bruce Xu
 * @date 2022/7/4
 */
public class No2249 {
    public int countLatticePoints(int[][] circles) {
        Set<Point> set = new HashSet<>();
        for (int[] circle : circles) {
            int r = circle[2];
            int ds = r * r;
            int x = circle[0];
            int y = circle[1];
            Point centre = new Point(x, y);
            for (int i = x - r; i <= x + r; i++) {
                for (int j = y - r; j <= y + r; j++) {
                    Point p = new Point(i, j);
                    if (centre.distanceSquare(p) <= ds) {
                        set.add(p);
                    }
                }
            }
        }

        return set.size();
    }

    static class Point {
        int x;
        int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
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

        public int distanceSquare(Point point) {
            int gapx = point.x - x;
            int gapy = point.y - y;
            return gapx * gapx + gapy * gapy;
        }

    }

}
