package DataTypes;

public class Edge {
	Vertex u;
	Edge next;
	Edge twin = null;

    public Edge(Vertex local){
        u = local;
    }
}
