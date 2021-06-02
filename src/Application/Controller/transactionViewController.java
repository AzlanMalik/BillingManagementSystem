package Application.Controller;

import Application.DAO.TransactionDAO;
import Application.Model.Transaction;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class transactionViewController implements Initializable {

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, Integer> invoiceIdCol;

    @FXML
    private TableColumn<Transaction, String> customerNameCol;

    @FXML
    private TableColumn<Transaction, String> companyNameCol;

    @FXML
    private TableColumn<Transaction, String> cityCol;

    @FXML
    private TableColumn<Transaction, Integer> amountCol;

    @FXML
    private TableColumn<Transaction, Integer> paidCol;

    @FXML
    private TableColumn<Transaction, LocalDate> dueDataCol;

    @FXML
    private TableColumn<Transaction, Integer> dueBalanceCol;

    @FXML
    private TableColumn<Transaction, String> salesmanCol;

    @FXML
    private TableColumn<Transaction, String> statusCol;


    ObservableList<Transaction> list;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        invoiceIdCol.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        amountCol.setCellValueFactory(new PropertyValueFactory<>("amount"));
        paidCol.setCellValueFactory(new PropertyValueFactory<>("paid"));
        dueDataCol.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        dueBalanceCol.setCellValueFactory(new PropertyValueFactory<>("DueBalance"));
        salesmanCol.setCellValueFactory(new PropertyValueFactory<>("salesman"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));





        loadData();
    }

    public void newDepositClicked(ActionEvent event){
        try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/View/PopUp/newTransaction.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/Application/CSS/MistSilver.css");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }

    public void loadData(){

        new Thread(() -> {
            //CONNECT TO WEB AND LOG IN (ON OUT OF FX THREAD)

            TransactionDAO dao = new TransactionDAO();
            list = dao.getTransactionList();



            //DO SOMETHING WITH CONTROLLS ON FX THREAD ACCORDING RESULT OF OVER
            Platform.runLater(() -> {
                System.out.println("working");
                transactionTable.getItems().addAll(list);
            });
        }).start();


    }
}
