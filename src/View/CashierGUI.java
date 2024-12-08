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
import java.io.IOException;
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

    public CashierGUI(Linker linkerObj){
        this.linkerObj = linkerObj;
        CashiercardLayout = new CardLayout();
        CashierPanel = new JPanel(CashiercardLayout);

        totalLabel = new JLabel("Total: Rs. ");  // Use the class field
        totalLabel.setFont(new Font("Helvetica", Font.BOLD, 20));

        color = new Color(44,116,229);
        color1 = new Color(229,185,35);
        color2 = new Color(11,28,64);
        color3 = new Color(136,193,229);
        color4 = new Color(38,97,156);

        addCashierLoginScreen();
        addCashierDashboardScreen();
        addMakeBillScreen();
        addViewStockScreen();
        addReturnScreen();

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

    public void setEmployeeId(int id) {
        this.employeeId = id;
    }

    public void addCashierLoginScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("Cashier Login",SwingConstants.CENTER);
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

        // Add this as class field

// In your login button action listener, modify it to:
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(idField.getText());
                String email = emailField.getText();
                String passcode = new String(passwordField.getPassword());

                if(linkerObj.EmpLoginValidator(id,email,passcode)) {
                    setBranchID(id);
                    setEmployeeId(linkerObj.getEmployeeId(email, id));
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
                                setPassword(newPass);
                                setEmail(email);
//                                CashierGUI.this.password = newPass;
//                                Email = email;
                                CashiershowScreen("CashierDashboardScreen");
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to update password!");
                            }
                        }
                    } else {
                        setPassword(passcode);
                        setEmail(email);
//                        CashierGUI.this.password = passcode;
//                        Email = email;
                        CashiershowScreen("CashierDashboardScreen");
                    }
                } else {
                    String str = "Wrong Credentials";
                    JOptionPane.showMessageDialog(null, str);
                }
            }
        });


        CashierPanel.add(mainpanel, "CashierLoginScreen");
    }

    public void addCashierDashboardScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("Welcome to Cashier Dashboard",SwingConstants.CENTER);
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

        JButton makeBillButton = new JButton("Make Bill");
        makeBillButton.setBackground(color3);
        makeBillButton.setBorder(BorderFactory.createLineBorder(color2,5));
        makeBillButton.setFont(new Font("Helvetica",Font.BOLD,28));
        makeBillButton.setForeground(color2);
        makeBillButton.setFocusPainted(false);
        makeBillButton.setPreferredSize(new Dimension(250,250));

        ImageIcon icon1 = new ImageIcon(getClass().getResource("/cashier-64.png"));
//        ImageIcon icon1 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\cashier-64.png");
        makeBillButton.setIcon(icon1);
        makeBillButton.setHorizontalTextPosition(SwingConstants.CENTER);  // Center text horizontally
        makeBillButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton viewBillButton = new JButton("View Bill");
        viewBillButton.setBackground(color3);
        viewBillButton.setBorder(BorderFactory.createLineBorder(color2,5));
        viewBillButton.setFont(new Font("Helvetica",Font.BOLD,26));// reduced size a bit to fit thetext from 28 to 26
        viewBillButton.setForeground(color2);
        viewBillButton.setPreferredSize(new Dimension(250,250));
        viewBillButton.setFocusPainted(false);

        ImageIcon icon2 = new ImageIcon(getClass().getResource("/data-entry-50.png"));
//        ImageIcon icon2 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\data-entry-50.png");
        viewBillButton.setIcon(icon2);
        viewBillButton.setHorizontalTextPosition(SwingConstants.CENTER);  // Center text horizontally
        viewBillButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton viewStockButton = new JButton("View Stock");
        viewStockButton.setBackground(color3);
        viewStockButton.setBorder(BorderFactory.createLineBorder(color2,5));
        viewStockButton.setFont(new Font("Helvetica",Font.BOLD,28));
        viewStockButton.setForeground(color2);
        viewStockButton.setFocusPainted(false);
        viewStockButton.setPreferredSize(new Dimension(250,250));

        ImageIcon icon3 = new ImageIcon(getClass().getResource("/staff-50.png"));
//        ImageIcon icon3 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\staff-50.png");
        viewStockButton.setIcon(icon3);
        viewStockButton.setHorizontalTextPosition(SwingConstants.CENTER);
        viewStockButton.setVerticalTextPosition(SwingConstants.BOTTOM);// Center text horizontally

        JButton returnItemScreenButton = new JButton("Return Bill");
        returnItemScreenButton.setBackground(color3);
        returnItemScreenButton.setBorder(BorderFactory.createLineBorder(color2,5));
        returnItemScreenButton.setFont(new Font("Helvetica",Font.BOLD,28));
        returnItemScreenButton.setForeground(color2);
        returnItemScreenButton.setFocusPainted(false);
        returnItemScreenButton.setPreferredSize(new Dimension(250,250));

        ImageIcon icon4 = new ImageIcon(getClass().getResource("/reports-50.png"));
//        ImageIcon icon4 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\reports-50.png");
        returnItemScreenButton.setIcon(icon4);
        returnItemScreenButton.setHorizontalTextPosition(SwingConstants.CENTER);
        returnItemScreenButton.setVerticalTextPosition(SwingConstants.BOTTOM);

        bottomPanel.add(makeBillButton);
        bottomPanel.add(viewBillButton);
        bottomPanel.add(viewStockButton);
        bottomPanel.add(returnItemScreenButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        makeBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CashiershowScreen("MakeBillScreen");
            }
        });

        viewBillButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CashiershowScreen("ViewBillScreen");
            }
        });

        viewStockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CashiershowScreen("ViewStockScreen");
            }
        });

        returnItemScreenButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CashiershowScreen("ReturnScreen");
            }
        });

        CashierPanel.add(mainpanel, "CashierDashboardScreen");
    }

    public void addMakeBillScreen() {
        // Main Panel Setup
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        // Top Panel Setup
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        topPanel.setBackground(color2);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Back Button
        JButton backButton = new JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource("/return-50.png"));
        backButton.setIcon(icon);
        backButton.setPreferredSize(new Dimension(55, 55));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> CashiershowScreen("CashierDashboardScreen"));

        // Search Field
        JTextField searchField = new JTextField(30);
        searchField.setPreferredSize(new Dimension(300, 35));
        searchField.setBorder(BorderFactory.createTitledBorder("Search Products"));

        topPanel.add(backButton);
        topPanel.add(searchField);

        // Add Top Panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.15;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(topPanel, gbc);

        // Bottom Panel Setup
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bottomGbc = new GridBagConstraints();

        // Left Panel - Products Table
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(color3);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Products Table Setup
        // Modify the Products Table Setup section
        String[] productColumns = {"Name", "Category", "Price", "Action"};  // Removed Product ID
        DefaultTableModel productModel = new DefaultTableModel(productColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only Action column is editable
            }
        };

        JTable productTable = new JTable(productModel);
        JScrollPane productScrollPane = new JScrollPane(productTable);
        // Load Products
        Object[][] products = linkerObj.getProductsforBillfromDB();
        if (products != null) {
            for (Object[] product : products) {
                productModel.addRow(new Object[]{
                        product[0], // Name
                        product[1], // Category
                        product[2], // Price
                        "Add"      // Action button
                });
            }
        }

        // Setup Add Button Column
        productTable.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer("Add"));
        productTable.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox(), "Add"));
        leftPanel.add(productScrollPane, BorderLayout.CENTER);

        // Right Panel - Bill Table
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(color4);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Bill Table Setup
        String[] billColumns = {"Product", "Category", "Price", "Quantity", "Total", "Action"};
        DefaultTableModel billModel = new DefaultTableModel(billColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 5; // Action column
            }
        };
        JTable billTable = new JTable(billModel);
        JScrollPane billScrollPane = new JScrollPane(billTable);

        // Setup Remove Button Column
        billTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("Remove"));
        billTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "Remove"));

        // Total Panel Setup
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        totalPanel.setBackground(color4);
//        JLabel totalLabel = new JLabel("Total: Rs. ");
//        totalLabel.setFont(new Font("Helvetica", Font.BOLD, 20));
        totalLabel.setForeground(Color.WHITE);

        JButton makeBillButton = new JButton("Make Bill");
        makeBillButton.setBackground(color2);
        makeBillButton.setForeground(color1);
        makeBillButton.setFont(new Font("Helvetica", Font.BOLD, 16));

        totalPanel.add(totalLabel);
        totalPanel.add(Box.createHorizontalStrut(20));
        totalPanel.add(makeBillButton);

        rightPanel.add(billScrollPane, BorderLayout.CENTER);
        rightPanel.add(totalPanel, BorderLayout.SOUTH);

        // Add Panels to Bottom Panel
        bottomGbc.gridx = 0;
        bottomGbc.gridy = 0;
        bottomGbc.weightx = 0.6;
        bottomGbc.weighty = 1.0;
        bottomGbc.fill = GridBagConstraints.BOTH;
        bottomGbc.insets = new Insets(5, 5, 5, 5);
        bottomPanel.add(leftPanel, bottomGbc);

        bottomGbc.gridx = 1;
        bottomGbc.weightx = 0.4;
        bottomPanel.add(rightPanel, bottomGbc);

        // Add Bottom Panel to Main Panel
        gbc.gridy = 1;
        gbc.weighty = 0.85;
        mainPanel.add(bottomPanel, gbc);

        // Search Listener
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { searchProducts(searchField, productTable); }
            @Override
            public void removeUpdate(DocumentEvent e) { searchProducts(searchField, productTable); }
            @Override
            public void changedUpdate(DocumentEvent e) { searchProducts(searchField, productTable); }
        });

        // Product Table Click Listener (Add Item)
        // Modify the Product Table Click Listener
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = productTable.getColumnModel().getColumnIndexAtX(evt.getX());
                int row = evt.getY() / productTable.getRowHeight();

                if (row < productTable.getRowCount() && row >= 0 && column == 3) {
                    if (evt.getClickCount() == 1) { // Only respond to single clicks
                        String quantity = JOptionPane.showInputDialog(null, "Enter quantity:", "Quantity Input", JOptionPane.PLAIN_MESSAGE);
                        if (quantity != null && !quantity.trim().isEmpty()) {
                            try {
                                int qty = Integer.parseInt(quantity.trim());
                                if (qty > 0) {
                                    addItemToBill(productTable, billModel, row, qty);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Quantity must be greater than 0");
                                }
                            } catch (NumberFormatException e) {
                                JOptionPane.showMessageDialog(null, "Please enter a valid number", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });

        // Bill Table Click Listener (Remove Item)
        billTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = billTable.getColumnModel().getColumnIndexAtX(evt.getX());
                int row = evt.getY() / billTable.getRowHeight();

                if (row < billTable.getRowCount() && row >= 0 && column == 5) {
                    billModel.removeRow(row);
                    updateTotalAmount(totalLabel, billModel);
                }
            }
        });

        // Make Bill Button Listener
        makeBillButton.addActionListener(e -> {
            if (billModel.getRowCount() > 0) {
                int response = JOptionPane.showConfirmDialog(null, "Confirm bill generation?", "Confirm", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    if (generateBill(billModel)) {
                        JOptionPane.showMessageDialog(null, "Bill generated successfully!");
                        billModel.setRowCount(0);
                        totalLabel.setText("Total: Rs. 0.0");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error generating bill!");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "No items in bill!");
            }
        });

        CashierPanel.add(mainPanel, "MakeBillScreen");
    }

    public void addViewStockScreen() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();

        // Top Panel with Back Button and Search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
        topPanel.setBackground(color2);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton backButton = new JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource("/return-50.png"));
        backButton.setIcon(icon);
        backButton.setPreferredSize(new Dimension(55, 55));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> CashiershowScreen("CashierDashboardScreen"));

        JTextField searchField = new JTextField(30);
        searchField.setPreferredSize(new Dimension(300, 35));
        searchField.setBorder(BorderFactory.createTitledBorder("Search Products"));

        topPanel.add(backButton);
        topPanel.add(searchField);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(topPanel, gbc);

        // Table Panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(color3);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] columns = {"Product Name", "Category", "Stock Quantity"};
        stockModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        stockTable = new JTable(stockModel);
        stockTable.setRowHeight(25);
        stockTable.getTableHeader().setFont(new Font("Helvetica", Font.BOLD, 14));
        stockTable.setFont(new Font("Helvetica", Font.PLAIN, 14));

        // Custom renderer for coloring low stock items
        stockTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                int stockQty = Integer.parseInt(table.getModel().getValueAt(row, 2).toString());

                if (stockQty < 20) {
                    c.setBackground(new Color(255, 200, 200)); // Light red for low stock
                    c.setForeground(Color.RED);
                } else {
                    c.setBackground(table.getBackground());
                    c.setForeground(table.getForeground());
                }

                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(stockTable);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridy = 1;
        gbc.weighty = 0.9;
        mainPanel.add(tablePanel, gbc);

        // Load initial stock data
        loadStockData(stockModel);

        // Add search functionality
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { searchStock(searchField.getText(), stockModel); }
            @Override
            public void removeUpdate(DocumentEvent e) { searchStock(searchField.getText(), stockModel); }
            @Override
            public void changedUpdate(DocumentEvent e) { searchStock(searchField.getText(), stockModel); }
        });

        CashierPanel.add(mainPanel, "ViewStockScreen");
    }

    private void loadStockData(DefaultTableModel model) {
        model.setRowCount(0);
        Object[][] stockData = linkerObj.getStockData();
        if (stockData != null) {
            for (Object[] row : stockData) {
                model.addRow(row);
            }
        }
    }

    private void searchStock(String searchText, DefaultTableModel model) {
        model.setRowCount(0);
        Object[][] searchResults = linkerObj.searchStock(searchText);
        if (searchResults != null) {
            for (Object[] row : searchResults) {
                model.addRow(row);
            }
        }
    }

    class ButtonRenderer extends JButton implements TableCellRenderer {
        private String buttonText;

        public ButtonRenderer(String buttonText) {
            setOpaque(true);
            this.buttonText = buttonText;
            setBackground(color3);
            setForeground(color2);
            setFocusPainted(false);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText(buttonText);
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String buttonText;
        private boolean isPushed;

        public ButtonEditor(JCheckBox checkBox, String buttonText) {
            super(checkBox);
            this.buttonText = buttonText;
            button = new JButton();
            button.setOpaque(true);
            button.setBackground(color3);
            button.setForeground(color2);
            button.setFocusPainted(false);
            button.addActionListener(e -> fireEditingStopped());
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column) {
            button.setText(buttonText);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return buttonText;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    // Search products functionality
    private void searchProducts(JTextField searchField, JTable productTable) {
        String searchText = searchField.getText().toLowerCase();
        DefaultTableModel model = (DefaultTableModel) productTable.getModel();
        model.setRowCount(0); // Clear current table

        // Get searched products from database through linker
        Object[][] products = linkerObj.searchProductsforBillfromDB(searchText);

        if (products != null) {
            for (Object[] product : products) {
                model.addRow(product);
            }
        }
    }

    private void updateTotalAmount(JLabel totalLabel, DefaultTableModel billModel) {
        double total = 0;
        for (int i = 0; i < billModel.getRowCount(); i++) {
            total += Double.parseDouble(billModel.getValueAt(i, 4).toString()); // Get total from column 4
        }
        totalLabel.setText(String.format("Total Amount: Rs. %.2f", total));
    }

    // Calculate total bill amount
    private double calculateTotal(DefaultTableModel billModel) {
        double grandTotal = 0;
        for (int i = 0; i < billModel.getRowCount(); i++) {
            grandTotal += (double) billModel.getValueAt(i, 5);
        }
        return grandTotal;
    }
    // Add item to bill
    private void addItemToBill(JTable productTable, DefaultTableModel billModel, int row, int qty) {
        String name = productTable.getValueAt(row, 0).toString();
        String category = productTable.getValueAt(row, 1).toString();
        double price = Double.parseDouble(productTable.getValueAt(row, 2).toString());
        double total = price * qty;

        // Check stock before adding
        int currentStock = linkerObj.getStock(name, category, price);
        if (currentStock >= qty) {
            Object[] rowData = {
                    name,           // Product name
                    category,       // Category
                    price,         // Price
                    qty,           // Quantity
                    total,         // Total
                    "Remove"       // Action
            };
            billModel.addRow(rowData);
            updateTotalAmount(totalLabel, billModel);
        } else {
            JOptionPane.showMessageDialog(null,
                    "Insufficient stock! Available: " + currentStock,
                    "Stock Error",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    // Generate and save bill
    private boolean generateBill(DefaultTableModel billModel) {
        if (billModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "Bill is empty!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // Calculate total amount
        double totalAmount = 0;
        for (int i = 0; i < billModel.getRowCount(); i++) {
            totalAmount += Double.parseDouble(billModel.getValueAt(i, 4).toString());
        }

        // Create sales record with the correct employee_id
        int saleId = linkerObj.createSaleRecord(this.employeeId, branchId, totalAmount);

        if (saleId > 0) {
            ArrayList<Object[]> billItems = new ArrayList<>();
            for (int i = 0; i < billModel.getRowCount(); i++) {
                Object[] row = new Object[]{
                        saleId,                             // sale_id
                        billModel.getValueAt(i, 0),        // name
                        billModel.getValueAt(i, 1),        // category
                        billModel.getValueAt(i, 2),        // price
                        billModel.getValueAt(i, 3),        // quantity
                        billModel.getValueAt(i, 4)         // total
                };
                billItems.add(row);
            }

            if (linkerObj.saveBill(billItems)) {
                // Update stock quantities
                for (Object[] item : billItems) {
                    String name = item[1].toString();
                    String category = item[2].toString();
                    double price = Double.parseDouble(item[3].toString());
                    int quantity = Integer.parseInt(item[4].toString());

                    linkerObj.updateStock(name, category, price, quantity);
                }

                JOptionPane.showMessageDialog(null, "Bill generated successfully!");
                billModel.setRowCount(0);
                totalLabel.setText("Total: Rs. 0.0");
                refreshSalesTable(salesTableModel);
                return true;

            }
        }
        return false;
    }

    public void CashiershowScreen(String screenName) {
        if (screenName.equals("ReturnScreen")) {
            refreshSalesTable(salesTableModel);
        }
        else if (screenName.equals("ViewStockScreen")) {
            refreshStockTable(stockModel);
        }
        CashiercardLayout.show(CashierPanel, screenName);
    }

    public JPanel CashiergetPanel() {
        return CashierPanel;
    }

    public void addReturnScreen() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Top Panel with Back Button and Search
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(color2);

        JButton backButton = new JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource("/return-50.png"));
        backButton.setIcon(icon);
        backButton.setPreferredSize(new Dimension(55, 55));
        backButton.addActionListener(e -> CashiershowScreen("CashierDashboardScreen"));

        JTextField searchField = new JTextField(20);
        searchField.setPreferredSize(new Dimension(200, 35));
        searchField.setBorder(BorderFactory.createTitledBorder("Search Sale ID"));

        topPanel.add(backButton);
        topPanel.add(searchField);

        // Sales Table
        String[] columns = {"Sale ID", "Employee", "Date", "Total Amount", "Action"};
        salesTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4;
            }
        };
        salesTable = new JTable(salesTableModel);
        salesTable.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer("View Items"));
        salesTable.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox(), "View Items"));

        // Load initial data
        Object[][] salesData = linkerObj.getSalesData();
        for (Object[] sale : salesData) {
            salesTableModel.addRow(sale);
        }

        // Handle return button click
        salesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = salesTable.getColumnModel().getColumnIndexAtX(evt.getX());
                int row = evt.getY() / salesTable.getRowHeight();

                if (row < salesTable.getRowCount() && row >= 0 && column == 4) {
                    int saleId = (int) salesTable.getValueAt(row, 0);
                    showSaleItemsDialog(saleId);
                }
            }
        });

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(salesTable), BorderLayout.CENTER);

        CashierPanel.add(mainPanel, "ReturnScreen");
    }

    private void showSaleItemsDialog(int saleId) {
        JDialog dialog = new JDialog();
        dialog.setTitle("Sale Items - Sale ID: " + saleId);
        dialog.setLayout(new BorderLayout());
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(null);

        String[] columns = {"Product ID", "Name", "Quantity", "Price", "Returned", "Action"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        JTable itemsTable = new JTable(model);

        Object[][] items = linkerObj.getSaleItems(saleId);
        for (Object[] item : items) {
            model.addRow(item);
        }

        itemsTable.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer("Return"));
        itemsTable.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox(), "Return"));

        itemsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int column = itemsTable.getColumnModel().getColumnIndexAtX(evt.getX());
                int row = evt.getY() / itemsTable.getRowHeight();

                if (row < itemsTable.getRowCount() && row >= 0 && column == 5) {
                    int productId = (int) itemsTable.getValueAt(row, 0);
                    int currentQuantity = (int) itemsTable.getValueAt(row, 2);
                    int returnedQuantity = (int) itemsTable.getValueAt(row, 4);

                    String input = JOptionPane.showInputDialog(dialog,
                            "Enter quantity to return (Available: " + (currentQuantity - returnedQuantity) + "):");

                    if (input != null && !input.isEmpty()) {
                        try {
                            int returnQuantity = Integer.parseInt(input);
                            if (returnQuantity > 0 && returnQuantity <= (currentQuantity - returnedQuantity)) {
                                if (linkerObj.processReturn(saleId, productId, returnQuantity)) {
                                    JOptionPane.showMessageDialog(dialog, "Return processed successfully!");
                                    if (stockTable != null) {
                                        DefaultTableModel stockModel = (DefaultTableModel) stockTable.getModel();
                                        refreshStockTable(stockModel);
                                    }

                                    dialog.dispose();
                                }
                            } else {
                                JOptionPane.showMessageDialog(dialog, "Invalid return quantity!");
                            }
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(dialog, "Please enter a valid number!");
                        }
                    }
                }
            }
        });

        dialog.add(new JScrollPane(itemsTable), BorderLayout.CENTER);
        dialog.setVisible(true);
    }

    private void refreshSalesTable(DefaultTableModel model) {
        model.setRowCount(0);
        Object[][] salesData = linkerObj.getSalesData();
        for (Object[] sale : salesData) {
            model.addRow(sale);
        }
    }

    private void refreshStockTable(DefaultTableModel model) {
        model.setRowCount(0);
        Object[][] stockData = linkerObj.getStockData();
        for (Object[] item : stockData) {
            model.addRow(item);
        }
    }


}
