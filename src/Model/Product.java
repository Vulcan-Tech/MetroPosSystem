package Model;

public class Product {
    //  productId, name, category, originalPrice, salePrice, stockQuantity, vendorId, createdAt
    private int product_id;
    private String name;
    private String category;
    private double originalPrice;
    private double salePrice;
    private int stockQty;
    private int vendor_id;

    public Product(String name,String category,double originalPrice,double salePrice,int stockQty,int vendor_id){
        this.name = name;
        this.category = category;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.stockQty = stockQty;
        this.vendor_id = vendor_id;
    }

    public Product(int product_id,String name,String category, double originalPrice, double salePrice, int stockQty, int vendor_id){
        this.product_id = product_id;
        this.name = name;
        this.category = category;
        this.originalPrice = originalPrice;
        this.salePrice = salePrice;
        this.stockQty = stockQty;
        this.vendor_id = vendor_id;
    }

    public int getProduct_id(){
        return product_id;
    }
    public String getName(){
        return name;
    }
    public String getCategory(){
        return category;
    }
    public double getOriginalPrice(){
        return originalPrice;
    }
    public double getSalePrice(){
        return salePrice;
    }
    public int getStockQuantity(){
        return stockQty;
    }

    public int getVendor_id(){
        return vendor_id;
    }


}
