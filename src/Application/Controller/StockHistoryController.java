package Application.Controller;

import Application.DAO.StockHistoryDAO;
import Application.Model.StockHistory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StockHistoryController implements Initializable {

    @FXML
    private TableView<StockHistory> stockHistoryTable;

    @FXML
    private TableColumn<StockHistory, Integer> updateIdCol;

    @FXML
    private TableColumn<StockHistory, String> productNameCol;

    @FXML
    private TableColumn<StockHistory, String> descriptionCol;

    @FXML
    private TableColumn<StockHistory, Integer> quantityCol;

    @FXML
    private TableColumn<StockHistory, LocalDate> updateDateCol;

    @FXML
    private TableColumn<StockHistory, String> manufacturerCol;

    ///////
    ObservableList<StockHistory> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources){
        updateIdCol.setCellValueFactory(new PropertyValueFactory<>("updateId"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        updateDateCol.setCellValueFactory(new PropertyValueFactory<>("updateDate"));
        manufacturerCol.setCellValueFactory(new PropertyValueFactory<>("manufacturerName"));

        load();

    }

    public void load(){

        new Thread(() -> {
            //CONNECT TO WEB AND LOG IN (ON OUT OF FX THREAD)

            StockHistoryDAO dao = new StockHistoryDAO();
            list = dao.getStockHistory();


            //DO SOMETHING WITH CONTROLLS ON FX THREAD ACCORDING RESULT OF OVER
            Platform.runLater(() -> {
                stockHistoryTable.getItems().addAll(list);
            });
        }).start();

    }

}
