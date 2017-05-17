import java.util.Vector;
import java.util.*;


public class Fast {
    public static void main(String[] args){
        
        boolean debug = false;
        // read the file
        In in = new In(args[0]);
        int N = in.readInt();
        
        // could I have used array here.. instead of vector.. no need for a vector 
        // since N is known in advance
        // Point [] points2 = new Point[N]; // why array isn't working
        // Arrays.sort(points2);
        
        // also could have used list. has shorter commands than vector.. 
        // List<Point> myList = new ArrayList<Point>(); 
        // could also use List but list is an abstract class..
        // Collections.sort(myList);
        
        Vector<Point> orig_points = new Vector<Point>();
        for (int i = 0; i < N; i++){
            int x = in.readInt();
            int y = in.readInt();
            orig_points.add(new Point(x,y));
        };
        
        if(debug){
            for(int k=0; k<N; ++k){
                StdOut.println(orig_points.elementAt(k).toString() + "->");
            }; 
            StdOut.println("\n");
        };
        
        // the final list of colinear points. 
        // we need set since we don't want duplicate list of points. 
        // we need sortedset inside since points need to be arranged otherwise 
        // difference in ordering will create duplicate sets. 
        Set<SortedSet<Point>> unique_sets = new HashSet<SortedSet<Point>>(); 
        
        for(int i=0; i<N; ++i)
        {
            // for each point P0, create a new sorted array (based on slope wrt P0)
            Point P0 = orig_points.elementAt(i);        
            if(debug){StdOut.println(P0.toString() + "->" );}
            
            // sort will rearrange the order. so create another copy and sort it  
            Vector<Point> points = new Vector<Point>(orig_points);
            Collections.sort(points,P0.SLOPE_ORDER);

            //print the sorted order.
            if(debug){   
                for(int k=0; k<N; ++k){
                    StdOut.println(points.elementAt(k).toString() + "->");
                }; 
                StdOut.println("\n");
            }
            
            // stores subsets of colinear points.  
            SortedSet<Point> colinear_set = new TreeSet<Point>();
       
            // for each point iterate through the list to find the number of points
            // with same slope and add it to a colinear set. 
            for(int j=1; j<N-1; ++j){  
                // iterate through the list  
                Point p1 = points.elementAt(j); 
                Point p2 = points.elementAt(j+1);
                //if slope of adjacent point is equal wrt to P0 add in set
                if( P0.slopeTo(p1)==P0.slopeTo(p2) ){
                    colinear_set.add(p1);
                    colinear_set.add(p2);
                }
                else{
                    // if different check the size of the colinear set.  
                    //if <3 discard the colinear_set.
                    if(colinear_set.size()<3){
                        colinear_set.clear();  
                    }
                    else{
                        // also add the point PO and add it to unique list set 
                        colinear_set.add(P0);
                        // colinear_set has natural ordering so unique sets only have unique copies
                        unique_sets.add(colinear_set); 
                        // start a new list to store new set. 
                        colinear_set = new TreeSet<Point>();
                    }
                } 
            }; //end of for j=1;
            
        }; 
        
        // unique_sets contains all the unique colinear set 
        for(Iterator<SortedSet<Point> > it = unique_sets.iterator(); it.hasNext();) {
            SortedSet<Point> tmp = it.next();
            System.out.println( tmp.size() + " " ); 
            for(Iterator<Point>  it2 = tmp.iterator(); it2.hasNext();) {
                System.out.print(it2.next()+ " " );
            };
              System.out.println();
        };
    }//end of i=0;
    
   
}