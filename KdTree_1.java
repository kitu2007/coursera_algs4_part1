/*************************************************************************
 *  Compilation:  javac PointSET.java
 *
 *  Immutable point data type for points in the plane.
 *
 *************************************************************************/


import java.util.TreeSet;
import java.lang.Double;
import java.util.Iterator;

public class KdTree {
    
    Node root;

    //----- Node struct ----------------
    private static class Node {
        
        private Point2D p;      // the point
        //private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        int depth;
        
        public Node(Point2D _p) {
            this.p = _p;
            //this.rect = _rect;
            this.lb = null;
            this.rt = null; 
        }
    
        public Node(Point2D _p, Node  _lb , Node _rt ){
             this.p = _p;
             this.lb = _lb;
            this.rt = _rt;
        }
    }

    
    //----------------------------
    // construct an empty set of points
    public KdTree(){
        root = null;
    };
    
    //----------------------------------
    // Simple get and set functions
    
    
    // is the set empty?
    public boolean isEmpty(){
        return (root==null);
    }
    
    
    // number of points in the set
    public int size(){
        // traverse the tree
        if(isEmpty())
            return 0;
       return size(root);
    }

    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p){
        if(root==null){
            root = new Node(p);
            root.depth = 0;
            return;
        }
        insert(root,p,0);
        
       // assert check();
    }
    
    // does the set contain the point p?
    public boolean contains(Point2D p){
        if(root==null)
            return false;
        return contains(root, p, 0);
    }
    
    
    // recursive function for computing size.
    // size = sizeof(left) + sizeof(right) + 1;
    private int size(Node currNode){

        int size = 0;
        if(currNode==null) return 0;
        size += size(currNode.lb); //size of left node
        size += size(currNode.rt); //size of right node
        size +=1; // side of the current node (1)
        
        return size;
    };
    
    
    // recursive insertion function
    // swaps the x and y value of the node and the point p to
    // change the order of comparison (Y order to X order)
    private Node insert(Node node, Point2D p, int levelIndex ) {
        
        if(node==null) return new Node(p);
        // create new tmp variables
        Point2D _p = new Point2D(p.x(),p.y());
        Point2D node_p = new Point2D(node.p.x(), node.p.y());
        // swapping levelIndex is 0 (alternative between 1 and 0)
        if(levelIndex==0){
            _p = swapXY(_p);
            node_p = swapXY(node_p);
        }
        
        // the temp _p and node_p are just for comparison
        int cmp =  _p.compareTo(node_p);
        
        if(cmp < 0){
            node.lb = insert(node.lb,p,1-levelIndex);
        }
        else if(cmp > 0){
            node.rt = insert(node.rt,p,1-levelIndex);
        }
        else {
            node.p=p;
        }
       return node;
    };

    // helper function for swapping X and Y values
    public Point2D swapXY(Point2D p){
        Point2D p1= new Point2D(p.y(),p.x());
        return p1;
    }

    // inplace swap would work too.. 
    public void swapXY1(Point2D p){
        /* this would work if x and y were not private and final variables 
         double tmp = p.x();
         p.x() = p.y(); 
         p.y() = tmp;
         */ 
    }

    // contains
    private boolean contains(Node node, Point2D p, int levelIndex ) {

        if(node==null) return false;

        Point2D _p = new Point2D(p.x(),p.y());
        Point2D node_p = new Point2D(node.p.x(), node.p.y());
        if(levelIndex==0){
            _p = swapXY(_p);
            node_p = swapXY(node_p);
        }
        
        // if cmp is less than node value then consider the left subtree
        int cmp =  _p.compareTo(node_p);

        if(cmp < 0 ) {
            return contains(node.lb,p,1-levelIndex); //1-levelIndex makes it alternate
        }
        else if( cmp > 0 ){
            return contains(node.rt,p,1-levelIndex);
        }
        else{
            return true;
        }
        
    }
    
    
    
    // FLOOR AND CEILING OPERATIONS
    //-----------------------------------------------
    
    // find node that is less than p but closest 
    private Node floor(Node node, Point2D p, int levelIndex){
        if(node==null) return null;

        Point2D _p = new Point2D(p.x(),p.y());
        Point2D node_p = new Point2D(node.p.x(), node.p.y());
        if(levelIndex==0){
            _p = swapXY(_p);
            node_p = swapXY(node_p);
        }
        
        int cmp =  _p.compareTo(node_p);
        if(cmp==0) return node;
        if(cmp<0){
            // key is less than node i.e  
            // node is greater than key..so traverse left.
            return floor(node.lb,p,1-levelIndex);
        }
        // if node is less than key/point.. then the current node may be the floor
        // until unless there is floor exist in the right subtree
        Node t=floor(node.rt,p,1-levelIndex);
        if(t!=null)
             return t;
        return node; 
    };
    
    // find node that is greater than p but closest
    private Node ceiling(Node node, Point2D p, int levelIndex){
        if(node==null) return null;
        
        Point2D _p = new Point2D(p.x(),p.y());
        Point2D node_p = new Point2D(node.p.x(), node.p.y());
        if(levelIndex==0){
            _p = swapXY(_p);
            node_p = swapXY(node_p);
        } 
        
        int cmp =  _p.compareTo(node_p);
        if(cmp==0) return node;
        // if key is greater than node. ie. node is less than key 
        // look into the right subtree
        if(cmp>0){
            return ceiling(node.rt,p,1-levelIndex);
        }
        // case when cmp<0 the current node is greater than key 
        // may be the ceiling until unless
        // something smaller exists in the left subtree.
         Node t = ceiling(node.lb,p,1-levelIndex);
         if(t!=null){
              return t;
         }
         return node;
    };
    
    
    // need to have an inorder iterator
    public void draw(Node node){
        if(node==null) return ;
        draw(node.lb);
        node.p.draw();
        draw(node.rt);
    };
 
    public void draw(){
     if(root==null) return;
     draw(root); 
    }; 
    
    // draw all of the points to standard draw
 /*
  * public void draw(){
        Stack<Point2D> stack = traverse(root);
        for( Iterator<Point2D> it = stack.iterator(); it.hasNext();){
            Point2D p = it.next();
            p.draw();
        }
    }
    */ 

    /*
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
   */
    
    public Point2D nearest(Point2D p){
        double minDist = p.distanceTo(root.p);
        Point2D minPoint = new Point2D(root.p.x(),root.p.y());
        Node node = root;
        int levelIndex = 0;
        
        while(node!=null){
            Point2D _p = new Point2D(p.x(),p.y());
            Point2D node_p = new Point2D(node.p.x(), node.p.y());
            if(levelIndex==0){
                _p = swapXY(_p);
                node_p = swapXY(node_p);
            }
            
            int cmp =  _p.compareTo(node_p);
            if(cmp==0) return node.p;
            if(cmp < 0 ) 
                node = node.lb; 
            else//( cmp > 0 )
                node = node.rt;
        
            if(node!=null){
                double dist = p.distanceTo(node.p);
                if(dist<minDist){
                    minDist = dist; 
                    minPoint = node.p;
                }
                levelIndex = 1-levelIndex;
            }
            else{
                break;
            }
        }
        return minPoint;
    }
    
    // needs a recursive call to neas 
    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest1(Point2D p){
        //double dist = p.distanceTo(root.p);
        
        //double dist = p.distanceTo(node.p); 

        Node node1 = floor(root,p,0);
        Node node2 = ceiling(root,p,0);
        if(node1==null && node2 == null){
            return null;
        }
        else if(node1==null && node2 != null){
            return node2.p;
        }
        else if(node1!=null && node2 == null){
            return node1.p;
        }
        else{
            double dist1 = p.distanceTo(node1.p);
            double dist2 = p.distanceTo(node2.p);
            if(dist1 < dist2){ 
                return node1.p;
            }
            return node2.p;
        }
    }
    
    
    private boolean check(){
        if( !isBST(root,null,null,0) ) StdOut.println("Not in order");
        return true;
    }
    
    private boolean isBST(Node node, Point2D min, Point2D max,int levelIndex) {
        if (node == null) return true;
        Point2D min_p = new Point2D(min.x(),min.y());
        Point2D max_p = new Point2D(max.x(),max.y());
        Point2D node_p = new Point2D(node.p.x(), node.p.y());
        if(levelIndex==0){
            min_p = swapXY(min_p);
            max_p = swapXY(max_p);
            node_p = swapXY(node_p);
        }
            
        if (min != null && node_p.compareTo(min_p) <= 0) return false;
        if (max != null && node_p.compareTo(max_p) >= 0) return false;
        return isBST(node.lb, min, node.p,1-levelIndex) && isBST(node.rt, node.p, max,1-levelIndex);
    }
}
        
