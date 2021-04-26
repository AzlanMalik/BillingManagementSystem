package Application.Controller;

import Application.Table.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;


import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


public class testController implements Initializable
{



    @FXML
    private TableView<Person> table;

    @FXML
    private TableColumn<Person, Integer> idCol;

    @FXML
    private TableColumn<Person, String> nameCol;

    @FXML
    private TableColumn<Person, String> companyNameCol;

    @FXML
    private TableColumn<Person, String> addressCol;

    @FXML
    private TableColumn<Person, String> cityCol;

    @FXML
    private TableColumn<Person, String> stateCol;

    @FXML
    private TableColumn<Person, Integer> phoneNo1Col;

    @FXML
    private TableColumn<Person, Integer> phoneNo2Col;

    @FXML
    private TableColumn<Person, Integer> prevBalanceCol;

    @FXML
    private TableColumn<Person, ComboBox> buttonsCol;





    @Override
    public void initialize(URL location, ResourceBundle resources) {


        idCol.setCellValueFactory(new PropertyValueFactory<Person,Integer>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("name"));
        companyNameCol.setCellValueFactory(new PropertyValueFactory<Person,String>("companyName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<Person,String>("address"));
        cityCol.setCellValueFactory(new PropertyValueFactory<Person,String>("city"));
        stateCol.setCellValueFactory(new PropertyValueFactory<Person,String>("state"));
        phoneNo1Col.setCellValueFactory(new PropertyValueFactory<Person,Integer>("phoneNo1"));
        phoneNo2Col.setCellValueFactory(new PropertyValueFactory<Person,Integer>("phoneNo2"));
        prevBalanceCol.setCellValueFactory(new PropertyValueFactory<Person,Integer>("previousBalance"));
        buttonsCol.setCellValueFactory(new PropertyValueFactory<Person,ComboBox>("comboBox"));

        load();

    }


    public void load(){

        try {
            ObservableList<String> data = FXCollections.observableArrayList("Azlan","Malik","DON","Boss");
            ObservableList<Person> list = FXCollections.observableArrayList();
            list.clear();

            Class.forName("oracle.jdbc.driver.OracleDriver");


            Connection con= DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","12345678900Ab");


            Statement stmt=con.createStatement();

            String sql = "Select * from Customer_Table";
            ResultSet rs=stmt.executeQuery(sql);


            while (rs.next()){

                list.add(new  Person(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getLong(7),
                        rs.getLong(8),
                        rs.getInt(9),
                        data
                ));

                table.setItems(list);

            }

            con.close();


        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(testController.class.getName()).log(Level.SEVERE, null, ex);
        }



    }


}