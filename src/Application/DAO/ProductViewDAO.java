package Application.DAO;

import Application.Model.Product;
import Application.connection.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;


public class ProductViewDAO {
    private Connection connection;
    private ResultSet rs;
    private Statement stmt;
    private PreparedStatement pst;


    ObservableList<Product> productList;


    public ObservableList<Product> getProductList(){

        productList = FXCollections.observableArrayList();

        try {

            connection = ConnectionFactory.getConnection();

            String query = "Select  product_id, product_name, product_description, product_quantity, product_price, product_date , product_status  From Product_Table ";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

                 while (rs.next()){
                      productList.add(new Product(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getInt(5),
                        rs.getDate(6).toLocalDate(),
                        getStatus(rs.getInt(7))
                 ));
                }

            }catch(SQLException e) {
            e.printStackTrace();
        } finally{
           closeConnection();
        }

        return productList;

    }


    ///////

    public void addNewProduct(String[] productData){
        try {

            connection = ConnectionFactory.getConnection();

            String query = "Insert Into Product_Table(product_name, product_description, product_quantity, product_price) " +
                                " Values(?,?,?,?) ";

            pst = connection.prepareStatement(query);
            pst.setString(1,productData[0]);
            pst.setString(2,productData[1]);
            pst.setString(3,productData[2]);
            pst.setString(4,productData[3]);

            rs = pst.executeQuery();

        }catch(SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
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
