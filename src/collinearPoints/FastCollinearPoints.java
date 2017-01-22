package collinearPoints;

import java.util.Arrays;
import java.util.ArrayList;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class FastCollinearPoints {
	private final LineSegment[] ls;
	private final int num;
	public FastCollinearPoints(Point[] points) {     // finds all line segments containing 4 or more points
		if (points == null) throw new NullPointerException();
		int N = points.length;
		for (int i = 0; i < N; i++) {
			if (points[i] == null) throw new NullPointerException();
			for (int j = i + 1; j < N; j++) {
				if (points[i].compareTo(points[j]) == 0) 
					throw new IllegalArgumentException();
			}
		}
		
		Point[] pointsSort = points.clone();
		Arrays.sort(pointsSort);
		Point[] ps = points.clone();
		Arrays.sort(ps);
		ArrayList<LineSegment> als = new ArrayList<LineSegment>(); 
		for (int i = 0; i < N; i++) {
			Arrays.sort(ps, pointsSort[i].slopeOrder());
			int j = 1;
			while (j < N - 2) {
				int k = j;
				double s1 = pointsSort[i].slopeTo(ps[k++]), s2;
				do {
					if (k == N) {
						k++;
						break;
					}
					s2 = pointsSort[i].slopeTo(ps[k++]);
				} while (s2 == s1);
				int j2 = j;
				if (k - j < 4) {
					j = k - 1;
					
					continue;
				}
				int len = k - j2;
				j = k - 1;
				Point[] p = new Point[len];
				p[0] = pointsSort[i];
				for (int m = 1; m <len; m++) {
					p[m] = ps[j2 + m -1];
				}
				Arrays.sort(p);
				if (p[0] == pointsSort[i])
					als.add(new LineSegment(p[0],p[len - 1]));

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

	    // read the n points from a file
	    In in = new In(args[0]);
	    int n = in.readInt();
	    Point[] points = new Point[n];
	    for (int i = 0; i < n; i++) {
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
	    StdDraw.show();

	    // print and draw the line segments
	    FastCollinearPoints collinear = new FastCollinearPoints(points);
	    for (LineSegment segment : collinear.segments()) {
	        StdOut.println(segment);
	        segment.draw();
	    }
	    StdDraw.show();
	}
}
