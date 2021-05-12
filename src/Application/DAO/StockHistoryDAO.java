package Application.DAO;


import Application.Model.StockHistory;
import Application.connection.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class StockHistoryDAO {

    private Connection connection;
    private ResultSet rs;
    private Statement stmt;
    private PreparedStatement pst;




    ObservableList<StockHistory> stockHistoryList;


    public ObservableList<StockHistory> getStockHistory(){

        stockHistoryList = FXCollections.observableArrayList();

        try {

            connection= ConnectionFactory.getConnection();

            String query = "Select SU.stockUpdate_id, P.product_name, P.product_description, SU.stockupdate_quantity, SU.stockupdate_date, SU.manufacturer_name  From StockUpdate_Table SU Inner JOIN Product_Table P ON SU.product_id = P.product_id  ";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){
                stockHistoryList.add(new StockHistory(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDate(5).toLocalDate(),
                        rs.getString(6)
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
          closeConnection();
        }

        return stockHistoryList;

    }


    //// MEthods
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
