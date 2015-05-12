package DataTypes;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by emxot_000 on 12.05.2015.
 */
public class Section  implements Comparable<Section>{
        private Point2D a, b;
    private double length;

    public Section(Point2D first, Point2D second){
        a = first;
        b = second;
        length = Math.sqrt(Math.pow(Math.abs(first.getX() - second.getX()), 2) + Math.pow(Math.abs(first.getY() - second.getY()), 2));
    }

    public double getLength(){
        return length;
    }


    public Point2D getFirstPoint(){
        return a;
    }

    public Point2D getSecondPoint(){
        return b;
    }


    public void printStruct(){
        System.out.println(a.getX() + " " + a.getY() + " " + b.getX() + " " + b.getY() + " " + length);
    }



    @Override public int compareTo(Section o) {
        double comparedSize = o.getLength();
        if (this.getLength() > comparedSize) {
            return 1;
        } else if (this.getLength() == comparedSize) {
            return 0;
        } else {
            return -1;
        }
    }
}
