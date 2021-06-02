package Application.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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


    @Override
    public void initialize(URL location, ResourceBundle resources)  {

        }

        //MainMenu LeftPnl Actions

    public void displayDashboard() throws IOException{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/View/dashboard.fxml"));
        dashboardPnl = loader.load();
        borderPane.setCenter(dashboardPnl);
    }

    public void displayTransactionView() throws IOException{
        transactionViewPnl = FXMLLoader.load(getClass().getResource("/Application/View/transactionView.fxml"));
        borderPane.setCenter(transactionViewPnl);
    }

    public void displayTransactionHistory() throws IOException{
        transactionHistoryPnl = FXMLLoader.load(getClass().getResource("/Application/View/transactionHistory.fxml"));
        borderPane.setCenter(transactionHistoryPnl);
    }

    public void displayCustomer() throws IOException{
        customerPnl = FXMLLoader.load(getClass().getResource("/Application/View/viewCustomers.fxml"));
       // customerPnl.getStylesheets().add("/Application/CSS/bootStrap3.css");
        borderPane.setCenter(customerPnl);
    }

    public void displayInvoice() throws IOException{
        invoiceViewPnl = FXMLLoader.load(getClass().getResource("/Application/View/invoiceView.fxml"));
        borderPane.setCenter(invoiceViewPnl);
    }

    public void displayNewInvoice() throws IOException{
        newInvoicePnl =  FXMLLoader.load(getClass().getResource("/Application/View/newInvoice.fxml"));
        borderPane.setCenter(newInvoicePnl);
    }

    public void displayStock() throws IOException{
        stockViewPnl = FXMLLoader.load(getClass().getResource("/Application/View/stockView.fxml"));
        borderPane.setCenter(stockViewPnl);
    }

    public void displayStockHistory() throws IOException{
        stockHistoryPnl = FXMLLoader.load(getClass().getResource("/Application/View/stockHistory.fxml"));
        borderPane.setCenter(stockHistoryPnl);
    }

    public void displayProducts() throws IOException{
        productPnl = FXMLLoader.load(getClass().getResource("/Application/View/productView.fxml"));
        borderPane.setCenter(productPnl);
    }

    public void displayReports(){
        System.out.println("Report not Build Yet!");
    }






}