package Application.DAO;


import Application.Model.Product;
import Application.Utils.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class StockDAO {
    private Connection connection;
    private ResultSet rs;
    private Statement stmt;
    private PreparedStatement pst;

    ObservableList<Product> productStockList;


    public ObservableList<Product> getProductStockList(){

        productStockList = FXCollections.observableArrayList();

        try {

            connection= ConnectionFactory.getConnection();

            String query = "Select product_id, product_name, product_description, product_quantity, product_price, product_date , product_status  From Product_Table  ";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);


            while (rs.next()){
                productStockList.add(new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6).toLocalDate(),
                        getStatus(rs.getInt(7))
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
           closeConnection();
        }

        return productStockList;

    }

    public void updateStock(String[] arr){
        try{
            connection = ConnectionFactory.getConnection();

            String query = "Insert into StockUpdate_Table(product_id,manufacturer_name,stockUpdate_quantity) " +
                    " Values((Select product_id From product_table where product_name = ?),?,?)";

            String queryWithDate = "Insert into StockUpdate_Table(product_id,manufacturer_name,stockUpdate_quantity,stockUpdate_date) " +
                    " Values((Select product_id From product_table where product_name = ?),?,?,TO_Date(?,'mm,dd,yyyy')) ";

            if(arr[1] == null)
                arr[1] = "  ";

            if(arr[3] == null){
                System.out.println("no Date Method Worked");
                pst = connection.prepareStatement(query);
            }
            else {
                pst = connection.prepareStatement(queryWithDate);
                pst.setString(4, arr[3]);
            }

            pst.setString(1, arr[0]);
            pst.setString(2, arr[1]);
            pst.setInt(3, Integer.parseInt(arr[2]));
            pst.executeQuery();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            closeConnection();
        }
    }

    public Boolean checkStock(String productName,int quantity){
            int stockQuantity = 0;
        try{
            connection = ConnectionFactory.getConnection();

            String query = "Select product_quantity From Product_Table where Product_Name = ?";

            pst = connection.prepareStatement(query);
            pst.setString(1,productName);
            rs = pst.executeQuery();

            while(rs.next()){
               stockQuantity = rs.getInt(1);
            }
        }catch(SQLException throwables){ }finally {
            closeConnection();
        }

        return stockQuantity >= quantity ;
    }



    //////

    public ArrayList<String> getProductName(){
        ArrayList<String> productName = new ArrayList<>();
        try{
            connection = ConnectionFactory.getConnection();

            String query = "Select product_name From Product_Table ";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                productName.add(rs.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            closeConnection();
        }
        return productName;
    }





    public ArrayList<String> getManufacturerName(){
        ArrayList<String> manufacturerName = new ArrayList<>();
        try{
            connection = ConnectionFactory.getConnection();

            String query = "Select manufacturer_name From Manufacturer_Table ";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                manufacturerName.add(rs.getString(1));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            closeConnection();
        }
        return manufacturerName;
    }



    ////////
    private String getStatus(int status){
        if(status == 0 ) {
            return "UnAvailable";
        }
        else
            return "Available";
    }

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
