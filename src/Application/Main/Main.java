package Application.Main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {

    double xOffset;
    double yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception{

       Parent root = FXMLLoader.load(getClass().getResource("/Application/View/mainMenu.fxml"));

       // Font.loadFont(getClass().getResourceAsStream("/Application/Icons/BalooBhaina2-Medium.ttf"),12);
       //primaryStage.initStyle(StageStyle.UNDECORATED);
       primaryStage.setTitle("Bill Soft");
       Scene scene = new Scene(root);
       /*/*/////////////////
         primaryStage.initStyle(StageStyle.TRANSPARENT);
      scene.setFill(Color.TRANSPARENT);

        /////////////////////////*/



        //scene.getStylesheets().add("/Application/CSS/MistSilver.css");
        primaryStage.getIcons().add(new Image("/Application/Icons/logo.png"));
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
