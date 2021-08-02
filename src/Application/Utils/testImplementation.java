package Application.Utils;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class testImplementation implements testInterface {

    Connection connection = ConnectionFactory.getConnection();

   public String[] testConnection(int id){
       if(connection != null)
           System.out.println(1);
       PreparedStatement pst = null;
       String[] array = new String[5];

       try{
           String query = "Select User_name From User_Table";
           pst = connection.prepareStatement(query);
           ResultSet rs = pst.executeQuery();
           int x=0;

           while(rs.next())
               array[x++] = rs.getString(1);

           pst.close();
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       }
       if(connection != null)
        System.out.println(11);
       return array;
   }
}
