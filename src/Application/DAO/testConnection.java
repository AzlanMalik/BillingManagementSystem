package Application.DAO;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class testConnection {

    private Connection connection;
    private String username = "system";
    private String password = "12345678900Ab";
    private String url = "jdbc:oracle:thin:@localhost:1521:xe";
    private String driver = "oracle.jdbc.driver.OracleDriver";



    public Connection getTestConnection(){

        try {
            Class.forName(driver);
            connection= DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public void closeTestConnection(Connection con){

           /* try {
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
                if(connection!=null) connection.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }*/

    }

}
