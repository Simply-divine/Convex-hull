 package Week2Coursera;

 import javax.swing.*;
 import java.awt.*;
 import java.util.Arrays;
 import java.util.Scanner;
 import java.util.Stack;


 public class ConvexHull extends JPanel {
    int xpoints[],ypoint[],counter = 0;
    private Stack<Point2D> hull = new Stack<>();
    public void init()
    {
        System.out.println("enter number of points");
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        double[] x = new double[n];
        double[] y = new double[n];
        Point2D[] p = new Point2D[n];
        System.out.println("Enter the (x,y) coordinates of points");
        for (int i = 0; i < n; i++) {
            x[i] = scanner.nextDouble()*75;
            y[i] = scanner.nextDouble()*75;
            p[i] = new Point2D(x[i], y[i]);
        }
        ConvexHull c = new ConvexHull(p);
        int size = c.hull.size();
        xpoints = new int[size];
        ypoint = new int[size];
        for (Point2D p1 : c.hull()) {
            System.out.println(p1);
            xpoints[counter] = ((int)p1.x);
            ypoint[counter] = ((int)p1.y);
            counter++;
        }
    }
    public void paintComponent(Graphics g)
    {
        int numberofpoints = counter;
        g.setColor(Color.blue);
        g.drawPolygon(xpoints, ypoint, numberofpoints);
    }
    ConvexHull(){

    }
    ConvexHull(Point2D[] pts) {
        int N = pts.length;
        Point2D[] points = new Point2D[N];
        for (int i = 0; i < N; i++) {
            points[i] = pts[i];
        }
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
            if (Point2D.ccw(points[0], points[k], points[k2]) != 0)
                break;
        }
        System.out.println("k2=" + k2);
        hull.push(points[k2 - 1]);
        for (int i = k2; i < N; i++) {
            Point2D top = hull.pop();
            while (Point2D.ccw(hull.peek(), top, points[i]) <= 0) {
                top = hull.pop();
            }
            hull.push(top);
            hull.push(points[i]);
        }
    }

    public static void main(String[] args) {
        ConvexHull c = new ConvexHull();
        c.init();
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Draw Polygon");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBackground(Color.white);
        frame.setSize(300, 200);
        frame.add(c);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public Iterable<Point2D> hull() {
        Stack<Point2D> s = new Stack<>();
        for (Point2D p : hull) {
            s.push(p);
        }
        return s;
    }

}