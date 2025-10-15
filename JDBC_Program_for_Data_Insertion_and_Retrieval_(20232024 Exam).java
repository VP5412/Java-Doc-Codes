import java.sql.*;

public class ProductManager {
    
    // Database connection details (replace with actual values)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/shopdb";
    private static final String USER = "root";
    private static final String PASS = "password";

    public static void main(String args) {
        Connection con = null;
        PreparedStatement ps = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            // 1. Load JDBC Driver (assuming MySQL)
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // 2. Establish Connection
            con = DriverManager.getConnection(DB_URL, USER, PASS);
            
            // 3. Transaction Control: Disable auto-commit
            con.setAutoCommit(false); 
            
            // --- INSERT Operation (DML) ---
            
            String insertSQL = "INSERT INTO products (id, name, price) VALUES (?,?,?)";
            ps = con.prepareStatement(insertSQL);
            
            // Insert specific record data
            int newId = 205;
            String newName = "Monitor";
            double newPrice = 8500.00;
            
            // Bind parameters to placeholders (?)
            ps.setInt(1, newId);
            ps.setString(2, newName);
            ps.setDouble(3, newPrice);
            
            // Execute the insertion DML command
            int rowsAffected = ps.executeUpdate();
            
            if (rowsAffected > 0) {
                // Commit the changes if insertion was successful
                con.commit(); 
                System.out.println("Record (ID: " + newId + ") inserted successfully.\n");
            } else {
                con.rollback();
                System.out.println("Insertion failed.");
            }

            // --- Retrieval Operation (Query) ---
            
            st = con.createStatement();
            rs = st.executeQuery("SELECT id, name, price FROM products");
            
            System.out.println("--- All Product Details ---");
            System.out.printf("%-5s %-20s %-10s\n", "ID", "Name", "Price");
            System.out.println("-------------------------------------");

            // Process and display the ResultSet data
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                
                System.out.printf("%-5d %-20s %-10.2f\n", id, name, price);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Rollback upon any exception to maintain consistency
            try {
                if (con!= null) con.rollback(); 
            } catch (SQLException rb_ex) {
                rb_ex.printStackTrace();
            }
        } finally {
            // 4. Close Resources
            try {
                if (rs!= null) rs.close();
                if (st!= null) st.close();
                if (ps!= null) ps.close();
                if (con!= null) con.close();
            } catch (SQLException close_ex) {
                close_ex.printStackTrace();
            }
        }
    }
}
