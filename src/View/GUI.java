package View;

import Controller.Linker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.lang.Integer.parseInt;

public class GUI {

    private SuperAdminGUI superAdminGUI;
    private BranchManagerGUI branchManagerGUI;
    private CashierGUI cashierGUI;
    private DataEntryOpGUI dataEntryOpGUI;

    private Linker linkerObj;
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Color color; // Blue colour the one in metro logo
    private Color color1; // Yellow colour the one in metro logo
    private Color color2; // Dark Blue colour the
    private Color color3; // Light Blue colour to be used in buttons;
    private Color color4;

    public GUI(Linker linkerObj,SuperAdminGUI superAdminGUI,BranchManagerGUI branchManagerGUI, CashierGUI cashierGUI, DataEntryOpGUI dataEntryOpGUI){

        this.linkerObj = linkerObj;
        this.superAdminGUI = superAdminGUI;
        this.branchManagerGUI = branchManagerGUI;
        this.cashierGUI = cashierGUI;
        this.dataEntryOpGUI = dataEntryOpGUI;

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);


        color = new Color(44,116,229);
        color1 = new Color(229,185,35);
        color2 = new Color(11,28,64);
        color3 = new Color(136,193,229);
        color4 = new Color(38,97,156);


        UIManager.put("ProgressBar.selectionForeground", new Color(3,65,124));
        UIManager.put("ProgressBar.selectionBackground", Color.BLACK);

        addSplashScreen();
        addEmployeeOptionsMenu();



        JFrame frame = new JFrame("Metro Pos System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(800, 500);
        frame.add(cardPanel);  // Add the cardPanel to the frame
        frame.setVisible(true);
    }

    public void switchToSuperAdmin() {
        cardLayout.show(cardPanel, "SuperAdminPanel");
    }

    public void showScreen(String screenName) {
        cardLayout.show(cardPanel, screenName);
    }

    // This function is to create the Splash Screen
    public void addSplashScreen(){
        JPanel splashPanel = new JPanel();
        splashPanel.setLayout(new BoxLayout(splashPanel,BoxLayout.Y_AXIS));
        splashPanel.setBackground(color4);

        ImageIcon image = new ImageIcon("D:\\Java Practice\\POS System Metro\\src\\metro.png");
        JLabel imglabel = new JLabel(image);
        imglabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        JProgressBar loadingbar = new JProgressBar();
        loadingbar.setValue(0);
        loadingbar.setStringPainted(true);
        loadingbar.setForeground(color1);
        loadingbar.setBackground(Color.BLACK);
        loadingbar.setPreferredSize(new Dimension(500,25));
        loadingbar.setMaximumSize(new Dimension(500,25));
        loadingbar.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadingbar.isIndeterminate();


        splashPanel.add(Box.createVerticalStrut(75));
        splashPanel.add(imglabel);
        splashPanel.add(Box.createVerticalStrut(20));
        splashPanel.add(loadingbar);
        splashPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        splashPanel.setAlignmentY(Component.CENTER_ALIGNMENT);

        cardPanel.add(splashPanel,"SplashScreen");

        ActionListener taskPerformer = new ActionListener() {
            int progress = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if(progress <= 100){
                    loadingbar.setValue(progress);
                    progress++;
                } else {
                    ((Timer) e.getSource()).stop();
                    showScreen("EmployeeTypeScreen");
                }
            }
        };
        Timer timer = new Timer(80,taskPerformer);
        timer.start();
    }

    //This function is to create a menu for employee types
    public void addEmployeeOptionsMenu(){
        JPanel employeetypesPanel = new JPanel(new GridBagLayout());
        employeetypesPanel.setBackground(color4); // Set background color to blue

        JPanel insidePanel = new JPanel(new GridBagLayout());
        insidePanel.setBackground(color3); // White box inside

        insidePanel.setPreferredSize(new Dimension(600, 600));

        insidePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();

        // Add the label "WHO ARE YOU?"
        JLabel askemployee = new JLabel("WHO ARE YOU?", SwingConstants.CENTER);
        askemployee.setForeground(color2);
        askemployee.setFont(new Font("Arial", Font.BOLD, 30));
        gbc.gridx = 0; // Column 0 (centered)
        gbc.gridy = 0; // Row 0 (first row)
        gbc.gridwidth = 2; // Span across two columns
        gbc.insets = new Insets(20, 10, 20, 10); // Add padding around the label
        gbc.anchor = GridBagConstraints.CENTER; // Center alignment
        insidePanel.add(askemployee, gbc); // Add label to inside panel

        // Add Super Admin button first
        JButton superAdminButton = new JButton("Super Admin");
//        Color color1 = new Color(229,185,35);
        superAdminButton.setBackground(color2);  // Yellow button background
        superAdminButton.setForeground(color1);   // Blue text
        superAdminButton.setFocusPainted(false);
        superAdminButton.setFont(new Font("Helvetica",Font.BOLD,20));
        superAdminButton.setPreferredSize(new Dimension(250, 70));
        superAdminButton.setMinimumSize(new Dimension(250,70));

        gbc.gridy = 1; // Row 1 (below the label)
        gbc.gridwidth = 2; // Span across two columns
        gbc.insets = new Insets(10, 10, 20, 10); // Padding for the button
        gbc.anchor = GridBagConstraints.CENTER; // Center alignment
        insidePanel.add(superAdminButton, gbc); // Add Super Admin button

        // Add Branch Manager button
        JButton branchManagerButton = new JButton("Branch Manager");
        branchManagerButton.setBackground(color2);  // Yellow button background
        branchManagerButton.setForeground(color1);
        branchManagerButton.setFont(new Font("Helvetica",Font.BOLD,20));// Blue text
        branchManagerButton.setFocusPainted(false);
        branchManagerButton.setPreferredSize(new Dimension(250, 70));
        branchManagerButton.setMinimumSize(new Dimension(250,70));

        gbc.gridy = 2; // Row 2 (below Super Admin button)
        insidePanel.add(branchManagerButton, gbc); // Add button to inside panel

        // Add Cashier button
        JButton cashierButton = new JButton("Cashier");
        cashierButton.setBackground(color2);  // Yellow button background
        cashierButton.setForeground(color1);
        cashierButton.setFont(new Font("Helvetica",Font.BOLD,20));// Blue text
        cashierButton.setFocusPainted(false);
        cashierButton.setPreferredSize(new Dimension(250, 70));
        cashierButton.setMinimumSize(new Dimension(250,70));

        gbc.gridy = 3; // Row 3 (below Branch Manager button)
        insidePanel.add(cashierButton, gbc); // Add button to inside panel

        // Add Data Entry Operator button
        JButton dataEntryOpButton = new JButton("Data Entry Operator");
        dataEntryOpButton.setBackground(color2);  // Yellow button background
        dataEntryOpButton.setForeground(color1);
        dataEntryOpButton.setFont(new Font("Helvetica",Font.BOLD,20));// Blue text
        dataEntryOpButton.setFocusPainted(false);
        dataEntryOpButton.setPreferredSize(new Dimension(250, 70));
        dataEntryOpButton.setMinimumSize(new Dimension(250,70));

        gbc.gridy = 4; // Row 4 (below Cashier button)
        insidePanel.add(dataEntryOpButton, gbc); // Add button to inside panel


        superAdminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEntityScreen("SuperAdmin","SuperAdminLoginScreen");
            }
        });

        branchManagerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEntityScreen("BranchManager","BranchManagerLoginScreen");
            }
        });

        cashierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEntityScreen("Cashier","CashierLoginScreen");
            }
        });

        dataEntryOpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showEntityScreen("DEO","DEOLoginScreen");
            }
        });

        // Add the inside panel to the main panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        employeetypesPanel.add(insidePanel, gbc);

        // Add the employeetypesPanel to the card panel
        cardPanel.add(employeetypesPanel, "EmployeeTypeScreen");
    }

    //This function is to create login error screen
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

    public void showEntityScreen(String entity, String internalScreenName) {
        if(entity.equals("SuperAdmin")){
            cardLayout.show(cardPanel, "SuperAdminPanel");
            superAdminGUI.SAshowScreen(internalScreenName);
        }
        //else if(entity.equals("BranchManager")){
//            cardLayout.show(cardPanel,"BranchManagerPanel");
//            branchManagerGUI.BMshowScreen("BMLoginScreen");
//        }
//        else if(entity.equals("Cashier")){
//            cardLayout.show(cardPanel,"CashierPanel");
//            cashierGUI.CashiershowScreen("CashierLoginScreen");
//        }
//        else if(entity.equals("DEO")){
//            cardLayout.show(cardPanel,"DEOPanel");
//            dataEntryOpGUI.DEOshowScreen("DEOLoginScreen");
//        }
        else{
            displayMessage("Invalid Entity Type");
        }
    }

}

