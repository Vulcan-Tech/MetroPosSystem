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
    public void addBMLoginScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("Branch Manager Login",SwingConstants.CENTER);
        welcomelabel.setFont(new Font("Helvetica",Font.BOLD,38));
        welcomelabel.setForeground(color1);
        topPanel.add(welcomelabel,BorderLayout.CENTER);

        JButton backButton = new JButton();
        ImageIcon iconimg = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\return-50.png");
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

        ImageIcon icon1 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\id-50.png");
        ImageIcon icon2 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\email-50.png");
        ImageIcon icon3 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\password-50.png");

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

        JTextField passwordField = new JTextField(30);
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
                String passcode = passwordField.getText();

                if(linkerObj.EmpLoginValidator(id,email,passcode)) {
                    setBranchID(id);
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
//                                BranchManagerGUI.this.password = newPass;
//                                Email = email;

                                BMshowScreen("BMDashboardScreen");

                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to update password!");
                            }
                        }
                    } else {
                        setPassword(passcode);
                        setEmail(email);
//                        BranchManagerGUI.this.password = passcode;
//                        Email = email;
                        BMshowScreen("BMDashboardScreen");
                    }
                } else {
                    String str = "Wrong Credentials";
                    displayMessage(str);
                }
            }
        });

        BranchManagerPanel.add(mainpanel, "BranchAddScreen");
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

    public void addBMDashboardScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("Welcome to Branch Manager Dashboard",SwingConstants.CENTER);
        welcomelabel.setFont(new Font("Helvetica",Font.BOLD,38));
        welcomelabel.setForeground(color1);
        topPanel.add(welcomelabel,BorderLayout.CENTER);

        JButton backButton = new JButton();
        ImageIcon iconimg = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\return-50.png");
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
        ImageIcon icon = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\settings-50.png");
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

        JButton addCashierbutton = new JButton("Add Cashier");
        addCashierbutton.setBackground(color3);
        addCashierbutton.setBorder(BorderFactory.createLineBorder(color2,5));
        addCashierbutton.setFont(new Font("Helvetica",Font.BOLD,28));
        addCashierbutton.setForeground(color2);
        addCashierbutton.setFocusPainted(false);
        addCashierbutton.setPreferredSize(new Dimension(250,250));

        ImageIcon icon1 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\cashier-64.png");
        addCashierbutton.setIcon(icon1);
        addCashierbutton.setHorizontalTextPosition(SwingConstants.CENTER);  // Center text horizontally
        addCashierbutton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton addDEObutton = new JButton("Add Data Entry Op");
        addDEObutton.setBackground(color3);
        addDEObutton.setBorder(BorderFactory.createLineBorder(color2,5));
        addDEObutton.setFont(new Font("Helvetica",Font.BOLD,26));// reduced size a bit to fit thetext from 28 to 26
        addDEObutton.setForeground(color2);
        addDEObutton.setPreferredSize(new Dimension(250,250));
        addDEObutton.setFocusPainted(false);

        ImageIcon icon2 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\data-entry-50.png");
        addDEObutton.setIcon(icon2);
        addDEObutton.setHorizontalTextPosition(SwingConstants.CENTER);  // Center text horizontally
        addDEObutton.setVerticalTextPosition(SwingConstants.BOTTOM);

        JButton viewStaffbutton = new JButton("View Staff");
        viewStaffbutton.setBackground(color3);
        viewStaffbutton.setBorder(BorderFactory.createLineBorder(color2,5));
        viewStaffbutton.setFont(new Font("Helvetica",Font.BOLD,28));
        viewStaffbutton.setForeground(color2);
        viewStaffbutton.setFocusPainted(false);
        viewStaffbutton.setPreferredSize(new Dimension(250,250));

        ImageIcon icon3 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\staff-50.png");
        viewStaffbutton.setIcon(icon3);
        viewStaffbutton.setHorizontalTextPosition(SwingConstants.CENTER);
        viewStaffbutton.setVerticalTextPosition(SwingConstants.BOTTOM);// Center text horizontally

        JButton viewReportsbutton = new JButton("View Reports");
        viewReportsbutton.setBackground(color3);
        viewReportsbutton.setBorder(BorderFactory.createLineBorder(color2,5));
        viewReportsbutton.setFont(new Font("Helvetica",Font.BOLD,28));
        viewReportsbutton.setForeground(color2);
        viewReportsbutton.setFocusPainted(false);
        viewReportsbutton.setPreferredSize(new Dimension(250,250));

        ImageIcon icon4 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\reports-50.png");
        viewReportsbutton.setIcon(icon4);
        viewReportsbutton.setHorizontalTextPosition(SwingConstants.CENTER);
        viewReportsbutton.setVerticalTextPosition(SwingConstants.BOTTOM);

        bottomPanel.add(addCashierbutton);
        bottomPanel.add(addDEObutton);
        bottomPanel.add(viewStaffbutton);
        bottomPanel.add(viewReportsbutton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        addCashierbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BMshowScreen("addCashierScreen");
            }
        });

        addDEObutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BMshowScreen("addDEOScreen");
            }
        });

        viewStaffbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BMshowScreen("ViewStaffScreen");
            }
        });

        viewReportsbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BMshowScreen("ViewReportsScreen");
            }
        });

        BranchManagerPanel.add(mainpanel, "BMDashboardScreen");
    }
    public void addCashierScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("ADD CASHIER MENU ",SwingConstants.CENTER);
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
                BMshowScreen("BMDashboardScreen");
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
        ImageIcon icon3 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\salary-50.png");
        ImageIcon icon4 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\roles-50.png");

        JLabel iconLabel1 = new JLabel(icon1);
        JLabel iconLabel2 = new JLabel(icon2);
        JLabel iconLabel3 = new JLabel(icon3);
        JLabel iconLabel4 = new JLabel(icon4);

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

        JTextField Salary = new JTextField(30);
        Salary.setBorder(BorderFactory.createTitledBorder("Salary"));
        Salary.setBackground(color3);
        Salary.setFont(new Font("Helvetica",Font.PLAIN,18));
        Salary.setPreferredSize(new Dimension(250, 60));

        JTextField Role = new JTextField(30);
        Role.setBorder(BorderFactory.createTitledBorder("Role"));
        Role.setBackground(color3);
        Role.setFont(new Font("Helvetica",Font.PLAIN,18));
        Role.setPreferredSize(new Dimension(250, 60));
        Role.setText("Cashier");
        Role.setEditable(false);

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
        panel3.add(Salary);
        panel3.setBackground(color4);
        panel3.setPreferredSize(new Dimension(icon3.getIconWidth() + Salary.getPreferredSize().width, 60));
        panel3.setMaximumSize(new Dimension(icon3.getIconWidth() + Email.getPreferredSize().width, 60));
        panel3.setMinimumSize(new Dimension(icon3.getIconWidth() + Email.getPreferredSize().width, 60));
        panel3.revalidate();
        panel3.repaint();

        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel4.add(iconLabel4);
        panel4.add(Role);
        panel4.setBackground(color4);
        panel4.setPreferredSize(new Dimension(icon4.getIconWidth() + Role.getPreferredSize().width, 60));
        panel4.setMaximumSize(new Dimension(icon4.getIconWidth() + Email.getPreferredSize().width, 60));
        panel4.setMinimumSize(new Dimension(icon4.getIconWidth() + Email.getPreferredSize().width, 60));
        panel4.revalidate();
        panel4.repaint();

        JButton addCashierbutton = new JButton("Add Cashier");
        addCashierbutton.setPreferredSize(new Dimension(300,60));
        addCashierbutton.setMinimumSize(new Dimension(300,60));
        addCashierbutton.setBackground(color2);
        addCashierbutton.setForeground(color1);
        addCashierbutton.setFont(new Font("Helvetica",Font.BOLD,20));
        addCashierbutton.setMaximumSize(new Dimension(300, 60));
        bottomPanel.revalidate();
        bottomPanel.repaint();

        bottomPanel.add(Box.createVerticalStrut(40));
        bottomPanel.add(panel1);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel2);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel3);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel4);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(addCashierbutton);

        panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel4.setAlignmentX(Component.CENTER_ALIGNMENT);
        addCashierbutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addCashierbutton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        addCashierbutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employee = new Employee();
                employee.setName(Name.getText());
                employee.setEmail(Email.getText());
                employee.setSalary(Double.parseDouble(Salary.getText()));
                employee.setRole(Role.getText());
                if(employee.getSalary() < 0){
                    String str = "Not possible";
                    displayMessage(str);
                }
                else {
                   linkerObj.addEmpData(employee.getName(),employee.getEmail(),employee.getSalary(),employee.getRole(), branchId);
                   displayMessage("Cashier Added");
                }
            }
        });

        BranchManagerPanel.add(mainpanel, "addCashierScreen");
    }
    public void BMshowScreen(String screenName) {
        BMcardLayout.show(BranchManagerPanel, screenName);
    }

    public JPanel BMgetPanel() {
        return BranchManagerPanel;
    }
    public void addDEOScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("ADD DATA ENTRY OPERATOR",SwingConstants.CENTER);
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
                BMshowScreen("BMDashboardScreen");
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
        ImageIcon icon3 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\salary-50.png");
        ImageIcon icon4 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\roles-50.png");

        JLabel iconLabel1 = new JLabel(icon1);
        JLabel iconLabel2 = new JLabel(icon2);
        JLabel iconLabel3 = new JLabel(icon3);
        JLabel iconLabel4 = new JLabel(icon4);

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

        JTextField Salary = new JTextField(30);
        Salary.setBorder(BorderFactory.createTitledBorder("Salary"));
        Salary.setBackground(color3);
        Salary.setFont(new Font("Helvetica",Font.PLAIN,18));
        Salary.setPreferredSize(new Dimension(250, 60));

        JTextField Role = new JTextField(30);
        Role.setBorder(BorderFactory.createTitledBorder("Role"));
        Role.setBackground(color3);
        Role.setFont(new Font("Helvetica",Font.PLAIN,18));
        Role.setPreferredSize(new Dimension(250, 60));
        Role.setText("Data Entry Operator");
        Role.setEditable(false);

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
        panel3.add(Salary);
        panel3.setBackground(color4);
        panel3.setPreferredSize(new Dimension(icon3.getIconWidth() + Salary.getPreferredSize().width, 60));
        panel3.setMaximumSize(new Dimension(icon3.getIconWidth() + Email.getPreferredSize().width, 60));
        panel3.setMinimumSize(new Dimension(icon3.getIconWidth() + Email.getPreferredSize().width, 60));
        panel3.revalidate();
        panel3.repaint();

        JPanel panel4 = new JPanel();
        panel4.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel4.add(iconLabel4);
        panel4.add(Role);
        panel4.setBackground(color4);
        panel4.setPreferredSize(new Dimension(icon4.getIconWidth() + Role.getPreferredSize().width, 60));
        panel4.setMaximumSize(new Dimension(icon4.getIconWidth() + Email.getPreferredSize().width, 60));
        panel4.setMinimumSize(new Dimension(icon4.getIconWidth() + Email.getPreferredSize().width, 60));
        panel4.revalidate();
        panel4.repaint();

        JButton addDEObutton = new JButton("Add DEO");
        addDEObutton.setPreferredSize(new Dimension(300,60));
        addDEObutton.setMinimumSize(new Dimension(300,60));
        addDEObutton.setBackground(color2);
        addDEObutton.setForeground(color1);
        addDEObutton.setFont(new Font("Helvetica",Font.BOLD,20));
        addDEObutton.setMaximumSize(new Dimension(300, 60));
        bottomPanel.revalidate();
        bottomPanel.repaint();

        bottomPanel.add(Box.createVerticalStrut(40));
        bottomPanel.add(panel1);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel2);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel3);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(panel4);
        bottomPanel.add(Box.createVerticalStrut(20));
        bottomPanel.add(addDEObutton);

        panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel4.setAlignmentX(Component.CENTER_ALIGNMENT);
        addDEObutton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addDEObutton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        addDEObutton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employee = new Employee();
                employee.setName(Name.getText());
                employee.setEmail(Email.getText());
                employee.setSalary(Double.parseDouble(Salary.getText()));
                employee.setRole(Role.getText());
                if(employee.getSalary() < 0){
                    String str = "Not possible";
                    JOptionPane.showMessageDialog(null, str);
                }
                else {
                    linkerObj.addEmpData(employee.getName(),employee.getEmail(),employee.getSalary(),employee.getRole(), branchId);
                    JOptionPane.showMessageDialog(null, "DEO ADDED");
                }
            }
        });

        BranchManagerPanel.add(mainpanel, "addDEOScreen");
    }
    public void addViewStaffScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("Branch Staff List",SwingConstants.CENTER);
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
                BMshowScreen("BMDashboardScreen");
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

        System.out.println("Current Branch ID for staff view: " + branchId);

        String[] columnNames = {"EmployeeID", "Name", "Email", "Salary", "Role"};
        DefaultTableModel Model = new DefaultTableModel(columnNames, 0);

        // Get employee list for current branch
        List<Employee> empList = linkerObj.employeelist(branchId);
        System.out.println("Found " + empList.size() + " employees");

        // Add each employee to the table model
        for(Employee emp : empList) {
            Object[] rowData = {
                    emp.getEmployeeId(),
                    emp.getName(),
                    emp.getEmail(),
                    emp.getSalary(),
                    emp.getRole()
            };
            Model.addRow(rowData);
        }

        // Configure table display properties
        JTable table = new JTable(Model);
        table.setFillsViewportHeight(true);
        table.setRowHeight(30);
        table.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollPane = new JScrollPane(table);
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        BranchManagerPanel.add(mainpanel, "ViewStaffScreen");
    }
}