package Application.Controller;

import Application.DAO.InvoiceViewDAO;
import Application.DAO.TransactionHistoryDAO;
import Application.Model.Transaction;
import Application.Model.TransactionHistory;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class transactionHistoryController implements Initializable {

    @FXML
    private TableView<TransactionHistory> transactionHistoryTable;

    @FXML
    private TableColumn<TransactionHistory, Integer> invoiceIdCol;

    @FXML
    private TableColumn<TransactionHistory, String> customerNameCol;

    @FXML
    private TableColumn<TransactionHistory, String> companyNameCol;

    @FXML
    private TableColumn<TransactionHistory, String> cityCol;

    @FXML
    private TableColumn<TransactionHistory, Integer> remainingAmountCol;

    @FXML
    private TableColumn<TransactionHistory, Integer> paidAmountCol;

    @FXML
    private TableColumn<TransactionHistory, LocalDate> dateCol;

    @FXML
    private TableColumn<TransactionHistory, String> salesmanCol;

    ObservableList<TransactionHistory> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        invoiceIdCol.setCellValueFactory(new PropertyValueFactory<>("invoiceId"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        remainingAmountCol.setCellValueFactory(new PropertyValueFactory<>("remainingAmount"));
        paidAmountCol.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        salesmanCol.setCellValueFactory(new PropertyValueFactory<>("salesmanName"));




        load();
    }

    public void load(){

        new Thread(() -> {
            //CONNECT TO WEB AND LOG IN (ON OUT OF FX THREAD)

            TransactionHistoryDAO dao = new TransactionHistoryDAO();
            list = dao.getTransactionHistoryList();


            //DO SOMETHING WITH CONTROLLS ON FX THREAD ACCORDING RESULT OF OVER
            Platform.runLater(() -> {
                transactionHistoryTable.getItems().addAll(list);
            });
        }).start();

    }

}
