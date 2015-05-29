package Calculus;

import java.io.*;
import java.util.Arrays;
import java.util.Stack;

import DataTypes.*;

public class ConvexHull2D {
	private boolean onHull[];
	private Vertex convexHull[];
	private int chainCent;

	public ConvexHull2D(Vertex[] points){
		Stack<Vertex> hull = new Stack<Vertex>();
		int minY = 0;
		int maxY = 0;
		onHull = new boolean[points.length];
		onHull[0] = false;
		for (int i = 1; i < points.length; ++i){
			onHull[i] = false;
			if (points[minY].getY() == points[i].getY())
				minY = (points[minY].getX() < points[i].getX()) ? (minY) : (i);
			else
				minY = (points[minY].getY() < points[i].getY()) ? (minY) : (i);
			
			if (points[maxY].getY() == points[i].getY())
				maxY = (points[maxY].getX() < points[i].getX()) ? (i) : (maxY);
			else
				maxY = (points[maxY].getY() < points[i].getY()) ? (i) : (maxY);
		}
		hull.push(points[minY]);

		int i = 0;
		int k = 0;
		do{
			if (k == onHull.length)
				k = 0;
			if (onHull[k]) {
				++k;
				continue;
			}
			double minAngle = Double.MAX_VALUE;
			for (int j = 0; j < points.length; ++j){
				if (onHull[j] || hull.peek() == points[j]) continue;
				double angle = hull.peek().polarAngle(points[j]);
				if (angle < 0)
					angle = 2 *Math.PI + angle;
				if (angle < minAngle){
					minAngle = angle;
					k = j;
				}
			}
			onHull[k] = true;
			hull.push(points[k]);
		} while (hull.peek() != points[maxY]);
		
		k = 0;
		do{
			if (k == onHull.length)
				k = 0;
			if (onHull[k]) {
				++k;
				continue;
			}
			double minAngle = Double.MAX_VALUE;
			for (int j = 0; j < points.length; ++j){
				if (onHull[j] || hull.peek() == points[j]) continue;
				double angle = Math.PI + hull.peek().polarAngle(points[j]);
				if (angle < minAngle){
					minAngle = angle;
					k = j;
				}
			}
			onHull[k] = true;
			hull.push(points[k]);
		} while (hull.peek() != points[minY]);
		
		convexHull = new Vertex[hull.size()];
		i = 0;
		while (!hull.isEmpty()){
			convexHull[i] = hull.pop();
			if (convexHull[i] == points[maxY])
				chainCent = i;
			i++;
		}
	}
	
	public Vertex[] getHull(){
		return convexHull;
	}
	
	public int getSize(){
		return convexHull.length;
	}
	
	public Vertex[] getLeftChain(){
		return Arrays.copyOfRange(convexHull, 1, chainCent + 1);
	}
	
	public Vertex[] getRightChain(){
		return Arrays.copyOfRange(convexHull, chainCent + 1, convexHull.length);
	}

    public int getChainOffset(){ return chainCent; }

    public void generateMathFile(String filename) throws IOException {

        String dir = System.getProperty("user.dir");
        String localname = dir +  "\\" + filename + ".m";
        File out = new File(localname);
        FileWriter wrt = new FileWriter(out);

        wrt.write("plot([");
        for (int i = 0; i < convexHull.length; ++i){
            wrt.write(Double.toString(convexHull[i].getX()) + ",");
        }
        wrt.write("], [");
        for (int i = 0; i < convexHull.length; ++i){
            wrt.write(Double.toString(convexHull[i].getY())+ ",");
        }
        wrt.write("],  'MarkerSize', 10,   'Color', 'g'); \n");
        wrt.write("hold all \n");
        wrt.flush();
        wrt.close();
    }

    public static void write(String fileName, String text) {
        //Определяем файл
        File file = new File(fileName);

        try {
            if(!file.exists()){
                file.createNewFile();
            }

            PrintWriter out = new PrintWriter(file.getAbsoluteFile());

            try {
                out.print(text);
            } finally {
                out.close();
            }
        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }





    
}
