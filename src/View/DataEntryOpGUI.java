package View;

import Controller.Linker;
import Model.Product;
import Model.Vendor;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DataEntryOpGUI {
    private Linker linkerObj;
    private CardLayout DEOcardLayout;
    private JPanel DEOPanel;
    private Color color; // Blue colour the one in metro logo
    private Color color1; // Yellow colour the one in metro logo
    private Color color2; // Dark Blue colour the
    private Color color3; // Light Blue colour to be used in buttons;
    private Color color4;

    private Vendor vendor;
    private Product product;
    private DefaultTableModel vendorTableModel;
    DefaultTableModel model;
    private int branchId;

    private String Email;
    private String password;

    private int vendorID;

    public DataEntryOpGUI(Linker linkerObj) {
        this.linkerObj = linkerObj;
        DEOcardLayout = new CardLayout();
        DEOPanel = new JPanel(DEOcardLayout);

        color = new Color(44, 116, 229);
        color1 = new Color(229, 185, 35);
        color2 = new Color(11, 28, 64);
        color3 = new Color(136, 193, 229);
        color4 = new Color(38, 97, 156);



    }
public void setBranchID(int Id){
        this.branchId = Id;
    }
    public void setEmail(String Email){
        this.Email = Email;
    }
 

 public int getbranchID(){
        return branchId;
    }
    public String getEmail(){
        return Email;
    }
    public String getPassword(){
        return password;
    }
    public void  setvendorID(int vendorID){
        this.vendorID = vendorID;
    }
    public int getVendorID(){
        return vendorID;
    }
}