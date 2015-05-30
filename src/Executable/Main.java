package Executable;

import Calculus.ConvexHull2D;

import Calculus.Triangulation;

import DataTypes.HalfEdge;
import DataTypes.Vertex;
import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBuilder;
import javafx.scene.transform.Rotate;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.PerspectiveCamera;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by emxot_000 on 12.05.2015.
 */
public class Main extends Application {

    private static final int VIEWPORT_SIZE = 800;

    private static final double MODEL_SCALE_FACTOR = 40;
    private static final double MODEL_X_OFFSET = 0;
    private static final double MODEL_Y_OFFSET = 0;
    private static final double MODEL_Z_OFFSET = VIEWPORT_SIZE / 2;

    //  private static final String textureLoc = "http://icons.iconarchive.com/icons/aha-soft/desktop-halloween/256/Skull-and-bones-icon.png"; // icon license linkware, backlink to http://www.aha-soft.com
    private static final String textureLoc = "http://d366vafdki9sdu.cloudfront.net/files/37/2688/seamless-marble-textures-pack-screenshots-4.jpg";
    // texture sourced from: http://www.creattor.com/textures-patterns/seamless-marble-textures-pack-2688
    // texture license: http://creativecommons.org/licenses/by-nc/3.0/ => Creative Commons Attribution-NonCommercial 3.0 Unported

    private Image texture;
    private PhongMaterial texturedMaterial = new PhongMaterial();

    private MeshView meshView = loadMeshView();

    private MeshView loadMeshView() {
        float[] points = {
                -5, 5, 0,
                -5, -5, 0,
                5, 5, 0,
                5, -5, 0
        };
        float[] texCoords = {
                1, 1,
                1, 0,
                0, 1,
                0, 0
        };
        int[] faces = {
                2, 2, 1, 1, 0, 0,
                2, 2, 3, 3, 1, 1
        };

        TriangleMesh mesh = new TriangleMesh();
        mesh.getPoints().setAll(points);
        mesh.getTexCoords().setAll(texCoords);
        mesh.getFaces().setAll(faces);

        return new MeshView(mesh);
    }

    private Group buildScene() {
        meshView.setTranslateX(VIEWPORT_SIZE / 2 + MODEL_X_OFFSET);
        meshView.setTranslateY(VIEWPORT_SIZE / 2 * 9.0 / 16 + MODEL_Y_OFFSET);
        meshView.setTranslateZ(VIEWPORT_SIZE / 2 + MODEL_Z_OFFSET);
        meshView.setScaleX(MODEL_SCALE_FACTOR);
        meshView.setScaleY(MODEL_SCALE_FACTOR);
        meshView.setScaleZ(MODEL_SCALE_FACTOR);

        return new Group(meshView);
    }




    private SubScene createScene3D(Group group) {
        SubScene scene3d = new SubScene(group, VIEWPORT_SIZE, VIEWPORT_SIZE * 9.0/16, true, SceneAntialiasing.DISABLED);
        scene3d.setFill(Color.rgb(10, 10, 40));
        scene3d.setCamera(new PerspectiveCamera());
        return scene3d;
    }





    @Override public void start(Stage primaryStage) throws Exception{

       // Group group = buildScene();
        //((Pane) scene2.getRoot()).getChildren().add(scene1.getRoot());
        /**
        Parent form = FXMLLoader.load(getClass().getResource("form.fxml"));
        Xform test = new Xform();
        primaryStage.setTitle("DELVIS");
        StackPane root = new StackPane();
        AnchorPane testy = new AnchorPane();
        testy.getChildren().addAll(form);


        Scene scene = new Scene (root, 1024, 768, false);
        SubScene subScene = new SubScene(testy, 1024, 768, true, SceneAntialiasing.DISABLED);
        root.getChildren().addAll(subScene);
        root.getChildren().addAll(getOverlay());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getIcons().addAll(new Image("icon.png"));
        */


        /**
        WebView webView = new WebView();
        webView.getEngine().load("http://www.vk.com");
        StackPane root = new StackPane();
        root.getChildren().addAll(webView, getOverlay());
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
        /*/

    }

    private Pane getOverlay() {
        StackPane p = new StackPane();
        Button test = new Button();
        test.textProperty().setValue("HELLO");
        Rectangle r = RectangleBuilder.create()
                .height(100).width(100)
                .arcHeight(40).arcWidth(40)
                .stroke(Color.RED)
                .fill(Color.web("red", 0.1))
                .build();

        Text txt= TextBuilder.create().text("OVERLAY")
                .font(Font.font("Arial", FontWeight.BOLD, 18))
                .fill(Color.BLUE)
                .build();
        p.getChildren().addAll(r, txt, test);
        return p;
    }


   static public void generateMathFile(String filename, Vertex[] array, Vertex massCenter, Vertex nearest) throws IOException {

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
        wrt.write("],  'o', 'Color', 'R'); \n");
        wrt.write("hold all; \n");
        wrt.write("plot( " + massCenter.getX() + " , " + massCenter.getY() + ", 'o', 'Color', 'B', 'Linewidth', 3); \n");
       wrt.write("plot( " + nearest.getX() + " , " + nearest.getY() + ", 'o', 'Color', 'G', 'Linewidth', 3); \n");
        wrt.flush();
    }


    static public void generateTriangulationFile(String filename,Triangulation test) throws IOException {

        String dir = System.getProperty("user.dir");
        String localname = dir +  "\\" + filename + ".m";
        File out = new File(localname);
        FileWriter wrt = new FileWriter(out);

        LinkedList<HalfEdge> list = test.getEdgesList();
        for (int i = 0; i < list.size(); ++i){
            wrt.write("plot( [" + list.get(i).getStart().getX() + " , " + list.get(i).getEnd().getX()  + "], ["  + list.get(i).getStart().getY() + " , " + list.get(i).getEnd().getY() + " ],  'Color', 'G', 'Linewidth', 2); \n");
            wrt.write("hold all; \n");
        }

        wrt.flush();
    }


    public static void main(String[] args){
        //launch(args); //return;
        double v1, v2;
        Random a = new Random();
        Random b = new Random();
        Random c = new Random();
        Random g = new Random();
        Vertex array[] = new Vertex[1000];
        for (int i = 0; i < 1000; ++i){
            array[i] = new Vertex(a.nextDouble() * b.nextInt(), c.nextDouble() * g.nextInt());
        }

        Triangulation test = new Triangulation(array);

        //Vertex massCenter = test.getMassCenter();
       // Vertex nearest = test.getNearest();
        /**
        try {
            generateMathFile("Denis", array, massCenter, nearest);
        } catch (IOException error){

        }
         */

        try {

            generateTriangulationFile("testtests", test);
        } catch (IOException error){

        }
    }


}
