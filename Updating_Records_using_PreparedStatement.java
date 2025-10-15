import java.sql.*;

public class DataUpdateDemo {

    public void updateEmployeeSalary(Connection con, int employeeId, double newSalary) {
        // Update statement targets a row based on ID, setting a new salary
        String updateSQL = "UPDATE Employees SET Salary =? WHERE ID =?";
        PreparedStatement ps = null;

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(updateSQL);
            
            // Set parameters: New Salary first, then the ID for the WHERE clause
            ps.setDouble(1, newSalary);
            ps.setInt(2, employeeId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {
                con.commit();
                System.out.println("Employee ID " + employeeId + " updated successfully.");
            } else if (rowsAffected == 0) {
                con.rollback();
                System.out.println("Employee ID " + employeeId + " not found. No update performed.");
            } else {
                // Handle unexpected case where multiple rows were updated (e.g., if ID wasn't unique)
                con.rollback(); 
                System.err.println("CRITICAL ERROR: Multiple rows updated. Rolling back.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            // Rollback on exception
            try {
                if (con!= null) con.rollback();
            } catch (SQLException rb_ex) {
                rb_ex.printStackTrace();
            }
        } finally {
            try {
                if (ps!= null) ps.close();
            } catch (SQLException close_ex) {
                close_ex.printStackTrace();
            }
        }
    }
}
