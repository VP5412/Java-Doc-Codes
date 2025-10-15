import java.sql.*;

public class DataInsertionDemo {

    public void insertEmployee(Connection con, int id, String name, double salary) {
        String insertSQL = "INSERT INTO Employees (ID, Name, Salary) VALUES (?,?,?)";
        PreparedStatement ps = null;

        try {
            // Step 1: Disable Auto-Commit for Transaction Management
            con.setAutoCommit(false);

            // Step 2: Prepare the statement and set parameters
            ps = con.prepareStatement(insertSQL);
            ps.setInt(1, id); // Set ID
            ps.setString(2, name); // Set Name
            ps.setDouble(3, salary); // Set Salary

            // Step 3: Execute the DML command
            int rowsAffected = ps.executeUpdate();
            
            // Step 4: Verify and Commit
            if (rowsAffected > 0) {
                con.commit();
                System.out.println("Insertion successful. Rows affected: " + rowsAffected);
            } else {
                con.rollback();
                System.out.println("Insertion failed. Rolling back.");
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
