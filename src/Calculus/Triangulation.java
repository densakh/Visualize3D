package Calculus;

import DataTypes.*;

import java.util.Collections;
import java.util.LinkedList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by emxot_000 on 12.05.2015.
 */
public class Triangulation  {
    private final static double EPSILON = 0.00001;
    private Point2D pointsArray[];
    LinkedList<Section> sectionList = new LinkedList<Section>();
    private double glA, glB, glC, glD;

    public Triangulation(Point2D pointsArray[]){
        for (int i = 0; i < pointsArray.length; ++i){
            for (int j = 0; j < pointsArray.length; ++j){
                sectionList.add(i * j + j, new Section(pointsArray[i], pointsArray[j]));
            }
        }

        Collections.sort(sectionList);
        for (int i = 0; i < sectionList.size(); ++i){
            sectionList.get(i).printStruct();
            for (int j = 0; j < sectionList.size(); ++j){
                if (intersect(sectionList.get(i).getFirstPoint(), sectionList.get(i).getSecondPoint(),sectionList.get(j).getFirstPoint(),sectionList.get(j).getSecondPoint())){
                    System.out.println("Hello Wolrld");
                    sectionList.get(i).setMarked();
                } else {
                    System.out.println("Riverside");
                }
            }
        }
/**
        for (int i = 0; i < sectionList.size(); ++i){
            if (sectionList.get(i).isMarked()){
                System.out.println("lala");
            } else {
                System.out.println("Non marked");
            }
                //sectionList.remove(i);
        }
 */


    }


    public void rebuild(){
        System.out.println(Double.toString(sectionList.size()));
        Section a = sectionList.getLast();
        int i = 0;
        int current = sectionList.size();

            while (current > 0){
            if (sectionList.get(current - 1).isMarked()){
                sectionList.remove(current - 1);
            } else {
               // System.out.println("Welcome");
            }
                //sectionList.remove(i);
            //System.out.println(i);
               --current;
        }

        System.out.println(sectionList.size());
    }




    public void generateMathFile(String filename) throws IOException{

        String dir = System.getProperty("user.dir");
        String localname = dir +  "\\" + filename + ".m";
        File out = new File(localname);
        FileWriter wrt = new FileWriter(out);


        for (int i = 0; i < sectionList.size(); ++i){
            wrt.write("plot([");
            wrt.write(Double.toString(sectionList.get(i).getFirstPoint().getX()) + ",");
            wrt.write(Double.toString(sectionList.get(i).getSecondPoint().getX()));
            wrt.write("], [");
            wrt.write(Double.toString(sectionList.get(i).getFirstPoint().getY()) + ",");
            wrt.write(Double.toString(sectionList.get(i).getSecondPoint().getY()));
            wrt.write("], 'Color', 'g') \n");
            wrt.write("hold on \n");
        }

        wrt.flush();
    }


    private double area (Point2D a, Point2D  b, Point2D  c) {
        return (b.getX() - a.getX()) * (c.getY() - a.getY()) - (b.getY() - a.getY()) * (c.getX() - a.getX());
    }


    private boolean intersect_1 (double a, double b, double c, double d) {
        glA = a;
        glB = b;
        glC = c;
        glD = d;
        if (glA > glB)  swapAB(glA, glB);
        if (glC > glD)  swapCD(glC, glD);
        return (max(glA,glC) <= min(glB,glD));
    }

    private boolean intersect (Point2D a, Point2D  b, Point2D  c, Point2D  d) {
        return (intersect_1 (a.getX(), b.getX(), c.getX(), d.getX()) && intersect_1 (a.getY(), b.getY(), c.getY(), d.getY()) && (area(a,b,c) * area(a,b,d) <= 0)  && (area(c,d,a) * area(c,d,b) <= 0));
    }

    private void swapAB(double x, double y){
        double localX = glA;
        glA = y;
        glB = localX;
    }

    private void swapCD(double x, double y){
        double localX = glC;
        glC = y;
        glD = localX;
    }

    private double min(double x, double y){
        if (x > y){
            return y;
        } else
            return x;
    }

    private double max(double x, double y){
        if (x > y){
            return x;
        } else
            return y;
    }
}
