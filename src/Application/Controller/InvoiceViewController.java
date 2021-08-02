package Application.Controller;

import Application.DAO.InvoiceViewDAO;
import Application.Model.Invoice;
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

public class InvoiceViewController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Invoice> invoiceTable;

    @FXML
    private TableColumn<Invoice, Integer> idCol;

    @FXML
    private TableColumn<Invoice, String> selectionCol;

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

    @FXML
    private Pagination pagination;
    ///////---- Declaring and Initializing Variables
    ObservableList<Invoice> masterData = FXCollections.observableArrayList();
    InvoiceViewDAO dao = new InvoiceViewDAO();

    public int ROWS_PER_PAGE = 9;
    private FilteredList<Invoice> filteredData;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        selectionCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(.04));
        idCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(.10));
        companyNameCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(.3));
        dateCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(.14));
        totalAmountCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(.10));
        paidAmountCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(.10));
        salesmanNameCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(.15));
        statusCol.prefWidthProperty().bind(invoiceTable.widthProperty().multiply(.07));


        masterData = dao.getInvoiceList();
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
            masterData.addAll(dao.getInvoiceList());

            Platform.runLater(() -> {
                filterField.clear();
            });
        }).start();

    }

    public void setTableFilter(){
        filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    person -> newValue == null || newValue.isEmpty() || person.getSalesmanName().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getCompanyName().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getStatus().toLowerCase()
                    .contains(newValue.toLowerCase()) || String.valueOf(person.getId())
                            .contains(newValue));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });


        idCol.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        companyNameCol.setCellValueFactory(data -> data.getValue().companyNameProperty());
        totalAmountCol.setCellValueFactory(data -> data.getValue().totalAmountProperty().asObject());
        paidAmountCol.setCellValueFactory(data -> data.getValue().paidAmountProperty().asObject());
        dateCol.setCellValueFactory(data -> data.getValue().dateProperty());
        salesmanNameCol.setCellValueFactory(data -> data.getValue().salesmanNameProperty());
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
        SortedList<Invoice> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(invoiceTable.comparatorProperty());
        invoiceTable.setItems(sortedData);
    }

    public void printClicked(){
        ObservableList<Invoice> selectedList = getSelected();
        if(selectedList.isEmpty()){
            System.out.println("List is Empty so All Data in Table will be printed");
        }
        else{
            System.out.println("list have content so that data will be printed");
        }
    }

    public void deleteClicked(){
        ObservableList<Invoice> selectedList = getSelected();

    }

    public void dispatchClicked(){
        ObservableList<Invoice> selectedList = getSelected();

        for(Invoice th : selectedList){
            th.setSelection(false);
        }
        // Refreshing the checkbox graphics
        pagination.setCurrentPageIndex(pagination.getCurrentPageIndex());
    }

    public ObservableList<Invoice> getSelected(){
        ObservableList<Invoice> selectedList = FXCollections.observableArrayList();

        for(Invoice i : masterData){
            if(i.getSelection().isSelected())
                selectedList.add(i);
        }

        return  selectedList;
    }




}
