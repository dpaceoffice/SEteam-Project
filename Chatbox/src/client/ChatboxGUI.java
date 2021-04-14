package client;

import java.awt.Point;

/**
 *
 * @author David
 */
public class ChatboxGUI extends javax.swing.JFrame {

    /**
     * Creates new form Chatbox
     */
    public ChatboxGUI() {
        initComponents();
    }

    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        chatScrollPane = new javax.swing.JScrollPane();
        chatbox = new javax.swing.JTextArea();
        userScrollPane = new javax.swing.JScrollPane();
        userList = new javax.swing.JList<>();
        messageField = new javax.swing.JTextField();
        sendButton = new javax.swing.JButton();
        logoLabel = new javax.swing.JLabel();
        bgLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TeH BoX");
        setBackground(new java.awt.Color(0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setForeground(new java.awt.Color(0, 0, 0));
        setIconImages(null);

        mainPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        chatbox.setEditable(false);
        chatbox.setBackground(new java.awt.Color(102, 102, 102));
        chatbox.setColumns(1);
        chatbox.setForeground(new java.awt.Color(255, 153, 0));
        chatbox.setLineWrap(true);
        chatbox.setRows(5);
        chatbox.setWrapStyleWord(true);
        chatbox.setBorder(new javax.swing.border.MatteBorder(null));
        chatbox.setFocusable(false);
        chatbox.setRequestFocusEnabled(false);
        chatbox.setSelectedTextColor(new java.awt.Color(255, 153, 0));
        chatbox.setSelectionColor(new java.awt.Color(0, 0, 0));
        chatScrollPane.setViewportView(chatbox);

        userList.setBackground(new java.awt.Color(102, 102, 102));
        userList.setBorder(new javax.swing.border.MatteBorder(null));
        userList.setForeground(new java.awt.Color(255, 102, 0));
        userList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        userList.setSelectionBackground(new java.awt.Color(0, 0, 0));
        userList.setSelectionForeground(new java.awt.Color(255, 153, 0));
        userScrollPane.setViewportView(userList);

        messageField.setBackground(new java.awt.Color(102, 102, 102));
        messageField.setForeground(new java.awt.Color(255, 153, 0));
        messageField.setToolTipText("Type a message...");
        messageField.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        messageField.setSelectedTextColor(new java.awt.Color(255, 153, 51));
        messageField.setSelectionColor(new java.awt.Color(0, 0, 0));
        messageField.setVerifyInputWhenFocusTarget(false);
        messageField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageFieldActionPerformed(evt);
            }
        });

        sendButton.setBackground(new java.awt.Color(51, 51, 51));
        sendButton.setForeground(new java.awt.Color(204, 102, 0));
        sendButton.setText("Send");
        sendButton.setBorder(new javax.swing.border.MatteBorder(null));
        sendButton.setBorderPainted(false);
        sendButton.setFocusable(false);

        logoLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\under\\Pictures\\logo.png")); // NOI18N

        bgLabel.setIcon(new javax.swing.ImageIcon("C:\\Users\\under\\Pictures\\bgs\\superiorlife.jpg")); // NOI18N
        bgLabel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentMoved(java.awt.event.ComponentEvent evt) {
                bgLabelComponentMoved(evt);
            }
        });

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(mainPanelLayout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup().addContainerGap(71, Short.MAX_VALUE)
                        .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                        mainPanelLayout.createSequentialGroup()
                                                .addComponent(userScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE,
                                                        149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addContainerGap())
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
                                        mainPanelLayout.createSequentialGroup()
                                                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 64,
                                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(188, 188, 188))
                                .addComponent(logoLabel, javax.swing.GroupLayout.Alignment.TRAILING,
                                        javax.swing.GroupLayout.PREFERRED_SIZE, 690,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup().addContainerGap()
                                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(mainPanelLayout.createSequentialGroup().addComponent(chatScrollPane)
                                                .addGap(189, 189, 189))
                                        .addGroup(mainPanelLayout.createSequentialGroup()
                                                .addComponent(messageField, javax.swing.GroupLayout.DEFAULT_SIZE, 486,
                                                        Short.MAX_VALUE)
                                                .addGap(265, 265, 265))))
                        .addComponent(bgLabel, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)));
        mainPanelLayout.setVerticalGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(logoLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 65,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(userScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                        .addGap(9, 9, 9)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 22,
                                javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(mainPanelLayout.createSequentialGroup().addGap(87, 87, 87)
                                .addComponent(chatScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 488,
                                        Short.MAX_VALUE)
                                .addGap(10, 10, 10)
                                .addComponent(messageField, javax.swing.GroupLayout.PREFERRED_SIZE, 24,
                                        javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(26, 26, 26))
                        .addComponent(bgLabel, javax.swing.GroupLayout.Alignment.TRAILING,
                                javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(mainPanel,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(mainPanel,
                        javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));

        pack();
    }

    private void messageFieldActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void bgLabelComponentMoved(java.awt.event.ComponentEvent evt) {
        // TODO add your handling code here:
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
                new ChatboxGUI().setVisible(true);
            }
        });
    }

    Point initialClick;

    private javax.swing.JLabel bgLabel;
    private javax.swing.JScrollPane chatScrollPane;
    private javax.swing.JTextArea chatbox;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JTextField messageField;
    private javax.swing.JButton sendButton;
    private javax.swing.JList<String> userList;
    private javax.swing.JScrollPane userScrollPane;
}
