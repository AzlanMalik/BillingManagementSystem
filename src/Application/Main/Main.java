package Application.Main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;
import javafx.stage.StageStyle;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;


public class Main extends Application {

    double xOffset;
    double yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception{

       Parent root = FXMLLoader.load(getClass().getResource("/Application/FXML/mainMenu.fxml"));
      // primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root);



        scene.getStylesheets().add("/Application/CSS/MistSilver.css");

        primaryStage.setScene(scene);
        primaryStage.show();


        //Dragable Stage Code
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
            }
        });

        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }
}
