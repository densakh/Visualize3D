package Calculus;

import java.util.Arrays;

import DataTypes.*;

public class Triangulation {
	private double xc = 0, yc = 0;
	private Vertex nearest = null;
	private Vertex massCenter = null;
	public Triangulation(Vertex[] vertices){
		Vertex[] copy = Arrays.copyOf(vertices, vertices.length);
		/*Build convex hull*/
		ConvexHull2D Chull = new ConvexHull2D(copy);
		Vertex[] hull = Arrays.copyOfRange(Chull.getHull(), 0, Chull.getSize() - 1);
		/*Find mass center of hull*/
		calcMassCenter(hull);
		/*Find nearest vertex to mass center*/
		massCenter = new Vertex(xc, yc);
		nearest = copy[0];
		double distance = massCenter.distance(nearest);
		for (int i = 0; i < copy.length; ++i){
			if (massCenter.distance(copy[i]) < distance){
				nearest = copy[i];
				distance = massCenter.distance(copy[i]); 
			}
		}
	}
	
	
	private double square(double x1, double x2, double x3, double y1, double y2, double y3){
		return 0.5 * Math.abs((x2-x3)*(y1-y3) - (x1-x3)*(y2-y3));
	}
	 
	private void calcMassCenter(Vertex[] vertices){
		int n = vertices.length;
		double xm = 0, ym = 0;
		for (int i = 0; i < n; ++i){
			xm += vertices[i].getX();
			ym += vertices[i].getY();
		}
		xm /= n; ym /= n;
		xc = 0; yc = 0;
		double s = 0;
		for (int i = 0; i < n; ++i){
			double s1 = square(xm, ym, vertices[i].getX(), vertices[i].getY(), vertices[(i+1)%n].getX(), vertices[(i+1)%n].getY());
			xc += s1 * (xm + vertices[i].getX() + vertices[(i+1)%n].getX()) / 3;
			yc += s1 * (ym + vertices[i].getY() + vertices[(i+1)%n].getY()) / 3;
			s += s1;
		}
		xc /= s;
		yc /= s;
	}
}
