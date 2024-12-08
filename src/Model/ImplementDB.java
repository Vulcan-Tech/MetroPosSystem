package Model;

import java.sql.*;
import java.util.ArrayList;

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
    public boolean addVendorData(String name, String email, String companyName) {
        String onlineQuery = String.format(
                "INSERT INTO Vendors (name, email, companyName) VALUES ('%s', '%s', '%s')",
                name, email, companyName
        );
        onlineDB.executeQuery(onlineQuery);
        System.out.println("Vendor added successfully.");
        return true;
    }


public Object[][] getVendorsTableData() {
        ArrayList<Vendor> vendors = new ArrayList<>();
        Object[][] tableData = null;
        try {
            String query = "SELECT vendor_id,name, email, companyName FROM Vendors";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vendors.add(new Vendor(
                        rs.getInt("vendor_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("companyName")
                ));
            }

            tableData = new Object[vendors.size()][5];
            for (int i = 0; i < vendors.size(); i++) {
                Vendor vendor = vendors.get(i);
                tableData[i][0] = vendor.getVendor_id();
                tableData[i][1] = vendor.getName();
                tableData[i][2] = vendor.getEmail();
                tableData[i][3] = vendor.getCompanyName();
                tableData[i][4] = ""; // Empty string for button column
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableData;
    }

    public boolean addProductData(String name, String category, double orgprice, double salesprice, int stockqty, int vendor_id) {
        String insertBranchSQL = "INSERT INTO Products (name,category,original_price,sale_price,stock_quantity,vendor_id) VALUES (?, ?, ?, ?, ?, ?)";


                String onlineQuery = String.format(
                        "INSERT INTO Products (name,category,original_price,sale_price,stock_quantity,vendor_id) " +
                                "VALUES ('%s', '%s', %f, %f, %d, %d)",
                        name, category, orgprice, salesprice, stockqty, vendor_id
                );
                onlineDB.executeQuery(onlineQuery);
                System.out.println("Product added successfully.");
                return true;

    }
    public boolean deleteProduct(int productId) {
        String query = "DELETE FROM Products WHERE product_id = ?";

                String onlineQuery = String.format(
                        "DELETE FROM Products WHERE product_id = %d",
                        productId
                );
                onlineDB.executeQuery(onlineQuery);
                return true;
    }

    public Object[][] searchProducts(String searchText) {
        String query = "SELECT product_id, name, category, original_price, sale_price, stock_quantity " +
                "FROM Products WHERE name LIKE ? OR category LIKE ?";
        ArrayList<Object[]> results = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            String searchPattern = "%" + searchText + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("original_price"),
                        rs.getDouble("sale_price"),
                        rs.getInt("stock_quantity")
                };
                results.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Error searching products: " + e.getMessage());
            return new Object[0][0];
        }

        return results.toArray(new Object[0][]);
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
    public Object[][] getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        Object[][] tableData = null;
        try {
            String query = "SELECT product_id, name, category, original_price, sale_price, stock_quantity, vendor_id FROM Products";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("original_price"),
                        rs.getDouble("sale_price"),
                        rs.getInt("stock_quantity"),
                        rs.getInt("vendor_id")
                ));
            }

            tableData = new Object[products.size()][9];
            for (int i = 0; i < products.size(); i++) {
                Product product = products.get(i);
                tableData[i][0] = product.getProduct_id();
                tableData[i][1] = product.getName();
                tableData[i][2] = product.getCategory();
                tableData[i][3] = product.getOriginalPrice();
                tableData[i][4] = product.getSalePrice();
                tableData[i][5] = product.getStockQuantity();
                tableData[i][6] = product.getVendor_id();
                tableData[i][7] = ""; // Empty string for Edit button column
                tableData[i][8] = ""; // Empty string for Delete button column
            }

            rs.close();
            stmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tableData;
    }

    public Object[][] getProductsforBill() {
        ArrayList<Object[]> productList = new ArrayList<>();
        String query = "SELECT name, category, sale_price FROM Products";
        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Object[] product = new Object[4];
                product[0] = rs.getString("name");
                product[1] = rs.getString("category");
                product[2] = rs.getDouble("sale_price");
                product[3] = "Add";
                productList.add(product);
            }
            return productList.toArray(new Object[0][]);
        } catch (SQLException e) {
            System.out.println("Error fetching products: " + e.getMessage());
            return null;
        }
    }

    public Object[][] searchProductsforBill(String searchText) {
        ArrayList<Object[]> productList = new ArrayList<>();
        String query = "SELECT name, category, sale_price FROM Products WHERE name LIKE ? OR category LIKE ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            String searchPattern = "%" + searchText + "%";
            pstmt.setString(1, searchPattern);
            pstmt.setString(2, searchPattern);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Object[] product = new Object[4];
                product[0] = rs.getString("name");
                product[1] = rs.getString("category");
                product[2] = rs.getDouble("sale_price");
                product[3] = "Add";
                productList.add(product);
            }
            return productList.toArray(new Object[0][]);
        } catch (SQLException e) {
            System.out.println("Error searching products: " + e.getMessage());
            return new Object[0][0];
        }
    }

public int getProductStock(String name, String company, double price) {
        String query = "SELECT stock_quantity FROM Products WHERE name = ? AND category = ? AND sale_price = ?";
        try (
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, name);
            pstmt.setString(2, company);
            pstmt.setDouble(3, price);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("stock_quantity");
            }
        } catch (SQLException e) {
            System.out.println("Error getting stock: " + e.getMessage());
        }
        return -1;
    }

    public boolean updateStockAfterBill(String name, String company, double price, int quantitySold) {
        String query = "UPDATE Products SET stock_quantity = stock_quantity - ? WHERE name = ? AND category = ? AND sale_price = ?";

                String onlineQuery = String.format(
                        "UPDATE Products SET stock_quantity = stock_quantity - %d " +
                                "WHERE name = '%s' AND category = '%s' AND sale_price = %f",
                        quantitySold, name, company, price
                );
                onlineDB.executeQuery(onlineQuery);
                return true;

    }
}
