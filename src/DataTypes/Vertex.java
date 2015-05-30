package DataTypes;

public class Vertex{
	private double _x;
	private double _y;
	private double _z;
	public HalfEdge rep;
	
	public Vertex(double x, double y){
		_x = x;
		_y = y;
		_z = 0;
	}
	
	public Vertex(double x, double y, double z){
		_x = x;
		_y = y;
		_z = z;
	}
	
	public double sqrLen(Vertex p){
		return Math.pow(p._x - _x, 2) + Math.pow(p._y, 2);
	}
	
	public double minPolarAngle(Vertex p1, Vertex p2){
		return (p1._x - _x) * (p2._y - _y) - (p2._x - _x) * (p1._y - _y);
	}
	
	public double polarAngle(Vertex p1){
		return Math.atan2(p1._y - _y, p1._x - _x);
	}
	
	public double distance(Vertex p1, Vertex p2){
		return Math.sqrt(Math.pow(p1._x - p2._x, 2) + Math.pow(p1._y - p2._y, 2));
	}
	
	public double distance(Vertex p){
		return distance(this, p);
	}
	
	public void setX(double x){
		_x = x;
	}
	
	public void setY(double y){
		_y = y;
	}
	
	public void setZ(double z){
		_z = z;
	}
	
	public double getX(){ return _x; };
	public double getY(){ return _y; }

	public boolean compare(Vertex v){
		double eps = 0.00000001;
		if (Math.abs(_x - v._x) < eps && Math.abs(_y - v._y) < eps && Math.abs(_z - v._z) < eps)
			return true;
		else return false;
	}
	
}
