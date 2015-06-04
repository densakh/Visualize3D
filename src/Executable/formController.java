package Executable;

/**
 * Created by emxot_000 on 12.05.2015.
 */

import DataTypes.Face;
import DataTypes.HalfEdge;
import DataTypes.Vertex;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.*;
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
    public String filePath;
    double defOpacity = 1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        mainPane.getStyleClass().add("transparentScene");
        drawGrid();
    }


    public void drawGrid(){
        final PhongMaterial axisMaterial = new PhongMaterial();
        axisMaterial.setDiffuseColor(Color.rgb(68, 81, 155));
        axisMaterial.setSpecularColor(Color.rgb(140, 81, 155));


        for (int i = 0; i < 30; ++i){
            Box localLine1 = new Box(50000, 1, 1);
            Box localLine2 = new Box(1, 50000, 1);
            localLine1.setMaterial(axisMaterial);
            localLine2.setMaterial(axisMaterial);
            localLine1.setTranslateY(i * 100);
            localLine2.setTranslateX(i * 100);
            localLine1.setOpacity(defOpacity);
            localLine2.setOpacity(defOpacity);
            mainPane.getChildren().addAll(localLine1, localLine2);
        }

        for (int i = 0; i < 30; ++i){
            Box localLine1 = new Box(50000, 1, 1);
            Box localLine2 = new Box(1, 50000, 1);
            localLine1.setMaterial(axisMaterial);
            localLine2.setMaterial(axisMaterial);
            localLine1.setTranslateY(i * - 100);
            localLine2.setTranslateX(i * - 100);
            localLine1.setOpacity(defOpacity);
            localLine2.setOpacity(defOpacity);
            mainPane.getChildren().addAll(localLine1, localLine2);
        }

        final Box yAxis = new Box(2, 50000, 2);
        final PhongMaterial yMaterial = new PhongMaterial();
        yMaterial.setDiffuseColor(Color.rgb(228, 101, 246));
        yMaterial.setSpecularColor(Color.rgb(228, 101, 246));
        yAxis.setMaterial(yMaterial);
        mainPane.getChildren().add(yAxis);

        final Box xAxis = new Box(50000, 2, 2);
        final PhongMaterial xMaterial = new PhongMaterial();
        xMaterial.setDiffuseColor(Color.rgb(228, 101, 246));
        xMaterial.setSpecularColor(Color.rgb(228, 101, 246));
        xAxis.setMaterial(yMaterial);
        mainPane.getChildren().add(xAxis);


        final Box zAxis = new Box(2, 2, 50000);
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
        final PhongMaterial dotMaterial = new PhongMaterial();
        dotMaterial.setDiffuseColor(Color.rgb(235, 188, 230));
        dotMaterial.setSpecularColor(Color.rgb(235, 188, 230));
        for (int i = 0; i < dataSet.getSize(); ++i){
            Sphere dotSphere = new Sphere(2);
            dotSphere.materialProperty().set(dotMaterial);
            dotSphere.setLayoutX(dataSet.getDot(i).getX());
            dotSphere.setLayoutY(dataSet.getDot(i).getY());
            mainPane.getChildren().add(dotSphere);
        }
    }

    public void drawConvexHull() throws IOException{
        if (dataReady == false)
            return;
        drawDots();
        for (int i = 0; i < dataSet.getConvexHull().length; ++i){
            Line localLine = new Line();
            localLine.setStroke(Color.rgb(192, 132, 241));
            localLine.setStrokeWidth(2);
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
        drawDots();
        LinkedList<HalfEdge> list = dataSet.getTriangulation();
        for (int i = 0; i < list.size(); ++i){
            Line localLine = new Line();
            localLine.setStroke(Color.rgb(54, 75, 238));
            localLine.setStrokeWidth(2);
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
            wrt.write(Double.toString(dataSet.getDot(i).getX()) + " " + Double.toString(dataSet.getDot(i).getY()) + " ");
        }
        wrt.flush();
    }



    public void drawFaces() throws IOException{
        if (dataReady == false)
            return;
        drawDots();
        LinkedList<Face>  localList =  dataSet.getFacesList();

        for (int i = 0; i < localList.size(); ++i){

            Line localLine = new Line();
            localLine.setStroke(Color.rgb(198, 197, 71));
            localLine.setStrokeWidth(2);
            localLine.smoothProperty().setValue(true);
            localLine.setStartX(localList.get(i).getEdge().getNext().getStart().getX());
            localLine.setStartY(localList.get(i).getEdge().getNext().getStart().getY());
            localLine.setEndX(localList.get(i).getEdge().getNext().getEnd().getX());
            localLine.setEndY(localList.get(i).getEdge().getNext().getEnd().getY());
            mainPane.getChildren().add(localLine);


            Line localLine1 = new Line();
            localLine1.setStroke(Color.rgb(213, 132, 36));
            localLine1.setStrokeWidth(2);
            localLine1.smoothProperty().setValue(true);
            localLine1.setStartX(localList.get(i).getEdge().getPrev().getStart().getX());
            localLine1.setStartY(localList.get(i).getEdge().getPrev().getStart().getY());
            localLine1.setEndX(localList.get(i).getEdge().getPrev().getEnd().getX());
            localLine1.setEndY(localList.get(i).getEdge().getPrev().getStart().getY());
            mainPane.getChildren().add(localLine1);

        }
    }







}
