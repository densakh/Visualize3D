package Calculus;

/**
 * Created by emxot_000 on 12.05.2015.
 */

import DataStructures.DCEL;
import DataTypes.*;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class DeloneTriangulation {
    private ConvexHull2D localConvexHull;
    private Point2D pointsArray[];
    private Deque<Vertex>  stack = new ArrayDeque<Vertex>();


    public void DeloneTriangulation(DCEL local){
        
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
