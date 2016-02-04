/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Emmanuel Ménat
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class gestionProtocole {
	
        Connection cn =null;
        Statement st =null;
        String url = "jdbc:mysql://binary-digit.net:3305/yahimenat";
        String url2 = "jdbc:mysql://binary-digit.net:3306/yahimenat";
        String loginBDD = "yahimenat";
        String passwordBDD = "odaime";
         
        Annuaire an1 = new Annuaire();
        
	public String travaille (String requete) throws SQLException, ClassNotFoundException {
		
                String [] param = requete.split(" ");
                
                // Retour OK/NOK
                String resultat = "";
                
                Class.forName("com.mysql.jdbc.Driver");
                cn = DriverManager.getConnection(url, loginBDD, passwordBDD);
                st = cn.createStatement();
                
                switch (param[0]) {
                    
                    // Creer compte
                    case "INSCRIPTION" :
                        try 
                        {
                            String login = param[1];
                            String mdp = param[2];
                            String droits = param[3];
                            Inscription id = new Inscription (login ,mdp, droits);
                            if (id.Inscription()== -1)
                            {
                                resultat = "NONOKINSCRIPTION";
                            }
                            else 
                            {
                                resultat = "OKINSCRIPTION";
                            }
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR INSCRIPTION";
                        }
                        break;
                        // Creer compte
                    case "CONNEXION" :
                        try 
                        {
                          String login = param[1];
                          String mdp = param[2];
                          Identification id = new Identification (login ,mdp); 
                          if (id.connexion()== -1)
                            {
                                resultat = "NONOKCONNEXION";
                            }
                            else 
                            {
                                int res = id.connexion();
                                resultat = "OKCONNEXION " + res;
                            }
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR CONNEXION";
                        }
                        break;
                        
                        
                        
                    case "CREATIONPROFIL" :
                        try 
                        {
                            String nom = param[1];
                            String prenom = param[2];
                            String date_naissance = param[3];
                            String mail = param[4];
                            String telephone = param[5];
                            int id_compte = Integer.parseInt(param[6]);
                            String infos=nom+" "+prenom+" "+date_naissance+" "+mail+" "+telephone+" "+id_compte;
                            an1.creationProfilEtudiant(infos);
                            resultat = "OKCREATION PROFIL ETUDIANT";
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR CREATION PROFIL ETUDIANT";
                        }
                        
                    break;
                        
                        
                    default: resultat = "ERREUR REQUETE INCONNUE";
                    
                }
            return resultat; 
	}	
}

