/*************************************************************************
 *  Compilation:  javac NearestNeighborVisualizer.java
 *  Execution:    java NearestNeighborVisualizer inputs_10.txt
 *  Dependencies: PointSET.java KdTree.java Point2D.java In.java StdDraw.java
 *
 *  Read points from a file (specified as a command-line argument) and
 *  draw to standard draw. Highlight the closest point to the mouse.
 *
 *  The nearest neighbor according to the brute-force algorithm is drawn
 *  in red; the nearest neighbor using the kd-tree algorithm is drawn in blue.
 *
 *************************************************************************/

public class NearestNeighborVisualizer {

    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        //PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        int c = 0;
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            c=c+1;
            if(c==7)
                 System.out.println("\n");
            kdtree.insert(p);
           // brute.insert(p);
        }
        
        Point2D p1 = new Point2D(0.206107, 0.904508);
        
        System.out.println("P1" + kdtree.contains(p1)); 

        Point2D p2 = new Point2D(0.216107, 0.914508);

        System.out.println("P2" + kdtree.contains(p2)); 

        //kdtree.print();
        System.out.println("\n");
        RectHV rect = new RectHV(0.0,0.1,1,1.00001);
        Iterable<Point2D> points = 
          kdtree.range(rect);
         
        
        for(Point2D p: points){
            System.out.println(p);
        }
        
//        Point2D query = new Point2D(0.9, 0.9);
//        Point2D nearPoint = kdtree.nearest(query);
        
        //System.out.println("nearest point:" + nearPoint.toString() + "\n");
        //System.out.println("distance:" );

        
        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

           // Point2D query = new Point2D(0.9, 0.9);
            // draw all of the points
            
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            kdtree.draw();
           
            /*
            // draw in red the nearest neighbor according to the brute-force algorithm
            StdDraw.setPenRadius(.03);
            StdDraw.setPenColor(StdDraw.RED);
            brute.nearest(query).draw();
            */ 
            
            // draw in blue the nearest neighbor according to the kd-tree algorithm
            StdDraw.setPenRadius(.02);
            StdDraw.setPenColor(StdDraw.BLUE);
            kdtree.nearest(query).draw();
            StdDraw.show(0);
            StdDraw.show(40);
        }
    
    
    }
}