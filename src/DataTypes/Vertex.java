package DataTypes;

public class Vertex {
	public int[] v = new int[3];
	public int vnum;
	public boolean onhull;
	public boolean marked;
	public Edge duplicate;
	
	static int idx = 0;
	
	public Vertex(int x, int y, int z){
		v[0] = x;
		v[1] = y;
		v[2] = z;
		onhull = false;
		marked = false;
		duplicate = null;
		vnum = idx;
		idx++;
	}
}
