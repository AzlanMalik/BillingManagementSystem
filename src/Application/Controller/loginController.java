package Application.Controller;

import Application.DAO.LoginDAO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;



public class loginController implements Initializable {
    @FXML
    private JFXTextField usernameTxt;

    @FXML
    private JFXButton loginBtn;

    @FXML
    private JFXPasswordField passwordTxt;



    Stage stage;
    Stage primaryStage;
    FXMLLoader loader = null;
    Parent root;
    double xOffset;
    double yOffset;


    String username;
    String password;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        usernameTxt.setOnAction(e -> passwordTxt.requestFocus());
        passwordTxt.setOnAction(e -> {
            try {
                login(e);
            } catch (InterruptedException | SQLException interruptedException) {
                interruptedException.printStackTrace();
            }
        });

        loadStage();

    }



    public void closeBtn () {
        Stage primaryStage = (Stage)  usernameTxt.getScene().getWindow();
        primaryStage.close();
       // ----Fading Code Experiment
     /*   FadeTransition fadeTransition = new FadeTransition(Duration.millis(400),loginPane);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.7);
        fadeTransition.play();
        fadeTransition.setOnFinished(event -> primaryStage.close() );
*/
    }

    public void loadStage(){
        new Thread(() -> {
            try {
                loader = new FXMLLoader(getClass().getResource("/Application/View/mainMenu.fxml"));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                stage = new Stage();
                stage.setMinWidth(1200);
                stage.setMinHeight(810);
                stage.getIcons().add(new Image("/Application/Icons/logo.png"));
                stage.setTitle("DCI");
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/Application/CSS/NewCss.css");
                /*/*/////////////////
                stage.initStyle(StageStyle.TRANSPARENT);
                scene.setFill(Color.TRANSPARENT);

                /////////////////////////*/

                //Dragable Stage Code
                root.setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = stage.getX() - event.getScreenX();
                        yOffset = stage.getY() - event.getScreenY();
                    }
                });

                root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        stage.setX(event.getScreenX() + xOffset);
                        stage.setY(event.getScreenY() + yOffset);
                    }
                });

                stage.setScene(scene);
            });
    }).start();
    }

    public void login(ActionEvent event) throws InterruptedException, SQLException {

        username = usernameTxt.getText();
        password = passwordTxt.getText();


        if (username != null && password != null) {

            LoginDAO verifyUser = new LoginDAO();
            if (verifyUser.login(username, password) == true) {
                ////// Currently the Fading event is not working for Successful login
                primaryStage = (Stage) usernameTxt.getScene().getWindow();
                primaryStage.close();
                stage.show();
            }
        }
    }



}
