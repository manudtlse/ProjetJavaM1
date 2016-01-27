
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;



/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Selim Yahi
 */
public class IHM extends javax.swing.JFrame {

    String url = " jdbc:mysql://binary-digit.net:3305/ServeurIdentification";
    String url2 = "jdbc:mysql://binary-digit.net:3305/ServeurAnnuaire";
   
    String loginBDD = "yahimenat";
    String passwordBDD = "odaime";
   
    
    /**
     * Creates new form IHM
     */
    public IHM() throws SQLException {
        initComponents();

    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        connexion = new javax.swing.JButton();
        inscrire = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Login :");

        jLabel2.setText("Mot de passe :");

        jScrollPane1.setViewportView(jTextPane1);
        jTextPane1.getAccessibleContext().setAccessibleName("areaLogin");

        jScrollPane2.setViewportView(jTextPane2);
        jTextPane2.getAccessibleContext().setAccessibleName("areaPassword");

        connexion.setText("Connexion");
        connexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connexionActionPerformed(evt);
            }
        });

        inscrire.setText("s'inscrire");
        inscrire.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inscrireActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inscrire)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(connexion)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(69, 69, 69))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(connexion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addComponent(inscrire)
                .addContainerGap())
        );

        connexion.getAccessibleContext().setAccessibleName("connexion");
        connexion.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // BOUTON CONNEXION
    private void connexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connexionActionPerformed
       
        Connection cn =null;
        Statement st =null;
                
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            cn = DriverManager.getConnection(url, loginBDD, passwordBDD);
            st = cn.createStatement();

            String identifiant = jTextPane1.getText();
            String mdp = jTextPane2.getText();

            // Identification de manu! RECUPERER LES DROITS A LA PLACE DE LOLO
            Identification id = new Identification(identifiant, mdp);










            /*
            // Mise en forme de la requete
            String sql = "SELECT login FROM Compte WHERE password = "+jTextPane2.getText();
            // Resultat de la requete
            ResultSet rs = st.executeQuery(sql);
            if (rs != null)
            { 
                // TRAITEMENT SI OK : OUVERTURE NOUVELLE FENETRE AVEC INFOS
            }  */
        }
        catch (ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }//GEN-LAST:event_connexionActionPerformed

    // BOUTON S'INSCRIRE
    private void inscrireActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inscrireActionPerformed
       
        new fenetreInscription().setVisible(true);
        this.setVisible(false); 
        
    }//GEN-LAST:event_inscrireActionPerformed

    
    /**
     * @author Selim YAHI
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IHM.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new IHM().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(IHM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton connexion;
    private javax.swing.JButton inscrire;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane2;
    // End of variables declaration//GEN-END:variables


}