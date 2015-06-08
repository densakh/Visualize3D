package Executable;

/**
 * Created by emxot_000 on 12.05.2015.
 */

import DataTypes.Face;
import DataTypes.HalfEdge;
import DataTypes.Vertex;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;


public class formController implements Initializable{
    @FXML AnchorPane mainPane;
    DataContainer dataSet;
    boolean dataReady = false;
    boolean dimSelection = false;
    public String filePath;
    double defOpacity = 1;
    int drawMediana = 1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        mainPane.getStyleClass().add("transparentScene");
        drawGrid();
    }

    public void reDraw(){
        try {
            clearBuffer();
            drawGrid();
            drawDots();
        } catch (IOException error){

        }

    }



    public void drawGrid(){
        final PhongMaterial axisMaterial = new PhongMaterial();
        axisMaterial.setDiffuseColor(Color.rgb(68, 81, 155));
        axisMaterial.setSpecularColor(Color.rgb(140, 81, 155));


        for (int i = 0; i < 30; ++i){
            Box localLine1 = new Box(50000, drawMediana, drawMediana);
            Box localLine2 = new Box(drawMediana, 50000, drawMediana);
            localLine1.setMaterial(axisMaterial);
            localLine2.setMaterial(axisMaterial);
            localLine1.setTranslateY(i * 100 * drawMediana);
            localLine2.setTranslateX(i * 100 * drawMediana);
            localLine1.setOpacity(defOpacity);
            localLine2.setOpacity(defOpacity);
            mainPane.getChildren().addAll(localLine1, localLine2);
        }

        for (int i = 0; i < 30; ++i){
            Box localLine1 = new Box(50000, drawMediana, drawMediana);
            Box localLine2 = new Box(drawMediana, 50000, drawMediana);
            localLine1.setMaterial(axisMaterial);
            localLine2.setMaterial(axisMaterial);
            localLine1.setTranslateY(i * - 100 * drawMediana);
            localLine2.setTranslateX(i * - 100 * drawMediana);
            localLine1.setOpacity(defOpacity);
            localLine2.setOpacity(defOpacity);
            mainPane.getChildren().addAll(localLine1, localLine2);
        }

        final Box yAxis = new Box(drawMediana * 2, 50000, drawMediana * 2);
        final PhongMaterial yMaterial = new PhongMaterial();
        yMaterial.setDiffuseColor(Color.rgb(228, 101, 246));
        yMaterial.setSpecularColor(Color.rgb(228, 101, 246));
        yAxis.setMaterial(yMaterial);
        mainPane.getChildren().add(yAxis);

        final Box xAxis = new Box(50000, drawMediana * 2, drawMediana * 2);
        final PhongMaterial xMaterial = new PhongMaterial();
        xMaterial.setDiffuseColor(Color.rgb(228, 101, 246));
        xMaterial.setSpecularColor(Color.rgb(228, 101, 246));
        xAxis.setMaterial(yMaterial);
        mainPane.getChildren().add(xAxis);


        final Box zAxis = new Box(drawMediana * 2, drawMediana * 2, 50000);
        final PhongMaterial zMaterial = new PhongMaterial();
        zMaterial.setDiffuseColor(Color.rgb(228, 101, 246));
        zMaterial.setSpecularColor(Color.rgb(228, 101, 246));
        zAxis.setMaterial(yMaterial);
        mainPane.getChildren().add(zAxis);
    }



    public void drawPiramid(){
        final PhongMaterial axisMaterial = new PhongMaterial();
        axisMaterial.setDiffuseColor(Color.rgb(68, 81, 155));
        axisMaterial.setSpecularColor(Color.rgb(140, 81, 155));
        TriangleMesh mesh = new TriangleMesh();
        mesh.getTexCoords().addAll(0,0);
        mesh.getPoints().addAll(0,    0,    0,            // Point 0 - Top
                0,    300,    -150/2,         // Point 1 - Front
                -150/2, 300,    0,            // Point 2 - Left
                150/2,  300,    0,            // Point 3 - Back
                0,    300,    150/2  );

        mesh.getFaces().addAll(
                0,0,  2,0,  1,0,          // Front left face
                0,0,  1,0,  3,0,          // Front right face
                0, 0,  3,0,  4,0,          // Back right face
                0,0,  4,0,  2,0,          // Back left face
                4,0,  1,0,  2,0,          // Bottom rear face
                4,0,  3,0,  1,0           // Bottom front face
        );

        MeshView test = new MeshView(mesh);
        test.setDrawMode(DrawMode.FILL);
        test.setMaterial(axisMaterial);
        test.setTranslateX(200);
        test.setTranslateY(100);
        test.setTranslateZ(200);
        mainPane.getChildren().add(test);


    }
    public void setDataSet(Vertex[] array) throws IOException{
        clearScreen();
        dataSet = new DataContainer(array);
        dataReady = true;
        drawDots();
    }

    @FXML protected void tryToOpenFile(){
        Stage stage = (Stage) mainPane.getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        String localPath;
        try {
            filePath = fileChooser.showOpenDialog(stage).getAbsolutePath();
            localPath = filePath;
            try{
                clearScreen();
            } catch(IOException error){

            }
            try {
                dataSet = new DataContainer(localPath);
                dataReady = true;
            } catch(IOException e){

            }
            try{
                drawDots();
            } catch(IOException error){

            }
        } catch(NullPointerException nullError){

        }
    }

    public void clearScreen() throws IOException{
        if (dataReady == false)
            return;
        mainPane.getChildren().clear();
        dataReady = false;
        drawGrid();
    }

    public void clearBuffer() throws IOException{
        mainPane.getChildren().clear();
        drawGrid();
        drawDots();
    }

    public void drawDots() throws IOException{
        if (dataReady == false)
            return;
        if (dimSelection == false){
            draw2DDots();
            return;
        }
        final PhongMaterial dotMaterial = new PhongMaterial();
        dotMaterial.setDiffuseColor(Color.rgb(235, 188, 230));
        dotMaterial.setSpecularColor(Color.rgb(235, 188, 230));
        for (int i = 0; i < dataSet.getSize(); ++i){
            Sphere dotSphere = new Sphere(drawMediana * 2);
            dotSphere.materialProperty().set(dotMaterial);
            dotSphere.setLayoutX(dataSet.getDot(i).getX());
            dotSphere.setLayoutY(dataSet.getDot(i).getY());
            dotSphere.setTranslateZ(dataSet.getDot(i).getZ());
            mainPane.getChildren().add(dotSphere);
        }
    }



    public void draw2DDots() throws IOException{
        if (dataReady == false)
            return;
        final PhongMaterial dotMaterial = new PhongMaterial();
        dotMaterial.setDiffuseColor(Color.rgb(235, 188, 230));
        dotMaterial.setSpecularColor(Color.rgb(235, 188, 230));
        for (int i = 0; i < dataSet.getSize(); ++i){
            Sphere dotSphere = new Sphere(drawMediana * 2);
            dotSphere.materialProperty().set(dotMaterial);
            dotSphere.setLayoutX(dataSet.getDot(i).getX());
            dotSphere.setLayoutY(dataSet.getDot(i).getY());
            mainPane.getChildren().add(dotSphere);
        }
    }

    public void drawConvexHull() throws IOException{
        if (dataReady == false)
            return;
        clearBuffer();
        for (int i = 0; i < dataSet.getConvexHull().length; ++i){
            Line localLine = new Line();
            localLine.setStroke(Color.rgb(192, 132, 241));
            localLine.setStrokeWidth(drawMediana * 2);
            localLine.smoothProperty().setValue(true);
            localLine.setStartX(dataSet.getConvexHull()[i].getX());
            localLine.setStartY(dataSet.getConvexHull()[i].getY());
            if (i == 0){
                localLine.setEndX(dataSet.getConvexHull()[dataSet.getConvexHull().length - 1].getX());
                localLine.setEndY(dataSet.getConvexHull()[dataSet.getConvexHull().length - 1].getY());
            } else {
                if (i != dataSet.getConvexHull().length - 1){
                    localLine.setEndX(dataSet.getConvexHull()[i + 1].getX());
                    localLine.setEndY(dataSet.getConvexHull()[i + 1].getY());
                }

            }
            if (i == (dataSet.getConvexHull().length - 1)) {
                localLine.setEndX(dataSet.getConvexHull()[1].getX());
                localLine.setEndY(dataSet.getConvexHull()[1].getY());
            }
            mainPane.getChildren().add(localLine);
        }
    }

    public Vertex getCenter(){
        return dataSet.getCenter();
    }

    public void drawTriangulation() throws IOException{
        if (dataReady == false)
            return;
        if (dimSelection == false){
            draw2DTriangulation();
            return;
        }
        clearBuffer();
        LinkedList<HalfEdge> list = dataSet.getTriangulation();
        final PhongMaterial lineMaterial = new PhongMaterial();
        lineMaterial.setDiffuseColor(Color.rgb(255, 0, 0));
        lineMaterial.setSpecularColor(Color.rgb(210, 101, 246));
        for (int i = 0; i < list.size(); ++i){
            Cylinder localLine = createConnection(list.get(i).getStart(), list.get(i).getEnd());
            localLine.setMaterial(lineMaterial);
            mainPane.getChildren().add(localLine);
        }

    }


    public void draw2DTriangulation() throws IOException{
        if (dataReady == false)
            return;
        clearBuffer();
        LinkedList<HalfEdge> list = dataSet.getTriangulation();
        for (int i = 0; i < list.size(); ++i){
            Line localLine = new Line();
            localLine.setStroke(Color.rgb(54, 75, 238));
            localLine.setStrokeWidth(drawMediana * 2);
            localLine.smoothProperty().setValue(true);
            localLine.setStartX(list.get(i).getStart().getX());
            localLine.setStartY(list.get(i).getStart().getY());
            localLine.setEndX(list.get(i).getEnd().getX());
            localLine.setEndY(list.get(i).getEnd().getY());
            mainPane.getChildren().add(localLine);
        }

    }

    public void drawIsolines() throws IOException{

    }

    public void makeAScreenshot(){
        try{
            BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
            Stage dialogStage = new Stage();
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(dialogStage);
            try {
                ImageIO.write(image, "png", file);
            } catch (Exception s) {

            }
        } catch (AWTException awException){

        }
    }


    public void saveDataToFile() throws  IOException{
        Stage dialogStage = new Stage();
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showSaveDialog(dialogStage);
        FileWriter wrt = new FileWriter(file);
        wrt.write(Integer.toString(dataSet.getSize()) + "\n");
        for (int i = 0; i < dataSet.getSize(); ++i){
            wrt.write(Double.toString(dataSet.getDot(i).getX()) + " " + Double.toString(dataSet.getDot(i).getY()) + " " + Double.toString(dataSet.getDot(i).getZ()) + " ");
        }
        wrt.flush();
    }



    public void drawFaces() throws IOException{
        if (dataReady == false)
            return;
        clearBuffer();
        LinkedList<Face>  localList =  dataSet.getFacesList();

        for (int i = 0; i < localList.size(); ++i){

            Line localLine = new Line();
            localLine.setStroke(Color.rgb(198, 197, 71));
            localLine.setStrokeWidth(drawMediana * 2);
            localLine.smoothProperty().setValue(true);
            localLine.setStartX(localList.get(i).getEdge().getNext().getStart().getX());
            localLine.setStartY(localList.get(i).getEdge().getNext().getStart().getY());
            localLine.setEndX(localList.get(i).getEdge().getNext().getEnd().getX());
            localLine.setEndY(localList.get(i).getEdge().getNext().getEnd().getY());

            mainPane.getChildren().add(localLine);


            Line localLine1 = new Line();
            localLine1.setStroke(Color.rgb(213, 132, 36));
            localLine1.setStrokeWidth(drawMediana * 2);
            localLine1.smoothProperty().setValue(true);
            localLine1.setStartX(localList.get(i).getEdge().getPrev().getStart().getX());
            localLine1.setStartY(localList.get(i).getEdge().getPrev().getStart().getY());
            localLine1.setEndX(localList.get(i).getEdge().getPrev().getEnd().getX());
            localLine1.setEndY(localList.get(i).getEdge().getPrev().getEnd().getY());
            mainPane.getChildren().add(localLine1);


            Line localLine2 = new Line();
            localLine2.setStroke(Color.rgb(213, 65, 36));
            localLine2.setStrokeWidth(drawMediana * 2);
            localLine2.smoothProperty().setValue(true);
            localLine2.setStartX(localList.get(i).getEdge().getStart().getX());
            localLine2.setStartY(localList.get(i).getEdge().getStart().getY());
            localLine2.setEndX(localList.get(i).getEdge().getEnd().getX());
            localLine2.setEndY(localList.get(i).getEdge().getEnd().getY());
            mainPane.getChildren().add(localLine2);

        }
    }


    public Cylinder createConnection(Vertex a, Vertex b) {
        Point3D origin = new Point3D(a.getX(), a.getY(), a.getZ());
        Point3D target = new Point3D(b.getX(), b.getY(), b.getZ());
        Point3D yAxis = new Point3D(0, 1, 0);
        Point3D diff = target.subtract(origin);
        double height = diff.magnitude();

        Point3D mid = target.midpoint(origin);
        Translate moveToMidpoint = new Translate(mid.getX(), mid.getY(), mid.getZ());

        Point3D axisOfRotation = diff.crossProduct(yAxis);
        double angle = Math.acos(diff.normalize().dotProduct(yAxis));
        Rotate rotateAroundCenter = new Rotate(-Math.toDegrees(angle), axisOfRotation);

        Cylinder line = new Cylinder(drawMediana, height);

        line.getTransforms().addAll(moveToMidpoint, rotateAroundCenter);

        return line;
    }







}
