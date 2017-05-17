import java.util.*;

public class MoveStack {
  private final int numStacks = 3;  
  List<Stack<Integer>>  stacks;     
  private int N;   
  MoveStack(int _N){
   stacks = new  ArrayList<Stack<Integer>>(numStacks);   
   for(int i = 0 ; i< numStacks; ++i){
       Stack<Integer> tmp = new Stack<Integer>();
       stacks.add(i, tmp);   
   }
   N = _N; 
   for(int i=0; i<N; ++i){
       stacks.get(0).push(N-i);     
   }
  }
  
  private void move(){
   moveToLast(stacks, N);  
  }
    
  private void moveToLast( List<Stack<Integer>>  stacks, int N){
      
      if(N==0)
          return; 
      moveToLast(stacks, N-1); 
      Integer item = stacks.get(0).pop();
      stacks.get(1).push(item);
      moveToFirst(stacks, N-1); 
      item = stacks.get(1).pop();
      stacks.get(2).push(item);
      moveToLast(stacks, N-1);
  }
    
  private void moveToFirst(  List<Stack<Integer>>  stacks, int N){
      
      if(N==0)
          return; 
      moveToFirst(stacks, N-1); 
      Integer item = stacks.get(numStacks-1).pop();
      stacks.get(numStacks-2).push(item);
      moveToLast(stacks, N-1); 
      item = stacks.get(numStacks-2).pop();
      stacks.get(numStacks-3).push(item);
      moveToFirst(stacks, N-1);
  }
   
  private void print(){
      
        for(int i = 0 ; i< numStacks; ++i){
             StdOut.print("Stack:" + i + "   ");
            for(Integer j: stacks.get(i)){
                StdOut.print(j+ " ");
            }
            StdOut.println();  
        }
  }
  
  public static void main(String args[]){
      
   MoveStack mv = new MoveStack(10);
   mv.print();
   mv.move();
   mv.print();
      
      
  }
    
    
    
    
    
}