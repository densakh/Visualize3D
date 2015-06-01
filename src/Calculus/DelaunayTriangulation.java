package Calculus;

import DataTypes.*;

public class DelaunayTriangulation extends Triangulation {
	public DelaunayTriangulation(Vertex[] vertices){
		super(vertices);
		boolean delaunay = false;
		double x0, x1, x2, x3;
		double y0, y1, y2, y3;
		while (!delaunay){
			delaunay = true;
			int id1 = 0;
			while (id1 < faces.size()){
				System.out.println(id1);
				HalfEdge e1 = faces.get(id1).getEdge();
				while (e1.getTwin() == null)
					e1 = e1.getNext();
				HalfEdge e2 = e1.getTwin();
				int id2 = faces.indexOf(e2.getFace());
				x0 = e2.getNext().getEnd().getX(); y0 = e2.getNext().getEnd().getY();
				x1 = e1.getStart().getX(); y1 = e1.getStart().getY();
				x2 = e1.getNext().getEnd().getX(); y2 = e1.getNext().getEnd().getY();
				x3 = e1.getEnd().getX(); y3 = e1.getEnd().getY();
				double s = ((x0 - x1) * (y0 - y3) - (x0 - x3) * (y0 - y1)) * ((x2 - x1) * (x2 - x3) + (y2 - y1) * (y2 - y3)) + 
						((x0 - x1) * (x0 - x3) + (y0 - y1) * (y0 - y3)) * ((x2 - x1) * (y2 - y3) - (x2 - x3) * (y2 - y1));
				boolean rebuild = false;
				if (s < 0)
					rebuild = true;
				if (rebuild){
					
				}
				id1++;
			}
		}
	}
}
