package Calculus;

/**
 * Created by emxot_000 on 12.05.2015.
 */

import DataStructures.DCEL;
import DataTypes.*;

import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.Stack;

public class DeloneTriangulation {
    private ConvexHull2D localConvexHull;
    private Point2D[] localSortedPoints;
    private DCEL rightDCEL, leftDCEL, middleDCEL;
    private Deque<Vertex>  stack = new ArrayDeque<Vertex>();
    private Vertex u[];
    public  DeloneTriangulation(Point2D[] array){
        Arrays.sort(array);
        u = new Vertex[array.length];
        for (int i = 0; i < array.length; ++i)
            u[i] = new Vertex(array[i]);

        rightDCEL = new DCEL();
        leftDCEL = new DCEL();
        localConvexHull = new ConvexHull2D(array);
        rightDCEL.addArray(localConvexHull.getRightChain());
        leftDCEL.addArray(localConvexHull.getLeftChain());
        Point2D[] setDiff = array;
        int i = 0;
        while (setDiff.length > 2){
            Point2D[] diff = getSetDifference(localConvexHull.getHull(), setDiff);
            try{
                localConvexHull.generateMathFile("test");
            } catch(IOException exception){

            }
            ++i;
            if (diff.length == 0)
                break;
            localConvexHull = new ConvexHull2D(diff);
            rightDCEL.addArray(localConvexHull.getRightChain());
            leftDCEL.addArray(localConvexHull.getLeftChain());
            setDiff = diff;
        }
    }




    private void triangulate(Vertex[] localArray){

    }

    private  void createMathFile(String name){

    }

    private Vertex[] pointArrayToVertex(Point2D[] array){
        Vertex[] returningVector = new Vertex[array.length];
        for (int i = 0; i < array.length; ++i){
            returningVector[i] = new Vertex(array[i]);
        }
        return returningVector;
    }

    private Point2D[] getSetDifference(Point2D[] convexSet, Point2D[] fullSet){
        Point2D[] returningArray = new Point2D[fullSet.length - convexSet.length + 1];
        boolean checker = false;
        int counter = 0;
        for (int i = 0; i < fullSet.length; ++i){
            for (int j = 0; j < convexSet.length; ++j){
                if (fullSet[i] == convexSet[j]){
                    checker = true;
                }
            }
            if (checker == false) {
                returningArray[counter] = fullSet[i];
                ++counter;
            }
            checker = false;

        }
        return returningArray;
    }
    /**
    Заведем изначально пустой (STACK.SIZE() = 0);
    STACK.PUSH(u1), STACK.PUSH(u2);
    Цикл для j ← 3, 2, …, n - 1:
    v ← STACK.TOP();
    STACK.POP();
    Если CHAIN(v) ≠ CHAIN(uj), тогда:
    Цикл до тех пор, пока (STACK.SIZE() > 0):
            DCEL.ADD({uj, v})
    v ← STACK.TOP();
    STACK.POP();
    КонецЦикла;
    STACK.PUSH(uj-1)
            STACK.PUSH(uj);
    Иначе:
    Цикл до тех пор, пока
    (STACK.SIZE() > 0) и (CORRECT(STACK.TOP(),{uj, v})):
            DCEL.ADD({uj, v});

    v ← STACK.TOP();
    STACK.POP();
    КонецЦикла;
    STACK.PUSH(v);
    STACK.PUSH(uj);
    КонецЕсли;
    КонецЦикла;
    STACK.POP();
    Цикл до тех пор, пока (STACK.SIZE() > 0):
            DCEL.ADD({un, v});
    v ← STACK.TOP();
    STACK.POP();
    КонецЦикла;
*/

}
