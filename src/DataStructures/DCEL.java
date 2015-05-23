package DataStructures;

import DataTypes.Edge;
import DataTypes.Point2D;
import DataTypes.Vertex;

import java.util.LinkedList;

/**
 * Created by emxot_000 on 22.05.2015.
 */
public class DCEL {
    LinkedList<Edge> list = new LinkedList<Edge>();

    public DCEL(){
    }

    public void addArray(Point2D[] array){
        int length = list.size();
        for (int i = length; i < length + array.length; ++i){
            list.add(i, new Edge(new Vertex(array[i - length])));
        }

    }

    public void add(Edge x){
    list.add(x);
    }
}



