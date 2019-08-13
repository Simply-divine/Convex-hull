package Week2Coursera;

import java.util.*;
 class Point2 implements Comparable<Point2> {
    public final Comparator<Point2> POLAR_ORDER = new PolarOrder();
    final double x;
    final double y;
    public Point2(double x, double y) {
        if (Double.isInfinite(x) || Double.isInfinite(y)) {
            throw new IllegalArgumentException("it has got no bounds");
        }
        if (Double.isNaN(x) || Double.isNaN(y)) {
            throw new IllegalArgumentException("it is not a number");
        }
        if (x == 0.0) {
            x = 0.0;
        }
        if (y == 0.0) {
            y = 0.0;
        }
        this.x = x;
        this.y = y;
    }

    public static int ccw(Point2 a, Point2 b, Point2 c) {
        double area = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (area < 0) return -1;
        if (area > 0) return 1;
        else return 0;
    }


    @Override
    public int compareTo(Point2 that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;
        return 0;
    }

    private class PolarOrder implements Comparator<Point2> {

        @Override
        public int compare(Point2 q1, Point2 q2) {

            double dy1 = q1.y - y;
            double dy2 = q2.y - y;
            double dx1 = q1.x - x;
            double dx2 = q2.x - x;
            if (dy1 >= 0 && dy2 < 0) {
                return -1;
            } else if (dy2 >= 0 && dy1 < 0) {
                return 1;
            } else if (dy1 == 0 && dy2 == 0) {
                if (dx1 >= 0 && dx2 < 0) return -1;
                if (dx2 >= 0 && dx1 < 0) return 1;
                else return 0;
            } else {
                return -ccw(Point2.this, q1, q2);
            }
        }

    }
    public  double distanceTo(Point2 that){
        return Math.sqrt((this.x-that.x)*(this.x-that.x)+(this.y-that.y)*(this.y-that.y));
    }

}


public class PebblesGame {

    public static double perimeter(Point2 a[]) {
        double sum = 0.0;
        int i;
        for ( i = 0; i < a.length; i++) {
            if (i == a.length-1) {
               // System.out.println("a["+i+"]="+a[i]+"distance last="+a[i].distanceTo(a[0]));
                sum =sum+ a[i].distanceTo(a[0]);
             //   System.out.println("current sum="+sum);
            }else {
           //     System.out.println("distance->"+a[i].distanceTo(a[i + 1]));
                sum = sum + a[i].distanceTo(a[i + 1]);
         //       System.out.println("current sum"+sum);
            }
        }
        return sum;
    }

    private Stack<Point2> hull = new Stack<>();
    PebblesGame(List<Point2> pts) {
        int N = pts.size();
        Point2[] points=new Point2[N];
        pts.toArray(points);
        Arrays.sort(points);
        Arrays.sort(points, 1, N, points[0].POLAR_ORDER);
        hull.push(points[0]);
        int k;
        for (k = 1; k < N; k++) {
            if (!points[0].equals(points[k])) {
                break;
            }
        }
        if (k == N) return;
        int k2;
        for (k2 = k + 1; k2 < N; k2++) {
            if (Point2.ccw(points[0], points[k], points[k2]) != 0)
                break;
        }
       // System.out.println("k2=" + k2);
        hull.push(points[k2 - 1]);
        for (int i = k2; i < N; i++) {
            Point2 top = hull.pop();
            while (Point2.ccw(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }


    }
    public static void main(String[] args) {
        Scanner scanner =new Scanner(System.in);
        int testCases=scanner.nextInt();
        for (int i = 0; i <testCases ; i++) {
            int N=scanner.nextInt();
            int M=scanner.nextInt();
            int[] a=new int[N];
            List<Point2> p=new ArrayList<>();
            //Point2[] p=new Point2[N*(N-1)];
            for (int j = 0; j <N ; j++) {
                a[j]=scanner.nextInt();

            }
            for (int j = 0; j < N; j++) {
                for (int k = 0; k <N ; k++) {
                    if(j!=k){
                        p.add(new Point2(a[j],a[k]));
                    }
                }
            }
          //  double sum1=perimeter(p);
         //   System.out.println("sum1="+sum1);
            PebblesGame pebblesGame=new PebblesGame(p);
            Stack<Point2> s=pebblesGame.hull;
            int q=s.size();
            Point2 points[]=new Point2[q];
            s.toArray(points);
            double sum=perimeter(points);
            long sumfinal= (long)(sum)+1;
            System.out.println(sumfinal*M);
       //     System.out.println("Sum="+sum);
        }

    }
}
