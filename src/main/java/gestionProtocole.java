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
                            int id_competence = Integer.parseInt(param[6]);
                            int id_compte = Integer.parseInt(param[7]);
                            String infos=nom+" "+prenom+" "+date_naissance+" "+mail+" "+telephone+" "+id_competence+" "+id_compte;
                            int resu=an1.creationProfilEtudiant(infos);
                            if (resu==-1)
                            {
                                resultat = "NON OK CREATION PROFIL ETUDIANT";
                            }
                            else 
                            {
                                resultat = "OK CREATION PROFIL ETUDIANT";
                            }
                        }    
                        catch (Exception e) 
                        {
                            resultat = "ERREUR CREATION PROFIL";
                        }
                            break;
                    
                    case "AFFICHERPROFILCOMPTE" :
                        try 
                        {
                            String id_compte = param[1];
                            String infos = id_compte;
                            String res=an1.AfficherListeProfilCompte(infos);
                            resultat = "OKAFFICHAGE PROFIL ETUDIANT DU COMPTE "+res;
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR AFFICHARGE PROFIL ETUDIANT DU COMPTE";
                        }
                        break;    
                    
                     case "MODIFICATIONMAIL" :
                        try 
                        {
                            String nouveauMail = param[1];
                            int id_compte = Integer.parseInt(param[2]);
                            String infos =nouveauMail+" "+id_compte;
                            an1.majInfoMail(infos);
                            resultat = "OKMODIFICATIONMAIL PROFIL ETUDIANT";
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR MODIFICATIONMAIL PROFIL ETUDIANT";
                        }
                        break; 
                   
                     case "MODIFICATIONTEL" :
                        try 
                        {
                            String nouveauTel = param[1];
                            int id_compte = Integer.parseInt(param[2]);
                            String infos =nouveauTel+" "+id_compte;
                            an1.majInfoTel(infos);
                            resultat = "OKMODIFICATIONTEL PROFIL ETUDIANT";
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR MODIFICATIONTEL PROFIL ETUDIANT";
                        }

                        break;
                    case "MODIFICATIONCOMPETENCE" :
                        try 
                        {
                            String nouvelleCompetence = param[1];
                            int id_compte = Integer.parseInt(param[2]);
                            String infos =nouvelleCompetence+" "+id_compte;
                            an1.majInfoCompetence(infos);
                            resultat = "OKMODIFICATIONCOMPETENCE PROFIL ETUDIANT";
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR MODIFICATION COMPETENCE PROFIL ETUDIANT";
                        }

                        break;
                    
                     case "RECHERCHE ETUDIANT" :
                        try
                        {
                         
                            String nomRecherche = param[1];
                            String res = an1.RechercherEtudiant(nomRecherche);
                            resultat= "OKRECHERCHE ETUDIANT "+res;
                        }
                        catch (Exception e) 
                        {
                            resultat = "ERREUR RECHERCHE ETUDIANT";
                        }
                        break;
                        
                        
                        
                    default: resultat = "ERREUR REQUETE INCONNUE";
                    
                }
            return resultat; 
	}	
}


