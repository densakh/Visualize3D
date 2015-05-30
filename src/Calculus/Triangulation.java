package Calculus;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

import DataTypes.*;

public class Triangulation {
	private double xc = 0, yc = 0;
	private Vertex nearest = null;
	private Vertex massCenter = null;
	private LinkedList<HalfEdge> edges = new LinkedList<HalfEdge>();
	private LinkedList<Face> faces = new LinkedList<Face>();
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
		
		//ListIterator<HalfEdge> it1 = edges.listIterator(0);
		//ListIterator<HalfEdge> it2 = edges.listIterator(0);
		int id1 = 0, id2 = 0;
		boolean convex = false;
		while (!convex){
			convex = true;
			//it1 = edges.listIterator(2);
			id1 = 2;
			while (id1 != 1){
				id1++;
				if (id1 == edges.size())
					id1 = 0;
				if (edges.get(id1).getTwin() != null){
					continue;
				}
				id2 = 0;
				HalfEdge e1 = edges.get(id1); 
				while (!(e1.getEnd().compare(edges.get(id2).getStart())) && (edges.get(id2).getTwin() != null)){
					id2++;
					if (id2 == edges.size());
						id2 = 0;
				}
				HalfEdge e2 = edges.get(id2);
				if (getAngle(e1.getEnd(), e1.getStart()) > getAngle(e2.getEnd(), e1.getStart())){
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
	
	private double getAngle(Vertex v1, Vertex v2){
		double res = Math.atan2(v1.getY() - v2.getY(), v1.getX() - v2.getX());
		res = (res > 0)?(res):(2 * Math.PI + res);
		return res;
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
