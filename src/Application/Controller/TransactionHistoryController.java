package Application.Controller;

import Application.DAO.TransactionHistoryDAO;
import Application.Model.TransactionHistory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class TransactionHistoryController implements Initializable {

    @FXML
    private TextField filterField;

    @FXML
    private TableView<TransactionHistory> transactionHistoryTable;

    @FXML
    private TableColumn<TransactionHistory, Integer> invoiceIdCol;

    @FXML
    private TableColumn<TransactionHistory, String> selectionCol;

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

    @FXML
    private VBox vBox;

    @FXML
    private Pagination pagination;

    /////------ Declaring and Initializing Variables
    private ObservableList<TransactionHistory> masterData = FXCollections.observableArrayList();
    TransactionHistoryDAO dao = new TransactionHistoryDAO();

    public int ROWS_PER_PAGE = 9;
    private FilteredList<TransactionHistory> filteredData;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        invoiceIdCol.prefWidthProperty().bind(transactionHistoryTable.widthProperty().multiply(.08));
        companyNameCol.prefWidthProperty().bind(transactionHistoryTable.widthProperty().multiply(.3));
        cityCol.prefWidthProperty().bind(transactionHistoryTable.widthProperty().multiply(.15));
        remainingAmountCol.prefWidthProperty().bind(transactionHistoryTable.widthProperty().multiply(.10));
        paidAmountCol.prefWidthProperty().bind(transactionHistoryTable.widthProperty().multiply(.10));
        dateCol.prefWidthProperty().bind(transactionHistoryTable.widthProperty().multiply(.10));
        salesmanCol.prefWidthProperty().bind(transactionHistoryTable.widthProperty().multiply(.13));
        selectionCol.prefWidthProperty().bind(transactionHistoryTable.widthProperty().multiply(.04));


        masterData = dao.getTransactionHistoryList();
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
            masterData.addAll( dao.getTransactionHistoryList());

            Platform.runLater(() -> {
                filterField.clear();
                transactionHistoryTable.refresh();
            });
        }).start();

    }

    public void setTableFilter(){
        filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    person -> newValue == null || newValue.isEmpty() || person.getCity().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getCompanyName().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getSalesmanName().toLowerCase()
                            .contains(newValue.toLowerCase()) || String.valueOf(person.getInvoiceId())
                            .contains(newValue));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        invoiceIdCol.setCellValueFactory(data -> data.getValue().invoiceIdProperty().asObject());
        companyNameCol.setCellValueFactory(data -> data.getValue().companyNameProperty());
        cityCol.setCellValueFactory(data -> data.getValue().cityProperty());
        remainingAmountCol.setCellValueFactory(data -> data.getValue().remainingAmountProperty().asObject());
        paidAmountCol.setCellValueFactory(data -> data.getValue().paidAmountProperty().asObject());
        dateCol.setCellValueFactory(data -> data.getValue().dateProperty());
        salesmanCol.setCellValueFactory(data -> data.getValue().salesmanNameProperty());
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
        SortedList<TransactionHistory> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(transactionHistoryTable.comparatorProperty());
        transactionHistoryTable.setItems(sortedData);
    }

    public void printClicked(){
        ObservableList<TransactionHistory> selectedList = getSelected();
        if(selectedList.isEmpty()){
            System.out.println("List is Empty so All Data in Table will be printed");
        }
        else{
            System.out.println("list have content so that data will be printed");
        }
    }

    public void deleteClicked(){
        ObservableList<TransactionHistory> selectedList = getSelected();

    }

    public void dispatchClicked(){
        ObservableList<TransactionHistory> selectedList = getSelected();

        for(TransactionHistory th : selectedList){
            th.setSelection(false);
        }
        // Refreshing the checkbox graphics
        pagination.setCurrentPageIndex(pagination.getCurrentPageIndex());
    }

    public ObservableList<TransactionHistory> getSelected(){
        ObservableList<TransactionHistory> selectedList = FXCollections.observableArrayList();

        for(TransactionHistory th : masterData){
            if(th.getSelection().isSelected())
                selectedList.add(th);
        }

        return  selectedList;
    }


}
