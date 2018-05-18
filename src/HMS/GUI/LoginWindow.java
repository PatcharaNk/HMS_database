package HMS.GUI;

import HMS.test.HMSdatabase_manager;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class LoginWindow extends javax.swing.JFrame {

    private HMSdatabase_manager database;
    private LoginListenner loginListener = null;

    public LoginWindow(LoginListenner loginListener, HMSdatabase_manager database) {
        initComponents();
        initListener();
        this.setLocationRelativeTo(null);
        
        this.setTheme();
        this.database = database;
        this.loginListener = loginListener;
        jlb_pwWrong.setText("          ");
        jpw_password.setText("admin");

    }

    //<editor-fold defaultstate="collapsed" desc="code by self">
    private void setTheme() {
        try {
            javax.swing.UIManager.setLookAndFeel(
                    javax.swing.UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException |
                IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            System.err.println("Error setting look and feel:\n\t" + ex);
        }
        this.getContentPane().setBackground(Constant.COLOR_LOGINWINDOW_BG);
        jlb_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource(Constant.LOGO_HMS_LOGINWINDOW)));
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(Constant.MINILOGO_HMS)));
    }

    private void initListener() {
        jbt_login.addActionListener((ActionEvent) -> {
            this.submit();
        }
        );

        KeyAdapter enterClick = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    submit();
                }
            }
        };
        
        jpw_password.addKeyListener(enterClick);
    }

    private void submit() {
        database.setPassword(new String(jpw_password.getPassword()));
        if (database.connect()) {
            loginListener.login();
            System.out.println("connect database successful!");
            jpw_password.setText("");
            jlb_pwWrong.setText("        ");
        } else {
            jlb_pwWrong.setText("กรุณาใส่รหัสผ่านใหม่อีกครั้ง !");
            jpw_password.setText("");
            jpw_password.requestFocus();
        }
    }

    private void clear() {
        jpw_password.setText("");
        jpw_password.requestFocus();
    }
    
    public interface LoginListenner {
        void login();
    }

//</editor-fold>
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jpn_logo = new javax.swing.JPanel();
        jlb_logo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jbt_login = new javax.swing.JButton();
        jlb_pwWrong = new javax.swing.JLabel();
        jpw_password = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("หน้าต่างการเข้าสู่ระบบ");
        setBackground(new java.awt.Color(255, 255, 0));
        setMinimumSize(new java.awt.Dimension(308, 409));
        setResizable(false);
        setSize(new java.awt.Dimension(308, 409));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        jpn_logo.setBackground(new java.awt.Color(255, 255, 255));
        jpn_logo.setPreferredSize(new java.awt.Dimension(225, 150));

        jlb_logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlb_logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/HMS/resource/HMSLOGO02.png"))); // NOI18N

        javax.swing.GroupLayout jpn_logoLayout = new javax.swing.GroupLayout(jpn_logo);
        jpn_logo.setLayout(jpn_logoLayout);
        jpn_logoLayout.setHorizontalGroup(
            jpn_logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlb_logo, javax.swing.GroupLayout.DEFAULT_SIZE, 225, Short.MAX_VALUE)
        );
        jpn_logoLayout.setVerticalGroup(
            jpn_logoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jlb_logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 14, 0);
        getContentPane().add(jpn_logo, gridBagConstraints);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("กรุณาใส่รหัสผ่าน:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        jPanel2.add(jLabel1, gridBagConstraints);

        jbt_login.setText("ลงชื่อเข้าใช้");
        jbt_login.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        jPanel2.add(jbt_login, gridBagConstraints);

        jlb_pwWrong.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jlb_pwWrong.setForeground(new java.awt.Color(255, 0, 0));
        jlb_pwWrong.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jlb_pwWrong.setText("กรุณาใส่รหัสผ่านใหม่อีกครั้ง !");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        jPanel2.add(jlb_pwWrong, gridBagConstraints);

        jpw_password.setMaximumSize(new java.awt.Dimension(120, 21));
        jpw_password.setMinimumSize(new java.awt.Dimension(120, 21));
        jpw_password.setPreferredSize(new java.awt.Dimension(120, 21));
        jpw_password.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jpw_passwordActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 8, 0);
        jPanel2.add(jpw_password, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.6;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jpw_passwordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jpw_passwordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jpw_passwordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jbt_login;
    private javax.swing.JLabel jlb_logo;
    private javax.swing.JLabel jlb_pwWrong;
    private javax.swing.JPanel jpn_logo;
    private javax.swing.JPasswordField jpw_password;
    // End of variables declaration//GEN-END:variables

}
