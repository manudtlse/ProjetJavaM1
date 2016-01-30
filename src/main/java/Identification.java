
import java.sql.*;
import java.util.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmanuel Ménat
 */
public class Identification 
{
        private String url1="jdbc:mysql://binary-digit.net:3305/yahimenat";//ServeurIdentification
        private String url2="jdbc:mysql://binary-digit.net:3306/yahimenat";//ServeurAnnuaire
        private String bdlogin="yahimenat";
        private String bdmdp="odaime";
	private String identifiant;
        private String mdp;
        Connection conn;
	
       public Identification(String identifiant,String mdp) 
        {
            this.identifiant=identifiant;
            this.mdp=mdp;
        }
        
        public int connexion()
        {  
            int resultat = 0;
                 try 
                 {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url1,bdlogin,bdmdp);
                    // on cree un objet Statement qui va permettre l'execution des requetes
                     Statement s = con.createStatement();
                    try {
                            ResultSet compteID;
                            compteID = s.executeQuery("SELECT id_compte FROM compte WHERE login = '"+identifiant+"' AND  password='"+mdp+"';");
                            if (!compteID.next())
                            {
                                resultat=-1;
                            } 
                            else 
                            {
                                resultat=compteID.getInt("id_compte");
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
            return resultat;
        }
        //Fermer connection
	public void fermer() throws Exception 
        {		
		try 
                {
			conn.close();
		} 
                catch(Exception ex) 
                {
		}
	}
	
	public static void main(String[] args) throws Exception 
        {
            int res;
            res = 0;
	    Identification ident;
            ident = new Identification("profil2","mdp1");
            res=ident.connexion();
            System.out.println("resultat : "+res);
	}
}
    

