package View;

import Controller.Linker;
import Model.Employee;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CashierGUI {
    private Linker linkerObj;
    private CardLayout CashiercardLayout;
    private JPanel CashierPanel;
    private Color color; // Blue colour the one in metro logo
    private Color color1; // Yellow colour the one in metro logo
    private Color color2; // Dark Blue colour the
    private Color color3; // Light Blue colour to be used in buttons;
    private Color color4;

    private Employee employee;
    private int branchId;
    private int employeeId;

    private String Email;
    private String password;

    private JLabel totalLabel;
    private JTable salesTable;
    private DefaultTableModel salesTableModel;
    private DefaultTableModel stockModel;
    private JTable stockTable;

    public CashierGUI(Linker linkerObj) {
        this.linkerObj = linkerObj;
        CashiercardLayout = new CardLayout();
        CashierPanel = new JPanel(CashiercardLayout);

        totalLabel = new JLabel("Total: Rs. ");  // Use the class field
        totalLabel.setFont(new Font("Helvetica", Font.BOLD, 20));

        color = new Color(44, 116, 229);
        color1 = new Color(229, 185, 35);
        color2 = new Color(11, 28, 64);
        color3 = new Color(136, 193, 229);
        color4 = new Color(38, 97, 156);



    }


}