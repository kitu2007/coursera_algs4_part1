/*************************************************************************
 * Name:
 * Email:
 *
 * Compilation:  javac Point.java
 * Execution:
 * Dependencies: StdDraw.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

import java.util.Comparator;
import java.util.Vector;
import java.util.*;
import java.lang.Double;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new sortBySlope();       // YOUR DEFINITION HERE

    private class sortBySlope implements Comparator<Point>{
        public int compare(Point p1, Point p2){
            double diff = slopeTo(p1)-slopeTo(p2);
            if(diff==0)
                return 0; 
            else if(diff<0)
                return -1; 
            else 
                return 1;
        };
    }
    
    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {

        double denom = (that.x-x);
        double numerator = (that.y-y);
        if(denom==0 && numerator==0)
            return Double.NEGATIVE_INFINITY;
        if(denom==0)
            return Double.POSITIVE_INFINITY;
        if(numerator==0)
            return 0;
        return numerator/denom;
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        if(that.y==y && that.x==x)
            return 0;
        else if( y<that.y || (that.y==y && x<that.x) )
           return -1;
        else
            return 1; 
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        //read a list of points..
        Vector<Point> testPoints = new Vector<Point>();// = new ArrayList<Point>();
        testPoints.add(new Point(1,10));
        testPoints.add(new Point(2,2));
        testPoints.add(new Point(1,2));
        testPoints.add(new Point(10,5));
        StdDraw.setXscale(0, 20);
        StdDraw.setYscale(0, 20);
        for(int i=0; i<testPoints.size(); ++i ){
            testPoints.elementAt(i).draw();
        }
     
        for(int i=0; i<testPoints.size()-1; ++i ){
            double slope = testPoints.elementAt(i).slopeTo(testPoints.elementAt(i+1));
            // testPoints.elementAt(i).drawTo(testPoints.elementAt(i+1));
             StdOut.println("slope:" + slope);
        }
        
        Collections.sort(testPoints);
        for(int i=0; i<testPoints.size(); ++i ){
            StdOut.println(testPoints.elementAt(i).toString());
        };
        StdOut.println("\n");
        
        Point P0 = new Point(0,0); 
        Collections.sort(testPoints,P0.SLOPE_ORDER);
        for(int i=0; i<testPoints.size(); ++i ){
            StdOut.println(testPoints.elementAt(i).toString());
        };
        
    }
}
