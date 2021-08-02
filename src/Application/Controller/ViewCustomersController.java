package Application.Controller;

import Application.Controller.PopUp.newCustomerController;
import Application.DAO.CustomerViewDAO;
import Application.Model.Customer;
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
import java.util.ResourceBundle;


public class ViewCustomersController implements Initializable {

    @FXML
    private TextField filterField;

    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer,Integer> idCol;

    @FXML
    private TableColumn<Customer, String> nameCol;

    @FXML
    private TableColumn<Customer, String> companyNameCol;

    @FXML
    private TableColumn<Customer, String> cityCol;

    @FXML
    private TableColumn<Customer, Integer> previousBalanceCol;

    @FXML
    private TableColumn<Customer, Long> phoneNoCol;

    @FXML
    private TableColumn<Customer, String> selectionCol;

    @FXML
    private Pagination pagination;


    @FXML
    private VBox vBox;


    //Add New Stage Declaration for Close MEthod
    ObservableList<Customer> masterData = FXCollections.observableArrayList();
    CustomerViewDAO dao = new CustomerViewDAO();

    public int ROWS_PER_PAGE = 9;
    private FilteredList<Customer> filteredData;

    Stage stage = null;
    newCustomerController newCustController = null;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.prefWidthProperty().bind(customerTable.widthProperty().multiply(.07));
        nameCol.prefWidthProperty().bind(customerTable.widthProperty().multiply(.16));
        companyNameCol.prefWidthProperty().bind(customerTable.widthProperty().multiply(.30));
        cityCol.prefWidthProperty().bind(customerTable.widthProperty().multiply(.13));
        phoneNoCol.prefWidthProperty().bind(customerTable.widthProperty().multiply(.19));
        selectionCol.prefWidthProperty().bind(customerTable.widthProperty().multiply(.04));
        previousBalanceCol.prefWidthProperty().bind(customerTable.widthProperty().multiply(.09));

        masterData = dao.getCustomerList();
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
        customerTable.getColumns().get(0).setVisible(false);
        customerTable.getColumns().get(0).setVisible(true);
    }

    public void getTableData(){
        new Thread(() -> {
            masterData.removeAll(masterData);
            FXCollections.copy(masterData, dao.getCustomerList());

            Platform.runLater(() -> {
                filterField.clear();
            });
        }).start();
    }

    public void addNewClicked(ActionEvent event){
        if(newCustController == null)
            loadNewCustView();

        newCustController.updateView(false);
        newCustController.clearStage();
        stage.show();
    }

    public void updateClicked(ActionEvent event){
        if(newCustController == null)
            loadNewCustView();

        Customer customer = customerTable.getSelectionModel().getSelectedItem();

        if(customer != null){
            newCustController.updateView(true);
            newCustController.loadData(customer.getId());
            stage.show();
        }
    }

    double xOffset;
    double yOffset;

    public void loadNewCustView() {

        try {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Application/View/PopUp/newCustomer.fxml")));
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
            newCustController = loader.getController();
            newCustController.setCustController(this);
            newCustController.setStage(stage);
            ///////____________
    } catch (IOException e) {
        e.printStackTrace();
    }

    }

    public void showMessage() {

     customerSavedLabel.setOpacity(100);

     new Thread(()-> {
         try {
             Thread.sleep(1000);
         } catch (InterruptedException e) {
             e.printStackTrace();
         }
         Platform.runLater(()->{
             customerSavedLabel.setOpacity(0);
         });
     }).start();
    }

    public void setTableFilter(){
        filteredData = new FilteredList<>(masterData, p -> true);
        filterField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(
                    person -> newValue == null || newValue.isEmpty() || person.getCity().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getCompanyName().toLowerCase()
                            .contains(newValue.toLowerCase()) || person.getName().toLowerCase()
                            .contains(newValue.toLowerCase()) || String.valueOf(person.getId())
                            .contains(newValue));
            changeTableView(pagination.getCurrentPageIndex(), ROWS_PER_PAGE);
        });

        idCol.setCellValueFactory(data -> data.getValue().idProperty().asObject());
        nameCol.setCellValueFactory(data -> data.getValue().nameProperty());
        companyNameCol.setCellValueFactory(data -> data.getValue().companyNameProperty());
        cityCol.setCellValueFactory(data -> data.getValue().cityProperty());
        previousBalanceCol.setCellValueFactory(data -> data.getValue().previousBalanceProperty().asObject());
        phoneNoCol.setCellValueFactory(data -> data.getValue().phoneNoProperty().asObject());
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
        SortedList<Customer> sortedData = new SortedList<>(
                FXCollections.observableArrayList(filteredData.subList(Math.min(fromIndex, minIndex), minIndex)));
        sortedData.comparatorProperty().bind(customerTable.comparatorProperty());
        customerTable.setItems(sortedData);
    }

    public void printClicked(){
        ObservableList<Customer> selectedList = getSelected();
        if(selectedList.isEmpty()){
            System.out.println("List is Empty so All Data in Table will be printed");
        }
        else{
            System.out.println("list have content so that data will be printed");
        }
    }

    public void deleteClicked(){
        ObservableList<Customer> selectedList = getSelected();

    }

    public void dispatchClicked(){
        ObservableList<Customer> selectedList = getSelected();

        for(Customer c : selectedList){
            c.setSelection(false);
        }
        // Refreshing the checkbox graphics
        pagination.setCurrentPageIndex(pagination.getCurrentPageIndex());
    }

    public ObservableList<Customer> getSelected(){
        ObservableList<Customer> selectedList = FXCollections.observableArrayList();

        for(Customer c : masterData){
            if(c.getSelection().isSelected())
                selectedList.add(c);
        }

        return  selectedList;
    }


}
