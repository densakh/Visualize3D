package Calculus;

import DataTypes.*;

import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by emxot_000 on 12.05.2015.
 */
public class Triangulation  {

    private Point2D pointsArray[];
    LinkedList<Section> sectionList = new LinkedList<Section>();


    public Triangulation(Point2D pointsArray[]){
        for (int i = 0; i < pointsArray.length; ++i){
            for (int j = 0; j < pointsArray.length; ++j){
                sectionList.add(i * j + j, new Section(pointsArray[i], pointsArray[j]));
            }
        }
        Collections.sort(sectionList);
        for (int i = 0; i < pointsArray.length *pointsArray.length; ++i) {
            sectionList.get(i).printStruct();
        }
    }
}
