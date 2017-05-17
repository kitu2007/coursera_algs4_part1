/*************************************************************************
 *  Compilation:  javac PointSET.java
 *
 *  Immutable point data type for points in the plane.
 *
 *************************************************************************/


import java.util.TreeSet;
import java.lang.Double;
import java.util.Iterator;

public class PointSET {
    
    private TreeSet<Point2D> set;
    // construct an empty set of points
    public PointSET(){
        //Point2D pt0 = new Point2D(0.0,0.0); 
        set = new TreeSet<Point2D>( );
    };
    
    // is the set empty?
    public boolean isEmpty(){
        return set.isEmpty();
    }
    
    // number of points in the set
    public int size(){
        return set.size();
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p){
        set.add(p);
        //setX.add(p);
    }
    
    // does the set contain the point p?
    public boolean contains(Point2D p){
        return set.contains(p);
    }
    
    // draw all of the points to standard draw
    public void draw(){
        for( Iterator<Point2D> it = set.iterator(); it.hasNext();){
            Point2D point = it.next();
            point.draw();
        }
    }
    
    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect){
                // simple iterate over the n points and check
        
            // this would be order n -- simple iterating over all the points in the set
            //iterate through the points in the set and return a stack of those lying
            // within the rectangle
            Stack<Point2D> points = new Stack<Point2D>();
            for( Iterator<Point2D> it = set.iterator();it.hasNext(); ){
                Point2D point = it.next();
                if(rect.contains(point)){
                    points.push(point);
                }
            }
          
          return points;
    }
   
    //     public Iterable<Point2D> range(RectHV rect){
 // if the tree was sorted with respect to X
//        if(false){
//            //get the values within xmin and xmax
//            Point2D elem1 = new Point2D(rect.xmin(),0.0); 
//            Point2D elem2 = new Point2D(rect.xmax(),0.0);
//            elem1 = setX.floor(elem1); 
//            elem2 = setX.ceiling(elem2); 
//            
//            SortedSet<Point2D> x_subset; 
//            x_subset = setX.subSet( elem1, elem2 );
//            //insert those elements in y-ordered sorting
//            TreeSet<Point2D> y_subset = new TreeSet<Point2D>( );
//            for( Iterator<Point2D> it = x_subset.iterator; it.hasNext(); ){
//                y_subset.add(it);
//            }
//            //select the points that lie witin y_min and y_max 
//            y_subset = y_subset.subSet(rect.ymin(),rect.ymax());
//        }
//}        

    
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p){

        Stack<Point2D> points = new Stack<Point2D>();    
        double min_dist = 1;
        Point2D min_point = new Point2D(0,0); 
        for( Iterator<Point2D> it = set.iterator();it.hasNext(); ){
            Point2D point = it.next();
            double dist = p.distanceSquaredTo(point);
            if(dist < min_dist){
                min_dist = dist;
                min_point = point; 
            }
        }
        return min_point;  
    }
}
        
    /*
        // get the nearest point greater than or equal to p.
        Point2D pt1 = set.ceiling(p);
        // get the nearest point less than or equal to p.
        Point2D pt2 = set.floor(p);
        
        //check is they are not null
        if(pt1==null && pt2==null)
            return null;
        else if(pt1==null && pt2!=null)
            return pt2;
        else if(pt2==null && pt1!=null)
            return pt1;
        else{
            // if not null return the nearer one
            if(p.distanceSquaredTo(pt1)< p.distanceSquaredTo(pt2)){
                return pt1;
            }
            else{
                return pt2;
            }
    
    double max = Double.POSITIVE_INFINITY;
         Point2D nearest_point = null;
         for(Iterator<Point2D> it = set.Iterator(); it.hasNext()){
         Point2D setPoint = set.next();
         double dist = p.distanceSquaredTo(setPoint);
         if( dist < max ){
         max = dist;
         nearest_point = setPoint;
         }
         }
         return nearest_point;
    
    */
