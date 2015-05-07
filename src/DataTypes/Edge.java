package DataTypes;

public class Edge {
	public Face[] adjface = new Face[2];
	public Vertex[] endpts = new Vertex[2];
	public Face newface;
	boolean delete;
	
	public Edge(){
		adjface[0] = adjface[1] = newface = null;
		endpts[0] = endpts[1] = null;
		delete = false;
	}
}
