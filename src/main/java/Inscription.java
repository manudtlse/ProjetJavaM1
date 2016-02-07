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
public class Inscription
{
    Connection conn;
        private String url1="jdbc:mysql://binary-digit.net:3305/yahimenat";//ServeurIdentification
        private String url2="jdbc:mysql://binary-digit.net:3306/yahimenat";//ServeurAnnuaire
        private String bdlogin="yahimenat";
        private String bdmdp="odaime";
	private String identifiant;
        private String mdp;
        private String droits;
	
       public Inscription(String identifiant,String mdp,String droits) 
       {
            this.identifiant=identifiant;
            this.mdp=mdp;
            this.droits=droits;
        }
        
        public int Inscription()
        {  
            int resultat = 0;
                try 
                {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url1,bdlogin,bdmdp);
                    // on cree un objet Statement qui va permettre l'execution des requetes
                     Statement s = con.createStatement();
                    try {
                            ResultSet Result;
                            Result = s.executeQuery("SELECT login FROM yahimenat.compte WHERE login ='"+identifiant+"';");
                            if (!Result.next())
                            {
                                s.executeUpdate("insert into COMPTE(login,password,droit) values ('"+identifiant+"','"+mdp+"','"+droits+"');");
                                resultat=1;
                            } 
                            else 
                            {
                                
                                resultat=-1;
                            }
                        } 
                    catch(Exception e) 
                    {
                        System.out.println("Erreur"+e.getMessage()); 
                    }
		} 
                catch(ClassNotFoundException | SQLException e) 
                {
                    System.out.println("Erreur : "+e.getMessage());
                }
                return resultat;
        }
        public static void main(String[] args) throws Exception 
        {
	    Inscription in1;
            in1 = new Inscription("prof2","mdp2","admin");
            in1.Inscription();
	}
}


