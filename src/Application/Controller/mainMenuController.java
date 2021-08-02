package Application.Controller;

import Application.Utils.MyFXMLLoader;
import Application.Utils.ReportFactory;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class mainMenuController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private Parent newInvoicePnl;
    private Parent customerPnl;
    private Parent productPnl;
    private Parent dashboardPnl;
    private Parent transactionViewPnl;
    private Parent transactionHistoryPnl;
    private Parent invoiceViewPnl;
    private Parent stockViewPnl;
    private Parent stockHistoryPnl;

@FXML
        private Button closeBtn;

    /////------- Declaring and Initializing Variables
    Stage stage = null;
    Stage loginStage;


    @Override
    public void initialize(URL location, ResourceBundle resources)  {


        displayDashboard();
        loadFxmlViews();

        new Thread(()->{
            //Later on the Set from the database will compile the.
        ReportFactory.setSingleInvoice("F:\\Invoice_Table_Based.jrxml");
        }).start();
    }


    public void loadFxmlViews(){
        ExecutorService exec = Executors.newSingleThreadExecutor();

        Task dTask = new Task<Void>() {
            @Override
            protected Void call(){
                dashboardPnl = MyFXMLLoader.load("/Application/View/dashboard.fxml");
                return null;
            }
        };
        exec.submit(dTask);

        Task cVTask = new Task<Void>() {
            @Override
            protected Void call(){
                customerPnl = MyFXMLLoader.load("/Application/View/viewCustomers.fxml");
              return null;
            }
        };
        exec.submit(cVTask);

        Task pVTask = new Task<Void>() {
            @Override
            protected Void call(){
                productPnl = MyFXMLLoader.load("/Application/View/productView.fxml");
                return null;
            }
        };
        exec.submit(pVTask);

        Task tVTask = new Task<Void>() {
            @Override
            protected Void call(){
                transactionViewPnl = MyFXMLLoader.load("/Application/View/transactionView.fxml");
                return null;
            }
        };
        exec.submit(tVTask);

        Task tHTask = new Task<Void>() {
            @Override
            protected Void call(){
                transactionHistoryPnl = MyFXMLLoader.load("/Application/View/transactionHistory.fxml");
                return null;
            }
        };
        exec.submit(tHTask);

        Task iVTask = new Task<Void>() {
            @Override
            protected Void call(){
                invoiceViewPnl = MyFXMLLoader.load("/Application/View/invoiceView.fxml");
                return null;
            }
        };
        exec.submit(iVTask);

        Task nITask = new Task<Void>() {
            @Override
            protected Void call(){
                newInvoicePnl = MyFXMLLoader.load("/Application/View/newInvoice.fxml");
                return null;
            }
        };
        exec.submit(nITask);

        Task sVTask = new Task<Void>() {
            @Override
            protected Void call(){
                stockViewPnl = MyFXMLLoader.load("/Application/View/stockView.fxml");
                return null;
            }
        };
        exec.submit(sVTask);

        Task sHTask = new Task<Void>() {
            @Override
            protected Void call(){
                stockHistoryPnl = MyFXMLLoader.load("/Application/View/stockHistory.fxml");
                return null;
            }
        };
        exec.submit(sHTask);

    }


    public void displayDashboard(){
       if(dashboardPnl == null)
           dashboardPnl = MyFXMLLoader.load("/Application/View/dashboard.fxml");

       borderPane.setCenter(dashboardPnl);
    }

    public void displayCustomer(){
      if(customerPnl == null)
          customerPnl = MyFXMLLoader.load("/Application/View/viewCustomers.fxml");

      borderPane.setCenter(customerPnl);
    }

    public void displayProducts(){
       if(productPnl == null)
           productPnl = MyFXMLLoader.load("/Application/View/productView.fxml");

       borderPane.setCenter(productPnl);
    }

    public void displayTransactionView(){
        if(transactionViewPnl == null)
            transactionViewPnl = MyFXMLLoader.load("/Application/View/transactionView.fxml");

        borderPane.setCenter(transactionViewPnl);
    }

    public void displayTransactionHistory(){
        if(transactionHistoryPnl == null)
            transactionHistoryPnl = MyFXMLLoader.load("/Application/View/transactionHistory.fxml");

        borderPane.setCenter(transactionHistoryPnl);
    }

    public void displayInvoice(){
        if(invoiceViewPnl == null)
            invoiceViewPnl = MyFXMLLoader.load("/Application/View/invoiceView.fxml");

        borderPane.setCenter(invoiceViewPnl);
    }

    public void displayNewInvoice(){
        if(newInvoicePnl == null)
            newInvoicePnl = MyFXMLLoader.load("/Application/View/newInvoice.fxml");

        borderPane.setCenter(newInvoicePnl);
    }

    public void displayStock(){
        if(stockViewPnl == null)
            stockViewPnl = MyFXMLLoader.load("/Application/View/stockView.fxml");

        borderPane.setCenter(stockViewPnl);
    }

    public void displayStockHistory(){
        if(stockHistoryPnl == null)
            stockHistoryPnl = MyFXMLLoader.load("/Application/View/stockHistory.fxml");

        borderPane.setCenter(stockHistoryPnl);
    }

    public void displayReports(){
        System.out.println("Report not Build Yet!");
    }



    public void maximizeClicked(){
        if(stage == null)
            stage = (Stage) borderPane.getScene().getWindow();

        if(stage.isMaximized())
            stage.setMaximized(false);
        else
            stage.setMaximized(true);
      /*  Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        stage.setX(bounds.getMinX());
        stage.setY(bounds.getMinY());
        stage.setWidth(bounds.getWidth());
        stage.setHeight(bounds.getHeight());*/
    }


    public void closeClicked(){
        if(stage == null)
            stage = (Stage) borderPane.getScene().getWindow();

        stage.close();
    }

    public void minimizeClicked(){
        if(stage == null)
            stage = (Stage) borderPane.getScene().getWindow();

        stage.setIconified(true);
    }


    double xOffset;
    double yOffset;

    public void logoutClicked() throws IOException {
        if(stage == null)
            stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/Application/View/login2.fxml"));


        //primaryStage.initStyle(StageStyle.UNDECORATED);
        Stage primaryStage = new Stage();
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





}