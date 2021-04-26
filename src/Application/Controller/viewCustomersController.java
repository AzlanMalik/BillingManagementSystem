package Application.Controller;

import Application.Service.loadCustomerService;
import Application.Table.Person;
import Application.Table.customer;
import javafx.beans.property.SimpleObjectProperty;
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
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class viewCustomersController implements Initializable {

    @FXML
    private TableView<customer> table;

    @FXML
    private TableColumn<customer,Integer> idCol;

    @FXML
    private TableColumn<customer, String> nameCol;

    @FXML
    private TableColumn<customer, String> companyNameCol;

    @FXML
    private TableColumn<customer, String> cityCol;

    @FXML
    private TableColumn<customer, Integer> previousBalanceCol;

    @FXML
    private TableColumn<customer, String> statusCol;

    ObservableList<customer> list = FXCollections.observableArrayList();

    //Add New Stage Declaration for Close MEthod
    Stage stage;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idCol.setCellValueFactory(new PropertyValueFactory<customer,Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<customer,String>("name"));
        companyNameCol.setCellValueFactory(new PropertyValueFactory<customer,String>("companyName"));
        cityCol.setCellValueFactory(new PropertyValueFactory<customer,String>("city"));
        previousBalanceCol.setCellValueFactory(new PropertyValueFactory<customer,Integer>("previousBalance"));
        statusCol.setCellValueFactory(new PropertyValueFactory<customer,String>("status"));

        /////////loadData();
        final loadCustomerService service = new loadCustomerService();
        table.itemsProperty().bind(service.valueProperty());
        service.start();


    }

    public void addNewClicked (ActionEvent event){

        try {
            Parent frame = FXMLLoader.load(getClass().getResource("/Application/FXML/newCustomer.fxml"));
            stage = new Stage();
            stage.setScene(new Scene(frame));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void updateClicked(ActionEvent event){
        if(!table.getSelectionModel().isCellSelectionEnabled()) {
            System.out.println("No Customer is Selected");
            return;
        }

        customer updateCustomer = table.getSelectionModel().getSelectedItem();
        try {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Application/FXML/newCustomer.fxml")));
            Parent frame = loader.load();
            stage = new Stage();
            stage.setScene(new Scene(frame));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
            newCustomerController controller = loader.getController();
            controller.loadData(updateCustomer.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*     using services now
    public void loadData(){

        try {
            list.clear();

            Class.forName("oracle.jdbc.driver.OracleDriver");


            Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","12345678900Ab");


            Statement stmt=con.createStatement();

            String sql = "Select customer_id,customer_name,customer_companyName,customer_city,customer_previousBalance from Customer_Table";
            ResultSet rs=stmt.executeQuery(sql);


            while (rs.next()){

                list.add(new customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        "T"     //rs.getString(6)
                ));

                table.setItems(list);

            }

            con.close();


        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(testController.class.getName()).log(Level.SEVERE, null, ex);
        }



    }*/


}
