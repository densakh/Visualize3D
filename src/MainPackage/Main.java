package MainPackage;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

import Calculus.ConvexHull2D;
import DataTypes.Point2D;

public class Main {
	public static void main(String[] s){
        Point2D array[] = new Point2D[10];
        Random vasya = new Random();
        System.out.println("BUGAGA");
        for (int i = 0; i < 10; ++i){
            array[i] = new Point2D(vasya.nextDouble() * 200,  vasya.nextDouble() * 200);
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
            alexander.generateMathFile("dost", array, 'm');
        } catch (IOException error){

        }

        System.out.print(convexTest.getLeftChain()[0].getX() + " " + convexTest.getLeftChain()[0].getY() + ", ");
        System.out.println();
        System.out.print(convexTest.getRightChain()[convexTest.getRightChain().length - 1].getX() + " " + convexTest.getRightChain()[convexTest.getRightChain().length - 1].getY() + ", ");
        System.out.println();
        System.out.print(convexTest.getRightChain()[0].getX() + " " + convexTest.getRightChain()[0].getY() + ", ");
        System.out.println();
        System.out.print(convexTest.getLeftChain()[convexTest.getLeftChain().length - 1].getX() + " " + convexTest.getLeftChain()[convexTest.getLeftChain().length - 1].getY() + ", ");

    }

}
