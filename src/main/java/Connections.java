
import java.sql.*;

// This program is just to check the connection with the database, can be excluded from the project.
public class Connections {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/bookstore";
        String user = "root";
        String password = "****";
        String querys = "INSERT INTO bookstore.books(title, author, price, stock) VALUES (?,?,?,?)";
        String display = "SELECT * FROM bookstore.books"; 

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to MySQL successfully!");
             PreparedStatement ps = conn.prepareStatement(querys);
             Statement st = conn.createStatement();
             
             ps.setString(1, "The Rising Sun 2");
             ps.setString(2, "Manndy Sam");
             ps.setDouble(3, 452.00);
             ps.setInt(4, 1);
             
             int insertedrows = ps.executeUpdate();
             
             if(insertedrows > 0){
                 System.out.println("Book inserted success");
             }
             else{
                 System.out.println("Failed insertion");
             }
             
             
             
             ResultSet res = st.executeQuery(display);
             
             while(res.next()){
                 System.out.println(res.getInt("Book_Id") + " | " 
                   + res.getString("title") + " | " 
                   + res.getString("author") + " | " 
                   + res.getDouble("price") + " | " 
                   + res.getInt("stock"));
             }
           
        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
