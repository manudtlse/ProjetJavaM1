
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
public class Identification {
    Connection conn;
	
	public Identification(String identifiant,String mdp,String droits) {
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    Connection con = DriverManager.getConnection("jdbc:mysql://binary-digit.net:3305/ServeurIdentification", "yahimenat", "odaime");
		    // on cree un objet Statement qui va permettre l'execution des requetes
	        Statement s = con.createStatement();
	        try {
	        	ResultSet compteID;
                        compteID = s.executeQuery("SELECT id_compte FROM compte WHERE login = "+identifiant+" AND  password="+mdp+";)");
	        } catch(Exception e) {
	        	// sinon on l'a cree
	               
	        }
		} catch(ClassNotFoundException | SQLException e) {
		}
	}
        //Fermer connection
	public void fermer() throws Exception {		
		try {
			conn.close();
		} catch(Exception ex) {
		}
	}
	
	public static void main(String[] args) throws Exception {
	    Identification ident;
            ident = new Identification("profil3","mdp3","admin");
	}
}
    

