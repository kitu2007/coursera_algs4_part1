
public class IntervalSearch{
 private Node root; 
 
 private class Interval{
  Integer lo;
  Integer hi;

  Interval(Integer _lo, Integer _hi){
   lo = _lo;
   hi = _hi; 
  }

 }
 
 private class Node{
  Interval val;
  Integer max;
  Node left; 
  Node right; 

   Node( ){
   left = null;
   right = null; 
   val = null;
   max  = null; 
  }

  Node(Node _left, Node _right, Interval _val){
   left = _left;
   right = _right; 
   val = _val;   
  }
  
 }
 
 IntervalSearch(){
     
  root = null;   
     
     
 }
 
 public void insert(  Integer lo, Integer hi){     
     insert(root,lo,hi);
 }
 
 private void insert(Node x, Integer lo, Integer hi){
     if(x == null){
         Interval val = new Interval(lo,hi);
         root = new Node(null,null,val);
         return;
     }
     
     if (lo < root.val.lo){
      insert(root.left,lo,hi);   
     }
     else{
         insert(root.right,lo,hi);
     }

     if(root.left!=null){
     }
     
 }
 
 void search(int lo, int hi){
     
     
    
    
    
 }
    
    
    
 public static void main(String args[]){
     
     
     
     
     
 }
    
    
    
    
    
    
    
}