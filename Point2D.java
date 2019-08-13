package Week2Coursera;

import java.util.Comparator;

public class Point2D implements Comparable<Point2D> {
   public final Comparator<Point2D> POLAR_ORDER = new PolarOrder();
   final double x;
   final double y;
   public Point2D(double x, double y) {
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

   public static int ccw(Point2D a, Point2D b, Point2D c) {
       double area = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
       if (area < 0) return -1;
       if (area > 0) return 1;
       else return 0;
   }

   @Override
   public String toString() {
       return "Point2D{" +
               "x=" + x +
               ", y=" + y +
               '}';
   }

   @Override
   public int compareTo(Point2D that) {
       if (this.y < that.y) return -1;
       if (this.y > that.y) return 1;
       if (this.x < that.x) return -1;
       if (this.x > that.x) return 1;
       return 0;
   }

   private class PolarOrder implements Comparator<Point2D> {

       @Override
       public int compare(Point2D q1, Point2D q2) {

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
               return -ccw(Point2D.this, q1, q2);
           }
       }

   }
    public  double distanceTo(Point2D that){
        return Math.sqrt((this.x-that.x)*(this.x-that.x)+(this.y-that.y)*(this.y-that.y));
    }

}
