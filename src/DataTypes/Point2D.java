package DataTypes;

public class Point2D {
	private double _x;
	private double _y;
	public Point2D(double x, double y){
		_x = x;
		_y = y;
	}
	
	public double sqrLen(Point2D p){
		return Math.pow(p._x - _x, 2) + Math.pow(p._y, 2);
	}
	
	public double minPolarAngle(Point2D p1, Point2D p2){
		return (p1._x - _x) * (p2._y - _y) - (p2._x - _x) * (p1._y - _y);
	}
	
	public double polarAngle(Point2D p1){
		return Math.atan2(p1._y - _y, p1._x - _x);
	}
	
	public double distance(Point2D p1, Point2D p2){
		return Math.sqrt(Math.pow(p1._x - p2._x, 2) + Math.pow(p1._y - p2._y, 2));
	}
	
	public double distance(Point2D p){
		return distance(this, p);
	}
	
	public void changeX(double x){
		_x = x;
	}
	
	public void changeY(double y){
		_y = y;
	}
	
	public double getX(){ return _x; };
	public double getY(){ return _y; };
}
