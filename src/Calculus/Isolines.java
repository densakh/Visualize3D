package Calculus;

import java.awt.List;
import java.util.ArrayList;
import java.util.LinkedList;

import DataTypes.*;


public class Isolines {
	private LinkedList<LinkedList<HalfEdge>> isolines = new LinkedList<LinkedList<HalfEdge>>();
	private LinkedList<Boolean> closed = new LinkedList<Boolean>();
	
	public Isolines(LinkedList<HalfEdge> edges, LinkedList<Face> faces, int n){
		boolean iso[] = new boolean[faces.size()];
		double highest = Double.MIN_VALUE;
		double lowest = Double.MAX_VALUE;
		for (int i = 0; i < faces.size(); ++i){
			double z = edges.get(i).getStart().getZ();
			if (z > highest)
				highest = z;
			if (z < lowest)
				lowest = z;
		}
		double step = (highest - lowest) / (n + 1);
		double height = highest - step;
		for (int i = 0; i < n; ++i){
			if (height < lowest)
				break;
			for (int k = 0; k < iso.length; ++k)
				iso[k] = false;
			while (true){
				int j = 0;
				boolean end = false;
				while (!(checkEdge(edges.get(j), height) && !iso[faces.indexOf(edges.get(j).getFace())])){
					j++;
					if (j == edges.size()){
						end = true;
						break;
					}
				}
				if (end)
					break;
				HalfEdge tmp = edges.get(j);
				HalfEdge copy;
				if (checkEdge(tmp.getNext(), height))
					copy = tmp.getNext();
				else 
					copy = tmp.getPrev();
				LinkedList<HalfEdge> list = new LinkedList<HalfEdge>();
				while (!(tmp.getTwin() == null || iso[faces.indexOf(tmp.getFace())])){
					list.add(tmp);
					iso[faces.indexOf(tmp.getFace())] = true;
					tmp = tmp.getTwin();
					if (checkEdge(tmp.getNext(), height))
						tmp = tmp.getNext();
					else
						tmp = tmp.getPrev();
				}
				if (tmp.getTwin() == null){
					list.add(tmp);
					iso[faces.indexOf(tmp.getFace())] = true;
					while (copy.getTwin() != null){
						copy = copy.getTwin();
						list.addFirst(copy);
						iso[faces.indexOf(copy.getFace())] = true;
						if (checkEdge(copy.getNext(), height))
							copy = copy.getNext();
						else
							copy = copy.getPrev();
					}
					list.addFirst(copy);
				}
				LinkedList<HalfEdge> line = new LinkedList<HalfEdge>();
				for (int k = 0; k < list.size(); ++k){
					double x1 = list.get(k).getStart().getX(), x2 = list.get(k).getEnd().getX();
					double y1 = list.get(k).getStart().getY(), y2 = list.get(k).getEnd().getY();
					double z1 = list.get(k).getStart().getZ(), z2 = list.get(k).getEnd().getZ();
					double x = (height - z1) / (z2 - z1) * (x2 - x1) + x1;
					double y = (height - z1) / (z2 - z1) * (y2 - y1) + y1;
					line.add(new HalfEdge(new Vertex(x, y, height)));
				}
				for (int k = 0; k < line.size() - 1; ++k){
					line.get(k).setNext(line.get(k+1));
					line.get(k+1).setPrev(line.get(k));
				}
				if (tmp.getTwin() != null){
					line.get(0).setPrev(line.get(line.size() - 1));
					line.get(line.size() - 1).setNext(line.get(0));
					closed.add(true);
				}
				else
					closed.add(false);
				isolines.add(list);
			}
			height -= step;
		}
	}
	
	private boolean checkEdge(HalfEdge e, double hh){
		if ((e.getEnd().getZ() > hh && e.getStart().getZ() < hh) || (e.getEnd().getZ() < hh && e.getStart().getZ() > hh))
			return true;
		return false;
	}
	
	public LinkedList<LinkedList<HalfEdge>> getIsolines(){
		return isolines;
	}
	
	public LinkedList<Boolean> getStatus(){
		return closed;
	}
}
