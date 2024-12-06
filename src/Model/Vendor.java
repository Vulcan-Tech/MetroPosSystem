package Model;

public class Vendor {
    private int vendor_id;
    private String name;
    private String email;
    private String companyName;

    public Vendor(String name,String email,String companyName){
        this.name = name;
        this.email = email;
        this.companyName = companyName;
    }

    public Vendor(int vendor_id,String name,String email, String companyName){
        this.vendor_id = vendor_id;
        this.name = name;
        this.email = email;
        this.companyName = companyName;
    }
    public int getVendor_id(){
        return vendor_id;
    }

    public String getName(){
        return name;
    }
    public String getCompanyName(){
        return companyName;
    }
    public String getEmail() {
        return email;
    }
}
