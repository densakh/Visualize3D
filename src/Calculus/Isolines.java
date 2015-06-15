package Calculus;

import java.util.LinkedList;
import DataTypes.*;


public class Isolines {
	private LinkedList<LinkedList<HalfEdge>> closed = null;
	private LinkedList<LinkedList<HalfEdge>> unclosed = null;
	
	public Isolines(LinkedList<HalfEdge> edges, LinkedList<Face> faces, int n){
		boolean belongs[] = new boolean[edges.size()];
		
	}
	
	public LinkedList<LinkedList<HalfEdge>> getClosedIsolines(){
		return closed;
	}
	
	public LinkedList<LinkedList<HalfEdge>> getUnclosedIsolines(){
		return unclosed;
	}
}
