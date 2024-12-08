package Model;

import java.sql.*;

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

        try {
            // Local DB insert
            PreparedStatement pstmt = conn.prepareStatement(insertManagerSQL);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setDouble(3, salary);
            pstmt.setString(4, role);
            pstmt.setInt(5, branchCode);

            int result = pstmt.executeUpdate();

            if(result > 0) {
                // Online DB insert - only employee data
                String employeeQuery = String.format(
                        "INSERT INTO Employees (name, email, salary, role, branch_id) VALUES ('%s', '%s', %f, '%s', %d)",
                        name, email, salary, role, branchCode
                );
                onlineDB.executeQuery(employeeQuery);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
