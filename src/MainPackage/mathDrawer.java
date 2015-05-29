package MainPackage;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by emxot_000 on 22.05.2015.
 */
public class mathDrawer {
    /*public void generateMathFile(String filename, Point2D array[], char color) throws IOException {

        String dir = System.getProperty("user.dir");
        String localname = dir +  "\\" + filename + ".m";
        File out = new File(localname);
        FileWriter wrt = new FileWriter(out);

        wrt.write("plot([");
        for (int i = 0; i < array.length; ++i){
            wrt.write(Double.toString(array[i].getX()) + ",");
        }
        wrt.write("], [");
        for (int i = 0; i < array.length; ++i){
            wrt.write(Double.toString(array[i].getY())+ ",");
        }
        wrt.write("],  'o', 'Color', '"+ color + "'); \n");
        wrt.write("hold all \n");
        wrt.flush();
    }*/
}
