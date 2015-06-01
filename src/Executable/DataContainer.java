package Executable;

import Calculus.ConvexHull2D;
import Calculus.DelaunayTriangulation;
import Calculus.Triangulation;
import DataTypes.HalfEdge;
import DataTypes.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * Created by emxot_000 on 31.05.2015.
 */
public class DataContainer {
    int size = 0;
    Vertex dotsArray[];
    ConvexHull2D localConvexHull;
    Triangulation localTriangulation;
    LinkedList<HalfEdge> list;

    DataContainer(String filename)throws FileNotFoundException {
        Scanner in = new Scanner (new File(filename));
        this.size  = in.nextInt();
        dotsArray = new Vertex[this.size];
        for (int i = 0; i < this.size; ++i){
            dotsArray[i] = new Vertex(Double.parseDouble(in.next().trim().replace(',','.')), Double.parseDouble(in.next().trim().replace(',','.')));
        }
        in.close();
        localConvexHull = new ConvexHull2D(dotsArray);
        localTriangulation = new Triangulation(dotsArray);
        list = new LinkedList<HalfEdge>(localTriangulation.getEdgesList());
    }

    DataContainer(Vertex[] array){
        this.size  = array.length;
        dotsArray = new Vertex[this.size];
        for (int i = 0; i < this.size; ++i){
            dotsArray[i] = new Vertex(array[i].getX(), array[i].getY());
        }
        localConvexHull = new ConvexHull2D(dotsArray);
        localTriangulation = new Triangulation(dotsArray);
        list = new LinkedList<HalfEdge>(localTriangulation.getEdgesList());

    }


    public Vertex getDot(int index){
        return dotsArray[index];
    }

    public int getSize(){
        return this.size;
    }

    public Vertex[] getConvexHull(){
        return localConvexHull.getHull();
    }

    public LinkedList<HalfEdge> getTriangulation(){
        return list;
    }


}
