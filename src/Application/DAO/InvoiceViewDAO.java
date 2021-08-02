package Application.DAO;

import Application.Model.Invoice;
import Application.Model.NewInvoice;
import Application.Utils.ConnectionFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

            String query = "Select I.invoice_id, C.customer_name, C.customer_companyName, I.invoice_date, I.invoice_totalAmount, I.invoice_paidAmount, S.salesman_name, I.invoice_status  From Invoice_Table I  Inner Join Customer_Table C ON I.customer_id = C.customer_id  Join Salesman_Table S  ON I.salesman_id = S.salesman_id";

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

    public ArrayList<String> getCustNameList(){
        ArrayList<String>   list = new ArrayList<>();
        try{
            connection = ConnectionFactory.getConnection();

            String query = "Select Customer_Name From Customer_Table ";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                list.add(rs.getString(1));
            }

        }catch(Exception e){
        }finally{
            closeConnection();
        }

        return list;
    }

    public String[] getCustDetails(String name){
        String[] arr = new String[4];
        try {
            connection = ConnectionFactory.getConnection();

            String query = "Select customer_id, customer_companyName,customer_previousBalance ,customer_address,customer_city,customer_state" +
                    " From Customer_Table where Customer_Name = ? ";

            pst = connection.prepareStatement(query);
            pst.setString(1,name);
            rs = pst.executeQuery();

            while(rs.next()){
            arr[0] = String.valueOf(rs.getInt(1));
            arr[1] = rs.getString(2);
            arr[2] = String.valueOf(rs.getInt(3));
            arr[3] = rs.getString(4) +", "+rs.getString(5)+", "+rs.getString(6);
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return arr;
    }

    public String[] getInvoiceId(){
        String[] arr = new String[2];
        try {
            connection = ConnectionFactory.getConnection();

            String query = "Select max(invoice_id) ,sysdate from Invoice_Table ";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                arr[0] = String.valueOf(rs.getInt(1));
                arr[1] = String.valueOf(rs.getDate(2));
            }
        }catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeConnection();
        }
        return arr;
    }

    public ObservableList<String> getProductList(){
        ObservableList<String> list = FXCollections.observableArrayList();
        try{
            connection = ConnectionFactory.getConnection();

            String query = "Select Product_Name from Product_Table ";

            stmt = connection.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                list.add(rs.getString(1));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            closeConnection();
        }
        return list;
    }

    public String[] getProductDetails(String productName){
        String[] productDetails = new String[4];
        try{
            connection = ConnectionFactory.getConnection();
            String query = "Select  product_id,product_description, product_price, product_quantity From Product_Table Where product_Name = ? ";
            pst = connection.prepareStatement(query);
            pst.setString(1,productName);
            rs = pst.executeQuery();
            while(rs.next()){
                productDetails[0] = String.valueOf(rs.getInt(1));
                productDetails[1] = rs.getString(2);
                productDetails[2] = String.valueOf(rs.getInt(3));
                productDetails[3] = String.valueOf(rs.getInt(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return productDetails;
    }

    public void addNewInvoice(String[] a){
        try{
            connection = ConnectionFactory.getConnection();
            String query ="Insert Into Invoice_Table(invoice_id, customer_id, invoice_bilityNo, invoice_date, invoice_duedate,salesman_id, invoice_comment, invoice_pComment, invoice_totalAmount, invoice_paidAmount, invoice_status ,transport_name) " +
                    " Values(?,?,?,?,?,( Select salesman_id from salesman_Table where salesman_name = ?),?,?,?,?,?,?) ";

            pst = connection.prepareStatement(query);

            pst.setInt(1,Integer.valueOf(a[0])); //invoice Id
            pst.setInt(2, Integer.parseInt(a[1])); //customer Id

            if(a[2].isEmpty())
                pst.setNull(3, Types.INTEGER);
            else
                pst.setInt(3, Integer.parseInt(a[2])); //bilty No

            pst.setDate(4, Date.valueOf(a[3])); //Date

            if(a[4] != null)
                pst.setDate(5, Date.valueOf(a[4])); //Due Date
            else
                pst.setNull(5,Types.DATE);

            if(a[5] != null)
                pst.setString(6, a[5]); //salesman Id        ///////////Check It Later
            else
                pst.setString(6,"Sajid Iqbal");//////------ Check IT later to Change id or create null string salesman

            if(a[6] != null)
                pst.setString(7, a[6]); //Comment
            else
                pst.setNull(7,Types.VARCHAR);

            if(a[7] != null)
                pst.setString(8, a[7]); //Private Comment
            else
                pst.setNull(8,Types.VARCHAR);

            pst.setInt(9, Integer.parseInt(a[8])); //Total Amount
            pst.setInt(10, Integer.parseInt(a[9])); //Paid Amount
            pst.setInt(11, Integer.parseInt(a[10])); //Status

            if(a[11] != null)
                pst.setString(12, a[11]); //Transport Name
            else
                pst.setNull(12,Types.VARCHAR);

            pst.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            closeConnection();
        }
    }

    public void addNewInvoiceItems(int invoiceId,ObservableList<NewInvoice> invoiceList){

            int size = invoiceList.size();
            int count = 0;
        try{
            connection = ConnectionFactory.getConnection();

            String query = "Insert Into InvoiceItems_Table " +
                    " Values(?,?,?,?,?,?,?,?) ";

            pst = connection.prepareStatement(query);
            connection.setAutoCommit(false);

            while(count != size-1) {
                NewInvoice newInvoice = invoiceList.get(count);

                if(newInvoice.getQuantity() != 0) {
                    pst.setInt(1,invoiceId);
                    pst.setInt(2,newInvoice.getProductId());
                    pst.setInt(3,newInvoice.getUnitPrice());
                    pst.setInt(4,newInvoice.getQuantity());
                    pst.setInt(5,newInvoice.getTax());
                    pst.setInt(6,newInvoice.getDiscount());
                    pst.setInt(7,newInvoice.getTotalPrice());
                    pst.setInt(8,newInvoice.getTotalPrice());

                    pst.addBatch();
                }
                count = count + 1;
            }

            pst.executeBatch();
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally{
            closeConnection();
        }
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




    public List<InvoiceJRBean> getInvoice(int invoiceId){
        InvoiceJRBean invoice = null;
        try{
        connection = ConnectionFactory.getConnection();

        String query = "Select invoice_id,Invoice_DueDate ,Invoice_BilityNo  , Invoice_Date, Transport_Name, Invoice_TotalAmount," +
                "  customer_Name , Customer_companyName, Customer_address, Customer_city,Customer_PreviousBalance " +
                "   ,Salesman_Name From Invoice_Table I  Join Customer_Table C On C.Customer_id = I.Customer_id Join Salesman_Table S ON " +
                " S.salesman_id = I.salesman_id where Invoice_id = ?";

        pst = connection.prepareStatement(query);
        pst.setInt(1,invoiceId);
        rs = pst.executeQuery();

        while (rs.next()) {
            invoice = new InvoiceJRBean(
                    rs.getInt(1),
                    rs.getDate(2),
                    rs.getInt(3),
                    rs.getDate(4),
                    rs.getString(5),
                    rs.getInt(6),
                    rs.getString(7),
                    rs.getString(8),
                    rs.getString(9),
                    rs.getString(10),
                    rs.getInt(11),
                    rs.getString(12),
                    rs.getInt(6) + rs.getInt(11));

        }

    } catch (SQLException throwables) {
        throwables.printStackTrace();
    }finally {
        closeConnection();
    }
        List<InvoiceJRBean> list = new ArrayList<>();
        list.add(invoice);
        return list;
    }

    public List<NewInvoice> getInvoiceItems(int invoiceId){
        List<NewInvoice> list = new ArrayList<NewInvoice>();

        try{
            connection = ConnectionFactory.getConnection();

            String query = "Select product_name,product_description,invoiceItems_unitPrice, invoiceItems_quantity, InvoiceItems_tax," +
                    " invoiceItems_discount, InvoiceItems_TotalUnitPrice, InvoiceItems_totalPrice from InvoiceItems_table I " +
                    " Join Product_table P ON P.product_id = I.product_id where Invoice_id = ?";

            pst = connection.prepareStatement(query);
            pst.setInt(1,invoiceId);
            rs = pst.executeQuery();

            while (rs.next()) {
                list.add(new NewInvoice(
                   rs.getInt(4),
                   rs.getString(1),
                   rs.getString(3),
                   rs.getInt(3),
                   rs.getInt(5),
                   rs.getInt(6),
                   rs.getInt(7),
                   rs.getInt(8),
                  0,0
                ));

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            closeConnection();
        }
        return list;
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
