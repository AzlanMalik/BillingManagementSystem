package Application.DAO;

import Application.Model.Transaction;
import Application.Utils.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class TransactionDAO {
    private Connection connection;
    private Statement stmt;
    private PreparedStatement pst;
    private ResultSet rs;



    ObservableList<Transaction> transactionList;


    public ObservableList<Transaction> getTransactionList(){

        transactionList = FXCollections.observableArrayList();

        try {

             connection= ConnectionFactory.getConnection();

            String query = "Select I.invoice_id,C.customer_name,C.customer_companyName,C.customer_city,I.invoice_totalAmount,I.invoice_paidAmount,I.invoice_dueDate,C.customer_previousBalance, S.Salesman_name ,I.invoice_status  From Invoice_Table I  Inner Join Customer_Table C  ON  I.customer_id =  C.customer_id Join Salesman_Table S ON I.Salesman_id = S.Salesman_id Where invoice_Status = 0";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){
                transactionList.add(new Transaction(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getDate(7).toLocalDate(),
                        rs.getInt(8),
                        rs.getString(9),
                        "Unpaid"
                        ));
                   }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeConnection();
        }

        return transactionList;

    }

    public String[] getInvoiceData(String id){

        String[] data = new String[5];

        try {
            connection = ConnectionFactory.getConnection();

            String query = "Select C.customer_name, C.customer_companyName, C.customer_city, I.invoice_totalAmount-I.invoice_paidAmount, S.Salesman_name From Invoice_Table I Join Customer_Table C ON I.customer_id = C.Customer_id  Join Salesman_Table S ON I.salesman_id = S.salesman_id   where I.invoice_id = ? ";

            pst = connection.prepareStatement(query);
            pst.setString(1,id);

            rs = pst.executeQuery();

            while(rs.next()) {
                data[0] = rs.getString(1);
                data[1] = rs.getString(2);
                data[2] = rs.getString(3);
                data[3] = rs.getString(4);
                data[4] = rs.getString(5);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeConnection();
        }
        return data;
    }

    public ArrayList<String> getSalesmanList(){
        ArrayList<String> salesmanList = new ArrayList<>();
        try {

            connection= ConnectionFactory.getConnection();

            String query = "Select salesman_name From Salesman_Table ";
            stmt = connection.createStatement();


            rs = stmt.executeQuery(query);

            while(rs.next()){
               salesmanList.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeConnection();
        }


        return salesmanList;
    }


    ///// Deposit Methods

    public void addDeposit(String[] dataArray){
        try {

            connection= ConnectionFactory.getConnection();

            int salesmanId = 0;
            String sql = "select Salesman_id From Salesman_Table where salesman_name = ?";
            pst = connection.prepareStatement(sql);
            pst.setString(1,dataArray[1]);
            rs = pst.executeQuery();
            while(rs.next()){
                salesmanId = rs.getInt(1);
            }


            String query = "Insert Into Transaction_Table T  (T.invoice_id, T.salesman_id, T.transaction_amount)  Values(?,?,?) ";
            pst = connection.prepareStatement(query);
            pst.setInt(1,parseInt(dataArray[0]));
            //pst.setString(2,dataArray[1]);
            pst.setInt(2,salesmanId);
            pst.setInt(3,parseInt(dataArray[2]));
          //  pst.setString(4,dataArray[3]);
            pst.executeQuery();


        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            closeConnection();
        }
    }



    ////// Methods

    public LocalDate getDate(){
        LocalDate date = null;
        try{
            connection = ConnectionFactory.getConnection();

            stmt = connection.createStatement();
            rs = stmt.executeQuery("Select sysdate From Invoice_Table ");

            while (rs.next()) {
                date = rs.getDate(1).toLocalDate();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            closeConnection();
        }
    return date;
    }

    private String getStatus(int status){
        if(status == 0) {
            return "Unpaid";
        }
        else
            return "Paid";
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
