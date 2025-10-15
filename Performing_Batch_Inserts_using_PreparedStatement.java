import java.sql.*;
import java.util.List;
import java.util.Arrays;

// Simple structure to hold data for bulk insert
class EmployeeData {
    int id;
    String name;
    double salary;
    public EmployeeData(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
}

public class BatchInsertDemo {

    public void insertBatch(Connection con, List<EmployeeData> employees) {
        String insertSQL = "INSERT INTO Employees (ID, Name, Salary) VALUES (?,?,?)";
        PreparedStatement ps = null;

        try {
            // Step 1: Disable Auto-Commit - Essential for batch integrity
            con.setAutoCommit(false); 
            ps = con.prepareStatement(insertSQL);

            // Step 2: Loop through data, set parameters, and add to batch
            for (EmployeeData emp : employees) {
                ps.setInt(1, emp.id);
                ps.setString(2, emp.name);
                ps.setDouble(3, emp.salary);
                ps.addBatch(); // Add command to the queue
            }

            // Step 3: Execute the entire batch
            int updateCounts = ps.executeBatch();
            
            // Step 4: Verify results and commit
            System.out.println("Batch execution complete. Update counts: " + Arrays.toString(updateCounts));

            // If execution reaches here, commit the entire transaction
            con.commit();
            System.out.println("Batch committed successfully.");

        } catch (BatchUpdateException bue) {
            // Handle specific batch errors, analyze update counts array from the exception
            System.err.println("Batch Update Failed: Rolling back.");
            System.err.println("Partial results: " + Arrays.toString(bue.getUpdateCounts()));
            try {
                if (con!= null) con.rollback();
            } catch (SQLException rb_ex) {
                rb_ex.printStackTrace();
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
