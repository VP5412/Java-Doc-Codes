import java.sql.*;

public class UpdatableResultSetDemo {

    public void updateInPlace(Connection con) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Configure statement for scrollable and updatable results
            stmt = con.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, 
                ResultSet.CONCUR_UPDATABLE
            );
            rs = stmt.executeQuery("SELECT ID, Name, Salary FROM Employees");

            // Update the third record (assuming it exists)
            if (rs.absolute(3)) { 
                System.out.println("Updating record 3...");
                
                // Local updates
                rs.updateString("Name", "New Name");
                rs.updateDouble("Salary", 95000.00); 

                // Commit changes to the database
                rs.updateRow(); 
                System.out.println("Record 3 updated successfully via ResultSet.");
                
                // Delete the second record (demonstrating deleteRow)
                if (rs.absolute(2)) {
                    rs.deleteRow();
                    System.out.println("Record 2 deleted successfully via ResultSet.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Standard resource closing
        }
    }
}
