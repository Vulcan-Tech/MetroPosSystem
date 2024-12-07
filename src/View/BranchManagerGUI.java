package View;

import Controller.Linker;
import Model.Employee;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BranchManagerGUI {
    private Linker linkerObj;
    private CardLayout BMcardLayout;
    private JPanel BranchManagerPanel;
    private Color color; // Blue colour the one in metro logo
    private Color color1; // Yellow colour the one in metro logo
    private Color color2; // Dark Blue colour the
    private Color color3; // Light Blue colour to be used in buttons;
    private Color color4;

    private Employee employee;
    private int branchId;

    private String Email;
    private String password;

    public BranchManagerGUI(Linker linkerObj) {
        this.linkerObj = linkerObj;
        BMcardLayout = new CardLayout();
        BranchManagerPanel = new JPanel(BMcardLayout);

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
        this.Email = Email;
    }
}