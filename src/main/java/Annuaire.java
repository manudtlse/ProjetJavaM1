/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmanuel Ménat
 */


//http://blog.paumard.org/cours/jdbc/chap02-apercu-exemple.html


import java.sql.*;
import java.util.*;


public class Annuaire {
        private String url1="jdbc:mysql://binary-digit.net:3305/yahimenat";//ServeurIdentification
        private String url2="jdbc:mysql://binary-digit.net:3306/yahimenat";//ServeurAnnuaire
        private String bdlogin="yahimenat";
        private String bdmdp="odaime";
	
	
	Connection conn;
	
	public Annuaire() 
        {
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
	
	
}