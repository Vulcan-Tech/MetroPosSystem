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

    public void DEOshowScreen(String screenName) {
        if (screenName.equals("addProductsScreen")) {
            refreshVendorTable();
        } else if(screenName.equals("modifyProductsScreen")){
            refreshProductTable(model);
        }
        DEOcardLayout.show(DEOPanel, screenName);
    }

public JPanel DEOgetPanel() {
        return DEOPanel;
    }

    public void displayMessage(String str) {
        JFrame frame = new JFrame("ERROR !");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        JLabel message = new JLabel(str, SwingConstants.CENTER);
        message.setForeground(Color.RED);
        frame.setSize(350, 200);
        frame.add(message);
        frame.setVisible(true);
    }

    public void addVendorScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("ADD VENDOR SCREEN",SwingConstants.CENTER);
        welcomelabel.setFont(new Font("Helvetica",Font.BOLD,38));
        welcomelabel.setForeground(color1);
        topPanel.add(welcomelabel,BorderLayout.CENTER);

        JButton backButton = new JButton();
        ImageIcon icon = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\return-50.png");
        backButton.setIcon(icon);
        backButton.setPreferredSize(new Dimension(55, 55));
        backButton.setFocusPainted(false);
        topPanel.add(backButton,BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DEOshowScreen("DEODashboardScreen");
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.4; // Takes 40% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(topPanel,gbc);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel,BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));
        bottomPanel.setBackground(color4);

        ImageIcon icon1 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\name-50.png");
        ImageIcon icon2 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\email-50.png");
        ImageIcon icon3 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\company-name-45.png");

        JLabel iconLabel1 = new JLabel(icon1);
        JLabel iconLabel2 = new JLabel(icon2);
        JLabel iconLabel3 = new JLabel(icon3);

        JTextField Name = new JTextField(30);
        Name.setBorder(BorderFactory.createTitledBorder("Name"));
        Name.setBackground(color3);
        Name.setFont(new Font("Helvetica",Font.PLAIN,18));
        Name.setPreferredSize(new Dimension(250, 60));

        JTextField Email = new JTextField(30);
        Email.setBorder(BorderFactory.createTitledBorder("Email"));
        Email.setBackground(color3);
        Email.setFont(new Font("Helvetica",Font.PLAIN,18));
        Email.setPreferredSize(new Dimension(250, 60));

        JTextField companyName = new JTextField(30);
        companyName.setBorder(BorderFactory.createTitledBorder("Company Name"));
        companyName.setBackground(color3);
        companyName.setFont(new Font("Helvetica",Font.PLAIN,18));
        companyName.setPreferredSize(new Dimension(250, 60));

        // Panel for city field and icon
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel1.add(iconLabel1);
        panel1.setBackground(color4);
        panel1.add(Name);
        panel1.setPreferredSize(new Dimension(icon1.getIconWidth() + Name.getPreferredSize().width, 60));
        panel1.setMaximumSize(new Dimension(icon1.getIconWidth() + Email.getPreferredSize().width, 60));
        panel1.setMinimumSize(new Dimension(icon1.getIconWidth() + Email.getPreferredSize().width, 60));
        panel1.revalidate();
        panel1.repaint();

        // Panel for address field and icon
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel2.add(iconLabel2);
        panel2.setBackground(color4);
        panel2.add(Email);
        panel2.setPreferredSize(new Dimension(icon2.getIconWidth() + Email.getPreferredSize().width, 60));
        panel2.setMaximumSize(new Dimension(icon2.getIconWidth() + Email.getPreferredSize().width, 60));
        panel2.setMinimumSize(new Dimension(icon2.getIconWidth() + Email.getPreferredSize().width, 60));
        panel2.revalidate();
        panel2.repaint();

        // Panel for phone number field and icon
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel3.add(iconLabel3);
        panel3.add(companyName);
        panel3.setBackground(color4);
        panel3.setPreferredSize(new Dimension(icon3.getIconWidth() + companyName.getPreferredSize().width, 60));
        panel3.setMaximumSize(new Dimension(icon3.getIconWidth() + Email.getPreferredSize().width, 60));
        panel3.setMinimumSize(new Dimension(icon3.getIconWidth() + Email.getPreferredSize().width, 60));
        panel3.revalidate();
        panel3.repaint();

        JButton addVendorButton = new JButton("Add Vendor");
        addVendorButton.setPreferredSize(new Dimension(300,60));
        addVendorButton.setMinimumSize(new Dimension(300,60));
        addVendorButton.setBackground(color2);
        addVendorButton.setForeground(color1);
        addVendorButton.setFont(new Font("Helvetica",Font.BOLD,20));
        addVendorButton.setMaximumSize(new Dimension(300, 60));
        bottomPanel.revalidate();
        bottomPanel.repaint();

        bottomPanel.add(Box.createVerticalStrut(40));
        bottomPanel.add(panel1);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel2);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel3);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(addVendorButton);

        panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        addVendorButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addVendorButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        addVendorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vendor = new Vendor(Name.getText(),Email.getText(),companyName.getText());
                if(linkerObj.addVendorToDB(vendor.getName(),vendor.getEmail(),vendor.getCompanyName())){
                    displayMessage("Vendor Added");
                }
                else {
                    displayMessage("Error While adding vendor");
                }
            }
        });

        DEOPanel.add(mainpanel, "addVendorScreen");
    }
}