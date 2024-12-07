package View;

import Controller.Linker;
import Model.Employee;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SuperAdminGUI {
    private Linker linkerObj;
    private CardLayout SAcardLayout;
    private JPanel SuperAdminPanel;
    private Color color; // Blue colour the one in metro logo
    private Color color1; // Yellow colour the one in metro logo
    private Color color2; // Dark Blue colour the
    private Color color3; // Light Blue colour to be used in buttons;
    private Color color4;

    private Employee employee;
    private String pnum;
    private String city;
    private String address;

    private String Email;
    private String password;

    public SuperAdminGUI(Linker linkerObj) {
        this.linkerObj = linkerObj;
        SAcardLayout = new CardLayout();
        SuperAdminPanel = new JPanel(SAcardLayout);

        color = new Color(44, 116, 229);
        color1 = new Color(229, 185, 35);
        color2 = new Color(11, 28, 64);
        color3 = new Color(136, 193, 229);
        color4 = new Color(38, 97, 156);

        addSALoginScreen();
        addSADashboardScreen();


    }

    public void addSALoginScreen() {
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel welcomelabel = new JLabel("SUPER ADMIN LOGIN", SwingConstants.CENTER);
        welcomelabel.setFont(new Font("Helvetica", Font.BOLD, 38));
        welcomelabel.setForeground(color1);
        topPanel.add(welcomelabel, BorderLayout.CENTER);

        JButton backButton = new JButton();
        ImageIcon icon = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\return-50.png");
        backButton.setIcon(icon);
        backButton.setPreferredSize(new Dimension(55, 55));
        backButton.setFocusPainted(false);
        topPanel.add(backButton, BorderLayout.WEST);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linkerObj.showEmployeeOptionsMenu();
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.4;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(topPanel, gbc);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        bottomPanel.setBackground(color4);

        ImageIcon icon1 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\email-50.png");
        ImageIcon icon2 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\password-50.png");

        JLabel iconLabel1 = new JLabel(icon1);
        JLabel iconLabel2 = new JLabel(icon2);

        JTextField Emailfield = new JTextField(30);
        Emailfield.setBorder(BorderFactory.createTitledBorder("Email"));
        Emailfield.setBackground(color3);
        Emailfield.setFont(new Font("Helvetica", Font.PLAIN, 18));
        Emailfield.setPreferredSize(new Dimension(250, 60));

        JPasswordField Password = new JPasswordField(30);
        Password.setBorder(BorderFactory.createTitledBorder("Password"));
        Password.setBackground(color3);
        Password.setFont(new Font("Helvetica", Font.PLAIN, 18));
        Password.setPreferredSize(new Dimension(250, 60));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel1.add(iconLabel1);
        panel1.setBackground(color4);
        panel1.add(Emailfield);
        panel1.setPreferredSize(new Dimension(icon1.getIconWidth() + Emailfield.getPreferredSize().width, 60));
        panel1.setMaximumSize(new Dimension(icon1.getIconWidth() + Emailfield.getPreferredSize().width, 60));
        panel1.setMinimumSize(new Dimension(icon1.getIconWidth() + Emailfield.getPreferredSize().width, 60));

        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel2.add(iconLabel2);
        panel2.setBackground(color4);
        panel2.add(Password);
        panel2.setPreferredSize(new Dimension(icon2.getIconWidth() + Password.getPreferredSize().width, 60));
        panel2.setMaximumSize(new Dimension(icon2.getIconWidth() + Password.getPreferredSize().width, 60));
        panel2.setMinimumSize(new Dimension(icon2.getIconWidth() + Password.getPreferredSize().width, 60));

        JButton loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(300, 60));
        loginButton.setMinimumSize(new Dimension(300, 60));
        loginButton.setBackground(color2);
        loginButton.setForeground(color1);
        loginButton.setFont(new Font("Helvetica", Font.BOLD, 20));
        loginButton.setMaximumSize(new Dimension(300, 60));
        loginButton.setFocusPainted(false);

        bottomPanel.add(Box.createVerticalStrut(80));
        bottomPanel.add(panel1);
        bottomPanel.add(Box.createVerticalStrut(30));
        bottomPanel.add(panel2);
        bottomPanel.add(Box.createVerticalStrut(50));
        bottomPanel.add(loginButton);

        panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 0.6;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        // Keep your existing login button action listener
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = Emailfield.getText();
                String passcode = new String(Password.getPassword());

                if (linkerObj.LoginValidator(email, passcode)) {
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
                                SuperAdminGUI.this.password = newPass;
                                Email = email;
                                SAshowScreen("SADashboardScreen");
                            } else {
                                JOptionPane.showMessageDialog(null, "Failed to update password!");
                            }
                        }
                    } else {
                        SuperAdminGUI.this.password = passcode;
                        Email = email;
                        SAshowScreen("SADashboardScreen");
                    }

                } else {
                    String str = "Wrong Credentials";
                    displayMessage(str);
                }
            }
        });

        SuperAdminPanel.add(mainpanel, "SuperAdminLoginScreen");
    }
    public void addSADashboardScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("Welcome to Super Admin Dashboard",SwingConstants.CENTER);
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
                    if (linkerObj.validateCurrentPassword(currentPass)) {
                        if (linkerObj.updateSAPassword(newPass)) {
                            JOptionPane.showMessageDialog(null, "Password updated successfully!");
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
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 60, 80));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10,20,20,20));
        bottomPanel.setBackground(color4);

        JButton button1 = new JButton("Add Branch");
        button1.setBackground(color3);
        button1.setBorder(BorderFactory.createLineBorder(color2,5));
        button1.setFont(new Font("Helvetica",Font.BOLD,28));
        button1.setForeground(color2);
        button1.setPreferredSize(new Dimension(300,300));

        ImageIcon icon1 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\shopping-mall-66.png");
        button1.setIcon(icon1);
        button1.setHorizontalTextPosition(SwingConstants.CENTER);  // Center text horizontally
        button1.setVerticalTextPosition(SwingConstants.BOTTOM);
        button1.setFocusPainted(false);

        JButton button2 = new JButton("View Reports");
        button2.setBackground(color3);
        button2.setBorder(BorderFactory.createLineBorder(color2,5));
        button2.setFont(new Font("Helvetica",Font.BOLD,28));
        button2.setForeground(color2);
        button2.setPreferredSize(new Dimension(300,300));
        button2.setFocusPainted(false);

        ImageIcon icon2 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\reports-50.png");
        button2.setIcon(icon2);
        button2.setHorizontalTextPosition(SwingConstants.CENTER);  // Center text horizontally
        button2.setVerticalTextPosition(SwingConstants.BOTTOM);

        bottomPanel.add(button1);
        bottomPanel.add(button2);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SAshowScreen("BranchAddScreen");
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SAshowScreen("ViewReportsScreen");
            }
        });

        SuperAdminPanel.add(mainpanel, "SADashboardScreen");
    }

    public void SAshowScreen(String screenName) {
        SAcardLayout.show(SuperAdminPanel, screenName);
    }
    public JPanel SAgetPanel() {
        return SuperAdminPanel;
    }
    public void addBranchAddScreen(){
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new GridBagLayout());
        mainpanel.setBackground(Color.white);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        topPanel.setBackground(color2);
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(20,20,10,20));
        JLabel welcomelabel = new JLabel("ADD BRANCH MENU",SwingConstants.CENTER);
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
                SAshowScreen("SADashboardScreen");
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

        ImageIcon icon1 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\city-buildings-50.png");
        ImageIcon icon2 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\location-48.png");
        ImageIcon icon3 = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\phone-48.png");

        JLabel iconLabel1 = new JLabel(icon1);
        JLabel iconLabel2 = new JLabel(icon2);
        JLabel iconLabel3 = new JLabel(icon3);

        JTextField cityField = new JTextField(30);
        cityField.setBorder(BorderFactory.createTitledBorder("City"));
        cityField.setBackground(color3);
        cityField.setFont(new Font("Helvetica",Font.PLAIN,18));
        cityField.setPreferredSize(new Dimension(250, 60));

        JTextField addressField = new JTextField(30);
        addressField.setBorder(BorderFactory.createTitledBorder("Address"));
        addressField.setBackground(color3);
        addressField.setFont(new Font("Helvetica",Font.PLAIN,18));
        addressField.setPreferredSize(new Dimension(250, 60));

        JTextField phoneNumField = new JTextField(30);
        phoneNumField.setBorder(BorderFactory.createTitledBorder("PhoneNumber"));
        phoneNumField.setBackground(color3);
        phoneNumField.setFont(new Font("Helvetica",Font.PLAIN,18));
        phoneNumField.setPreferredSize(new Dimension(250, 60));

        // Panel for city field and icon
        JPanel panel1 = new JPanel();
        panel1.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel1.add(iconLabel1);
        panel1.setBackground(color4);
        panel1.add(cityField);
        panel1.setPreferredSize(new Dimension(icon1.getIconWidth() + cityField.getPreferredSize().width, 60));
        panel1.setMaximumSize(new Dimension(icon2.getIconWidth() + addressField.getPreferredSize().width, 60));
        panel1.setMinimumSize(new Dimension(icon2.getIconWidth() + addressField.getPreferredSize().width, 60));
        panel1.revalidate();
        panel1.repaint();

        // Panel for address field and icon
        JPanel panel2 = new JPanel();
        panel2.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel2.add(iconLabel2);
        panel2.setBackground(color4);
        panel2.add(addressField);
        panel2.setPreferredSize(new Dimension(icon2.getIconWidth() + addressField.getPreferredSize().width, 60));
        panel2.setMaximumSize(new Dimension(icon2.getIconWidth() + addressField.getPreferredSize().width, 60));
        panel2.setMinimumSize(new Dimension(icon2.getIconWidth() + addressField.getPreferredSize().width, 60));
        panel2.revalidate();
        panel2.repaint();

        // Panel for phone number field and icon
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panel3.add(iconLabel3);
        panel3.add(phoneNumField);
        panel3.setBackground(color4);
        panel3.setPreferredSize(new Dimension(icon3.getIconWidth() + phoneNumField.getPreferredSize().width, 60));
        panel3.setMaximumSize(new Dimension(icon2.getIconWidth() + addressField.getPreferredSize().width, 60));
        panel3.setMinimumSize(new Dimension(icon2.getIconWidth() + addressField.getPreferredSize().width, 60));
        panel3.revalidate();
        panel3.repaint();

        JButton proceedNextButton = new JButton("Proceed Next");
        proceedNextButton.setPreferredSize(new Dimension(300,60));
        proceedNextButton.setMinimumSize(new Dimension(300,60));
        proceedNextButton.setBackground(color2);
        proceedNextButton.setForeground(color1);
        proceedNextButton.setFont(new Font("Helvetica",Font.BOLD,20));
        proceedNextButton.setMaximumSize(new Dimension(300, 60));
        bottomPanel.revalidate();
        bottomPanel.repaint();

        bottomPanel.add(Box.createVerticalStrut(80));
        bottomPanel.add(panel1);
        bottomPanel.add(Box.createVerticalStrut(30));
        bottomPanel.add(panel2);
        bottomPanel.add(Box.createVerticalStrut(30));
        bottomPanel.add(panel3);
        bottomPanel.add(Box.createVerticalStrut(50));
        bottomPanel.add(proceedNextButton);

        panel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel3.setAlignmentX(Component.CENTER_ALIGNMENT);
        proceedNextButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        proceedNextButton.setFocusPainted(false);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0; // Takes full width
        gbc.weighty = 0.6; // Takes 70% of height
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;
        mainpanel.add(bottomPanel, gbc);

        proceedNextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pnum = phoneNumField.getText();
                city = cityField.getText();
                address = addressField.getText();
                if(!linkerObj.isValidPhoneNumber(pnum)){
                    String str = "Invalid PhoneNum";
                    displayMessage(str);
                }
                else {
                    CardLayout cl = (CardLayout) SuperAdminPanel.getLayout();
                    cl.show(SuperAdminPanel, "addBranchManagerScreen");
                }
            }
        });

        SuperAdminPanel.add(mainpanel, "BranchAddScreen");
    }
}