/*************************************************************************
  *  Compilation:  javac PointSET.java
  *
  *  Immutable point data type for points in the plane.
  *
  *************************************************************************/


import java.util.TreeSet;
//import java.lang.Double;
import java.util.Iterator;
//import java.awt.geom.Point2D.Double; 
import java.lang.Double;


// need to include size as a variable in the node. 

public class KdTree {
    
    private Node root; 
    //private double minX[];
    //private double minY[]; 
    
    //----- Node struct ----------------
    private static class Node {
        
        private Point2D p;      // the point
        int depth;              // decide the level
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private int N; 
        
        public Node( Point2D _p ) {
            this.p = _p;
            this.lb = null;
            this.rt = null; 
        }
        
        public Node( Point2D _p, Node  _lb , Node _rt ){
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
        return (size() == 0);
    }
    
    
    // number of points in the set
    public int size(){
        return size(root); 
    }
    
    private int size(Node x){
        if (x == null) return 0; 
        return x.N; 
    }
    
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p){

        /*
        if(root == null) {
            root = new Node(p);
            root.N = 1;
           
            root.depth = 0;
            return;
        }
        */ 
        
      RectHV rect = new RectHV(0, 0, 1, 1);
       root = insert(root, p, 0, rect);
    }
    
    
    // contains
    public boolean contains( Point2D p ) {
        return contains(root, p, 0);
    }
    
    // draws
    public void draw(){
        if(root==null) return;
        draw(root);
    };
    
    
    // range function
    public Iterable<Point2D> range(RectHV rect){
        Stack<Point2D> stacks = new Stack<Point2D>();
        range_helper(rect,root,stacks);
        return stacks;
    }
    
    // nearest 
    public Point2D nearest(Point2D p){
        
        if (root == null)
            return null;
        
        // the min point so far is the root. 
        Point2D [] minPoint = new Point2D[1]; 
        minPoint[0] = new Point2D( 0.0, 0.0 );
        
        double [] minDist = new double[1];
        minDist[0] =  Double.POSITIVE_INFINITY;
        
        int levelIndex = 0;
        nearest_helper(p, root, levelIndex, minPoint, minDist); //Point);
        
        return minPoint[0]; //new Point2D(minX[0],minY[0]);
    }
    
    
    
    // private methods 
    //-------------------------------------------------
    
    
    // draw the tree (inplace iteration)
    private void draw(Node node){
        if(node == null) 
            return ;
        draw(node.lb);
        node.p.draw();
        draw(node.rt);
    };
    
    private int compareAtLevel( Point2D p, Point2D node_p, int levelIndex){
        
        // check if swapXY is not changing the values of the node_p permanently
        // create new tmp variables
        int cmp;
        if (levelIndex == 0) { 
            cmp  = p.X_ORDER.compare(p, node_p);
        }
        else {
            cmp  = p.Y_ORDER.compare(p, node_p);
        }
        
        return cmp;        
    }
    
    
    private RectHV getRect(Node pNode,  boolean  isLess, int levelIndex){
    
        // if the node exist just return it 
        if (isLess) {
            if (pNode.lb != null)  return pNode.lb.rect;
        }
        else {
            if(pNode.rt != null)  return pNode.rt.rect;
        }; 
        
        // compute the new rect; 
            
        // get the rectangle coordinate
        RectHV pRect = pNode.rect; 
        double p_xmin = pRect.xmin();
        double p_ymin = pRect.ymin();
        double p_xmax = pRect.xmax();
        double p_ymax = pRect.ymax();
        
        RectHV newRect;  
        if (levelIndex == 0) {
            if (isLess) {
                // bisects x with x_max = x_loc (rect lives in left space)
                newRect = new RectHV(p_xmin, p_ymin, pNode.p.x(), p_ymax);
            }
            else {
                // bisects x with x_min = x_loc (rect is in right of point)
                newRect = new RectHV(pNode.p.x(), p_ymin, p_xmax, p_ymax);
            }
        }
        else{
             // level index == 1
            if (isLess) {
                // bisects the y with y_max = currect y (rect lives in bottom space)
                newRect  = new RectHV(p_xmin, p_ymin, p_xmax, pNode.p.y());
            }
            else {
                newRect = new RectHV(p_xmin, pNode.p.y(), p_xmax, p_ymax);   
            }
        }
        
        return newRect; 
    }
    
    
    // recursive insertion function
    // swaps the x and y value of the node and the point p to
    // change the order of comparison (Y order to X order)
    private Node insert( Node node, Point2D p, int levelIndex, RectHV rect) {
        
        if(node == null) { 
            Node newNode = new Node(p);
            newNode.N = 1;
            newNode.rect = rect; 
            return newNode;
        }   
        if( p.equals(node.p) ) return node; 
        int cmp = compareAtLevel( p, node.p, levelIndex);
        
        if(cmp < 0) {
            //left subtree
            RectHV newRect = getRect(node, true, levelIndex);
            node.lb = insert(node.lb, p, 1-levelIndex, newRect);
        }
        else {
            RectHV newRect = getRect(node, false, levelIndex);           
            node.rt = insert(node.rt, p, 1-levelIndex, newRect);
        }
        
        node.N = size(node.lb) + size(node.rt) + 1; 
        
        return node;
    };
    
    
    // nearest helper
    private void nearest_helper(Point2D qp, Node node, int levelIndex,
                                Point2D [] minPoint, double[] minDist)
    {
        // base case
        if (node == null) return;
        // if nearest distance to qp is > minDist 
        if ( node.rect.distanceTo(qp) > minDist[0] ) return;

        // compute the distance of qp with node point
        // update if less than minDist also update point
        double dist = qp.distanceTo(node.p);
        if (dist < minDist[0]) {
            minDist[0]   = dist;
            minPoint[0]  = new Point2D( node.p.x(), node.p.y());
        }
        
        // check if query point is to the left/right of node. 
        int cmp = compareAtLevel( qp, node.p, levelIndex);
        if (cmp < 0){ 
            // check if distance of qp from left rectangle is not greater than minDist
            nearest_helper(qp, node.lb, 1-levelIndex, minPoint, minDist);  
            nearest_helper(qp, node.rt, 1-levelIndex, minPoint, minDist);   
        }
        else {
            nearest_helper(qp, node.rt, 1-levelIndex, minPoint, minDist);
            nearest_helper(qp, node.lb, 1-levelIndex, minPoint, minDist);  

        }
        
    }
    
    
    // node 
    private void range_helper(RectHV rect, Node node, Stack<Point2D> stacks){
        
        if(node==null) return;
        
        // add the node point to the stack if it is within the rectangle  
        if(rect.contains(node.p)){
            stacks.push(node.p);
        }
        // if it intersects than look in that tree
        if( node.lb!=null && node.lb.rect.intersects(rect) ) {
            // recursively call the left subtree
            range_helper(rect, node.lb, stacks );
        }
        // if given rect intersects than look in the right tree        
        if( node.rt!=null && node.rt.rect.intersects(rect) ) {
            range_helper(rect,node.rt,stacks );
        }
    }
    
    // recursive function for computing size.
    // size = sizeof(left) + sizeof(right) + 1;
    private int size1(Node currNode){
        
        int size = 0;
        if(currNode==null) return 0;
        size += size(currNode.lb); //size of left node
        size += size(currNode.rt); //size of right node
        size +=1; // side of the current node (1)
        
        return size;
    };
    
    
    // helper function for swapping X and Y values
    private Point2D swapXY(Point2D p){
        Point2D p1 = new Point2D(p.y(), p.x());
        return p1;
    }
    
    
    
    // contains
    private boolean contains( Node node, Point2D qp, int levelIndex ) {
        
        if (node == null) return false;
        if (node.p.equals(qp)) return true; 
        
        // compare query point qp with node and see which direction to go.  
        int cmp = compareAtLevel( qp, node.p, levelIndex);
        
        if (cmp < 0 ) {
            // if the lb rect contain query point then recurse
            if(node.lb != null && node.lb.rect.contains(qp))
                return contains(node.lb, qp, 1-levelIndex); //1-levelIndex makes it alternate
        }
        else {
            // only recurse if the rt rect contain query point 
            if(node.rt != null && node.rt.rect.contains(qp))
                return contains(node.rt, qp, 1-levelIndex);
        }
        
        return false; 
    }
    
    private void print(){
        System.out.println("Size:" + size());
        print(root);   
    }
    
    // draw the tree (inplace iteration)
    private void print(Node node){
        if(node==null) return ;
        System.out.printf( node.p.toString() + "\t" ) ; 
        System.out.printf( node.rect.toString() + "\n") ;
        print(node.lb);
        print(node.rt);
    };
    
    
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
    
    public static void main(String [] args){
        
        KdTree kdtree = new KdTree(); 
        
        int N = 100000; double eps = 0.001;
        // create random points between 0 1 and 0, 1 x and y
        Point2D p = new Point2D(0,0); 

         for (int i = 0; i < N ; ++i) {
            double x = StdRandom.uniform(eps,1-eps);
            double y = StdRandom.uniform(eps,1-eps);
            p = new Point2D(x,y);
            kdtree.insert(p);
        }
        
        long startTime = System.currentTimeMillis();
     
        for (int i = 0; i < N ; ++i) {
            double x = StdRandom.uniform(eps,1-eps);
            double y = StdRandom.uniform(eps,1-eps);
            Point2D qp = new Point2D(x,y);
            kdtree.nearest(qp);
        }     
        
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime; 
        StdOut.println("Time for " + N   + " inserts = " +  (float)duration/1000.0 );
        
        StdOut.println("Point" + p + " contains " +   kdtree.contains(p)); 
        StdOut.println("Point" + " test not contain" +  kdtree.contains( new Point2D(0.122121,0.3232323) )); 
        
        startTime = System.currentTimeMillis();
        kdtree.size(); 
        endTime = System.currentTimeMillis();
        duration = endTime - startTime; 
        
        StdOut.println("Size" + kdtree.size() + " Time:" + duration  );
        
        
        
    }
}





// RANGE OPERATION
//----------------------------

// list all points in the set that are inside the rectangle
/*
 public Iterable<Point2D> range(RectHV rect){
 
 // check if the rect intersect left subRect and right subRect
 // if not intersect then leave that subTree, otherwise continue
 
 Stack<Point2D> stacks = new Stack<Point2D>();
 Node node = root;
 if(rect.contains(node.p)){
 stacks.push_back(node.p);
 }
 
 while( node != null  ){
 
 // if rect intersects both the left and right subtree then
 // search recursively in both the nodes..
 if( node.lb.rect.intersects(rect) ) {
 
 if(rect.contains(node.p)){
 stacks.push_back(node.p);
 }
 node = node.lt;
 }
 
 if( node.rt.rect.intersects(rect) ) {
 if(rect.contains(node.p)){
 stacks.push_back(node.p);
 }
 node = node.rt;
 }
 }
 
 return stack;
 }
 */

/*
 *  
 
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


    
    
    /*
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
     
     */
