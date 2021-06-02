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


    public ObservableList<Customer> getCustomerList() {

        try {

            connection = ConnectionFactory.getConnection();

            String query = "Select customer_id,customer_name,customer_companyName,customer_city,customer_previousBalance , customer_phoneNo1 from Customer_Table";


            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {

                customerList.add(new Customer(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getLong(6)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }

        return customerList;
    }

    /////// ----PopUp Methods------


    public String[] getCustomerData(int custId) {

        String[] arr = new String[8];

        try {

            connection = ConnectionFactory.getConnection();

            String query = "Select customer_name,customer_companyName,customer_address,customer_city,customer_state,customer_phoneNo1, customer_PhoneNo2, customer_previousBalance from Customer_Table where customer_id = ?";


            pst = connection.prepareStatement(query);
            pst.setInt(1, custId);
            rs = pst.executeQuery();

            while (rs.next()) {
                arr[0] = rs.getString(1);
                arr[1] = rs.getString(2);
                arr[2] = rs.getString(3);
                arr[3] = rs.getString(4);
                arr[4] = rs.getString(5);
                arr[5] = rs.getString(6);
                arr[6] = rs.getString(7);
                arr[7] = rs.getString(8);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
        return arr;
    }

    public void updateCustomer(String[] customerData,int id){

        try {

        connection = ConnectionFactory.getConnection();

        String query = "Update Customer_Table " +
                " Set customer_name = ? ,customer_companyName = ?,customer_address = ? ,customer_city = ?, " +
                " customer_state = ?,customer_phoneNo1 = ?, customer_PhoneNo2 = ?, customer_previousBalance = ?  " +
                " where customer_id = ? ";


        pst = connection.prepareStatement(query);
        pst.setString(1, customerData[0]);
        pst.setString(2, customerData[1]);
        pst.setString(3, customerData[2]);
        pst.setString(4, customerData[3]);
        pst.setString(5, customerData[4]);
        pst.setString(6, customerData[5]);
        pst.setString(7, customerData[6]);
        pst.setString(8, customerData[7]);
        pst.setInt(9,id);

        rs = pst.executeQuery();


    } catch(SQLException e) {
            e.printStackTrace();
    }finally {
        closeConnection();
    }

}


    public void addNewCustomer (String[] customerData){

        try {

            connection = ConnectionFactory.getConnection();

            String query = "Insert Into Customer_Table(customer_name,customer_companyName,customer_address,customer_city,customer_state,customer_phoneNo1, customer_PhoneNo2, customer_previousBalance) " +
                    " Values(?,?,?,?,?,?,?,?)";

            pst = connection.prepareStatement(query);
            pst.setString(1,customerData[0]);
            pst.setString(2,customerData[1]);
            pst.setString(3,customerData[2]);
            pst.setString(4,customerData[3]);
            pst.setString(5,customerData[4]);
            pst.setString(6,customerData[5]);
            pst.setString(7,customerData[6]);
            pst.setString(8,customerData[7]);

            rs = pst.executeQuery();


        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeConnection();
        }

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
