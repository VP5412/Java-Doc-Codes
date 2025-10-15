import java.sql.*;

public class DataDeleteDemo {

    public void deleteEmployee(Connection con, int employeeId) {
        String deleteSQL = "DELETE FROM Employees WHERE ID =?";
        PreparedStatement ps = null;

        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(deleteSQL);
            
            // Set parameter for the WHERE clause
            ps.setInt(1, employeeId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 1) {
                con.commit();
                System.out.println("Employee ID " + employeeId + " deleted successfully.");
            } else {
                con.rollback();
                System.out.println("Deletion failed (ID not found or multiple deleted). Rows affected: " + rowsAffected);
            }

        } catch (SQLException e) {
            e.printStackTrace();
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
