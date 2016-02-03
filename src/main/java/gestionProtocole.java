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
                        // ce qui correspond à mettre en base de données
                        /* Soit on met les foncion ici, soit on les met dans annuiaire et ici on appel les fonction
                        Je pense que vu que cest gestion protocole qui decoupe les chaines qu'on recoit, ca allege si
                        on appel juste les fonctions qu'on laisse dans annuaire, comme ca les fonctions concernant l'annuaire
                        Restent dans annuaire, et celles de l'identification on les met dans une classe identification et on fait
                        2 gestion protocols, 1 pour l'annuaire et 1 pour l'identification (Tessier a conseillé a ludo de faire 2
                        Gestion protocol, il aime bien donc si on le fait on est bien)*/
                        try 
                        {
                            String login = param[1];
                            String mdp = param[2];
                            String droits = param[3];
                            Inscription id = new Inscription (login ,mdp, droits);
                            if (id.Inscription()== -1)
                            {
                                resultat = "NONOK CREATION";
                            }
                            else 
                            {
                                resultat = "OK CREATION";
                            }
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR CREATION";
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
                                resultat = "OKCONNEXION " + id.toString();
                            }
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR CONNEXION";
                        }
                        break;
                        
                        
                    default: resultat = "ERREUR REQUETE INCONNUE";
                    
                }
            return resultat; 
	}	
}

