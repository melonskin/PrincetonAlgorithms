package eightPuzzle;

import java.util.ArrayList;
//import java.util.Comparator;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Board {
    
	// construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
	private char[] tiles;
	private int N, zero;
	private int hamming, manhattan;
	public Board(int[][] blocks) {
		N = blocks.length;
		tiles = new char[N*N];
		for (int i = 0; i<N; i++) {
			for (int j = 0; j<N; j++) {
				tiles[i*N+j] = (char) blocks[i][j];
				if (blocks[i][j] == 0)
					zero = i*N + j;
			}
		}
		hamming = calHamming(tiles);
		manhattan = calManhattan(tiles);
	}
	private Board(char[] tiles, int zero, int hamming, int manhattan) {
		N = (int) Math.sqrt(tiles.length);
		this.tiles = tiles;
		this.zero = zero;
		this.hamming = hamming;
		this.manhattan = manhattan;
	}
	// board dimension n
	public int dimension() {
		return N;
	}
	private int calHamming(char ch, int t) {
		if (ch == 0) return 0;
		if (ch != t) 
			return 1;
		else
			return 0;
	}
	private int calHamming(char[] tiles) {
		int ham = 0;
		for (int i = 0; i<N*N; i++)
			ham += calHamming(tiles[i],i+1);
		return ham;
	}
	// number of blocks out of place
	public int hamming() {
		return hamming;
	}
	
	private int calManhattan(char ch, int row, int col) {
		if (ch == 0) return 0;
		return (Math.abs((ch-1)/N - row) + Math.abs((ch-1)%N - col));
	}
	private int calManhattan(char[] tiles) {
		int man = 0;
		for (int i = 0; i< N; i++) {
			for (int j = 0; j<N; j++) {
				man += calManhattan(tiles[i*N+j], i, j);
			}
		}
		return man;
	}
	// sum of Manhattan distances between blocks and goal
	public int manhattan() {
		return manhattan;
	}
	
	// is this board the goal board?
	public boolean isGoal() {
		return (hamming == 0);
	}
	// define an exchange method
	private char[] exchange(char[] tiles, int i, int j) {
		char temp = tiles[i];
		tiles[i] = tiles[j];
		tiles[j] = temp;
		return tiles;
	}
	// a board that is obtained by exchanging any pair of blocks
	public Board twin() {
		char[] newTiles = tiles.clone();
		for (int i = 0; i < N*N; i++) {
			if (i%N == 0 || tiles[i-1]*tiles[i]==0) continue;
			exchange(newTiles, i-1, i);
			int h = hamming, m = manhattan;
			for (int j = i-1; j<= i; j++) {
				h += calHamming(newTiles[j], j+1) - calHamming(tiles[j], j+1);
				m += calManhattan(newTiles[j],j/N,j%N) - calManhattan(tiles[j],j/N,j%N);
			}
			return new Board(newTiles, zero, h, m);
		}
		return null;
	}
	// does this board equal y?
	public boolean equals(Object y) {
		if ( y == null || !(y instanceof Board)) return false;
		return new String(tiles).equals(new String(((Board)y).tiles));
	}
	// method for neighbors
	private Board neighbor(int newZero) {
		char[] newTiles = exchange(tiles.clone(),zero, newZero);
		int h = hamming, m = manhattan;
		h += calHamming(newTiles[zero],zero+1) - calHamming(tiles[newZero],newZero+1);
		m += calManhattan(newTiles[zero], zero/N, zero%N) - calManhattan(tiles[newZero], newZero/N,newZero%N);
		return new Board(newTiles, newZero, h, m);
	}
	// all neighboring boards
	public Iterable<Board> neighbors() {
		ArrayList<Board> nb = new ArrayList<Board>(4);
		
		if (zero/N != 0) nb.add(neighbor(zero-N));
		if (zero/N != N-1) nb.add(neighbor(zero+N));
		if (zero%N != 0) nb.add(neighbor(zero-1));
		if (zero%N != N-1) nb.add(neighbor(zero+1));
		return nb;
	}
    
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(N + " \n ");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%d ", (int) tiles[i*N+j]));
            }
            s.append("\n ");
        }
        return s.toString();
    }
    
    // unit tests
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board b = new Board(blocks);
        StdOut.println(b);
        StdOut.println(b.hamming);
        StdOut.println(b.manhattan);
    }
}

