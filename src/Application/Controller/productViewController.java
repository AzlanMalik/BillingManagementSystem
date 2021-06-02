package Application.Controller;

import Application.DAO.ProductViewDAO;
import Application.Model.Product;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


import javafx.event.ActionEvent;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class productViewController implements Initializable {

    @FXML
    private TextField productNameTxt;

    @FXML
    private TextField quantityTxt;

    @FXML
    private TextField priceTxt;

    @FXML
    private TextField descriptionTxt;

    @FXML
    private TableView<Product> productTable;

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
    private TableColumn<Product, LocalDate> creationDate;

    @FXML
    private TableColumn<Product, String> statusCol;


    ///
    ProductViewDAO dao = new ProductViewDAO();
    ObservableList<Product> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("productName"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        stockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        creationDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        load();

    }

    public void load(){

        new Thread(() -> {
            //CONNECT TO WEB AND LOG IN (ON OUT OF FX THREAD)


            list = dao.getProductList();


            //DO SOMETHING WITH CONTROLLS ON FX THREAD ACCORDING RESULT OF OVER
            Platform.runLater(() -> {
                productTable.getItems().addAll(list);
            });
        }).start();

    }

    public void addNewProductClicked(ActionEvent event){
        String[] productData = new String[4];

        productData[0]  = productNameTxt.getText();
        productData[1]  = descriptionTxt.getText();
        productData[2]  = quantityTxt.getText();
        productData[3]  = priceTxt.getText();

        dao.addNewProduct(productData);

    }

    }
