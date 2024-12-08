package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImplementDB {
    protected String url = "jdbc:mysql://localhost:3306/MetroPosDB";
    protected String username = "root";
    protected String password = "legendcr7";
    Connection conn;

    private ImplementOnlineDB onlineDB;

    public ImplementDB(){
        try {
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connection successful !");
        }
        catch (SQLException e){
            System.out.println("Connection failed : "+ e.getMessage());
        }
    }

    public void setOnlineDB(ImplementOnlineDB onlineDB) {
        this.onlineDB = onlineDB;
        System.out.println("Online DB initialized");
    }

// these are the functions to be used in Super Admin

    public boolean validateEmployeeLogin(int id,String email,String password){
        String query = "SELECT password FROM Employees WHERE branch_id = ? AND email = ?";

        try {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            pstmt.setString(2, email);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedPassword = rs.getString("password");
                return storedPassword.equals(password);
            }
        } catch (SQLException e) {
            System.out.println("Error validating employee credentials: " + e.getMessage());
        }

        return false;
    }

    public boolean validateLogin(String email,String passcode){
        String query = "SELECT password FROM Employees where email = ?";

        try{
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,email);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                String mypassword = rs.getString("password");

                return mypassword.equals(passcode);
            }
        } catch (SQLException e) {
            System.out.println("Error validating credentials : "+ e.getMessage());
        }

        return false;
    }

    public boolean addBranchDatatoDB(String city, String address, String phoneNumber) {
        String insertBranchSQL = "INSERT INTO Branches (city, address, phone) VALUES (?, ?, ?)";

        try {
            // Local DB operation is handled by onlineDB.executeQuery
            String formattedQuery = String.format(
                    "INSERT INTO Branches (city, address, phone) VALUES ('%s', '%s', '%s')",
                    city, address, phoneNumber);
            onlineDB.executeQuery(formattedQuery);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addEmployeesData(String name, String email, double salary, String role, int branchCode) {
        String insertManagerSQL = "INSERT INTO Employees (name, email, salary, role, branch_id) VALUES (?, ?, ?, ?, ?)";

        String employeeQuery = String.format(
                "INSERT INTO Employees (name, email, salary, role, branch_id) VALUES ('%s', '%s', %f, '%s', %d)",
                name, email, salary, role, branchCode
        );
        onlineDB.executeQuery(employeeQuery);

    }

    public boolean isPasswordChanged(String email) {
        String query = "SELECT passwordChanged FROM Employees WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("passwordChanged");
            }
            return false;
        } catch (SQLException e) {
            System.out.println("Error checking password status: " + e.getMessage());
            return false;
        }
    }

    public List<Employee> fetchEmpList(int branchId){
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT employee_id,name,email,salary,role FROM Employees WHERE branch_id = ?";

        try{
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,branchId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int empId = rs.getInt("employee_id");
                String empName = rs.getString("name");
                String empEmail = rs.getString("email");
                double empSalary = rs.getDouble("salary");
                String empRole = rs.getString("role");

                Employee employee = new Employee(empId, empName, empEmail, empSalary, empRole);
                employeeList.add(employee);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return employeeList;
    }

    public int fetchBranchId() {
        int branchCode = -1; // Default value indicating no branchCode retrieved yet

        // SQL query to fetch the last inserted branch_id from the Branches table
        String selectBranchIdSQL = "SELECT branch_id FROM Branches ORDER BY branch_id DESC LIMIT 1";

        try (PreparedStatement pstmt = conn.prepareStatement(selectBranchIdSQL)) {
            // Execute the query to fetch the most recent branch_id
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    branchCode = rs.getInt("branch_id"); // Get the last inserted branch_id
                    System.out.println("Fetched Branch Code: " + branchCode);  // Debug output
                } else {
                    System.out.println("No branch_id found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return branchCode; // Return the fetched branch_id
    } // to be used in Super Admin

    public boolean validateSACurrentPassword(String currentPass) {
        String query = "SELECT * FROM Employees WHERE role = 'SuperAdmin' AND password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, currentPass);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error validating password: " + e.getMessage());
            return false;
        }
    }

    public boolean updateSAPassword(String newPass) {
        System.out.println("1. Starting password update");
        String query = "UPDATE Employees SET password = ? WHERE role = 'SuperAdmin'";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPass);
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("2. Local update complete. Rows affected: " + rowsAffected);
            System.out.println("3. OnlineDB object exists: " + (onlineDB != null));

            if(rowsAffected > 0 && onlineDB != null) {
                String onlineQuery = String.format(
                        "UPDATE Employees SET password = '%s' WHERE role = 'SuperAdmin'",
                        newPass);
                System.out.println("4. Sending to online DB: " + onlineQuery);
                onlineDB.executeQuery(onlineQuery);
                System.out.println("5. Online update complete");
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error in updateSAPassword: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean updatePasswordAndStatus(String email, String newPass) {
        String query = "UPDATE Employees SET password = ?, passwordChanged = true WHERE email = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPass);
            pstmt.setString(2, email);
            System.out.println("Changing password in the local db");
            int rowsAffected = pstmt.executeUpdate();

            if(rowsAffected > 0) {
                //&& onlineDB != null
                String onlineQuery = String.format(
                        "UPDATE Employees SET password = '%s', passwordChanged = true WHERE email = '%s'",
                        newPass, email);
                onlineDB.executeQuery(onlineQuery);
            }
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Error updating password and status: " + e.getMessage());
            return false;
        }
    }

    // iss se neechay zain ko bhejne


    public boolean validateCurrentPassword(int branchId, String email, String currentPassword) {
        try {
            String query = "SELECT * FROM Employees WHERE branch_id = ? AND email = ? AND password = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setInt(1, branchId);
            pst.setString(2, email);
            pst.setString(3, currentPassword);

            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //    public boolean addCashierData(String name,String email,double salary){
//        String insertBranchSQL = "INSERT INTO Employees (name, email, salary) VALUES (?, ?, ?)";
//
//        try {
//            // Assuming dbObject handles your database connection
//            PreparedStatement pstmt = conn.prepareStatement(insertBranchSQL);
//
//            // Set the values for city, address, and phone number
//            pstmt.setString(1, name);
//            pstmt.setString(2, email);
//            pstmt.setDouble(3, salary);
//
//            // Execute the update to insert the branch
//            int affectedRows = pstmt.executeUpdate();
//
//            // Check if the insert was successful
//            if (affectedRows > 0) {
//                System.out.println("Cashier added successfully.");
//                return true;
//            } else {
//                System.out.println("Insert failed.");
//                return false;
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }


}
