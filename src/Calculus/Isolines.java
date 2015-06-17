package Calculus;

import java.util.LinkedList;
import DataTypes.*;


public class Isolines {
	private LinkedList<LinkedList<HalfEdge>> closed = new LinkedList<LinkedList<HalfEdge>>();
	private LinkedList<LinkedList<HalfEdge>> unclosed = new LinkedList<LinkedList<HalfEdge>>();
	
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
		double delt = step / 100;
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
				//System.out.println(j);
				//System.out.println("ok");
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
					//System.out.println("NULL");
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
						//list.addFirst(copy);
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
					closed.add(line);
				}
				else
					unclosed.add(line);
			}
			height -= step;
		}
	}
	
	private boolean checkEdge(HalfEdge e, double hh){
		if ((e.getEnd().getZ() > hh && e.getStart().getZ() < hh) || (e.getEnd().getZ() < hh && e.getStart().getZ() > hh))
			return true;
		return false;
	}
	
	private boolean checkFace(Face f, double hh){
		double z[] = new double[3];
		z[0] = f.getEdge().getEnd().getZ();
		z[1] = f.getEdge().getNext().getEnd().getZ();
		z[2] = f.getEdge().getStart().getZ();
		int h = 0, l = 0;
		for (int i = 0; i < 3; ++i){
			if (z[i] > hh)
				++h;
			if (z[i] < hh)
				++l;
		}
		if (Math.abs(h - l) == 1)
			return true;
		return false;
	}
	
	public LinkedList<LinkedList<HalfEdge>> getClosedIsolines(){
		return closed;
	}
	
	public LinkedList<LinkedList<HalfEdge>> getUnclosedIsolines(){
		return unclosed;
	}
}
