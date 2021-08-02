package Application.Controller;


import Application.Controller.PopUp.newStockController;
import Application.DAO.StockDAO;
import Application.Model.Product;
import Application.Model.TransactionHistory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class StockUpdateController implements Initializable {



    @FXML
    private TextField filterField;

    @FXML
    private TableView<Product> stockTable;

    @FXML
    private TableColumn<Product, Integer> idCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Integer> stockCol;

    @FXML
    private TableColumn<Product, Integer> priceCol;

    @FXML
    private TableColumn<Product, LocalDate> lastUpdateCol;

    @FXML
    private TableColumn<Product, String> statusCol;

    @FXML
    private TableColumn<Product, String> selectionCol;

    @FXML
    private Pagination pagination;

    @FXML
    private VBox vBox;
    ///
    ObservableList<Product> masterData = FXCollections.observableArrayList();
    StockDAO dao = new StockDAO();
    Stage stage = null;
    newStockController nStockController  = null;

    public int ROWS_PER_PAGE = 9;
    private FilteredList<Product> filteredData;

    double xOffset;
    double yOffset;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.prefWidthProperty().bind(stockTable.widthProperty().multiply(.11));
        productNameCol.prefWidthProperty().bind(stockTable.widthProperty().multiply(.3));
        stockCol.prefWidthProperty().bind(stockTable.widthProperty().multiply(.12));
        priceCol.prefWidthProperty().bind(stockTable.widthProperty().multiply(.12));
        lastUpdateCol.prefWidthProperty().bind(stockTable.widthProperty().multiply(.15));
        statusCol.prefWidthProperty().bind(stockTable.widthProperty().multiply(.15));
        selectionCol.prefWidthProperty().bind(stockTable.widthProperty().multiply(.05));


        masterData = dao.getProductStockList();
        setTableFilter();

        vBox.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) {
                // not showing...
            } else {
                // showing ...
                refreshView();
            }
        });
    }

    public void refreshView(){
        getTableData();
        pagination.setCurrentPageIndex(0);
    }

    public void getTableData(){
        new Thread(() -> {
            masterData.clear();
            masterData.addAll(dao.getProductStockList());

            Platform.runLater(() -> {
                filterField.clear();
            });
        }).start();
    }

    public void loadUpdateStockView(){
        try {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Application/View/PopUp/newStock.fxml")));
            Parent root = loader.load();
            stage = new Stage();
            Scene scene = new Scene(root);

            ////////////////////////////
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);


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
            //////////////////////*/

            //scene.getStylesheets().add("/Application/CSS/MistSilver.css");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);

            ///// ----- passing the customer view controller to new Customer Controller
            nStockController = loader.getController();
            nStockController.setStockUpdateController(this);
            nStockController.setStage(stage);
            ///////____________
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateStockClicked(){
        if(nStockController == null)
            loadUpdateStockView();

        nStockController.clearFields();
        nStockController.getComboData();
        stage.show();
    }

    public void setTableFilter(){
        filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    person -> newValue == null || newValue.isEmpty() || person.getProductName().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getStatus().toLowerCase()
                            .contains(newValue.toLowerCase()) || String.valueOf(person.getId())
                            .contains(newValue));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        idCol.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        productNameCol.setCellValueFactory(data -> data.getValue().productNameProperty());
        stockCol.setCellValueFactory(data -> data.getValue().stockProperty().asObject());
        priceCol.setCellValueFactory(data -> data.getValue().priceProperty().asObject());
        lastUpdateCol.setCellValueFactory(data -> data.getValue().dateProperty());
        statusCol.setCellValueFactory(data -> data.getValue().statusProperty());
        selectionCol.setCellValueFactory(new PropertyValueFactory<>("selection"));

        int totalPage = (int) (Math.ceil(masterData.size() * 1.0 / ROWS_PER_PAGE));
        pagination.setPageCount(totalPage);
        pagination.setCurrentPageIndex(0);
        changeTableView(0, ROWS_PER_PAGE);
        pagination.currentPageIndexProperty().addListener(
                (observable, oldValue, newValue) -> changeTableView(newValue.intValue(), ROWS_PER_PAGE));

    }

    private void changeTableView(int index, int limit) {
        int fromIndex = index * limit;
        int toIndex = Math.min(fromIndex + limit, masterData.size());

        int minIndex = Math.min(toIndex, filteredData.size());
        SortedList<Product> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(stockTable.comparatorProperty());
        stockTable.setItems(sortedData);
    }

    public void printClicked(){
        ObservableList<Product> selectedList = getSelected();
        if(selectedList.isEmpty()){
            System.out.println("List is Empty so All Data in Table will be printed");
        }
        else{
            System.out.println("list have content so that data will be printed");
        }
    }

    public void deleteClicked(){
        ObservableList<Product> selectedList = getSelected();

    }

    public void dispatchClicked(){
        ObservableList<Product> selectedList = getSelected();

        for(Product p : selectedList){
            p.setSelection(false);
        }
        // Refreshing the checkbox graphics
        pagination.setCurrentPageIndex(pagination.getCurrentPageIndex());
    }

    public ObservableList<Product> getSelected(){
        ObservableList<Product> selectedList = FXCollections.observableArrayList();

        for(Product p : masterData){
            if(p.getSelection().isSelected())
                selectedList.add(p);
        }

        return  selectedList;
    }


}
