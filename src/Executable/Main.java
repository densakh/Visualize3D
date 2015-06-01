package Executable;

import Calculus.Triangulation;
import DataTypes.HalfEdge;
import DataTypes.Vertex;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.paint.Color;
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

    final Group root = new Group();
    final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    private static final double CAMERA_INITIAL_DISTANCE = -3050;
    private static final double CAMERA_INITIAL_X_ANGLE = 5;
    private static final double CAMERA_INITIAL_Y_ANGLE = 0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;

    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;
    int dotsRandom = 100;
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
    FXMLLoader fxmlLoader;
    formController testController;


    private void buildCamera() {
        root.getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
        camera.setTranslateX(camera.getTranslateX() - 700);
        camera.setTranslateY(camera.getTranslateY() - 400);
    }




    private void handleMouse(final Scene scene, final Node root) {


        scene.setOnScroll(new EventHandler<ScrollEvent>() {
            @Override
            public void handle(ScrollEvent event) {
                camera.setTranslateZ(camera.getTranslateZ() + event.getDeltaY());
            }
        });

        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseOldX = me.getSceneX();
                mouseOldY = me.getSceneY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override public void handle(MouseEvent me) {
                mouseOldX = mousePosX;
                mouseOldY = mousePosY;
                mousePosX = me.getSceneX();
                mousePosY = me.getSceneY();
                mouseDeltaX = (mousePosX - mouseOldX);
                mouseDeltaY = (mousePosY - mouseOldY);

                double modifier = 1.0;

                if (me.isControlDown()) {
                    modifier = CONTROL_MULTIPLIER;
                }
                if (me.isShiftDown()) {
                    modifier = SHIFT_MULTIPLIER;
                }
                if (me.isPrimaryButtonDown()) {
                    cameraXform.ry.setAngle(cameraXform.ry.getAngle() - mouseDeltaX*MOUSE_SPEED*modifier*ROTATION_SPEED);
                    cameraXform.rx.setAngle(cameraXform.rx.getAngle() + mouseDeltaY*MOUSE_SPEED*modifier*ROTATION_SPEED);
                }
                else if (me.isSecondaryButtonDown()) {
                    double z = camera.getTranslateZ();
                    double newZ = z + mouseDeltaX*MOUSE_SPEED*modifier;
                    camera.setTranslateZ(newZ);
                }
                else if (me.isMiddleButtonDown()) {
                    cameraXform2.t.setX(cameraXform2.t.getX() + mouseDeltaX*MOUSE_SPEED*modifier*TRACK_SPEED);
                    cameraXform2.t.setY(cameraXform2.t.getY() + mouseDeltaY*MOUSE_SPEED*modifier*TRACK_SPEED);
                }
            }
        });
    }

    private void handleKeyboard(Scene scene, final Node root) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case NUMPAD0:
                        try {
                            testController.setDataSet(randomTest(dotsRandom));
                        } catch (IOException error){

                        }
                        break;
                    case NUMPAD1:
                        try {
                            testController.drawTriangulation();
                        } catch (IOException error){

                        }
                        break;
                    case NUMPAD2:
                        try {
                            testController.clearScreen();
                        } catch (IOException error){

                        }
                        break;
                    case NUMPAD3:
                        try {
                            testController.drawConvexHull();
                        } catch (IOException error){

                        }
                        break;

                    case W:
                        camera.setTranslateZ(camera.getTranslateZ() + 20);
                        break;
                    case S:
                        camera.setTranslateZ(camera.getTranslateZ() - 20);
                        break;
                    case D:
                        camera.setTranslateX(camera.getTranslateX() + 20);
                        break;
                    case A:
                        camera.setTranslateX(camera.getTranslateX() - 20);
                        break;
                    case Q:
                        camera.setTranslateY(camera.getTranslateY() - 20);
                        break;
                    case E:
                        camera.setTranslateY(camera.getTranslateY() + 20);
                        break;
                }
            }
        });
    }



    @Override public void start(Stage primaryStage) throws Exception{
        root.getChildren().add(world);
        root.setDepthTest(DepthTest.ENABLE);
        buildCamera();
        Xform test = new Xform();
        primaryStage.setTitle("VISUALIZE");
        StackPane root = new StackPane();
        AnchorPane testy = new AnchorPane();
        testy.getStyleClass().add("transparentScene");
        fxmlLoader = new FXMLLoader();
        Parent form = fxmlLoader.load(getClass().getResource("form.fxml").openStream());
        testController =  (formController) fxmlLoader.getController();
        testy.getChildren().addAll(form);
        Scene scene = new Scene (root, 1280, 720, false);
        SubScene subScene = new SubScene(testy, 1280, 720, true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.rgb(22, 45, 71));
        scene.setFill(Color.rgb(22, 45, 71));
        subScene.setCamera(camera);
        scene.getStylesheets().add("mainTheme.css");
        root.getChildren().addAll(subScene);
        root.getChildren().addAll(getOverlay());
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.getIcons().addAll(new Image("icon.png"));
        handleKeyboard(scene, world);
        handleMouse(scene, world);
    }




    private Pane getOverlay() {
        AnchorPane p = new AnchorPane();
        final ImageView openFileButton = new ImageView(new Image("HUD/fileOpen.png"));
        final ImageView  makeConvexHullButton = new ImageView(new Image("HUD/convexHull.png"));
        final ImageView  clearButton = new ImageView(new Image("HUD/clearScreen.png"));
        final ImageView  makeTriangulation = new ImageView(new Image("HUD/Triangulation.png"));
        final ImageView  randomTest = new ImageView(new Image("HUD/randomData.png"));
        final ImageView logoImage = new  ImageView(new Image("logo.png"));
        final ImageView copyright = new  ImageView(new Image("HUD/copyright.png"));
        final ImageView toolbar = new  ImageView(new Image("HUD/toolbar.png"));
        final ImageView saveFile = new ImageView(new Image("HUD/fileSave.png"));
        final ImageView makeScreen = new ImageView(new Image("HUD/fileScreen.png"));
        final TextField dotsQuantity = new TextField("");
        final Random dQ = new Random();

        int skipSize = 20;
        int skipConst = 80;
        int rightBorderSize = 20;
        final double deffaultOpacity = 0.7;
        final double noOpacity = 1.0;
        openFileButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                testController.tryToOpenFile();
            }
        });

        openFileButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openFileButton.opacityProperty().setValue(noOpacity);
            }
        });

        openFileButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                openFileButton.opacityProperty().setValue(deffaultOpacity);
            }
        });

        clearButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    testController.clearScreen();
                } catch (IOException error) {

                }
            }
        });

        clearButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearButton.opacityProperty().setValue(noOpacity);
            }
        });

        clearButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                clearButton.opacityProperty().setValue(deffaultOpacity);
            }
        });

        makeConvexHullButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    testController.drawConvexHull();
                } catch (IOException error) {

                }
            }
        });

        makeConvexHullButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeConvexHullButton.opacityProperty().setValue(noOpacity);
            }
        });

        makeConvexHullButton.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeConvexHullButton.opacityProperty().setValue(deffaultOpacity);
            }
        });


        makeTriangulation.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    testController.drawTriangulation();
                } catch (IOException error) {

                }
            }
        });


        makeTriangulation.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeTriangulation.opacityProperty().setValue(noOpacity);
            }
        });

        makeTriangulation.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeTriangulation.opacityProperty().setValue(deffaultOpacity);
            }
        });

        randomTest.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    //dotsRandom = Integer.parseInt(dotsQuantity.getText());
                    //testController.setDataSet(randomTest(Integer.parseInt(dotsQuantity.getText())));
                    testController.setDataSet(randomTest(dotsRandom));
                } catch (IOException error) {

                }
            }
        });

        randomTest.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                randomTest.opacityProperty().setValue(noOpacity);
            }
        });

        randomTest.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                randomTest.opacityProperty().setValue(deffaultOpacity);
            }
        });



        saveFile.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    testController.saveDataToFile();
                } catch (IOException error){

                }
            }
        });

        saveFile.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveFile.opacityProperty().setValue(noOpacity);
            }
        });

        saveFile.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                saveFile.opacityProperty().setValue(deffaultOpacity);
            }
        });


        makeScreen.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                testController.makeAScreenshot();
            }
        });

        makeScreen.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeScreen.opacityProperty().setValue(noOpacity);
            }
        });

        makeScreen.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                makeScreen.opacityProperty().setValue(deffaultOpacity);
            }
        });


        logoImage.layoutXProperty().setValue(rightBorderSize);
        logoImage.layoutYProperty().setValue(rightBorderSize);

        copyright.layoutXProperty().setValue(1100);
        copyright.layoutYProperty().setValue(rightBorderSize);

        toolbar.layoutXProperty().setValue(100);
        toolbar.layoutYProperty().setValue(140);

        openFileButton.layoutXProperty().setValue(rightBorderSize);
        openFileButton.layoutYProperty().setValue(skipSize += skipConst);
        openFileButton.opacityProperty().setValue(deffaultOpacity);


        saveFile.layoutXProperty().setValue(rightBorderSize);
        saveFile.layoutYProperty().setValue(skipSize += skipConst);
        saveFile.opacityProperty().setValue(deffaultOpacity);

        makeScreen.layoutXProperty().setValue(rightBorderSize);
        makeScreen.layoutYProperty().setValue(skipSize += skipConst);
        makeScreen.opacityProperty().setValue(deffaultOpacity);

        randomTest.layoutXProperty().setValue(rightBorderSize);
        randomTest.layoutYProperty().setValue(skipSize += skipConst);
        randomTest.opacityProperty().setValue(deffaultOpacity);

        /**
        dotsQuantity.layoutXProperty().setValue(200);
        dotsQuantity.layoutYProperty().setValue(180);
        dotsQuantity.setMinWidth(40);
        dotsQuantity.setMaxWidth(40);
        dotsQuantity.getStyleClass().add("textfield");
    */
        clearButton.layoutXProperty().setValue(rightBorderSize);
        clearButton.layoutYProperty().setValue(skipSize += skipConst);
        clearButton.opacityProperty().setValue(deffaultOpacity);

        makeConvexHullButton.layoutXProperty().setValue(rightBorderSize);
        makeConvexHullButton.layoutYProperty().setValue(skipSize += skipConst);
        makeConvexHullButton.opacityProperty().setValue(deffaultOpacity);

        makeTriangulation.layoutXProperty().setValue(rightBorderSize);
        makeTriangulation.layoutYProperty().setValue(skipSize += skipConst);
        makeTriangulation.opacityProperty().setValue(deffaultOpacity);

        p.getChildren().addAll(openFileButton, clearButton, logoImage, makeConvexHullButton, makeTriangulation, randomTest, copyright, toolbar, makeScreen, saveFile);
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


    static public void generateTestFile(String filename, Vertex[] array) throws IOException {

        String dir = System.getProperty("user.dir");
        String localname = dir +  "\\" + filename + ".txt";
        File out = new File(localname);
        FileWriter wrt = new FileWriter(out);

        wrt.write(array.length + " \n");
        for (int i = 0; i < array.length; ++i){
            wrt.write(Double.toString(array[i].getX()) + " ");
            wrt.write(Double.toString(array[i].getY())+ " ");
        }
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


    static public Vertex[] randomTest(int size){
        Random a = new Random();
        Random c = new Random();
        Vertex array[] = new Vertex[size];
        for (int i = 0; i < size; ++i){
            array[i] = new Vertex(a.nextDouble()  * 1000, c.nextDouble() * 1000);
        }
        return array;
    }


    public static void main(String[] args){
        launch(args);
    }


}
