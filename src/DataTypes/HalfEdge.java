package DataTypes;

public class HalfEdge {
	private HalfEdge twin = null;
	private HalfEdge next = null;
	private HalfEdge prev = null;
	private Vertex tail = null;
	private Face left = null;
	
	public HalfEdge(Vertex v){
		tail = v;
	}
	
	public void setTwin(HalfEdge e){
		twin = e;
	}
	
	public void setNext(HalfEdge e){
		next = e;
	}
	
	public void setPrev(HalfEdge e){
		prev = e;
	}
	
	public void setFace(Face f){
		left = f;
	}
	
	public HalfEdge getTwin(){
		return twin;
	}
	
	public HalfEdge getNext(){
		return next;
	}
	
	public HalfEdge getPrev(){
		return prev;
	}
	
	public Face getFace(){
		return left;
	}
	
	public Vertex getStart(){
		return tail;
	}
	
	public Vertex getEnd(){
		return next.getStart();
	}
}
