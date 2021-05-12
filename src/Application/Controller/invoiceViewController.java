package Application.Controller;

import Application.DAO.InvoiceViewDAO;
import Application.Model.Invoice;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class invoiceViewController implements Initializable {


    @FXML
    private TableView<Invoice> invoiceTable;

    @FXML
    private TableColumn<Invoice, Integer> idCol;

    @FXML
    private TableColumn<Invoice, String> customerNameCol;

    @FXML
    private TableColumn<Invoice, String> companyNameCol;

    @FXML
    private TableColumn<Invoice, LocalDate> dateCol;

    @FXML
    private TableColumn<Invoice, String> salesmanNameCol;

    @FXML
    private TableColumn<Invoice, Integer>totalAmountCol;

    @FXML
    private TableColumn<Invoice, Integer> paidAmountCol;

    @FXML
    private TableColumn<Invoice, String> statusCol;

    ///////
    ObservableList<Invoice> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        totalAmountCol.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        paidAmountCol.setCellValueFactory(new PropertyValueFactory<>("paidAmount"));
        salesmanNameCol.setCellValueFactory(new PropertyValueFactory<>("salesmanName"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));


        load();
    }

    public void load(){

        new Thread(() -> {
            //CONNECT TO WEB AND LOG IN (ON OUT OF FX THREAD)

            InvoiceViewDAO dao = new InvoiceViewDAO();
            list = dao.getInvoiceList();


            //DO SOMETHING WITH CONTROLLS ON FX THREAD ACCORDING RESULT OF OVER
            Platform.runLater(() -> {
                invoiceTable.getItems().addAll(list);
            });
        }).start();

    }
}
