package Executable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.SubScene;


/**
 * Created by emxot_000 on 12.05.2015.
 */
public class Main extends Application {

    final Group root = new Group();
    final Xform axisGroup = new Xform();




    @Override public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("form.fxml"));
        primaryStage.setTitle("DELVIS");
        Circle test = new Circle(100);
        Group addition = new Group(test);
        Scene scene = new Scene(root, 1024, 768, true);
       // scene.setFill(Color.GREY);
        primaryStage.setScene(scene);
        //primaryStage.getScene().getStylesheets().add("futureCustomTheme.css");
        primaryStage.getIcons().add(new Image("icon.png"));
        primaryStage.setResizable(true);

        //  MyController controller = loader.getController();
        // controller.setStage(this.stage);
        primaryStage.show();


    }
    public static void main(String[] args){
        launch(args);
    }


}
