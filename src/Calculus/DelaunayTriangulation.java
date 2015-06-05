package Calculus;

import DataTypes.*;

public class DelaunayTriangulation extends Triangulation {
	boolean[] mas;
	public DelaunayTriangulation(Vertex[] vertices){
		super(vertices);
		boolean delaunay = false;
		mas = new boolean[edges.size()];
		for (int i = 0; i < mas.length; ++i){
			mas[i] = false;
		}
		int id2 = 0;
		if (faces.size() == 1)
			return;
		while (!delaunay){
			delaunay = true;
			int id1 = 0;
			while (id1 < edges.size()){
				if (rebuild(edges.get(id1), id1))
					delaunay = false;
				id1++;
			}
			id2++;
		}
	}
	
	private boolean rebuild(HalfEdge e, int id){
		if (e.getTwin() == null)
			return false;
		HalfEdge e2 = e.getTwin();
		Vertex t = new Vertex(e2.getNext().getEnd().getX() - e.getNext().getEnd().getX(), e2.getNext().getEnd().getY() - e.getNext().getEnd().getY());
		Vertex v1 = new Vertex(e.getStart().getX() - e.getNext().getEnd().getX(), e.getStart().getY() - e.getNext().getEnd().getY());
		Vertex v2 = new Vertex(e.getEnd().getX() - e.getNext().getEnd().getX(), e.getEnd().getY() - e.getNext().getEnd().getY());
		double c1 = t.getX() * v1.getY() - t.getY() * v1.getX();
		double c2 = t.getX() * v2.getY() - t.getY() * v2.getX();
		if (c1 * c2 > 0)
			return false;
		double x0, x1, x2, x3;
		double y0, y1, y2, y3;
		x0 = e2.getNext().getEnd().getX(); y0 = e2.getNext().getEnd().getY();
		x1 = e.getStart().getX(); y1 = e.getStart().getY();
		x2 = e.getNext().getEnd().getX(); y2 = e.getNext().getEnd().getY();
		x3 = e.getEnd().getX(); y3 = e.getEnd().getY();
		double a = x1 * (y2 - y3) - y1 * (x2 - x3) + x2 * y3 - x3 * y2;
		double b = (Math.pow(x1, 2) + Math.pow(y1, 2)) * (y2 - y3) - y1 * ((Math.pow(x2, 2) + Math.pow(y2, 2)) - (Math.pow(x3, 2) + Math.pow(y3, 2))) + ((Math.pow(x2, 2) + Math.pow(y2, 2)) * y3 - (Math.pow(x3, 2) + Math.pow(y3, 2)) * y2); 
		double c = (Math.pow(x1, 2) + Math.pow(y1, 2)) * (x2 - x3) - x1 * ((Math.pow(x2, 2) + Math.pow(y2, 2)) - (Math.pow(x3, 2) + Math.pow(y3, 2))) + ((Math.pow(x2, 2) + Math.pow(y2, 2)) * x3 - (Math.pow(x3, 2) + Math.pow(y3, 2)) * x2);
		double d = (Math.pow(x1, 2) + Math.pow(y1, 2)) * (x2 * y3 - x3 * y2) - x1 * ((Math.pow(x2, 2) + Math.pow(y2, 2)) * y3 - (Math.pow(x3, 2) + Math.pow(y3, 2)) * y2) + y1 * ((Math.pow(x2, 2) + Math.pow(y2, 2)) * x3 - (Math.pow(x3, 2) + Math.pow(y3, 2)) * x2);
		double s = (a * (Math.pow(x0, 2) + Math.pow(y0, 2)) - b * x0  + c * y0 - d) * Math.signum(a);
		if (s >= 0)
			return false;
		/*double s1 = (x2 - x1) * (x2 - x3) + (y2 - y1) * (y2 - y3);
		double s2 = (x0 - x1) * (x0 - x3) + (y0 - y1) * (y0 - y3);
		if (s1 >= 0 && s2 >= 0)
			return false;
		double s = ((x0 - x1) * (y0 - y3) - (x0 - x3) * (y0 - y1)) * ((x2 - x1) * (x2 - x3) + (y2 - y1) * (y2 - y3)) + 
				((x0 - x1) * (x0 - x3) + (y0 - y1) * (y0 - y3)) * ((x2 - x1) * (y2 - y3) - (x2 - x3) * (y2 - y1));
		if (s >= 0)
			return false;*/
		/*if (mas[id] == true){
			System.out.println("(" + Double.toString(e.getStart().getX()) + "," + Double.toString(e.getStart().getY()) + ")" + "(" + Double.toString(e.getEnd().getX()) + "," + Double.toString(e.getEnd().getY()) + ")");
			return false;
		}
		mas[id] = true;*/
		e.setStart(e.getNext().getEnd());
		e2.setStart(e2.getNext().getEnd());
		e.getPrev().setNext(e2.getNext()); 
		e.getNext().setPrev(e2.getPrev());
		e2.getPrev().setNext(e.getNext()); 
		e2.getNext().setPrev(e.getPrev());
		e.getNext().setNext(e); 
		e.getNext().getPrev().setPrev(e);
		e2.getNext().setNext(e2); 
		e2.getNext().getPrev().setPrev(e2);
		e.setNext(e.getNext().getPrev()); 
		e.setPrev(e.getNext().getNext());
		e2.setNext(e2.getNext().getPrev()); 
		e2.setPrev(e2.getNext().getNext());
		e.getFace().setEdge(e); 
		e.getNext().setFace(e.getFace()); 
		e.getPrev().setFace(e.getFace());
		e2.getFace().setEdge(e2);
		e2.getNext().setFace(e2.getFace()); 
		e2.getPrev().setFace(e2.getFace());
		return true;
	}
}
