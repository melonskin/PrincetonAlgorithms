package eightPuzzle;
import java.util.Comparator;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;

public class Solver {
	private MinPQ<Node> pq1, pq2;
	private int minMove = 0;
	private Comparator<Node> MANHATTAN_PRIORITY = new ManhattanPriority();
	
	private class Node {
		private Board board;
		private int moves;
		private Node prev;
		
		Node(Board board, int moves, Node prev) {
			this.board = board;
			this.moves = moves;
			this.prev = prev;
		}
	}
	
    private class ManhattanPriority implements Comparator<Node> {
    	public int compare(Node n1, Node n2) {
    		int c1 = n1.board.manhattan() + n1.moves;
    		int c2 = n2.board.manhattan() + n1.moves;
    		if (c1 < c2) return -1;
    		if (c1 > c2) return +1;
    		if (n1.board.hamming() == n2.board.hamming()) return 0;
    		if (n1.board.hamming() < n2.board.hamming()) return -1;
    		return +1;
    		
    	}
    }
	// find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial)  {
    	if (initial == null) throw new NullPointerException("Board can't be null");
    	pq1 = new MinPQ<Node>(MANHATTAN_PRIORITY);
    	pq2 = new MinPQ<Node>(MANHATTAN_PRIORITY);
    	pq1.insert(new Node(initial, minMove, null));
    	pq2.insert(new Node(initial.twin(), minMove, null));
    	Node prev1, prev2;
    	while (!pq1.min().board.isGoal() && !pq2.min().board.isGoal()) {
    		prev1 = pq1.delMin();
    		prev2 = pq2.delMin();
    		for (Board nb : prev1.board.neighbors()) {
    			if (prev1.prev != null && nb.equals(prev1.prev.board)) continue;
    			pq1.insert(new Node(nb, prev1.moves+1,prev1));
    		}
    		for (Board nb : prev2.board.neighbors()) {
    			if (prev2.prev != null && nb.equals(prev2.prev.board)) continue;
    			pq2.insert(new Node(nb, prev2.moves+1,prev2));
    		}
    	}
    	if (!pq1.min().board.isGoal()) minMove = -1;
    	else minMove = pq1.min().moves;
    	
    }
    // is the initial board solvable?
    public boolean isSolvable()  {
    	return (minMove != -1);
    }
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
    	return minMove;
    }
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
    	if (!isSolvable()) return null;
    	Stack<Board> solu = new Stack<Board>();
    	Node path = pq1.min();
    	while (path != null) {
    		solu.push(path.board);
    		path = path.prev;
    	}
    	return solu;
    }
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Stopwatch sw = new Stopwatch();
        Solver solver = new Solver(initial);
        StdOut.println(sw.elapsedTime());
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
//            for (Board board : solver.solution())
//                StdOut.println(board);
        }    	
    }
}
