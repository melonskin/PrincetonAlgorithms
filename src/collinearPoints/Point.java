package collinearPoints;

import java.util.Arrays;
import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Point implements Comparable<Point>{
	private final int x;
	private final int y;
	public Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void draw()
	{
		StdDraw.point(x, y);
	}
	
	public void drawTo(Point that)
	{
		StdDraw.line(this.x, this.y, that.x, that.y);
	}
	
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	
	public int compareTo(Point that)     // compare two points by y-coordinates, breaking ties by x-coordinates
	{
        if (this.y < that.y) return -1;
        if (this.y == that.y) {
            if (this.x < that.x) return -1;
            if (this.x == that.x) return 0;
        }
        return +1;
	}
	public double slopeTo(Point that)       // the slope between this point and that point
	{
		if (this.x == that.x) {
			if (that.y == this.y ) return Double.NEGATIVE_INFINITY;
			else return Double.POSITIVE_INFINITY;
		}
		if (that.y == this.y) return 0;
		return ((double) that.y-this.y)/(that.x-this.x);
	}
	public Comparator<Point> slopeOrder()              // compare two points by slopes they make with this point
	{
		return new SlopeOrder();
	}
	private  class SlopeOrder implements Comparator<Point>
	{
		public int compare(Point p1, Point p2)
		{
			double d1 = slopeTo(p1);
			double d2 = slopeTo(p2);
			if (d1 == d2) return 0;
			if (d1 < d2)  return -1;
			return +1;
		}
	}
	public static void main(String[] args)
	{
		Point[] p = new Point[10];
        for (int i = 0; i < 10; ++i) 
            p[i] = new Point(i, i*i-10*i+25);
        for (int i = 0; i < 9; ++i) 
        {
            StdOut.println(p[i].slopeTo(new Point(0, i*i)));
            //p[i].draw();
            //p[i].drawTo(p[i+1]);
        }
        //p[1].drawTo(p[2]);
        //p[1].draw();
        for (int i = 0; i < 10; ++i)
            StdOut.println(p[i]);
        StdOut.println();
        Arrays.sort(p, p[5].slopeOrder());
        for (int i = 0; i < 10; ++i)
            StdOut.println(p[i]);
	}
}
