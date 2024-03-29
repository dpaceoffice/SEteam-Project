package client;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import client.Threading.ServerThread;
import client.Threading.State;


public class GUI extends javax.swing.JFrame {

    /**
     * Default seralization value
     */
    private static final long serialVersionUID = 1L;
    private ServerThread thread;
    private Client client;


    /**
     * Creates new form GUI
     */
    public GUI(ServerThread thread, Client client) {
        this.thread = thread;
        this.client = client;
        initComponents();
    }

    private void initComponents() {

        infoPanel = new javax.swing.JPanel();
        scrollInfoPane = new javax.swing.JScrollPane();
        infoPane = new javax.swing.JTextPane();
        backButton = new javax.swing.JButton();
        loginPanel = new javax.swing.JPanel();
        usrField = new javax.swing.JTextField();
        userLabel = new javax.swing.JLabel();
        pwdLabel = new javax.swing.JLabel();
        pwdField = new javax.swing.JPasswordField();
        loginButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        logoImg = new javax.swing.JLabel();
        bgImg = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("ThE BoX");
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(486, 388));
        setPreferredSize(new java.awt.Dimension(486, 388));
        setResizable(false);
        getContentPane().setLayout(null);

        infoPanel.setBackground(new java.awt.Color(255, 153, 51));
        infoPanel.setBorder(new javax.swing.border.MatteBorder(null));
        infoPanel.setLayout(null);

        infoPane.setEditable(false);
        infoPane.setText("No error");
        scrollInfoPane.setViewportView(infoPane);

        infoPanel.add(scrollInfoPane);
        infoPanel.setVisible(false);
        scrollInfoPane.setBounds(11, 12, 281, 188);

        backButton.setBackground(new java.awt.Color(0, 0, 0));
        backButton.setForeground(new java.awt.Color(255, 255, 255));
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });
        infoPanel.add(backButton);
        backButton.setBounds(108, 206, 87, 23);

        getContentPane().add(infoPanel);
        infoPanel.setBounds(80, 100, 310, 240);

        loginPanel.setBackground(new java.awt.Color(255, 153, 51));
        loginPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        loginPanel.setForeground(new java.awt.Color(51, 51, 51));
        loginPanel.setLayout(null);

        usrField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        usrField.setCaretColor(new java.awt.Color(0, 0, 0));
        usrField.setMinimumSize(new java.awt.Dimension(8, 16));
        usrField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    pwdField.requestFocusInWindow();
                }
            }
        });
        loginPanel.add(usrField);
        usrField.setBounds(13, 34, 194, 16);

        userLabel.setText("Enter a username:");
        loginPanel.add(userLabel);
        userLabel.setBounds(67, 14, 120, 14);

        pwdLabel.setText("Enter a password:");
        loginPanel.add(pwdLabel);
        pwdLabel.setBounds(67, 61, 130, 19);

        pwdField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pwdField.setMinimumSize(new java.awt.Dimension(8, 16));
        pwdField.setName(""); // NOI18N
        pwdField.setPreferredSize(new java.awt.Dimension(5, 16));
        pwdField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (pwdField.toString().isEmpty() || usrField.getText().isEmpty())
                        usrField.requestFocusInWindow();
                    else
                        loginButtonActionPerformed(null);

                }
            }
        });
        loginPanel.add(pwdField);
        pwdField.setBounds(13, 86, 194, 17);

        loginButton.setBackground(new java.awt.Color(0, 0, 0));
        loginButton.setForeground(new java.awt.Color(255, 255, 255));
        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        loginPanel.add(loginButton);
        loginButton.setBounds(66, 137, 90, 23);

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));
        loginPanel.add(jSeparator1);
        jSeparator1.setBounds(3, 121, 214, 10);

        getContentPane().add(loginPanel);
        loginPanel.setBounds(130, 110, 220, 190);

        logoImg.setBackground(new java.awt.Color(0, 0, 0));
        logoImg.setFont(new java.awt.Font("Broadway", 0, 24)); // NOI18N
        logoImg.setForeground(new java.awt.Color(255, 204, 0));
        logoImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/data/logo.png"))); // NOI18N
        getContentPane().add(logoImg);
        logoImg.setBounds(30, 20, 410, 90);

        bgImg.setBackground(new java.awt.Color(255, 0, 0));
        bgImg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/client/data/cutout.png"))); // NOI18N
        getContentPane().add(bgImg);
        bgImg.setBounds(0, -10, 490, 400);

        pack();
    }

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String username = usrField.getText();
        String pass = String.valueOf(pwdField.getPassword());
        thread.checkLogin(username, pass);
    }

    public void handleLoginReq(int opcode) {
        backButton.setText("Back");
        if(opcode == 1) {
            infoPane.setText("Success!\nPress the continue button to begin chatting!");
            backButton.setText("Continue");
        } else if(opcode == 3) {
            infoPane.setText("Failure!\nThis account seems to already be logged in!");
        } else if(opcode == 4) {
            infoPane.setText("Failure!\nThis username doesn't exist!");
        } else {
            infoPane.setText("Failure!\nThe username and password you entered is incorrect!");
        }
        infoPanel.setVisible(true);
        loginPanel.setVisible(false);
    
    }

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {
        infoPanel.setVisible(false);
        if(backButton.getText().equals("Continue")) {
            try {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (ClassNotFoundException ex) {
                java.util.logging.Logger.getLogger(ChatboxGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
                        ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(ChatboxGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
                        ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(ChatboxGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
                        ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(ChatboxGUI.class.getName()).log(java.util.logging.Level.SEVERE, null,
                        ex);
            }
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new ChatboxGUI(thread, client).setVisible(true);
                    thread.changeState(State.CHATTING.ordinal());
                    System.out.println("Enter a message below to chat:");
                }
            });
            this.setVisible(false);
        } else
            loginPanel.setVisible(true);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GUI(null,null).setVisible(true);
            }
        });
    }

    private javax.swing.JLabel bgImg;
    private javax.swing.JTextPane infoPane;
    private javax.swing.JButton backButton;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton loginButton;
    private javax.swing.JPanel loginPanel;
    private javax.swing.JLabel logoImg;
    private javax.swing.JPasswordField pwdField;
    private javax.swing.JLabel pwdLabel;
    private javax.swing.JScrollPane scrollInfoPane;
    private javax.swing.JLabel userLabel;
    private javax.swing.JTextField usrField;
}
