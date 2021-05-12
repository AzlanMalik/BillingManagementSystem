package Application.DAO;

import Application.Model.Customer;
import Application.connection.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class CustomerViewDAO {

    ObservableList<Customer> customerList = FXCollections.observableArrayList();

    private Connection connection;
    private ResultSet rs;
    private Statement stmt;
    private PreparedStatement pst;


    public ObservableList<Customer> getCustomerList(){

        try {

            connection = ConnectionFactory.getConnection();

            String query = "Select customer_id,customer_name,customer_companyName,customer_city,customer_previousBalance , customer_phoneNo1 from Customer_Table";


            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){

                customerList.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getLong(6)
                ));
            }
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeConnection();
        }

        return customerList;
    }


    ////// Methods
    public void closeConnection(){
        try {
            if(rs!=null) rs.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        try {
            if(stmt!=null) stmt.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        try {
            if(pst!=null) pst.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
        try {
            if(connection!=null) connection.close();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

}
