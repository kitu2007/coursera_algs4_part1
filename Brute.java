import java.util.Vector;
import java.util.*;
public class Brute {
    public static void main(String[] args){
        
        // read the file
        In in = new In(args[0]);
        int N = in.readInt();
        Vector<Point> points = new Vector<Point>();
        for (int i = 0; i < N; i++){
            int x = in.readInt();
            int y = in.readInt();
            points.add(new Point(x,y));
        }
        Collections.sort(points);
         
        // select first point as pivot and then select the other 3 from the N-1
        for(int i=0; i<N; ++i)
        {
            for(int j=i+1; j<N; ++j)
            {
                for(int k=j+1; k<N; ++k)
                {
                    for(int l=k+1; l<N; ++l)
                    {
                        Point p0 = points.elementAt(i);
                        double slope1 = p0.slopeTo(points.elementAt(j));
                        double slope2 = p0.slopeTo(points.elementAt(k));
                        double slope3 = p0.slopeTo(points.elementAt(l));
                        if(slope1==slope2 && slope1==slope3){
                            StdOut.println( points.elementAt(i).toString() + "->" +
                                           points.elementAt(j).toString()  + "->" +
                                           points.elementAt(k).toString()  + "->" +
                                           points.elementAt(l).toString() );
                        }
                        
                    }
                }
            }
        }
        
    }
}