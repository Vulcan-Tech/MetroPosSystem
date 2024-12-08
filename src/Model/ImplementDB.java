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

    public int createSaleRecord(int employeeId, int branchId, double totalAmount) {
        // First create sale in local DB and get generated ID
        String localQuery = "INSERT INTO Sales (employee_id, branch_id, total_amount) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(localQuery, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setInt(1, employeeId);
            pstmt.setInt(2, branchId);
            pstmt.setDouble(3, totalAmount);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    int generatedSaleId = rs.getInt(1);

                    // Now create online DB query with the same ID
                    String onlineQuery = String.format(
                            "INSERT INTO Sales (sale_id, employee_id, branch_id, total_amount) VALUES (%d, %d, %d, %f)",
                            generatedSaleId, employeeId, branchId, totalAmount
                    );
                    onlineDB.executeSpecialQuery(onlineQuery);

                    return generatedSaleId;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error creating sale record: " + e.getMessage());
        }
        return -1;
    }

    public int getEmployeeId(String email, int branchId) {
        String query = "SELECT employee_id FROM Employees WHERE email = ? AND branch_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, email);
            pstmt.setInt(2, branchId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("employee_id");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching employee ID: " + e.getMessage());
        }
        return -1;
    }

    // idhr tak

    public Object[][] getStockData() {
        String query = "SELECT name, category, stock_quantity FROM Products ORDER BY name";
        ArrayList<Object[]> stockList = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getInt("stock_quantity")
                };
                stockList.add(row);
            }

            return stockList.toArray(new Object[0][]);
        } catch (SQLException e) {
            System.out.println("Error fetching stock data: " + e.getMessage());
        }
        return new Object[0][0];
    }

    public Object[][] searchStock(String searchText) {
        String query = "SELECT name, category, stock_quantity FROM Products WHERE LOWER(name) LIKE ? ORDER BY name";
        ArrayList<Object[]> stockList = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, "%" + searchText.toLowerCase() + "%");

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                            rs.getString("name"),
                            rs.getString("category"),
                            rs.getInt("stock_quantity")
                    };
                    stockList.add(row);
                }
            }

            return stockList.toArray(new Object[0][]);
        } catch (SQLException e) {
            System.out.println("Error searching stock: " + e.getMessage());
        }
        return new Object[0][0];
    }


//    public boolean processReturn(int saleId, int productId, int returnQuantity) {
//        String updateSaleItem = "UPDATE SaleItems SET returned_quantity = returned_quantity + ? " +
//                "WHERE sale_id = ? AND product_id = ?";
//        String updateProduct = "UPDATE Products SET stock_quantity = stock_quantity + ? " +
//                "WHERE product_id = ?";
//        String updateSaleTotal = "UPDATE Sales SET total_amount = total_amount - ? " +
//                "WHERE sale_id = ?";
//
//        try {
//            conn.setAutoCommit(false);
//
//            // Get the price of returned items
//            double returnAmount = getPriceForReturn(saleId, productId) * returnQuantity;
//
//            try (PreparedStatement pstmt1 = conn.prepareStatement(updateSaleItem);
//                 PreparedStatement pstmt2 = conn.prepareStatement(updateProduct);
//                 PreparedStatement pstmt3 = conn.prepareStatement(updateSaleTotal)) {
//
//                pstmt1.setInt(1, returnQuantity);
//                pstmt1.setInt(2, saleId);
//                pstmt1.setInt(3, productId);
//                pstmt1.executeUpdate();
//
//                pstmt2.setInt(1, returnQuantity);
//                pstmt2.setInt(2, productId);
//                pstmt2.executeUpdate();
//
//                pstmt3.setDouble(1, returnAmount);
//                pstmt3.setInt(2, saleId);
//                pstmt3.executeUpdate();
//
//                conn.commit();
//                return true;
//            }
//        } catch (SQLException e) {
//            try {
//                conn.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace();
//            }
//            System.out.println("Error processing return: " + e.getMessage());
//            return false;
//        } finally {
//            try {
//                conn.setAutoCommit(true);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }


    public boolean processReturn(int saleId, int productId, int returnQuantity) {
        String updateSaleItem = "UPDATE SaleItems SET returned_quantity = returned_quantity + ? WHERE sale_id = ? AND product_id = ?";
        String updateProduct = "UPDATE Products SET stock_quantity = stock_quantity + ? WHERE product_id = ?";
        String updateSaleTotal = "UPDATE Sales SET total_amount = total_amount - ? WHERE sale_id = ?";

        try {
            conn.setAutoCommit(false);
            double returnAmount = getPriceForReturn(saleId, productId) * returnQuantity;

            try (PreparedStatement pstmt1 = conn.prepareStatement(updateSaleItem);
                 PreparedStatement pstmt2 = conn.prepareStatement(updateProduct);
                 PreparedStatement pstmt3 = conn.prepareStatement(updateSaleTotal)) {

                // Local DB updates
                pstmt1.setInt(1, returnQuantity);
                pstmt1.setInt(2, saleId);
                pstmt1.setInt(3, productId);
                pstmt1.executeUpdate();

                pstmt2.setInt(1, returnQuantity);
                pstmt2.setInt(2, productId);
                pstmt2.executeUpdate();

                pstmt3.setDouble(1, returnAmount);
                pstmt3.setInt(2, saleId);
                pstmt3.executeUpdate();

                // Online DB updates using formatted queries
                String onlineQuery1 = String.format(
                        "UPDATE SaleItems SET returned_quantity = returned_quantity + %d WHERE sale_id = %d AND product_id = %d",
                        returnQuantity, saleId, productId
                );
                String onlineQuery2 = String.format(
                        "UPDATE Products SET stock_quantity = stock_quantity + %d WHERE product_id = %d",
                        returnQuantity, productId
                );
                String onlineQuery3 = String.format(
                        "UPDATE Sales SET total_amount = total_amount - %f WHERE sale_id = %d",
                        returnAmount, saleId
                );

                onlineDB.executeSpecialQuery(onlineQuery1);
                onlineDB.executeSpecialQuery(onlineQuery2);
                onlineDB.executeSpecialQuery(onlineQuery3);

                conn.commit();
                return true;
            }
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Error processing return: " + e.getMessage());
            return false;
        } finally {
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public double getPriceForReturn(int saleId, int productId) {
        String query = "SELECT price_at_sale FROM SaleItems WHERE sale_id = ? AND product_id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, saleId);
            pstmt.setInt(2, productId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price_at_sale");
            }
        } catch (SQLException e) {
            System.out.println("Error getting price for return: " + e.getMessage());
        }
        return 0.0;
    }

    public boolean saveBill(ArrayList<Object[]> billItems) {
        try {
            for (Object[] item : billItems) {
                int saleId = (int) item[0];
                int productId = getProductId((String)item[1], (String)item[2], (double)item[3]);
                int quantity = (int)item[4];
                double price = (double)item[3];

                String onlineQuery = String.format(
                        "INSERT INTO SaleItems (sale_id, product_id, quantity, price_at_sale) VALUES (%d, %d, %d, %f)",
                        saleId, productId, quantity, price
                );
                onlineDB.executeQuery(onlineQuery);
            }
            return true;
        } catch (Exception e) {
            System.out.println("Error saving bill items: " + e.getMessage());
            return false;
        }
    }

    private int getProductId(String name, String category, double price) {
        String query = "SELECT product_id FROM Products WHERE name = ? AND category = ? AND sale_price = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, name);
            pstmt.setString(2, category);
            pstmt.setDouble(3, price);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("product_id");
            }
        } catch (SQLException e) {
            System.out.println("Error getting product id: " + e.getMessage());
        }
        return -1;
    }

}
