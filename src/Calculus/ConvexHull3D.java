package Calculus;

import java.io.*;
import java.util.*;

import list.CircularDoublyLinkedList;
import DataTypes.*;

public class ConvexHull3D {
	public CircularDoublyLinkedList<Vertex> vertexlist;
	public CircularDoublyLinkedList<Edge> edgelist;
	public CircularDoublyLinkedList<Face> facelist;
	
	public void ReadVertices(String filename) throws FileNotFoundException{
		Scanner in;
		in = new Scanner(new File(filename));
		int n = in.nextInt();
		for (int i = 0; i < n; ++i){
			int x = in.nextInt();
			int y = in.nextInt();
			int z = in.nextInt();
			vertexlist.insertAtEnd(new Vertex(x, y, z));
		}
		in.close();
	}
	
	public void DoubleTriangle(){
		Vertex v0, v1, v2, v3, t;
		Face f0 = null, f1 = null;
		Edge e0, e1, e2, s;
		int vol;
		v0 = vertexlist.get(0);
		while (Collinear(v0, vertexlist.get(v0.vnum + 1), vertexlist.get(v0.vnum + 1)))
			if ((v0 = vertexlist.get(v0.vnum)) == null)
				System.exit(0);
		v1 = vertexlist.get(v0.vnum + 1);
		v2 = vertexlist.get(v1.vnum + 1);
		v0.marked = true; v1.marked = true; v2.marked = true;
		
		f0 = MakeFace(v0, v1, v2, f1);
		f1 = MakeFace(v2, v1, v0, f0);
	}
	
	public Face MakeFace(Vertex v0, Vertex v1, Vertex v2, Face f){
		Face f0;
		Edge e0, e1, e2;
		if (f == null){
			e0 = new Edge();	e1 = new Edge();	e2 = new Edge();
			edgelist.insertAtEnd(e0);	edgelist.insertAtEnd(e1);	edgelist.insertAtEnd(e2);
		}
		else{
			e0 = f.edge[2];		e1 = f.edge[1];		e2 = f.edge[0];
		}
		e0.endpts[0] = v0; e0.endpts[1] = v1;
		e1.endpts[0] = v1; e1.endpts[1] = v2;
		e2.endpts[0] = v2; e2.endpts[1] = v0;
		
		f0 = new Face();
		f0.edge[0] = e0;	f0.edge[1] = e1;	f0.edge[2] = e2;
		f0.vertex[0] = v0;	f0.vertex[1] = v1;	f0.vertex[2] = v2;
		facelist.insertAtEnd(f0);
		
		e0.adjface[0] = e1.adjface[1] = e2.adjface[2] = f0;
		
		return f0;
	}
	
	boolean Collinear(Vertex a, Vertex b, Vertex c){
		return (c.v[2] - a.v[2]) * (b.v[1] - a.v[1]) -
				(b.v[2] - a.v[2]) * (c.v[1] - a.v[1]) == 0 &&
				(b.v[2] - a.v[2]) * (c.v[0] - a.v[0]) -
				(b.v[0] - a.v[0]) * (c.v[2] - a.v[2]) == 0 &&
				(b.v[0] - a.v[0]) * (c.v[1] - a.v[1]) -
				(b.v[1] - a.v[1]) * (c.v[0] - a.v[0]) == 0;
	}
}
