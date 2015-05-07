package DataTypes;

public class Face {
	public Edge[] edge = new Edge[3];
	public Vertex[] vertex = new Vertex[3];
	boolean visible;
	
	public Face(){
		for (int i = 0; i < 3; ++i){
			edge[i] = null;
			vertex[i] = null;
		}
		visible = false;
	}
}
