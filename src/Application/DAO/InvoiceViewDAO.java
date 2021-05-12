package Application.DAO;

import Application.Model.Invoice;
import Application.connection.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class InvoiceViewDAO {

    private Connection connection;

    private ResultSet rs;
    private Statement stmt;
    private PreparedStatement pst;

    ObservableList<Invoice> invoiceList;


    public ObservableList<Invoice> getInvoiceList() {

        invoiceList = FXCollections.observableArrayList();

        try {
            connection = ConnectionFactory.getConnection();

            String query = "Select I.invoice_id, C.customer_name, C.customer_companyName, I.invoice_date, I.invoice_total, I.invoice_paidAmount, S.salesman_name, I.invoice_status  From Invoice_Table I  Inner Join Customer_Table C ON I.customer_id = C.customer_id  Join Salesman_Table S  ON I.salesman_id = S.salesman_id";

             stmt = connection.createStatement();
             rs = stmt.executeQuery(query);

            while (rs.next()){

                invoiceList.add(new Invoice(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4).toLocalDate(),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        getStatus(rs.getInt(8))
                ));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally{
            closeConnection();
        }

        return invoiceList;

    }

    ////////

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


    private String getStatus(int status){
        if(status == 0) {
            return "Unpaid";
        }
        else
            return "Paid";
    }

}
