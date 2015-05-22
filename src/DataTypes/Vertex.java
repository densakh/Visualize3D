package DataTypes;

public class Vertex {
	private Point2D _v;
	private double _z;
	
	public Vertex(Point2D p, double z){
		_v = new Point2D(p);
		_z = z;
	}
	
	public Vertex(double x, double y, double z){
		_v = new Point2D(x, y);
		_z = z;
	}
	
}
