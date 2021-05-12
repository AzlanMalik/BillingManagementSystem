package Application.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection connection = null;

    private static final String USER = "system";
    private static final String PASS = "12345678900Ab";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String driver = "oracle.jdbc.driver.OracleDriver";

    public static Connection getConnection(){
        try{
            System.out.println("new Connection");
            connection = DriverManager.getConnection(URL,USER,PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() throws SQLException{
        if(connection != null){
            connection.close();
        }
    }


}
