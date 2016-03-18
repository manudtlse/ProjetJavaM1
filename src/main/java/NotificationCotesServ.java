
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author many31
 */
public class NotificationCotesServ {
     private String url1="jdbc:mysql://binary-digit.net:3305/yahimenat";//ServeurIdentification
    private String url2="jdbc:mysql://binary-digit.net:3306/yahimenat";//ServeurAnnuaire
    private String bdlogin="yahimenat";
    private String bdmdp="odaime";
    Connection con;
    
    public String notificationLike(int num_etudiant)
    { 
        String resultat="";
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            Statement s2 = con.createStatement();
            ResultSet rs=s.executeQuery("SELECT * FROM notification where id_notifie='"+num_etudiant+"' AND (id_likeur!='null' or id_likeur!='0');");
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            while (rs.next())
            {
                int num_etudiantLikeur=rs.getInt("id_likeur");
                System.out.println("id_likeur: "+num_etudiantLikeur);
                ResultSet rs2 = s2.executeQuery("select * from profil_etudiant WHERE num_etudiant='"+num_etudiantLikeur+"' ;");
                while (rs2.next())
                {
                    String nom_likeur=rs2.getString("nom_etudiant");
                    String prenom_likeur=rs2.getString("prenom_etudiant");
                    resultat = resultat+nom_likeur+" "+prenom_likeur+" a liké votre profil"+"  "; 
                }  
            } 
           s2.executeUpdate("DELETE FROM notification WHERE id_notifie="+num_etudiant+" AND (id_likeur!='null' or id_likeur!='0');");
              
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            resultat="erreur"; 
        }
        return resultat;
    }
    
    public String notificationRecommandation(int num_etudiant)
    { 
        String resultat="";
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            Statement s2 = con.createStatement();
            Statement s3 = con.createStatement();
            ResultSet rs=s.executeQuery("SELECT * FROM notification where id_notifie='"+num_etudiant+"' AND (id_recommandeur!='null' or id_recommandeur!='0');");
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            while (rs.next())
            {
                int num_etudiantRecommandeur=rs.getInt("id_recommandeur");
                int id_competence=rs.getInt("id_competence");
                String label_competence="";
                System.out.println("id_recommandeur: "+num_etudiantRecommandeur);
                ResultSet rs2 = s2.executeQuery("select * from profil_etudiant WHERE num_etudiant='"+num_etudiantRecommandeur+"';");
                while (rs2.next())
                {
                    
                    String nom_Recommandeur=rs2.getString("nom_etudiant");
                    String prenom_Recommandeur=rs2.getString("prenom_etudiant");
                    ResultSet rs3 = s3.executeQuery("select label_competence from competence where id_competence= '"+id_competence+"';");
                    while (rs3.next())
                    {
                        label_competence=rs3.getString("label_competence");
                    }
                    
                    resultat = resultat+label_competence+" vous a été recommandé par: "+nom_Recommandeur+" "+prenom_Recommandeur+"  "; 
                }  
            } 
            s2.executeUpdate("DELETE FROM notification WHERE id_notifie="+num_etudiant+" AND (id_recommandeur!='null' or id_recommandeur!='0');");
              
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            resultat="erreur"; 
        }
        return resultat;
    }
    
    public String notificationMessage(int num_etudiant)
    { 
        String resultat="";
        try 
        {	
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            Statement s = con.createStatement();
            Statement s2 = con.createStatement();
            Statement s3 = con.createStatement();
            ResultSet rs=s.executeQuery("SELECT * FROM notification where id_notifie='"+num_etudiant+"' AND (id_envoyeur_message!='null' or id_envoyeur_message!='0');");
            rs.last();
            int NbLignes=rs.getRow();
            rs.beforeFirst();
            resultat=NbLignes+"  ";
            while (rs.next())
            {
                int num_etudiantEnvoyeur=rs.getInt("id_envoyeur_message");
                int id_competence=rs.getInt("id_competence");
                String label_competence="";
                System.out.println("id_envoyeur_message: "+num_etudiantEnvoyeur);
                ResultSet rs2 = s2.executeQuery("select * from profil_etudiant WHERE num_etudiant='"+num_etudiantEnvoyeur+"';");
                while (rs2.next())
                {
                    
                    String nom_Envoyeur=rs2.getString("nom_etudiant");
                    String prenom_Envoyeur=rs2.getString("prenom_etudiant");
                    resultat = resultat+nom_Envoyeur+" "+prenom_Envoyeur+" vous a envoyé un message"+"  "; 
                }  
            } 
          //  s2.executeUpdate("DELETE FROM notification WHERE id_notifie="+num_etudiant+" AND (id_envoyeur_message!='null' or id_envoyeur_message!='0');");
              
        } 
        catch(ClassNotFoundException | SQLException ex) 
        {
            resultat="erreur"; 
        }
        return resultat;
    }

}
