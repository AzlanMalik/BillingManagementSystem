package Application.Controller;

import Application.Table.customer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class newCustomerController {


    @FXML
    private TextField nameTxt;

    @FXML
    private TextField addressTxt;

    @FXML
    private TextField stateTxt;

    @FXML
    private TextField previousBalanceTxt;

    @FXML
    private TextField companyNameTxt;

    @FXML
    private TextField cityTxt;

    @FXML
    private TextField phoneNo1Txt;

    @FXML
    private TextField phoneNo2Txt;

    @FXML
    private Button closeBtn;

    public void closeClicked(ActionEvent event){
        Stage stage = (Stage) closeBtn.getScene().getWindow();
        stage.close();
    }

    public void loadData(int id) {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

            Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "12345678900Ab");

            Statement stmt = con.createStatement();

            String sql = "Select * from Customer_Table where customer_id = "+ id;
            ResultSet rs = stmt.executeQuery(sql);


            while (rs.next()) {

               nameTxt.setText(rs.getString(2));
                companyNameTxt.setText(rs.getString(3));
                addressTxt.setText(rs.getString(4));
                cityTxt.setText(rs.getString(5));
                stateTxt.setText(rs.getString(6));
                phoneNo1Txt.setText(rs.getString(7));
                phoneNo2Txt.setText(rs.getString(8));
                previousBalanceTxt.setText(rs.getString(9));

            }

            con.close();


        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(testController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void addClicked(ActionEvent event){
        System.out.println("new Customer Added");
    }
}
