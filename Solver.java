import java.util.Comparator;
import java.util.Iterator;

public  class Solver {
    
    // SearchNode keeps the info of the board, nMoves and prevNode (for solution)
    //------------------------------------------------------------------------
    private class SearchNode{
        private Board currBoard; // the current board
        private int nMoves; // number of moves of currBoard from the initial board. 
        private SearchNode prevNode; //this in important to get the final solution
        private int priority; 
        
        public SearchNode(Board _currBoard, int _nMoves, SearchNode _prevNode) {
            currBoard = _currBoard;
            nMoves = _nMoves; 
            prevNode = _prevNode;
            priority = nMoves + currBoard.manhattan();
        }
        
        public String toString(){
            String str = new String();
            str = str + "\n Moves " + nMoves;
            str = str + "\n priority " + priority;
            str = currBoard.toString();
            return str; 
        }
    };
    
    // this is a way to sort the search node (based on hamming distance + moves)
    // private static class searchNodeComparator -- one class (no instances)
    //------------------------------------------------------------------------
  
    /*
      private static class SearchNodeComparatorHamming implements Comparator<SearchNode> {
        public int compare(SearchNode b1, SearchNode b2) {
            
            int b1Val = b1.currBoard.hamming() + b1.nMoves; 
            int b2Val = b2.currBoard.hamming() + b2.nMoves;
            return ( b1Val - b2Val ); 
        }       
    }
    */


    private static class SearchNodeComparatorManhattan implements Comparator<SearchNode> {
        public int compare(SearchNode b1, SearchNode b2) {
            return ( b1.priority - b2.priority ); 
        }       
    }

    
    
    // member variables (main priority queue, the min node is the move to select)
    //------------------------------------------------------------------------
    private final MinPQ<SearchNode> pq; //a priority queue of possible nodes sorted on comparator
    private final MinPQ<SearchNode> twin_pq; //priority queue of the twin board..

    // used to collect the final solution
    //---------------------------------------
    private Stack<Board> solutionBoards;
    private boolean isSolveable; 
//    private static final Comparator<SearchNode> compareSearchNodeHamming = new SearchNodeComparatorHamming();
    private static final Comparator<SearchNode> compareSearchNodeManhattan = new SearchNodeComparatorManhattan();
    
    // find a solution to the initial board (using the A* algorithm)
    //--------------------------------------------------------------
    
    // the constructor: sets up the pq with the comparator and empty sol stack
    public Solver(Board initial)
    {
        pq = new MinPQ<SearchNode>(compareSearchNodeManhattan);
        twin_pq = new MinPQ<SearchNode>(compareSearchNodeManhattan);
        solutionBoards = new Stack<Board>();
        
        // insert the initial node
        SearchNode tmpNode = new SearchNode(initial, 0, null); 
        pq.insert(tmpNode);

        // insert the twin node
        SearchNode tmpTwinNode = new SearchNode(initial.twin(), 0, null); 
        twin_pq.insert(tmpTwinNode);
        
        // solve 
        isSolveable = solve();
        
    }
    
    // insert the new board position accessible from curr board config
    //-----------------------------------------------------------------
    private void insertNewSearchNodes(SearchNode tmpNode, MinPQ<SearchNode> tmpPq)
    {
        Iterable<Board> boards = tmpNode.currBoard.neighbors();
        Iterator<Board> itr = boards.iterator();
        //iterate through the board
        while (itr.hasNext()) {
            Board newBoard = itr.next();
            // if prevNode is null add all the boards otherwise check if
            // newBoard is not the same as the parent board
            if( tmpNode.prevNode==null || !newBoard.equals(tmpNode.prevNode.currBoard) ) {
                SearchNode node = new SearchNode(newBoard, tmpNode.nMoves+1, tmpNode);
                tmpPq.insert(node);
            };
        };
    }
    
    // nextMove returns the solution if it is the goal otherwise
    // adds new moves and returns the best next node
    //-----------------------------------------------------------
    private SearchNode nextMove( MinPQ<SearchNode> tmpPq, boolean show){
         
        SearchNode minNode = tmpPq.delMin();
        
        if(!minNode.currBoard.isGoal() ){
           //
            insertNewSearchNodes(minNode,tmpPq);
         }
        if (false && show) {
            StdOut.println("deletedNode");
               StdOut.println(minNode);
               
            StdOut.println("PQ size:" + tmpPq.size());
            for(SearchNode x:tmpPq)
                StdOut.println(x);
        }
        
        return minNode;
    }
    
    // this is the main solve step. 
    private boolean solve(){
        
        SearchNode minNode = nextMove(pq,true);
        SearchNode minTwinNode = nextMove(twin_pq,false);
        boolean solveable = false;
        
        // recursively call the nextMove
        while ( !(minNode.currBoard.isGoal() || minTwinNode.currBoard.isGoal() )) {
            // solve the board and its twin each step simultaneously
            // one of them will have a result
            minNode = nextMove(pq,true);
            minTwinNode = nextMove(twin_pq,false);
        }
        
        //once you know it is solveable get the solution
        if(!minNode.currBoard.isGoal()) {
            return false;
        }
        else{
            SearchNode solvedNode = minNode;       
            // backtrace from the current node
            while(solvedNode.prevNode!=null) {
                solutionBoards.push(solvedNode.currBoard);
                solvedNode = solvedNode.prevNode;
            }
            solutionBoards.push(solvedNode.currBoard);
            
            return true;
        }   
    }
    
    // checks if a board is solvable (the board or the twin has to be solveable)
    //-----------------------------------------------------------
    public boolean isSolvable(){
        return isSolveable; 
    }
    
    // simple accessor functions
    //----------------------------------------------------

    // min number of moves to solve initial board. -1 if no solution
    public int moves(){
        return solutionBoards.size()-1;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Iterable<Board> solution() {
        if(isSolvable())
            return solutionBoards;
        else 
            return null; 
    }

    // MAIN calling function
    //-----------------------------------------------------

    // solve a slider puzzle (given below)
    public static void main( String[] args ){

        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                blocks[i][j] = in.readInt();
            }
        }
        Board initial = new Board(blocks);
        
        // solve the puzzle
        long startTime = System.currentTimeMillis();
        Solver solver = new Solver(initial);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime; 
        StdOut.println("Time for " + N   + " inserts = " +  duration/1000 );
        
        // print solution to standard output
        if (!solver.isSolvable() ){
            StdOut.println("No solution possible");
        }
        else{
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution() )
                StdOut.println(board);
        };   
    }
};