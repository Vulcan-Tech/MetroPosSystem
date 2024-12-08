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
    private JTable table;
    private JTable vendorTable;

    private String Email;
    private String password;

    private int vendorID;

    public DataEntryOpGUI(Linker linkerObj){
        this.linkerObj = linkerObj;
        DEOcardLayout = new CardLayout();
        DEOPanel = new JPanel(DEOcardLayout);

        color = new Color(44,116,229);
        color1 = new Color(229,185,35);
        color2 = new Color(11,28,64);
        color3 = new Color(136,193,229);
        color4 = new Color(38,97,156);

        addDEOLoginScreen();
        addDEODAshboardScreen();
        addVendorScreen();
        addProducts();
        addModifyProducts();

    }

    public void setBranchID(int Id){
        this.branchId = Id;
    }
    public void setEmail(String Email){
        this.Email = Email;
    }
    public void setPassword(String password){
        this.password = password;
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

    private void refreshVendorTable() {
        vendorTableModel.setRowCount(0);
        Object[][] newData = linkerObj.vendors2d();
        for (Object[] row : newData) {
            vendorTableModel.addRow(row);
        }
    }

    public void addDEODAshboardScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("Data Entry Operator Dashboard",SwingConstants.CENTER);
        welcomelabel.setFont(new Font("Helvetica",Font.BOLD,38));
        welcomelabel.setForeground(color1);
        topPanel.add(welcomelabel,BorderLayout.CENTER);

        JButton backButton = new JButton();
        ImageIcon iconimg = new ImageIcon(getClass().getResource("/return-50.png"));
//        ImageIcon iconimg = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\return-50.png");
        backButton.setIcon(iconimg);
        backButton.setPreferredSize(new Dimension(55, 55));
        backButton.setFocusPainted(false);
        topPanel.add(backButton,BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linkerObj.showEmployeeOptionsMenu();
            }
        });

        JButton changePasswordButton = new JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource("/settings-50.png"));
//        ImageIcon icon = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\settings-50.png");
        changePasswordButton.setIcon(icon);
        changePasswordButton.setPreferredSize(new Dimension(55, 55));
        changePasswordButton.setFocusPainted(false);
        topPanel.add(changePasswordButton,BorderLayout.EAST);

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

                JPasswordField currentPassField = new JPasswordField();
                JPasswordField newPassField = new JPasswordField();
                JPasswordField confirmPassField = new JPasswordField();

                panel.add(new JLabel("Current Password:"));
                panel.add(currentPassField);
                panel.add(new JLabel("New Password:"));
                panel.add(newPassField);
                panel.add(new JLabel("Confirm Password:"));
                panel.add(confirmPassField);

                int result = JOptionPane.showConfirmDialog(null, panel,
                        "Change Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION) {
                    String currentPass = new String(currentPassField.getPassword());
                    String newPass = new String(newPassField.getPassword());
                    String confirmPass = new String(confirmPassField.getPassword());

                    if (currentPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "All fields are required!");
                        return;
                    }

                    if (!newPass.equals(confirmPass)) {
                        JOptionPane.showMessageDialog(null, "New passwords do not match!");
                        return;
                    }

                    if (linkerObj.validateCurrentPassword(getbranchID(), getEmail(), currentPass)) {
                        if (linkerObj.updatePassword(getbranchID(), getEmail(), newPass)) {
                            JOptionPane.showMessageDialog(null, "Password updated successfully!");
                            setPassword(newPass);
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to update password!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Current password is incorrect!");
                    }
                }
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
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 50));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));
        bottomPanel.setBackground(color4);

        JButton addVendorbutton = new JButton("Add Vendor");
        addVendorbutton.setBackground(color3);
        addVendorbutton.setBorder(BorderFactory.createLineBorder(color2,5));
        addVendorbutton.setFont(new Font("Helvetica",Font.BOLD,28));
        addVendorbutton.setForeground(color2);
        addVendorbutton.setFocusPainted(false);
        addVendorbutton.setPreferredSize(new Dimension(250,250));

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/cashier-64.png"));
//        ImageIcon icon1 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\cashier-64.png");
        addVendorbutton.setIcon(icon1);
        addVendorbutton.setHorizontalTextPosition(SwingConstants.CENTER);  // Center text horizontally
        addVendorbutton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton addProductsbutton = new JButton("Add Products");
        addProductsbutton.setBackground(color3);
        addProductsbutton.setBorder(BorderFactory.createLineBorder(color2,5));
        addProductsbutton.setFont(new Font("Helvetica",Font.BOLD,26));// reduced size a bit to fit thetext from 28 to 26
        addProductsbutton.setForeground(color2);
        addProductsbutton.setPreferredSize(new Dimension(250,250));
        addProductsbutton.setFocusPainted(false);

        ImageIcon icon2 = new ImageIcon(getClass().getResource("/data-entry-50.png"));
//        ImageIcon icon2 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\data-entry-50.png");
        addProductsbutton.setIcon(icon2);
        addProductsbutton.setHorizontalTextPosition(SwingConstants.CENTER);  // Center text horizontally
        addProductsbutton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton modifyProductsButton = new JButton("Modify Products");
        modifyProductsButton.setBackground(color3);
        modifyProductsButton.setBorder(BorderFactory.createLineBorder(color2,5));
        modifyProductsButton.setFont(new Font("Helvetica",Font.BOLD,28));
        modifyProductsButton.setForeground(color2);
        modifyProductsButton.setFocusPainted(false);
        modifyProductsButton.setPreferredSize(new Dimension(250,250));

        ImageIcon icon3 = new ImageIcon(getClass().getResource("/staff-50.png"));
//        ImageIcon icon3 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\staff-50.png");
        modifyProductsButton.setIcon(icon3);
        modifyProductsButton.setHorizontalTextPosition(SwingConstants.CENTER);
        modifyProductsButton.setVerticalTextPosition(SwingConstants.BOTTOM);// Center text horizontally

//        JButton modifyVendorButton = new JButton("Modify Vendor");
//        modifyVendorButton.setBackground(color3);
//        modifyVendorButton.setBorder(BorderFactory.createLineBorder(color2,5));
//        modifyVendorButton.setFont(new Font("Helvetica",Font.BOLD,28));
//        modifyVendorButton.setForeground(color2);
//        modifyVendorButton.setFocusPainted(false);
//        modifyVendorButton.setPreferredSize(new Dimension(250,250));
//
//        ImageIcon icon4 = new ImageIcon(getClass().getResource("/reports-50.png"));
////        ImageIcon icon4 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\reports-50.png");
//        modifyVendorButton.setIcon(icon4);
//        modifyVendorButton.setHorizontalTextPosition(SwingConstants.CENTER);
//        modifyVendorButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        bottomPanel.add(addVendorbutton);
        bottomPanel.add(addProductsbutton);
        bottomPanel.add(modifyProductsButton);
//        bottomPanel.add(modifyVendorButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        addVendorbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DEOshowScreen("addVendorScreen");
            }
        });

        addProductsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DEOshowScreen("addProductsScreen");
            }
        });

        modifyProductsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DEOshowScreen("modifyProductsScreen");
            }
        });

//        modifyVendorButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                DEOshowScreen("modifyVendorScreen");
//            }
//        });

        DEOPanel.add(mainpanel, "DEODashboardScreen");
    }

    public void addDEOLoginScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("Data Entry Operator Login",SwingConstants.CENTER);
        welcomelabel.setFont(new Font("Helvetica",Font.BOLD,38));
        welcomelabel.setForeground(color1);
        topPanel.add(welcomelabel,BorderLayout.CENTER);

        JButton backButton = new JButton();
        ImageIcon iconimg = new ImageIcon(getClass().getResource("/return-50.png"));
//        ImageIcon iconimg = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\return-50.png");
        backButton.setIcon(iconimg);
        backButton.setPreferredSize(new Dimension(55, 55));
        backButton.setFocusPainted(false);
        topPanel.add(backButton,BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linkerObj.showEmployeeOptionsMenu();
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

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/id-50.png"));
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/email-50.png"));
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/password-50.png"));

//        ImageIcon icon1 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\id-50.png");
//        ImageIcon icon2 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\email-50.png");
//        ImageIcon icon3 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\password-50.png");

        JLabel iconLabel1 = new JLabel(icon1);
        JLabel iconLabel2 = new JLabel(icon2);
        JLabel iconLabel3 = new JLabel(icon3);

        JTextField idField = new JTextField(30);
        idField.setBorder(BorderFactory.createTitledBorder("Branch ID"));
        idField.setBackground(color3);
        idField.setFont(new Font("Helvetica",Font.PLAIN,18));
        idField.setPreferredSize(new Dimension(250, 60));

        JTextField emailField = new JTextField(30);
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));
        emailField.setBackground(color3);
        emailField.setFont(new Font("Helvetica",Font.PLAIN,18));
        emailField.setPreferredSize(new Dimension(250, 60));

        JPasswordField passwordField = new JPasswordField(30);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        passwordField.setBackground(color3);
        passwordField.setFont(new Font("Helvetica",Font.PLAIN,18));
        passwordField.setPreferredSize(new Dimension(250, 60));

        // Panel for city field and icon
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel1.add(iconLabel1);
        panel1.setBackground(color4);
        panel1.add(idField);
        panel1.setPreferredSize(new Dimension(icon1.getIconWidth() + idField.getPreferredSize().width, 60));
        panel1.setMaximumSize(new Dimension(icon1.getIconWidth() + emailField.getPreferredSize().width, 60));
        panel1.setMinimumSize(new Dimension(icon1.getIconWidth() + emailField.getPreferredSize().width, 60));
        panel1.revalidate();
        panel1.repaint();

        // Panel for address field and icon
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel2.add(iconLabel2);
        panel2.setBackground(color4);
        panel2.add(emailField);
        panel2.setPreferredSize(new Dimension(icon2.getIconWidth() + emailField.getPreferredSize().width, 60));
        panel2.setMaximumSize(new Dimension(icon2.getIconWidth() + emailField.getPreferredSize().width, 60));
        panel2.setMinimumSize(new Dimension(icon2.getIconWidth() + emailField.getPreferredSize().width, 60));
        panel2.revalidate();
        panel2.repaint();

        // Panel for phone number field and icon
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel3.add(iconLabel3);
        panel3.add(passwordField);
        panel3.setBackground(color4);
        panel3.setPreferredSize(new Dimension(icon3.getIconWidth() + passwordField.getPreferredSize().width, 60));
        panel3.setMaximumSize(new Dimension(icon3.getIconWidth() + emailField.getPreferredSize().width, 60));
        panel3.setMinimumSize(new Dimension(icon3.getIconWidth() + emailField.getPreferredSize().width, 60));
        panel3.revalidate();
        panel3.repaint();

        JButton LoginButton = new JButton("Login");
        LoginButton.setPreferredSize(new Dimension(300,60));
        LoginButton.setMinimumSize(new Dimension(300,60));
        LoginButton.setBackground(color2);
        LoginButton.setForeground(color1);
        LoginButton.setFont(new Font("Helvetica",Font.BOLD,20));
        LoginButton.setMaximumSize(new Dimension(300, 60));
        bottomPanel.revalidate();
        bottomPanel.repaint();

        bottomPanel.add(Box.createVerticalStrut(80));
        bottomPanel.add(panel1);
        bottomPanel.add(Box.createVerticalStrut(30));
        bottomPanel.add(panel2);
        bottomPanel.add(Box.createVerticalStrut(30));
        bottomPanel.add(panel3);
        bottomPanel.add(Box.createVerticalStrut(50));
        bottomPanel.add(LoginButton);

        panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        LoginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        LoginButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String email = emailField.getText();
                String passcode = new String(passwordField.getPassword());

                if(linkerObj.EmpLoginValidator(id,email,passcode)) {
                    branchId = id;
                    if (!linkerObj.isPasswordChanged(email)) {
                        JOptionPane.showMessageDialog(null,
                                "You must change your default password before continuing.",
                                "Password Change Required",
                                JOptionPane.INFORMATION_MESSAGE);

                        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 5));

                        JPasswordField newPassField = new JPasswordField();
                        JPasswordField confirmPassField = new JPasswordField();

                        panel.add(new JLabel("New Password:"));
                        panel.add(newPassField);
                        panel.add(new JLabel("Confirm Password:"));
                        panel.add(confirmPassField);

                        int result = JOptionPane.showConfirmDialog(null, panel,
                                "Change Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if (result == JOptionPane.OK_OPTION) {
                            String newPass = new String(newPassField.getPassword());
                            String confirmPass = new String(confirmPassField.getPassword());

                            if (newPass.isEmpty() || confirmPass.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "All fields are required!");
                                return;
                            }

                            if (!newPass.equals(confirmPass)) {
                                JOptionPane.showMessageDialog(null, "New passwords do not match!");
                                return;
                            }

                            if (linkerObj.updatePasswordAndStatus(email, newPass)) {
                                JOptionPane.showMessageDialog(null, "Password updated successfully!");
                                DataEntryOpGUI.this.password = newPass;
                                Email = email;
                                DEOshowScreen("DEODashboardScreen");
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to update password!");
                            }
                        }
                    } else {
                        DataEntryOpGUI.this.password = passcode;
                        Email = email;
                        DEOshowScreen("DEODashboardScreen");
                    }
                } else {
                    String str = "Wrong Credentials";
                    JOptionPane.showMessageDialog(null, str);
                }
            }
        });

        DEOPanel.add(mainpanel, "DEOLoginScreen");
    }

    public void DEOshowScreen(String screenName) {
        if (screenName.equals("addProductsScreen")) {
            refreshVendorTable();
            // Clear any existing selection in the table
            if (table != null) {
                table.clearSelection();
            }
        } else if(screenName.equals("modifyProductsScreen")){
            refreshProductTable(model);
        }
        DEOcardLayout.show(DEOPanel, screenName);
    }

    public JPanel DEOgetPanel() {
        return DEOPanel;
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
        ImageIcon icon = new ImageIcon(getClass().getResource("/return-50.png"));
//        ImageIcon icon = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\return-50.png");
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

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/name-50.png"));
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/email-50.png"));
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/company-name-45.png"));

//        ImageIcon icon1 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\name-50.png");
//        ImageIcon icon2 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\email-50.png");
//        ImageIcon icon3 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\company-name-45.png");

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
                if(linkerObj.checkEmailExistsVendors(vendor.getEmail())){
                    JOptionPane.showMessageDialog(null,"Email Already Used!");
                }
                else if(linkerObj.addVendorToDB(vendor.getName(),vendor.getEmail(),vendor.getCompanyName())){
                    JOptionPane.showMessageDialog(null, "Vendor Added");
                }
                else {
                    JOptionPane.showMessageDialog(null, "Error while adding vendor");
                }
            }
        });

        DEOPanel.add(mainpanel, "addVendorScreen");
    }

    public void addProducts(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("Add Products Screen",SwingConstants.CENTER);
        welcomelabel.setFont(new Font("Helvetica",Font.BOLD,38));
        welcomelabel.setForeground(color1);
        topPanel.add(welcomelabel,BorderLayout.CENTER);

        JButton backButton = new JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource("/return-50.png"));
//        ImageIcon icon = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\return-50.png");
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
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));
        bottomPanel.setBackground(color4);

        String[] columnNames = {"ID","Name", "Email", "Company Name", "Add Product"};
        Object[][] data  = linkerObj.vendors2d();

//        for (Object[] row : data) {
//            for (Object element : row) {
//                System.out.print(element + " ");
//            }
//            System.out.println(); // Move to the next line after each row
//        }

        vendorTableModel = new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
// Use vendorTableModel instead of model
        table = new JTable(vendorTableModel);
        table.setRowHeight(35);

        // Create button column
        TableColumn buttonColumn = table.getColumnModel().getColumn(4);
        buttonColumn.setCellRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                JButton button = new JButton("Add Products");
                button.setBackground(color3);
                button.setForeground(color2);
                return button;
            }
        });



        buttonColumn.setCellEditor(new DefaultCellEditor(new JCheckBox()) {
            JButton button;
            {
                button = new JButton("Add Products");
                button.setBackground(color3);
                button.setForeground(color2);
                button.setFocusPainted(false);
                button.addActionListener(e -> {
                    int row = vendorTable.getSelectedRow();
                    if (row >= 0) {
                        int vendorId = (int) vendorTable.getValueAt(row, 0);
                        setvendorID(vendorId);
                        System.out.println("VendorID set to: " + getVendorID());
                        // Create new instance of product menu
                        addproductAddingMenu();
                        DEOshowScreen("ProductAddingMenu");
                    }
                    vendorTable.clearSelection();
                    fireEditingStopped();
                });
            }

            @Override
            public Component getTableCellEditorComponent(JTable table, Object value,
                                                         boolean isSelected, int row, int column) {
                return button;
            }
        });

        // Store reference to table
        vendorTable = table;

        // Add table to scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        bottomPanel.add(scrollPane, BorderLayout.CENTER);


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        DEOPanel.add(mainpanel, "addProductsScreen");
    }

    public void addproductAddingMenu(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("Product Adding Menu",SwingConstants.CENTER);
        welcomelabel.setFont(new Font("Helvetica",Font.BOLD,38));
        welcomelabel.setForeground(color1);
        topPanel.add(welcomelabel,BorderLayout.CENTER);

        JButton backButton = new JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource("/return-50.png"));
//        ImageIcon icon = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\return-50.png");
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

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/name-50.png"));
        ImageIcon icon2 = new ImageIcon(getClass().getResource("/email-50.png"));
        ImageIcon icon3 = new ImageIcon(getClass().getResource("/original-price-45.png"));
        ImageIcon icon4 = new ImageIcon(getClass().getResource("/sale-price-45.png"));
        ImageIcon icon5 = new ImageIcon(getClass().getResource("/stock-45.png"));
        ImageIcon icon6 = new ImageIcon(getClass().getResource("/id-50.png"));

//        ImageIcon icon1 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\name-50.png");
//        ImageIcon icon2 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\email-50.png");
//        ImageIcon icon3 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\original-price-45.png");
//        ImageIcon icon4 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\sale-price-45.png");
//        ImageIcon icon5 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\stock-45.png");
//        ImageIcon icon6 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\id-50.png");

        JLabel iconLabel1 = new JLabel(icon1);
        JLabel iconLabel2 = new JLabel(icon2);
        JLabel iconLabel3 = new JLabel(icon3);
        JLabel iconLabel4 = new JLabel(icon4);
        JLabel iconLabel5 = new JLabel(icon5);
        JLabel iconLabel6 = new JLabel(icon6);

        JTextField Name = new JTextField(30);
        Name.setBorder(BorderFactory.createTitledBorder("Name"));
        Name.setBackground(color3);
        Name.setFont(new Font("Helvetica",Font.PLAIN,18));
        Name.setPreferredSize(new Dimension(250, 60));

        JTextField Category = new JTextField(30);
        Category.setBorder(BorderFactory.createTitledBorder("Category"));
        Category.setBackground(color3);
        Category.setFont(new Font("Helvetica",Font.PLAIN,18));
        Category.setPreferredSize(new Dimension(250, 60));

        JTextField orgPrice = new JTextField(30);
        orgPrice.setBorder(BorderFactory.createTitledBorder("Original Price"));
        orgPrice.setBackground(color3);
        orgPrice.setFont(new Font("Helvetica",Font.PLAIN,18));
        orgPrice.setPreferredSize(new Dimension(250, 60));

        JTextField salePrice = new JTextField(30);
        salePrice.setBorder(BorderFactory.createTitledBorder("Sales Price"));
        salePrice.setBackground(color3);
        salePrice.setFont(new Font("Helvetica",Font.PLAIN,18));
        salePrice.setPreferredSize(new Dimension(250, 60));

        JTextField StockQty = new JTextField(30);
        StockQty.setBorder(BorderFactory.createTitledBorder("Stock Quantity"));
        StockQty.setBackground(color3);
        StockQty.setFont(new Font("Helvetica",Font.PLAIN,18));
        StockQty.setPreferredSize(new Dimension(250, 60));

        JTextField vendor_id = new JTextField(30);
        vendor_id.setBorder(BorderFactory.createTitledBorder("Vendor ID"));
        vendor_id.setBackground(color3);
        vendor_id.setFont(new Font("Helvetica",Font.PLAIN,18));
        vendor_id.setText(String.valueOf(vendorID));
        vendor_id.setEditable(false);
        vendor_id.setPreferredSize(new Dimension(250, 60));

        // Panel for city field and icon
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel1.add(iconLabel1);
        panel1.setBackground(color4);
        panel1.add(Name);
        panel1.setPreferredSize(new Dimension(icon1.getIconWidth() + Name.getPreferredSize().width, 60));
        panel1.setMaximumSize(new Dimension(icon1.getIconWidth() + Category.getPreferredSize().width, 60));
        panel1.setMinimumSize(new Dimension(icon1.getIconWidth() + Category.getPreferredSize().width, 60));
        panel1.revalidate();
        panel1.repaint();

        // Panel for address field and icon
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel2.add(iconLabel2);
        panel2.setBackground(color4);
        panel2.add(Category);
        panel2.setPreferredSize(new Dimension(icon2.getIconWidth() + Category.getPreferredSize().width, 60));
        panel2.setMaximumSize(new Dimension(icon2.getIconWidth() + Category.getPreferredSize().width, 60));
        panel2.setMinimumSize(new Dimension(icon2.getIconWidth() + Category.getPreferredSize().width, 60));
        panel2.revalidate();
        panel2.repaint();

        // Panel for phone number field and icon
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel3.add(iconLabel3);
        panel3.add(orgPrice);
        panel3.setBackground(color4);
        panel3.setPreferredSize(new Dimension(icon3.getIconWidth() + orgPrice.getPreferredSize().width, 60));
        panel3.setMaximumSize(new Dimension(icon3.getIconWidth() + Category.getPreferredSize().width, 60));
        panel3.setMinimumSize(new Dimension(icon3.getIconWidth() + Category.getPreferredSize().width, 60));
        panel3.revalidate();
        panel3.repaint();

        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel4.add(iconLabel4);
        panel4.add(salePrice);
        panel4.setBackground(color4);
        panel4.setPreferredSize(new Dimension(icon4.getIconWidth() + salePrice.getPreferredSize().width, 60));
        panel4.setMaximumSize(new Dimension(icon4.getIconWidth() + Category.getPreferredSize().width, 60));
        panel4.setMinimumSize(new Dimension(icon4.getIconWidth() + Category.getPreferredSize().width, 60));
        panel4.revalidate();
        panel4.repaint();

        JPanel panel5 = new JPanel();
        panel5.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel5.add(iconLabel5);
        panel5.setBackground(color4);
        panel5.add(StockQty);
        panel5.setPreferredSize(new Dimension(icon5.getIconWidth() + Name.getPreferredSize().width, 60));
        panel5.setMaximumSize(new Dimension(icon5.getIconWidth() + Category.getPreferredSize().width, 60));
        panel5.setMinimumSize(new Dimension(icon5.getIconWidth() + Category.getPreferredSize().width, 60));
        panel5.revalidate();
        panel5.repaint();

        JPanel panel6 = new JPanel();
        panel6.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel6.add(iconLabel6);
        panel6.setBackground(color4);
        panel6.add(vendor_id);
        panel6.setPreferredSize(new Dimension(icon6.getIconWidth() + Name.getPreferredSize().width, 60));
        panel6.setMaximumSize(new Dimension(icon6.getIconWidth() + Category.getPreferredSize().width, 60));
        panel6.setMinimumSize(new Dimension(icon6.getIconWidth() + Category.getPreferredSize().width, 60));
        panel6.revalidate();
        panel6.repaint();

        JButton addProductbutton = new JButton("Add Product");
        addProductbutton.setPreferredSize(new Dimension(300,60));
        addProductbutton.setMinimumSize(new Dimension(300,60));
        addProductbutton.setBackground(color2);
        addProductbutton.setForeground(color1);
        addProductbutton.setFont(new Font("Helvetica",Font.BOLD,20));
        addProductbutton.setMaximumSize(new Dimension(300, 60));
        bottomPanel.revalidate();
        bottomPanel.repaint();

        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel1);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel2);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel3);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel4);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel5);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel6);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(addProductbutton);

        panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel4.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel5.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel6.setAlignmentX(Component.CENTER_ALIGNMENT);
        addProductbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addProductbutton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        addProductbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Get values inside the actionPerformed method
                    String name = Name.getText().trim();
                    String category = Category.getText().trim();
                    double originalPrice = Double.parseDouble(orgPrice.getText().trim());
                    double salesPrice = Double.parseDouble(salePrice.getText().trim());
                    int stockquantity = Integer.parseInt(StockQty.getText().trim());
                    // Validate the input
                    if (name.isEmpty() || category.isEmpty()) {
                        JOptionPane.showMessageDialog(null, "Name and Category cannot be empty");
                        return;
                    }
                    if (originalPrice <= 0 || salesPrice <= 0 || stockquantity <= 0) {
                        JOptionPane.showMessageDialog(null, "Prices cannot be zero or negative");
                        return;
                    }
                    // Create product and add to database
                    product = new Product(name, category, originalPrice, salesPrice, stockquantity, getVendorID());
                    if (linkerObj.addProductstoDB(name, category, originalPrice, salesPrice, stockquantity, getVendorID())) {
                        JOptionPane.showMessageDialog(null, "Product Added");
                        // Clear fields after successful addition
                        Name.setText("");
                        Category.setText("");
                        orgPrice.setText("");
                        salePrice.setText("");
                        StockQty.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error while adding product");
                    }

                } catch (NumberFormatException ex) {
                    String str = "Please enter valid numbers for prices and quantity";
                    JOptionPane.showMessageDialog(null, str);
                }
            }
        });

        DEOPanel.add(mainpanel, "ProductAddingMenu");
    }

    public void addModifyProducts() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(Color.WHITE);

        // Top Panel with Search and Back Button
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(color2);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton backButton = new JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource("/return-50.png"));
        backButton.setIcon(icon);
        backButton.setPreferredSize(new Dimension(55, 55));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> DEOshowScreen("DEODashboardScreen"));

        JLabel titleLabel = new JLabel("Modify Products", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Helvetica", Font.BOLD, 38));
        titleLabel.setForeground(color1);

        topPanel.add(backButton, BorderLayout.WEST);
        topPanel.add(titleLabel, BorderLayout.CENTER);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(color4);
        JTextField searchField = new JTextField(30);
        searchField.setPreferredSize(new Dimension(300, 35));
        searchPanel.add(new JLabel("Search: "));
        searchPanel.add(searchField);

        // Table Panel
        String[] columns = {"ID", "Name", "Category", "Original Price", "Sale Price", "Stock", "Edit", "Delete"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 6 || column == 7; // Only Edit and Delete columns are editable
            }
        };

        JTable table = new JTable(model);
        table.setRowHeight(35);
        table.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer("Edit"));
        table.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor("Edit"));
        table.getColumnModel().getColumn(7).setCellRenderer(new ButtonRenderer("Delete"));
        table.getColumnModel().getColumn(7).setCellEditor(new ButtonEditor("Delete"));

        table.getColumnModel().getColumn(0).setMinWidth(50);  // Minimum width of 50 pixels
        table.getColumnModel().getColumn(0).setMaxWidth(100); // Maximum width of 100 pixels
        table.getColumnModel().getColumn(0).setPreferredWidth(75); // Preferred width of 75 pixels

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add search functionality
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { searchProducts(searchField.getText(), model); }
            public void removeUpdate(DocumentEvent e) { searchProducts(searchField.getText(), model); }
            public void insertUpdate(DocumentEvent e) { searchProducts(searchField.getText(), model); }
        });

        // Layout assembly
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(searchPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Initial load of products
        refreshProductTable(model);

        DEOPanel.add(mainPanel, "modifyProductsScreen");
    }

    public void addModifyVendors(){

    } // This function needs to be implemented not doing now to fullfil basic functionality

    private void searchProducts(String searchText, DefaultTableModel model) {
        model.setRowCount(0);
        Object[][] products = linkerObj.searchProductsfromDB(searchText);
        for (Object[] product : products) {
            model.addRow(product);
        }
    }

    private void refreshProductTable(DefaultTableModel model) {
        model.setRowCount(0);
        Object[][] products = linkerObj.getAllProductsfromDB();
        for (Object[] product : products) {
            model.addRow(product);
        }
    }

    private void showEditProductScreen(int productId) {
        // Create and show edit product panel similar to addproductAddingMenu
        // but populate fields with existing product data
        // You can reuse most of the addproductAddingMenu code here
        // Just modify it to update instead of insert
    }

    private class ButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private int currentRow;
        private JTable table;

        public ButtonEditor(String text) {
            super(new JCheckBox());
            button = new JButton(text);
            button.setFocusPainted(false);
            button.setBackground(color3);
            button.setForeground(color2);

            // Store button label
            this.label = text;

            button.addActionListener(e -> {
                int row = table.getSelectedRow();
                if (label.equals("Delete")) {
                    int productId = (int) table.getValueAt(row, 0);
                    String productName = (String) table.getValueAt(row, 1);

                    int choice = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure you want to delete product: " + productName + "?",
                            "Confirm Delete",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.WARNING_MESSAGE
                    );

                    if (choice == JOptionPane.YES_OPTION) {
                        if (linkerObj.deleteProductfromDB(productId)) {
                            refreshProductTable((DefaultTableModel) table.getModel());
                            JOptionPane.showMessageDialog(null, "Product deleted successfully!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error deleting product", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                } else if (label.equals("Edit")) {
                    int productId = (int) table.getValueAt(row, 0);
                    String productName = (String) table.getValueAt(row, 1);

                    // Create panel with two input fields
                    JPanel panel = new JPanel(new GridLayout(2, 2, 5, 5));
                    JTextField originalPriceField = new JTextField(10);
                    JTextField salePriceField = new JTextField(10);

                    panel.add(new JLabel("New Original Price:"));
                    panel.add(originalPriceField);
                    panel.add(new JLabel("New Sale Price:"));
                    panel.add(salePriceField);

                    int result = JOptionPane.showConfirmDialog(null, panel,
                            "Update Prices for " + productName,
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE);

                    if (result == JOptionPane.OK_OPTION) {
                        try {
                            double newOriginalPrice = Double.parseDouble(originalPriceField.getText());
                            double newSalePrice = Double.parseDouble(salePriceField.getText());

                            // Validate inputs
                            if (newOriginalPrice <= 0 || newSalePrice <= 0) {
                                JOptionPane.showMessageDialog(null,
                                        "Prices must be greater than 0",
                                        "Invalid Input",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            if (newSalePrice <= newOriginalPrice) {
                                JOptionPane.showMessageDialog(null,
                                        "Sale price must be greater than original price",
                                        "Invalid Input",
                                        JOptionPane.ERROR_MESSAGE);
                                return;
                            }

                            // Confirm changes
                            int confirm = JOptionPane.showConfirmDialog(null,
                                    "Are you sure you want to update the prices?\n" +
                                            "Original Price: " + newOriginalPrice + "\n" +
                                            "Sale Price: " + newSalePrice,
                                    "Confirm Update",
                                    JOptionPane.YES_NO_OPTION);

                            if (confirm == JOptionPane.YES_OPTION) {
                                if (linkerObj.updateProductPricesinDB(productId, newOriginalPrice, newSalePrice)) {
                                    JOptionPane.showMessageDialog(null,
                                            "Prices updated successfully!",
                                            "Success",
                                            JOptionPane.INFORMATION_MESSAGE);
                                    refreshProductTable((DefaultTableModel) table.getModel());
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Failed to update prices",
                                            "Error",
                                            JOptionPane.ERROR_MESSAGE);
                                }
                            }

                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null,
                                    "Please enter valid numbers",
                                    "Invalid Input",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    fireEditingStopped();
                }
            });
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.table = table;
            this.currentRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    private class ProductButtonEditor extends DefaultCellEditor {
        private JButton button;
        private String label;
        private boolean isPushed;
        private int currentRow;
        private JTable table;

        public ProductButtonEditor(String text) {
            super(new JCheckBox());
            button = new JButton(text);
            button.setFocusPainted(false);
            button.setBackground(color3);
            button.setForeground(color2);

            button.addActionListener(e -> {
                fireEditingStopped();
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            this.table = table;  // Store the table reference
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            currentRow = row;
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                if (label.equals("Delete")) {
                    int productId = (int) table.getValueAt(currentRow, 0);
                    if (linkerObj.deleteProductfromDB(productId)) {
                        refreshProductTable((DefaultTableModel) table.getModel());
                    }
                } else if (label.equals("Edit")) {
                    int productId = (int) table.getValueAt(currentRow, 0);
                    showEditProductScreen(productId);
                }
            }
            isPushed = false;
            return label;
        }
    }

    private class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer(String text) {
            setText(text);
            setFocusPainted(false);
            setBackground(color3);
            setForeground(color2);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

}






