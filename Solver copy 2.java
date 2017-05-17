import java.util.Comparator;
import java.util.NoSuchElementException;

public class Solver {
    
    // SearchNode keeps the info of the board, nMoves and prevNode (for solution)
    //------------------------------------------------------------------------
    private class SearchNode{
        Board currBoard; // the current board
        int nMoves; // number of moves of currBoard from the initial board. 
        SearchNode prevNode; //this in important to get the final solution
        
        public SearchNode(Board _currBoard, int _nMoves, SearchNode _prevNode ){
            currBoard = _currBoard;
            nMoves = _nMoves; 
            prevNode = _prevNode;
        }
    };
    
    // this is a way to sort the search node (based on hamming distance + moves)
    // private static class searchNodeComparator -- one class (no instances)
    //------------------------------------------------------------------------
    private static class SearchNodeComparator implements Comparator<SearchNode>{
        public int compare(SearchNode b1, SearchNode b2){
            int b1Val = b1.currBoard.hamming()+b1.nMoves; 
            int b2Val = b2.currBoard.hamming()+b2.nMoves;
            return (b1Val-b2Val); 
        }       
    }

    // member variables (main priority queue, the min node is the move to select)
    //------------------------------------------------------------------------
    private MinPQ<SearchNode> pq; //a priority queue of possible nodes sorted on comparator
    private MinPQ<SearchNode> twin_pq; //priority queue of the twin board..

    // used to collect the final solution
    //---------------------------------------
    private Stack<Board> solutionBoards;
    private static final Comparator<SearchNode> compareSearchNode = new SearchNodeComparator();
    
    // find a solution to the initial board (using the A* algorithm)
    //--------------------------------------------------------------
    
    // the constructor: sets up the pq with the comparator and empty sol stack
    public Solver(Board initial)
    {
        pq = new MinPQ<SearchNode>(compareSearchNode);
        twin_pq = new MinPQ<SearchNode>(compareSearchNode);
        solutionBoards = new Stack<Board>();
        
        // insert the initial node
        SearchNode tmpNode = new SearchNode(initial,0,null); 
        pq.insert(tmpNode);

        // insert the twin node
        SearchNode tmpTwinNode = new SearchNode(initial.twin(),0,null); 
        twin_pq.insert(tmpTwinNode);
    }
    
    // insert the new board position accessible from curr board config
    //-----------------------------------------------------------------
    private void insertNewSearchNodes(SearchNode tmpNode, MinPQ<SearchNode> tmpPq)
    {
        Iterable<Board> boards = tmpNode.currBoard.neighbors();
       
        //iterate through the board
        while(boards.size()>0){
            Board newBoard = boards.pop();
            // if prevNode is null add all the boards otherwise check if
            // newBoard is not the same as the parent board
            if( tmpNode.prevNode==null || !newBoard.equals(tmpNode.prevNode.currBoard) ){
                SearchNode node = new SearchNode(newBoard,tmpNode.nMoves+1,tmpNode);
                tmpPq.insert(node);
            };
        };
    }
    
    // nextMove returns the solution if it is the goal otherwise
    // adds new moves and returns the best next node
    //-----------------------------------------------------------
    public SearchNode nextMove( MinPQ<SearchNode> tmpPq){
         
        SearchNode minNode = tmpPq.delMin();
        
        if(!minNode.currBoard.isGoal() ){
           StdOut.println("PQ size:" + tmpPq.size());
            insertNewSearchNodes(minNode,tmpPq);
             return minNode;
         }
         else{
             return minNode;
         }
    }

    // checks if a board is solvable (the board or the twin has to be solveable)
    //-----------------------------------------------------------
    public boolean isSolvable(){
        
        SearchNode minNode = nextMove(pq);
        SearchNode minTwinNode = nextMove(twin_pq);
        boolean solveable = false;
        
        // recursively call the nextMove
        while(!(minNode.currBoard.isGoal() || minTwinNode.currBoard.isGoal() )){
            // solve the board and its twin each step simultaneously
            // one of them will have a result
            minNode = nextMove(pq);
            minTwinNode = nextMove(twin_pq);
        }
       
        //once you know it is solveable get the solution
        if(!minNode.currBoard.isGoal()){
            return false;
        }
        else{
	    SearchNode solvedNode = minTwinNode;
            
	    // backtrace from the current node
	    while(solvedNode.prevNode!=null){
		solutionBoards.push(solvedNode.currBoard);
		solvedNode = solvedNode.prevNode;
	    }
            solutionBoards.push(solvedNode.currBoard);
            
	    return true;
	}
    }
    
    // simple accessor functions
    //----------------------------------------------------

    // min number of moves to solve initial board. -1 if no solution
    public int moves(){
        return solutionBoards.size()-1;
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Stack<Board> solution() {
        return solutionBoards;
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
        Solver solver = new Solver(initial);
       // Solver twinSolver = new Solver(initial.twin()); // this was wrong because we need to check at each step..
        
        // print solution to standard output
        if (!solver.isSolvable() ){
            StdOut.println("No solution possible");
        }
        else{
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        };   
    }
};