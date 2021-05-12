package Application.DAO;


import Application.Model.Product;
import Application.connection.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

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

            String query = "Select P.product_id, P.product_name, P.product_description, PS.product_quantity, PS.product_price, P.product_date , PS.product_status  From Product_Table P  Inner Join Product_Stock PS  ON P.product_id = PS.product_id ";

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
