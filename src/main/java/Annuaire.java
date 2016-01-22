/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmanuel Ménat
 */
import java.sql.*;
import java.util.*;


public class Annuaire {
	
	Connection conn;
	
	public Annuaire(String nomAnnuaire) {
		try {
		    Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection("jdbc:mysql://localhost/"+nomAnnuaire+"", "root", "");
		    // on cree un objet Statement qui va permettre l'execution des requetes
	        Statement s = conn.createStatement();
	
	        // On regarde si la table existe deja
	        String query = "select * from ANNUAIRE limit 1";
	        try {
	        	s.executeQuery(query);
	        } catch(Exception e) {
	        	// sinon on l'a cree
	        	s.execute("create table ANNUAIRE  ( " +
	        			" nom VARCHAR( 256 ) NOT NULL PRIMARY KEY, " +
	        			" telephone VARCHAR( 32 ) NOT NULL , " +
	        			" eMail VARCHAR( 256 ) NOT NULL)");
	        	// on ajoute des entrees de test
	        	s.executeUpdate("insert into ANNUAIRE values ('Toto', '0561123456', 'toto@ici.com')");
	        	s.executeUpdate("insert into ANNUAIRE values ('Titi', '0561123457', 'titi@ici.com')");
	        	s.executeUpdate("insert into ANNUAIRE values ('Tata', '0561123458', 'tata@ici.com')");
	        	s.executeUpdate("insert into ANNUAIRE values ('Tutu', '0561123459', 'tutu@ici.com')");
	        }
		} catch(Exception e) {
			// il y a eu une erreur
			e.printStackTrace();
		}
	}

	public String lireInfos(String nom) {
		try {
			Statement s = conn.createStatement();
			ResultSet rs = s.executeQuery("select * from ANNUAIRE where nom = '"+nom+"'");
	        if (rs.next()) {
	        	return rs.getString("nom")+":"+rs.getString("telephone")+":"+rs.getString("eMail");
	        } else {
	        	return null;
	        }
		} catch(Exception ex) {
			// il y a eu une erreur
			ex.printStackTrace();
			return null;
		}
	}

	public boolean ajoutInfos(String infos) {
		try {
			String [] tab = infos.split(":");
			if(tab.length != 3) 
				return false;
			String nom = tab[0];
			String telephone = tab[1];
			String eMail = tab[2];
			
			Statement s = conn.createStatement();
			if (s.executeUpdate("insert into ANNUAIRE values ('"+nom+"', '"+telephone+"', '"+eMail+"')")==1)
				return true;
			else
				return false;
		} catch(Exception ex) {
			// il y a eu une erreur
			ex.printStackTrace();
			return false;
		}
	}

	public boolean majInfos(String infos) {
		try {
			String [] tab = infos.split(":");
			if(tab.length != 3) 
				return false;
			String nom = tab[0];
			String telephone = tab[1];
			String eMail = tab[2];
			
			Statement s = conn.createStatement();
			if (s.executeUpdate("update ANNUAIRE set telephone='"+telephone+"', eMail='"+eMail+"' where nom='"+nom+"'")==1)
				return true;
			else
				return false;	
		} catch(Exception ex) {
			// il y a eu une erreur
			ex.printStackTrace();
			return false;
		}
	}
	
	public void fermer() throws Exception {		
		try {
			conn.close();
		} catch(Exception ex) {
			// il y a eu une erreur
			ex.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		Annuaire annu = new Annuaire("annu");
		System.out.println(annu.lireInfos("Toto"));
		System.out.println(annu.lireInfos("Bobby"));
		annu.ajoutInfos("Bobby:1234:bob@truc.com");
		System.out.println(annu.lireInfos("Bobby"));
		annu.majInfos("Bobby:12345678:bob@truc.net");
		System.out.println(annu.lireInfos("Bobby"));
		annu.fermer();		
	}
}