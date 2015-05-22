package MainPackage;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import Calculus.ConvexHull2D;
import DataTypes.Point2D;

public class Main {
	public static void main(String[] s){
        Point2D array[] = new Point2D[1000];
        Random vasya = new Random();
        System.out.println("BUGAGA");
        for (int i = 0; i < 1000; ++i){
            array[i] = new Point2D(vasya.nextDouble() * 2,  vasya.nextDouble() * 2);
        }
        ConvexHull2D convexTest = new ConvexHull2D(array);



        try {
            convexTest.generateMathFile("vasyaMakeStuff");
        } catch (IOException error){

        }
        mathDrawer alexander = new mathDrawer();
        try {
            alexander.generateMathFile("1", convexTest.getLeftChain(), 'r');
        } catch (IOException error){

        }
        try {
            alexander.generateMathFile("2", convexTest.getRightChain(), 'b');
        } catch (IOException error){

        }

        try {
            alexander.generateMathFile("dost", array, 'y');
        } catch (IOException error){

        }
	}
}
