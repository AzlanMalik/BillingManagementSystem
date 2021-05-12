package Application.DAO;

import Application.connection.ConnectionFactory;

import java.sql.*;

public class LoginDAO {

    private Connection connection;
    private ResultSet rs;
    private Statement stmt;
    private PreparedStatement pst;


    Boolean loginStatus;

    public Boolean login(String  name, String pass){
        loginStatus = false;

        try {
            connection= ConnectionFactory.getConnection();

            String query = "Select user_username, user_password From User_Table where user_username = ? AND user_password = ? ";

            pst = connection.prepareStatement(query);
            pst.setString(1,name);
            pst.setString(2,pass);

            rs = pst.executeQuery();

            if (rs.next())
                loginStatus = true;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally{
            closeConnection();
        }
        return loginStatus;
    }


    ///// methods
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
