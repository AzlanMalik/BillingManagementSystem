package Application.Utils;

import java.sql.*;

public class ConnectionFactory {

    private static Connection conn = null;

    private static final String USER = "dci";
    private static final String PASS = "shahabuddin";
    private static final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private static final String driver = "oracle.jdbc.driver.OracleDriver";

    public static Connection getConnection(){
        try{
            conn = DriverManager.getConnection(URL,USER,PASS);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return conn;
    }

    public static void closeQuietly(ResultSet rs) {
        try {
            if (rs != null)
                rs.close();
        }catch(Exception ex) { ex.printStackTrace(); }
    }

    public static void closeQuietly(PreparedStatement pst) {
        try {
            if (pst != null)
                pst.close();
        }catch(Exception ex) { ex.printStackTrace(); }
    }

    public static void closeQuietly(Statement stmt) {
        try {
            if (stmt != null)
                stmt.close();
        }catch(Exception ex) { ex.printStackTrace(); }
    }

    public static void closeQuietly(Connection conn) {
        try {
            if (conn != null)
                conn.close();
        }catch(Exception ex) { ex.printStackTrace(); }
    }
}
