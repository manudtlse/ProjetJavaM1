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
        Connection con;
                      
	
	public Annuaire() {
		try {
		     Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
                    // on cree un objet Statement qui va permettre l'execution des requetes
                     Statement s = con.createStatement();
		} catch(Exception e) {
			// il y a eu une erreur
			e.printStackTrace();
		}
	}
 
                    
        

	public String lireInfos(int numetudiant) {
		try {	
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
                    Statement s = con.createStatement();
			ResultSet rs = s.executeQuery("select * from profil_etudiant where num_etudiant = '"+numetudiant+"';");
	        if (rs.next()) {
	        	return rs.getInt("num_etudiant")+":"+rs.getString("nom_etudiant")+":"+rs.getString("prenom_etudiant")+":"+rs.getString("date_naissance")+":"+rs.getString("mail")+":"+rs.getString("telephone")+":"+rs.getInt("id_competence");
	        } else {
                    return null;

	        }
		} catch(Exception ex) {
			// il y a eu une erreur
			ex.printStackTrace();
                        return null;
         

		}
	}

	public boolean creationProfilEtudiant(String infos) {
		try {
			String [] tab = infos.split(" ");
			if(tab.length != 6) 
				return false;
                        String nom_etudiant = tab[0];
			String prenom_etudiant = tab[1];
			String date_naissance = tab[2];
                        String mail = tab[3];
                        String telephone = tab[4];
                        String id_comp = tab[5]; //a convertir en int pour la BD
                        Integer id_competence=Integer.parseInt(tab[5]);
			
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
			Statement s = con.createStatement();

			if (s.executeUpdate("INSERT INTO profil_etudiant(nom_etudiant,prenom_etudiant,date_naissance,mail,telephone,id_competence) VALUES ('"+tab[0]+"','"+tab[1]+"','"+tab[2]+"','"+tab[3]+"','"+tab[4]+"','"+id_competence+"');")==1)
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
			if(tab.length != 7) 
				return false;
                        String nom_etudiant = tab[1];
			String prenom_etudiant = tab[2];
			String date_naissance = tab[3];
                        String mail = tab[4];
                        String telephone = tab[5];
                        Integer id_competence=Integer.parseInt(tab[6]);               //converti en int pour la BD

                        Integer num_etudiant=Integer.parseInt(tab[0]);
			
			Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection(url2,bdlogin,bdmdp);
			Statement s = con.createStatement();
			if (s.executeUpdate("update profil_etudiant set  nom_etudiant='"+nom_etudiant+"',prenom_etudiant='"+prenom_etudiant+"',date_naissance='"+date_naissance+"',mail='"+mail+"',telephone='"+telephone+"',id_competence='"+id_competence+"' where num_etudiant="+num_etudiant+";")==1)
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
			con.close();
		} catch(Exception ex) {
			// il y a eu une erreur
			ex.printStackTrace();
		}
	}
 public static void main(String[] args) throws Exception 
        {
	    String resultat;
            Boolean result;
            Annuaire an1;
            an1 = new Annuaire();
            resultat=an1.lireInfos(1);
            System.out.println("Résultat : "+resultat);
            result=an1.creationProfilEtudiant("nom2:prenom2:18/12/1993:nom2.prenom2@gmail.com:0665758387:1");
            an1.majInfos("5:nom4:prenom4:18/12/1993:nom4.prenom4@gmail.com:0665758387:1");
            System.out.println(result);
	}	

    
	
}