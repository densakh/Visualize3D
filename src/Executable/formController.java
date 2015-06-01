package Executable;

/**
 * Created by emxot_000 on 12.05.2015.
 */

import DataTypes.HalfEdge;
import DataTypes.Vertex;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.effect.Bloom;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Line;
import javafx.scene.shape.Sphere;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import java.awt.*;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPane.getStyleClass().add("transparentScene");
        drawGrid();
    }


    public void drawGrid(){
        final PhongMaterial axisMaterial = new PhongMaterial();
        axisMaterial.setDiffuseColor(Color.rgb(0, 114, 225));
        axisMaterial.setSpecularColor(Color.rgb(10, 114, 225));


        Bloom e1 = new Bloom();
        e1.setThreshold(3);

        for (int i = 0; i < 30; ++i){
            Box localLine1 = new Box(50000, 1, 1);
            Box localLine2 = new Box(1, 1, 50000);
            localLine1.setMaterial(axisMaterial);
            localLine2.setMaterial(axisMaterial);
            localLine1.setTranslateZ(i * 100);
            localLine2.setTranslateX(i * 100);
            localLine1.setEffect(e1);
            localLine2.setEffect(e1);
            mainPane.getChildren().addAll(localLine1, localLine2);
        }

        for (int i = 0; i < 30; ++i){
            Box localLine1 = new Box(50000, 1, 1);
            Box localLine2 = new Box(1, 1, 50000);
            localLine1.setMaterial(axisMaterial);
            localLine2.setMaterial(axisMaterial);
            localLine1.setEffect(e1);
            localLine2.setEffect(e1);
            localLine1.setTranslateZ(i * - 100);
            localLine2.setTranslateX(i * - 100);
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









}
