package MainPkg;
import Calculus.ConvexHull2D;
import DataTypes.*;

public class Main {
	static public void main(String[] s){
		Point2D mas[] = new Point2D[11];
		mas[0] = new Point2D(1,3);
		mas[1] = new Point2D(2,6);
		mas[2] = new Point2D(4,1);
		mas[3] = new Point2D(4,4);
		mas[4] = new Point2D(5,4);
		mas[5] = new Point2D(6,6);
		mas[6] = new Point2D(6,8);
		mas[7] = new Point2D(7,4);
		mas[8] = new Point2D(9,1);
		mas[9] = new Point2D(9,6);
		mas[10] = new Point2D(10, 4);
		ConvexHull2D hull = new ConvexHull2D(mas);
		Point2D res[] = hull.getHull();
		System.out.println(res.length);
		System.out.println(res[0].getX() + " " + res[0].getY());
		System.out.println(res[1].getX() + " " + res[1].getY());
		System.out.println(res[2].getX() + " " + res[2].getY());
		System.out.println(res[3].getX() + " " + res[3].getY());
		System.out.println(res[4].getX() + " " + res[4].getY());
		System.out.println(res[5].getX() + " " + res[5].getY());
		System.out.println(res[6].getX() + " " + res[6].getY());
		System.out.println(res[7].getX() + " " + res[7].getY());
	}
}
