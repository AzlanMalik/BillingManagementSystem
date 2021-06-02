package Application.Controller;


import Application.DAO.StockDAO;
import Application.Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class stockUpdateController implements Initializable {

    @FXML
    private ComboBox<String> productNameCombo;

    @FXML
    private TextField quatityTxt;

    @FXML
    private ComboBox<String> manufacturerNameCombo;

    @FXML
    private TableView<Product> stockTable;

    @FXML
    private TableColumn<Product, Integer> idCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, String> descriptionCol;

    @FXML
    private TableColumn<Product, Integer> stockCol;

    @FXML
    private TableColumn<Product, Integer> priceCol;

    @FXML
    private TableColumn<Product, LocalDate> lastUpdateCol;

    @FXML
    private TableColumn<Product, String> statusCol;


    ///
    ObservableList<Product> list = FXCollections.observableArrayList();
    StockDAO dao = new StockDAO();



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        lastUpdateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        load();

    }


    public void load(){

        new Thread(() -> {
            //CONNECT TO WEB AND LOG IN (ON OUT OF FX THREAD)


            list = dao.getProductStockList();

            ArrayList<String> productNamesList = dao.getProductName();
            ArrayList<String> manufacturerName = dao.getManufacturerName();


            //DO SOMETHING WITH CONTROLLS ON FX THREAD ACCORDING RESULT OF OVER
            Platform.runLater(() -> {
                stockTable.getItems().addAll(list);
                productNameCombo.getItems().addAll(productNamesList);
                manufacturerNameCombo.getItems().addAll(manufacturerName);
            });
        }).start();

    }


    public void addStockClicked(ActionEvent event) {

        dao.updateStock(productNameCombo.getValue(),manufacturerNameCombo.getValue(), Integer.parseInt(quatityTxt.getText()));
    }

    public void removeStockClicked(ActionEvent event){
        if(true){
            //type the condition to check if stock is available
        }
        dao.updateStock(productNameCombo.getValue(),manufacturerNameCombo.getValue(), - Integer.parseInt(quatityTxt.getText()));
    }

}
