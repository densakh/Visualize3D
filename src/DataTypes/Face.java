package DataTypes;

public class Face {
	private HalfEdge rep;
	
	public Face(HalfEdge e){
		rep = e;
	}
	
	public HalfEdge getEdge(){
		return rep;
	}
}
