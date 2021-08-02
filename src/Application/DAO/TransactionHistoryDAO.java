package Application.DAO;

import Application.Model.TransactionHistory;
import Application.Utils.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;


public class TransactionHistoryDAO {

        private Connection connection;
        private Statement stmt;
        private PreparedStatement pst;
        private ResultSet rs;



        ObservableList<TransactionHistory> transactionHistoryList;


        public ObservableList<TransactionHistory> getTransactionHistoryList(){

            transactionHistoryList = FXCollections.observableArrayList();

            try {

                connection= ConnectionFactory.getConnection();

                String  query = "Select T.invoice_id, C.customer_Name, C.customer_companyName, C.customer_city, C.customer_previousBalance, T.transaction_amount, T.transaction_date , S.salesman_name " +
                                  " From Transaction_Table T " +
                                   " Join Invoice_Table I ON T.invoice_id = I.invoice_id " +
                                      " Join Customer_Table C ON I.customer_id = C.customer_id " +
                                       " Join Salesman_Table S ON T.salesman_id = S.salesman_id ";

                stmt = connection.createStatement();
                rs = stmt.executeQuery(query);

                while (rs.next()){
                    transactionHistoryList.add(new TransactionHistory(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getInt(5),
                            rs.getInt(6),
                            rs.getDate(7).toLocalDate(),
                            rs.getString(8)
                    ));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally{
                closeConnection();
            }

            return transactionHistoryList;

        }

        ////// mETHODS
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
