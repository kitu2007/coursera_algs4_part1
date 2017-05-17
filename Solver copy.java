import java.util.Comparator;
import java.util.NoSuchElementException;

public class Solver {
    // private class searchNode 
    private class SearchNode{
        Board currBoard;
        int nMoves;
        SearchNode prevNode;
        
        public SearchNode(Board _currBoard, int _nMoves, SearchNode _prevNode ){
            currBoard = _currBoard;
            nMoves = _nMoves; 
            prevNode = _prevNode;
        }
    };
    // private static class searchNodeComparator -- one class (no instances) 
    private static class SearchNodeComparator implements Comparator<SearchNode>{
        public int compare(SearchNode b1, SearchNode b2){
            int b1Val = b1.currBoard.hamming()+b1.nMoves; 
            int b2Val = b2.currBoard.hamming()+b2.nMoves;
            return (b1Val-b2Val); 
        }       
    }

    // member variables
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> twin_pq;
    private Stack<Board> solutionBoards;
    private static final Comparator<SearchNode> compareSearchNode = new SearchNodeComparator();
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)
    {
        pq = new MinPQ<SearchNode>(compareSearchNode);
        twin_pq = new MinPQ<SearchNode>(compareSearchNode);
        solutionBoards = new Stack<Board>();
        
        SearchNode tmpNode = new SearchNode(initial,0,null); 
        pq.insert(tmpNode);
        
        SearchNode tmpTwinNode = new SearchNode(initial.twin(),0,null); 
        twin_pq.insert(tmpTwinNode);
    }
    
    
    private void insertNeighboringNodes(SearchNode minNode, MinPQ<SearchNode> tmp_pq)
    {
       Stack<Board> boards = minNode.currBoard.neighbors();

     //iterate through the board
            while(boards.size()>0){
                Board newBoard = boards.pop();
                // if prevNode is null add all the boards otherwise check if 
                // newBoard is not the same as the parent board
                if( minNode.prevNode==null || !newBoard.equals(minNode.prevNode.currBoard) ){
                    SearchNode node = new SearchNode(newBoard,minNode.nMoves+1,minNode);
                    pq.insert(node);
                };
            };
               
    }
    // is the initial board solvable?
    public boolean isSolvable()
    {
        SearchNode minNode = pq.delMin();
        SearchNode twin_minNode = twin_pq.delMin();
        // repeat until queue is empty or currBoard is  goal
        while( !minNode.currBoard.isGoal() || !twin_minNode.currBoard.isGoal() )
        {
            StdOut.println("PQ size:" + pq.size());
            //get the neighboring board
            insertNeighboringNodes(minNode,pq);

            
            
        };
        
        boolean solveable =  minNode.currBoard.isGoal(); 

        if (solveable){
            while(minNode.prevNode!=null){
                solutionBoards.push(minNode.currBoard);
                minNode = minNode.prevNode;
            }
            solutionBoards.push(minNode.currBoard);
        }
        
        return solveable;
        
        
    }
    
    // min number of moves to solve initial board; -1 if no solution
    public int moves()
    {
        return solutionBoards.size();
    }
    
    // sequence of boards in a shortest solution; null if no solution
    public Stack<Board> solution()
    {
        return solutionBoards;
    }
    
    // solve a slider puzzle (given below)
    public static void main( String[] args )
    {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
       // Solver twinSolver = new Solver(initial.twin());
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