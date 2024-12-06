package Controller;
import Model.*;
import View.GUI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Linker {
    ImplementDB dbObj;
    ImplementOnlineDB onlineDB;
    Employee employee;

    private GUI gui;

    public Linker(ImplementDB dbObj, ImplementOnlineDB onlineDB) {
        this.dbObj = dbObj;
        this.onlineDB = onlineDB;
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void showEmployeeOptionsMenu() {
        gui.showScreen("EmployeeTypeScreen");
    }

    public boolean LoginValidator(String email, String password) {
        return dbObj.validateLogin(email, password);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\+\\d{1,3}[\\d]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public boolean isPasswordChanged(String email) {
        return dbObj.isPasswordChanged(email);
    }

    public boolean updatePasswordAndStatus(String email, String newPass) {
        return dbObj.updatePasswordAndStatus(email, newPass);
    }

    public boolean validateCurrentPassword(int branchId, String email, String currentPassword) {
        return dbObj.validateCurrentPassword(branchId, email, currentPassword);
    }

    public boolean updatePassword(int branchId, String email, String newPassword) {
        return dbObj.updatePassword(branchId, email, newPassword);
    }

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