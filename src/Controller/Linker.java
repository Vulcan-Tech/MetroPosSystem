package Controller;
import Model.*;
import View.GUI;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Linker {
    ImplementDB dbObj;
    ImplementOnlineDB onlineDB;
    Employee employee;

    private GUI gui;

    public Linker(ImplementDB dbObj,ImplementOnlineDB onlineDB){
        this.dbObj = dbObj;
        this.onlineDB = onlineDB;
    }

    public void setGUI(GUI gui) {
        this.gui = gui;
    }

    public void showEmployeeOptionsMenu() {
        gui.showScreen("EmployeeTypeScreen");
    }

    public boolean LoginValidator(String email, String password){
        return dbObj.validateLogin(email,password);
    }

    public List<Employee> employeelist(int id){
        return dbObj.fetchEmpList(id);
    }

    public boolean EmpLoginValidator(int id,String email, String password){
        return dbObj.validateEmployeeLogin(id,email,password);
    }

    public boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^\\+\\d{1,3}[\\d]{10}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(phoneNumber);
        return matcher.matches();
    }

    public int fetchBranchId(){
        int i = dbObj.fetchBranchId();
        return i;
    }

    public void addEmpData(String name, String email, double salary, String role, int branchCode){
        dbObj.addEmployeesData(name,email,salary,role,branchCode);
    }

    public boolean addbranchDatatoDB(String city,String address,String pnum){
        return  dbObj.addBranchDatatoDB(city,address,pnum);
    }

//    public boolean addEmpData(String name, String email, double salary){
//        return dbObj.addCashierData(name,email,salary);
//    }

    public boolean addBranchManagerDatatoDB(String city, String address, String pnum, String name, String email, double salary, String role) {
        int branchCode = dbObj.fetchBranchId();

        if (branchCode != -1) {  // Ensure the branchCode is valid
            dbObj.addEmployeesData(name, email, salary, role, branchCode);
            return true;
        } else {
            System.out.println("Failed to add branch, cannot add branch manager.");
            return  false;
        }
    }

    public boolean addVendorToDB(String name,String email,String companyName){
        return dbObj.addVendorData(name,email,companyName);
    }

    public Object[][] vendors2d(){
        return dbObj.getVendorsTableData();
    }

    public boolean addProductstoDB(String name,String category,double orgprice,double saleprice, int stockqty, int vendor_id){
        return dbObj.addProductData(name,category,orgprice,saleprice,stockqty,vendor_id);
    }

    public boolean deleteProductfromDB(int productID){
        return dbObj.deleteProduct(productID);
    }

    public Object[][] searchProductsfromDB(String str){
        return dbObj.searchProducts(str);
    }

    public Object[][] getAllProductsfromDB(){
        return dbObj.getAllProducts();
    }

    public Object[][] getProductsforBillfromDB(){
        return dbObj.getProductsforBill();
    }

    public Object[][] searchProductsforBillfromDB(String str){
        return dbObj.searchProductsforBill(str);
    }


    public int getStock(String name, String company, double price) {
        return dbObj.getProductStock(name, company, price);
    }

    public boolean updateStock(String name, String company, double price, int quantitySold) {
        return dbObj.updateStockAfterBill(name, company, price, quantitySold);
    }

    public Object[][] getStockData() {
        return dbObj.getStockData();
    }

    public Object[][] searchStock(String searchText) {
        return dbObj.searchStock(searchText);
    }


    public int createSaleRecord(int employeeId, int branchId, double totalAmount) {
        return dbObj.createSaleRecord(employeeId, branchId, totalAmount);
    }

    public int getEmployeeId(String email, int branchId) {
        return dbObj.getEmployeeId(email, branchId);
    }

    // added functions from here

    public boolean validateCurrentPassword(String currentPass) {
        return dbObj.validateSACurrentPassword(currentPass);
    }

    public boolean updateSAPassword(String newPass) {
        return dbObj.updateSAPassword(newPass);
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

    // to here

    public boolean processReturn(int saleId, int productId, int returnQuantity) {
        return dbObj.processReturn(saleId, productId, returnQuantity);
    }

    public Object[][] getSalesData() {
        return dbObj.getSalesData();
    }

    public Object[][] getSaleItems(int saleId) {
        return dbObj.getSaleItems(saleId);
    }

    public boolean saveBill(ArrayList<Object[]> billItems) {
        return dbObj.saveBill(billItems);
    }

    public double getPriceForReturn(int saleId, int productId) {
        return dbObj.getPriceForReturn(saleId, productId);
    }

    public double[] getDailySales(int branchId) {
        return dbObj.getDailySales(branchId);
    }

    public double[] getWeeklySales(int branchId) {
        return dbObj.getWeeklySales(branchId);
    }

    public double[] getMonthlySales(int branchId) {
        return dbObj.getMonthlySales(branchId);
    }

    public boolean checkEmailExists(String email) {
        return dbObj.doesEmailExist(email);
    }

    public boolean checkEmailExistsVendors(String email) {
        return dbObj.doesEmailExistVendors(email);
    }

    public boolean updateProductPricesinDB(int productId, double newOriginalPrice, double newSalePrice){
        return dbObj.updateProductPrices(productId,newOriginalPrice,newSalePrice);
    }

}


