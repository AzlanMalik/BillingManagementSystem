package Application.Controller;

import Application.Controller.PopUp.newCustomerController;
import Application.DAO.CustomerViewDAO;
import Application.Model.Customer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class viewCustomersController implements Initializable {

    @FXML
    private TableView<Customer> table;

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

    ObservableList<Customer> list = FXCollections.observableArrayList();

    //Add New Stage Declaration for Close MEthod
    Stage stage;
    Parent root = null;
    FXMLLoader loader = null;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        cityCol.setCellValueFactory(new PropertyValueFactory<>("city"));
        previousBalanceCol.setCellValueFactory(new PropertyValueFactory<>("previousBalance"));
        phoneNoCol.setCellValueFactory(new PropertyValueFactory<>("phoneNo"));

        loadData();

    }

    public viewCustomersController() {

        new Thread(() -> {
            try {
                loader = new FXMLLoader((getClass().getResource("/Application/View/PopUp/newCustomer.fxml")));
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Platform.runLater(() -> {
                stage = new Stage();
                Scene scene = new Scene(root);
                scene.getStylesheets().add("/Application/CSS/MistSilver.css");
                stage.setScene(scene);
                stage.initModality(Modality.APPLICATION_MODAL);
            });
        }).start();

    }

    public void loadData(){
        new Thread(() -> {
            //CONNECT TO WEB AND LOG IN (ON OUT OF FX THREAD)

            CustomerViewDAO dao = new CustomerViewDAO();

            list = dao.getCustomerList();

            //DO SOMETHING WITH CONTROLLS ON FX THREAD ACCORDING RESULT OF OVER
            Platform.runLater(() -> {
                System.out.println("working");
                table.getItems().addAll(list);
            });
        }).start();

    }

    public void addNewClicked(ActionEvent event){
        newCustomerController controller = loader.getController();
        controller.updateView(false);
        controller.clearStage();
        stage.show();
    }

    public void updateClicked(ActionEvent event){
        Customer customer = table.getSelectionModel().getSelectedItem();

        if(customer != null){
            newCustomerController controller = loader.getController();
            controller.updateView(true);
            controller.loadData(customer.getId());
            stage.show();
        }
    }

}
