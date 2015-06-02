package Calculus;

import java.util.Arrays;
import java.util.LinkedList;

import DataTypes.*;

public class Triangulation {
	
	private double xc = 0, yc = 0;
	private Vertex nearest = null;
	private Vertex massCenter = null;
	protected LinkedList<HalfEdge> edges = new LinkedList<HalfEdge>();
	protected LinkedList<Face> faces = new LinkedList<Face>();
	public Triangulation(Vertex[] vertices){
		/*Build convex hull*/
		ConvexHull2D Chull = new ConvexHull2D(vertices);
		Vertex[] hull = Arrays.copyOfRange(Chull.getHull(), 0, Chull.getSize() - 1);
		/*Find mass center of hull*/
		calcMassCenter(hull);
		/*Find nearest vertex to mass center*/
		massCenter = new Vertex(xc, yc);
		nearest = vertices[0];
		double distance = massCenter.distance(nearest);
		for (int i = 0; i < vertices.length; ++i){
			if (massCenter.distance(vertices[i]) < distance){
				nearest = vertices[i];
				distance = massCenter.distance(vertices[i]); 
			}
		}
		Vertex[] copy = new Vertex[vertices.length - 1];
		boolean onHull = false;
		if (Arrays.asList(Chull.getHull()).contains(nearest)){
			onHull = true;
			nearest = vertices[0];
			for (int i = 1; i < vertices.length; ++i){
				if (vertices[i].getY() < nearest.getY())
					nearest = vertices[i];
				if (vertices[i].getY() == nearest.getY())
					if (vertices[i].getX() > nearest.getY())
						nearest = vertices[i];
			}
		}
		int i1 = 0, j1 = 0;
		while (i1 < vertices.length){
			if (vertices[i1] == nearest){
				i1++;
				continue;
			}
			copy[j1] = vertices[i1];
			j1++; i1++;
		}
		
		/*Calculate polar angle*/
		double angles[] = new double[copy.length];
		for (int i = 0; i < copy.length; ++i){
			angles[i] = Math.atan2(copy[i].getY() - nearest.getY(), copy[i].getX() - nearest.getX());
			if (angles[i] < 0)
				angles[i] = 2 * Math.PI + angles[i];
		}
	
		/*Sort by angle*/
		for (int i = 0; i < copy.length - 1; ++i)
			for (int j = 0; j < copy.length - 1; ++j)
				if (angles[j] > angles[j+1]){
					double tmp = angles[j];
					angles[j] = angles[j+1];
					angles[j+1] = tmp;
					Vertex tmp1 = copy[j];
					copy[j] = copy[j+1];
					copy[j+1] = tmp1;
				}
		if (!onHull){
			computeTriangulation(copy);
		}
		else{
			computeTriangulationFromHull(copy);
		}
		int id1 = 0;
		boolean convex = false;
		while (!convex){
			convex = true;
			id1 = 0;
			while(true) {
				id1++;
				if (id1 == edges.size()){
					id1 = 0;
					break;
				}
				if (edges.get(id1).getTwin() != null){
					continue;
				}
				HalfEdge e1 = edges.get(id1); 
				HalfEdge e2 = e1.getNext();
				while (e2.getTwin() != null){
					e2 = e2.getTwin().getNext();
				}
				Vertex t = new Vertex(e2.getEnd().getX() - e1.getStart().getX(), e2.getEnd().getY() - e1.getStart().getY());
				Vertex v1 = new Vertex(nearest.getX() - e1.getStart().getX(), nearest.getY() - e1.getStart().getY());
				Vertex v2 = new Vertex(e1.getEnd().getX() - e1.getStart().getX(), e1.getEnd().getY() - e1.getStart().getY());
				double c1 = t.getX() * v1.getY() - t.getY() * v1.getX();
				double c2 = t.getX() * v2.getY() - t.getY() * v2.getX();
				boolean build = false;
				if (c1 > 0 && c2 > 0)
					build = true;
				if (build){
					convex = false;
					HalfEdge tmp1 = new HalfEdge(e1.getStart());
					HalfEdge tmp2 = new HalfEdge(e2.getEnd());
					HalfEdge tmp3 = new HalfEdge(e2.getStart());
					tmp1.setNext(tmp2);		tmp2.setNext(tmp3); 	tmp3.setNext(tmp1);
					tmp1.setPrev(tmp3);		tmp2.setPrev(tmp1); 	tmp3.setPrev(tmp2);
					faces.add(new Face(tmp1));
					tmp1.setFace(faces.getLast());
					tmp2.setFace(faces.getLast());
					tmp3.setFace(faces.getLast());
					tmp2.setTwin(e2);	tmp3.setTwin(e1);
					e2.setTwin(tmp2);	e1.setTwin(tmp3);
					edges.add(tmp1); 	edges.add(tmp2);	edges.addFirst(tmp3);
				}
			}
		}
	}
	
	private void computeTriangulationFromHull(Vertex[] copy){
		for (int i = 0; i < copy.length - 1; ++i)
			edges.add(new HalfEdge(nearest));
		for (int i = 0; i < copy.length - 2; ++i){
			edges.add(new HalfEdge(copy[i]));
			edges.add(new HalfEdge(copy[i + 1]));
			int size = edges.size();
			edges.get(size - 2).setNext(edges.getLast());
			edges.get(size - 2).setPrev(edges.get(i));
			edges.getLast().setNext(edges.get(i));
			edges.getLast().setPrev(edges.get(size - 2));
			edges.get(i).setNext(edges.get(size - 2));
			edges.get(i).setPrev(edges.getLast());
			edges.getLast().setTwin(edges.get(i + 1));
			edges.get(i + 1).setTwin(edges.getLast());
			faces.add(new Face(edges.getLast()));
			edges.getLast().setFace(faces.getLast());
			edges.get(size - 2).setFace(faces.getLast());
			edges.get(i).setFace(faces.getLast());
		}
		edges.add(new HalfEdge(copy[copy.length - 2]));
		edges.add(new HalfEdge(copy[copy.length - 1]));
		int size = edges.size();
		edges.get(size - 2).setNext(edges.getLast());
		edges.get(size - 2).setPrev(edges.get(copy.length - 2));
		edges.getLast().setNext(edges.get(copy.length - 2));
		edges.getLast().setPrev(edges.get(size - 2));
		edges.get(copy.length - 2).setNext(edges.get(size - 2));
		edges.get(copy.length - 2).setPrev(edges.getLast());
		faces.add(new Face(edges.getLast()));
		edges.getLast().setFace(faces.getLast());
		edges.get(size - 2).setFace(faces.getLast());
		edges.get(copy.length - 2).setFace(faces.getLast());
	}
	
	private void computeTriangulation(Vertex[] copy){
		for (int i = 0; i < copy.length; ++i)
			edges.add(new HalfEdge(nearest));
	
		int size = edges.size();
		for (int i = 0; i < copy.length - 1; ++i){
			edges.add(new HalfEdge(copy[i]));
			edges.add(new HalfEdge(copy[i+1]));
			size += 2;
			edges.get(size - 2).setNext(edges.getLast());
			edges.getLast().setPrev(edges.get(size - 2));
			edges.getLast().setNext(edges.get(i));
			edges.get(size - 2).setPrev(edges.getLast().getNext());
			edges.get(i).setNext(edges.get(size - 2));
			edges.get(i).setPrev(edges.getLast());
			edges.getLast().setTwin(edges.get(i+1));
			edges.get(i+1).setTwin(edges.getLast());
			faces.add(new Face(edges.get(i)));
			edges.get(i).setFace(faces.getLast());
			edges.get(size - 2).setFace(faces.getLast());
			edges.getLast().setFace(faces.getLast());
		}
		edges.add(new HalfEdge(copy[copy.length - 1]));
		edges.add(new HalfEdge(copy[0]));
		size += 2;
		edges.get(size - 2).setNext(edges.getLast());
		edges.getLast().setPrev(edges.get(size - 2));
		edges.getLast().setNext(edges.get(copy.length - 1));
		edges.get(size - 2).setPrev(edges.getLast().getNext());
		edges.get(copy.length - 1).setNext(edges.get(size - 2));
		edges.get(copy.length - 1).setPrev(edges.getLast());
		edges.getLast().setTwin(edges.get(0));
		edges.get(0).setTwin(edges.getLast());
		faces.add(new Face(edges.get(copy.length - 1)));
		edges.get(copy.length - 1).setFace(faces.getLast());
		edges.get(size - 2).setFace(faces.getLast());
		edges.getLast().setFace(faces.getLast());
	}
	
	public LinkedList<HalfEdge> getEdgesList(){
		return edges;
	}
	
	public LinkedList<Face> getFacesList(){
		return faces;
	}
	
	public Vertex getMassCenter(){
		return massCenter;
	}
	
	public Vertex getNearest(){
		return nearest;
	}
	
	private double square(double x1, double y1, double x2, double y2, double x3, double y3){
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
