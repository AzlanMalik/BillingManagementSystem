package Application.Controller;

import Application.Controller.PopUp.newTransactionController;
import Application.DAO.TransactionDAO;
import Application.Model.Transaction;
import Application.Model.TransactionHistory;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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


public class TransactionViewController implements Initializable {

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Transaction> transactionTable;

    @FXML
    private TableColumn<Transaction, Integer> invoiceIdCol;


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
    private TableColumn<Transaction, String> salesmanCol;

    @FXML
    private TableColumn<Transaction, String> selectionCol;

    @FXML
    private VBox vBox;

    @FXML
    private Pagination pagination;

    ///////----- Declaring and Initializing Variables
    ObservableList<Transaction> masterData = FXCollections.observableArrayList();
    TransactionDAO dao = new TransactionDAO();

    public int ROWS_PER_PAGE = 9;
    private FilteredList<Transaction> filteredData;

    Stage stage = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        invoiceIdCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(.08));
        companyNameCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(.3));
        cityCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(.15));
        amountCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(.10));
        paidCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(.10));
        dueDataCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(.10));
        salesmanCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(.13));
        selectionCol.prefWidthProperty().bind(transactionTable.widthProperty().multiply(.04));


        masterData = dao.getTransactionList();
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

    public void newDepositClicked(ActionEvent event){
        if(stage == null)
            loadNewDeposit();

        stage.show();
    }

    public void refreshView(){
        getTableData();
        pagination.setCurrentPageIndex(0);
    }

    public void getTableData(){
        new Thread(() -> {
            masterData.clear();
            masterData.addAll(dao.getTransactionList());

            Platform.runLater(() -> {
                filterField.clear();
            });
        }).start();
    }

    double xOffset;/// dragable variable
    double yOffset;// dragable variable

    public void loadNewDeposit(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Application/View/PopUp/newTransaction.fxml"));
            Parent root = loader.load();
            stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add("/Application/CSS/NewCss.css");
            stage.setScene(scene);
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
            stage.initModality(Modality.APPLICATION_MODAL);

            newTransactionController controller = loader.getController();
            controller.setEventHandler();
            controller.setTransactionController(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTableFilter(){
        filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    person -> newValue == null || newValue.isEmpty() || person.getCity().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getCompanyName().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getSalesman().toLowerCase()
                            .contains(newValue.toLowerCase()) || String.valueOf(person.getInvoiceId())
                            .contains(newValue));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        invoiceIdCol.setCellValueFactory(data -> data.getValue().invoiceIdProperty().asObject());
        companyNameCol.setCellValueFactory(data -> data.getValue().companyNameProperty());
        cityCol.setCellValueFactory(data -> data.getValue().cityProperty());
        amountCol.setCellValueFactory(data -> data.getValue().amountProperty().asObject());
        paidCol.setCellValueFactory(data -> data.getValue().paidProperty().asObject());
        dueDataCol.setCellValueFactory(data -> data.getValue().dueDateProperty());
        salesmanCol.setCellValueFactory(data -> data.getValue().salesmanProperty());
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
        SortedList<Transaction> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(transactionTable.comparatorProperty());
        transactionTable.setItems(sortedData);
    }

    public void printClicked(){
        ObservableList<Transaction> selectedList = getSelected();
        if(selectedList.isEmpty()){
            System.out.println("List is Empty so All Data in Table will be printed");
        }
        else{
            System.out.println("list have content so that data will be printed");
        }
    }

    public void deleteClicked(){
        ObservableList<Transaction> selectedList = getSelected();

    }

    public void dispatchClicked(){
        ObservableList<Transaction> selectedList = getSelected();

        for(Transaction t : selectedList){
            t.setSelection(false);
        }
        // Refreshing the checkbox graphics
        pagination.setCurrentPageIndex(pagination.getCurrentPageIndex());
    }

    public ObservableList<Transaction> getSelected(){
        ObservableList<Transaction> selectedList = FXCollections.observableArrayList();

        for(Transaction t : masterData){
            if(t.getSelection().isSelected())
                selectedList.add(t);
        }

        return  selectedList;
    }
}
