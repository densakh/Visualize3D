package Calculus;

import java.util.Stack;

import DataTypes.*;

public class ConvexHull2D {
	private boolean onHull[];
	private Point2D convexHull[];
	
	public ConvexHull2D(Point2D[] points){
		Stack<Point2D> hull = new Stack<Point2D>();
		int minY = 0;
		int maxY = 0;
		onHull = new boolean[points.length];
		onHull[0] = false;
		for (int i = 1; i < points.length; ++i){
			onHull[i] = false;
			if (points[minY].getY() == points[i].getY())
				minY = (points[minY].getX() < points[i].getX()) ? (minY) : (i);
			else
				minY = (points[minY].getY() < points[i].getY()) ? (minY) : (i);
			
			if (points[maxY].getY() == points[i].getY())
				maxY = (points[maxY].getX() < points[i].getX()) ? (i) : (maxY);
			else
				maxY = (points[maxY].getY() < points[i].getY()) ? (i) : (maxY);
		}
		//onHull[minY] = true;
		hull.push(points[minY]);
		int i = 0;
		do{
			System.out.println("nnnn");
			if (onHull[k]) continue;
			System.out.println("k = " + k);
			double minAngle = Double.MAX_VALUE;
			for (int j = 0; j < points.length; ++j){
				if (onHull[j] || hull.peek() == points[j]) continue;
				if (hull.peek().minPolarAngle(points[k], points[j]) < minAngle){
					minAngle = hull.peek().polarAngle(points[j]);
					k = j;
				}
			}
			onHull[k] = true;
			hull.push(points[k]);
		} while (hull.peek() != points[maxY]);
		
		do{
			System.out.println("nnnn");
			int k = (int)(Math.random() * points.length);
			if (onHull[k]) continue;
			System.out.println("k = " + k);
			double minAngle = Double.MAX_VALUE;
			for (int j = 0; j < points.length; ++j){
				if (onHull[j] || hull.peek() == points[j]) continue;
				if (hull.peek().minPolarAngle(points[k], points[j]) + Math.PI < minAngle){
					minAngle = hull.peek().polarAngle(points[j]);
					k = j;
				}
			}
			onHull[k] = true;
			hull.push(points[k]);
		} while (hull.peek() != points[minY]);
		
		convexHull = new Point2D[hull.size()];
		i = 0;
		while (!hull.isEmpty()){
			convexHull[i] = hull.pop();
			i++;
		}
		System.out.println("ok");
	}
	
	public Point2D[] getHull(){
		return convexHull;
	}
}

