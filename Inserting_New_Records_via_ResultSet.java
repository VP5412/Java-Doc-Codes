import java.sql.*;

public class UpdatableResultSetInsertDemo {

    public void insertViaResultSet(Connection con) {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Must be configured as updatable
            stmt = con.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, 
                ResultSet.CONCUR_UPDATABLE
            );
            rs = stmt.executeQuery("SELECT ID, Name, Salary FROM Employees");

            // Move cursor to the insert row buffer
            rs.moveToInsertRow();
            
            // Build the new row using update methods
            rs.updateInt("ID", 1001);
            rs.updateString("Name", "Jane Doe");
            rs.updateDouble("Salary", 72000.00); 

            // Commit the row
            rs.insertRow();
            
            // Move back to the result set data area
            rs.moveToCurrentRow(); 
            System.out.println("New record (1001) inserted successfully via ResultSet.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Standard resource closing
        }
    }
}
