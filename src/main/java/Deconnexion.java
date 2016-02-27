
import java.sql.Connection;
import java.sql.DriverManager;
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
public class Deconnexion {
    //Déconnecté client
    
    private String url1="jdbc:mysql://binary-digit.net:3305/yahimenat";//ServeurIdentification
    private String url2="jdbc:mysql://binary-digit.net:3306/yahimenat";//ServeurAnnuaire
    private String bdlogin="yahimenat";
    private String bdmdp="odaime";
    private String identifiant;
    private String mdp;
    Connection conn;

    public void deconnexion(int id_compte) throws Exception 
    {   
            try 
            {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
                Statement s = con.createStatement(); 
                int rs = s.executeUpdate("update yahimenat.profil_etudiant set connecte='0', portClient=null,addressClient=null where id_compte='"+id_compte+"';");
            } 
            catch(Exception ex) 
            {
            }
    }
    public static void main(String[] args) throws Exception 
    {
        int resultat;
        int chiffre;
        Deconnexion d1;
        d1 = new Deconnexion();
        int id_compte=76;
        d1.deconnexion(id_compte);
            
    }		
}
