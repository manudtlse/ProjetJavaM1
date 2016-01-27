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
        private String url1="jdbc:mysql://binary-digit.net:3305/ServeurIdentification";
        private String url2="jdbc:mysql://binary-digit.net:3306/ServeurAnnuaire";
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
                    Connection con =null;
                    con = DriverManager.getConnection(url1, bdlogin, bdmdp);
                    Statement s = con.createStatement();
                    try {
                            ResultSet Result;
                            Result = s.executeQuery("SELECT login,password WHERE login = "+identifiant+" AND  password="+mdp+";)");
                            if (!Result.next())
                            {
                                s.executeUpdate("insert into COMPTE values (identifiant,mdp,droits)");
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
                    System.out.println("Erreur"+e.getMessage());
                }
                return resultat;
        }
        public static void main(String[] args) throws Exception 
        {
	    Inscription in1;
            in1 = new Inscription("prof1","mdp1","admin");
            in1.Inscription();
	}
}


