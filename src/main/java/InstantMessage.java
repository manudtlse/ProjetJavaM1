
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author many31
 */
public class InstantMessage {
    private String url1="jdbc:mysql://binary-digit.net:3305/yahimenat";//ServeurIdentification
    private String url2="jdbc:mysql://binary-digit.net:3306/yahimenat";//ServeurAnnuaire
    private String bdlogin="yahimenat";
    private String bdmdp="odaime";
    private String identifiant;
    private String mdp;
    
    
    
    public String InsertPortAddressClient(int id_compte,int portClient, InetAddress address) throws ClassNotFoundException
    {
        String res="";
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            // on cree un objet Statement qui va permettre l'execution des requetes
            Statement s = con.createStatement();
            ResultSet Result;
            Result = s.executeQuery("SELECT * FROM yahimenat.profil_etudiant WHERE id_compte ='"+id_compte+"';");
            if (Result.next())
            {
                s.executeUpdate("update yahimenat.profil_etudiant set portClient='"+portClient+"',addressClient='"+address+"' where id_compte='"+id_compte+"';");
                
                
                
                
                
                res="insertion ok";
            }
            else
            {
                res="Compte inexistant";
            }
        }
        catch (SQLException ex) 
        {
            Logger.getLogger(InstantMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return res;
    }
    
    public int recupPortClient(int id_compte) throws ClassNotFoundException
    {
        int RsPortClient=-1;
    
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            // on cree un objet Statement qui va permettre l'execution des requetes
            Statement s = con.createStatement();
            ResultSet portClient;
            portClient = s.executeQuery("select portClient from profil_etudiant where id_compte='"+id_compte+"';");
            if (!portClient.next())
                    {
                        RsPortClient=-1;
                    } 
                else 
                    {
                        RsPortClient=portClient.getInt("portClient");
                    }
        }   
        catch (SQLException ex) 
        {
            Logger.getLogger(InstantMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return RsPortClient;
    }
    
    public String recupAddressClient(int id_compte) throws ClassNotFoundException
    {
        String RsAddressClient="erreur";
    
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            // on cree un objet Statement qui va permettre l'execution des requetes
            Statement s = con.createStatement();
            ResultSet addressClient;
            addressClient = s.executeQuery("select addressClient from profil_etudiant where id_compte='"+id_compte+"';");
            if (!addressClient.next())
                    {
                        RsAddressClient="erreur";
                    } 
                else 
                    {
                        RsAddressClient=addressClient.getString("addressClient");
                    }
        }   
        catch (SQLException ex) 
        {
            Logger.getLogger(InstantMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return RsAddressClient;
    }
    
     public String recupIdentiteClient(int id_compte) throws ClassNotFoundException
    {
        String RsIdentiteClient="erreur";
    
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            // on cree un objet Statement qui va permettre l'execution des requetes
            Statement s = con.createStatement();
            ResultSet IdentiteClient;
            IdentiteClient = s.executeQuery("select * from profil_etudiant where id_compte='"+id_compte+"';");
            if (!IdentiteClient.next())
                    {
                        RsIdentiteClient="erreur";
                    } 
                else 
                    {
                        RsIdentiteClient=IdentiteClient.getString("nom_etudiant")+" "+IdentiteClient.getString("prenom_etudiant");
                    }
        }   
        catch (SQLException ex) 
        {
            Logger.getLogger(InstantMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return RsIdentiteClient;
    }
    
    
    public int Connecte(int id_compte) throws ClassNotFoundException
    {
        int RsConnecte=-1;
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
            // on cree un objet Statement qui va permettre l'execution des requetes
            Statement s = con.createStatement();
            s.executeUpdate("update yahimenat.profil_etudiant set connecte=1 where id_compte='"+id_compte+"';");
            RsConnecte=1;
                   
        }   
        catch (SQLException ex) 
        {
            Logger.getLogger(InstantMessage.class.getName()).log(Level.SEVERE, null, ex);
            RsConnecte=-1;
        }
        return RsConnecte;
    }
     
            
            
   
    
    
    
    public static void main(String[] args) throws Exception 
    {
        
        InstantMessage IM;
        InetAddress in = null;
        IM = new InstantMessage();
        IM.InsertPortAddressClient(75,11111,in);
        System.out.println("port client =: "+IM.recupPortClient(75));
        IM.InsertPortAddressClient(75,11111,in);
        System.out.println("identite client =: "+IM.recupIdentiteClient(75));
        IM.Connecte(75);
        
    }
}
