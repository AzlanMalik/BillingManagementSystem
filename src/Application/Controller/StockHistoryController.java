package Application.Controller;

import Application.DAO.StockHistoryDAO;
import Application.Model.StockHistory;
import Application.Model.TransactionHistory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

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

    @FXML
    private TableColumn<StockHistory, String> selectionCol;

    @FXML
    private TextField filterField;

    @FXML
    private Pagination pagination;

    @FXML
    private VBox vBox;
    ///////
    ObservableList<StockHistory> masterData = FXCollections.observableArrayList();
    StockHistoryDAO dao = new StockHistoryDAO();

    public int ROWS_PER_PAGE = 9;
    private FilteredList<StockHistory> filteredData;

    @Override
    public void initialize(URL location, ResourceBundle resources){

        updateIdCol.prefWidthProperty().bind(stockHistoryTable.widthProperty().multiply(.08));
        productNameCol.prefWidthProperty().bind(stockHistoryTable.widthProperty().multiply(.3));
        descriptionCol.prefWidthProperty().bind(stockHistoryTable.widthProperty().multiply(.25));
        quantityCol.prefWidthProperty().bind(stockHistoryTable.widthProperty().multiply(.10));
        updateDateCol.prefWidthProperty().bind(stockHistoryTable.widthProperty().multiply(.10));
        manufacturerCol.prefWidthProperty().bind(stockHistoryTable.widthProperty().multiply(.13));
        selectionCol.prefWidthProperty().bind(stockHistoryTable.widthProperty().multiply(.04));


        masterData = dao.getStockHistory();
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
            masterData.addAll(dao.getStockHistory());

            Platform.runLater(() -> {
                filterField.clear();
            });
        }).start();
    }

    public void setTableFilter(){
        filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    person -> newValue == null || newValue.isEmpty() || person.getProductName().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getDescription().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getManufacturerName().toLowerCase()
                            .contains(newValue.toLowerCase()) || String.valueOf(person.getUpdateId())
                            .contains(newValue));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });


        updateIdCol.setCellValueFactory(data -> data.getValue().updateIdProperty().asObject());
        productNameCol.setCellValueFactory(data -> data.getValue().productNameProperty());
        descriptionCol.setCellValueFactory(data -> data.getValue().descriptionProperty());
        quantityCol.setCellValueFactory(data -> data.getValue().quantityProperty().asObject());
        updateDateCol.setCellValueFactory(data -> data.getValue().updateDateProperty());
        manufacturerCol.setCellValueFactory(data -> data.getValue().manufacturerNameProperty());
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
        SortedList<StockHistory> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(stockHistoryTable.comparatorProperty());
        stockHistoryTable.setItems(sortedData);
    }

    public void printClicked(){
        ObservableList<StockHistory> selectedList = getSelected();
        if(selectedList.isEmpty()){
            System.out.println("List is Empty so All Data in Table will be printed");
        }
        else{
            System.out.println("list have content so that data will be printed");
        }
    }

    public void deleteClicked(){
        ObservableList<StockHistory> selectedList = getSelected();

    }

    public void dispatchClicked(){
        ObservableList<StockHistory> selectedList = getSelected();

        for(StockHistory sh : selectedList){
            sh.setSelection(false);
        }
        // Refreshing the checkbox graphics
        pagination.setCurrentPageIndex(pagination.getCurrentPageIndex());
    }

    public ObservableList<StockHistory> getSelected(){
        ObservableList<StockHistory> selectedList = FXCollections.observableArrayList();

        for(StockHistory sh : masterData){
            if(sh.getSelection().isSelected())
                selectedList.add(sh);
        }

        return  selectedList;
    }

}
