
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
public class Admin {
    private String url1="jdbc:mysql://binary-digit.net:3305/yahimenat";//ServeurIdentification
    private String url2="jdbc:mysql://binary-digit.net:3306/yahimenat";//ServeurAnnuaire
    private String bdlogin="yahimenat";
    private String bdmdp="odaime";
    Connection conn;
    private String identifiant;
    private String mdp;
    public int id_compte_admin_resultat=0;
    
     public Admin(String identifiant,String mdp) 
    {
        this.identifiant=identifiant;
        this.mdp=mdp;
    }
     
    public int recupIdCompte(String loginAdminCompte)
    {
        int id_Compte_administre=-1;
        
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url1,bdlogin,bdmdp);
            // on cree un objet Statement qui va permettre l'execution des requetes
            Statement s = con.createStatement();
            try 
            {
                ResultSet compteID;
                compteID = s.executeQuery("SELECT id_compte FROM compte WHERE login = '"+loginAdminCompte+"';");
                if (!compteID.next())
                    {
                        id_Compte_administre=-1;
                    } 
                else 
                    {
                        id_Compte_administre=compteID.getInt("id_compte");
                    }
            } 
            catch(Exception e) 
            {
                System.out.println("Erreur"+e.getMessage());
            }
        } 
        catch(ClassNotFoundException | SQLException e) 
        {
            System.out.println("Erreur"+e.getMessage());
        }
       return id_Compte_administre; 
    }
    public int connexionAdmin()
    {  
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url1,bdlogin,bdmdp);
            // on cree un objet Statement qui va permettre l'execution des requetes
            Statement s = con.createStatement();
            try 
            {
                ResultSet compteID_Admin;
                compteID_Admin = s.executeQuery("SELECT id_compte FROM compte WHERE login = '"+identifiant+"' AND password='"+mdp+"' AND droit='admin';");
                if (!compteID_Admin.next())
                    {
                        id_compte_admin_resultat=-1;
                    } 
                else 
                    {
                        id_compte_admin_resultat=compteID_Admin.getInt("id_compte");
                    }
            } 
            catch(Exception e) 
            {
                System.out.println("Erreur"+e.getMessage());
            }
        } 
        catch(ClassNotFoundException | SQLException e) 
        {
            System.out.println("Erreur"+e.getMessage());
        }
        return id_compte_admin_resultat;
    }

    public boolean ChangeMdpAdmin(String infos)
    {
            boolean resultat;
            try
            {
                String [] tab = infos.split(" ");
                if(tab.length != 2) 
                    return false;
                String NouveauMdp = tab[0];
                String login = tab[1];
                System.out.println(infos);
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url1,bdlogin,bdmdp);
                Statement s = con.createStatement(); 
                int rs = s.executeUpdate("update yahimenat.compte set  password='"+NouveauMdp+"' where login='"+login+"';");
                resultat=true;
            }
           catch(NumberFormatException | ClassNotFoundException | SQLException e) 
                {
                    System.out.println("Erreur"+e.getMessage());
                   resultat=false;
                }
            return resultat;
    }        
   /* public static void main(String[] args) throws Exception 
    {
        String ind="i1";
        int id_compte;
        Admin ad1;
        ad1 = new Admin();
        
        id_compte=ad1.connexionAdmin(ind);
        //an1.majInfos("5:nom4:prenom4:18/12/1993:nom4.prenom4@gmail.com:0665758387:1");
        System.out.println(id_compte);     
    }	*/	
    
}
