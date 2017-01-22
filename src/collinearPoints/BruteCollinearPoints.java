package collinearPoints;

import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
	private final LineSegment[] ls;
	private final int num;
	
	public BruteCollinearPoints(Point[] points) {   // finds all line segments containing 4 points
		if (points == null) throw new NullPointerException();
		int N = points.length;
		for (int i = 0; i < N; i++) {
			if (points[i] == null) throw new NullPointerException();
			for (int j = i + 1; j < N; j++) {
				if (points[i].compareTo(points[j]) == 0) 
					throw new IllegalArgumentException();
			}
		}
		Point[] ps = points.clone();
		Arrays.sort(ps);
		ArrayList<LineSegment> als = new ArrayList<LineSegment>(); 
		for (int i = 0; i < N; i++) {
			for (int j = i+1; j < N; j++) {
				for (int k = j + 1; k < N; k++) {
					for (int l = k + 1; l < N; l++) {
						if (ps[i].slopeTo(ps[j]) == ps[i].slopeTo(ps[k]) & ps[i].slopeTo(ps[j]) == ps[i].slopeTo(ps[l])) {
							Point[] p = new Point[4];
							p[0] = ps[i];
							p[1] = ps[j];
							p[2] = ps[k];
							p[3] = ps[l];
							Arrays.sort(p);
							als.add(new LineSegment(p[0],p[3]));						
						}
					}	
				}
			}
		}
		num = als.size();
		ls = new LineSegment[num];
		for (int i = 0; i < num; i++) {
			ls[i] = als.get(i);
		}
		
	}
	
	
	public int numberOfSegments() {       // the number of line segments
		return num;
	}
	public LineSegment[] segments() {               // the line segments
		return ls.clone();
	}
	
    public static void main(String[] args) {

        // read the N points from a file
        In in = new In(args[0]);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }	
}
