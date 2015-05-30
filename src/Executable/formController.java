package Executable;

/**
 * Created by emxot_000 on 12.05.2015.
 */

import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Sphere;
import java.net.URL;
import java.util.ResourceBundle;

public class formController implements Initializable{
    @FXML ImageView applicationLogo;
    @FXML Button openFileButton;
    @FXML AnchorPane mainPane;
    //@FXML AnchorPane geometryView;
    final Xform world = new Xform();
    final Xform axisGroup = new Xform();
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();
    final Xform moleculeGroup = new Xform();
    final double cameraDistance = 350;
    final PerspectiveCamera camera = new PerspectiveCamera(true);


    private Timeline timeline;
    boolean timelinePlaying = false;
    double ONE_FRAME = 1.0 / 24.0;
    double DELTA_MULTIPLIER = 200.0;
    double CONTROL_MULTIPLIER = 0.1;
    double SHIFT_MULTIPLIER = 0.1;
    double ALT_MULTIPLIER = 0.5;
    double mousePosX;
    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;
    int value = 90;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buildScene();
        buildCamera();
        buildModels(90);
        buildAxes();


    }


    private void buildScene() {
        //geometryView.getChildren().add(world);
    }

    private void buildCamera() {

        //geometryView.getChildren().add(cameraXform);

        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        camera.setTranslateZ(-cameraDistance);
        cameraXform.ry.setAngle(320.0);
        cameraXform.rx.setAngle(40);
    }



    private void buildAxes(){
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(240.0, 1, 1);
        final Box yAxis = new Box(1, 240.0, 1);
        final Box zAxis = new Box(1, 1, 240.0);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        world.getChildren().addAll(axisGroup);
    }


    private void buildModels(int value){
        Xform moleculeXform = new Xform();
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        moleculeGroup.getChildren().add(moleculeXform);
        Sphere oxygenSphere = new Sphere(value);
        oxygenSphere.setMaterial(redMaterial);
        moleculeXform.getChildren().addAll(oxygenSphere);
        world.getChildren().addAll(moleculeGroup);
    }

    private void buildM(int value){
        Xform moleculeXform = new Xform();
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        moleculeGroup.getChildren().add(moleculeXform);
        Sphere oxygenSphere = new Sphere(value);
        oxygenSphere.setMaterial(redMaterial);
        moleculeXform.getChildren().addAll(oxygenSphere);
        //world.getChildren().addAll(moleculeGroup);
    }


    public void keyboardTest(KeyEvent e){
        if (e.isControlDown()){
            value += 10;
            buildM(value);
        }
    }

}
